package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp
public class SlidePIDTuning extends LinearOpMode {


    public static double p = 0.02, i = 0, d = 0;

    public static int target = 0;

    private PIDController controller;
    private DcMotorEx slideMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        controller = new PIDController(p,i,d);
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        slideMotor = (DcMotorEx) hardwareMap.dcMotor.get("arm");

        ElapsedTime timer = new ElapsedTime();

        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();
        if(isStopRequested()) {return;}
        timer.startTime();

        while(opModeIsActive()) {
/*
            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);
            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);
*/


            controller.setPID(p,i,d);
           //if(currentGamepad1.a && !previousGamepad1.a) {target    += 50;}
           // else if(currentGamepad1.b && !previousGamepad1.b) {target -= 50;}

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
