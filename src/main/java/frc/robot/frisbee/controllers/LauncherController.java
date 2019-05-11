/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.frisbee.controllers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.controllers.CBRobotController;
import org.montclairrobotics.cyborg.core.utils.CBStateMachine;
import org.montclairrobotics.cyborg.devices.CBServo;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.devices.CBTalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.frisbee.FrisbeeCB.*;

/**
 * Add your docs here.
 */
public class LauncherController extends CBRobotController {
    CBServo servo;
    CBSolenoid ready;
    CBSolenoid launch;
    CBTalonSRX spinner;

    enum LauncherStates {
        Start, Idle, Release, Launch
    };

    private class LauncherStateMachine extends CBStateMachine<LauncherStates> {
    
        public LauncherStateMachine() {
            super(LauncherStates.Start);
            setLoopMode(CBStateMachineLoopMode.OneShot);
        }

        @Override
        public void calcNextState() {
            // SmartDashboard.putString("calc Next State:", currentState.name());

            switch (currentState) {
            case Start:
                nextState = LauncherStates.Idle;
                break;
            case Idle:
                if (controlData.launch)
                    nextState = LauncherStates.Release;
                break;
            case Release:
                if (this.cyclesInState > 100)
                    nextState = LauncherStates.Launch;
                break;
            case Launch:
                if (this.cyclesInState > 100) {
                    if (controlData.launch)
                        nextState = LauncherStates.Release;
                    else
                        nextState = LauncherStates.Idle;
                }
                break;
            }
        }

        @Override
        public void doTransition() {
            if (isTransitionTo(LauncherStates.Idle)) {
                servo.set(0);
                launch.set(false);
                ready.set(true);
                spinner.set(0);
            }
            if (isTransitionTo(LauncherStates.Release)) {
                servo.set(1);
                spinner.set(1);
            }
            if (isTransitionTo(LauncherStates.Launch)) {
                servo.set(0);
                launch.set(true);
                ready.set(false);
            }
        }

        @Override
        protected void doCurrentState() {
            SmartDashboard.putString("Launcher State:", currentState.name());
        }
    }

    LauncherStateMachine sm;

    public LauncherController(Cyborg robot) {
        super(robot);
    }

    public void init() {
        servo = hardwareAdapter.getServo(loadServ0ID);
        ready = hardwareAdapter.getSolenoidValve(launcherReadySol0ID);
        launch = hardwareAdapter.getSolenoidValve(launcherLaunchSol1ID);
        spinner = hardwareAdapter.getTalonSRX(frsMC5ID);
        sm = new LauncherStateMachine();
    }

    public void update() {
        sm.update();
    }

    public void configHardware() {
    }

}
