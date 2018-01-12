package org.firstinspires.ftc.libraries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by lamanwyner on 1/11/18.
 */

public class GlyphArmLibrary {
    private OpMode opMode;
    private HardwareMap hardwareMap;
    private DcMotor pulley;
    private Servo leftTop, leftBottom, rightTop, rightBottom;

    private double[] closedPosition, openPosition;
    private double[] maxClosed, maxOpen;
    private double increment;

    double pulleySpeed;

    public GlyphArmLibrary(OpMode opMode) {
        this.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        pulley = hardwareMap.get(DcMotor.class, "pulley");
        leftTop = hardwareMap.get(Servo.class, "left_top");
        leftBottom = hardwareMap.get(Servo.class, "left_bottom");
        rightTop = hardwareMap.get(Servo.class, "right_top");
        rightBottom = hardwareMap.get(Servo.class, "right_bottom");

        closedPosition = new double[] {0.55, 0.45};
        openPosition = new double[] {0.1, 0.9};
        maxClosed = new double[] {1.0, 0};
        maxOpen = new double[] {0.1, 0.9};
        increment = 0.02;

        pulleySpeed = 0.5;
    }

    public void setClosedPosition(double l, double r) {
        closedPosition[0] = l;
        closedPosition[1] = r;
    }

    public void setOpenPosition(double l, double r) {
        openPosition[0] = l;
        openPosition[1] = r;
    }

    public void setIncrement(double d) {
        increment = d;
    }

    public void setMaxClosed(double l, double r) {
        maxClosed[0] = l;
        maxClosed[1] = r;
    }

    public void setMaxOpen(double l, double r) {
        maxOpen[0] = l;
        maxOpen[1] = r;
    }

    public void setPulleySpeed(double speed) {
        pulleySpeed = speed;
    }

    public void closeArmsPreset(boolean lb) {
        if (lb) {
            leftTop.setPosition(closedPosition[0]);
            leftBottom.setPosition(closedPosition[0]);
            rightTop.setPosition(closedPosition[1]);
            rightBottom.setPosition(closedPosition[1]);
        }
    }

    public void openArmsPreset(float lt) {
        if (lt > 0.5) {
            leftTop.setPosition(openPosition[0]);
            leftBottom.setPosition(openPosition[0]);
            rightTop.setPosition(openPosition[1]);
            rightBottom.setPosition(openPosition[1]);
        }
    }

    public void closeArmsIncrement(boolean rb) {
        if (rb) {
            if (leftTop.getPosition() + increment <= maxClosed[0]) {
                leftTop.setPosition(leftTop.getPosition() + increment);
            }
            if (leftBottom.getPosition() + increment <= maxClosed[0]) {
                leftBottom.setPosition(leftBottom.getPosition() + increment);
            }
            if (rightTop.getPosition() - increment >= maxClosed[1]) {
                rightTop.setPosition(rightTop.getPosition() - increment);
            }
            if (rightBottom.getPosition() - increment >= maxClosed[1]) {
                rightBottom.setPosition(rightBottom.getPosition() - increment);
            }
        }
    }

    public void openArmsIncrement(float rt) {
        if (rt > 0.5) {
            if (leftTop.getPosition() - increment >= maxOpen[0]) {
                leftTop.setPosition(leftTop.getPosition() - increment);
            }
            if (leftBottom.getPosition() - increment >= maxOpen[0]) {
                leftBottom.setPosition(leftBottom.getPosition() - increment);
            }
            if (rightTop.getPosition() + increment <= maxOpen[1]) {
                rightTop.setPosition(rightTop.getPosition() + increment);
            }
            if (rightBottom.getPosition() + increment <= maxOpen[1]) {
                rightBottom.setPosition(rightBottom.getPosition() + increment);
            }
        }
    }

    public void movePulley(Gamepad gamepad) {
        if (gamepad.dpad_up) {
            pulley.setPower(pulleySpeed);
        } else if (gamepad.dpad_down) {
            pulley.setPower(-pulleySpeed);
        } else {
            pulley.setPower(0);
        }
    }
}