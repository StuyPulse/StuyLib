package com.stuypulse.stuylib.input;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This class lets you make a wpi button with
 * any function. This is useful as it removes
 * the need to create a seperate button class
 * for every special case.
 * 
 * To make a button, use:
 * new LambdaButton( [LAMBDA] );
 * 
 * This button will fire whenever Function()
 * returns true.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class LambdaButton extends Button {

    /**
     * Lets us recieve a function to use as a button
     */
    public interface ButtonCheck {
        public boolean get();
    }

    /**
     * what stores the functions that the button
     * would call when get is calles
     */
    private ButtonCheck mButton;

    /**
     * Initializes LambdaButton with a lambda that
     * always returns false
     */
    public LambdaButton() {
        this.mButton = () -> { return false; };
    }

    /**
     * Gpves class lambda that will be used in get()
     * @param lambda Boolean function used as button
     */
    public LambdaButton(ButtonCheck lambda) {
        this.mButton = lambda;
    }

    /**
     * Gives class lambda that will be used in get()
     * @param lambda Boolean function used as button
     */
    public void setButtonCheck(ButtonCheck lambda) {
        this.mButton = lambda;
    }

    /**
     * Gets the lambda that the button uses
     * @return the lambda
     */
    public ButtonCheck getButtonCheck() {
        return this.mButton;
    }

    /**
     * Override get funciton to make it work like a button
     * @return boolean that the lambda returns
     */
    @Override
    public boolean get() {
        return mButton.get();
    }
}