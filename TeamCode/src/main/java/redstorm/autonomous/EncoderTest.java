package redstorm.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;


// @Autonomous (name="Encoder Test", group="Test")

/*
 * Created by Steve Kocik as a sample for RedStorm to build off of...
 */


public class EncoderTest extends LinearOpMode {

    Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException{


        // Initialize and set up the robot's drive motors
        robot.initialize(hardwareMap);             // Initialize the robot
        robot.resetEncoders();                     // Reset the encoder counts
        robot.runWithEncoders();                   // Tell the motors to run with encoders

        telemetry.addData("Status ", "Initialized");
        telemetry.update();

        // Wait for the start button to be pushed
        waitForStart();




        double encCounts[] = new double[4];

        // While the autonomous period is still active AND the robot has not reached the number
        // of encoder counts to travel 24 inches
        while(opModeIsActive()) {
            telemetry.addData("Status ", "opModeIsActive");


            robot.setDriveMotorPower(0.5, 0.50,0.5,0.5);   // Set power to 50%
            encCounts = robot.getDriveEncoderCounts();
            telemetry.addData("Back Left Drive Encoder Counts","(%.0f)",encCounts[0]);
            telemetry.addData("Back Right Drive Encoder Counts","(%.0f)",encCounts[1]);
            telemetry.addData("Front Left Drive Encoder Counts","(%.0f)",encCounts[2]);
            telemetry.addData("Front Right Drive Encoder Counts","(%.0f)",encCounts[3]);

            robot.setDriveMotorPower(0.0,0.0,0.0,0.0);

            telemetry.update();
        }

    }
}
