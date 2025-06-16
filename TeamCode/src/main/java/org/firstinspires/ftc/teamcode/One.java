package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@TeleOp(name="One", group="Linear OpMode")
public class One extends OpMode {

    PinPointDriver odo;

    private DcMotor backright, frontright, backleft, frontleft;

    // Параметры P-контроллера
    private static final double Kp = 0.008; // Уменьшенный коэффициент пропорциональности
    private static final double DEAD_ZONE = 3; // Мёртвая зона для heading

    @Override
    public void init() {
        backright = hardwareMap.get(DcMotor.class, "backright");
        frontright = hardwareMap.get(DcMotor.class, "frontright");
        backleft = hardwareMap.get(DcMotor.class, "backleft");
        frontleft = hardwareMap.get(DcMotor.class, "frontleft");

        frontleft.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.REVERSE);

        odo = hardwareMap.get(PinPointDriver.class,"odo");
        odo.setOffsets(-84.0, -168.0, DistanceUnit.MM);
        odo.setEncoderResolution(PinPointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(PinPointDriver.EncoderDirection.FORWARD, PinPointDriver.EncoderDirection.FORWARD);
        odo.resetPosAndIMU();
    }

    @Override
    public void loop() {
        odo.update();
        Pose2D pos = odo.getPosition();

        telemetry.addData("X", pos.getX(DistanceUnit.MM));
        telemetry.addData("Y", pos.getY(DistanceUnit.MM));
        telemetry.addData("Heading", pos.getHeading(AngleUnit.DEGREES));
        telemetry.update();

        // Движение вперед, пока X меньше 150
        if (pos.getX(DistanceUnit.MM) > -500) {
            backright.setPower(0.3);
            frontright.setPower(0.3);
            backleft.setPower(0.3);
            frontleft.setPower(0.3);
        } else if (pos.getX(DistanceUnit.MM) < -550) {
            backright.setPower(-0.3);
            frontright.setPower(-0.3);
            backleft.setPower(-0.3);
            frontleft.setPower(-0.3);
        } else {
            // Остановка моторов
            backright.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            frontleft.setPower(0);

            // Удержание heading на 0 с использованием P-контроллера
            double currentHeading = pos.getHeading(AngleUnit.DEGREES);
            double error = -currentHeading; // Ошибка (направление к 0)

            // Проверка на мёртвую зону
            if (Math.abs(error) > DEAD_ZONE) {

                // Применение коррекции к моторам
                double correction = error * Kp;

                backright.setPower(correction);
                frontright.setPower(correction);
                backleft.setPower(correction);
                frontleft.setPower(correction);
            }
        }
    }
}