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
    public DcMotor backLeftDrive = null;
    public DcMotor backRightDrive = null;
    public DcMotor frontLeftDrive = null;
    public DcMotor frontRightDrive = null;
    public DcMotor tapeMotor = null;
    public DcMotor liftMotor = null;

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
        frontLeftDrive = hwMap.get(DcMotor.class, "front_left_motor");
        frontRightDrive = hwMap.get(DcMotor.class, "front_right_motor");
        backLeftDrive = hwMap.get(DcMotor.class, "back_left_motor");
        backRightDrive = hwMap.get(DcMotor.class, "back_right_motor");
        tapeMotor = hwMap.get(DcMotor.class, "tapeMotor");
        liftMotor = hwMap.get(DcMotor.class, "liftMotor");

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
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        tapeMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);

        // Set all motors to run without encoders.
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Set servos to starting positions
        setFoundationServosUp();
        setServoClawClosed();
        setServoArmUp();


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

        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * This method will set the mode of all of the motors to run using encoder
     */
    public void runWithEncoders() {

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * This method will set the mode of all of the drive train motors to run without encoder
     */
    public void runWithoutEncoders() {

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * This method sets the motors to run with encoders and in RUN_TO_POSITION mode
     */

    public void runWithEncodersRTP() {

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * This method will set the target position for the drive train motors.
     * @param Position the target position
     */
    public void setDTMotorPosition(int Position){

        frontLeftDrive.setTargetPosition(Position);
        frontRightDrive.setTargetPosition(Position);
        backLeftDrive.setTargetPosition(Position);
        backLeftDrive.setTargetPosition(Position);

    }

    /**
     * This method will calculate a run to position encodercount for the drive train motors.
     * If the robot is to travel backwards, specify a negative for teh deltaEncoderCount.
     * @param deltaEncoderCount is the average encoder count.
     * @return
     */
    public double calculateRTPEncoderCounts(double deltaEncoderCount) {

        double currentDTEncoderCount;

        currentDTEncoderCount = getSortedEncoderCount();

        return (currentDTEncoderCount + deltaEncoderCount);

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
    public double getSortedEncoderCount() {
        double[] encoderCounts = new double[4];
        encoderCounts = getDriveEncoderCounts();
        double temp = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = i+1; j < 4; j++) {
                if (encoderCounts[i] <= encoderCounts[j]) {
                    //do nothing
                }

                else {
                    temp = encoderCounts[i];
                    encoderCounts[i] = encoderCounts[j];
                    encoderCounts[j] = temp;

                }

            }


        }



        return (encoderCounts[1] + encoderCounts[2]) / 2.0;

    }
    /**
     * This method will return the average encoder count from the left and right drive motors
     * @return averageEncoderCount - the average encoder count from the left and right drive motors
     */
    public double getBackLeftDriveEncoderCounts() {
        double leftEncoderCount;

        leftEncoderCount = Math.abs(backLeftDrive.getCurrentPosition());      // Get the current encoder count for the left motor

        return leftEncoderCount;
    }

    /**
     * This method will return the average encoder count from the left and right drive motors
     * @return averageEncoderCount - the average encoder count from the left and right drive motors
     */
    public double getBackRightDriveEncoderCounts() {
        double rightEncoderCount;
        rightEncoderCount = Math.abs(backRightDrive.getCurrentPosition());    // Get the current encoder count for the right motor
        return rightEncoderCount;
    }

    /**
     * This method will return the average encoder count from the left and right drive motors
     * @return averageEncoderCount - the average encoder count from the left and right drive motors
     */
    public double getFrontRightDriveEncoderCounts() {
        double rightEncoderCount;
        rightEncoderCount = Math.abs(frontRightDrive.getCurrentPosition());    // Get the current encoder count for the right motor
        return rightEncoderCount;
    }

    /**
     * This method will return the average encoder count from the left and right drive motors
     * @return averageEncoderCount - the average encoder count from the left and right drive motors
     */
    public double getFrontLeftDriveEncoderCounts() {
        double rightEncoderCount;
        rightEncoderCount = Math.abs(frontLeftDrive.getCurrentPosition());    // Get the current encoder count for the right motor
        return rightEncoderCount;
    }

    /**
     * This method will return the drive motors
     * @return encoderCounts - returns encoder counts backLeft, backRight, frontLeft, frontRight
     */
    public double[] getDriveEncoderCounts() {
        double[] encoderCounts = new double[4];
        encoderCounts[0] = Math.abs(backLeftDrive.getCurrentPosition());
        encoderCounts[1] = Math.abs(backRightDrive.getCurrentPosition());
        encoderCounts[2] = Math.abs(frontLeftDrive.getCurrentPosition());
        encoderCounts[3] = Math.abs(frontRightDrive.getCurrentPosition());

        return encoderCounts;
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
     * @param backLeftMotorPower power setting for the back left motor
     * @param backRightMotorPower power setting for the back right motor
     * @param frontLeftMotorPower power setting for the front left motor
     * @param frontRightMotorPower power setting for the front right motor
     */
    public void setDriveMotorPower(double backLeftMotorPower, double backRightMotorPower, double frontLeftMotorPower, double frontRightMotorPower){

        /* Set the motor powers */
        backLeftDrive.setPower(backLeftMotorPower);
        backRightDrive.setPower(backRightMotorPower);
        frontLeftDrive.setPower(frontLeftMotorPower);
        frontRightDrive.setPower(frontRightMotorPower);
    }

    /**
     * This method will set the power for the tape motors
     *
     * @param tapeMotorPower power setting for the left back motor
     */
    public void setTapeMotorPower(double tapeMotorPower)    {

        /* Set the motor powers */
        tapeMotor.setPower(tapeMotorPower);
    }

    /**
     *
     * This method will set the power for the lift motor
     *
     * @param liftMotorPower
     */

    public void setLiftMotorPower(double liftMotorPower) {

        // Set lift motor power

        liftMotor.setPower(liftMotorPower);
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
        setFoundationServoRight(1.0);
    }

    public void setFoundationServosUp() {
        setFoundationServoLeft(1.0);
        setFoundationServoRight(0.0);
    }


    public void setServoArmUp () { servoArm.setPosition(0.2);}
    public void setServoArmDown () { servoArm.setPosition(0.75);}

    public void setServoClawOpen () { servoClaw.setPosition(1.0);}
    public void setServoClawClosed () { servoClaw.setPosition(0.0);}



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
