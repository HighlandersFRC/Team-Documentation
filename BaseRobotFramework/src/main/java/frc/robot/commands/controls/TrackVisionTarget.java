/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controls;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

public class TrackVisionTarget extends CommandBase {
  /**
   * Creates a new TrackVisionTarget.
   */
  private double latestLockTime;
  public TrackVisionTarget() {
    // Use addRequirements() here to declare subsystem dependencies.
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotMap.visionRelay.set(Value.kForward);
    latestLockTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(RobotMap.drive.trackVisionTape()>1.5){
      latestLockTime = Timer.getFPGATimestamp();
    }

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotMap.visionRelay.set(Value.kReverse);
    RobotMap.drive.setLeftSpeed(0);
    RobotMap.drive.setRightSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp()-latestLockTime >0.4;

  }
}
