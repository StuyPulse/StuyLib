/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.network.limelight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.Topic;

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

        validTarget = table.getTopic("tv");

        xAngle = table.getTopic("tx");
        yAngle = table.getTopic("ty");

        targetArea = table.getTopic("ta");
        targetSkew = table.getTopic("ts");

        latency = table.getTopic("tl");

        shortestSideLength = table.getTopic("tshort");
        longestSideLength = table.getTopic("tlong");
        horizontalSideLength = table.getTopic("thor");
        verticalSideLength = table.getTopic("tvert");

        xCorners = table.getTopic("tcornx");
        yCorners = table.getTopic("tcorny");

        solve3D = table.getTopic("camtran");
        ledMode = table.getTopic("ledMode");
        camMode = table.getTopic("camMode");
        pipeline = table.getTopic("pipeline");
        getPipeline = table.getTopic("getpipe");
        cameraStream = table.getTopic("stream");
        snapshotMode = table.getTopic("snapshot");

        timingEntry = table.getTopic(".timing_data");
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
    public final Topic validTarget;

    // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    public final Topic xAngle;

    // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    public final Topic yAngle;

    // Target Area (0% of image to 100% of image)
    public final Topic targetArea;

    // Skew or rotation (-90 degrees to 0 degrees)
    public final Topic targetSkew;

    // The pipeline’s latency contribution (ms) Add at
    // least 11ms for image capture latency.
    public final Topic latency;

    // Pixel information returned from these functions
    public final Topic shortestSideLength;
    public final Topic longestSideLength;
    public final Topic horizontalSideLength;
    public final Topic verticalSideLength;

    // Corner Topics
    public final Topic xCorners;
    public final Topic yCorners;

    // Solve 3D Topics
    public final Topic solve3D;

    // Camera Control Topics
    public final Topic ledMode;
    public final Topic camMode;
    public final Topic pipeline;
    public final Topic getPipeline;
    public final Topic cameraStream;
    public final Topic snapshotMode;

    // Custom Timing Topics
    public final Topic timingEntry;

    /************************************************/
    /*** Functions to get Entries not listed here ***/
    /************************************************/

    /**
     * @param key ID of value on the network table
     * @return The Topic of the network table value on the Limelight Table
     */
    public Topic getTopic(String key) {
        return table.getTopic(key);
    }
}
