package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="Drive 2 Feet")

public class driveTwoFeet extends LinearOpMode {

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

        snacktime.resetEncoders();
        snacktime.runWithEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(24.0);
        snacktime.setDriveMotorPower(1.0, 1.0);
        while (opModeIsActive() && snacktime.getDriveEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getDriveEncoderCount());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0,0);
    }
}