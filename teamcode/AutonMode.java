package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.enums.Direction;
import org.firstinspires.ftc.enums.FTCAlliance;
import org.firstinspires.ftc.enums.FTCPosition;
import org.firstinspires.ftc.libraries.DrivingLibrary;
import org.firstinspires.ftc.libraries.VuMarkIdentifyLibrary;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

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
    VuMarkIdentifyLibrary vuMarkIdentify;

    public AutonMode(LinearOpMode opMode, FTCAlliance alliance, FTCPosition position) {
        this.alliance = alliance;
        this.position = position;
        this.drivingLibrary = new DrivingLibrary(opMode);
        this.drivingLibrary.setSpeed(0.75);
        this.colorArm = opMode.hardwareMap.get(Servo.class,"color_arm");
        colorSensor = opMode.hardwareMap.get(ColorSensor.class, "color_sensor");
        distanceSensor = opMode.hardwareMap.get(DistanceSensor.class, "color_sensor");
        this.opMode = opMode;
        this.vuMarkIdentify = new VuMarkIdentifyLibrary(opMode);
    }

    /*public Direction knockOffJewel() {
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
        opMode.telemetry.addData("Red - blue * 2  ", colorSensor.red() - colorSensor.blue()*2);
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });
        opMode.telemetry.update();
        opMode.sleep(3000);
        Direction dir;
        if (alliance == FTCAlliance.RED) {
            if (colorSensor.red() - colorSensor.blue()*2 > 0) {
                drivingLibrary.driveStraight(0, -.4f);
                dir = Direction.BACKWARD;
            }
            else {
                drivingLibrary.driveStraight(0, .4f);
                dir = Direction.FORWARD;
            }
        } else {
            if (colorSensor.red() - colorSensor.blue()*2 > 0) {
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
    }*/
    //updated code
    public Direction knockOffJewel() {
        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;

        //MOVE SERVO
        colorArm.setPosition(0.1);
        opMode.sleep(2000);

        // convert the RGB values to HSV values.
        //SENSE COLOR
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
        float hue = hsvValues[0];
        opMode.telemetry.addData("hue", hue);

        boolean seeingRedJewel = hue < 60 || hue > 320;
        boolean seeingBlueJewel = hue > 120 && hue < 260;

        //DELETE
        if (seeingBlueJewel && !seeingRedJewel) {
            opMode.telemetry.addData("Saw:", "Blue Jewel");
        }
        else if (!seeingBlueJewel && seeingRedJewel) {
            opMode.telemetry.addData("Saw:", "Red Jewel");
        }
        else {
            opMode.telemetry.addData("Saw:", "Unknown");
        }
        //DELETE
        opMode.telemetry.update();
        /*
        opMode.sleep(3000);
        Direction dir;
        if (alliance == FTCAlliance.RED) {
            if (seeingBlueJewel) {
                drivingLibrary.driveStraight(0, -.4f);
                dir = Direction.BACKWARD;
            }
            else if (seeingRedJewel) {
                drivingLibrary.driveStraight(0, .4f);
                dir = Direction.FORWARD;
            }
            else {
                colorArm.setPosition(1);
                opMode.sleep(250);
                drivingLibrary.driveStraight(0, .4f);
                dir = Direction.FORWARD;
            }
        } else {
            if (seeingRedJewel) {
                drivingLibrary.driveStraight(0, .4f);
                dir = Direction.FORWARD;
            }
            else if (seeingBlueJewel){
                drivingLibrary.driveStraight(0, -.4f);
                dir = Direction.BACKWARD;
            }
            else {
                colorArm.setPosition(1);
                opMode.sleep(250);
                drivingLibrary.driveStraight(0, .4f);
                dir = Direction.FORWARD;
            }
        }
        opMode.sleep(500);
        drivingLibrary.stopDrivingMotors();
        colorArm.setPosition(1);
        opMode.telemetry.update();
        return dir*/
        return Direction.FORWARD;
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
                    opMode.sleep(800);
                }
            }

        } else {
            if (dir == Direction.BACKWARD) {
                if (alliance == FTCAlliance.RED) {
                    //if red, on right side, and went backwards
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

    public RelicRecoveryVuMark identifyPictograph() {
        return vuMarkIdentify.identifyPictograph(opMode);
    }

}
