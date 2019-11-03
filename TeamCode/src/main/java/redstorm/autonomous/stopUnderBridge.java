package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="Stop Under Bridge", group="distance")


public class stopUnderBridge extends LinearOpMode {


    public Robot robot = new Robot();    // Create a new instance of the robot!


    public void runOpMode () {

        // Initialize and set up the robot's drive motors
        robot.initialize(hardwareMap);             // Initialize the robot
        robot.resetEncoders();                     // Reset the encoder counts
        robot.runWithEncoders();                   // Tell the motors to run with encoders

        telemetry.addData("Status:  ", "Initialized");
        telemetry.update();

        double distanceToTravel = 0;


        // Wait for the start button to be pushed!
        waitForStart();

            distanceToTravel = robot.calculateEncoderCounts(24);

            robot.setDriveMotorPower(0.5, 0.5);

            while (opModeIsActive() && robot.getHue() < 100 && robot.getDriveEncoderCount() <= distanceToTravel) {

                robot.getHue();

            }

            robot.setDriveMotorPower(0,0);



    }


}