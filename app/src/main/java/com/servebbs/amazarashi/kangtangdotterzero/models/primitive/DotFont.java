package com.servebbs.amazarashi.kangtangdotterzero.models.primitive;

import android.graphics.Typeface;

public class DotFont {

    private static Typeface dotTypeface;

    public static void setDotTypeface(Typeface typeface) {
        dotTypeface = typeface;
    }

    public static Typeface getDotTypeface() {
        return dotTypeface;
    }
}
