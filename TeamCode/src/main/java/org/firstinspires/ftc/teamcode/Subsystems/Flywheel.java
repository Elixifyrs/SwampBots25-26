package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.bindings.Range;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Flywheel implements Subsystem {
    public static final Flywheel INSTANCE = new Flywheel();

    private Flywheel(){}

    private MotorEx left;
    private MotorEx right;

    //gotta figure out what tis does first
    //i believe its the "tuning" for the velocity for the motor
    ControlSystem controller = ControlSystem.builder()
            .velPid(0.011,0,0)
            .basicFF(0.0005)
            .build();

//    MotorGroup man = new MotorGroup(left,right);

    @Override
    public void initialize(){
        //find motors will have to change the names and may have to reverse the direction of one
        left = new MotorEx("left_flywheel").brakeMode();
        right = new MotorEx("right_flywheel").brakeMode().reversed();
    }

    //the number is ticks or Velocity in (ticks/s)  28 tikcs per rev
    public Command shoot = new RunToVelocity(controller,1700).requires(this);


    @Override
    public void periodic(){
        left.setPower(
                controller.calculate(
                        new KineticState(left.getCurrentPosition(),left.getVelocity(),left.getState().getAcceleration())
                )
        );
        right.setPower(
                controller.calculate(
                        new KineticState(right.getCurrentPosition(),right.getVelocity(),right.getState().getAcceleration())
                )
        );
    }
}
