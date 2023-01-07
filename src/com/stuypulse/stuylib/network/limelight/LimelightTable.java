/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.network.limelight;

import edu.wpi.first.networktables.BooleanEntry;
import edu.wpi.first.networktables.DoubleArrayEntry;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.IntegerEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * This class provides an extremely low level implementation construction of the limelight network
 * table. It defines all of the NetworkTabe entries, and nothing else. This is not made for use in
 * robot code, but instead to assist other API's that use the limelight.
 *
 * @author Sam B (sam.belliveau@gmail.com)
 */
public final class LimelightTable {

    /** Create a LimelightTable with the default table name */
    public LimelightTable() {
        this("limelight");
    }

    /**
     * Create a LimelightTable with a custom NetworkTable name. This may be used if we need multiple
     * limelights.
     *
     * @param tableName the custom name of the limelight's network table
     */
    public LimelightTable(String tableName) {
        tableInstance = NetworkTableInstance.getDefault();
        table = tableInstance.getTable(tableName);

        validTarget = table.getIntegerTopic("tv").getEntry(0);

        xAngle = table.getDoubleTopic("tx").getEntry(0);
        yAngle = table.getDoubleTopic("ty").getEntry(0);

        targetArea = table.getDoubleTopic("ta").getEntry(0);
        targetSkew = table.getDoubleTopic("ts").getEntry(0);

        latency = table.getIntegerTopic("tl").getEntry(0);

        shortestSideLength = table.getDoubleTopic("tshort").getEntry(0);
        longestSideLength = table.getDoubleTopic("tlong").getEntry(0);
        horizontalSideLength = table.getDoubleTopic("thor").getEntry(0);
        verticalSideLength = table.getDoubleTopic("tvert").getEntry(0);

        xCorners = table.getDoubleArrayTopic("tcornx").getEntry(new double[] {});
        yCorners = table.getDoubleArrayTopic("tcorny").getEntry(new double[] {});

        solve3D = table.getDoubleArrayTopic("camtran").getEntry(new double[] {});

        ledMode = table.getIntegerTopic("ledMode").getEntry(0);
        camMode = table.getIntegerTopic("camMode").getEntry(0);
        pipeline = table.getIntegerTopic("pipeline").getEntry(0);
        getPipeline = table.getIntegerTopic("getpipe").getEntry(0);
        cameraStream = table.getIntegerTopic("stream").getEntry(0);
        snapshotMode = table.getIntegerTopic("snapshot").getEntry(0);

        timingEntry = table.getBooleanTopic(".timing_data").getEntry(false);
    }

    /****************************************************/
    /*** Network Table Info used to contact Limelight ***/
    /****************************************************/

    public final NetworkTableInstance tableInstance;

    public final NetworkTable table;

    /****************************************************************/
    /*** Network Table Entries used to communicate with Limelight ***/
    /****************************************************************/

    // Whether the limelight has any valid targets (0 or 1)
    public final IntegerEntry validTarget;

    // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    public final DoubleEntry xAngle;

    // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    public final DoubleEntry yAngle;

    // Target Area (0% of image to 100% of image)
    public final DoubleEntry targetArea;

    // Skew or rotation (-90 degrees to 0 degrees)
    public final DoubleEntry targetSkew;

    // The pipeline’s latency contribution (ms) Add at
    // least 11ms for image capture latency.
    public final IntegerEntry latency;

    // Pixel information returned from these functions
    public final DoubleEntry shortestSideLength;
    public final DoubleEntry longestSideLength;
    public final DoubleEntry horizontalSideLength;
    public final DoubleEntry verticalSideLength;

    // Corner DoubleArrayEntry
    public final DoubleArrayEntry xCorners;
    public final DoubleArrayEntry yCorners;

    // Solve 3D DoubleArrayEntrys
    public final DoubleArrayEntry solve3D;

    // Camera Control DoubleEntrys
    public final IntegerEntry ledMode;
    public final IntegerEntry camMode;
    public final IntegerEntry pipeline;
    public final IntegerEntry getPipeline;
    public final IntegerEntry cameraStream;
    public final IntegerEntry snapshotMode;

    // Custom Timing DoubleEntrys
    public final BooleanEntry timingEntry;

    /************************************************/
    /*** Functions to get Entries not listed here ***/
    /************************************************/

    /**
     * @param key ID of value on the network table
     * @return The {@link GenericEntry} of the network table value on the Limelight Table
     */
    public GenericEntry getGenericEntry(String key) {
        return table.getTopic(key).getGenericEntry();
    }
}
