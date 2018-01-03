package org.firstinspires.ftc.teamcode;

/**
 * Created by megankaye on 12/13/17.
 */

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.whatever.DrivingLib;

@Disabled
@Autonomous

public class AutonTest extends LinearOpMode {
    //dlib
    private DrivingLib dlib;

    //pully motor

    //claw servo motor 1
    //claw servo motor 2

    //jewel servo motor
    private Servo jewelServo;

    //color sensor
    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;


    @Override
    public void runOpMode() {
        dlib = new DrivingLib(this, true, true);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // get a reference to the color sensor.
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "colorSensor");
        jewelServo = hardwareMap.get(Servo.class, "jewelServo");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // wait for the start button to be pressed.
        waitForStart();

        while (opModeIsActive()) {
            //MOVE SERVO
            jewelServo.setPosition(1);

            //SENSE COLOR
            Color.RGBToHSV((int) (colorSensor.red() * SCALE_FACTOR),
                    (int) (colorSensor.green() * SCALE_FACTOR),
                    (int) (colorSensor.blue() * SCALE_FACTOR),
                    hsvValues);
            telemetry.addData("Alpha", colorSensor.alpha());
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Blue ", colorSensor.blue());
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });
            sleep(10000);
            //assuming we're blue
            if (colorSensor.red() > 50) {
                //drive forward
                dlib.drivGay(30,-.1, 0);
            } else {
                //drive backwards
                dlib.drivGay(30,.1, 0);
            }

            // send the info back to driver station using telemetry function.

            sleep(10000);

            //DRIVE TO SAFE MODE

            //SLEEP
            telemetry.update();
        }

        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
}
