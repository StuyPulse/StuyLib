package com.stuypulse.stuylib.math;

import edu.wpi.first.math.geometry.Rotation2d;

public interface Angle {


    /*********
     * UTILS *
     *********/

    public static long hashCode(Angle angle) {
        return Double.hashCode(angle.toRadians());
    }

    public static boolean equals(Angle lhs, Object rhs) {
        if (lhs == rhs) return true;
        if (!(rhs instanceof Angle)) return false;
        return lhs.toRadians() == ((Angle) rhs).toRadians();
    }

    public static String toString(Angle angle) {
        StringBuilder out = new StringBuilder();
        out.append("Angle(")
           .append(SLMath.round(angle.toDegrees(), 5)).append("deg)");

        return out.toString();
    }

    /*************
     * CONSTANTS *
     *************/

    public static final Angle kZero = fromDegrees(0);
    public static final Angle k30deg = fromDegrees(30);
    public static final Angle k45deg = fromDegrees(45);
    public static final Angle k60deg = fromDegrees(60);
    public static final Angle k90deg = fromDegrees(90);
    public static final Angle k120deg = fromDegrees(120);
    public static final Angle k135deg = fromDegrees(135);
    public static final Angle k150deg = fromDegrees(150);
    public static final Angle k180deg = fromDegrees(180);

    /** An angle that will return cos() = 0, sin() = 0, toRadians() = 0 */
    public static final Angle kNull = new ImmutableAngle();

    /*********
     * CACHE *
     *********/

    static class AngleCache {
        static final Angle cache[];

        static {
            cache = new Angle[360];
            for (int degrees = 0; degrees < cache.length; ++degrees)
                cache[degrees] = new ImmutableAngle(Math.toRadians(degrees));
        }

        static final Angle fromDegrees(int degrees) {
            return cache[degrees - 360 * (int) Math.floor(degrees / 360.0)];
        }

        private AngleCache() {}
    }

    /***********
     * FACTORY *
     **********/

    public static Angle fromRadians(double radians) {
        return new ImmutableAngle(radians);
    }

    public static Angle fromDegrees(double degrees) {
        return fromRadians(Math.toRadians(degrees));
    }

    public static Angle fromDegrees(int degrees) {
        return AngleCache.fromDegrees(degrees);
    }

    public static Angle fromRotation2d(Rotation2d rotation) {
        return fromRadians(rotation.getRadians());
    }

    public static Angle fromVector(double x, double y) {
        return fromRadians(Math.atan2(y, x));
    }

    public static Angle fromVector(Vector2D vector) {
        return fromVector(vector.x, vector.y);
    }

    /*********
     * UNITS *
     *********/

    public double toRadians();

    public default double toDegrees() {
        return Math.toDegrees(toRadians());
    }

    public default double toRotations() {
        return Math.toDegrees(toRadians());
    }

    public default Rotation2d getRotation2d() {
        return new Rotation2d(toRadians());
    }

    /****************
     * TRIGONOMETRY *
     ****************/
    
    public default double sin() {
        return Math.sin(toRadians());
    }

    public default double cos() { 
        return Math.cos(toRadians());
    }

    public default double tan() {
        return Math.tan(toRadians());
    }

    public default Vector2D getVector() {
        return new Vector2D(cos(), sin());
    }

    /********
     * MATH *
     ********/

    public default Angle add(Angle rhs) {
        return fromRadians(toRadians() + rhs.toRadians());
    }

    public default Angle sub(Angle rhs) {
        return fromRadians(toRadians() - rhs.toRadians());
    }

    public default Angle mul(double scalar) {
        return fromRadians(toRadians() * scalar);
    }

    public default Angle div(double scalar) {
        return fromRadians(toRadians() / scalar);
    }

    public default Angle negative() {
        return fromRadians(-toRadians());
    }

    public default Angle opposite() {
        return fromRadians(Math.PI + toRadians()); 
    }

}
