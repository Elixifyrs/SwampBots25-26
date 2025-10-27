package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.bindings.Range;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;


public class Lift implements Subsystem {
    public static final Lift INSTANCE = new Lift();
    private Lift(){}

    private MotorEx lift_motor;

    //moves the lift up or down depending on the joystick var which i need to check if it works
    //otherwise i will make it just a set power and on/off w gamepad 2
    public Command lift(double targetPos){
        lift_motor.setPower(1);


        //to sustain position
        lift_motor.atPosition(targetPos);
        while (lift_motor.getCurrentPosition() < targetPos){
        lift_motor.setPower(1);
        }
        if (lift_motor.getCurrentPosition() >= targetPos){
            lift_motor.setPower(.3);
        }
        return null;
    }

    @Override
    public void initialize(){
        lift_motor = new MotorEx("lift_motor");
    }

}
