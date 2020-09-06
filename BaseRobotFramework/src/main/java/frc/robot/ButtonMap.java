/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Add your docs here.
 */
public class ButtonMap {
    public static OI oi = new OI();
    //driver controller buttons
    public static double getDriveThrottle(){
        return -oi.driverController.getRawAxis(1);
    } 
    public static double getRotation(){
        return -oi.driverController.getRawAxis(4);
    }
    public static boolean switchCamera(){
        return oi.driverController.getTriggerAxis(Hand.kLeft)>=0.15;
    }
    public static boolean manualTarget(){
        return oi.driverController.getBumper(Hand.kLeft);
    }
    public static boolean autoTarget(){
        return oi.driverController.getBumperPressed(Hand.kRight);
    }
    public static boolean endAutoTarget(){
        return oi.driverController.getBumperReleased(Hand.kRight);
    }
    public static boolean manualAdjustLeft(){
        return oi.driverController.getXButton();
    }
    public static boolean manualAdjustRight(){
        return oi.driverController.getBButton();
    }
    public static boolean turnOnLightRing(){
        return oi.driverController.getStartButton();
    }
    //operator controller

    //autochooser
  

}
