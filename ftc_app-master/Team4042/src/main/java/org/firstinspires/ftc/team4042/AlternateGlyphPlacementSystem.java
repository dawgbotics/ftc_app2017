package org.firstinspires.ftc.team4042;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Ryan Whiting (aka meme) on 11/2/2017.
 */

public class AlternateGlyphPlacementSystem {

    private DcMotor verticalDrive;
    private Servo hand;
    private final int BASE_DISP = 0;
    private final int BLOCK_DISP = 1500;

    public AlternateGlyphPlacementSystem(HardwareMap map) {
        verticalDrive = map.dcMotor.get("vertical drive");
    }

    public void setTargetPosition(int block) {
        verticalDrive.setTargetPosition(BASE_DISP + block*(BLOCK_DISP + 1));
    }

    public void runToPosition() {
        verticalDrive.setPower((verticalDrive.getTargetPosition() - verticalDrive.getCurrentPosition())/ 500);
    }
}
