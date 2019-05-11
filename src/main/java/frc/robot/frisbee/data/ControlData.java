/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.frisbee.data;

import org.montclairrobotics.cyborg.core.data.CBControlData;
import org.montclairrobotics.cyborg.core.data.CBStdDriveControlData;

/**
 * Add your docs here.
 */
public class ControlData extends CBControlData {

    /**
     * Initialize the driveData field with standard drive control data.
     * 
     */
    public CBStdDriveControlData drivetrain = new CBStdDriveControlData();

    public boolean launch;

}
