/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.Robot;
import frc.robot.RobotConfig;
import frc.robot.RobotMap;
import frc.robot.commands.controls.TrackVisionTarget;
import frc.robot.sensors.DriveEncoder;
import frc.robot.tools.controlLoops.PID;
import frc.robot.tools.pathTools.Odometry;

public class DriveTrain extends SubsystemBase {
	//turn deadzone for controller drift
	private double deadZone = 0.01;
	//drive train input values: turn = desired differential, throttel = desired velocity
	private double throttel = 0;
	//desired forward/backwards velocity while tracking the vision tape in ft/s
	private double trackTapeThrottel;
	//setting up sensors for different sides of the drive train
	private static DriveEncoder leftMainDrive = new DriveEncoder(RobotMap.leftDriveLead,
			RobotMap.leftDriveLead.getSelectedSensorPosition(0));
	private static DriveEncoder rightMainDrive = new DriveEncoder(RobotMap.rightDriveLead,
			RobotMap.rightDriveLead.getSelectedSensorPosition(0));
	//FPID values for the velocity control loop on drive train, taken from drive train characterization 
	private double vKF = 0;
	private double vKP = 0;
	private double vKI = 0;
	private double vKD = 0;
	//Talon SRX can store multiple FPID loops, this specifies which one is desired
	private int profile = 0;
	//PID for trackingn vision tape, outputs values in ft/s which then goes to the drive train velocity control loop
	private PID alignmentPID;
	private double aKP = 0;
	private double aKI = 0;
	private double aKD = 0;
	//offset for misaligned camera
	private double visionOffset = 0;
	//odometry which tracks drive train position throughout autonomous movements
	private Odometry autoOdometry;
	//boolean which allows for interchange between tracking the vision tape and the alignment PID
	private boolean driveTrainBeingUsed;
	private TrackVisionTarget trackVisionTarget;
	public DriveTrain() {

	}
	public void startAutoOdometry(boolean reversed, double x, double y){
		autoOdometry = new Odometry(reversed, x, y);
		autoOdometry.start();
	};
	public double getDriveTrainX(){
		return autoOdometry.getX();
	}
	public double getDriveTrainY(){
		return autoOdometry.getY();
	}
	public double getDriveTrainHeading(){
		return autoOdometry.gettheta();
	}
	public void setDriveTrainX(double x){
		autoOdometry.setX(x);
	}
	public void setDriveTrainY(double y){
		autoOdometry.setY(y);
	}
	public void setDriveTrainHeading(double theta){
		autoOdometry.setTheta(theta);
	}
	public void setOdometryReversed(boolean reversed){
		autoOdometry.setReversed(reversed);
	}
	public void initVelocityPIDs(){
		RobotMap.leftDriveLead.selectProfileSlot(profile, 0);
		RobotMap.leftDriveLead.config_kF(profile, vKF, 0);
		RobotMap.leftDriveLead.config_kP(profile, vKP, 0);
		RobotMap.leftDriveLead.config_kI(profile, vKI, 0);
		RobotMap.leftDriveLead.config_kD(profile, vKD, 0);
		RobotMap.rightDriveLead.selectProfileSlot(profile, 0);
		RobotMap.rightDriveLead.config_kF(profile, vKF, 0);
		RobotMap.rightDriveLead.config_kP(profile, vKP, 0);
		RobotMap.rightDriveLead.config_kI(profile, vKI, 0);
		RobotMap.rightDriveLead.config_kD(profile, vKD, 0);

	}
	public void initAlignmentPID(){
		alignmentPID = new PID(aKP, aKD, aKI);
		alignmentPID.setSetPoint(visionOffset);
		alignmentPID.setMaxOutput(8);
		alignmentPID.setMinInput(-8);
	}
	public void arcadeDrive(){
		if(!driveTrainBeingUsed){
			//coast mode b/c its smoother
			RobotConfig.setDriveMotorsCoast();
			double leftPower;
			double rightPower;
			double differential;
			if(Math.abs(ButtonMap.getDriveThrottle())>deadZone){
				//change to throttel curve for smoother driving
				throttel = Math.tanh(ButtonMap.getDriveThrottle())*(4/Math.PI); 
			}
			else{
				throttel = 0;
			}
			//deadzone checks for rotation
			if(Math.abs(ButtonMap.getRotation())>deadZone){
				differential = ButtonMap.getRotation()*0.75;
			}
			else{
				differential = 0;
			}
			leftPower = (throttel - (differential));
			rightPower = (throttel + (differential));
			//this maintains the ratio between the left power and right power even if throttel + differential >1
			if(Math.abs(leftPower)>1) {
				rightPower = Math.abs(rightPower/leftPower)*Math.signum(rightPower);
				leftPower = Math.signum(leftPower);
			}
			else if(Math.abs(rightPower)>1) {
				leftPower = Math.abs(leftPower/rightPower)*Math.signum(leftPower);
				rightPower = Math.signum(rightPower);
			}
			RobotMap.leftDriveLead.set(ControlMode.PercentOutput, leftPower);
			RobotMap.rightDriveLead.set(ControlMode.PercentOutput, rightPower);
		}

	}

	public double trackVisionTape(){
		try{
			//allows driver to control forward/backward speed while tracking tape
			if(RobotState.isOperatorControl()){
				if(Math.abs(ButtonMap.getDriveThrottle())>deadZone){
					trackTapeThrottel = Math.tanh(ButtonMap.getDriveThrottle())*(4/Math.PI); 
				}
				else{
					trackTapeThrottel = 0;
				}
			}
			else{
				trackTapeThrottel = 0;
			}
			driveTrainBeingUsed = true;
			SmartDashboard.putNumber("offset", visionOffset);
			//ALWAYS use brake for closed loop controls
			RobotConfig.setDriveMotorsBrake();
			//updating vision camera values
			Robot.visionCamera.updateVision();
			//if the last time you had a valid vision value was less than 0.25 seconds ago, then update the PID, else do nothing
			if(Timer.getFPGATimestamp()-Robot.visionCamera.lastParseTime>0.25){
				alignmentPID.updatePID(visionOffset);
				return 0;
			}
			else{
				alignmentPID.updatePID(Robot.visionCamera.getAngle());
			}
			//converts tape track throttel to ft/s with a max value of 6ft/second and then adds/subtracts the alignment PID
			setLeftSpeed((trackTapeThrottel*6)+alignmentPID.getResult());
			setRightSpeed((trackTapeThrottel*6)-alignmentPID.getResult());
			//this returns the angle for use it other areas
			return Math.abs(Robot.visionCamera.getAngle()-visionOffset);	
		}
		catch(Exception e){
			return 0;
		}
	}
	public void Stop(){
		//stops the drive train
		RobotMap.leftDriveLead.set(ControlMode.PercentOutput, 0);
		RobotMap.rightDriveLead.set(ControlMode.PercentOutput, 0);
	}
	public void setLeftSpeed(double speed){
		//sets left speed in ft/s
		RobotMap.leftDriveLead.set(ControlMode.Velocity, leftMainDrive.convertftpersToNativeUnitsper100ms(speed));
	}
	public void setRightSpeed(double speed){
		//sets right speed in ft/s
		RobotMap.rightDriveLead.set(ControlMode.Velocity, rightMainDrive.convertftpersToNativeUnitsper100ms(speed));
	}
	public void setLeftPercent(double percent){
		//sets left percent
		RobotMap.leftDriveLead.set(ControlMode.PercentOutput, percent);
	}
	public void setRightPercent(double percent){
		//set right percent
		RobotMap.rightDriveLead.set(ControlMode.PercentOutput, percent);
	}
	@Override
	public void periodic() {
	}
	//allows for changing of the vision offset
	public void shiftVisionLeft(){
		visionOffset = visionOffset +0.5;
		alignmentPID.setSetPoint(visionOffset);
	}
	public void shiftVisionRight(){		
		visionOffset = visionOffset-0.5;
		alignmentPID.setSetPoint(visionOffset);
	}
	public void teleopPeriodic(){
		//tracks vision tape
		if(ButtonMap.autoTarget()){
			trackVisionTarget =	new TrackVisionTarget();
			trackVisionTarget.schedule();
		}
		else if(ButtonMap.endAutoTarget()){
			trackVisionTarget.cancel();
		}
		//if you aren't running a firing sequence, or tracking the tape then you can have drive train control
		else{
			//allows for arcade drive
			if(!ButtonMap.manualTarget()){
				arcadeDrive();
			}
			//allows for minor adjustments for manual targeting at 1 ft/s
			else{
				if(ButtonMap.manualAdjustLeft()){
					this.setLeftSpeed(-1);
					this.setRightSpeed(1);
				}
				else if(ButtonMap.manualAdjustRight()){
					this.setLeftSpeed(1);
					this.setRightSpeed(-1);

				}
				else{
					this.setLeftPercent(0);
					this.setRightPercent(0);
				}
			}
			
		}
		driveTrainBeingUsed = false;
	}
		
}

