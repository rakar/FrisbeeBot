/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.frisbee;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.core.assemblies.CBSmartSpeedControllerArray;
import org.montclairrobotics.cyborg.core.behaviors.CBStdDriveBehavior;
import org.montclairrobotics.cyborg.core.controllers.CBDifferentialDriveController;
import org.montclairrobotics.cyborg.core.data.CBLogicData;
import org.montclairrobotics.cyborg.core.mappers.CBArcadeDriveMapper;
import org.montclairrobotics.cyborg.core.utils.CB2DVector;
import org.montclairrobotics.cyborg.core.utils.CBEnums;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBPDB;
import org.montclairrobotics.cyborg.devices.CBServo;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.devices.CBTalonSRX;

import frc.robot.frisbee.behaviors.FrisbeeBehavior;
import frc.robot.frisbee.controllers.LauncherController;
import frc.robot.frisbee.data.ControlData;
import frc.robot.frisbee.data.RequestData;
import frc.robot.frisbee.mappers.TeleOpMapper;

/**
 * Add your docs here.
 */
public class FrisbeeCB extends Cyborg {

        // joystick ports
        private final int driveStickID = 0;

        // #region Device List...
        public static CBDeviceID
        // modules
        pdbID,

                        // driver controls
                        driveRotAxisID, driveFwdAxisID, launchButtonID,

                        // drivetrain Motors
                        lsMC1ID, lsMC2ID, rsMC3ID, rsMC4ID,

                        // dt Encoders
                        lsEncID, rsEncID,

                        // launch Motors
                        frsMC5ID,

                        // pneumatic splenoids
                        launcherReadySol0ID, launcherLaunchSol1ID,

                        // servos
                        loadServ0ID

        ;
        // #endregion

        public static RequestData requestData;
        public static ControlData controlData;
        public static CBLogicData logicData;

        @Override
        public void cyborgInit() {

                // data init
                requestData = new RequestData();
                controlData = new ControlData();
                logicData = new CBLogicData();

                defineDevices();

                addMappers();
                addControllers();
                addBehaviors();
        }

        private void defineDevices() {
                pdbID = hardwareAdapter.add(new CBPDB());

                driveRotAxisID = hardwareAdapter.add(new CBAxis(driveStickID, 0).setDeadzone(0.1));
                driveFwdAxisID = hardwareAdapter.add(new CBAxis(driveStickID, 0).setDeadzone(0.1));
                launchButtonID = hardwareAdapter.add(new CBButton(driveStickID, 0));

                // drivetrain Motors
                lsMC1ID = hardwareAdapter
                                .add(new CBTalonSRX(1).setControlMode(CBEnums.CBMotorControlMode.PERCENTAGEOUTPUT));
                lsMC2ID = hardwareAdapter
                                .add(new CBTalonSRX(2).setControlMode(CBEnums.CBMotorControlMode.PERCENTAGEOUTPUT));
                rsMC3ID = hardwareAdapter
                                .add(new CBTalonSRX(3).setControlMode(CBEnums.CBMotorControlMode.PERCENTAGEOUTPUT));
                rsMC4ID = hardwareAdapter
                                .add(new CBTalonSRX(4).setControlMode(CBEnums.CBMotorControlMode.PERCENTAGEOUTPUT));

                // dt Encoders
                // lsEnc, rsEnc,

                // launch Motors
                frsMC5ID = hardwareAdapter.add(new CBTalonSRX(5));

                // pneumatic splenoids
                launcherReadySol0ID = hardwareAdapter.add(new CBSolenoid(0));
                launcherLaunchSol1ID = hardwareAdapter.add(new CBSolenoid(1));

                // servos
                loadServ0ID = hardwareAdapter.add(new CBServo(0));

        }

        private void addMappers() {
                this.addTeleOpMapper(new CBArcadeDriveMapper(this, requestData.drivetrain).setAxes(driveFwdAxisID, null,
                                driveRotAxisID));
                this.addTeleOpMapper(new TeleOpMapper(this));
        }

        private void addControllers() {
                this.addRobotController(new CBDifferentialDriveController(this, controlData.drivetrain)
                                .addLeftDriveModule(new CBDriveModule(new CB2DVector(-1, 0), 0)
                                                .addSpeedControllerArray(new CBSmartSpeedControllerArray()
                                                                .addSpeedController(lsMC1ID).addSpeedController(lsMC2ID)))
                                .addRightDriveModule(new CBDriveModule(new CB2DVector(1, 0), 180)
                                                .addSpeedControllerArray(new CBSmartSpeedControllerArray()
                                                                .addSpeedController(rsMC3ID).addSpeedController(rsMC4ID)))

                );
                this.addRobotController(new LauncherController(this));
        }

        private void addBehaviors() {
                this.addBehavior(new CBStdDriveBehavior(this, requestData.drivetrain, controlData.drivetrain));
                this.addBehavior(new FrisbeeBehavior(this));
        }

        @Override
        public void cyborgAutonomousInit() {

        }

        @Override
        public void cyborgDisabledInit() {

        }

        @Override
        public void cyborgTeleopInit() {

        }

        @Override
        public void cyborgTestInit() {

        }
}
