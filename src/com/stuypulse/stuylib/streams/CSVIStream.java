// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.


package com.stuypulse.stuylib.streams;

import com.stuypulse.stuylib.file.csv.*;

import java.io.IOException;
import java.util.Iterator;

/**
 * Class Writer allows you to read input from an IStream into a CSV file Class Reader is an IStream
 * that reads from CSV file
 *
 * <p>They extend from IStream, so they also work with the existing IStream classes
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class CSVIStream {

    /**
     * This CSVIStream writer let an IStream pass through while recording the values of the IStream
     * into a CSV file.
     */
    public static class Writer implements IStream {

        /** The IStream that will be read from into the CSV file */
        private IStream mStream;

        private CSVWriter mCSVFile;
        private int mFlushRate;
        private int mFlushCount;

        /**
         * Makes a new IStream that, while getting values, will record them in a CSV file
         *
         * @param filepath path of the CSV file to write to
         * @param stream IStream to read in from
         * @param flushRate how many times .get should be called before it flushes
         */
        public Writer(String filepath, IStream stream, int flushRate) {
            mStream = stream;
            mFlushRate = flushRate;
            mFlushCount = 0;
            try {
                mCSVFile = new CSVWriter(filepath);
            } catch (IOException e) {
                e.printStackTrace();
                mCSVFile = null;
            }
        }

        /**
         * Makes a new IStream that, while getting values, will record them in a CSV file
         *
         * @param filepath path of the CSV file to write to
         * @param stream IStream to read in from
         */
        public Writer(String filepath, IStream stream) {
            this(filepath, stream, 100);
        }

        /**
         * Gets the next value from the IStream and records it in a CSV file
         *
         * @return next value in the IStream
         */
        public double get() {
            double result = mStream.get();
            try {
                if (mCSVFile != null) {
                    mCSVFile.write(result);

                    if (++mFlushCount > mFlushRate) {
                        mFlushCount = 0;
                        mCSVFile.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    /** This CSVIStream Reader lets you replay a CSV file as if it was any other IStream */
    public static class Reader implements IStream {

        /** Iterator over the elements of the CSV file */
        private Iterator<CSVElement> mCSVData;

        /**
         * Makes a new IStream sourcing from a CSV file
         *
         * @param filepath path of the CSV file to read from
         */
        public Reader(String filepath) {
            try {
                mCSVData = new CSVReader(filepath).iterator();
            } catch (IOException e) {
                e.printStackTrace();
                mCSVData = null;
            }
        }

        /**
         * Get the next value from the CSV file
         *
         * @return next value of the CSV file
         */
        public double get() {
            if (mCSVData == null) return 0.0;
            return (mCSVData.hasNext()) ? mCSVData.next().toDouble() : 0.0;
        }
    }
}
