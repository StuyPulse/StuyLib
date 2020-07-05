package com.stuypulse.stuylib.math;

/**
 * A class to represent a group of Vector2D points.
 *
 * @author Myles Pasetsky (selym3)
 */
public final class Mesh {

    /**
     * Internal array of points.
     */
    private final Vector2D[] mPoints;

    /**
     * Vector2D representing the center of the mesh because all transformations are centroid-oriented.
     */
    private Vector2D mCentroid;

    /**
     * Create a mesh from points.
     *
     * @param points array of points
     */
    public Mesh(Vector2D... points) {

        if(points.length < 1) {
            throw new IllegalArgumentException(
                    "A mesh must have at least 1 point");
        }

        mCentroid = null;

        mPoints = points;

    }

    /**
     * Utility function with same purpose as the constructor.
     *
     * @param points array points
     * @return new mesh
     */
    public final static Mesh from(Vector2D... points) {
        return new Mesh(points);
    }

    /**
     * Get the center of the mesh
     *
     * @return mesh centroid
     */
    public Vector2D getCentroid() {
        if(mCentroid == null) {
            int n = mPoints.length;
            double sumX = 0;
            double sumY = 0;
            for(int i = 0; i < n; ++i) {
                sumX += getVector(i).x;
                sumY += getVector(i).y;
            }
            mCentroid = new Vector2D(sumX / n, sumY / n);
        }

        return mCentroid;
    }

    /**
     * Retrieves a single vector for the mesh
     *
     * @param index index
     * @return Vector2D
     */
    public Vector2D getVector(int index) {
        return mPoints[index];
    }

    /**
     * Gets the number of points in the mesh
     *
     * @return size
     */
    public int size() {
        return mPoints.length;
    }

    /**
     * Returns the internal array of points
     *
     * @return points
     */
    public Vector2D[] getPoints() {
        return mPoints;
    }

    /**
     * Scales the mesh.
     *
     * @param scale vector to multiply each point by
     * @return scaled mesh
     */
    public Mesh scale(Vector2D scale) {
        int n = size();
        Vector2D[] points = new Vector2D[n];

        Vector2D translation = Vector2D.kOrigin.sub(getCentroid());

        for(int i = 0; i < n; ++i) {
            points[i] = getVector(i).add(translation).mul(scale)
                    .sub(translation);
        }

        return new Mesh(points);
    }

    /**
     * Scales the mesh.
     *
     * @param scale double to multiply each point by
     * @return scaled mesh
     */
    public Mesh scale(double scale) {
        return scale(new Vector2D(scale, scale));
    }

    /**
     * Rotate the mesh.
     *
     * @param angle angle to rotate the mesh by
     * @return rotated mesh
     */
    public Mesh rotate(Angle angle) {
        int n = size();
        Vector2D[] points = new Vector2D[n];

        Vector2D translation = Vector2D.kOrigin.sub(getCentroid());

        for(int i = 0; i < n; ++i) {
            points[i] = getVector(i).add(translation).rotate(angle)
                    .sub(translation);
        }

        return new Mesh(points);
    }

    /**
     * Translate the mesh.
     *
     * @param translation translation each point will undergo
     * @return the translated mesh
     */
    public Mesh translate(Vector2D translation) {
        int n = size();
        Vector2D[] points = new Vector2D[n];

        mCentroid = getCentroid().add(translation);

        for(int i = 0; i < n; ++i) {
            points[i] = getVector(i).add(translation);
        }

        return new Mesh(points);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Mesh = [");

        for(int i = 0; i < size() - 1; ++i) {
            out.append(getVector(i));
            out.append(", ");
        }

        out.append(getVector(size() - 1));
        out.append(" ]");

        return out.toString();
    }

}
