package com.servebbs.amazarashi.kangtangdotterzero.domains;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.util.DisplayMetricsUtil;

public class ScreenSize {
    private static int height;
    private static int iconSize;
    private static int dotSize;
    private static int padding;
    private static int margin;

    public static void init(Context context) {
        height = DisplayMetricsUtil.calcHeight(context);
        iconSize = DisplayMetricsUtil.calcPixel(context, 64);
        dotSize = iconSize / 16;
        padding = DisplayMetricsUtil.calcPixel(context, 10);
        margin = DisplayMetricsUtil.calcPixel(context, 4);
    }

    public static int getHeight() { return height; }
    public static int getIconSize() { return iconSize; }
    public static int getDotSize() { return dotSize; }
    public static int getPadding() { return padding; }
    public static int getMargin() { return margin; }
}
