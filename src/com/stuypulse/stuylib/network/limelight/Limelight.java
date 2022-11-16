/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.network.limelight;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.math.Vector2D;

/**
 * This is a class that lets you interface with the limelight network table.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public final class Limelight {

    /*****************/
    /*** Singleton ***/
    /*****************/

    private static Limelight mDefaultInstance = null;

    /**
     * Returns a Limelight Class using the default table name.
     *
     * <p>
     * If you only have one limelight, this is most likely the constructor you want
     * to use.
     *
     * @return instance of limelight
     */
    public static Limelight getInstance() {
        if (mDefaultInstance == null)
            mDefaultInstance = new Limelight();
        return mDefaultInstance;
    }

    /**
     * Returns a Limelight Class using a custom table name.
     *
     * <p>
     * If you have multiple limelights, this is most likely the constructor you want
     * to use.
     *
     * @param tableName the table name of the limelight
     * @return instance of limelight
     */
    public static Limelight getInstance(String tableName) {
        return new Limelight(tableName);
    }

    /********************/
    /*** Constructors ***/
    /********************/

    /**
     * Construct a Limelight Class using the default table name.
     *
     * <p>
     * If you only have one limelight, this is most likely the constructor you want
     * to use.
     */
    private Limelight() {
        table = new LimelightTable();
    }

    /**
     * Construct a Limelight Class using a custom table name.
     *
     * <p>
     * If you have multiple limelights, this is most likely the constructor you want
     * to use.
     *
     * @param tableName the table name of the limelight
     */
    private Limelight(String tableName) {
        table = new LimelightTable(tableName);
    }

    /****************************/
    /*** Variable Definitions ***/
    /****************************/

    private final LimelightTable table;

    /************************/
    /*** Connnection Test ***/
    /************************/

    /** @return time of last network table change from limelight */
    public long getLastUpdate() {
        long lastChange = table.latency.getLastChange();
        lastChange = Math.max(lastChange, table.xAngle.getLastChange());
        lastChange = Math.max(lastChange, table.yAngle.getLastChange());
        return lastChange;
    }

    /** @return if the limelight has updated its */
    public boolean isConnected() {
        final long MAX_UPDATE_TIME = 250_000;

        table.timingEntry.setValue(!table.timingEntry.getBoolean(false));
        long currentTime = table.timingEntry.getLastChange();

        return Math.abs(currentTime - getLastUpdate()) < MAX_UPDATE_TIME;
    }

    /*****************************************/
    /*** Commonly Used Contour Information ***/
    /*****************************************/

    /** @return Whether the limelight has any valid targets */
    public boolean getValidTarget() {
        return (table.validTarget.getDouble(0) > 0.5) && (isConnected());
    }

    /**
     * @return Horizontal Offset From Crosshair To Target (-27 degrees to 27
     *         degrees)
     */
    public double getTargetXAngle() {
        return table.xAngle.getDouble(0);
    }

    /**
     * @return Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5
     *         degrees)
     */
    public double getTargetYAngle() {
        return table.yAngle.getDouble(0);
    }

    /** @return Percent of the screen the target takes up on a scale of 0 to 1 */
    public double getTargetArea() {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return SLMath.clamp(table.targetArea.getDouble(0) / 100.0, 0, 1);
    }

    /** @return Skew or rotation (-90 degrees to 0 degrees) */
    public double getTargetSkew() {
        return table.targetSkew.getDouble(0);
    }

    /** @return Latency of limelight information in milli-seconds */
    public double getLatencyMs() {
        // Add Image Capture Latency to get more accurate result
        return table.latency.getDouble(0) + LimelightConstants.IMAGE_CAPTURE_LATENCY;
    }

    /********************/
    /*** Side Lengths ***/
    /********************/

    /** @return Shortest side length of target in pixels */
    public double getShortestSidelength() {
        return table.shortestSideLength.getDouble(0);
    }

    /**
     * @return Sidelength of longest side of the fitted bounding box (0 - 320
     *         pixels)
     */
    public double getLongestSidelength() {
        return table.longestSideLength.getDouble(0);
    }

    /** @return Horizontal sidelength of the rough bounding box (0 - 320 pixels) */
    public double getHorizontalSidelength() {
        return table.horizontalSideLength.getDouble(0);
    }

    /** @return Vertical sidelength of the rough bounding box (0 - 320 pixels) */
    public double getVerticalSidelength() {
        return table.verticalSideLength.getDouble(0);
    }

    /**********************/
    /*** Target Corners ***/
    /**********************/

    /** @return Number array of corner x-coordinates */
    public double[] getRawTargetCornersX() {
        return table.xCorners.getDoubleArray(new double[] {});
    }

    /** @return Number array of corner y-coordinates */
    public double[] getRawTargetCornersY() {
        return table.yCorners.getDoubleArray(new double[] {});
    }

    /** @return Vector2D array of the target corners */
    public Vector2D[] getTargetCorners() {
        double[] rawX = getRawTargetCornersX();
        double[] rawY = getRawTargetCornersX();

        if (rawX.length != 0 && rawY.length != 0 && rawX.length == rawY.length) {
            int length = rawX.length;

            Vector2D[] output = new Vector2D[length];

            for (int i = 0; i < length; ++i) {
                output[i] = new Vector2D(rawX[i], rawY[i]);
            }

            return output;
        }

        return new Vector2D[] {};
    }

    /**************************************************************/
    /*** Advanced Usage with Raw Contours (Not sent by default) ***/
    /**************************************************************/

    // Raw Contours are formatted as tx0, ty0, tx1, ty1, tx2, ty2
    // So to make this easier, you pass an int and it formats it

    /**
     * @param target Target to read X Angle from
     * @return X Angle of corresponding target
     */
    public double getRawTargetXAngle(int target) {
        return table.getEntry("tx" + target).getDouble(0);
    }

    /**
     * @param target Target to read Y Angle from
     * @return Y Angle of corresponding target
     */
    public double getRawTargetYAngle(int target) {
        return table.getEntry("ty" + target).getDouble(0);
    }

    /**
     * @param target Target to read Area from
     * @return Percent of the screen the corresponding target takes up on a scale of
     *         0 to 1
     */
    public double getRawTargetArea(int target) {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return SLMath.clamp(table.getEntry("ta" + target).getDouble(0) / 100.0, 0, 1);
    }

    /**
     * @param target Target to read Skew from
     * @return Skew of corresponding target
     */
    public double getRawTargetSkew(int target) {
        return table.getEntry("ts" + target).getDouble(0);
    }

    /**
     * @param crosshair Crosshair to read coords from
     * @return X Coordinate of corresponding crosshair
     */
    public double getCustomRawCrosshairX(int crosshair) {
        return table.getEntry("cx" + crosshair).getDouble(0);
    }

    /**
     * @param crosshair Crosshair to read coords from
     * @return Y Coordinate of corresponding crosshair
     */
    public double getRawCrosshairY(int crosshair) {
        return table.getEntry("cy" + crosshair).getDouble(0);
    }

    /****************/
    /*** Solve 3D ***/
    /****************/

    /** @return The Solve 3D Result */
    public Solve3DResult getSolve3D() {
        return new Solve3DResult(table.solve3D.getDoubleArray(new double[] {}));
    }

    /***************************/
    /*** Camera Mode Control ***/
    /***************************/

    /** @param mode Specified LED Mode to set the limelight to */
    public void setLEDMode(LEDMode mode) {
        table.ledMode.setNumber(mode.getCodeValue());
    }

    /** @param mode Specified Cam Mode to set the limelight to */
    public void setCamMode(CamMode mode) {
        table.camMode.setNumber(mode.getCodeValue());
    }

    /** @param mode Specified Snapshot Mode to set the limelight to */
    public void setSnapshotMode(SnapshotMode mode) {
        table.snapshotMode.setNumber(mode.getCodeValue());
    }

    /** @param stream Specified Camera Stream to set the limelight to */
    public void setCameraStream(CameraStream stream) {
        table.cameraStream.setNumber(stream.getCodeValue());
    }

    /** @param pipeline Specified pipeline to set the limelight to */
    public void setPipeline(Pipeline pipeline) {
        if (!pipeline.equals(Pipeline.INVALID_PIPELINE))
            table.pipeline.setNumber(pipeline.getCodeValue());
    }

    /** @return The current pipeline the limelight is set to */
    public Pipeline getPipeline() {
        double ntValue = table.getPipeline.getDouble(0);
        int pipelineID = (int) (ntValue + 0.5);
        switch (pipelineID) {
            case 0:
                return Pipeline.SETTING_0;
            case 1:
                return Pipeline.SETTING_1;
            case 2:
                return Pipeline.SETTING_2;
            case 3:
                return Pipeline.SETTING_3;
            case 4:
                return Pipeline.SETTING_4;
            case 5:
                return Pipeline.SETTING_5;
            case 6:
                return Pipeline.SETTING_6;
            case 7:
                return Pipeline.SETTING_7;
            case 8:
                return Pipeline.SETTING_8;
            case 9:
                return Pipeline.SETTING_9;
            default:
                return Pipeline.INVALID_PIPELINE;
        }
    }

    /*************************************/
    /*** Camera Modes Enum Definitions ***/
    /*************************************/

    /** Modes for how the lights on the Limelight should be configured */
    public enum LEDMode {
        /* Use LED mode set in pipeline */
        PIPELINE(0),

        /* Force LEDs off */
        FORCE_OFF(1),

        /* Force LEDs to blink */
        FORCE_BLINK(2),

        /* Force LEDs on */
        FORCE_ON(3);

        private final int value;

        LEDMode(int value) {
            this.value = value;
        }

        public int getCodeValue() {
            return value;
        }
    };

    /** Modes for how the Camera should be configured */
    public enum CamMode {
        /* Use limelight for CV */
        VISION(0),

        /* Use limelight for Driving */
        DRIVER(1);

        private final int value;

        CamMode(int value) {
            this.value = value;
        }

        public int getCodeValue() {
            return value;
        }
    };

    /** Mode for how the limelight should take snapshots */
    public enum SnapshotMode {
        /* Don't take snapshots */
        STOP(0),

        /* Take two snapshots per second */
        TWO_PER_SECOND(1);

        private final int value;

        SnapshotMode(int value) {
            this.value = value;
        }

        public int getCodeValue() {
            return value;
        }
    };

    /** Modes for how the camera stream over the network should be delivered */
    public enum CameraStream {
        /* Shows limelight and secondary camera side by side */
        STANDARD(0),

        /** Shows the secondary camera along with and within the limelight camera */
        PIP_MAIN(1),

        /** Shows the limelight along with and within the limelight camera */
        PIP_SECONDARY(2);

        private final int value;

        CameraStream(int value) {
            this.value = value;
        }

        public int getCodeValue() {
            return value;
        }
    };

    /** The different Pipelines that the limelight camera can be set to */
    public enum Pipeline {
        INVALID_PIPELINE(-1),
        SETTING_0(0),
        SETTING_1(1),
        SETTING_2(2),
        SETTING_3(3),
        SETTING_4(4),
        SETTING_5(5),
        SETTING_6(6),
        SETTING_7(7),
        SETTING_8(8),
        SETTING_9(9);

        private final int value;

        Pipeline(int value) {
            this.value = value;
        }

        public int getCodeValue() {
            return value;
        }
    };

    /**************************/
    /*** Custom Grip Values ***/
    /**************************/

    /**
     * @param element Name of double provided by GRIP Pipeline
     * @return Double provided by GRIP Pipeline
     */
    public double getCustomDouble(String element) {
        return table.getEntry(element).getDouble(0);
    }

    /**
     * @param element Name of Number to set on Network Table
     * @param value   Value to set the Number on the Network Table to
     * @return Whether or not the write was successful
     */
    public boolean setCustomNumber(String element, Number value) {
        return table.getEntry(element).setNumber(value);
    }

    /**
     * @param element Name of String provided by GRIP Pipeline
     * @return String provided by GRIP Pipeline
     */
    public String getCustomString(String element) {
        return table.getEntry(element).getString("");
    }

    /**
     * @param element Name of String to set on Network Table
     * @param value   Value to set the Sting on the Network Table to
     * @return Whether or not the write was successful
     */
    public boolean setCustomString(String element, String value) {
        return table.getEntry(element).setString(value);
    }
}
