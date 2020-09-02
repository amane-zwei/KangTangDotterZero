package com.servebbs.amazarashi.kangtangdotterzero.models.primitive;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.R;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DotIcon {
    private static Bitmap bitmap;

    public static void init(Resources resources) {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon);
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static final DotIconData pallet = new DotIconData(0, 0, 16, 16);
    public static final DotIconData cursor = new DotIconData(16, 0, 16, 16);
    public static final DotIconData seekBar = new DotIconData(32, 0, 32, 16);
    public static final DotIconData seekBarThumb = new DotIconData(96, 0, 8, 16);

    @Getter
    @AllArgsConstructor
    public static class DotIconData {
        private int left;
        private int top;
        private int width;
        private int height;

        public int getRight() {
            return left + width;
        }

        public int getBottom() {
            return top + height;
        }

        public Rect createRect() {
            return new Rect(left, top, getRight(), getBottom());
        }
    }
}
