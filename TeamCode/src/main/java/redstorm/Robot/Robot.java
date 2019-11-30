package redstorm.Robot;

import android.graphics.Color;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

//import static redstorm.Constants.RobotConstants.ANDYMARK_NEVEREST_40_PULSES;
import static redstorm.Constants.RobotConstants.ANDYMARK_NEVEREST_40_PULSES;
import static redstorm.Constants.RobotConstants.ANDYMARK_NEVEREST_60_PULSES;
import static redstorm.Constants.RobotConstants.DRIVE_GEAR_RATIO;
import static redstorm.Constants.RobotConstants.DRIVE_WHEEL_CIRCUMFERENCE;

import static redstorm.Constants.RobotConstants.COLOR_SENSOR_SCALE_FACTOR;

public class Robot {

    public HardwareMap hwMap = null;
    public DcMotor rightDrive = null;
    public DcMotor leftDrive = null;

    public BNO055IMU imu = null;
    public ColorSensor colorSensor = null;

    public Servo servoRight = null;
    public Servo servoLeft = null;
    public Servo servoArm = null;
    public Servo servoClaw = null;

    public HardwareMap hardwareMap = null;


    public BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    public Orientation angles;
    public double integratedZAxis;
    public double iza_lastHeading = 0.0;
    public double iza_deltaHeading;
    public float iza_newHeading;
    public Orientation iza_angles;

    public Robot() {

    }

    /**
     * This method will initialize the robot
     *
     * @param ahwMap hardware map for the robot
     */
    public void initialize(HardwareMap ahwMap) {

        // Save reference to hardware map
        hwMap = ahwMap;

        // Define and initialize motors, the names here are what appears
        // in the configuration file on the Robot Controller/Driver Station
        leftDrive = hwMap.get(DcMotor.class, "left_motor");
        rightDrive = hwMap.get(DcMotor.class, "right_motor");

        // Defines and initializes the color sensor
        colorSensor = hwMap.get(ColorSensor.class, "sensor_color_distance");

        // Defines and initializes the servos
        servoRight = hwMap.get(Servo.class,"right_servo");
        servoLeft = hwMap.get(Servo.class,"left_servo");
        servoArm = hwMap.get(Servo.class,"servo_arm");
        servoClaw = hwMap.get(Servo.class,"servo_claw");


        // Defines the directions the motors will spin, typically motors that
        // are mounted on the left side are mounted in such a way as that the
        // the motor will spin backwards, so we set the default direction to
        // to be REVERSE so that the left motor will spin forwards with respect
        // to the ROBOT.
        //
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);


        // Set all motors to zero power
        rightDrive.setPower(0);
        leftDrive.setPower(0);

        // Set all motors to run without encoders.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Set servos to starting positions
        servoLeft.setPosition(0.0);
        servoRight.setPosition(1.0);
        servoArm.setPosition(0.035);
        servoClaw.setPosition(1.0);


        imu = hwMap.get(BNO055IMU.class, "imu");
        initializeIMU();

    }

    /**
     * Gets hue from Color Sensor
     * @return hue
     */
    public float getHue(){

        float hsv[] = {0F, 0F, 0F};
        convertToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsv);
        return hsv[0];
    }

    /**
     * Converts RGB to HSV
     * @param red component
     * @param green component
     * @param blue component
     * @param hsv returned
     */
    public void convertToHSV(int red, int green, int blue, float hsv[]){
        Color.RGBToHSV((int) (red * COLOR_SENSOR_SCALE_FACTOR),
                (int) (green * COLOR_SENSOR_SCALE_FACTOR),
                (int) (blue * COLOR_SENSOR_SCALE_FACTOR),
        hsv);

    }

    /**
     * This method will reset the encoder count of each motor to 0. It should be used before runWithEncoders
     * and getEncoderCount when strafing.
     */
    public void resetEncoders() {

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * This method will set the mode of all of the motors to run using encoder
     */
    public void runWithEncoders() {

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * This method will set the mode of all of the drive train motors to run without encoder
     */
    public void runWithoutEncoders() {

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    /**
     * This method will return COUNTS after it is calculated from distance
     *
     * @param distance the desired distance in inches the robot will travel
     * @return counts - the number of encoder counts the robot will travel that is equal
     * to the number of inches
     */
    public double calculateEncoderCounts(double distance) {

        double encoderCounts;
        double rotations;

        // Calculate the number of rotations for the given drive wheel size for the supplied distance
        rotations = (distance / DRIVE_WHEEL_CIRCUMFERENCE) * DRIVE_GEAR_RATIO;

        // Calculate the number of encoder counts for the given distance
        encoderCounts = ANDYMARK_NEVEREST_40_PULSES * rotations; //calculate encoder counts for given distance

        return encoderCounts;
    }

    /**
     * This method will return the average encoder count from the left and right drive motors
     * @return averageEncoderCount - the average encoder count from the left and right drive motors
     */
    public double getDriveEncoderCount() {
        double leftEncoderCount;
        double rightEncoderCount;
        double averageEncoderCount;

        leftEncoderCount = Math.abs(leftDrive.getCurrentPosition());      // Get the current encoder count for the left motor
        rightEncoderCount = Math.abs(rightDrive.getCurrentPosition());    // Get the current encoder count for the right motor

        averageEncoderCount = (leftEncoderCount + rightEncoderCount) / 2.0;  // Calculate the average

        return averageEncoderCount;

    }

    /**
     * This method will return the average encoder count from the left and right drive motors
     * @return averageEncoderCount - the average encoder count from the left and right drive motors
     */
    public double getLeftDriveEncoderCounts() {
        double leftEncoderCount;

        leftEncoderCount = Math.abs(leftDrive.getCurrentPosition());      // Get the current encoder count for the left motor

        return leftEncoderCount;
    }

    /**
     * This method will return the average encoder count from the left and right drive motors
     * @return averageEncoderCount - the average encoder count from the left and right drive motors
     */
    public double getRightDriveEncoderCounts() {
        double rightEncoderCount;
        rightEncoderCount = Math.abs(rightDrive.getCurrentPosition());    // Get the current encoder count for the right motor
        return rightEncoderCount;
    }

    public float getHeading() {
        float heading;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        heading = Math.abs(angles.firstAngle);

        return heading;
    }
    //
    /**
     * This method will set the power for the drive motors
     *
     * @param leftBackMotorPower power setting for the left back motor
     * @param rightBackMotorPower power setting for the right back motor
     */
    public void setDriveMotorPower(double leftBackMotorPower, double rightBackMotorPower){

        /* Set the motor powers */
        leftDrive.setPower(leftBackMotorPower);
        rightDrive.setPower(rightBackMotorPower);
    }

    /**
     * this method sets the position of the right servo
     * @param position
     */
    public void setFoundationServoRight(double position) {
        servoRight.setPosition(position);
    }

    /**
     * this method sets the position of the left servo
     * @param position
     */
    public void setFoundationServoLeft(double position) {
        servoLeft.setPosition(position);
    }

    public void setFoundationServosDown() {
        setFoundationServoLeft(0.0);
        setFoundationServoRight(0.0);
    }

    public void setFoundationServosUp() {
        setFoundationServoLeft(1.0);
        setFoundationServoRight(1.0);
    }

    public void setServoArmUp () { servoArm.setPosition(0.3);}
    public void setServoArmDown () { servoArm.setPosition(0.7);}

    public void setServoClawOpen () { servoClaw.setPosition(0.0);}
    public void setServoClawClosed () { servoClaw.setPosition(1.0);}


    public void initializeIMU() {
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.mode = BNO055IMU.SensorMode.IMU;
        imu.initialize(parameters);

    }
    /**
     * This will mimic the Modern Robotics getIntegratedZAxis method
     *
     * @return integratedZAxis - the integrated z axis
     */
    public double getIntegratedZAxis() {

        // This sets up the how we want the IMU to report data
        iza_angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        // Obtain the heading (Z-Axis)
        iza_newHeading = iza_angles.firstAngle;

        // Calculate the change in the heading from the previous heading
        iza_deltaHeading = iza_newHeading - iza_lastHeading;

        // Bosch IMU wraps at 180, so compensate for this
        if (iza_deltaHeading <= -180.0) {

            iza_deltaHeading += 360.0;
        }
        else if (iza_deltaHeading >= 180.0) {

            iza_deltaHeading -= 360;
        }

        // Calculate the integratedZAxis
        integratedZAxis += iza_deltaHeading;

        // Save the current heading for the next call to this method
        iza_lastHeading = iza_newHeading;

        return integratedZAxis;
    }
    }
