package frc.robot.components;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

import com.revrobotics.spark.SparkLowLevel;


public class Motor {
    private SparkMax motor;
    private RelativeEncoder encoder;

    private double objective_position = 0;

    private double speed = 0;
    private double divider = 20;

    private double optimal_divider = 20;
    private double optimal_precision = 1;

    private double last_position = 0;

    public Motor()
    {
        motor = new SparkMax(8, SparkLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();
        last_position = encoder.getPosition();
    }

    public void updateData() {
        double current_position = encoder.getPosition();
        speed = Math.abs((objective_position-current_position)/divider);
        SmartDashboard.putNumber("Encoder Position", encoder.getPosition());
        SmartDashboard.putNumber("Encoder Velocity", encoder.getVelocity());
        SmartDashboard.putNumber("Objective Position", objective_position);
        SmartDashboard.putNumber("Speed", speed);
        SmartDashboard.putNumber("Divider", divider);
        SmartDashboard.putNumber("Optimal Divider", optimal_divider);
        SmartDashboard.putNumber("Optimal Precision", optimal_precision);
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
        if( current_position == last_position ) {
            double current_precision = Math.abs(objective_position - current_position);
            if( current_precision < optimal_precision ) {
                divider = optimal_divider;
                optimal_precision = current_precision;
            }
        }
        last_position = current_position;
    }

    public void goToPosition( double position ) {
        objective_position = position;
    }

    public void incrementDivider() {
        divider++;
    }

    public void toggleTarget() {
        objective_position = Math.abs( 25-objective_position );
    }
}
