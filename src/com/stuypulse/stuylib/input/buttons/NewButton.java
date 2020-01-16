package com.stuypulse.stuylib.input.buttons;

import com.stuypulse.stuylib.input.buttons.BooleanProvider;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * This class will let us use the new button by 
 * providing a lambda for it to use
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NewButton extends Button {

    /**
     * what stores the functions that the button would call when get is calles
     */
    private BooleanProvider mButton;

    /**
     * Initializes NewButton with a lambda that will be used when calling .get()
     */
    public NewButton(BooleanProvider lambda) {
        this.mButton = lambda;
    }

    /**
     * Initializes NewButton with a lambda that always returns false
     */
    public NewButton() {
        this(() -> false);
    }

	/**
     * Override get funciton to make it work like a button
     * 
     * @return boolean that the lambda returns
     */
    @Override
    public boolean get() {
        return mButton.get();
    }
}