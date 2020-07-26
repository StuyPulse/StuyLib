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
    public final static Mesh of(Vector2D... points) {
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
    public Mesh mul(Vector2D scale) {
        return mul(scale.x, scale.y);
    }

    /**
     * Scales the mesh.
     *
     * @param sx scalar x
     * @param sy scalar y
     * @return scaled mesh
     */
    public Mesh mul(double sx, double sy) {
        int n = size();
        Vector2D[] points = new Vector2D[n];

        Vector2D scale = new Vector2D(sx, sy);

        for(int i = 0; i < n; ++i) {
            points[i] = getVector(i).mul(scale);
        }

        return new Mesh(points);
    }

    /**
     * Scales the mesh.
     *
     * @param scale double to multiply each point by
     * @return scaled mesh
     */
    public Mesh mul(double scale) {
        return mul(scale, scale);
    }

    /**
     * Scale the mesh inversely.
     * 
     * @param isx inverse scale x
     * @param isy inverse scale y
     * @return scaled mesh
     */
    public Mesh div(double isx, double isy) {
        return mul(1.0/isx, 1.0/isy);
    }

    /**
     * Scale the mesh inversely.
     * 
     * @param is inverse scale x and y
     * @return scaled mesh
     */
    public Mesh div(double is) {
        return div(is, is);
    }

    /**
     * Scale the mesh inversely
     * 
     * @param invScale vector representing inverse scale x and y
     * @return
     */
    public Mesh div(Vector2D invScale) {
        return div(invScale.x, invScale.y);
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

        for(int i = 0; i < n; ++i) {
            points[i] = getVector(i).rotate(angle);
        }

        return new Mesh(points);
    }
    
    /**
     * Rotate the mesh around an origin .
     *
     * @param angle angle to rotate the mesh by
     * @param origin origin of the rotation
     * @return rotated mesh
     */
    public Mesh rotate(Angle angle, Vector2D origin) {
        int n = size();
        Vector2D[] points = new Vector2D[n];

        for(int i = 0; i < n; ++i) {
            points[i] = getVector(i).rotate(angle, origin);
        }

        return new Mesh(points);
    }

    /**
     * Translate the mesh.
     *
     * @param translation translation each point will undergo
     * @return the translated mesh
     */
    public Mesh add(Vector2D translation) {
        return add(translation.x, translation.y);
    }

    /**
     * Translate the mesh negatively.
     * 
     * @param ntx translation x
     * @param nty translation y
     * @return translated mesh
     */
    public Mesh sub(double ntx, double nty) {
        return add(-ntx, -nty);
    }

    /**
     * Translate the mesh negatively.
     * 
     * @param nt translation x and y
     * @return translated mesh
     */
    public Mesh sub(double nt) {
        return sub(nt, nt);
    }

    /**
     * Translate the mesh.
     *
     * @param t translation x and y
     * @return the translated mesh
     */
    public Mesh add(double t) {
        return add(t,t);
    }

    /**
     * Translate the mesh negatively.
     * 
     * @param ntx translation x
     * @param nty translation y
     * @return translated mesh
     */
    public Mesh sub(Vector2D nTranslation) {
        return sub(nTranslation.x, nTranslation.y);
    }

    /**
    * Translate the mesh.
    *
    * @param tx translation x
    * @param ty translation y
    * @return the translated mesh
    */
    public Mesh add(double tx, double ty) {
        int n = size();
        Vector2D[] points = new Vector2D[n];

        Vector2D translation = new Vector2D(tx,ty);

        for(int i = 0; i < n; ++i) {
            points[i] = getVector(i).add(translation);
        }

        return new Mesh(points);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Mesh = [");
        int n = size()-1;

        for(int i = 0; i < n; ++i) {
            out.append(getVector(i));
            out.append(", ");
        }

        out.append(getVector(n));
        out.append(" ]");

        return out.toString();
    }

}
