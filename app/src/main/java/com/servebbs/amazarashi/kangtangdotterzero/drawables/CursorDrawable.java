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
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class CursorDrawable extends Drawable {
    private Paint paint;

    public CursorDrawable() {
        paint = new Paint();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final int iconSize = ScreenSize.getIconSize();
        final int dotSize = ScreenSize.getDotSize();
        final Rect bounds = getBounds();

        {
            canvas.drawBitmap(
                    DotIcon.getBitmap(),
                    new Rect(
                            16,
                            0,
                            16 + 8,
                            8
                    ),
                    new Rect(
                      bounds.left - dotSize,
                      bounds.top - dotSize,
                      bounds.left - dotSize + bounds.width()/2,
                      bounds.top - dotSize + bounds.height()/2
                    ),
                    paint);
        }
        {
            canvas.drawBitmap(
                    DotIcon.getBitmap(),
                    new Rect(
                            16 + 8,
                            0,
                            16 + 16,
                            8
                    ),
                    new Rect(
                            bounds.left + bounds.width()/2,
                            bounds.top - dotSize,
                            bounds.right,
                            bounds.top - dotSize + bounds.height()/2
                    ),
                    paint);
        }
        {
            canvas.drawBitmap(
                    DotIcon.getBitmap(),
                    new Rect(
                            16,
                            8,
                            16 + 8,
                            16
                    ),
                    new Rect(
                            bounds.left - dotSize,
                            bounds.top + bounds.height()/2,
                            bounds.left - dotSize + bounds.width()/2,
                            bounds.bottom
                    ),
                    paint);
        }
        {
            canvas.drawBitmap(
                    DotIcon.getBitmap(),
                    new Rect(
                            16 + 8,
                            8,
                            16 + 16,
                            16
                    ),
                    new Rect(
                            bounds.left + bounds.width()/2,
                            bounds.top + bounds.height()/2,
                            bounds.right,
                            bounds.bottom
                    ),
                    paint);
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

