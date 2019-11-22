package redstorm.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import redstorm.Robot.Robot;


@Autonomous (name="Encoder Test", group="Test")
/**
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





        // While the autonomous period is still active AND the robot has not reached the number
        // of encoder counts to travel 24 inches
        while(opModeIsActive() && robot.getDriveEncoderCount() < 5000) {

            telemetry.addData("Status ", "opModeIsActive");


            robot.setDriveMotorPower(0.5, 0.50);   // Set power to 50%
            telemetry.addData("Left Drive Encoder Counts", "(%.0f)",robot.getLeftDriveEncoderCounts());
            telemetry.addData("Right Front Drive Encoder Counts", "(%.0f)", robot.getRightDriveEncoderCounts());

            robot.setDriveMotorPower(0.0,0.0);

            telemetry.update();
        }

        Thread.sleep(500);
        robot.resetEncoders();
        robot.runWithEncoders();


        while( opModeIsActive()  && robot.getDriveEncoderCount() < 5000) {




            robot.setDriveMotorPower(-0.5, -0.50);   // Set power to 50%
            telemetry.addData("Left Drive Encoder Counts", "(%.0f)", robot.getLeftDriveEncoderCounts());
            telemetry.addData("Right Front Drive Encoder Counts", "(%.0f)", robot.getRightDriveEncoderCounts());


            telemetry.update();
        }




        robot.setDriveMotorPower(0.0,0.0);         // Motors stop
    }
}