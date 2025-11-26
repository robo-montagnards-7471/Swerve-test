package frc.robot.components;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.spark.SparkLowLevel;


public class Motor {
    private SparkMax motor;
    private RelativeEncoder encoder;

    public Motor()
    {
        motor = new SparkMax(8, SparkLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();
    }

    public void setMotor( double speed ) {
        motor.set(speed);
        SmartDashboard.putNumber("Encoder Position", encoder.getPosition());
        SmartDashboard.putNumber("Encoder Velocity", encoder.getVelocity());
        SmartDashboard.putNumber("Speed", speed);
    }
}
