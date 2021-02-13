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

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DotIconDrawable extends Drawable {
    private final Paint paint = new Paint();
    @Getter
    private final Rect src = new Rect();
    @Getter
    private final Rect dst = new Rect();

    public DotIconDrawable(DotIcon.DotIconData dotIconData) {
        setSrcRect(dotIconData);
    }

    public Rect setSrcRect(DotIcon.DotIconData dotIconData) {
        return dotIconData.setRect(src);
    }
    public Rect setSrcRect(DotIcon.DotIconData dotIconData, int x, int y) {
        return dotIconData.setRect(src, x, y);
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
