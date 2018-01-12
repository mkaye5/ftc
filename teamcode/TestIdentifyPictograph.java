package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.enums.FTCAlliance;
import org.firstinspires.ftc.enums.FTCPosition;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by megankaye on 1/10/18.
 */
@Autonomous
public class TestIdentifyPictograph extends LinearOpMode {
    AutonMode autonMode;

    public void runOpMode() throws InterruptedException {
        autonMode = new AutonMode(this, FTCAlliance.BLUE, FTCPosition.LEFT);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        boolean started = false;
        int taps = 0;
        waitForStart();

        while (opModeIsActive()) {
            if (!started) {
                RelicRecoveryVuMark vuMark = autonMode.identifyPictograph();

                //taps algorithm?
                if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    //go right
                    //CHANGE taps?
                    taps = 3;
                } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                    //go left
                    //CHANGE taps?
                    taps = 1;
                }
                if (vuMark == RelicRecoveryVuMark.CENTER) {
                    //go center
                    taps = 2;
                } else {
                    //abandon ship. Put glyph in Center automatically
                    taps = 2;
                }
                started = true;
            } else {
                //OUTWARD if statement checking if sensor.

                //secondary if statement
                if (taps == 0) {
                    //turn
                }

                //check failsafe
            }
        }
    }
}
