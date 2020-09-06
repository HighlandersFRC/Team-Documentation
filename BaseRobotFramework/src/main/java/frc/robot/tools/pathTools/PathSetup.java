package frc.robot.tools.pathTools;

import edu.wpi.first.wpilibj.trajectory.Trajectory;

public class PathSetup {
	private boolean isReversed;
	private Trajectory mainPath;
	public PathSetup(Trajectory trajectory, boolean reverse) {
		mainPath = trajectory;
		isReversed = reverse;
	}
	public boolean getReversed(){
		return isReversed;
	}
	public Trajectory getMainPath(){
		return mainPath;
	}
	   
}


