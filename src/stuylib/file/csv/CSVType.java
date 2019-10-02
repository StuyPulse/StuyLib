package stuylib.file.csv;

/**
 * Contains all the information needed
 * for each of the csv files to work.
 */
public enum CSVType {

    DEFAULT(","), EXCEL(","), MYSQL("\t"), TCSV(",\t");

    // Stores Seperator for CSV 
    private String mDelimiter;

    // 
    private CSVType(String delimiter) {
        mDelimiter = delimiter;
    }

    public String getDelimiter() {
        return mDelimiter;
    }
}