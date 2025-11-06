package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Loader;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp (name = "test shooter")
public class TestShooter extends NextFTCOpMode {

    public TestShooter(){
        addComponents(
                new SubsystemComponent(Intake.INSTANCE, Flywheel.INSTANCE, Loader.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }


    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().rightTrigger().greaterThan(.2).whenBecomesTrue(Flywheel.INSTANCE.shoot);
        Gamepads.gamepad1().a().whenBecomesTrue(Loader.INSTANCE.push);
    }
}
