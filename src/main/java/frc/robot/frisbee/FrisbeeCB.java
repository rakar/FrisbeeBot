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
        pdb,

                        // driver controls
                        driveRotAxis, driveFwdAxis, launchButton,

                        // drivetrain Motors
                        lsMC1, lsMC2, rsMC3, rsMC4,

                        // dt Encoders
                        lsEnc, rsEnc,

                        // launch Motors
                        frsMC5,

                        // pneumatic splenoids
                        launcherReadySol0, launcherLaunchSol1,

                        // servos
                        loadServ0

        ;
        // #endregion

        public static RequestData requestData;
        public static ControlData controlData;

        @Override
        public void cyborgInit() {

                // data init
                requestData = new RequestData();
                controlData = new ControlData();

                defineDevices();
                defineMappers();
                defineControllers();
                defineBehaviors();
        }

        private void defineDevices() {
                pdb = hardwareAdapter.add(new CBPDB());

                driveRotAxis = hardwareAdapter.add(new CBAxis(driveStickID, 0).setDeadzone(0.1));
                driveFwdAxis = hardwareAdapter.add(new CBAxis(driveStickID, 0).setDeadzone(0.1));
                launchButton = hardwareAdapter.add(new CBButton(driveStickID, 0));

                // drivetrain Motors
                lsMC1 = hardwareAdapter
                                .add(new CBTalonSRX(1).setControlMode(CBEnums.CBMotorControlMode.PERCENTAGEOUTPUT));
                lsMC2 = hardwareAdapter
                                .add(new CBTalonSRX(2).setControlMode(CBEnums.CBMotorControlMode.PERCENTAGEOUTPUT));
                rsMC3 = hardwareAdapter
                                .add(new CBTalonSRX(3).setControlMode(CBEnums.CBMotorControlMode.PERCENTAGEOUTPUT));
                rsMC4 = hardwareAdapter
                                .add(new CBTalonSRX(4).setControlMode(CBEnums.CBMotorControlMode.PERCENTAGEOUTPUT));

                // dt Encoders
                // lsEnc, rsEnc,

                // launch Motors
                frsMC5 = hardwareAdapter.add(new CBTalonSRX(5));

                // pneumatic splenoids
                launcherReadySol0 = hardwareAdapter.add(new CBSolenoid(0));
                launcherLaunchSol1 = hardwareAdapter.add(new CBSolenoid(1));

                // servos
                loadServ0 = hardwareAdapter.add(new CBServo(0));

        }

        private void defineMappers() {
                this.addTeleOpMapper(new CBArcadeDriveMapper(this, requestData.drivetrain).setAxes(driveFwdAxis, null,
                                driveRotAxis));
                this.addTeleOpMapper(new TeleOpMapper(this));
        }

        private void defineControllers() {
                this.addRobotController(new CBDifferentialDriveController(this, controlData.drivetrain)
                                .addLeftDriveModule(new CBDriveModule(new CB2DVector(-1, 0), 0)
                                                .addSpeedControllerArray(new CBSmartSpeedControllerArray()
                                                                .addSpeedController(lsMC1).addSpeedController(lsMC2)))
                                .addRightDriveModule(new CBDriveModule(new CB2DVector(1, 0), 180)
                                                .addSpeedControllerArray(new CBSmartSpeedControllerArray()
                                                                .addSpeedController(rsMC3).addSpeedController(rsMC4)))

                );
                this.addRobotController(new LauncherController(this));
        }

        private void defineBehaviors() {
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
