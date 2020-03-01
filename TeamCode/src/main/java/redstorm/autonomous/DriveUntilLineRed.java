package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="DriveUntilLineRed")

public class DriveUntilLineRed extends LinearOpMode {

    public Robot snacktime = new Robot();    // Create a new instance of the robot

    public void runOpMode() throws InterruptedException {

        // Initialize and set up the robot's drive motors
        snacktime.initialize(hardwareMap);             // Initialize the robot


        telemetry.addData("Status:  ", "Initialized");
        telemetry.update();

        double distanceToTravel;
        double startHeading;
        double runToPosEncoderCount;

        // Wait for the start button to be pushed!
        waitForStart();

        snacktime.setServoArmDown();

        Thread.sleep(25000);

        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();

        distanceToTravel = snacktime.calculateEncoderCounts(120);

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);
        snacktime.setDriveMotorPower(0.75, 0.75, 0.75, 0.75);
        snacktime.setDTMotorPosition((int) runToPosEncoderCount);
        telemetry.addData("Hue: ", snacktime.getHue());
        telemetry.update();

        while (opModeIsActive() && snacktime.getHue() > 10 && snacktime.getSortedEncoderCount() <= runToPosEncoderCount) {


            telemetry.addData("Hue: ", snacktime.getHue());
            telemetry.update();

        }
    }
}