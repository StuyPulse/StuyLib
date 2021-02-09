package com.stuypulse.stuylib.input.gamepads;

public class KeyConfig {
    private static final String[] DEFAULT_CONFIG = {
        "d", "a", "w", "s",             //  getLeftX(), getLeftY()
        "l", "j", "i", "k",             //  getRightX(), getRightY()
        "up", "down", "left", "right",  //  getRawDPad<dir>()
        "e", "u",                       //  getRaw<dir>Bumper()
        "q", "o",                       //  get<dir>Trigger()
        "z", "x", "c", "v",             //  getRaw<dir>Button()
        "r", "t", "y",                  //  getRawStart/Select/OptionButton()
        "f", "h"                        //  getRaw<dir>AnalogButton()
    };

    private String[] keyBinding;

    public KeyConfig() {
        this(DEFAULT_CONFIG);
    }

    public KeyConfig(String[] config) {
        if (config.length == DEFAULT_CONFIG.length)
            keyBinding = config;
        else
            throw new IllegalArgumentException("Config array given to constructor is the wrong length!");
    }


    public String get(int index) {
        return keyBinding[index];
    }
}