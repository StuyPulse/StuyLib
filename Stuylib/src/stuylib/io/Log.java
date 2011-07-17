/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stuylib.io;

//TODO: Import WPIlibj stuff
/**
 *
 * @author Ginkgo
 */
public class Log {
    
    private StringBuffer concatString;
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
        /*
         * switch (robot_status) {
         * case debug_mode:
         *     System.out.println(s);
         *     break;
         * case comp_mode:
         *     Append s to a file, neatly
         *     break;
         * case concat_mode:
         *     concatString += s;
         *     break;
         * 
         */
    }
    
    
}
