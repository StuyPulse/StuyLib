package com.stuypulse.stuylib.exception;

/**
 * Exception to be thrown when a parameter in construction is given an invalid
 * value
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class ConstructionError extends RuntimeException {

    /**
     * Serialized value of the class used by java
     */
    private static final long serialVersionUID = -8596724833623474026L;

    /**
     * Error Message
     */
    private String mError;

    /**
     * Create exception with formatted error message if construction is incorrectly
     * given constructor name and the error to be thrown
     * 
     * @param constructor constructor name formatted like 
     *                    "ClassName(Type Variable)"
     * @param error       reason why the variable is invalid
     */
    public ConstructionError(String constructor, String error) {
        mError = constructor + " -> " + error;
    }

    /**
     * Report error message through toString()
     * 
     * @return error message
     */
    public String toString() {
        return mError;
    }
}