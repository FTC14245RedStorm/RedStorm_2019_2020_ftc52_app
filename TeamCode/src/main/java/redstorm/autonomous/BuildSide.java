package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="BuildSide")


public class BuildSide extends LinearOpMode {


    public Robot snacktime = new Robot();    // Create a new instance of the robot


    public void runOpMode() throws InterruptedException {

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
        distanceToTravel = snacktime.calculateEncoderCounts(40.0);

        // Having the robot travel 50 inches
        snacktime.setDriveMotorPower(0.5, 0.5);
        while (opModeIsActive() && snacktime.getDriveEncoderCount() <= distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getDriveEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0,0.0);

        telemetry.addData("Stopping", " Motors");
        telemetry.update();



        // Putting servos down to latch onto foundation
        newPosition = 0.9;
        snacktime.setServoRight(1.0-newPosition);
        snacktime.setServoLeft(newPosition);
        telemetry.addData("Latching on to", " foundation");
        telemetry.addData("ServoRight: ", 1.0-newPosition);
        telemetry.addData("ServoLeft: ", newPosition);
        telemetry.update();
        Thread.sleep( 500);    // Need some time to let the servos get into position



        // Pulling the foundation backwards to reach the corner
        snacktime.resetEncoders();
        snacktime.runWithEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(33.0);
        snacktime.setDriveMotorPower(-0.5, -0.5);
        while (opModeIsActive() && snacktime.getDriveEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getDriveEncoderCount());
            telemetry.update();

        }
        snacktime.setDriveMotorPower(0,0);
        // Let go of the foundation
        newPosition = 0.0;
        snacktime.setServoRight(1.0-newPosition);
        snacktime.setServoLeft(newPosition);
        telemetry.addData("Latching on to", " foundation");
        telemetry.addData("ServoRight: ", 1.0-newPosition);
        telemetry.addData("ServoLeft: ", newPosition);
        telemetry.update();
        Thread.sleep( 500);    // Need some time to let the servos get into position


        //turn right 90 degrees
        snacktime.initializeIMU();
        double startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(0.5, -0.5);
        while (opModeIsActive() &&
                Math.abs(snacktime.getHeading()) < 90.0) {
            telemetry.addData("Starting heading: ","%5.2f",startHeading);
            telemetry.addData("Current heading: ","%5.2f",snacktime.getHeading());
            telemetry.update();
        }
        snacktime.setDriveMotorPower(0.0, 0.0);
        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncoders();                   // Tell the motors to run with encoders

        distanceToTravel = snacktime.calculateEncoderCounts(50.0);

        // find the line and stop
        snacktime.setDriveMotorPower(0.5, 0.5);
        telemetry.addData("Hue: ", snacktime.getHue());
        telemetry.update();

        while (opModeIsActive() && snacktime.getHue() < 150 && snacktime.getDriveEncoderCount() <= distanceToTravel) {


            telemetry.addData("Hue: ", snacktime.getHue());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0,0);


    }


}