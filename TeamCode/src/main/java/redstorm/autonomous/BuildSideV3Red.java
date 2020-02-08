package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="BuildSideV2Red")


public class BuildSideV3Red extends LinearOpMode {


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
        distanceToTravel = snacktime.calculateEncoderCounts(10.0);

        // Having the robot travel 10 inches
        snacktime.setDriveMotorPower(0.75, 0.75, 0.75, 0.75);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() <= distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0,0.0, 0.0, 0.0);

        telemetry.addData("Stopping", " Motors");
        telemetry.update();


        //turn right 10 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(0.30, -0.30, 0.30, -0.30);
        while (opModeIsActive() &&
                snacktime.getHeading() < 10.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();
        }

            snacktime.resetEncoders();                     // Reset the encoder counts
            snacktime.runWithEncoders();                   // Tell the motors to run with encoders
            distanceToTravel = snacktime.calculateEncoderCounts(21.0);

            // Having the robot travel 21 inches
            snacktime.setDriveMotorPower(0.75, 0.75, 0.75, 0.75);
            while (opModeIsActive() && snacktime.getSortedEncoderCount() <= distanceToTravel) {
                telemetry.addData("Distance To Travel: ", distanceToTravel);
                telemetry.addData("Encoder Count: ", snacktime.getSortedEncoderCount());
                telemetry.update();
            }

            snacktime.setDriveMotorPower(0.0,0.0, 0.0, 0.0);

        // Putting servos down to latch onto foundation
        snacktime.setFoundationServosDown();
        telemetry.addData("Latching on to", " foundation");
        telemetry.update();
        Thread.sleep( 500);    // Need some time to let the servos get into position



        // Pulling the foundation backwards 33 in.
        snacktime.resetEncoders();
        snacktime.runWithEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(33.0);
        snacktime.setDriveMotorPower(-0.85, -0.65, -0.85, -0.65);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0,0, 0, 0);
        // Let go of the foundation

        snacktime.setFoundationServosUp();
        telemetry.addData("Letting Go of Foundation", " foundation");
        telemetry.update();
        Thread.sleep( 500);    // Need some time to let the servos get into position

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);
        snacktime.resetEncoders();                     // Reset the encoder counts
        snacktime.runWithEncoders();                   // Tell the motors to run with encoders

        // Go backwards 1 inches
        snacktime.resetEncoders();
        snacktime.runWithEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(1.0);
        snacktime.setDriveMotorPower(-1.00, -1.00, -1.00, -1.00);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0.0, 0.0,0.0,0.0);

        //turn left 60 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(-0.30, 0.30,-0.30,-0.30);
        while (opModeIsActive() &&
                snacktime.getHeading() < 60.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

            // Go fowards 18 inches
            snacktime.resetEncoders();
            snacktime.runWithEncoders();
            distanceToTravel = snacktime.calculateEncoderCounts(18.0);
            snacktime.setDriveMotorPower(1.00, 1.00, 1.00,1.00);
            while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
                telemetry.addData("Distance To Travel: ", distanceToTravel);
                telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
                telemetry.update();

            }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

            //turn right 70 degrees
            snacktime.initializeIMU();
            startHeading = snacktime.getHeading();
            snacktime.setDriveMotorPower(0.30, -0.30, 0.30, -0.30);
            while (opModeIsActive() &&
                    snacktime.getHeading() < 70.0) {
                telemetry.addData("Starting heading: ", "%5.2f", startHeading);
                telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
                telemetry.update();
            }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0,0.0);

        // Go fowards 25 inches get by foundation
        snacktime.resetEncoders();
        snacktime.runWithEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(25.0);
        snacktime.setDriveMotorPower(1.00, 1.00,1.00, 1.00);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        //turn right 55 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(0.30, -0.30, 0.30, -0.30);
        while (opModeIsActive() &&
                snacktime.getHeading() < 65.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        // Go fowards 12 inches
        snacktime.resetEncoders();
        snacktime.runWithEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(12.0);
        snacktime.setDriveMotorPower(1.00, 1.00, 1.00, 1.00);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ",snacktime.getSortedEncoderCount());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        //turn right 70 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(0.50, -0.50, 0.50, -0.50);
        while (opModeIsActive() &&
                snacktime.getHeading() < 70.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        // Go fowards 42 inches
        snacktime.resetEncoders();
        snacktime.runWithEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(42.0);
        snacktime.setDriveMotorPower(1.00, 1.00, 1.00, 1.00);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ", snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0,0.0);

        // Go backwards 5 inches
        snacktime.resetEncoders();
        snacktime.runWithEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(5.0);
        snacktime.setDriveMotorPower(-1.00, -1.00, -1.00, -1.00);
        while (opModeIsActive() && snacktime.getSortedEncoderCount() < distanceToTravel) {
            telemetry.addData("Distance To Travel: ", distanceToTravel);
            telemetry.addData("Encoder Count: ", snacktime.getSortedEncoderCount());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        //turn right 33 degrees
        snacktime.initializeIMU();
        startHeading = snacktime.getHeading();
        snacktime.setDriveMotorPower(0.50, -0.50, 0.50, -0.50);
        while (opModeIsActive() &&
                snacktime.getHeading() < 34.0) {
            telemetry.addData("Starting heading: ", "%5.2f", startHeading);
            telemetry.addData("Current heading: ", "%5.2f", snacktime.getHeading());
            telemetry.update();
        }

        snacktime.setDriveMotorPower(0.0, 0.0, 0.0, 0.0);

        // find the line and stop

        snacktime.resetEncoders();
        snacktime.runWithEncoders();
        distanceToTravel = snacktime.calculateEncoderCounts(46.0);
        snacktime.setDriveMotorPower(1.00, 1.00, 1.00, 1.00);
        telemetry.addData("Hue: ", snacktime.getHue());
        telemetry.update();

        while (opModeIsActive() && snacktime.getHue() < 150 && snacktime.getSortedEncoderCount() <= distanceToTravel) {


            telemetry.addData("Hue: ", snacktime.getHue());
            telemetry.update();

        }

        snacktime.setDriveMotorPower(0,0, 0, 0);


    }


}