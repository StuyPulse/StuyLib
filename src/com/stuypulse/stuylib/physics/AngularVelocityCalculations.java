package com.stuypulse.stuylib.physics;

public class AngularVelocityCalculations {
    public static double getLinearVelocityFromRPM(double RPM) {
        return 2 * Math.PI / 60 * RPM;
    }

    public static double getRPMFromLinearVelocity(double LV, double r) {
        return 60 / (2 * Math.PI * r) * LV;
    }
}
