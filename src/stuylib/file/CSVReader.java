package stuylib.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Read CSV Files with a bunch of epic features.
 * After file is opened you can read the data like
 * it was an array. 
 * 
 * Each of the elements is stored as a custom type
 * called Element. Which prevents the need to constantly
 * parse the data over and over again if it is a number.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class CSVReader {

    // CSV Splitter
    private static final String SPLIT_TOKEN = ",";

    // File Path
    private String mCSVFilePath;

    // Data of CSV Reader
    private Vector<Element> mCSVData;

    /**
     * Open CSVReader with file
     * @param filePath file path
     */
    public CSVReader(String filePath) throws IOException {
        open(filePath);
    }

    /**
     * Open file
     * @param filePath file path
     */
    public void open(String filePath) throws IOException {
        mCSVFilePath = filePath;
        read();
    }

    /**
     * Clears data from object.
     * Recommended to call read() after this
     */
    public void clearData() {
        mCSVData = new Vector<Element>();
    }

    /**
     * Read data from opened file.
     * You can call multiple times if file is updated
     */
    public void read() throws IOException {
        clearData();

        BufferedReader mCSVReader = new BufferedReader(new FileReader(mCSVFilePath));

        String line;
        while((line = mCSVReader.readLine()) != null) {
            String[] lineInfo = line.split(SPLIT_TOKEN);

            for(String str : lineInfo) {
                mCSVData.add(new Element(str));
            }
        }

        mCSVReader.close();
    }

    /**
     * Get the file path of the CSV File
     * @return file path
     */
    public String getFilePath() {
        return mCSVFilePath;
    }

    /**
     * Check if CSVReader has any data
     * @return if CSVReader has any data
     */
    public boolean hasData() {
        return (mCSVData.size() > 0) && (mCSVFilePath != "");
    }

    /**
     * Get a String Array of the CSV Data
     * @return String array
     */
    public Element[] getCSVData() {
        Element[] data = new Element[mCSVData.size()];

        for(int i = 0; i < mCSVData.size(); ++i) {
            data[i] = mCSVData.get(i);
        }

        return data;
    }

    /**
     * Get amount of elements in csv file
     * @return the amout of elements in the csv file
     */
    public int size() {
        return mCSVData.size();
    }

    /**
     * Get element of CSV as custom element type
     * @param index element position
     * @return element as custom Element type
     * @throws IndexOutOfBoundsException if the index is higher than the amount of elements
     */
    public Element at(int index) throws IndexOutOfBoundsException {
        if(index < size()) {
            return mCSVData.get(index);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Get element of CSV as string
     * @param index element position
     * @return string of the element
     * @throws IndexOutOfBoundsException if the index is higher than the amount of elements
     */
    public String stringAt(int index) throws IndexOutOfBoundsException {
        return at(index).toString();
    }

    /**
     * Get element of CSV as double
     * @param index element position
     * @return double representation of the element
     * @throws IndexOutOfBoundsException if the index is higher than the amount of elements
     */
    public Number doubleAt(int index) throws IndexOutOfBoundsException {
        return at(index).toDouble();
    }


    /**
     * Get element of CSV as number
     * @param index element position
     * @return number representation of the element
     * @throws IndexOutOfBoundsException if the index is higher than the amount of elements
     */
    public Number numberAt(int index) throws IndexOutOfBoundsException {
        return at(index).toNumber();
    }

    /**
     * Test if element is a number
     * @param index element position
     * @return if element is of type double
     * @throws IndexOutOfBoundsException
     */
    public boolean isNumber(int index) throws IndexOutOfBoundsException {
        return at(index).isNumber();
    }

    /**
     * CSV File as string. This should represent
     * what the file looks like.
     * @return string representation of csv file
     */
    public String toString() {
        StringBuilder csv = new StringBuilder();

        for(int i = 0; i < mCSVData.size(); ++i) {
            csv.append(at(i).toNumber());
            csv.append(", ");
        }

        return csv.toString();
    }

    /**
     * Stores the data for each element in a 
     * custom class. This is to prevent the 
     * need to parse doubles over and over
     * again, when we can just store it as a
     * double.
     */
    public class Element {
        // If value is a number
        boolean mIsNumber;

        // Value as Double
        double mNumberValue;

        // Value as String
        String mStringValue;

        /**
         * Initialize with string
         * @param value desired value
         */
        public Element(String value) {
            setValue(value);
        }

        /**
         * Set internal value
         * @param value desired value
         * @return if value was a number
         */
        public boolean setValue(String value) {
            mStringValue = value.trim();
            try {
                mNumberValue = Double.parseDouble(mStringValue);
                mIsNumber = true;
            } catch (NumberFormatException e) {
                mNumberValue = Double.NaN;
                mIsNumber = false;
            }

            return mIsNumber;
        }
        
        /**
         * Value of element as a string
         * @return value as a string
         */
        public String asString() {
            return mStringValue;
        }

        /**
         * Value of element as a number
         * @return value as a number
         */
        public Number asNumber() {
            return Double.valueOf(mNumberValue);
        }

        /**
         * Value of element as a double
         * @return value as a double
         */
        public double asDouble() {
            return mNumberValue;
        }
        
        /**
         * Value of element as a string
         * @return value as a string
         */
        public String toString() {
            return asString();
        }

        /**
         * Value of element as a number
         * @return value as a number
         */
        public Number toNumber() {
            return asNumber();
        }

        /**
         * Value of element as a double
         * @return value as a double
         */
        public double toDouble() {
            return asDouble();
        }

        /**
         * Returns if the element is a string
         * @return if the element is a string
         */
        public boolean isString() {
            return !mIsNumber;
        }

        /**
         * Returns if the element is a number
         * @return if the element is a number
         */
        public boolean isNumber() {
            return mIsNumber;
        }

        /**
         * Returns if the element is a double
         * @return if the element is a double
         */
        public boolean isDouble() {
            return mIsNumber;
        }
    }
}