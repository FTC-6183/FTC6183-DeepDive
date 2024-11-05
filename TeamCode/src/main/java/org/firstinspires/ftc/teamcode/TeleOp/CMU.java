package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.UtilityOctoQuadConfigMenu;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Commands.ManualDriveCommand;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrive;

import java.util.List;

@Photon
@TeleOp
public class CMU extends LinearOpMode {
    private MecanumDrive drive;

    private GamepadEx controller1;

    private ElapsedTime time;

    Telemetry telemetry;




    @Override
    public void runOpMode() throws InterruptedException {

        drive = new MecanumDrive(hardwareMap);

        controller1 = new GamepadEx(gamepad1);

        time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

       // telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        drive.setDefaultCommand(new ManualDriveCommand(
                drive,
                controller1::getLeftY,
                controller1::getRightX,
                controller1::getLeftX
        ));

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            for (LynxModule hub : allHubs) {
                hub.clearBulkCache();
            }


            time.reset();

            for (LynxModule hub : allHubs) {
                    hub.clearBulkCache();
            }

            double loop = 1000/time.milliseconds();


            CommandScheduler.getInstance().run();


            //telemetry.addData("Loop (hz)", loop);

          //0  telemetry.update();

        }
    }

}
