package org.firstinspires.ftc.teamcode.Tests;

import android.widget.LinearLayout;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Subsystems.Slide;
@TeleOp(name = "test3")
@Photon
@Config

public class SlideArmMech extends LinearOpMode {
    private DcMotorEx slideMotor;


    private PIDController controller = new PIDController(.02, 0,0);

    double target = 0;

     @Override
    public void runOpMode() throws InterruptedException {

         slideMotor =  (DcMotorEx) hardwareMap.dcMotor.get("slide");

         slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
         slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

         GamepadEx gamepad = new GamepadEx(gamepad1);


        Slide slide = new Slide(hardwareMap, telemetry);
        waitForStart();

        if(isStopRequested()) return;

        while(opModeIsActive()) {




          //  slide.move(gamepad1.dpad_up, gamepad1.dpad_down);
              //  slide.move(gamepad.wasJustPressed(GamepadKeys.Button.DPAD_UP), gamepad.wasJustPressed(GamepadKeys.Button.DPAD_DOWN) );
            if(gamepad1.dpad_up) {target    = 1000;}
            else if(gamepad1.dpad_down) {target = 500;}
            int pos = slideMotor.getCurrentPosition();

            double power = controller.calculate(pos, target);
            slideMotor.setPower(power);
            telemetry.addData("Position", pos);
            telemetry.addData("Target", target);
            telemetry.addData("Power", power);
            telemetry.update();




        }
    }
}
