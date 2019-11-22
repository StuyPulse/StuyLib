package com.stuypulse.stuylib.network.limelight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.stuypulse.stuylib.network.limelight.Limelight;

/**
 * Do bluebox filtering here until limelight updates their code. Just call
 * hasValidTarget() to filter out errors
 * 
 * @author Kevin (k1029384756c@gmail.com)
 */

class Filters {

    // Toggle for posting to SmartDashboard
    public static boolean POST_TO_SMART_DASHBOARD = true;

    /**
     * @param targetHeightThreshold Height threshold for target
     * @param minRatio              Min ratio for the blue aspect ratio
     * @param maxRatio              Max ratio for the blue aspect ratio
     * @param angleThreshold        maximum skew the target can have
     * @return Whether or not the limelight has a target in view
     */
    public static boolean hasValidTarget(double targetHeightThreshold, double minRatio, double maxRatio,
            double angleThreshold) {
        return hasAnyTarget() 
             & hasValidHeight(targetHeightThreshold) 
             & hasValidBlueAspectRatio(minRatio, maxRatio)
             & hasValidBlueOrientation(angleThreshold);
    }

    // Not final incase user wants
    // to change them at runtime
    public static double DEFAULT_TARGET_HEIGHT_THRESHOLD = 6;
    public static double DEFAULT_MIN_ASPECT_RATIO = 1.2;
    public static double DEFAULT_MAX_ASPECT_RATIO = 3.3;
    public static double DEFAULT_ANGLE_THRESHOLD = 25;

    /**
     * @return Whether or not the limelight has a target in view
     */
    public static boolean hasValidTarget() {
        return hasValidTarget( 
                DEFAULT_TARGET_HEIGHT_THRESHOLD, 
                DEFAULT_MIN_ASPECT_RATIO, 
                DEFAULT_MAX_ASPECT_RATIO,
                DEFAULT_ANGLE_THRESHOLD);
    }

    /**
     * Decides if a target shows up on limelight screen
     * 
     * @return If it has any target
     */
    public static boolean hasAnyTarget() {
        boolean validTarget = Limelight.hasValidTarget();

        if (POST_TO_SMART_DASHBOARD) {
            SmartDashboard.putBoolean("Valid Target", validTarget);
        }

        return validTarget;
    }

    /**
     * @param targetHeightThreshold Height threshold for target
     * @return If the target fits the height threshold
     */
    public static boolean hasValidHeight(double targetHeightThreshold) {
        boolean validHeight = Limelight.getTargetYAngle() < targetHeightThreshold;

        if (POST_TO_SMART_DASHBOARD) {
            SmartDashboard.putBoolean("Valid Height", validHeight);
        }

        return validHeight;
    }

    /**
     * The blue aspect ratio is the ratio of the width to height of the rotated
     * rectangle.
     * 
     * @param minRatio Min ratio for the blue aspect ratio
     * @param maxRatio Max ratio for the blue aspect ratio
     * @return If the blue aspect ratio fits the thresholds
     */
    public static boolean hasValidBlueAspectRatio(double minRatio, double maxRatio) {
        double aspectRatio = Limelight.getHorizontalSidelength() / Limelight.getVerticalSidelength();
        boolean validRatio = aspectRatio > minRatio && aspectRatio < maxRatio;

        if (POST_TO_SMART_DASHBOARD) {
            SmartDashboard.putBoolean("Valid Ratio", validRatio);
            SmartDashboard.putNumber("Aspect Ratio", aspectRatio);
        }

        return validRatio;
    }

    /**
     * @param angleThreshold maximum skew the target can have
     * @return if the skew is less than the maximum skew
     */
    public static boolean hasValidBlueOrientation(double angleThreshold) {
        double skew = Math.abs(Limelight.getTargetSkew());
        boolean validOrientation = Math.min(skew, 90.0 - skew) <= angleThreshold;

        if (POST_TO_SMART_DASHBOARD) {
            SmartDashboard.putBoolean("Valid Skew", validOrientation);
            SmartDashboard.putNumber("Skew Value", Math.min(skew, 90.0 - skew));
        }

        return validOrientation;
    }
}