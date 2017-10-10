package org.firstinspires.ftc.team4042;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="TestAuto", group="Autonomous")
public class Auto extends LinearOpMode {

    MecanumDrive drive;

    public enum Direction {Right, Left, Forward, Backward, Clockwise, Counterclockwise};

    @Override
    public void runOpMode() {
        drive = new MecanumDrive(hardwareMap, telemetry, true);

        waitForStart();

        //TODO: TEST THIS
        autoDrive(Direction.Forward, Drive.FULL_SPEED, 50);
        autoDrive(Direction.Left, Drive.FULL_SPEED, 50);
        autoDrive(Direction.Counterclockwise, Drive.FULL_SPEED, 50);
        //check sensor sums
        //robot starts facing right
        //scan vision patter
        //go to front of jewels
        //cv scan
        //knock off other jewel
        //head right
        //whisker sensor hits cryptobox
        //back up
        //repeat ^ until whisker disengages
        //move right until we see -^-^-| from ultrasonic
        //place block
        //detach and extend robot towards glyph
    }

    /**
     * Drives in the given Direction at the given speed until targetTicks is reached
     * @param direction The direction to head in
     * @param speed The speed to move at
     * @param targetTicks The final distance to have travelled, in encoder ticks
     */
    private void autoDrive(Direction direction, double speed, double targetTicks) {
        boolean done = false;
        while (opModeIsActive() && !done) {
            done = drive.driveWithEncoders(direction, speed, targetTicks);
        }
    }
}