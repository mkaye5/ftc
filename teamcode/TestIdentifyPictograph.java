package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.enums.FTCAlliance;
import org.firstinspires.ftc.enums.FTCPosition;
import org.firstinspires.ftc.libraries.AutonModeLibrary;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by megankaye on 1/10/18.
 */
@Autonomous
public class TestIdentifyPictograph extends LinearOpMode {
    AutonModeLibrary autonMode;

    public void runOpMode() throws InterruptedException {
        autonMode = new AutonModeLibrary(this, FTCAlliance.BLUE, FTCPosition.LEFT);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        int state = 0;
        boolean tapped;
        int taps = 0;
        waitForStart();

        while (opModeIsActive()) {
            switch (state) {
                case 0:
                    RelicRecoveryVuMark vuMark = autonMode.identifyPictograph();
                    //taps algorithm?
                    if (vuMark == RelicRecoveryVuMark.RIGHT) {
                        //go right
                        //CHANGE taps?
                        taps = 3;
                        state += 1;
                    } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                        //go left
                        //CHANGE taps?
                        taps = 1;
                        state += 1;
                    }
                     else if (vuMark == RelicRecoveryVuMark.CENTER) {
                        //go center
                        taps = 2;
                        state += 1;
                    } else {
                        //CHECK how much time has passed????
                        //try?
                        //abandon ship. Put glyph in Center automatically
                        taps = 0;
                    }
                    telemetry.addData("Taps", taps);
                    telemetry.update();
                    sleep(200);
                    break;
                case 1:
                    //DRIVE
                    telemetry.addData("Taps", taps);
                    telemetry.update();
                    //checking sensor.
                    tapped = autonMode.testTouchSensor();
                    if (!tapped) {
                        taps -= 1;
                    }
                    //secondary if statement
                    if (taps <= 0) {
                        state += 1;
                    }
                    //check failsafe
                    break;
                case 2:
                    //turn
                    telemetry.addData("Turning", "Yes!");
                    telemetry.update();
                    //turn for howevermany seconds
                    sleep(3000);
                    state += 1;
                    break;
                case 3:
                    //place glyph
                    telemetry.addData("Placing glyph", "Yes!");
                    telemetry.update();
                    state += 1;
                    break;
                case 4:
                    //GET GLYPHS
                    telemetry.addData("Getting additional glyphs", "Yes!");
                    telemetry.update();
                    state += 1;
                    break;
                default:
                    telemetry.addData("waiting", "Yes!");
                    telemetry.update();
                    //stop all motors
                    break;
            }
        }
    }
}
