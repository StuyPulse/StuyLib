package edu.stuylib.math;

/**
 * NumberDrag is a class that lets you smooth out
 * a stream of numbers in an easy way. You can 
 * think of it like a paint brush smoother on many
 * art applications. This could be helpful when 
 * applied to controller input as it lets the driver
 * be more percise, without worring about shaking
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NumberDrag {

    // Values needed for number drag
    private static double mValue;
    private static double mDrag;

    /**
     * Initialize NumberDrag with custom
     * drag values. The higher the value, 
     * the more smooth the outputs are,
     * the lower the value, the faster it
     * converges
     * 
     * @param drag drag (drag > 0.5)
     */
    public NumberDrag(double drag) {
        mValue = 0;
        mDrag = Math.max(0.5 + 1.0/256.0, drag);
    }

    /**
     * Get drag value
     * @return drag value
     */
    public double getDrag() {
        return mDrag;
    }

    /**
     * Get current value of number drag
     * @return value
     */
    public double getValue() {
        return mValue;
    }

    /**
     * Set drag value
     * @param drag new value
     */
    public void setDrag(double drag) {
        mDrag = drag;
    }

    /**
     * Set value
     * @param value new value
     */
    public void setValue(double value) {
        mValue = value;
    }

    /**
     * Smooth out input with past values,
     * this creates a smoothed out plot
     * over many iterations, and can help
     * with controller input if it needs
     * percision
     * 
     * @param input requested value to converge on
     * @return dragged number
     */
    public double drag(double input) {
        mValue *= mDrag;
        mValue += input;
        mValue /= mDrag + 1;
        return mValue;
    }
}