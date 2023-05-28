/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util;

import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;

import java.util.HashSet;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * The RioAprilTagDetection class is designed to detect AprilTags with OpenCV on a RoboRIO. It has
 * methods for detecting AprilTags via a USB camera connected to any of the USB ports on the
 * RoboRIO. As well as helper functions that retrieve some data from the detection.
 *
 * <p>The detector is optimized to use as little memory and processor power as possible, through the
 * use of sets (avoids duplication of data), and a seperate Thread to perform the vision processing.
 */
public class RioAprilTagDetector {

    static int height;
    static int width;
    static String name;
    static int minClusterPixels;
    static double criticalAngle;

    static int detectionResults;
    static int detectionsPerSecond;

    /**
     * Creates a new RioAprilTagDetection
     *
     * @param cameraWidth - width in pixels
     * @param cameraHeight - height in pixels
     * @param cameraName - name of the camera shown in SmartDashboard
     * @param minClusterPix - minimum size of pixels of an AprilTag
     * @param critAngle
     */
    public RioAprilTagDetector(
            int cameraWidth,
            int cameraHeight,
            String cameraName,
            int minClusterPix,
            double critAngle) {
        height = cameraHeight;
        width = cameraWidth;
        name = cameraName;

        minClusterPixels = minClusterPix;
        criticalAngle = critAngle;
    }

    static Thread aprilTagDetectionThread;

    /**
     * call this method in robotInit()
     *
     * <p>RoboRIO 2 is reccomended for best performance with the RIO.
     *
     * <p>Vision processing on the RoboRIO is computationally expensive, so don't expect performance
     * to be stable. Try using a lower camera resolution to improve fps, and AprilTag detection,
     * 16:9 ratio is reccomended, but all others should work.
     *
     * <p>This assumes that a Camera, like a Logitech C270, is connected to any of the RoboRIO's USB
     * ports.
     */
    public static void detectAprilTags() {
        aprilTagDetectionThread =
                new Thread(
                        () -> {
                            var camera = CameraServer.startAutomaticCapture();
                            camera.setResolution(width, height);

                            var cvSink = CameraServer.getVideo();
                            var outputStream = CameraServer.putVideo(name, width, height);

                            // Mats are memory expensive, it is best to use as little as possible
                            var mat = new Mat();
                            var grayMat = new Mat();

                            // points that connect all corners of AprilTag and draw a rectangle
                            var pt0 = new Point();
                            var pt1 = new Point();
                            var pt2 = new Point();
                            var pt3 = new Point();
                            var center = new Point();
                            var red = new Scalar(0, 0, 255);
                            var green = new Scalar(0, 255, 0);

                            var aprilTagDetector = new AprilTagDetector();

                            var config = aprilTagDetector.getConfig();
                            config.quadSigma = 0.8f;
                            aprilTagDetector.setConfig(config);

                            var quadThreshParams = aprilTagDetector.getQuadThresholdParameters();
                            quadThreshParams.minClusterPixels = minClusterPixels; // default is 400
                            quadThreshParams.criticalAngle *= criticalAngle; // default is 10 or 5
                            quadThreshParams.maxLineFitMSE *= 1.5;

                            aprilTagDetector.setQuadThresholdParameters(quadThreshParams);

                            // the AprilTag detector can only detect one family at a time
                            // to detect multiple tag fam's we must use multiple AprilTagDetectors
                            // this leads worse performance as AprilTagDetectors are expensive to
                            // setup
                            aprilTagDetector.addFamily("tag16h5");

                            var timer = new Timer();
                            timer.start();
                            var count = 0;

                            // this can never be true the robot must be off for this to be true
                            while (!Thread.interrupted()) {
                                if (cvSink.grabFrame(mat) == 0) {
                                    outputStream.notifyError(cvSink.getError());
                                    continue;
                                }

                                // convert image to grayscale
                                Imgproc.cvtColor(mat, grayMat, Imgproc.COLOR_RGB2GRAY);

                                var results = aprilTagDetector.detect(grayMat);

                                var set = new HashSet<>();

                                for (var result : results) {
                                    count += 1;

                                    detectionResults = result.getId();

                                    pt0.x = result.getCornerX(0);
                                    pt1.x = result.getCornerX(1);
                                    pt2.x = result.getCornerX(2);
                                    pt3.x = result.getCornerX(3);

                                    pt0.y = result.getCornerY(0);
                                    pt1.y = result.getCornerY(1);
                                    pt2.y = result.getCornerY(2);
                                    pt3.y = result.getCornerY(3);

                                    center.x = result.getCenterX();
                                    center.y = result.getCenterY();

                                    set.add(result.getId());

                                    // Imgproc doesn't have a square/rectangle member function
                                    // so we must use this awkward ass way of drawing squares around
                                    // the tag
                                    Imgproc.line(mat, pt0, pt1, red, 5);
                                    Imgproc.line(mat, pt1, pt2, red, 5);
                                    Imgproc.line(mat, pt2, pt3, red, 5);
                                    Imgproc.line(mat, pt3, pt0, red, 5);

                                    Imgproc.circle(mat, center, 4, green);
                                    // print id (number) of the tag
                                    Imgproc.putText(
                                            mat,
                                            String.valueOf(result.getId()),
                                            pt2,
                                            Imgproc.FONT_HERSHEY_SIMPLEX,
                                            2,
                                            green,
                                            7);
                                }
                                ;

                                for (var id : set) {
                                    System.out.println("Tag: " + String.valueOf(id));
                                }

                                if (timer.advanceIfElapsed(1.0)) {
                                    detectionsPerSecond = count;
                                    System.out.println(
                                            "detections per second: " + String.valueOf(count));
                                    count = 0;
                                }

                                outputStream.putFrame(mat);
                            }

                            aprilTagDetector.close();
                        });
        aprilTagDetectionThread.setDaemon(true);
        aprilTagDetectionThread.start();
    }

    public int getAprilTagId() {
        return detectionResults;
    }

    public int getDetectionsPerSecond() {
        return detectionsPerSecond;
    }
}
