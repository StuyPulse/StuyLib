/* Lime Light Docs: http://docs.limelightvision.io/en/latest/networktables_api.html# */
/* StuyPulse 694, Stuyvesant Highschool, NY */

package com.stuypulse.stuylib.network.limelight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.stuypulse.stuylib.network.limelight.Solve3DResult;

/**
 * This is a class that lets you interface with the limelight network table.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class Limelight {

    // Network Table used to contact Lime Light
    private static final NetworkTableInstance mTableInstance = NetworkTableInstance.getDefault();
    private static final NetworkTable mTable = mTableInstance.getTable("limelight");

    // Toggle for posting to SmartDashboard
    public static boolean POST_TO_SMART_DASHBOARD = true;

    // Uses network tables to check status of limelight
    private static final NetworkTableEntry mTimingTestEntry = mTable.getEntry("TIMING_TEST_ENTRY");
    private static boolean mTimingTestEntryValue = false;

    public static final long MAX_UPDATE_TIME = 125_000; // Micro Seconds = 0.125 Seconds
    public static final long MIN_WARNING_TIME = 500_000; // Micro Seconds = 0.5 Seconds
    public static final long MAX_WARNING_TIME = 2_500_000; // Micro Seconds = 5.0 Seconds

    public static final boolean IS_CONNECTED_REQUIRED = false;

    /**
     * @return if limelight is connected
     */
    public static boolean isConnected() {
        // Force an update and get current time
        mTimingTestEntryValue = !mTimingTestEntryValue; // flip test value
        mTimingTestEntry.forceSetBoolean(mTimingTestEntryValue);
        long currentTime = mTimingTestEntry.getLastChange();

        // Get most recent update from limelight
        long lastUpdate = mLatencyEntry.getLastChange(); // Latency is always updated
        lastUpdate = Math.max(lastUpdate, mValidTargetEntry.getLastChange());

        // Calculate limelights last update
        long timeDifference = currentTime - lastUpdate;
        boolean connected = timeDifference < MAX_UPDATE_TIME;

        if (POST_TO_SMART_DASHBOARD) {
            SmartDashboard.putBoolean("Limelight Connected", connected);
            SmartDashboard.putNumber("Limelight Time Difference", timeDifference);
        }

        if (MIN_WARNING_TIME < timeDifference && timeDifference < MAX_WARNING_TIME) {
            double milli = (double) timeDifference / 1000.0;

            System.err.println("WARNING: Limelight has disconnected and is not responding!");
            System.err.println("         Limelight last updated " + milli + "ms ago!");
        }

        return connected;
    }

    /* Commonly Used Contour Information */
    // Whether the limelight has any valid targets (0 or 1)
    private static final NetworkTableEntry mValidTargetEntry = mTable.getEntry("tv");

    /**
     * Decides if a target shows up on limelight screen
     * 
     * @return If it has any target
     */
    @SuppressWarnings("unused")
    public static boolean hasValidTarget() {
        boolean validTarget = mValidTargetEntry.getDouble(0) > 0.5;

        if (POST_TO_SMART_DASHBOARD) {
            SmartDashboard.putBoolean("Valid Target", validTarget);
        }

        return validTarget && (!IS_CONNECTED_REQUIRED || isConnected());
    }


    // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    public static final double MIN_X_ANGLE = -27;
    public static final double MAX_X_ANGLE = 27;
    private static final NetworkTableEntry mXAngleEntry = mTable.getEntry("tx");

    /**
     * 
     * @return Horizontal side length of the target
     */
    public static double getTargetXAngle() {
        return mXAngleEntry.getDouble(0);
    }


    // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    public static final double MIN_Y_ANGLE = -20.5;
    public static final double MAX_Y_ANGLE = 20.5;
    private static final NetworkTableEntry mYAngleEntry = mTable.getEntry("ty");

    /**
     * @return The vertical angle of the target
     */
    public static double getTargetYAngle() {
        return mYAngleEntry.getDouble(0);
    }


    // Target Area (0% of image to 100% of image)
    public static final double MIN_TARGET_AREA = 0;
    public static final double MAX_TARGET_AREA = 1;
    private static final NetworkTableEntry mTargetAreaEntry = mTable.getEntry("ta");

    /**
     * @return Percent of the screen the target takes up on a scale of 0 to 1
     */
    public static double getTargetArea() {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return Math.min(mTargetAreaEntry.getDouble(0) / 100.0, 1);
    }


    // Skew or rotation (-90 degrees to 0 degrees)
    public static final double MIN_SKEW = -90;
    public static final double MAX_SKEW = 0;
    private static final NetworkTableEntry mTargetSkewEntry = mTable.getEntry("ts");

    /**
     * @return Skew of the Target
     */
    public static double getTargetSkew() {
        return mTargetSkewEntry.getDouble(0);
    }


    // The pipeline’s latency contribution (ms) Add at
    // least 11ms for image capture latency.
    public static final double IMAGE_CAPTURE_LATENCY = 11;
    private static final NetworkTableEntry mLatencyEntry = mTable.getEntry("tl");

    /**
     * @return Latency of limelight information in milli-seconds
     */
    public static double getLatencyMs() {
        // Add Image Capture Latency to get more accurate result
        return mLatencyEntry.getDouble(0) + IMAGE_CAPTURE_LATENCY;
    }


    // Pixel information returned from these functions
    public static final double MIN_SIDE_LENGTH = 0;
    public static final double MAX_SIDE_LENGTH = 320;

    private static final NetworkTableEntry mShortestSideLengthEntry = mTable.getEntry("tshort");

    /**
     * Sidelength of shortest side of the fitted bounding box (0 - 320 pixels)
     * 
     * @return Shortest side length of target in pixels
     */
    public static double getShortestSidelength() {
        return mShortestSideLengthEntry.getDouble(0);
    }


    private static final NetworkTableEntry mLongestSideLengthEntry = mTable.getEntry("tlong");

    /**
     * Sidelength of longest side of the fitted bounding box (0 - 320 pixels)
     * 
     * @return Longest side length of the target in pixels
     */
    public static double getLongestSidelength() {
        return mLongestSideLengthEntry.getDouble(0);
    }


    private static final NetworkTableEntry mHorizontalSideLengthEntry = mTable.getEntry("thor");

    /**
     * Horizontal sidelength of the rough bounding box (0 - 320 pixels)
     * 
     * @return Horizontal side length of target in pixels
     */
    public static double getHorizontalSidelength() {
        return mHorizontalSideLengthEntry.getDouble(0);
    }


    private static final NetworkTableEntry mVerticalSideLengthEntry = mTable.getEntry("tvert");

    /**
     * Vertical sidelength of the rough bounding box (0 - 320 pixels)
     * 
     * @return Vertical side length of target in pixels
     */
    public static double getVerticalSidelength() {
        return mVerticalSideLengthEntry.getDouble(0);
    }


    /* Advanced Usage with Raw Contours (Not sent by default) */
    // Raw Contours are formatted as tx0, ty0, tx1, ty1, tx2, ty2
    // So to make this easier, you pass an int and it formats it

    /**
     * @param target Target to read X Angle from
     * @return X Angle of corresponding target
     */
    public static double getRawTargetXAngle(int target) {
        return mTable.getEntry("tx" + target).getDouble(0);
    }

    /**
     * @param target Target to read Y Angle from
     * @return Y Angle of corresponding target
     */
    public static double getRawTargetYAngle(int target) {
        return mTable.getEntry("ty" + target).getDouble(0);
    }

    /**
     * @param target Target to read Area from
     * @return Percent of the screen the corresponding target takes up on a scale of
     *         0 to 1
     */
    public static double getRawTargetArea(int target) {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return Math.min(mTable.getEntry("ta" + target).getDouble(0) / 100.0, 1);
    }

    /**
     * @param target Target to read Skew from
     * @return Skew of corresponding target
     */
    public static double getRawTargetSkew(int target) {
        return mTable.getEntry("ts" + target).getDouble(0);
    }

    /**
     * @param crosshair Crosshair to read coords from
     * @return X Coordinate of corresponding crosshair
     */
    public static double getCustomRawCrosshairX(int crosshair) {
        return mTable.getEntry("cx" + crosshair).getDouble(0);
    }

    /**
     * @param crosshair Crosshair to read coords from
     * @return Y Coordinate of corresponding crosshair
     */
    public static double getRawCrosshairY(int crosshair) {
        return mTable.getEntry("cy" + crosshair).getDouble(0);
    }


    /* Solve 3D */
    private static final NetworkTableEntry mSolve3DEntry = mTable.getEntry("camtran");
    private static final double[] mDefaultSolve3DArray = new double[6];

    /**
     * Get Solve3D Result from network table
     * 
     * @return Solve 3D Result
     */
    public static Solve3DResult getSolve3D() {
        return new Solve3DResult(mSolve3DEntry.getDoubleArray(mDefaultSolve3DArray));
    }


    /* Custom Grip Values */
    // Return data given by custom GRIP pipeline
    /**
     * @param element Name of double provided by GRIP Pipeline
     * @return Double provided by GRIP Pipeline
     */
    public static double getCustomDouble(String element) {
        return mTable.getEntry(element).getDouble(0);
    }

    /**
     * @param element Name of Number to set on Network Table
     * @param value   Value to set the Number on the Network Table to
     * @return Whether or not the write was successful
     */
    public static boolean setCustomNumber(String element, Number value) {
        return mTable.getEntry(element).setNumber(value);
    }

    /**
     * @param element Name of String provided by GRIP Pipeline
     * @return String provided by GRIP Pipeline
     */
    public static String getCustomString(String element) {
        return mTable.getEntry(element).getString("");
    }

    /**
     * @param element Name of String to set on Network Table
     * @param value   Value to set the Sting on the Network Table to
     * @return Whether or not the write was successful
     */
    public static boolean setCustomString(String element, String value) {
        return mTable.getEntry(element).setString(value);
    }


    /* Camera Controls (Use Enums to prevent invalid inputs) */
    // LEDMode
    public enum LEDMode {
        PIPELINE(0), // Use LED mode set in pipeline
        FORCE_OFF(1), // Force LEDs off
        FORCE_BLINK(2), // Force LEDs to blink
        FORCE_ON(3); // Force LEDs on

        LEDMode(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    private static final NetworkTableEntry mLEDModeEntry = mTable.getEntry("ledMode");

    /**
     * @param mode Specified LED Mode to set the limelight to
     */
    public static void setLEDMode(LEDMode mode) {
        mLEDModeEntry.setNumber(mode.getCodeValue());
    }


    // CAM_MODE
    public enum CamMode {
        VISION(0), // Use limelight for CV
        DRIVER(1); // Use limelight for driving

        CamMode(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    private static final NetworkTableEntry mCamModeEntry = mTable.getEntry("camMode");

    /**
     * @param mode Specified Cam Mode to set the limelight to
     */
    public static void setCamMode(CamMode mode) {
        mCamModeEntry.setNumber(mode.getCodeValue());
    }


    // PIPELINE
    private static final NetworkTableEntry mPipelineEntry = mTable.getEntry("pipeline");

    /**
     * @param pipeline Specified pipeline to set the limelight to
     */
    public static void setPipeline(int pipeline) {
        // Prevent input of invalid pipelines
        if (pipeline >= 0 && pipeline <= 9) {
            mPipelineEntry.setNumber(pipeline);
        }
    }

    private static final NetworkTableEntry mGetPipelineEntry = mTable.getEntry("getpipe");

    public static double getPipeline() {
        return mGetPipelineEntry.getDouble(0);
    }


    // STREAM
    public enum CameraStream { // PIP = Picture-In-Picture
        STANDARD(0), // Shows limelight and secondary camera side by side
        PIP_MAIN(1), // Shows the secondary camera along with and within the limelight camera
        PIP_SECONDARY(2); // Shows the limelight along with and within the limelight camera

        CameraStream(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    private static final NetworkTableEntry mCameraStreamEntry = mTable.getEntry("stream");

    /**
     * @param stream Specified Camera Stream to set the limelight to
     */
    public static void setCameraStream(CameraStream stream) {
        mCameraStreamEntry.setNumber(stream.getCodeValue());
    }

    
    // SNAPSHOT_MODE
    public enum SnapshotMode {
        STOP(0), // Don't take snapshots
        TWO_PER_SECOND(1);

        SnapshotMode(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    private static final NetworkTableEntry mSnapshotModeEntry = mTable.getEntry("snapshot");

    /**
     * @param mode Specified Snapshot Mode to set the limelight to
     */
    public static void setSnapshotMode(SnapshotMode mode) {
        mSnapshotModeEntry.setNumber(mode.getCodeValue());
    }
}
