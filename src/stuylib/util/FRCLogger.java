package stuylib.util;

import java.util.logging.SimpleFormatter;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

/** 
 * A class that will let you log any class
 * that implements loggable.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class FRCLogger {

    /**
     * Implementable interface with getLog() function.
     * This lets us pass in subsystems that implement
     * loggable to our logger in a very easy way.
     */
    public interface Loggable {
        public String getLog();
    }

    private Logger mLogger;
    private FileHandler mFileHandler;

    /**
     * Open FRCLogger
     * @param file file name
     */
    FRCLogger(String file) {
        mLogger = Logger.getLogger(FRCLogger.class.getName());

        try {
            mFileHandler = new FileHandler("./Logs/" + file + ".log");
        } catch(Exception e) {
            e.printStackTrace();
        }

        mFileHandler.setFormatter(new SimpleFormatter());
        mLogger.addHandler(mFileHandler);
    }

    public void log(Loggable in) {
        mLogger.info(
            in.getClass().getName().toUpperCase() + ":\n" + 
            in.getLog()
        );
    }

    public void logInfo(String info) {
        mLogger.info(info);
    }

    public void logError(String error) {
        mLogger.severe(error);
    }
}