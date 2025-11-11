package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.bindings.Range;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Flywheel implements Subsystem {
    public static final Flywheel INSTANCE = new Flywheel();

    private Flywheel(){}

    private MotorEx left;
    private MotorEx right;

    private ServoEx pusher;

    //gotta figure out what tis does first
    //i believe its the "tuning" for the velocity for the motor
    ControlSystem controller = ControlSystem.builder()
            .velPid(12,3,3)
            .basicFF(0.0005)
            .build();

//    MotorGroup man = new MotorGroup(left,right);

    ControlSystem pusher_control = ControlSystem.builder()
            .velPid(0.011,0,0)
            .basicFF(0.0005)
            .build();

    @Override
    public void initialize(){
        //find motors will have to change the names and may have to reverse the direction of one
        left = new MotorEx("leftFly").brakeMode();
        right = new MotorEx("rightFly").brakeMode().reversed();
        pusher = new ServoEx("hammer");

    }

    //the number is ticks or Velocity in (ticks/s)  28 tikcs per rev

    //1800 top triangle
    //2000 far triangle
    public Command shoot_short= new SequentialGroup(

            //look at blocks to tune the servos
            new RunToVelocity(controller,1800).requires(this),
            new SetPosition(pusher,.9),
            new Delay(.5),
            new SetPosition(pusher, 0)

    );

    public Command shoot_long = new SequentialGroup(
            new RunToVelocity(controller,2000).requires(this),
            new SetPosition(pusher,.9),
            new Delay(.5),
            new SetPosition(pusher, 0)
    );

    public Command stop = new RunToVelocity(controller, 0).requires(this);


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
