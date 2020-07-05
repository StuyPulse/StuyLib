package com.stuypulse.stuylib.math;

/**
 * A Vector2D class that stores x and y position data. It is made to work with the StuyLib Angle
 * class and be easy to use. It is a standard Vector2D class with all of the functions that you
 * would expect.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public final class Vector2D {

    public static final Vector2D kOrigin = new Vector2D(0,0);
    public static final Vector2D kIHat = new Vector2D(1,0);
    public static final Vector2D kJHat = new Vector2D(0,1);
    
    /**
     * The x position of the Vector2D
     */
    public final double x;

    /**
     * The y position of the Vector2D
     */
    public final double y;

    /**
     * Make a Vector2D with two numbers
     *
     * @param x the x axis of the vector
     * @param y the y axis of the vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Make a Vector2D with an array of 2 numbers
     *
     * @param axis an array or varargs for x and y
     */
    public Vector2D(double... axis) {
        if(axis.length != 2) {
            throw new IllegalArgumentException("axis must be of size 2");
        }

        this.x = axis[0];
        this.y = axis[1];
    }

    /**
     * Get x, y coordinates as an array
     *
     * @return array of x and y
     */
    public double[] getArray() {
        return new double[] { x, y };
    }

    /**
     * Check if two Vector2D's equal each other
     *
     * @param other other Vector2D
     * @return if the two Vector2Ds are equal
     */
    public boolean equals(Vector2D other) {
        return this.x == other.x && this.y == other.y;
    }

    /**
     * Get the distance between two Vector2Ds
     *
     * @param other other Vector2D
     * @return the distance between the two Vector2Ds
     */
    public double distance(Vector2D other) {
        return Math.hypot(other.x - this.x, other.y - this.y);
    }

    /**
     * Get the distance from 0, 0
     *
     * @return distance from 0, 0
     */
    public double distance() {
        return Math.hypot(this.x, this.y);
    }

    /**
     * Get the magnitude of the vector (same as distance from 0, 0)
     *
     * @return magnitude of the vector
     */
    public double magnitude() {
        return this.distance();
    }

    /**
     * Get the angle of the Vector2D around 0
     *
     * @return the angle of the Vector2D around 0
     */
    public Angle getAngle() {
        return Angle.fromRadians(Math.atan2(this.y, this.x));
    }

    /**
     * Rotate Vector2D around point
     *
     * @param angle  amount to rotate
     * @param origin point to rotate around
     * @return result of rotation
     */
    public Vector2D rotate(Angle angle, Vector2D origin) {
        final Vector2D point = this.sub(origin);
        final Vector2D out = new Vector2D(
                point.x * angle.cos() - point.y * angle.sin(),
                point.y * angle.cos() + point.x * angle.sin());

        return origin.add(out);
    }

    /**
     * Rotate Vector2D around origin
     *
     * @param angle amount to rotate
     * @return result of rotation
     */
    public Vector2D rotate(Angle angle) {
        return rotate(angle, new Vector2D(0, 0));
    }

    /**
     * Add two Vector2Ds
     *
     * @param other the other Vector2D
     * @return sum of the two Vector2Ds
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Subtract two Vector2Ds
     *
     * @param other the other Vector2D
     * @return difference between the two Vector2Ds
     */
    public Vector2D sub(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Multiply two Vector2Ds
     *
     * @param other the other Vector2D
     * @return product of the two Vector2Ds
     */
    public Vector2D mul(Vector2D other) {
        return new Vector2D(this.x * other.x, this.y * other.y);
    }

    /**
     * Divide two Vector2Ds
     *
     * @param other the other Vector2D
     * @return division of the two Vector2Ds
     */
    public Vector2D div(Vector2D other) {
        return new Vector2D(this.x / other.x, this.y / other.y);
    }

    /**
     * Multiply the x and y of the Vector2D by a certain amount
     *
     * @param multiplier amount to multiply the Vector2D by
     * @return the multiplied Vector2D
     */
    public Vector2D mul(double multiplier) {
        return new Vector2D(this.x * multiplier, this.y * multiplier);
    }

    /**
     * Divide the x and y of the Vector2D by a certain amount
     *
     * @param divisor amount to divide the Vector2D by
     * @return the divided Vector2D
     */
    public Vector2D div(double divisor) {
        return new Vector2D(this.x / divisor, this.y / divisor);
    }

    /**
     * Calculate the vector dot product of the two vectors
     *
     * @param other the other Vector2D
     * @return the result of the dot product
     */
    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Normalize the Vector2D so that the point lands on the unit circle
     *
     * @return the normalized Vector2D
     */
    public Vector2D normalize() {
        return this.div(this.distance());
    }

    /**
     * Format the angle into a string
     *
     * @return formatted string value
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
