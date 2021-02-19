// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.

package com.stuypulse.stuylib.input.buttons;

/**
 * This class helps us bridge the gap between new and old code by allowing one button class to
 * provide a new and old button.
 *
 * <p>The main difference between the two is the commands that they use.
 *
 * <p>NewButton is used by default and if you want to use the old button, you will have to use
 * .old() in order to get it.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class ButtonWrapper extends NewButton {

    /** A variable that stores the old button in case it needs to be used */
    private OldButton mOldButton;

    /**
     * Initializes ButtonWrapper with a lambda that will be used when calling .get()
     *
     * @param lambda lambda that provides the boolean the button will use
     */
    public ButtonWrapper(BooleanProvider lambda) {
        super(lambda);
        mOldButton = new OldButton(lambda);
    }

    /** Initializes ButtonWrapper with a lambda that always returns false */
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
