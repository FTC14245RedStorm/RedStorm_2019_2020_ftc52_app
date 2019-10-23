package RoboRaiders.examples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "LEDServo Test", group = "Hubbot")
@Disabled


public class LEDServo extends LinearOpMode {

    Servo ledServo;  // Hardware Device Object
    Blink

    public boolean currStateY = false;
    public boolean prevStateY = false;
    public boolean currStateX = false;
    public boolean prevStateX = false;
    public double servoPos = 0.0;

    @Override
    public void runOpMode() {

        // get a reference to our Light Sensor object.
        ledServo = hardwareMap.get(Servo.class, "ledServo");

        ledServo.setPosition(-0.99);   //initial position  doc says send servo 0.2525 to 0.7475

        // wait for the start button to be pressed.
        waitForStart();

        // while the op mode is active, loop and read the light levels.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {

            currStateX = gamepad1.x;
            currStateY = gamepad1.y;


            // send the info back to driver station using telemetry function.
            telemetry.addData("Press X", "to increment");
            telemetry.addData("Press Y", "to decrement");

            servoPos = ledServo.getPosition();

            if (currStateX) {
                servoPos = servoPos + 0.02 ;
            }
            else if (currStateY) {
                servoPos = servoPos - 0.02;
            }

            telemetry.addData("servoPos","(%.2f)",servoPos);

            ledServo.setPosition(servoPos);

            telemetry.update();
        }
    }
}

