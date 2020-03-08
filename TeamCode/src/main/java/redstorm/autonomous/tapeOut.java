package redstorm.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;

@Autonomous(name="Tape Out Delayed")

public class tapeOut extends LinearOpMode {

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

        snacktime.setTapeMotorPower(1.0);
        Thread.sleep(1750);
        snacktime.setTapeMotorPower(0.0);

    }
}