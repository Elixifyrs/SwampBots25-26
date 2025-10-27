package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Loader;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.FieldCentric;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp (name = "field centric")
public class field_centric_tele extends NextFTCOpMode {
    public field_centric_tele() {
        addComponents(
                new SubsystemComponent(Intake.INSTANCE, Flywheel.INSTANCE, Loader.INSTANCE, Lift.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    // change the names and directions to suit your robot
    private final MotorEx frontLeftMotor = new MotorEx("leftFront").reversed();
    private final MotorEx frontRightMotor = new MotorEx("rightFront");
    private final MotorEx backLeftMotor = new MotorEx("leftBack").reversed();
    private final MotorEx backRightMotor = new MotorEx("rightBack");
    private IMUEx imu = new IMUEx("imu", Direction.BACKWARD, Direction.UP).zeroed();


    @Override
    public void onStartButtonPressed() {
        //field centric
        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX(),
                new FieldCentric(imu)
        );
        driverControlled.schedule();

        if(gamepad1.dpad_up){
            imu.zeroed();
        }
        //spins the intake system
        Gamepads.gamepad1().leftTrigger().greaterThan(.2).whenBecomesTrue(Intake.INSTANCE.spin());

        //spins the flywheels to shoot the ball
        Gamepads.gamepad1().rightTrigger().greaterThan(.2).whenBecomesTrue(Flywheel.INSTANCE.shoot);

        //set the flywheel/outtake pos
        Gamepads.gamepad1().rightBumper().whenBecomesTrue(Loader.INSTANCE.push).whenBecomesFalse(Loader.INSTANCE.reset);


    }
}