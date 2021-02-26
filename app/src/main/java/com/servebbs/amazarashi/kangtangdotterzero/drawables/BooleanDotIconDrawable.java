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

public class BooleanDotIconDrawable extends Drawable {
    private static final Paint paint = new Paint();
    @Getter
    private final Rect srcT;
    @Getter
    private final Rect srcF;
    @Getter
    private final Rect dst = new Rect();
    private final Function function;

    public BooleanDotIconDrawable(Rect srcT, Rect srcF, Function function) {
        this.srcT = srcT;
        this.srcF = srcF;
        this.function = function;
    }

    public void setSrcRect(Rect srcT, Rect srcF) {
        this.srcT.set(srcT);
        this.srcF.set(srcF);
    }

    @Override
    public void onBoundsChange(Rect bounds) {
        dst.set(bounds);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect src = function.apply() ? srcT : srcF;
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

    public interface Function {
        boolean apply();
    }
}
