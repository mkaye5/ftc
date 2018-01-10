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

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            drivingLibrary.driveStraight(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            drivingLibrary.turn(gamepad1.right_stick_x, -gamepad1.right_stick_y);

            closeClaws = gamepad2.left_bumper;
            openClaws = gamepad2.left_trigger > 0.5;

            if (closeClaws) {
                servoLeft.setPosition(0.55);
                servoRight.setPosition(0.45);
            }
            if (openClaws) {
                servoLeft.setPosition(0.1);
                servoRight.setPosition(0.9);
            }

            if (gamepad2.dpad_up) {
                pulley.setPower(0.5);
            } else if (gamepad2.dpad_down) {
                pulley.setPower(-0.5);
            } else {
                pulley.setPower(0);
            }

            telemetry.addData("Status", "Running");
            telemetry.addData("Left Arm Pos", servoLeft.getPosition());
            telemetry.addData("Right Arm Pos", servoRight.getPosition());
            telemetry.update();
        }
    }
}
