package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="Blue, Load Side, Short Distance")

public class loadBlueShort extends LinearOpMode {

    public Robot snacktime = new Robot();    // Create a new instance of the robot


    public void runOpMode() throws InterruptedException {

        // Initialize and set up the robot's drive motors
        snacktime.initialize(hardwareMap);             // Initialize the robot


        telemetry.addData("Status:  ", "Initialized");
        telemetry.update();

        double distanceToTravel;
        double startHeading;


        // Wait for the start button to be pushed!
        waitForStart();

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncoders();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(3.0);
        snacktime.setDriveMotorPower(0.5, 0.5, 0.5, 0.5);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        //turn left 90 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(-0.5, 0.5, -0.5, 0.5);
        while (opModeIsActive() &&
                snacktime.getHeading() < 90.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        // Find the line and stop

        snacktime.resetEncoders();
        snacktime.runWithEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(60.0);
        snacktime.setDriveMotorPower(0.5, 0.5, 0.5, 0.5);
        telemetry.addData("Hue: ", snacktime.getHue());
        telemetry.update();

        while (opModeIsActive() && snacktime.getHue() < 150.0 && snacktime.getSortedEncoderCount() <= distanceToTravel) {


            telemetry.addData("Hue: ", snacktime.getHue());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0,0, 0, 0);
    }
}