/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stuylib.io;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.microedition.io.Connector;

/**
 * This is a helper class for UserIO.  It handles logging logic for the UserOutput write() function, by determining whether to:
 * 1. print data to stdout
 * 2. aggregate data, then print all at once in the end
 * 3. write data to a logfile
 *
 * NOTE: When using this class, you MUST call the close() method if you wish for expected behavior!
 * @author Alejandro Carrillo
 */
public class Log {

    private StringBuffer logString; // For DEBUG_CATSTR mode
    private int logState;
    // Three possible states
    public static final int DEBUG_STDOUT = 0;  // For printing log data to stdout, the default mode
    public static final int DEBUG_CATSTR = 1;  // For appending log data to a string, which is printed at the end
    public static final int FMS_WRITELOG = 2;  // When in FMS mode, will write data to a log file
    private OutputStreamWriter writer;

    /**
     * If DEBUG_STDOUT is passed, then the write() method will print data to stdout
     * If DEBUG_CATSTR is passed, then the write() method will append data to a String, which is printed once
     * Finally, passing FMS_WRITELOG will write data to a logfile
     *
     * @param state Either DEBUG_STDOUT, DEBUG_CATSTR, or FMS_WRITELOG.  Using any other value will default to DEBUG_STDOUT behavior.
     */
    public Log(int state) {
        logState = state;
        if (logState == DEBUG_CATSTR) {
            logString = new StringBuffer("");
        }
        else if (logState == FMS_WRITELOG) {
            try {
                String url = "file:///competition.log";
                FileConnection c = (FileConnection) Connector.open(url);
                writer = new OutputStreamWriter(c.openOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prepares regular input for writing via the writer() method
     * @param s message string
     * @param logger calling object
     */
    public void write(String message, Object logger) {
        /* pseudocode */
        String prefix = "[" + System.currentTimeMillis() + " @" + logger.getClass().getName() + " ] INFO - "; // Records timestamp and origin
        String data = prefix + message;

        writer(data);  // Logs the message string
    }

    /**
     * Prepares exception data for writing via the writer() method
     *
     * @param e Exception object
     * @param logger calling object
     */
    public void write(Exception e, Object logger) {
        /* pseudocode */
        String prefix = "[" + System.currentTimeMillis() + " @" + logger.getClass().getName() + " ] ERROR - "; // Records timestamp and origin
        String data = prefix + e.getMessage(); // Creates data String from Exception data

        writer(data); // Logs the message String
    }

    /**
     * Outputs data to one of three places:
     * 1. stdout if we're in DEBUG_STDOUT mode
     * 2. the end of a string, which will be printed out later
     * 3. a logfile if we're in FMS_WRITELOG mode
     *
     * @param data the data to be written
     */
    private void writer(String data) {
        switch (logState) {

            case DEBUG_CATSTR:
                logString.append(data + "\n"); // Appends data to the logString
                break;
            case FMS_WRITELOG:
                try {
                    writer.write(data);        // Saves data to a logfile on the cRIO
                } catch (IOException f) {
                    f.printStackTrace();
                }
                break;
            default:
                System.out.println(data);      // Prints data to stdout, the default behavior
                break;
        }
    }

    /**
     * MUST be called in order to close logfile or print data.
     *
     * It is recommended that this be called once when trasitioning from enabled -> disabled,
     * e.g. right after the program exits the operatorControl() method's while loop if you use SimpleRobot.
     *
     * This method should be called regardless of mode.  If in DEBUG_STDOUT, this method does nothing, so it's safe.
     */
    public void close() {
        if (logState == FMS_WRITELOG) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (logState == DEBUG_CATSTR) {
            System.out.println(logString); // Print out all the data collected over time via DEBUG_CATSTR mode
        }

    }
}






