package redstorm.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import redstorm.Robot.Robot;

@TeleOp(name = "ServosTeleop", group = "Teleop")

public class ServosTeleop extends OpMode{
    public Robot snacktime = new Robot();

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

        double newPosition = 0.0;

        if (gamepad1.a) {
            newPosition = 0.0;
        } else if (gamepad1.b) {
            newPosition = 0.5;
        } else if (gamepad1.x) {
            newPosition = -0.5;
        }
        snacktime.setServoRight(newPosition);
        snacktime.setServoLeft(newPosition);
    }

}
