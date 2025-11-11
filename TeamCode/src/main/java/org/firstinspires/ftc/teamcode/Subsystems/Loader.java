package org.firstinspires.ftc.teamcode.Subsystems;


import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class Loader implements Subsystem {
    public static final Loader INSTANCE = new Loader();


    private CRServoEx roller;
    private ServoEx pusher;

    @Override
    public void initialize() {

        roller = new CRServoEx("wheel");
        pusher = new ServoEx("feedSweep");

    }


    //Positions will vary based on things like
    //pushes the ball into the flywheel
    public Command push = new SequentialGroup(
            //

            //turns the roller to push
            new SetPower(roller,-1),
            //the pusher, pushes the ball into position if not already loaded
            new SetPosition(pusher,.9)
    );

    //resets the pusher to rest
    public Command reset = new ParallelGroup(
            new SetPower(roller,0),
            new SetPosition(pusher, 0)
    );
}
