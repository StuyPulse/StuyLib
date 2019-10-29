package edu.stuylib.input.keyboard;

/**
 * This holds basic information for the 
 * Network Keyboard like the Default Table
 * to use when no table is given
 * 
 * It also hold other methods that should
 * be used in the network table keyboard
 */
public interface NetKeyboardInfo {
    
    /**
     * Gets name of network table for Network Keyboard
     * and its virtual port number
     * @param port virtual port number
     * @return network table name
     */
    public static String getTabelName(int port) {
        return ("NetworkKeyboard/port/" + Integer.toString(Math.abs(port)));
    }

    /**
     * Sanatize key names to prevent caps issues
     * @param key unsanatized key name
     * @return sanatized key name
     */
    public static String sanatize(String key) {
        return key.toUpperCase().trim();
    }
}