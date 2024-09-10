package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Vision.Pipelines.DashboardStream;
import org.firstinspires.ftc.teamcode.Vision.Pipelines.LocationID;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp
public class Dashtest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        DashboardStream processor = new DashboardStream();

        LocationID process = new LocationID();
        waitForStart();

        new VisionPortal.Builder()
                .addProcessor(process)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

     //   FtcDashboard.getInstance().startCameraStream(processor, 0);

        waitForStart();

        if(isStopRequested()) {return;}


        while(opModeInInit()) {
            telemetry.addData("Zone", process.getSelection());
            telemetry.update();

        }
        while (opModeIsActive()) {
            telemetry.addData("Zone", process.getSelection());
            telemetry.update();

        }
    }
}