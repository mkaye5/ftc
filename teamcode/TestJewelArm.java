package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by megankaye on 1/10/18.
 */
@Autonomous
public class TestJewelArm extends LinearOpMode {
    AutonMode autonMode;
    public void runOpMode() throws InterruptedException {
        autonMode = new AutonMode(this, FTCAlliance.BLUE, FTCPosition.LEFT);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            Direction dir = autonMode.knockOffJewel();
            sleep(3000);
        }
    }
}
