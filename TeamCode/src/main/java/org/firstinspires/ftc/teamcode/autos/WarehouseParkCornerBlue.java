package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.botconfigs.QuadOmni;

@Autonomous(name="WarehouseParkCornerBlue", group="FreightFrenzy")
public class WarehouseParkCornerBlue extends LinearOpMode {

    // robot reference
    public QuadOmni bot;

    // which alliance the robot is on
    public boolean isBlue = true;

    // run the autonomous
    @Override
    public void runOpMode() {

        // initialize robot
        bot = new QuadOmni(this, telemetry, hardwareMap);
        bot.driveTrain.gyroCompOn = false;

        // wait for press play
        waitForStart();

        // move backward 2 feet (24 inches)
        bot.driveTrain.moveEncoders(0, 24 * (isBlue ? -1 : 1), 0, 0.2);
        bot.driveTrain.normalizeGyro(0, 0.2);

        // move sideways 2 feet (24 inches)
        bot.driveTrain.moveEncoders(-24, 0, 0, 0.2);
        bot.driveTrain.normalizeGyro(0, 0.2);
    }
}
