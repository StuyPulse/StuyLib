/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stuylib.io;

//TODO: Import WPIlibj stuff
/**
 *
 * @author Alejandro Carrillo
 */
public class Log {
    
    private StringBuffer concatString;
    private int logState;

    private static final int DEBUG_STDOUT_MODE  = 0;
    private static final int DEBUG_CATSTR_MODE  = 1;
    private static final int FMS_WRITELOG_MODE  = 2;

    public Log(int state) {
       logState = state;
    }
    /**
     * Determines what to do with input:
     * 1. Print to stdout
     * 2. Write to log file
     * 3. Append data to StringBuffer, then print StringBuffer to stdout when disabled
     * 
     * Uses both user specifications and FMS data to determine which route it can take
     * @param s 
     */
    public void write(String s) {
        /* pseudocode */
        
         switch (logState) {
        //TODO:  Implement this
         
         case DEBUG_STDOUT_MODE:
             System.out.println(s);
             break;
         case DEBUG_CATSTR_MODE:
             concatString.append(s);
             break;
         case FMS_WRITELOG_MODE:
             //Write to a file
             break;

         
         }
    
    }
}
