package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Arm extends SubsystemBase {

    private PIDFCoefficients pidfCoefficients = new PIDFCoefficients(.04, 0, .01);

}
