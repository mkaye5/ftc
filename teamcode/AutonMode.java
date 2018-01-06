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
        this.drivingLibrary = new DrivingLibrary(opMode);
        this.drivingLibrary.setSpeed(0.75);
        this.colorArm = opMode.hardwareMap.get(Servo.class,"color_arm");
        colorSensor = opMode.hardwareMap.get(ColorSensor.class, "color_sensor");
        distanceSensor = opMode.hardwareMap.get(DistanceSensor.class, "color_sensor");
        this.opMode = opMode;
    }

    public Direction knockOffJewel() {
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
        Direction dir;
        if (alliance == FTCAlliance.RED) {
            if (colorSensor.red() >= 30) {
                drivingLibrary.driveStraight(0, -.4f);
                dir = Direction.BACKWARD;
            }
            else {
                drivingLibrary.driveStraight(0, .4f);
                dir = Direction.FORWARD;
            }
        } else {
            if (colorSensor.red() >= 15) {
                drivingLibrary.driveStraight(0, .4f);
                dir = Direction.FORWARD;

            }
            else {
                drivingLibrary.driveStraight(0, -.4f);
               dir = Direction.BACKWARD;
            }
        }
        opMode.sleep(500);
        drivingLibrary.stopDrivingMotors();
        colorArm.setPosition(1);
        opMode.telemetry.update();
        return dir;
    }

    public void driveToSafeZone(Direction dir) {
        opMode.sleep(500);
        if (position == FTCPosition.LEFT) {
            if (dir == Direction.BACKWARD) {
                if (alliance == FTCAlliance.RED) {
                    //if red, on left side, and went backwards
                    drivingLibrary.driveStraight(0, .6f);
                    opMode.sleep(2500);
                    drivingLibrary.driveStraight(1f, 0);
                    opMode.sleep(250);
                } else {
                    //if blue, on left side, and went backwards
                    drivingLibrary.driveStraight(0, -.4f);
                    opMode.sleep(500);
                    drivingLibrary.driveStraight(-1f, 0);
                    opMode.sleep(1000);
                    drivingLibrary.driveStraight(0, -.4f);
                    opMode.sleep(500);
                }
            } else {
                if (alliance == FTCAlliance.RED) {
                    //if red, on left side, and went forwards
                    drivingLibrary.driveStraight(0, .4f);
                    opMode.sleep(750);
                    drivingLibrary.driveStraight(1f, 0);
                    opMode.sleep(250);
                }
                else {
                    //if blue, on left side, and went forwards
                    drivingLibrary.driveStraight(0, -.6f);
                    opMode.sleep(1250);
                    drivingLibrary.driveStraight(-1f, 0);
                    opMode.sleep(1000);
                    drivingLibrary.driveStraight(0, -.4f);
                    opMode.sleep(500);
                }
            }

        } else {
            opMode.telemetry.addData("else", "yes");
            if (dir == Direction.BACKWARD) {
                if (alliance == FTCAlliance.RED) {
                    //if red, on right side, and went backwards
                    opMode.telemetry.addData("backwards", "yes");
                    drivingLibrary.driveStraight(0, .6f);
                    opMode.sleep(2000);
                    drivingLibrary.driveStraight(-1f, 0);
                    opMode.sleep(1000);
                    drivingLibrary.driveStraight(0, .4f);
                    opMode.sleep(500);
                } else {
                    //if blue, on right side, and went backwards
                    drivingLibrary.driveStraight(0, -.4f);
                    opMode.sleep(750);
                    drivingLibrary.driveStraight(1f, 0);
                    opMode.sleep(250);
                }
            } else {
                if (alliance == FTCAlliance.RED) {
                    //if red, on right side, and went forwards
                    opMode.telemetry.addData("backwards", "no");
                    drivingLibrary.driveStraight(0, .4f);
                    opMode.sleep(800);
                    drivingLibrary.driveStraight(-1f, 0);
                    opMode.sleep(1000);
                    drivingLibrary.driveStraight(0, .4f);
                    opMode.sleep(300);
                } else {
                    //if blue, on right side, and went forwards
                    drivingLibrary.driveStraight(0, -.6f);
                    opMode.sleep(2500);
                    drivingLibrary.driveStraight(1f, 0);
                    opMode.sleep(250);
                }
            }
        }
        opMode.telemetry.update();
        drivingLibrary.stopDrivingMotors();
    }

}
