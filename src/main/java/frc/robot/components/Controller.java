package frc.robot.components;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.data.StickPosition;
import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    private final XboxController hardwareController;


    public Controller() {
        this.hardwareController = new XboxController(0);

        // Register to dashboard
        // FIXME: Default Sendable not pushing POV (D-Pad) value
    }

    /**
     * Update user desired states from non analog inputs based on state changes since last check
     */
    public void poll() {}

    /**
     * Used for single stick piloting
     */
    public StickPosition getDriveStickPosition() {
        return new StickPosition(-1d * hardwareController.getLeftX(), -1d * hardwareController.getLeftY());
    }

    /**
     * Raw access to the left stick poistion
     */
    public StickPosition getLeftStickPosition() {
        return new StickPosition(hardwareController.getLeftX(), hardwareController.getLeftY());
    }


    /**
     * Raw access to the right stick poistion
     */
    public StickPosition getRightStickPosition() {
        return new StickPosition(hardwareController.getRightX(), hardwareController.getRightY());
    }

    public boolean getAButton() {
        return hardwareController.getAButtonPressed();
    }

    public boolean getBButton() {
        return hardwareController.getBButtonPressed();
    }

    public boolean getXButton() {
        return hardwareController.getXButtonPressed();
    }

    public boolean getYButton() {
        return hardwareController.getYButtonPressed();
    }
}
