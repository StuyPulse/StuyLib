/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.network.limelight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
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

        validTarget = table.getEntry("tv");

        xAngle = table.getEntry("tx");
        yAngle = table.getEntry("ty");

        targetArea = table.getEntry("ta");
        targetSkew = table.getEntry("ts");

        latency = table.getEntry("tl");

        shortestSideLength = table.getEntry("tshort");
        longestSideLength = table.getEntry("tlong");
        horizontalSideLength = table.getEntry("thor");
        verticalSideLength = table.getEntry("tvert");

        xCorners = table.getEntry("tcornx");
        yCorners = table.getEntry("tcorny");

        solve3D = table.getEntry("camtran");
        ledMode = table.getEntry("ledMode");
        camMode = table.getEntry("camMode");
        pipeline = table.getEntry("pipeline");
        getPipeline = table.getEntry("getpipe");
        cameraStream = table.getEntry("stream");
        snapshotMode = table.getEntry("snapshot");

        timingEntry = table.getEntry(".timing_data");
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
    public final NetworkTableEntry validTarget;

    // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    public final NetworkTableEntry xAngle;

    // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    public final NetworkTableEntry yAngle;

    // Target Area (0% of image to 100% of image)
    public final NetworkTableEntry targetArea;

    // Skew or rotation (-90 degrees to 0 degrees)
    public final NetworkTableEntry targetSkew;

    // The pipeline’s latency contribution (ms) Add at
    // least 11ms for image capture latency.
    public final NetworkTableEntry latency;

    // Pixel information returned from these functions
    public final NetworkTableEntry shortestSideLength;
    public final NetworkTableEntry longestSideLength;
    public final NetworkTableEntry horizontalSideLength;
    public final NetworkTableEntry verticalSideLength;

    // Corner Entries
    public final NetworkTableEntry xCorners;
    public final NetworkTableEntry yCorners;

    // Solve 3D Entry
    public final NetworkTableEntry solve3D;

    // Camera Control Entries
    public final NetworkTableEntry ledMode;
    public final NetworkTableEntry camMode;
    public final NetworkTableEntry pipeline;
    public final NetworkTableEntry getPipeline;
    public final NetworkTableEntry cameraStream;
    public final NetworkTableEntry snapshotMode;

    // Custom Timing NetworkTableEntries
    public final NetworkTableEntry timingEntry;

    /************************************************/
    /*** Functions to get Entries not listed here ***/
    /************************************************/

    /**
     * @param key ID of value on the network table
     * @return The entry of the network table value on the Limelight Table
     */
    public NetworkTableEntry getEntry(String key) {
        return table.getEntry(key);
    }
}
