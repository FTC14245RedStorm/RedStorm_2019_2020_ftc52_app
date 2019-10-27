package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import RedStorm.Robot.Robot;

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

        double wallDistanceToTravel = 0;
        double distanceFromWall;
        double wallDistanceTraveled = 0;
        String remember = new String();


        // Wait for the start button to be pushed!
        waitForStart();


            robot.resetEncoders();
            robot.runWithEncoders();

            wallDistanceToTravel = robot.calculateEncoderCounts(72);

            robot.setDriveMotorPower(0.5, 0.5);

            while (opModeIsActive() && ) {


                telemetry.addData("distance traveled", "(%.2f)",robot.getLeftDistance());
                telemetry.update();

                robot.setDriveMotorPower(0.5,0.5);



            }

            robot.setDriveMotorPower(0,0);



    }


}