package frc.robot.components;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel;


public class Motor {
    private SparkMax motor;
    private SparkAbsoluteEncoder encoder;

    private double objective_position = 0;

    private double speed = 0;
    private double divider = 10;

    // private double min_speed = 0.02;
    // private double max_speed = 1;
    // Put correct values here
    // min_speed should be the minimum speed that the motor can run at to actually move the mechanism


    public Motor()
    {
        motor = new SparkMax(8, SparkLowLevel.MotorType.kBrushless);
        encoder = motor.getAbsoluteEncoder();
    }

    public void updateData() {
        double current_position = encoder.getPosition();
        speed = Math.abs((objective_position-current_position)/divider);
        // speed = Math.min( Math.max(speed, max_speed), min_speed );
        SmartDashboard.putNumber("Encoder Position", encoder.getPosition());
        SmartDashboard.putNumber("Encoder Velocity", encoder.getVelocity());
        SmartDashboard.putNumber("Objective Position", objective_position);
        SmartDashboard.putNumber("Speed", speed);
        SmartDashboard.putNumber("Divider", divider);
        SmartDashboard.putNumber("Distance To Go", Math.abs(current_position - objective_position));
    }

    public void poll() {
        double current_position = encoder.getPosition();
        if( current_position < objective_position ) {
            motor.set(speed);
        }
        else if( current_position > objective_position ) {
            motor.set(speed*-1);
        }
        else {
            motor.set(0);
        }
    }

    public void goToPosition( double position ) {
        objective_position = position;
    }

    public void modifyPosition( double position ) {
        objective_position += position;
    }
}
