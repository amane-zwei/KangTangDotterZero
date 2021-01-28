package com.servebbs.amazarashi.kangtangdotterzero.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;

public class UnderLineDrawable extends Drawable {

    private Paint paint;

    public UnderLineDrawable() {
        paint = new Paint();
    }

    public UnderLineDrawable(int color) {
        paint = new Paint();
        setColor(color);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final int number = 3;

        final Rect bounds = getBounds();
        final int strokeWidth = ScreenSize.getIconSize() / 16;

        canvas.drawRect(bounds.left, bounds.bottom - strokeWidth, bounds.right, bounds.bottom, paint);
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}