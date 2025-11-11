package org.firstinspires.ftc.teamcode.pathing;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Loader;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.time.Duration;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.CommandGroup;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

/*
 *
 * START OP MODE RIGHT HALF (RED SIDE)
 * ON BOTTOM
 * ON TAPE
 * FACING TOWARDS OPPOSITE WALL
 *
 * */
@Autonomous(name = "TestAuto", group = "Examples")
public class autoOp extends NextFTCOpMode {

    public autoOp() {
        addComponents(new SubsystemComponent(Intake.INSTANCE, Flywheel.INSTANCE, Loader.INSTANCE, Lift.INSTANCE), BulkReadComponent.INSTANCE, BindingsComponent.INSTANCE);
    }

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private Intake intake;
    private Flywheel flywheel;
    private Lift lift;
    private int pathState;

    private final Pose startRedClose = new Pose(118, 118, Math.toRadians(225));//right up against goal, facing away
    private final Pose startRedFar = new Pose(84, 12, Math.toRadians(90)); //on line, facing forward, middle of square
    private final Pose shootPoseFarRed = new Pose(84, 84, Math.toRadians(45)); //CHANGE ONCE VELOCITY DEFINED
    private final Pose shootPoseCloseRed = new Pose(84, 84, Math.toRadians(45)); //CHANGE ONCE VELOCITY DEFINED

    //object poses top to bottom
    private final Pose objects1 = new Pose(96, 84, Math.toRadians(0));
    private final Pose endpickup1 = new Pose(115, 84, Math.toRadians(0));
    private final Pose objects2 = new Pose(96, 60, Math.toRadians(0));
    private final Pose endpickup2 = new Pose(115, 60, Math.toRadians(0));
    private final Pose objects3 = new Pose(96, 36, Math.toRadians(0));
    private final Pose endpickup3 = new Pose(115, 36, Math.toRadians(0));

    private PathChain moveToShoot, moveToObjects1, pickupObjects1, moveToShoot1, moveToObjects2, pickupObjects2, moveToShoot2, moveToObjects3, pickupObjects3, moveToShoot3;


    public void buildPaths() {

        moveToShoot = follower.pathBuilder().addPath(new BezierLine(startRedFar, shootPoseFarRed)).setLinearHeadingInterpolation(startRedFar.getHeading(), shootPoseFarRed.getHeading()).build();

        moveToObjects1 = follower.pathBuilder().addPath(new BezierLine(shootPoseFarRed, objects1)).setLinearHeadingInterpolation(shootPoseFarRed.getHeading(), objects1.getHeading()).build();

        pickupObjects1 = follower.pathBuilder().addPath(new BezierLine(objects1, endpickup1)).setConstantHeadingInterpolation(Math.toRadians(0)).build();

        moveToShoot1 = follower.pathBuilder().addPath(new BezierLine(endpickup1, shootPoseFarRed)).setLinearHeadingInterpolation(endpickup1.getHeading(), shootPoseFarRed.getHeading()).build();

        moveToObjects2 = follower.pathBuilder().addPath(new BezierLine(shootPoseFarRed, objects2)).setLinearHeadingInterpolation(shootPoseFarRed.getHeading(), objects2.getHeading()).build();

        pickupObjects2 = follower.pathBuilder().addPath(new BezierLine(objects2, endpickup2)).setConstantHeadingInterpolation(Math.toRadians(0)).build();

        moveToShoot2 = follower.pathBuilder().addPath(new BezierLine(endpickup2, shootPoseFarRed)).setLinearHeadingInterpolation(endpickup2.getHeading(), shootPoseFarRed.getHeading()).build();

        moveToObjects3 = follower.pathBuilder().addPath(new BezierLine(shootPoseFarRed, objects3)).setLinearHeadingInterpolation(shootPoseFarRed.getHeading(), objects3.getHeading()).build();

        pickupObjects3 = follower.pathBuilder().addPath(new BezierLine(objects3, endpickup3)).setConstantHeadingInterpolation(Math.toRadians(0)).build();

        moveToShoot3 = follower.pathBuilder().addPath(new BezierLine(endpickup3, shootPoseFarRed)).setLinearHeadingInterpolation(endpickup3.getHeading(), shootPoseFarRed.getHeading()).build();

    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                //pause
                //flywheel shoot
                new Delay(1);

                //for shooting 2 balls
                new SequentialGroup(
                        //flywheel shoot,
                        Flywheel.INSTANCE.shoot,
                        //stop the flys
                        Flywheel.INSTANCE.stop,
                        //load next ball
                        Loader.INSTANCE.push,
                        //reset the loader
                        Loader.INSTANCE.reset,
                        //Shoot second preload
                        Flywheel.INSTANCE.shoot, Flywheel.INSTANCE.stop);
                follower.followPath(moveToShoot, true);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    //start intake
                    Intake.INSTANCE.spin();
                    follower.followPath(moveToObjects1, true);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    //stop intake
                    Intake.INSTANCE.stop();
                    follower.followPath(pickupObjects1, true);
                    setPathState(3);
                }
            case 3:
                if (!follower.isBusy()) {
                    //pause
                    new Delay(3);
                    //flywheel shoot
                    new SequentialGroup(
                            //flywheel shoot,
                            Flywheel.INSTANCE.shoot,
                            //stop the flys
                            Flywheel.INSTANCE.stop,
                            //load next ball
                            Loader.INSTANCE.push,
                            //reset the loader
                            Loader.INSTANCE.reset,
                            //Shoot second preload
                            Flywheel.INSTANCE.shoot, Flywheel.INSTANCE.stop);
                    follower.followPath(moveToShoot1, true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    Intake.INSTANCE.spin();
                    //start intake
                    follower.followPath(moveToObjects2, true);

                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(pickupObjects2, true);
                    //stop intake
                    setPathState(6);
                    Intake.INSTANCE.stop();
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    //pause
                    new Delay(.5);
                    //flywheel shoot
                    new SequentialGroup(
                            //flywheel shoot,
                            Flywheel.INSTANCE.shoot,
                            //stop the flys
                            Flywheel.INSTANCE.stop,
                            //load next ball
                            Loader.INSTANCE.push,
                            //reset the loader
                            Loader.INSTANCE.reset,
                            //Shoot second preload
                            Flywheel.INSTANCE.shoot,
                            Flywheel.INSTANCE.stop
                    );
                    follower.followPath(moveToShoot2, true);
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    //start intake
                    Intake.INSTANCE.spin();
                    follower.followPath(moveToObjects3, true);
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()) {
                    follower.followPath(pickupObjects3, true);
                    //stop intake
                    Intake.INSTANCE.stop();
                    setPathState(9);
                }
                break;
            case 9:
                if (!follower.isBusy()) {
                    //pause
                    new Delay(.5);
                    //flywheel shoot
                    new SequentialGroup(
                            //flywheel shoot,
                            Flywheel.INSTANCE.shoot,
                            //stop the flys
                            Flywheel.INSTANCE.stop,
                            //load next ball
                            Loader.INSTANCE.push,
                            //reset the loader
                            Loader.INSTANCE.reset,
                            //Shoot second preload
                            Flywheel.INSTANCE.shoot,
                            Flywheel.INSTANCE.stop
                    );
                    follower.followPath(moveToShoot3, true);
                    setPathState(10);
                }
                break;
        }
    }

    public Pose mirrorPose(Pose input) {
        double inputX = input.getX();
        double inputY = input.getY();
        double inputHeading = input.getHeading();

        return new Pose(inputX - 72, inputY, 180 - inputHeading);
    }

    public void setPathState(int set) {
        pathState = set;
        pathTimer.resetTimer();
    }

    /**
     * This is the main loop of the OpMode, it will run repeatedly after clicking "Play".
     **/
    @Override
    public void onUpdate() {

        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /**
     * This method is called once at the init of the OpMode.
     **/
    @Override
    public void onInit() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();


        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startRedFar);

    }

    /** This method is called continuously after Init while waiting for "play". **/
  /*  @Override
    public void periodic() {

    }
*/

    /**
     * This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system
     **/
    @Override
    public void onStartButtonPressed() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /**
     * We do not use this because everything should automatically disable
     **/
    @Override
    public void onStop() {
    }


}
