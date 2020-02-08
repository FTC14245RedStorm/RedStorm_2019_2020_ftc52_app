package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="BuildSideV3Blue")


public class BuildSideV3Blue extends LinearOpMode {


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
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateRTPEncoderCounts(30.0);

        // Having the robot travel 30 inches
        snacktime.setDTMotorPosition((int)distanceToTravel);
        snacktime.setDriveMotorPower(1,1,1,1);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0,0.0, 0.0, 0.0);

        telemetry.addData("Stopping", " Motors");
        telemetry.update();

        // Putting servos down to latch onto foundation
        snacktime.setFoundationServosDown();
        telemetry.addData("Latching on to", " foundation");
        telemetry.update();
        Thread.sleep( 500);    // Need some time to let the servos get into position

        // Pulling the foundation backwards 20 in.
        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();
        distanceToTravel = snacktime.calculateRTPEncoderCounts(20.0);
        snacktime.setDriveMotorPower(1, 1, 1, 1);
        snacktime.setDTMotorPosition(-(int) distanceToTravel);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0,0, 0, 0);
        // Let go of the foundation

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders

        //turn left backwards 90 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(0.5, 1, 0.5, 1);
        while (opModeIsActive() &&
                snacktime.getHeading() < 90.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

            // Go forwards 20 inches
            snacktime.resetEncoders();
            snacktime.runWithEncodersRTP();
            distanceToTravel = snacktime.calculateRTPEncoderCounts(20);
            snacktime.setDriveMotorPower(1.0, 1.0, 1.0, 1.0);
            snacktime.setDTMotorPosition((int)distanceToTravel);
            while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
                telemetry.addData("Distance To Travel: ", distanceToTravel);
                telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
                telemetry.update();

            }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        // Putting servos up to let go of foundation
        snacktime.setFoundationServosUp();
        telemetry.addData("Letting Go of Foundation ", " foundation");
        telemetry.update();
        Thread.sleep( 500);    // Need some time to let the servos get into position

        // find the line and stop

        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();
        distanceToTravel = snacktime.calculateRTPEncoderCounts(40.0);
        snacktime.setDriveMotorPower(1, 1, 1, 1);
        snacktime.setDTMotorPosition(-(int)distanceToTravel);
        telemetry.addData("Hue: ", snacktime.getHue());
        telemetry.update();

        while (opModeIsActive() && snacktime.getHue() < 150 && snacktime.getSortedEncoderCount() <= distanceToTravel) {


            telemetry.addData("Hue: ", snacktime.getHue());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0.0,0.0, 0.0, 0.0);


    }


}