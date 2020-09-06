package frc.robot;
/*This is for the interface between the physical components on the robot-motor controllers, sensors,
lights-and the software objects which represent them*/

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.sensors.DriveEncoder;
import frc.robot.subsystems.DriveTrain;

public final class RobotMap{
    public static final AHRS navx = new AHRS(Port.kMXP);
    private static final int leftDriveLeadID = 0;
    private static final int rightDriveLeadID = 0;
    public static final TalonFX leftDriveLead = new TalonFX(leftDriveLeadID);
    public static final TalonFX rightDriveLead = new TalonFX(rightDriveLeadID);
    public static final Relay visionRelay = new Relay(0);
    public static final DriveTrain drive = new DriveTrain();
    public static final DriveEncoder leftDriveEncoder = new DriveEncoder(leftDriveLead, 0);
    public static final DriveEncoder rightDriveEncoder = new DriveEncoder(rightDriveLead, 0);
    
    public static SupplyCurrentLimitConfiguration robotCurrentConfigurationEnabled = new SupplyCurrentLimitConfiguration(true, RobotStats.driveTrainMaxCurrent, RobotStats.driveTrainPeakThreshold, RobotStats.driveTrainPeakTime);

    public static SupplyCurrentLimitConfiguration robotCurrentConfigurationDisabled = new SupplyCurrentLimitConfiguration(false, RobotStats.driveTrainMaxCurrent, RobotStats.driveTrainPeakThreshold, RobotStats.driveTrainPeakTime);
    
    public static TalonFX driveMotors[] = {
        RobotMap.leftDriveLead,
        RobotMap.rightDriveLead,
    };
    public static TalonFX driveMotorLeads[] = {
        RobotMap.leftDriveLead,
        RobotMap.rightDriveLead,
    };
    public static TalonFX allTalonMotorLeads[] = {
        RobotMap.leftDriveLead,
        RobotMap.rightDriveLead,
    };
    public static TalonFX allFalcons[] = {
        RobotMap.leftDriveLead,
        RobotMap.rightDriveLead,
    };

}