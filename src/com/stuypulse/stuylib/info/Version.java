package com.stuypulse.stuylib.info;

/**
 * This class holds version information that can be printed and comparted
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class Version implements Comparable<Version> {

    // Storage of version
    private int mMajor;
    private int mMinor;
    private int mPatch;

    /**
     * Initalize Version with all parameters
     * 
     * @param major major value
     * @param minor minor value
     * @param patch patch value
     */
    public Version(int major, int minor, int patch) {
        mMajor = Math.max(0, major);
        mMinor = Math.max(0, minor);
        mPatch = Math.max(0, patch);
    }

    /**
     * Initalize Version with all parameters but patch
     * 
     * @param major major value
     * @param minor minor value
     */
    public Version(int major, int minor) {
        mMajor = Math.max(0, major);
        mMinor = Math.max(0, minor);
        mPatch = -1;
    }

    /**
     * Get Major Value
     * 
     * @return major value
     */
    public final int getMajor() {
        return Math.max(0, mMajor);
    }

    /**
     * Get Minor Value
     * 
     * @return minor value
     */
    public final int getMinor() {
        return Math.max(0, mMinor);
    }

    /**
     * Get Patch Value
     * 
     * @return patch value
     */
    public final int getPatch() {
        return Math.max(0, mPatch);
    }

    /**
     * Convert version into string
     */
    public final String toString() {
        String out = "";
        if (mMajor >= 0)
            out += "v" + Math.max(0, mMajor);
        if (mMinor >= 0)
            out += "." + Math.max(0, mMinor);
        if (mPatch >= 0)
            out += "." + Math.max(0, mPatch);
        return out;
    }

    /**
     * Compare two integers for compare two
     * 
     * @param a first int
     * @param b second int
     * @return result
     */
    private final int compareInts(int a, int b) {
        if (Math.max(0, a) < Math.max(0, b)) {
            return -1;
        }
        if (Math.max(0, a) > Math.max(0, b)) {
            return 1;
        }
        return 0;
    }

    /**
     * Compare two versions
     */
    public final int compareTo(Version other) {
        int result = 0;
        if (mMajor >= 0)
            result += 4 * compareInts(this.getMajor(), other.getMajor());
        if (mMinor >= 0)
            result += 2 * compareInts(this.getMinor(), other.getMinor());
        if (mPatch >= 0)
            result += 1 * compareInts(this.getPatch(), other.getPatch());
        return result;
    }

}