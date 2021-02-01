package com.servebbs.amazarashi.kangtangdotterzero.domains.primitive;

import android.graphics.Typeface;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;

public class DotFont {

    public static final int normalHeight = ScreenSize.getIconSize() / 2;
    public static final int numericWidth = ScreenSize.getDotSize() * 5;

    private static Typeface dotTypeface;

    public static void setDotTypeface(Typeface typeface) {
        dotTypeface = typeface;
    }

    public static Typeface getDotTypeface() {
        return dotTypeface;
    }
}
