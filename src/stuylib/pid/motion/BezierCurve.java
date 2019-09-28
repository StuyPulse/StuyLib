package stuylib.pid.motion;

import stuylib.math.Vector2D;

public class BezierCurve {

    // Weighted sum of points
    private static Vector2D average(Vector2D a, Vector2D b, double preportion) {
        Vector2D out = new Vector2D(0, 0);
        out.x = a.x * preportion + b.x * (1.0 - preportion);
        out.y = a.y * preportion + b.y * (1.0 - preportion);
        
        return out;
    }

    // Weighted sum of points, and then weighted sum of those points
    private static Vector2D getPosition(Vector2D[] points, double preportion) {
        // Make copy to do calculations on
        Vector2D[] lines = points.clone();
        
        for(int max = points.length - 1; max > 0; --max)
            for(int i = 0; i < max; ++i)
                lines[i] = average(lines[i], lines[i+1], preportion);

        return lines[0];
    }

    /**
     * Generate path
     * @param points array of Vector2Ds that work as the input points
     * @param accuracy amount of points that the generator will return
     * @return the generated path
     */
    public static Vector2D[] generatePath(Vector2D[] points, int accuracy) {
        Vector2D[] path = new Vector2D[accuracy];

        for(int i = 0; i < accuracy; ++i) {
            path[i] = getPosition(points, ((double)i)/((double)accuracy));
        }

        return path;
    }
}