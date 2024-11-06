package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Slide extends SubsystemBase {

    private DcMotorEx slideMotor;


    private PIDController controller = new PIDController(.02, 0,0);

    double target = 0;



    MultipleTelemetry telemetry;
    public Slide(HardwareMap hardwareMap, Telemetry tel) {

        slideMotor =  (DcMotorEx) hardwareMap.dcMotor.get("slide");
        this.telemetry = new MultipleTelemetry(tel, FtcDashboard.getInstance().getTelemetry());

        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void move(boolean up, boolean down) {

        if(up) {

            target = 500;
        }
        else if(down) {
            target = 1000;
        }


       // target = Range.clip(target, 0, 1400);

        int pos = slideMotor.getCurrentPosition();

        double power = controller.calculate(pos, target);
        slideMotor.setPower(power);
        telemetry.addData("Position", pos);
        telemetry.addData("Target", target);
        telemetry.addData("Power", power);
        telemetry.update();


    }
}
