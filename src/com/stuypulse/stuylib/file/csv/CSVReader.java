package com.stuypulse.stuylib.file.csv;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.Iterator;

import com.stuypulse.stuylib.file.csv.CSVElement;
import com.stuypulse.stuylib.file.csv.CSVType;

/**
 * Read CSV Files with a bunch of epic features. After file is opened you can
 * read the data like it was an array.
 * 
 * Each of the elements is stored as a custom type called Element. Which
 * prevents the need to constantly parse the data over and over again if it is a
 * number.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class CSVReader implements Iterable<CSVElement> {

    // File Path
    private String mCSVFilePath;

    // Data of CSV Reader
    private Vector<CSVElement> mCSVData;

    // Stores Delimiter
    private CSVType mCSVType;

    /**
     * Open CSVReader with file and CSV type
     * 
     * @param file file path
     * @param type CSV type
     */
    public CSVReader(String file, CSVType type) throws IOException {
        setCSVType(type);
        open(file);
    }

    /**
     * Open CSVReader with file
     * 
     * @param file file path
     */
    public CSVReader(String file) throws IOException {
        this(file, CSVType.DEFAULT);
    }

    /**
     * Open file
     * 
     * @param file file path
     */
    public void open(String file) throws IOException {
        mCSVFilePath = file;
        read();
    }

    /**
     * Set CSV type
     * 
     * @param type CSV Type
     */
    private void setCSVType(CSVType type) {
        mCSVType = type;
    }

    /**
     * Clears data from object. Recommended to call read() after this
     */
    public void clearData() {
        mCSVData = new Vector<CSVElement>();
    }

    /**
     * Read data from opened file. You can call multiple times if file is updated
     */
    public void read() throws IOException {
        clearData();

        Scanner csvfile = new Scanner(new File(mCSVFilePath));
        csvfile.useDelimiter(mCSVType.getDelimiter());

        while (csvfile.hasNext()) {
            mCSVData.add(new CSVElement(csvfile.next()));
        }

        csvfile.close();
    }

    /**
     * Get the file path of the CSV File
     * 
     * @return file path
     */
    public String getFilePath() {
        return mCSVFilePath;
    }

    /**
     * Check if CSVReader has any data
     * 
     * @return if CSVReader has any data
     */
    public boolean hasData() {
        return (mCSVData.size() > 0) && (mCSVFilePath != "");
    }

    /**
     * Get a String Array of the CSV Data
     * 
     * @return String array
     */
    public CSVElement[] getCSVData() {
        CSVElement[] data = new CSVElement[mCSVData.size()];

        for (int i = 0; i < mCSVData.size(); ++i) {
            data[i] = mCSVData.get(i);
        }

        return data;
    }

    /**
     * Get amount of elements in csv file
     * 
     * @return the amout of elements in the csv file
     */
    public int size() {
        return mCSVData.size();
    }

    /**
     * Get element of CSV as CSVElement type
     * 
     * @param index element position
     * @return element as custom CSVElement type
     * @throws IndexOutOfBoundsException if the index is higher than the amount of
     *                                   elements
     */
    public CSVElement get(int index) throws IndexOutOfBoundsException {
        if (index < size()) {
            return mCSVData.get(index);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Get element of CSV as string
     * 
     * @param index element position
     * @return string of the element
     * @throws IndexOutOfBoundsException if the index is higher than the amount of
     *                                   elements
     */
    public String getString(int index) throws IndexOutOfBoundsException {
        return get(index).toString();
    }

    /**
     * Get element of CSV as double
     * 
     * @param index element position
     * @return double representation of the element
     * @throws IndexOutOfBoundsException if the index is higher than the amount of
     *                                   elements
     */
    public double getDouble(int index) throws IndexOutOfBoundsException {
        return get(index).toDouble();
    }

    /**
     * Get element of CSV as number
     * 
     * @param index element position
     * @return number representation of the element
     * @throws IndexOutOfBoundsException if the index is higher than the amount of
     *                                   elements
     */
    public Number getNumber(int index) throws IndexOutOfBoundsException {
        return get(index).toNumber();
    }

    /**
     * Test if element is a number
     * 
     * @param index element position
     * @return if element is of type double
     * @throws IndexOutOfBoundsException
     */
    public boolean isNumber(int index) throws IndexOutOfBoundsException {
        return get(index).isNumber();
    }

    /**
     * CSV File as string. This should represent what the file looks like.
     * 
     * @return string representation of csv file
     */
    public String toString() {
        StringBuilder csv = new StringBuilder();

        for (int i = 0; i < mCSVData.size(); ++i) {
            csv.append(get(i).toNumber());
            csv.append(mCSVType.getDelimiter());
        }

        return csv.toString();
    }

    /**
     * Allows the CSVReader class to be iterated over
     * 
     * @return iterator of type CSVElement
     */
    public Iterator<CSVElement> iterator() {
        return mCSVData.iterator();
    }
}