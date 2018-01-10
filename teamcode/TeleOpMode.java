package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by lamanwyner on 12/30/17.
 */

@TeleOp
public class TeleOpMode extends LinearOpMode {
    DrivingLibrary drivingLibrary;
    Servo servoLeft, servoRight;
    DcMotor pulley;

    public void runOpMode() throws InterruptedException {
        drivingLibrary = new DrivingLibrary(this);
        drivingLibrary.setSpeed(1);
        servoLeft = hardwareMap.get(Servo.class, "left_arm");
        servoRight = hardwareMap.get(Servo.class, "right_arm");
        pulley = hardwareMap.get(DcMotor.class, "pulley");

        boolean closeClaws = false;
        boolean openClaws = true;
        boolean closeLeftClaw = false;
        boolean closeRightClaw = false;
        boolean openLeftClaw = false;
        boolean openRightClaw = false;
        boolean clawSync = true;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            drivingLibrary.driveStraight(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            drivingLibrary.turn(gamepad1.right_stick_x, -gamepad1.right_stick_y);

            if (clawSync) {
                closeClaws = gamepad2.left_bumper;
                openClaws = gamepad2.left_trigger > 0.5;

                if (closeClaws) {
                    servoLeft.setPosition(servoLeft.getPosition() + 0.02);
                    servoRight.setPosition(servoRight.getPosition() - 0.02);
                }
                if (openClaws) {
                    if (servoLeft.getPosition() - 0.02 < 0.1) {
                        servoLeft.setPosition(0.1);
                    } else {
                        servoLeft.setPosition(servoLeft.getPosition() - 0.02);
                    }
                    if (servoRight.getPosition() + 0.02 > 0.9) {
                        servoRight.setPosition(0.9);
                    } else {
                        servoRight.setPosition(servoRight.getPosition() + 0.02);
                    }
                }
            } else {
                closeLeftClaw = gamepad2.left_bumper;
                openLeftClaw = gamepad2.left_trigger > 0.5;
                closeRightClaw = gamepad2.right_bumper;
                openRightClaw = gamepad2.right_trigger > 0.5;

                if (closeLeftClaw) {
                    servoLeft.setPosition(servoLeft.getPosition() + 0.02);
                }
                if (openLeftClaw) {
                    servoLeft.setPosition(servoLeft.getPosition() - 0.02);
                }

                if (closeRightClaw) {
                    servoRight.setPosition(servoRight.getPosition() - 0.02);
                }
                if (openRightClaw) {
                    servoRight.setPosition(servoRight.getPosition() + 0.02);
                }
            }

            if (gamepad2.dpad_up) {
                pulley.setPower(0.5);
            } else if (gamepad2.dpad_down) {
                pulley.setPower(-0.5);
            } else {
                pulley.setPower(0);
            }

            if (gamepad2.a) {
                clawSync = !clawSync;
            }

            telemetry.addData("Status", "Running");
            telemetry.addData("Left Arm Pos", servoLeft.getPosition());
            telemetry.addData("Right Arm Pos", servoRight.getPosition());
            telemetry.addData("Claw Sync", clawSync);
            telemetry.update();
        }
    }
}
