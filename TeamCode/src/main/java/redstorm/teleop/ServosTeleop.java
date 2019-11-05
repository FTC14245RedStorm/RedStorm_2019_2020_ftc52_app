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
        double left;
        double right;

        left = gamepad1.a;
        right = gamepad1.b;

        snacktime.servoLeft.setPosition(left);
        snacktime.servoRight.setPosition(right);
    }

}
