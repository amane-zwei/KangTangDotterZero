package com.servebbs.amazarashi.kangtangdotterzero.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;

public class DividerDrawable extends Drawable {

    private Paint paint;
    private Paint strokePaint;

    public DividerDrawable() {
        paint = new Paint();
        strokePaint = new Paint();
        strokePaint.setColor(0xff000000);
    }

    public DividerDrawable(int color) {
        paint = new Paint();
        setColor(color);
        strokePaint = new Paint();
        strokePaint.setColor(0xff000000);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final int number = 3;

        final Rect bounds = getBounds();
        final int size = bounds.height();
        final int delta = size / 2;
        final int strokeWidth = ScreenSize.getIconSize() / 16;

        int edge = bounds.width() - (size + delta) * number;
        canvas.drawRect(0, 0, edge, size, strokePaint);
        canvas.drawRect(strokeWidth, strokeWidth, edge - strokeWidth, size - strokeWidth, paint);

        edge += delta;
        for (int index = 0; index < number; index++, edge += size + delta) {
            canvas.drawRect(edge, 0, edge + size, size, strokePaint);
            canvas.drawRect(edge + strokeWidth, strokeWidth, edge + size - strokeWidth, size - strokeWidth, paint);
        }
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