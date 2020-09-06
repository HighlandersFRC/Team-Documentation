package frc.robot.sensors;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.RobotStats;

public class DriveEncoder {
	private TalonFX masterTalon;
	private int startingValue;
	
	public DriveEncoder(TalonFX talon, int startingValue) {
		masterTalon = talon;
	}
	public double getEncoderValue() {
		return masterTalon.getSelectedSensorPosition(0)-startingValue;
	}
	public double getEncoderVelocity(){
		return masterTalon.getSelectedSensorVelocity(0);
	}
	public double getDistance(){
		return ((((getEncoderValue())/RobotStats.driveTicsPerWheelRotation)*RobotStats.wheelCircum));
	}
	public double getVelocity(){
		return (((((getEncoderVelocity()*10))/RobotStats.driveTicsPerWheelRotation)*RobotStats.wheelCircum));
	}
	public void softReset(){
		startingValue = masterTalon.getSelectedSensorPosition(0);
	}
	public double convertftpersToNativeUnitsper100ms(double feetPerSecond){
		return (((feetPerSecond/10)/(RobotStats.wheelCircum))*RobotStats.driveTicsPerWheelRotation);
	}

}
