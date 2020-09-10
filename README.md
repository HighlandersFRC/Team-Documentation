Team-Documentation
==================
Updated Repositories:
- 2020-Chronostar: https://github.com/HighlandersFRC/2020-Chronostar
- 2020-Mirage: https://github.com/HighlandersFRC/2020-Mirage
- 2019-Gravistar: https://github.com/HighlandersFRC/2019-Gravastar
- 2019-Mirage: https://github.com/HighlandersFRC/2019-Mirage
- 2018-Scymitar-V2: https://github.com/HighlandersFRC/2018-Symitar-V2 (v2 due to a full reprogramming of Symitar during the 2018 season)
- 2016-Magnitar: https://github.com/HighlandersFRC/2016-Java (included as it is consistently updated as a way to train new programmers)

This is a list of various updated repositories. Each of these repositories corresponds to a specific robot. Dev branches in each repository contain in season development, while Master branches should include competition code.  Any updates to the code of these robots should be entered as a branch within the repositories named under the convention *year*-*robot name*-*developer name*.

Included in this repository is a robot project containing past code which is likely to be applicable to future robots. While this repository is set up as a nearly complete robot project, it is not designed to be used for this purpose, nor is it designed to be a absolute framework to structure future robot code on. This is simply the structure that was used and tested during the 2019-2020 season. A list of major features that are included within this project are:

 - The TrackVisionTarget class in commands/controls and corresponding trackVisionTape method in Drive, which when taken together manipulate the drivetrain in order to track a vision target using a Jevois camera. 
 - The various classes in the sensors folder, which allow other classes to interact with various common sensors. The DriveEncoder and Navx classes have a secondary use of allowing the programmer to make multiple instances of a sensor which can be accessed simultaneously. This allows the programmer to create a version of a sensor zeroed at a specific time or location without changing the output of the main sensor. 
 - A tried and tested acradeDrive method within the Drive subsystem which includes throttle modulation allowing for more precise driving, and integration with vision tracking. 
 - An integration within the Drivetrain subsystem with the Odometry class which allows the user to discover the robots start relative position and orientation. 
 - Custom Vector and Point classes which allow for various mathematical operations to be done on these objects
 - A CascadingPIDTurn command allowing the robot to change its orientation. 
 - A RoboRIO based PID class which allows for custom loops to be built and run on the robot. 
 - A PurePursuitController class which allows for the user to follow Pathweaver generated paths. These paths are added to the project by placing them in the Deploy folder, and then instantiating a PathSetup object within the PathList class. Examples for proper use of the PurePursuit controller can be found in the Autonomous routines of the 2020-Chronostar project. Various versions of the PurePursuitController class can be found throughout past projects as it has been under development for the past three years, this version simply represents the most up to date PurePursuitController. While many of the past versions of the PurePursuitController have used versions of Jaci's Pathfinder, this dependency has been removed due to its discontinuation as a supported product. Currently, many of the underlying functions of the PurePursuitContoller rely on WPI's Trajectory libraries included with the standard WPILIB download. An explanation of the function of the PurePursuitController can be found here: https://www.chiefdelphi.com/t/paper-implementation-of-the-adaptive-pure-pursuit-controller/166552
 - A robot structure which allows for relatively easy year to year development by making most of the functions of the robot as general as possible and separating many of the major components.  

In terms of physical differences that matter for development, 2020-Chronostar is the only robot on this list that requires either TalonFX libraries or Spark Max libraries; every other robot only uses the TalonSRX libraries. Every updated repository requires NavX libraries and uses a NavX imu.


## Setting up the Java Linter
- Make sure you have `C:\Users\Public\wpilib\jdk\bin` added to your PATH.
- Add the "Run on Save" extension to Code.
- Go [here] (https://www.github.com/google/google-java-format), and grab the latest `.jar` file from the releases page.
- Open your `settings.json` file, then type in the following: 
```json
""
```
