package org.firstinspires.ftc.team12788;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;
import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.opmode.LinearVisionOpMode;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Jewl Auto", group = "Linear Opmode")
public class JewelZoneAuto extends LinearOpMode {

    private MecanumDrive drive = new MecanumDrive();
    //private VuMarkIdentifier mark = new VuMarkIdentifier();

    private DcMotor lift;
    private DcMotor intakeRight;
    private DcMotor intakeLeft;

    public Servo grabLeft;
    public Servo grabRight;
    public Servo jewel;

    ColorSensor color = new ColorSensor("color");
    AnalogSensor infrared = new AnalogSensor("ir");
    AnalogSensor whisker = new AnalogSensor("whisker");

    private RelicRecoveryVuMark vuMark;


    //@Override
    //public void runOpMode() {
    //drive.initialize(telemetry, hardwareMap);
    //liftLeft = hardwareMap.dcMotor.get("liftLeft");
    //liftRight = hardwareMap.dcMotor.get("liftRight");
    //intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
    //intakeRight = hardwareMap.dcMotor.get("intakeRight");
    //drive.runWithoutEncoders();
    //grabLeft = hardwareMap.servo.get("grabLeft");
    //grabRight = hardwareMap.servo.get("grabRight");
    //mark.initialize(telemetry, hardwareMap);
    //waitForStart();
    //}

    public void driveLength(double length, boolean isRed) {
        infrared.getVoltageAvg();
        double diff = length - infrared.getVoltageAvg();
        diff = diff * -.25;
        if (isRed) {
            drive.driveXYR(.7, diff, 1, 0);
        } else {
            drive.driveXYR(.7, diff, -1, 0);
        }
    }

    public void reset() {
        drive.resetEncoders();
        sleep(500);
        drive.runWithEncoders();
    }

    public void move(Direction direction, double speed, double targetTicks) {
        while (!drive.driveWithEncoders(direction, speed, targetTicks) && opModeIsActive()) {
        }
        reset();
    }

    public void rotate(Direction.Rotation rotation, double speed, double targetTicks) {
        while (!drive.rotateWithEncoders(rotation, speed, targetTicks) && opModeIsActive()) {
        }
        reset();
    }


    public void dropoff() {
        while (!drive.driveWithEncoders(Direction.Backward, Autonomous.speedy, 9 * Autonomous.tile / 24) && opModeIsActive())
            ;
        grabLeft.setPosition(.5);
        grabRight.setPosition(-.1);
        while (!drive.driveWithEncoders(Direction.Forward, Autonomous.speedy, 9 * Autonomous.tile / 24) && opModeIsActive())
            ;
    }

    public void jewelKnock(boolean isRed) {
        jewel.setPosition(.7);
        sleep(1000);
        if (isRed) {
            if (color.SenseRed()) {
                move(Direction.Forward, Autonomous.speedy-.4, Autonomous.tile);

            } else {
                move(Direction.Backward, Autonomous.speedy-.5, Autonomous.tile / 8);
                jewel.setPosition(0.2);
                sleep(1000);
                move(Direction.Forward, Autonomous.speedy-.4, 7 * Autonomous.tile / 8);
            }
        } else {
            if (color.SenseBlue()) {
                move(Direction.Forward, Autonomous.speedy-.4, Autonomous.tile / 8);
                jewel.setPosition(0.2);
                sleep(1000);
                move(Direction.Backward, Autonomous.speedy-.4, 7 * Autonomous.tile / 8);
            } else {
                move(Direction.Backward, Autonomous.speedy-.4, Autonomous.tile);
            }
        }
        jewel.setPosition(.25);
        sleep(1000);
    }


    public void JewelWhisker(boolean isRed, boolean isTop) {
        /*drive.initialize(telemetry, hardwareMap);
        liftLeft = hardwareMap.dcMotor.get("liftLeft");
        liftRight = hardwareMap.dcMotor.get("liftRight");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");
        drive.runWithoutEncoders();


        grabLeft = hardwareMap.servo.get("grabLeft");
        grabRight = hardwareMap.servo.get("grabRight");
        mark.initialize(telemetry, hardwareMap);

        waitForStart();
        grabRight.setPosition(-1);
        grabLeft.setPosition(.8);
        jewel.setPosition(-1);*/
        //vuMark = mark.getMark();
        vuMark = RelicRecoveryVuMark.CENTER;
        jewelKnock(isRed);
        if (isRed && !isTop) {

            while (whisker.getVoltage() < 2) {
                drive.driveXYR(.4, 0, 1, 0);
            }
            sleep(1000);
            rotate(Direction.Rotation.Counterclockwise, Autonomous.speedy - .2, Autonomous.turn);
            /*

            if (vuMark == RelicRecoveryVuMark.LEFT) {
                move(Direction.Right, Autonomous.speedy, 1 * Autonomous.tile / 6);
                dropoff();
                move(Direction.Right, Autonomous.speedy, 1 * Autonomous.tile / 3);
            }*/
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                move(Direction.Right, Autonomous.speedy, 3 * Autonomous.tile / 6);
                dropoff();
            }/*
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                move(Direction.Right, Autonomous.speedy, 5 * Autonomous.tile / 6);
                dropoff();
                move(Direction.Left, Autonomous.speedy, 1 * Autonomous.tile / 3);
            }*/


        }
        if (!isRed && !isTop) {

            while (whisker.getVoltage() < 2) {
                drive.driveXYR(.4, 0, -1, 0);
            }
            sleep(1000);
            rotate(Direction.Rotation.Counterclockwise, Autonomous.speedy - .2, Autonomous.turn);
            /*jewel.setPosition(1);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                move(Direction.Left, Autonomous.speedy, 1 * Autonomous.tile / 6);
                dropoff();
                move(Direction.Left, Autonomous.speedy, 1 * Autonomous.tile / 3);
            }
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                move(Direction.Left, Autonomous.speedy, 3 * Autonomous.tile / 6);
                dropoff();
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                move(Direction.Left, Autonomous.speedy, 5 * Autonomous.tile / 6);
                dropoff();
                move(Direction.Right, Autonomous.speedy, 1 * Autonomous.tile / 3);
            }*/
        }

        if (isRed && isTop) {

            while (whisker.getVoltage() < 2) {
                drive.driveXYR(.4, 0, 1, 0);
            }
            sleep(1000);
            rotate(Direction.Rotation.Clockwise, Autonomous.speedy - .2, Autonomous.turn);
            /*jewel.setPosition(1);
            rotate(Direction.Rotation.Clockwise, Autonomous.speedy - .2, Autonomous.turn);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                move(Direction.Right, Autonomous.speedy, 5 * Autonomous.tile / 6);
                dropoff();
                move(Direction.Left, Autonomous.speedy, 1 * Autonomous.tile / 3);
            }
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                move(Direction.Right, Autonomous.speedy, 3 * Autonomous.tile / 6);
                dropoff();
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                move(Direction.Right, Autonomous.speedy, 1 * Autonomous.tile / 6);
                dropoff();
                move(Direction.Right, Autonomous.speedy, 1 * Autonomous.tile / 3);
            }*/
        }
        if (!isRed && isTop) {

            while (whisker.getVoltage() < 2) {
                drive.driveXYR(.4, 0, -1, 0);
            }
            sleep(1000);
            rotate(Direction.Rotation.Clockwise, Autonomous.speedy - .2, Autonomous.turn);
            /*jewel.setPosition(0);
            rotate(Direction.Rotation.Counterclockwise, Autonomous.speedy - .2, Autonomous.turn);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                move(Direction.Left, Autonomous.speedy, 1 * Autonomous.tile / 6);
                dropoff();
                move(Direction.Left, Autonomous.speedy, 1 * Autonomous.tile / 3);
            }
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                move(Direction.Left, Autonomous.speedy, 3 * Autonomous.tile / 6);
                dropoff();
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                move(Direction.Left, Autonomous.speedy, 5 * Autonomous.tile / 6);
                dropoff();
                move(Direction.Right, Autonomous.speedy, 1 * Autonomous.tile / 3);
            }*/
        }


    }

    @Override
    public void runOpMode() {
        drive.initialize(telemetry, hardwareMap);
        lift = hardwareMap.dcMotor.get("lift");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");
        drive.runWithEncoders();

        jewel = hardwareMap.servo.get("jewel");
        grabLeft = hardwareMap.servo.get("grabLeft");
        grabRight = hardwareMap.servo.get("grabRight");
        color.initialize(hardwareMap);
        whisker.initialize(hardwareMap);

        //mark.initialize(telemetry, hardwareMap);

        boolean isRed = true;
        boolean isTop = false;
        /*while (opModeIsActive() && !isStarted()) {
            if (gamepad1.x) {
                isRed = false;
            }
            if (gamepad1.b) {
                isRed = true;
            }
            if (gamepad1.y) {
                isTop = true;
            }
            if (gamepad1.a) {
                isTop = false;
            }
        }*/
        waitForStart();
        grabLeft.setPosition(1);
        grabRight.setPosition(0);
        JewelWhisker(isRed, isTop);

    }
}

