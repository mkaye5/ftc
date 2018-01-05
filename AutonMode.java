package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by megankaye on 1/3/18.
 */

public class AutonMode {
    DrivingLibrary drivingLibrary;
    Servo colorArm;
    FTCAlliance alliance;
    FTCPosition position;
    LinearOpMode opMode;
    ColorSensor colorSensor;
    DistanceSensor distanceSensor;

    public AutonMode(LinearOpMode opMode, FTCAlliance alliance, FTCPosition position) {
        this.alliance = alliance;
        this.position = position;
        this.drivingLibrary = new DrivingLibrary(opMode, FTCAlliance.RED, FTCPosition.LEFT);
        this.drivingLibrary.setSpeed(0.75);
        this.colorArm = opMode.hardwareMap.get(Servo.class,"color_arm");
        colorSensor = opMode.hardwareMap.get(ColorSensor.class, "color_sensor");
        distanceSensor = opMode.hardwareMap.get(DistanceSensor.class, "color_sensor");
        this.opMode = opMode;
    }

    public void knockOffJewel() {
        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = opMode.hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", this.opMode.hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) this.opMode.hardwareMap.appContext).findViewById(relativeLayoutId);

        //MOVE SERVO
        colorArm.setPosition(0.1);
        opMode.sleep(2000);

        //SENSE COLOR
        Color.RGBToHSV((int) (colorSensor.red() * SCALE_FACTOR),
                (int) (colorSensor.green() * SCALE_FACTOR),
                (int) (colorSensor.blue() * SCALE_FACTOR),
                hsvValues);
        opMode.telemetry.addData("Red  ", colorSensor.red());
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });
        opMode.telemetry.update();
        opMode.sleep(3000);
        if (alliance == FTCAlliance.RED) {
            if (colorSensor.red() >= 25) {
                drivingLibrary.driveStraight(0, -.4f);
            }
            else {
                drivingLibrary.driveStraight(0, .4f);
            }
        } else {
            if (colorSensor.red() >= 25) {
                drivingLibrary.driveStraight(0, .4f);

            }
            else {
                drivingLibrary.driveStraight(0, -.4f);
            }
        }
        opMode.sleep(500);
        drivingLibrary.stopDrivingMotors();
        colorArm.setPosition(1);
        opMode.telemetry.update();
    }

    public void driveToSafeZone() {
        if (position == FTCPosition.LEFT) {
            if (alliance == FTCAlliance.RED) {

            } else {

            }

        } else {
            if (alliance == FTCAlliance.RED) {

            } else {

            }
        }
        drivingLibrary.stopDrivingMotors();
    }

}
