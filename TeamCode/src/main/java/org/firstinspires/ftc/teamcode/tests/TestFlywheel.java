package org.firstinspires.ftc.teamcode.tests;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class TestFlywheel implements Subsystem {
    public static final TestFlywheel INSTANCE = new TestFlywheel();

    private TestFlywheel(){}

    private MotorEx left;
    private MotorEx right;
    private ServoEx flywheel_servo;

    @Override
    public void initialize(){
        //find motors will have to change the names and may have to reverse the direction of one
        left = new MotorEx("left_flywheel").brakeMode();
        right = new MotorEx("right_flywheel").brakeMode().reversed();
        flywheel_servo = new ServoEx("mover");
    }

    //have to figure out the pos needed
    public Command setPosMid = new SetPosition(flywheel_servo,.5);

    public Command shoot = new ParallelGroup(
           new SetPower(left,1),
           new SetPower(right, 1)
    );
}
