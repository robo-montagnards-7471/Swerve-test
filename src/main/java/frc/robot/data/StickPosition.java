package frc.robot.data;

/**
 * Simple data class to transport x/y position pairs
 * 
 * Can be used to represent the x/y of a control stick
 * OR the y position of stick with the x being resolved from a pair of analog triggers
 */
public record StickPosition(double x, double y) {}