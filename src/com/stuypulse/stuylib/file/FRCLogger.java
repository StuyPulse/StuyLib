package com.stuypulse.stuylib.file;

import java.util.logging.SimpleFormatter;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that will let you log any class that implements loggable.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 * @author Ivan (ivanw8288@gmail.com)
 * @author Ayan ()
 */

public class FRCLogger {
    /**
     * The Loggable interface should be implemented by a class that can 
     * return a useful piece of data that can be called on demand to be written
     * into the log.
     */
    public interface Loggable {
        public String putLog();
    }

    private Logger mLogger;
    private FileHandler mFileHandler;

    /**
     * Open a new FRCLogger
     * 
     * @param file Name of the file to be written to.
     */
    FRCLogger(String file) {
        mLogger = Logger.getLogger(FRCLogger.class.getName());

        try {
            mFileHandler = new FileHandler("./Logs/" + file + ".log");
        } catch (Exception e) {
            logError(this, e);
            e.printStackTrace();
        }

        mFileHandler.setFormatter(new SimpleFormatter());
        mLogger.addHandler(mFileHandler);
    }

    /**
     * Adds a new log entry to the log.
     * 
     * @param level Level of severity of the log entry.
     * @param T Object that is the source of the log entry.
     * @param toLog Log entry message.
     */
    public <T> void log(Level level, T obj, String toLog) {
        mLogger.log(level, obj.getClass().getName().toUpperCase() + ": ", toLog);
    }

    /**
     * Grabs next putInfo() from the class implementing Loggable, and logs the
     * returned data entry under the info tab with the class' name.
     * 
     * @param toLog Class implementing Loggable
     */
    public void log(Loggable toLog) {
        mLogger.info(toLog.getClass().getName().toUpperCase() + ":\n" + toLog.putLog());
    }

    /**
     * Log an error.  Should be used in conjunction with try/catch.
     * 
     * @param obj Object that is the source of the error.
     * @param e Exception, usually from try/catch
     */
    public <T> void logError(T obj, Exception e) {
        mLogger.severe("Exception thrown from " + obj.getClass().getName().toUpperCase() + ":\n" + e);
    }

}