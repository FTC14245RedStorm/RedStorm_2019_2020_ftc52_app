package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="BuildSideV4Red")


public class BuildSideV4Red extends LinearOpMode {


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
        distanceToTravel = snacktime.calculateEncoderCounts(44);   // Calculate the number of encoders counts for 30inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        // Having the robot travel 44 inches
        snacktime.setDTMotorPosition((int)runToPosEncoderCount);
        snacktime.setDriveMotorPower(0.75,0.75,0.75,0.75);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
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

        // Pulling the foundation backwards 50 in.
        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();

        distanceToTravel = snacktime.calculateEncoderCounts(50);   // Calculate the number of encoders counts for 30inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        snacktime.setDriveMotorPower(1.0, 1.0, 1.0, 1.0);
        snacktime.setDTMotorPosition(-(int) runToPosEncoderCount);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
            if (snacktime.getSortedEncoderCount() > runToPosEncoderCount-5) {
                snacktime.setDriveMotorPower(1.0,1.0,1.0,1.0);
            }

        }

        snacktime.setDriveMotorPower(0,0, 0, 0);

        snacktime.resetEncoders();
        snacktime.runWithEncoders();

        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(0.5, -0.5, 0.5, -0.5);
        while (opModeIsActive() &&
                snacktime.getHeading() < 60.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();

        }
        snacktime.setDriveMotorPower(0.0,0.0,0.0,0.0);

        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();

        distanceToTravel = snacktime.calculateEncoderCounts(14);   // Calculate the number of encoders counts for 30inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        snacktime.setDriveMotorPower(1.0, 1.0, 1.0, 1.0);
        snacktime.setDTMotorPosition((int) runToPosEncoderCount);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
            if (snacktime.getSortedEncoderCount() > runToPosEncoderCount) {
                snacktime.setDriveMotorPower(1.0,1.0,1.0,1.0);
            }

        }



        //         Let go of the foundation

        snacktime.setFoundationServosUp();
 //       snacktime.setFoundationServoLeft(1.0);
        telemetry.addData("Latching on to", " foundation");
        telemetry.update();
        Thread.sleep( 500);    // Need some time to let the servos get into position


        // find the line and stop

        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();

        distanceToTravel = snacktime.calculateEncoderCounts(48);

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);
        snacktime.setDriveMotorPower(1.0,1.0, 1.0, 1.0);
        snacktime.setDTMotorPosition(-(int)runToPosEncoderCount);
        telemetry.addData("Hue: ", snacktime.getHue());
        telemetry.update();

        while (opModeIsActive() && snacktime.getHue() < 150 && snacktime.getSortedEncoderCount() <= runToPosEncoderCount) {


            telemetry.addData("Hue: ", snacktime.getHue());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0,0,0,0);

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(3);   // Calculate the number of encoders counts for 30inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        // Having the robot travel 3 inches
        snacktime.setDTMotorPosition((int)runToPosEncoderCount);
        snacktime.setDriveMotorPower(0.5,0.5,0.5,0.5);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0,0.0, 0.0, 0.0);

        telemetry.addData("Stopping", " Motors");
        telemetry.update();

    }


}