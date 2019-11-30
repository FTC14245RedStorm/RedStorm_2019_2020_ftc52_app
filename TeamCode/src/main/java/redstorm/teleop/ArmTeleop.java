package redstorm.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import redstorm.Robot.Robot;

@TeleOp(name="Arm TeleOp", group="TeleOp")

public class ArmTeleop extends OpMode{
public Robot snacktime = new Robot()
    public void init() {


        initialize(hardwareMap);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");
    }


    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    public void loop() {

    }


    public HardwareMap hwMap = null;

    public BNO055IMU imu = null;

    public Servo servoArm = null;
    public Servo servoClaw = null;

    public HardwareMap hardwareMap = null;


    public BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    public void initialize(HardwareMap ahwMap) {

        // Save reference to hardware map
        hwMap = ahwMap;

        servoArm = hwMap.get(Servo.class,"servo_arm");
        servoClaw = hwMap.get(Servo.class,"servo_claw");

        //Set servos to starting positions

        servoArm.setPosition(1.0);
        servoClaw.setPosition(1.0);


        imu = hwMap.get(BNO055IMU.class, "imu");

        if (gamepad1.a) {
            newPosition = 0.9;
        }  else if (gamepad1.x) {
            newPosition = 0.0;
        }
        snacktime.setServoRight(1.0-newPosition);
        snacktime.setServoLeft(newPosition);

        double left;
        double right;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;

        snacktime.setDriveMotorPower(left, right);

    }

}



