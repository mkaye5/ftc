package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by megankaye on 1/4/18.
 */

@Autonomous
public class AutonRedLeft extends LinearOpMode {
    AutonMode autonMode;

    public void runOpMode() throws InterruptedException {
        autonMode = new AutonMode(this, FTCAlliance.RED, FTCPosition.LEFT);

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
