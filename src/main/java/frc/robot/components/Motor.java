package frc.robot.components;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel;


public class Motor {
    private SparkMax motor;
    private SparkAbsoluteEncoder encoder;
    private ProfiledPIDController rotating_PidController;

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
        double distance_to_go = objective_position-current_position;
        double modifier = 1;

        // // If the motor has to turn all the way around, turn other way
        // // 1. if start position is under 0.25 and objective is over 0.75
        // // 2. if start position is over 0.75 and objective is under 0.25
        // if( (current_position < 0.25 && objective_position > 0.75) || (current_position > 0.75 && objective_position < 0.25) ) {
        //     if( current_position < 0.25 ) {
        //         distance_to_go = (objective_position-1) - current_position;
        //     }
        //     else {
        //         distance_to_go = (objective_position+1) - current_position;
        //     }
        // }
        
        speed = distance_to_go*12/divider;

        motor.set(speed);

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


    /**
     * Get the closest angle between the given angles.
     */
    private static double closestAngle(double a, double b)
    {
            // get direction
            double dir = b%360.0 - a%360.0;

            // convert from -360 to 360 to -180 to 180
            if (Math.abs(dir) > 180.0)
            {
                    dir = -(Math.signum(dir) * 360.0) + dir;
            }
            return dir/360;
    }
}
