package Reference.RedStorm.LastYear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import redstorm.Robot.Robot;

/**
 *  Created by Steve Kocik
 */

@TeleOp(name="Teleop: Lets Figure out servo positions")
@Disabled


public class ServoPositionTest extends OpMode {

    public Robot robot = new Robot();

    /* Define variables */

    public boolean currStateRightBumper1 = false;
    public boolean prevStateRightBumper1 = false;
    public boolean currStateLeftBumper1  = false;
    public boolean prevStateLeftBumper1  = false;
    public boolean currStateRightTrigger = false;
    public boolean prevStateRightTrigger = false;
    public boolean currStateLeftTrigger  = false;
    public boolean prevStateLeftTrigger  = false;

    public boolean currStateY = false;
    public boolean prevStateY = false;
    public boolean currStateX = false;
    public boolean prevStateX = false;
    public boolean currStateB = false;
    public boolean prevStateB = false;
    public boolean currStateA = false;
    public boolean prevStateA = false;
    public boolean currStateDPadUp = false;
    public boolean currStateDPadDown = false;


    double newPosition = 0.32;

    @Override
    public void init() {

        robot.initialize(hardwareMap);

        telemetry.addData("Initialized", true);
        telemetry.update();
    }

    @Override
    public void start() {

        // robot.initializeServosTeleOp();
    }

    @Override
    public void loop() {

//contorols the servos on the lift arm
        currStateA = gamepad2.a;
        currStateB = gamepad2.b;
        currStateX = gamepad2.x;
        currStateY = gamepad2.y;

        // send the info back to driver station using telemetry function.
        telemetry.addData("currStateA", currStateA);
        telemetry.addData("currStateB", currStateB);

        if (currStateA) {
            newPosition = 0.7;
        }
        else if (currStateB) {
            newPosition = 0.8;
        }
        else if (currStateX) {
            newPosition = 0.25;
        }
        else if (currStateY) {
            newPosition = 0.35;
        }

        robot.setServoPosition(newPosition);
        telemetry.addData("Servo Position",String.valueOf(newPosition));
        telemetry.update();

    }

    @Override
    public void stop() {

    }


}