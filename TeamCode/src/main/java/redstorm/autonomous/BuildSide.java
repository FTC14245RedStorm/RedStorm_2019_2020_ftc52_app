package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="Stop Under Bridge", group="distance")


public class BuildSide extends LinearOpMode {


    public Robot robot = new Robot();    // Create a new instance of the robot


    public void runOpMode() {

        // Initialize and set up the robot's drive motors
        robot.initialize(hardwareMap);             // Initialize the robot


        telemetry.addData("Status:  ", "Initialized");
        telemetry.update();

        double distanceToTravel;


        // Wait for the start button to be pushed!
        waitForStart();

    }


}