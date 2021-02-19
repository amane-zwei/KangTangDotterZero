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

public class SingleDividerDrawable extends Drawable {

    private final Paint paint = new Paint();
    private final Rect rect = new Rect();
    private final Rect dst = new Rect();
    private final int size = ScreenSize.getDotSize();

    public SingleDividerDrawable() {
        setColor(0xff000000);
    }

    public SingleDividerDrawable(int color) {
        setColor(color);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public int getIntrinsicHeight() {
        return size * 3;
    }

    @Override
    public void onBoundsChange(Rect bounds) {
        dst.set(bounds);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final int size = this.size;
        final int delta = size + size;
        final int number = 3;

        int left = dst.left;
        int top = dst.top + size;
        int right = dst.right;
        int bottom = dst.bottom - size;

//        for (int index = 0; index < number; index++) {
//            rect.set(left, top, left + size, bottom);
//            canvas.drawRect(rect, paint);
//            left += delta;
//        }

        for (int index = 0; index < number; index++) {
            rect.set(right - size, top, right, bottom);
            canvas.drawRect(rect, paint);
            right -= delta;
        }

        rect.set(left, top, right, bottom);
        canvas.drawRect(rect, paint);
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