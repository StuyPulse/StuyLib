package com.stuypulse.stuylib.input.buttons;

import com.stuypulse.stuylib.input.buttons.BooleanProvider;
import com.stuypulse.stuylib.input.buttons.NewButton;
import com.stuypulse.stuylib.input.buttons.ButtonWrapper;

/**
 * This class helps us bridge the gap between new and old code
 * by allowing one button class to provide a new and old button.
 * 
 * The main difference between the two is the commands that they use.
 * 
 * NewButton is used by default and if you want to use the old button,
 * you will have to use .old() in order to get it.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class ButtonWrapper extends NewButton {

    /**
     * A variable that stores the old button in case it needs to be used
     */
    private OldButton mOldButton;

    /**
     * Initializes ButtonWrapper with a lambda that will be used when calling .get()
     */
    public ButtonWrapper(BooleanProvider lambda) {
        super(lambda);
        mOldButton = new OldButton(lambda);
    }

    /**
     * Initializes ButtonWrapper with a lambda that always returns false
     */
    public ButtonWrapper() {
        this(() -> false);
    }

    /**
     * Get the the old button version of this class for use in legacy code
     * 
     * @return the old wpi button class
     */
    public OldButton old() {
        return mOldButton;
    }
}