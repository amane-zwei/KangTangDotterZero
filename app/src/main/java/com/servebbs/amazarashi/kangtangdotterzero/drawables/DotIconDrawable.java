package com.servebbs.amazarashi.kangtangdotterzero.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

public class DotIconDrawable extends Drawable {
    private final Paint paint = new Paint();
    private final Rect src = new Rect();
    private final Rect dst = new Rect();

    public Rect setSrcRect(DotIcon.DotIconData dotIconData) {
        return dotIconData.setRect(src);
    }

    protected Rect getSrc() {
        return src;
    }

    @Override
    public void onBoundsChange(Rect bounds) {
        dst.set(bounds);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(DotIcon.getBitmap(), src, dst, paint);
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
