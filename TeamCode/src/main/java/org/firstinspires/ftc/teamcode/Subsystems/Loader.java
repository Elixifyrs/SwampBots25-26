package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Loader implements Subsystem {
    public static final Loader INSTANCE = new Loader();


    private ServoEx pusher;

    @Override
    public void initialize() {
        pusher = new ServoEx("loader_servo");
    }


    //Positions will vary based on things like
    //pushes the ball into the flywheel
    public Command push = new SetPosition(pusher,1);

    //resets the pusher to rest
    public Command reset = new SetPosition(pusher,0);
}
