package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.bindings.Range;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;


public class Lift implements Subsystem {
    public static final Lift INSTANCE = new Lift();
    private Lift(){}

    private MotorEx lift_motor;

    private ControlSystem controlSystem = ControlSystem.builder()
            .posPid(0.005, 0, 0)
            .elevatorFF(0)
            .build();

    @Override
    public void initialize(){
        lift_motor = new MotorEx("lift_motor");
    }


    //should make it so that the control makes it so it stays elevated
    // unit is ticks
    public Command lift = new RunToPosition(controlSystem, 1000).requires(this);

    @Override
    public void periodic(){
        lift_motor.setPower(
                controlSystem.calculate(
                        new KineticState(lift_motor.getCurrentPosition(), lift_motor.getVelocity(), lift_motor.getState().getAcceleration())
                )
        );
    }
}
