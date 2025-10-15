package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

@TeleOp(name = "robot Centric")
public class Robocentric_tele extends NextFTCOpMode {
    public Robocentric_tele() {
        addComponents(
                new SubsystemComponent(),
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
        //robot centric
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
    }

}