package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="FullRedSide")

public class FullRedSide extends LinearOpMode {

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
        snacktime.setServoClawOpen();


        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncoders();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(28.0);
        snacktime.setDriveMotorPower(1.0, 1.0, 1.0, 1.0);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ", snacktime.getSortedEncoderCount());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        snacktime.setServoClawClosed();
        Thread.sleep(500);    // Need some time to let the servos get into position
        snacktime.setServoArmMid();

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(6);   // Calculate the number of encoders counts for 30inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        // Having the robot travel 6 inches back
        snacktime.setDTMotorPosition(-(int)runToPosEncoderCount);
        snacktime.setDriveMotorPower(-1.0,-1.0,-1.0,-1.0);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0,0.0, 0.0, 0.0);

        telemetry.addData("Stopping", " Motors");
        telemetry.update();
        snacktime.resetEncoders();
        snacktime.runWithoutEncoders();

        //turn right 90 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(0.65, -0.65, 0.65, -0.65);
        while (opModeIsActive() &&
                snacktime.getHeading() < 80.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0, 0.0,0.0,0.0);


        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(50);   // Calculate the number of encoders counts for 50 inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        // Having the robot travel 50 inches
        snacktime.setDTMotorPosition((int)runToPosEncoderCount);
        snacktime.setDriveMotorPower(1.0,1.0,1.0,1.0);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0,0.0, 0.0, 0.0);

        telemetry.addData("Stopping", " Motors");
        telemetry.update();


        Thread.sleep( 500);    // Need some time to let the servos get into position

        snacktime.setServoArmDown();
        snacktime.setServoClawOpen();

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(72);   // Calculate the number of encoders counts for 60 inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        // Having the robot travel backwards 72 inches
        snacktime.setDTMotorPosition(-(int)runToPosEncoderCount);
        snacktime.setDriveMotorPower(1.0,1.0,1.0,1.0);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0,0,0,0);

        telemetry.addData("Stopping", " Motors");
        telemetry.update();
        snacktime.resetEncoders();
        snacktime.runWithoutEncoders();

        //turn left 90 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(-0.65, 0.65, -0.65, 0.65);
        while (opModeIsActive() &&
                snacktime.getHeading() < 85.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0,0,0,0);

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(8);   // Calculate the number of encoders counts for 30inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        // Having the robot travel 8 inches
        snacktime.setDTMotorPosition((int)runToPosEncoderCount);
        snacktime.setDriveMotorPower(1.0,1.0,1.0,1.0);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0,0.0, 0.0, 0.0);

        snacktime.setServoClawClosed();
        Thread.sleep(500);    // Need some time to let the servos get into position
        snacktime.setServoArmMid();

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(6);   // Calculate the number of encoders counts for 30inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        // Having the robot travel 6 inches back
        snacktime.setDTMotorPosition(-(int)runToPosEncoderCount);
        snacktime.setDriveMotorPower(-1.0,-1.0,-1.0,-1.0);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0,0,0,0);

        telemetry.addData("Stopping", " Motors");
        telemetry.update();
        snacktime.resetEncoders();
        snacktime.runWithoutEncoders();

        //turn right 74 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(0.70, -0.70, 0.70, -0.70);
        while (opModeIsActive() &&
                snacktime.getHeading() < 74.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();
        }

       snacktime.setDriveMotorPower(0,0,0,0);

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(100);   // Calculate the number of encoders counts for 60 inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        // Having the robot travel 100 inches
        snacktime.setDTMotorPosition((int)runToPosEncoderCount);
        snacktime.setDriveMotorPower(1.00,1.00,1.00,1.00);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0,0.0, 0.0, 0.0);

        telemetry.addData("Stopping", " Motors");
        telemetry.update();
        snacktime.resetEncoders();
        snacktime.runWithoutEncoders();

        //turn left 90 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(-0.70, 0.70, -0.70, 0.70);
        while (opModeIsActive() &&
                snacktime.getHeading() < 85.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0,0,0,0);

        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncodersRTP();                   // Tell the motors to run with encoders
        distanceToTravel = snacktime.calculateEncoderCounts(8);   // Calculate the number of encoders counts for 30inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        // Having the robot travel 8 inches
        snacktime.setDTMotorPosition((int)runToPosEncoderCount);
        snacktime.setDriveMotorPower(1.0,1.0,1.0,1.0);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < runToPosEncoderCount) {
            telemetry.addData("Distance To Travel: ", runToPosEncoderCount);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        // Putting servos down to latch onto foundation
        snacktime.setFoundationServosDown();
        telemetry.addData("Latching on to", " foundation");
        telemetry.update();
        Thread.sleep( 500);    // Need some time to let the servos get into position

        // Pulling the foundation backwards 45 in.
        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();

        distanceToTravel = snacktime.calculateEncoderCounts(45);   // Calculate the number of encoders counts for 45 inches

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);

        snacktime.setDriveMotorPower(0.75, 1.0, 0.75, 1.0);
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
        snacktime.setDriveMotorPower(1.00, -1.00, 1.00, -1.00);
        while (opModeIsActive() &&
                snacktime.getHeading() < 68.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();

        }
        snacktime.setDriveMotorPower(0.0,0.0,0.0,0.0);

        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();

        snacktime.setFoundationServosUp();
        //       snacktime.setFoundationServoLeft(1.0);
                telemetry.addData("Latching on to", " foundation");
        telemetry.update();
        Thread.sleep( 500);    // Need some time to let the servos get into position

        distanceToTravel = snacktime.calculateEncoderCounts(16);   // Calculate the number of encoders counts for 16 inches

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

        snacktime.resetEncoders();
        snacktime.runWithEncodersRTP();

        distanceToTravel = snacktime.calculateEncoderCounts(48);

        runToPosEncoderCount = snacktime.calculateRTPEncoderCounts(distanceToTravel);
        snacktime.setServoClawOpen();


        // Find the line and stop

        snacktime.setDriveMotorPower(-1.0,-1.0, -1.0, -1.0);
        snacktime.setDTMotorPosition(-(int)runToPosEncoderCount);
        telemetry.addData("Hue: ", snacktime.getHue());
        telemetry.update();

        while (opModeIsActive() && snacktime.getHue() > 10 && snacktime.getSortedEncoderCount() <= runToPosEncoderCount) {


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
        snacktime.setDriveMotorPower(1.0,1.0,1.0,1.0);
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

