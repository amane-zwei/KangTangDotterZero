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

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DotRoundRectDrawable extends Drawable {

    public static final int paddingLeft = ScreenSize.getDotSize() * 2;
    public static final int paddingTop = ScreenSize.getDotSize() * 2;
    public static final int paddingRight = ScreenSize.getDotSize() * 3;
    public static final int paddingBottom = ScreenSize.getDotSize() * 3;

    private int alpha = 0xff;

    private final Paint backgroundPaint = new Paint();
    private final Paint linePaint = new Paint();
    private final Paint shadowPaint = new Paint();

    @Getter
    private final Rect backGroundRect = new Rect();

    private final Rect l = new Rect();
    private final Rect lt = new Rect();
    private final Rect t = new Rect();
    private final Rect rt = new Rect();
    private final Rect r = new Rect();
    private final Rect rb = new Rect();
    private final Rect b = new Rect();
    private final Rect lb = new Rect();

    private final Rect sr = new Rect();
    private final Rect srb = new Rect();
    private final Rect sb = new Rect();

    public DotRoundRectDrawable(int backgroundColor, int lineColor, int shadowColor) {
        setBackgroundColor(backgroundColor);
        setLineColor(lineColor);
        setShadowColor(shadowColor);
    }

    public void setBackgroundColor(int color) {
        backgroundPaint.setColor(color);
    }

    public void setLineColor(int color) {
        linePaint.setColor(color);
    }

    public void setShadowColor(int color) {
        shadowPaint.setColor(color);
    }

    @Override
    public void onBoundsChange(Rect bounds) {
        final int ds = ScreenSize.getDotSize();
        final int dds = ds * 2;
        final int ddds = dds + ds;

        final int left = bounds.left;
        final int top = bounds.top;
        final int right = bounds.right;
        final int bottom = bounds.bottom;

        backGroundRect.set(left + ds, top + ds, right - dds, bottom - dds);

        l.set(left, top + dds, left + ds, bottom - ddds);
        lt.set(left + ds, top + ds, left + dds, left + dds);
        t.set(left + dds, top, right - ddds, top + ds);
        rt.set(right - ddds, top + ds, right - dds, top + dds);
        r.set(right - dds, top + dds, right - ds, bottom - ddds);
        rb.set(right - ddds, bottom - ddds, right - dds, bottom - dds);
        b.set(left + dds, bottom - dds, right - ddds, bottom - ds);
        lb.set(left + ds, bottom - ddds, left + dds, bottom - dds);

        sr.set(right - ds, top + ddds, right, bottom - dds);
        srb.set(right - ddds, bottom - ddds, right - ds, bottom - ds);
        sb.set(left + ddds, bottom - ds, right - dds, bottom);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        canvas.drawRect(backGroundRect, backgroundPaint);

        canvas.drawRect(l, linePaint);
        canvas.drawRect(lt, linePaint);
        canvas.drawRect(t, linePaint);
        canvas.drawRect(rt, linePaint);
        canvas.drawRect(r, linePaint);
        canvas.drawRect(rb, linePaint);
        canvas.drawRect(b, linePaint);
        canvas.drawRect(lb, linePaint);

        canvas.drawRect(sr, shadowPaint);
        canvas.drawRect(srb, shadowPaint);
        canvas.drawRect(sb, shadowPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
