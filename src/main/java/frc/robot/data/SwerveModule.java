package frc.robot.data;

public class SwerveModule {
    private double target_speed;
    private double target_rotation;

    private double max_velocity;

    
    public SwerveModule() {
        target_speed = 0;
        target_rotation = 0;
        max_velocity = 0; // TODO: Put correct value
        // TODO: Put correct declaration
    }

    public void poll() {
        // TODO: Use velcity encoder for speed, not direct 1
        // TODO: put correct rotation objective method from tests (other branches)
    }

    public void setSpeed( double value ) {
        target_speed = value;
    }

    public void setRotation( double value ) {
        target_rotation = value;
    }
}
