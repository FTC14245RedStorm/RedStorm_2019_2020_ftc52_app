package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="BuildSideV4RedTape")


public class BuildSideV4RedTape extends LinearOpMode {


    public Robot snacktime = new Robot();    // Create a new instance of the robot


    public void runOpMode() throws InterruptedException {

        // Initialize and set up the robot's drive motors
        snacktime.initialize(hardwareMap);             // Initialize the robot


        telemetry.addData("Status:  ", "Initialized");
        telemetry.update();

        double distanceToTravel;
        double runToPosEncoderCount;
        double startHeading;

        // Wait for the start button to be pushed!
        waitForStart();

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(40);   // Calculate the number of encoders counts for 30inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        // Having the robot travel 40 inches
        snacktime.setDTMotorPosition((int) runToPosEncoderCount);
        snacktime.setDriveMotorPower(0.75, 0.75, 0.75, 0.75);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ", snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        telemetry.addData("Stopping", " Motors");
        telemetry.update();

        // Putting servos down to latch onto foundation
        snacktime.setFoundationServosDown();
        telemetry.addData("Latching on to", " foundation");
        telemetry.update();
        Thread.sleep(500);    // Need some time to let the servos get into position

        // Pulling the foundation backwards 45 in.
        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();

        distanceToTravel = snacktime.calculateEncoderCounts(45);   // Calculate the number of encoders counts for 45 inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        snacktime.setDriveMotorPower(0.75, 1.0, 0.75, 1.0);
        snacktime.setDTMotorPosition(-(int) runToPosEncoderCount);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ", snacktime.getSortedEncoderCount());
            telemetry.update();
            if (snacktime.getSortedEncoderCount() > runToPosEncoderCount - 5) {
                snacktime.setDriveMotorPower(1.0, 1.0, 1.0, 1.0);
            }

        }

        snacktime.setDriveMotorPower(0, 0, 0, 0);

        snacktime.resetEncoders();
        snacktime.runWithEncoders();

        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(0.5, -0.5, 0.5, -0.5);
        while (opModeIsActive() &&
                snacktime.getHeading() < 68.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();

        }
        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();

        snacktime.setFoundationServosUp();
        //       snacktime.setFoundationServoLeft(1.0);
        telemetry.addData("Unlatching from", " foundation");
        telemetry.update();
        Thread.sleep(500);    // Need some time to let the servos get into position

        distanceToTravel = snacktime.calculateEncoderCounts(16);   // Calculate the number of encoders counts for 16 inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        snacktime.setDriveMotorPower(1.0, 1.0, 1.0, 1.0);
        snacktime.setDTMotorPosition((int) runToPosEncoderCount);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ", snacktime.getSortedEncoderCount());
            telemetry.update();
            if (snacktime.getSortedEncoderCount() > runToPosEncoderCount) {
                snacktime.setDriveMotorPower(1.0, 1.0, 1.0, 1.0);
            }

        }

        distanceToTravel = snacktime.calculateEncoderCounts(6);   // Calculate the number of encoders counts for 16 inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        snacktime.setDriveMotorPower(0.5, 0.5, 0.5, 0.5);
        snacktime.setDTMotorPosition(-(int) runToPosEncoderCount);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ", snacktime.getSortedEncoderCount());
            telemetry.update();
            if (snacktime.getSortedEncoderCount() > runToPosEncoderCount) {
                snacktime.setDriveMotorPower(1.0, 1.0, 1.0, 1.0);
            }

            snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

            snacktime.resetEncoders();
            snacktime.runWithEncoders();

            snacktime.initializeIMU();
            startHeading = snacktime.getHeading();
            snacktime.setDriveMotorPower(0.5, -0.5, 0.5, -0.5);
            while (opModeIsActive() &&
                    snacktime.getHeading() < 180.0) {
                telemetry.addData("Starting heading: ", "%5.2f", startHeading);
                telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
                telemetry.update();

            }
            snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

            snacktime.setTapeMotorPower(1.0);
            Thread.sleep(3000);
            snacktime.setTapeMotorPower(0.0);

        }


    }
}