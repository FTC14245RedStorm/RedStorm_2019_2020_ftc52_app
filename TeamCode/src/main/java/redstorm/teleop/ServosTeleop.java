package redstorm.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import redstorm.Robot.Robot;

@TeleOp(name = "ServosTeleop", group = "Teleop")

public class ServosTeleop extends OpMode{
    public Robot snacktime = new Robot();
    double newPosition = 0.0;

    @Override
    public void init(){

        snacktime.initialize(hardwareMap);
        telemetry.addData("Say", "Hello Drvier");
    }

    @Override
    public void init_loop(){
    }

    @Override
    public void start(){
    }

    @Override
    public void loop(){



        if (gamepad1.a) {
            newPosition = 0.9;
        }  else if (gamepad1.x) {
            newPosition = 0.0;
        }
        snacktime.setFoundationServoRight(1.0-newPosition);
        snacktime.setFoundationServoLeft(newPosition);

        double left;
        double right;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;

        snacktime.setDriveMotorPower(left, right);

    }

}
