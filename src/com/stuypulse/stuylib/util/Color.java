package com.stuypulse.stuylib.util;

public class Color {
    private final int red;
    private final int green;
    private final int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public Color setRGB(int red, int green, int blue) {
        return new Color(red, green, blue);
    }

    /***********************/
    /*** COLOR CONSTANTS ***/
    /***********************/


    public static final Color AQUA = new Color(0, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color BLUE = new Color(0, 128, 255);
    public static final Color BLUE_GREEN = new Color(0, 255, 128);
    public static final Color BLUE_VIOLET = new Color(51, 51, 255);
    public static final Color DARK_BLUE = new Color(0, 0, 204);
    public static final Color DARK_GRAY = new Color(64, 64, 64);
    public static final Color DARK_GREEN = new Color(0, 153, 0);
    public static final Color DARK_RED = new Color(204, 0, 0);
    public static final Color GOLD = new Color(218,165,32);
    public static final Color GRAY = new Color(128, 128, 128);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color HOT_PINK = new Color(255, 105, 180);
    public static final Color LAWN_GREEN = new Color(102, 204, 0);
    public static final Color LIME = new Color(191, 255, 0);
    public static final Color ORANGE = new Color(255, 128, 0);
    public static final Color PINK = new Color(255, 192, 203);
    public static final Color PURPLE = new Color(160, 32, 240);
    public static final Color RED = new Color(255, 0 , 0);
    public static final Color RED_ORANGE = new Color(255, 83, 73);
    public static final Color VIOLET = new Color(127, 0, 255);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color YELLOW = new Color(255, 255, 0);

    public static final Color OFF = new Color(0, 0, 0);
    public static final Color RAINBOW = OFF;
}
