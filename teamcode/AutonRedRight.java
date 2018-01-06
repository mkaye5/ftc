package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by megankaye on 1/5/18.
 */

@Autonomous
public class AutonRedRight extends LinearOpMode {
    AutonMode autonMode;

    public void runOpMode() throws InterruptedException {
        autonMode = new AutonMode(this, FTCAlliance.RED, FTCPosition.RIGHT);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            Direction dir = autonMode.knockOffJewel();
            autonMode.driveToSafeZone(dir);
            sleep(30000);
        }
    }
}
