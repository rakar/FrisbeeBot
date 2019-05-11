/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.frisbee.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.behaviors.CBBehavior;

import static frc.robot.frisbee.FrisbeeCB.*;

/**
 * Add your docs here.
 */
public class FrisbeeBehavior extends CBBehavior {
    
    public FrisbeeBehavior(Cyborg robot) {
        super(robot);
    }

    public void init() {
    }
    
    public void update() {
        controlData.launch = requestData.launchFrisbee;
    }

}
