/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tools.controlLoops;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;
import frc.robot.RobotStats;

public class CascadingPIDTurn extends CommandBase {
  private PID turnPID;
  private double desiredAngle;
  private double p;
  private double i;
  private double d;

  public CascadingPIDTurn(double Angle, double kp, double ki, double kd) {
    desiredAngle = Angle;
    p = kp;
    i = ki;
    d = kd;
    addRequirements(RobotMap.drive);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    turnPID =  new PID(p,i,d);
    turnPID.setMaxOutput(RobotStats.robotMaxVelocity);
    turnPID.setMinOutput(-RobotStats.robotMaxVelocity);
    turnPID.setSetPoint(desiredAngle);
  }
  public void setTarget(double target){
    turnPID.setSetPoint(target);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    turnPID.updatePID(RobotMap.drive.getDriveTrainHeading());
    RobotMap.drive.setLeftSpeed(-turnPID.getResult());
    RobotMap.drive.setRightSpeed(turnPID.getResult());

  }
  public void forceFinish(){
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    if(Math.abs(RobotMap.drive.getDriveTrainHeading()-desiredAngle)<1.5){
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    RobotMap.drive.Stop();
    System.out.println("done");
  }

}
