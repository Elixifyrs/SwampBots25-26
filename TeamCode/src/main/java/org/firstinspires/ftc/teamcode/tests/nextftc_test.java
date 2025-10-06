package org.firstinspires.ftc.teamcode.tests;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.*;
import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.driving.DriverControlledCommand;
import dev.nextftc.hardware.driving.FieldCentric;
import dev.nextftc.hardware.driving.HolonomicDrivePowers;
import dev.nextftc.hardware.driving.HolonomicMode;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp
public class nextftc_test extends NextFTCOpMode {



    DriverControlledCommand driverControlled;

    //Left motors r reversed, may be different tho on robot tho

    private MotorEx frontLeft = new MotorEx("front_left").brakeMode().reversed();
    private MotorEx frontRight = new MotorEx("front_right").brakeMode();
    private MotorEx backLeft = new MotorEx("back_left").brakeMode().reversed();
    private MotorEx backRight = new MotorEx("back_right").brakeMode();




    //gyroscope
    private IMUEx imu = new IMUEx("imu", Direction.UP, Direction.FORWARD).zeroed();

    @Override
    public void onInit(){
        telemetry.addData("Status", "Initialized");
    }
    @Override
    public void onStartButtonPressed(){
        telemetry.addData("Status", "Started");
        telemetry.update();
        DriverControlledCommand driverControlled = new MecanumDriverControlled(frontLeft,
                frontRight,
                backLeft,
                backRight,
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX(),
                new FieldCentric(imu)
        );


        driverControlled.schedule();
    }





}



// DEPENCIES FOR NEXTFTC 0.6
/*
    implementation 'com.rowanmcalpin.nextftc:core:0.6.2'
    implementation 'com.rowanmcalpin.nextftc:ftc:0.6.2'
    implementation 'com.rowanmcalpin.nextftc:pedro:0.6.2' // Remove if you don't intend to use PedroPathing
 */