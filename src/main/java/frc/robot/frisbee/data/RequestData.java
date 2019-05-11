/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.frisbee.data;

import org.montclairrobotics.cyborg.core.data.CBRequestData;
import org.montclairrobotics.cyborg.core.data.CBStdDriveRequestData;

/**
 * Add your docs here.
 */
public class RequestData extends CBRequestData {
    
    /**
     * Drivetrain
     */
    public CBStdDriveRequestData drivetrain = new CBStdDriveRequestData();

    public boolean launchFrisbee;

}
