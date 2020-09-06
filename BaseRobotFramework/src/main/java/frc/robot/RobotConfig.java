/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;



/**
 * Add your docs here.
 */
public class RobotConfig {
    public void setStartingConfig() {

        RobotConfig.setAllMotorsBrake();
        RobotMap.rightDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        RobotMap.leftDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);

        RobotMap.rightDriveLead.setInverted(true);

        RobotMap.leftDriveLead.setInverted(false);

        RobotMap.leftDriveLead.setSensorPhase(false);
        RobotMap.rightDriveLead.setSensorPhase(false);

        RobotMap.leftDriveLead.setSelectedSensorPosition(0, 0, 0);
        RobotMap.rightDriveLead.setSelectedSensorPosition(0, 0, 0);

        RobotMap.drive.initVelocityPIDs();
        RobotMap.drive.initAlignmentPID();

        RobotConfig.enableDriveCurrentLimiting();
        RobotConfig.setDriveTrainVoltageCompensation();

    }

    public void setTeleopConfig() {
        RobotConfig.disableDriveTrainVoltageCompensation();
        RobotConfig.enableDriveCurrentLimiting();
        RobotConfig.setDriveMotorsCoast();
    }
    public void setAutoConfig(){
        RobotConfig.setDriveTrainVoltageCompensation();
        RobotConfig.enableDriveCurrentLimiting();
        RobotConfig.setDriveMotorsBrake();
    }
    public static void setAllMotorsBrake() {
		for(TalonFX talon:RobotMap.allFalcons){
            talon.setNeutralMode(NeutralMode.Brake);
        }
    }
    public static void setAllMotorsCoast() {
		for(TalonFX talon:RobotMap.allFalcons){
            talon.setNeutralMode(NeutralMode.Coast);
        }
	}
	public static void setDriveMotorsCoast() {
		for(TalonFX talon:RobotMap.driveMotors){
            talon.setNeutralMode(NeutralMode.Coast);
        }
	}
	public static void setDriveMotorsBrake() {
		for(TalonFX talon:RobotMap.driveMotors){
            talon.setNeutralMode(NeutralMode.Brake);
        }
    }
    public static void enableDriveCurrentLimiting() {
		for(TalonFX talon:RobotMap.driveMotors){
            talon.configSupplyCurrentLimit(RobotMap.robotCurrentConfigurationEnabled);
        }
    }
    public static void disableDriveCurrentLimiting() {
		for(TalonFX talon:RobotMap.driveMotors){
            talon.configSupplyCurrentLimit(RobotMap.robotCurrentConfigurationDisabled);
        }
    }
    public static void setDriveTrainVoltageCompensation(){
        for(TalonFX talon:RobotMap.driveMotors){
            talon.configVoltageCompSaturation(RobotStats.voltageCompensationValue);
            talon.enableVoltageCompensation(true);
        }
    }
    public static void disableDriveTrainVoltageCompensation(){
        for(TalonFX talon:RobotMap.driveMotors){
            talon.enableVoltageCompensation(false);
        }
    }
}
