package org.firstinspires.ftc.teamcode.Vision;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.RelativeDateTimeFormatter;
import android.provider.ContactsContract;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.concurrent.atomic.AtomicReference;
import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@TeleOp
@Config
public class Dashcam extends LinearOpMode {

    public static int xCord = 100;

    static Rect rectLeft = new Rect(xCord, 190, 80, 90);


   // public Rect rectMiddle = new Rect(250, 170, 200, 40);
   // public Rect rectRight = new Rect(500, 190, 90, 80);

    public static class CameraStreamProcessor implements VisionProcessor, CameraStreamSource {
        private final AtomicReference<Bitmap> lastFrame =
                new AtomicReference<>(Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565));

        @Override
        public void init(int width, int height, CameraCalibration calibration) {
            lastFrame.set(Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565));
        }

        //
        @Override
        public Object processFrame(Mat frame, long captureTimeNanos) {
            Bitmap b = Bitmap.createBitmap(frame.width(), frame.height(), Bitmap.Config.RGB_565);

            Mat frameCopy = frame.clone(); //.clone() is required to make a copy otherwise doing this is the same pointer
            rectLeft =  new Rect(xCord, 190, 80, 90);
            Imgproc.rectangle(frameCopy, rectLeft, new Scalar(255, 0,0), 2);
//            Imgproc.rectangle(frame, rectLeft, new Scalar(255,0 ,0), 2);
            Utils.matToBitmap(frameCopy, b);
            lastFrame.set(b);
            return null;
        }

        //on draw frame is what is returned to the ds camera stream

        //Canvas is an android class that only exists to draw on Android streams
        @Override
        public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight,
                                float scaleBmpPxToCanvasPx, float scaleCanvasDensity,
                                Object userContext) {
        }

        @Override
        public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation) {
            continuation.dispatch(bitmapConsumer -> bitmapConsumer.accept(lastFrame.get()));
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        final CameraStreamProcessor processor = new CameraStreamProcessor();

        LocationID id = new LocationID();
        new VisionPortal.Builder()
                .addProcessor(processor)
                .addProcessor(id)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

        FtcDashboard.getInstance().startCameraStream(processor, 0);

        waitForStart();

        while(opModeInInit()) {
            telemetry.addData("Zone", id.getSelection());
            telemetry.update();

        }
        while (opModeIsActive()) {
            sleep(100L);

        }
    }
}