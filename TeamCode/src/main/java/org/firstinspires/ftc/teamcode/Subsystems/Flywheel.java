package org.firstinspires.ftc.teamcode.Subsystems;

import static dev.nextftc.bindings.Bindings.variable;

import dev.nextftc.bindings.Range;
import dev.nextftc.bindings.Variable;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class Flywheel implements Subsystem {
    public static final Flywheel INSTANCE = new Flywheel();

    private Flywheel(){}

    private MotorEx left;
    private MotorEx right;
    private ServoEx flywheel_servo;

    Range leftStickY;

    @Override
    public void initialize(){
        //find motors will have to change the names and may have to reverse the direction of one
        left = new MotorEx("left_flywheel").brakeMode();
        right = new MotorEx("right_flywheel").brakeMode().reversed();
        flywheel_servo = new ServoEx("mover");
        leftStickY = Gamepads.gamepad2().leftStickY();
    }

    //have to figure out the pos needed
    public Command setPosMid = new SetPosition(flywheel_servo,.5);

    //have to figure out the power @ certain pos when pedro is done
    public Command shoot = new ParallelGroup(
           new SetPower(left,1),
           new SetPower(right, 1)
    );
}
