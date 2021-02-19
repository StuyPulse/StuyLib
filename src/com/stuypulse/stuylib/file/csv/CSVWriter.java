// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.

package com.stuypulse.stuylib.file.csv;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;

/**
 * CSVWriter class that allows you to write to a file in the format of a CSV. It automatically adds
 * commas and calls toString on objects.
 *
 * <p>Every function will throw an IOException if it arises.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class CSVWriter implements Flushable, Closeable {

    // FileWriter that has CSV File open
    final FileWriter mCSVFile;

    // Stores Delimiter
    final CSVType mCSVType;

    /**
     * Open CSV File with file name file and csv type
     *
     * @param file file name
     * @param type type of csv file
     * @throws IOException error with opening file
     */
    public CSVWriter(String file, CSVType type) throws IOException {
        mCSVType = type;
        mCSVFile = new FileWriter(file);
    }

    /**
     * Open CSV File with file name file
     *
     * @param file file name
     * @throws IOException error with opening file
     */
    public CSVWriter(String file) throws IOException {
        this(file, CSVType.DEFAULT);
    }

    /** Closes CSV File */
    public void close() throws IOException {
        mCSVFile.close();
    }

    /** Flushes CSV File */
    public void flush() throws IOException {
        mCSVFile.flush();
    }

    /**
     * Write String to CSV file
     *
     * @param data string
     * @throws IOException error writing to file
     */
    public void write(String data) throws IOException {
        if (data.contains(mCSVType.getDelimiter())) {
            throw new IOException("Data being written to CSV contains Delimiter!");
        } else {
            mCSVFile.append(data);
            mCSVFile.append(mCSVType.getDelimiter());
        }
    }

    /**
     * Write object to CSV file
     *
     * @param data object that implements toString
     * @throws IOException error writing to file
     */
    public void write(Object data) throws IOException {
        write(data.toString());
    }

    /**
     * Write double to CSV file
     *
     * @param data double that you want to write
     * @throws IOException error writing to file
     */
    public void write(double data) throws IOException {
        write(Double.toString(data));
    }

    /**
     * Write long to CSV file
     *
     * @param data long that you want to write
     * @throws IOException error writing to file
     */
    public void write(long data) throws IOException {
        write(Long.toString(data));
    }
}
