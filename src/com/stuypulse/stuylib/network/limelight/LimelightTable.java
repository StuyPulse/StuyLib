/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.network.limelight;

import edu.wpi.first.networktables.GenericEntry;
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

        validTarget = table.getTopic("tv").getGenericEntry();

        xAngle = table.getTopic("tx").getGenericEntry();
        yAngle = table.getTopic("ty").getGenericEntry();

        targetArea = table.getTopic("ta").getGenericEntry();
        targetSkew = table.getTopic("ts").getGenericEntry();

        latency = table.getTopic("tl").getGenericEntry();

        shortestSideLength = table.getTopic("tshort").getGenericEntry();
        longestSideLength = table.getTopic("tlong").getGenericEntry();
        horizontalSideLength = table.getTopic("thor").getGenericEntry();
        verticalSideLength = table.getTopic("tvert").getGenericEntry();

        xCorners = table.getTopic("tcornx").getGenericEntry();
        yCorners = table.getTopic("tcorny").getGenericEntry();

        solve3D = table.getTopic("camtran").getGenericEntry();
        ledMode = table.getTopic("ledMode").getGenericEntry();
        camMode = table.getTopic("camMode").getGenericEntry();
        pipeline = table.getTopic("pipeline").getGenericEntry();
        getPipeline = table.getTopic("getpipe").getGenericEntry();
        cameraStream = table.getTopic("stream").getGenericEntry();
        snapshotMode = table.getTopic("snapshot").getGenericEntry();

        timingEntry = table.getTopic(".timing_data").getGenericEntry();
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
    public final GenericEntry validTarget;

    // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    public final GenericEntry xAngle;

    // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    public final GenericEntry yAngle;

    // Target Area (0% of image to 100% of image)
    public final GenericEntry targetArea;

    // Skew or rotation (-90 degrees to 0 degrees)
    public final GenericEntry targetSkew;

    // The pipeline’s latency contribution (ms) Add at
    // least 11ms for image capture latency.
    public final GenericEntry latency;

    // Pixel information returned from these functions
    public final GenericEntry shortestSideLength;
    public final GenericEntry longestSideLength;
    public final GenericEntry horizontalSideLength;
    public final GenericEntry verticalSideLength;

    // Corner GenericEntrys
    public final GenericEntry xCorners;
    public final GenericEntry yCorners;

    // Solve 3D GenericEntrys
    public final GenericEntry solve3D;

    // Camera Control GenericEntrys
    public final GenericEntry ledMode;
    public final GenericEntry camMode;
    public final GenericEntry pipeline;
    public final GenericEntry getPipeline;
    public final GenericEntry cameraStream;
    public final GenericEntry snapshotMode;

    // Custom Timing GenericEntrys
    public final GenericEntry timingEntry;

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
