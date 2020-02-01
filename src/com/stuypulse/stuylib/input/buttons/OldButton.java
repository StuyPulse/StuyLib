package com.stuypulse.stuylib.input.buttons;

import com.stuypulse.stuylib.input.buttons.BooleanProvider;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This class will let us use the old button by 
 * providing a lambda for it to use
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class OldButton extends Button {

    /**
     * what stores the functions that the button would call when get is called
     */
    private BooleanProvider mButton;

    /**
     * Initializes OldButton with a lambda that will be used when calling .get()
     * 
     * @param lambda lambda that provides the boolean the button will use
     */
    public OldButton(BooleanProvider lambda) {
        this.mButton = lambda;
    }

    /**
     * Initializes OldButton with a lambda that always returns false
     */
    public OldButton() {
        this(() -> false);
    }

    /**
     * Override get function to make it work like a button
     * 
     * @return boolean that the lambda returns
     */
    @Override
    public boolean get() {
        return mButton.get();
    }
}