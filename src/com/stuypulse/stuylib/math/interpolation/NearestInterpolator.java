package com.stuypulse.stuylib.math.interpolation;

import java.util.function.BiFunction;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.math.Vector2D;

/**
 * A nearest interpolator takes the y-value of the reference point closest 
 * on the x-axis. 
 * 
 * There are three interpolation "biases": left, center, right. The center "bias" 
 * will simply use x-axis distance to find the nearest reference point to the x-value
 * being interpolated for. However, the left and right "biases" will only use reference 
 * points to the left and right, respectively. 
 * 
 * Because left and right biases reject points, they may not have outputs at certain x-values.
 * 
 * @author Myles Pasetsky
 */
public class NearestInterpolator implements Interpolator {

    /**
     * A bias describes how the interpolator will accept or reject
     * points. 
     */
    public static enum Bias {
        // use points to the left 
        kLeft((x, point) -> x >= point.x), 

        // use any point
        kCenter((x, point) -> true), 

        // use points to the right
        kRight((x, point) -> x <= point.x);
 
        private BiFunction<Double, Vector2D, Boolean> mAcceptor;

        private Bias(BiFunction<Double, Vector2D, Boolean> acceptor) {
            mAcceptor = acceptor;
        }

        private boolean accept(double x, Vector2D point) {
            return mAcceptor.apply(x, point);
        }
    }

    /** array of reference points */
    private final Vector2D[] mPoints;

    /** bias mode to use */
    private final Bias mBias;

    /**
     * Creates a nearest interpolator given a bias mode and reference points.
     * 
     * There must be at least one reference point.
     * 
     * @param bias bias mode
     * @param points reference points
     */
    public NearestInterpolator(Bias bias, Vector2D... points) {
        if (points.length < 1) {
            throw new IllegalArgumentException("Nearest Interpolator requires 1 point");
        }

        mPoints = points;
        mBias = bias;
    }

    /**
     * Given an x-value, returns the y-value of the nearest reference
     * point. 
     * 
     * Due to biasing of reference points, there may be no value, in 
     * which this function returns NaN
     * 
     * @return the interpolated value
     */
    @Override
    public double interpolate(double x) {
        double nearest = Double.MAX_VALUE;
        double y = Double.NaN;

        for (Vector2D point : mPoints) {
            if (!mBias.accept(x, point)) continue;
            
            double distance = Math.abs(point.x - x);
            if (distance < nearest) {
                nearest = distance;
                y = point.y;
            }
        }

        /*
        if (Double.isNaN(y)) {
            if (x < mPoints[0].x) return mPoints[0].y;
            else return mPoints[mPoints.length - 1].y;
        } 
        */

        return y;
    }

    public static void main(String[] args) {
        for (Bias bias : new Bias[] { Bias.kLeft, Bias.kCenter, Bias.kRight}) {
            System.out.println(bias.name());

            NearestInterpolator interp = new NearestInterpolator(
                bias, 
                new Vector2D(+0.25, +1),
                new Vector2D(+0.75, -1)
            );

            for (double i = 0; i <= 1.0; i+=0.05) {
                System.out.println(SLMath.round(i, 2) + " -> " + interp.get(i));
            }
            System.out.println();
        }
    }

}