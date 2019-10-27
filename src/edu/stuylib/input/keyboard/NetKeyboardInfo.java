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
    // Default table when nothing is givin
    public static final String DEFAULT_TABLE = "StuyLibNetworkKeyboard";

    // How to sanatize the inputs given to network
    // table so that it is not case sensitive
    public static String sanatize(String key) {
        return key.toUpperCase().trim();
    }
}