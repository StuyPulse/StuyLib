package com.stuypulse.stuylib.math.interpolation;

import java.util.function.BiFunction;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.math.Vector2D;

public class NearestInterpolator implements Interpolator {
    public static enum Bias {
        kLeft((x, point) -> x >= point.x), 
        kCenter((x, point) -> true), 
        kRight((x, point) -> x <= point.x);

        private BiFunction<Double, Vector2D, Boolean> mAcceptor;

        private Bias(BiFunction<Double, Vector2D, Boolean> acceptor) {
            mAcceptor = acceptor;
        }

        private boolean accept(double x, Vector2D point) {
            return mAcceptor.apply(x, point);
        }
    }

    private final Vector2D[] mPoints;
    private final Bias mBias;

    public NearestInterpolator(Bias bias, Vector2D... points) {
        if (points.length < 1) {
            throw new IllegalArgumentException("Nearest Interpolator requires 1 point");
        }

        mPoints = points;
        mBias = bias;
    }

    public NearestInterpolator(Vector2D... points) {
        this(Bias.kCenter, points);
    }

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

        System.out.println(Double.NaN < Double.NaN);
    }

}