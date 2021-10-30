package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.GameController;
import org.firstinspires.ftc.teamcode.botconfigs.QuadOmni;

// quad omni drive train tele op for PushBot
@TeleOp(name="QuadOmniTele", group="FreightFrenzy")
public class QuadOmniTele extends OpMode {

    // robot reference
    public QuadOmni bot;

    // controller reference
    public GameController pad;

    // speed settings
    public double[] speedSettings = {1, 0.5, 0.25};
    public int currentSpeed = 0;

    // init, get robot and controller
    @Override
    public void init() {
        bot = new QuadOmni(null, telemetry, hardwareMap);
        pad = new GameController(telemetry, new Gamepad[]{gamepad1, gamepad2});
    }

    // loop every frame
    @Override
    public void loop() {

        // update game controller input
        pad.update();

        // reorient gyro to current position upon A press
        if (pad.boolInputsThis[0][pad.buttonA]) {
            bot.driveTrain.gyro.startAngle = bot.driveTrain.gyro.getAngleRaw();
        }

        // toggle gyro compensate on B press
        if (pad.boolInputsThis[0][pad.buttonB]) {
            bot.driveTrain.gyroCompOn = !bot.driveTrain.gyroCompOn;
        }

        // increment speed selection on X press
        if (pad.boolInputsThis[0][pad.buttonX]) {
            currentSpeed++;

            // if selection is over limit, reset
            if (currentSpeed >= speedSettings.length){
                currentSpeed = 0;
            }
        }

        // begin spinning carousel wheel on bumper press
        if (pad.boolInputsThis[0][pad.bumperR]) {
            bot.carouselWheel.startSpinDuck(1);
        }
        if (pad.boolInputsThis[0][pad.bumperL]) {
            bot.carouselWheel.startSpinDuck(-1);
        }
        bot.carouselWheel.motor.loopMoveEncoders();

        // reverse intake direction on bumper press
        if (pad.boolInputsThis[1][pad.bumperR]) {
            bot.sharedDeposit.reverse();
        }
        if (pad.boolInputsThis[1][pad.bumperL]) {
            bot.sharedDeposit.toggleOn();
        }

        // set drive train power with controller x, y, and rotational input
        bot.driveTrain.run(
                pad.doubleInputs[0][pad.stickLX] * speedSettings[currentSpeed],
                pad.doubleInputs[0][pad.stickLY] * speedSettings[currentSpeed],
                pad.doubleInputs[0][pad.stickRX] * speedSettings[currentSpeed]);

        // set arm powers based on controller 2
        bot.sharedDeposit.run(pad.doubleInputs[1][pad.stickLY]);

        // give debug data
        telemetry.addData("gyro on", bot.driveTrain.gyroCompOn);

        // push telemetry debugging
        telemetry.update();
    }
}