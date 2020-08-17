package com.servebbs.amazarashi.kangtangdotterzero.models.primitive;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.servebbs.amazarashi.kangtangdotterzero.R;

public class DotIcon {
    private static Bitmap bitmap;

    public static void init(Resources resources) {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon);
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }
}
