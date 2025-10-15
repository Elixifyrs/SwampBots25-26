package org.firstinspires.ftc.teamcode.Subsystems;
import dev.nextftc.core.commands.Command;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;
import dev.nextftc.core.subsystems.Subsystem;
public class Intake implements Subsystem {

    public static final Intake INSTANCE = new Intake();
    public MotorEx motor;

    public String name = "intake_servo";

    private Intake() { }

    public Command spin(){
      //set power = spin
      return new SetPower(motor,1);

    }
    @Override
    public void initialize() {
        //may have to reverse depending on the robot
        //tells what motor from hardwaremap will have to change to what it says on driver hub

        motor = new MotorEx("intake_motor").brakeMode();
    }
}
