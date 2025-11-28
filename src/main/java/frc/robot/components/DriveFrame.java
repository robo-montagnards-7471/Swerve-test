package frc.robot.components;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.data.StickPosition;

import java.lang.Math;

import com.revrobotics.spark.SparkLowLevel;


public class DriveFrame {
    double x_speed; // Forward/backward
    double y_speed; // Left/right
    double rot_speed; // Rotation
    
    Translation2d front_left_motor_position;
    Translation2d front_right_motor_position;
    Translation2d back_left_motor_position;
    Translation2d back_right_motor_position;

    SwerveModuleState front_left_module_state;
    SwerveModuleState front_right_module_state;
    SwerveModuleState back_left_module_state;
    SwerveModuleState back_right_module_state;

    SwerveDriveKinematics kinematic_drive;

    public DriveFrame() {

        front_left_motor_position = new Translation2d(0.5, 0.5); // 0.5 front 0.5 left
        front_right_motor_position = new Translation2d(0.5, -0.5); // 0.5 front 0.5 right
        back_left_motor_position = new Translation2d(-0.5, 0.5); // 0.5 back 0.5 left
        back_right_motor_position = new Translation2d(-0.5, -0.5); // 0.5 back 0.5 right

        kinematic_drive = new SwerveDriveKinematics(
            front_left_motor_position,
            front_right_motor_position,
            back_left_motor_position,
            back_right_motor_position
        );
        
        ChassisSpeeds speeds = new ChassisSpeeds(0, 0, 0);
        SwerveModuleState[] module_states = kinematic_drive.toSwerveModuleStates(speeds);

        front_left_module_state = module_states[0];
        front_right_module_state = module_states[1];
        back_left_module_state = module_states[2];
        back_right_module_state = module_states[3];


        SmartDashboard.putData("Speeds", builder -> {
            builder.addDoubleProperty("Speed X", () -> getXSpeed(), null);
            builder.addDoubleProperty("Speed Y", () -> getYSpeed(), null);
            builder.addDoubleProperty("Speed Rot", () -> getRotSpeed(), null);
        });

        SmartDashboard.putData("Module States", builder -> {
            builder.addDoubleProperty("Front Left Angle", () -> front_left_module_state.angle.getDegrees(), null);
            builder.addDoubleProperty("Front Left Speed", () -> front_left_module_state.speedMetersPerSecond, null);
            builder.addDoubleProperty("Front Right Angle", () -> front_right_module_state.angle.getDegrees(), null);
            builder.addDoubleProperty("Front Right Speed", () -> front_right_module_state.speedMetersPerSecond, null);
            builder.addDoubleProperty("Back Left Angle", () -> back_left_module_state.angle.getDegrees(), null);
            builder.addDoubleProperty("Back Left Speed", () -> back_left_module_state.speedMetersPerSecond, null);
            builder.addDoubleProperty("Back Right Angle", () -> back_right_module_state.angle.getDegrees(), null);
            builder.addDoubleProperty("Back Right Speed", () -> back_right_module_state.speedMetersPerSecond, null);
        });
    }


    public void drive( StickPosition left_stick, StickPosition right_stick ) {
        x_speed = left_stick.y(); // Forward/backward
        y_speed = left_stick.x(); // Left/right
        rot_speed = right_stick.x(); // Rotation

        ChassisSpeeds speeds = new ChassisSpeeds(x_speed, y_speed, rot_speed);
        SwerveModuleState[] module_states = kinematic_drive.toSwerveModuleStates(speeds);

        front_left_module_state = module_states[0];
        front_right_module_state = module_states[1];
        back_left_module_state = module_states[2];
        back_right_module_state = module_states[3];
    }

    private double getXSpeed() {
        return x_speed;
    }

    private double getYSpeed() {
        return y_speed;
    }

    private double getRotSpeed() {
        return rot_speed;
    }
}
