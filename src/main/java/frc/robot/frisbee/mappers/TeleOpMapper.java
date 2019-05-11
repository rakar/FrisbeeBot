/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.frisbee.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.mappers.CBTeleOpMapper;
import org.montclairrobotics.cyborg.devices.CBButton;

import frc.robot.frisbee.FrisbeeCB;

/**
 * Add your docs here.
 */
public class TeleOpMapper extends CBTeleOpMapper {

    CBButton launchButton;

    public TeleOpMapper(Cyborg robot) {
        super(robot);
    }

    @Override
    public void init() {
        launchButton = FrisbeeCB.hardwareAdapter.getButton(FrisbeeCB.launchButton);
    }

    @Override
    public void update() {
        FrisbeeCB.requestData.launchFrisbee = launchButton.getState(); 
    }
}
