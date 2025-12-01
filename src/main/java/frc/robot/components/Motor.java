package frc.robot.components;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

import com.revrobotics.spark.SparkLowLevel;


public class Motor {
    private SparkMax motor;
    private RelativeEncoder encoder;

    private double objective_speed = 0;
    private double current_power = 0;
    private double speed_modifier = 0.5; // set to reasonable value

    public Motor()
    {
        motor = new SparkMax(8, SparkLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();
    }

    public void updateData() {
        SmartDashboard.putNumber("Encoder Position", encoder.getPosition());
        SmartDashboard.putNumber("Encoder Velocity", encoder.getVelocity());
        SmartDashboard.putNumber("Current Power", current_power);
    }

    public void poll() {
        double current_speed = encoder.getVelocity();
        double speed_error = objective_speed - current_speed;
        current_power += speed_error * speed_modifier;
        motor.set(current_power);
    }

    public void setSpeed( double speed ) {
        motor.set(speed);
    }
}
