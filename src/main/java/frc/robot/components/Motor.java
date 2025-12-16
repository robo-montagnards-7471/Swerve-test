package frc.robot.components;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;
import java.nio.file.Path;

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
        motor = new SparkMax(7, SparkLowLevel.MotorType.kBrushless);
        encoder = motor.getAbsoluteEncoder();
    }

    public void updateData() {

        SmartDashboard.putNumber("Encoder Position", encoder.getPosition());
        SmartDashboard.putNumber("Encoder Velocity", encoder.getVelocity());
        SmartDashboard.putNumber("Objective Position", objective_position);
        SmartDashboard.putNumber("Speed", speed);
        SmartDashboard.putNumber("Divider", divider);
        SmartDashboard.putNumber("Distance To Go", Math.abs(objective_position-encoder.getPosition()));
    }

    public void poll() {
        double current_position = encoder.getPosition();
        double distance_to_go = Math.abs(objective_position-current_position);
        double modifier = 1;

        if( distance_to_go > 0.50 )
            distance_to_go = 1.0 - distance_to_go;
            modifier = -1;
        
        speed = distance_to_go*12/divider;

        if( current_position < objective_position ) {
            motor.set(speed*modifier*-1);
        }
        else if( current_position > objective_position ) {
            motor.set(speed*modifier);
        }
        else {
            motor.set(0);
        }
    }

    public void goToPosition( double position ) {
        objective_position = position;
        SmartDashboard.putNumber("Position Request", position);
    }
}
