package org.firstinspires.ftc.teamcode.tests;
import com.rowanmcalpin.nextftc.core.Subsystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class TestIntake extends Subsystem {

    public static final TestIntake INSTANCE = new TestIntake();
    public MotorEx motor;

    public String name = "intake_servo";

    private TestIntake() { }

    public Command spin(){
      return new SetPower(motor,1);

    }


    @Override
    public void initialize() {
        //may have to reverse depending on the robot
        motor = new MotorEx("intake_motor").brakeMode();
        //tells what motor from hardwaremap
    }
}
