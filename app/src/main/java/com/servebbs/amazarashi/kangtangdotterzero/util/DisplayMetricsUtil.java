package com.servebbs.amazarashi.kangtangdotterzero.util;

import android.content.Context;

public class DisplayMetricsUtil {
    public static int calcPixel(Context context, int dp) {
        return (int)(context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public static int calcHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
