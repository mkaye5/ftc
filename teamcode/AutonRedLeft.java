package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by megankaye on 1/4/18.
 */

public class AutonRedLeft extends LinearOpMode {
    DrivingLibrary drivingLibrary;
    AutonMode autonMode;

    public void runOpMode() throws InterruptedException {
        autonMode = new AutonMode(this, FTCAlliance.RED, FTCPosition.LEFT);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            autonMode.knockOffJewel();
            sleep(30000);
        }
    }
}
