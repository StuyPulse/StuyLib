package stuylib.file.csv;

import java.io.FileWriter;
import java.io.IOException;

import stuylib.file.csv.CSVCommon;

/**
 * CSVWriter class that allows you to write to a 
 * file in the format of a CSV. It automatically
 * adds commas and calls toString on objects.
 * 
 * Every function will throw an IOException if it
 * arises.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class CSVWriter {

    // FileWriter that has CSV File open
    FileWriter mCSVFile;

    /**
     * Open CSV File with file name file
     * @param file file name
     * @throws IOException error with opening file
     */
    public CSVWriter(String file) throws IOException {
        open(file);
    }

    /**
     * Open CSV File with file name file
     * @param file file name
     * @throws IOException error with opening file
     */
    public void open(String file) throws IOException {
        mCSVFile = new FileWriter(file);
    }

    /**
     * Closes CSV File
     */
    public void close() throws IOException {
        mCSVFile.close();
    }

    /**
     * Write String to CSV file
     * @param data string
     * @throws IOException error writing to file
     */
    public void write(String data) throws IOException {
        if(data.contains(CSVCommon.SPLIT_TOKEN))
        {
            throw new IOException("Data being written to CSV contains comma!");
        } else {
            mCSVFile.append(data);
            mCSVFile.append(CSVCommon.SPLIT_TOKEN);
        }
    }

    /**
     * Write object to CSV file
     * @param data object that implements toString
     * @throws IOException error writing to file
     */
    public void write(Object data) throws IOException {
        write(data.toString());
    }

    /**
     * Write double to CSV file
     * @param data double that you want to write
     * @throws IOException error writing to file
     */
    public void write(double data) throws IOException {
        write(Double.toString(data));
    }

    /**
     * Write int to CSV file
     * @param data int that you want to write
     * @throws IOException error writing to file
     */
    public void write(int data) throws IOException {
        write(Integer.toString(data));
    }

    /**
     * Write long to CSV file
     * @param data long that you want to write
     * @throws IOException error writing to file
     */
    public void write(long data) throws IOException {
        write(Long.toString(data));
    }

    /**
     * Write object array to CSV file
     * @param data array of objects that implements toString
     * @throws IOException error writing to file
     */
    public void write(Object[] data) throws IOException {
        for(Object d : data) {
            write(d);
        }
    }

    /**
     * Write double array to CSV file
     * @param data array of doubles
     * @throws IOException error writing to file
     */
    public void write(double[] data) throws IOException {
        for(double d : data) {
            write(d);
        }
    }

    /**
     * Write int array to CSV file
     * @param data array of ints
     * @throws IOException error writing to file
     */
    public void write(int[] data) throws IOException {
        for(int d : data) {
            write(d);
        }
    }

    /**
     * Write long array to CSV file
     * @param data array of longs
     * @throws IOException error writing to file
     */
    public void write(long[] data) throws IOException {
        for(long d : data) {
            write(d);
        }
    }
}