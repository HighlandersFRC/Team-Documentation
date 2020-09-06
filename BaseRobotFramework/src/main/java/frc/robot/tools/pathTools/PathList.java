/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tools.pathTools;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import java.nio.file.Paths;

public class PathList {
  private static Trajectory center3AutoTraj1;
  public static PathSetup center3AutoPath1;
  private static Trajectory right6AutoTraj1;
  public static PathSetup right6AutoPath1;
  private static Trajectory right6AutoTraj2;
  public static PathSetup right6AutoPath2;
  private static Trajectory right8AutoTraj1;
  public static PathSetup right8AutoPath1;
  private static Trajectory right8AutoTraj2;
  public static PathSetup right8AutoPath2;


   //remember that for all paths if the first point is at (0,0,0) for some reason the end y value is revesred in the coordinate plane
  //for example for a path from (x,y,h) to (0,0,0) a path that goes from (0,0,0) to (x,y,h) would look the same but for one you would 
  // be decreasing y units on the coordinate plane, while in the other you would be increasing y units
  public PathList() {
    try{
      center3AutoTraj1 = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Center3Highgoal.wpilib.json"));
      center3AutoPath1 = new PathSetup(center3AutoTraj1, true);

      right6AutoTraj1 = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Right6Highgoal1.wpilib.json"));
      right6AutoPath1 = new PathSetup(right6AutoTraj1, true);
      right6AutoTraj2 = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Right6HighGoal2.wpilib.json"));
      right6AutoPath2 = new PathSetup(right6AutoTraj2, false);

      right8AutoTraj1 = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Right8HighGoal1.wpilib.json"));
      right8AutoPath1 = new PathSetup(right8AutoTraj1, true);
      right8AutoTraj2 = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Right8HighGoal2.wpilib.json"));
      right8AutoPath2 = new PathSetup(right8AutoTraj2, false);
    }      
    
    catch(Exception e){

    }
	}
    
  
  public void resetAllPaths(){
  }
}
 
