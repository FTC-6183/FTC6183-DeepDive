package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp
public class SlidePIDTuning extends LinearOpMode {


    public static double p = 0, i = 0, d = 0;

    public static int target = 0;

    private PIDController controller;
    private DcMotorEx slideMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        controller = new PIDController(p,i,d);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        slideMotor = (DcMotorEx) hardwareMap.dcMotor.get("arm");

        ElapsedTime timer = new ElapsedTime();


        waitForStart();
        if(isStopRequested()) {return;}
        timer.startTime();

        while(opModeIsActive()) {

            controller.setPID(p,i,d);

            int pos = slideMotor.getCurrentPosition();

            double power = controller.calculate(pos, target);

            slideMotor.setPower(power);

            telemetry.addData("Position", pos);
            telemetry.addData("Target", target);

            System.out.println(timer.time() + " " + pos);

            telemetry.update();

        }



    }
}
