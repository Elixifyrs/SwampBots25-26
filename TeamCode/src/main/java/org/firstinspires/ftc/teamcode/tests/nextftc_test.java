package org.firstinspires.ftc.teamcode.tests;

import static com.rowanmcalpin.nextftc.ftc.OpModeData.telemetry;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

import org.apache.commons.math3.stat.descriptive.moment.SemiVariance;

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

@TeleOp
public class nextftc_test extends NextFTCOpMode {

    DriverControlledCommand driverControlled;
    private  MotorEx frontLeftMotor = new MotorEx("front_left").reverse();
    private  MotorEx frontRightMotor = new MotorEx("front_right");
    private  MotorEx backLeftMotor = new MotorEx("back_left").reverse();
    private  MotorEx backRightMotor = new MotorEx("back_right");
    private IMUEx imu = new IMUEx("imu", Direction.UP, Direction.FORWARD).zeroed();
    private HolonomicMode mode;

    @Override
    public void onInit(){
        telemetry.addData("Status", "Initialized");

        frontLeftMotor.setPower(DcMotor.ZeroPowerBehavior.BRAKE.ordinal());
        frontRightMotor.setPower(DcMotor.ZeroPowerBehavior.BRAKE.ordinal());
        backLeftMotor.setPower(DcMotor.ZeroPowerBehavior.BRAKE.ordinal());
        backRightMotor.setPower(DcMotor.ZeroPowerBehavior.BRAKE.ordinal());




    }

    @Override
    public void onStartButtonPressed(){
        DriverControlledCommand driverControlled = new MecanumDriverControlled(frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX(),
                new FieldCentric(imu)


        );
        driverControlled.schedule();
    }





}
