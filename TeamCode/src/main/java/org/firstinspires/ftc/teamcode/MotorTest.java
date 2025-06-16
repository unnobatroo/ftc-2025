package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;



@TeleOp
public class MotorTest extends OpMode {

    private Servo s1, s2;


    double intakeClaw_close = 0.19;
    double intakeClaw_open = 0.40;

    private DcMotor leftlift, centerlift, rightlift;

    @Override
    public void init() {
        s1 = hardwareMap.get(Servo.class, "s1");
        s2 = hardwareMap.get(Servo.class, "s2");

        leftlift = hardwareMap.get(DcMotor.class, "leftlift");
        centerlift = hardwareMap.get(DcMotor.class, "centerlift");
        rightlift = hardwareMap.get(DcMotor.class, "rightlift");


        leftlift.setDirection(DcMotor.Direction.REVERSE);
    }


    @Override
    public void loop() {
        if (1 == 1){
            s1.setPosition(0.0);
            s2.setPosition(1.0);
        }

        if (gamepad1.right_trigger > 0.5) {
            leftlift.setPower(1.0);
            centerlift.setPower(1.0);
            rightlift.setPower(1.0);
        } else if (gamepad1.left_trigger > 0.5) {
            leftlift.setPower(-1.0);
            centerlift.setPower(-1.0);
            rightlift.setPower(-1.0);
        } else {
            leftlift.setPower(0);
            centerlift.setPower(0);
            rightlift.setPower(0);
        }

    }
}
