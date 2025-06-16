package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Povorot extends OpMode{
    //intake
    //private Servo servoIntakeLeft, servoIntakeRight, servoIntakeClaw, servoIntakeRotate, servoIntakeHandHand;
    //outtake
    private Servo servoOuttakeLeft, servoOuttakeRight, servoOuttakeClaw, servoOuttakeRotate, servoOuttakeLift;

    //lifts
    //private DcMotor liftMotorLeft, liftMotorRight, intakeLift, liftMotorCenter;

    //movement
    private DcMotor backLeft, backRight, frontLeft, frontRight;

    private long actionStartTime = 0;
    private boolean actionInProgress = false;
    private boolean actionInProgress2 = false;
    private boolean actionInProgress3 = false;
    private boolean actionInProgress4 = false;
    private int currentStep = 0;

    int a = 0;

    private boolean isLifted = false;

    double intakeClaw_close = 0.19;
    double intakeClaw_open = 0.4;
    double intakeRight_up = 0.0;
    double intakeRight_down = 0.0;
    double intakeLeft_up = 0.0;
    double intakeLeft_down = 0.0;
    double intakeRight_default = 0.0;
    double intakeLeft_default = 0.0;
    double intakeRotate_default = 0.0;
    double intakeHand_down = 0.0;
    double intakeHand_up = 0.0;

    double outtakeClaw_close = 0.19;
    double outtakeClaw_open = 0.4;
    double outtakeRight_up = 0.15;
    double outtakeRight_down = 0.0;
    double outtakeLeft_up = 0.95;
    double outtakeLeft_down = 0.0;
    double outtakeRight_default = 0.0;
    double outtakeLeft_default = 0.0;
    double outtakeRotate_default = 0.0;
    double outtakeRotate_down = 0.0;
    double outtakeRotate_up = 0.9;
    double outtakeLift_close = 1.0;
    double outtakeLift_open = 0.3;
    int intakeLift_open = 0;
    int intakeLift_close = 300;

    public void init (){


        servoOuttakeLeft = hardwareMap.get(Servo.class, "outtake_left");
        servoOuttakeRight = hardwareMap.get(Servo.class, "outtake_right");
        servoOuttakeClaw = hardwareMap.get(Servo.class, "outtake_claw");
        servoOuttakeRotate = hardwareMap.get(Servo.class, "outtake_rotate");
        servoOuttakeLift = hardwareMap.get(Servo.class, "outtake_lift");

        backLeft = hardwareMap.get(DcMotorEx.class, "frontleft");
        backRight = hardwareMap.get(DcMotorEx.class, "frontright");
        frontLeft = hardwareMap.get(DcMotorEx.class, "backleft");
        frontRight = hardwareMap.get(DcMotorEx.class, "backright");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);


    }
    public void loop(){
        //default position

        if (gamepad1.left_trigger > 0.5 && !actionInProgress2) {
            actionStartTime = System.currentTimeMillis();
            actionInProgress2 = true;
            currentStep = 0;
        }
        if (actionInProgress2) {
            long elapsedTime = System.currentTimeMillis() - actionStartTime;

            switch (currentStep) {
                case 0:
                    if (elapsedTime > 100) {
                        currentStep++;

                        actionStartTime = System.currentTimeMillis();
                        servoOuttakeLeft.setPosition(outtakeLeft_up+ 0.5);
                        servoOuttakeRight.setPosition(outtakeRight_up-0.5);
                    }
                case 1:
                    if (elapsedTime > 300) {
                        currentStep++;

                        actionStartTime = System.currentTimeMillis();
                        servoOuttakeClaw.setPosition(outtakeClaw_open);
                        actionInProgress2 = false;
                    }
            }
        }

        if (gamepad1.right_trigger > 0.5 && !actionInProgress) {
            actionStartTime = System.currentTimeMillis();
            actionInProgress = true;
            currentStep = 0;
        }

        if (actionInProgress) {
            long elapsedTime = System.currentTimeMillis() - actionStartTime;

            switch (currentStep) {
                case 0:
                    if (elapsedTime > 50) {
                        currentStep++;

                        actionStartTime = System.currentTimeMillis();
                        servoOuttakeClaw.setPosition(outtakeClaw_open);
                        servoOuttakeRotate.setPosition(outtakeRotate_up);
                    }
                    break;

                case 1:
                    if (elapsedTime > 200) {
                        currentStep++;
                        actionStartTime = System.currentTimeMillis();
                        servoOuttakeLeft.setPosition(outtakeLeft_up+0.1);
                        servoOuttakeRight.setPosition(outtakeRight_up-0.1);
                        servoOuttakeLift.setPosition(0.85);
                    }
                    break;

                case 2:
                    if (elapsedTime > 500) {
                        currentStep++;
                        servoOuttakeClaw.setPosition(outtakeClaw_close);
                        actionStartTime = System.currentTimeMillis();

                    }
                case 3:
                    if (elapsedTime > 700) {
                        currentStep++;
                        servoOuttakeLeft.setPosition(outtakeLeft_up-0.10);
                        servoOuttakeRight.setPosition(outtakeRight_up+0.10);
                        actionStartTime = System.currentTimeMillis();
                        actionInProgress = false;
                    }
                    break;
            }
        }


        double drive = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotate = -gamepad1.right_stick_x;

        double speed = 1;
        if (gamepad1.right_bumper){
            speed = 0.3;
        } else {
            speed = 1;
        }

        double frontLeftPower = -(drive + strafe + rotate);
        double frontRightPower = drive - strafe - rotate;
        double backLeftPower = -(drive - strafe + rotate);
        double backRightPower = drive + strafe - rotate;


        frontLeft.setPower(frontLeftPower * speed);
        frontRight.setPower(frontRightPower * speed);
        backLeft.setPower(backLeftPower * speed);
        backRight.setPower(backRightPower * speed);
    }

}