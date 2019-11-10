package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="BuildSide")


public class BuildSide extends LinearOpMode {


    public Robot snacktime = new Robot();    // Create a new instance of the robot


    public void runOpMode() {

        // Initialize and set up the robot's drive motors
        snacktime.initialize(hardwareMap);             // Initialize the robot


        telemetry.addData("Status:  ", "Initialized");
        telemetry.update();

        double distanceToTravel;
        double newPosition;


        // Wait for the start button to be pushed!
        waitForStart();

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncoders();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(50);

        snacktime.setDriveMotorPower(0.5, 0.5);
        while (opModeIsActive() && snacktime.getDriveEncoderCount() <= distanceToTravel) {
        }

        snacktime.setDriveMotorPower(0,0);

        newPosition = 0.9;
        snacktime.setServoRight(1.0-newPosition);
        snacktime.setServoLeft(newPosition);
        snacktime.resetEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(28);
        snacktime.setDriveMotorPower(-0.5, -0.5);
        while (opModeIsActive() && snacktime.getDriveEncoderCount() >= -distanceToTravel) {
        }
        snacktime.setDriveMotorPower(0,0);
    }


}