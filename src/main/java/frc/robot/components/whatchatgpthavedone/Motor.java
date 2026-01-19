package frc.robot.components.whatchatgpthavedone;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Motor {

    private SparkMax motor;
    private SparkAbsoluteEncoder encoder;

    private PIDController rotatingPid;

    private double objectivePosition = 0.0;
    private boolean driveInverted = false;

    public Motor() {
        motor = new SparkMax(7, SparkLowLevel.MotorType.kBrushless);
        encoder = motor.getAbsoluteEncoder();

        rotatingPid = new PIDController(1.3, 1.14, 0.09);
        rotatingPid.enableContinuousInput(0.0, 1.0);
    }

    /* -------------------- UTILS -------------------- */

    private double wrapError(double target, double current) {
        double error = target - current;
        error = error - Math.round(error); // [-0.5 ; +0.5]
        return error;
    }

    private double optimizeTarget(double target, double current) {

        // Option normale
        double errorNormal = wrapError(target, current);

        // Option flip 180°
        double flippedTarget = (target + 0.5) % 1.0;
        double errorFlipped = wrapError(flippedTarget, current);

        if (Math.abs(errorFlipped) < Math.abs(errorNormal)) {
            driveInverted = true;
            return flippedTarget;
        } else {
            driveInverted = false;
            return target;
        }
    }

    /* -------------------- MAIN LOOP -------------------- */

    public void poll() {
        double currentPosition = encoder.getPosition();

        double optimizedTarget = optimizeTarget(objectivePosition, currentPosition);

        double speed = rotatingPid.calculate(currentPosition, optimizedTarget);

        motor.set(speed);

        SmartDashboard.putNumber("Encoder Position", currentPosition);
        SmartDashboard.putNumber("Objective Position", objectivePosition);
        SmartDashboard.putNumber("Optimized Target", optimizedTarget);
        SmartDashboard.putBoolean("Drive Inverted", driveInverted);
    }

    /* -------------------- API -------------------- */

    public void goToPosition(double position) {
        objectivePosition = position % 1.0;
        if (objectivePosition < 0) objectivePosition += 1.0;
        SmartDashboard.putNumber("Position Request", objectivePosition);
    }

    public boolean isDriveInverted() {
        return driveInverted;
    }
}
