package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;

public class DotSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    DotSeekBarDrawable drawable;

    public DotSeekBar(Context context) {
        super(context);

        setProgressDrawable(drawable = new DotSeekBarDrawable());
        setThumb(new GradientDrawable());
    }

    public void setColor(int mainColor, int subColor) {
        drawable.setColor(mainColor, subColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        final int trackHeight = h - getPaddingTop() - getPaddingBottom();
        final Drawable track = getProgressDrawable();
        final Drawable thumb = getThumb();

        if (track != null) {
            final int trackWidth = w - getPaddingRight() - getPaddingLeft();
            track.setBounds(0, 0, trackWidth, trackHeight);
        }

        if (thumb != null) {
            final int thumbHeight = thumb.getIntrinsicHeight();
            final int thumbWidth = thumb.getIntrinsicWidth();
            final int thumbOffset = (trackHeight - thumbHeight) / 2;
            thumb.setBounds(0, thumbOffset, thumbWidth, thumbOffset + thumbHeight);
        }
    }

    static class DotSeekBarDrawable extends Drawable {
        static final Paint linePaint;
        static final Paint highLightPaint;
        static final Paint shadowPaint;
        static final Paint barPaint;

        static {
            linePaint = new Paint();
            linePaint.setColor(0xff000000);

            highLightPaint = new Paint();
            highLightPaint.setColor(0xffffffff);

            shadowPaint = new Paint();
            shadowPaint.setColor(0x40000000);

            barPaint = new Paint();
            barPaint.setColor(0xffffffff);
        }

        private final Paint paint;
        private final Paint mainColorPaint;

        private final Rect dstRect;
        private final Rect dstBarRect;

        private Bitmap frameBitmap;
        private final Rect frameRect;

        private Bitmap barBitmap;
        private final Rect barRect;


        public DotSeekBarDrawable() {
            paint = new Paint();

            mainColorPaint = new Paint();

            dstRect = new Rect();
            dstBarRect = new Rect();

            frameBitmap = null;
            frameRect = new Rect();

            barBitmap = null;
            barRect = new Rect();
        }

        public void setColor(int mainColor, int subColor) {
            mainColorPaint.setColor(mainColor);
        }

        @Override
        public void onBoundsChange(Rect bounds) {
            dstRect.set(bounds);
            frameRect.set(0, 0, bounds.width(), bounds.height());
            if (bounds.width() < 1 || bounds.height() < 1) {
                return;
            }

            frameBitmap = Bitmap.createBitmap(frameRect.width(), frameRect.height(), Bitmap.Config.ARGB_8888);
            barBitmap = Bitmap.createBitmap(frameRect.width(), frameRect.height(), Bitmap.Config.ARGB_8888);

            if (frameRect.height() < ScreenSize.getDotSize() * 12) {
                drawFrameL(
                        new Canvas(frameBitmap),
                        0,
                        0,
                        frameRect.width(),
                        frameRect.height(),
                        linePaint,
                        highLightPaint,
                        shadowPaint
                );

                drawBarL(
                        new Canvas(barBitmap),
                        0,
                        0,
                        frameRect.width(),
                        frameRect.height(),
                        mainColorPaint
                );
            } else {
                drawFrameH(
                        new Canvas(frameBitmap),
                        0,
                        0,
                        frameRect.width(),
                        frameRect.height(),
                        linePaint,
                        highLightPaint,
                        shadowPaint
                );

                drawBarH(
                        new Canvas(barBitmap),
                        0,
                        0,
                        frameRect.width(),
                        frameRect.height(),
                        mainColorPaint
                );
            }
        }

        @Override
        public void draw(@NonNull Canvas canvas) {

            final int dotSize = ScreenSize.getDotSize();
            final int width = frameRect.width() - dotSize * 3;
            final int height = frameRect.height();
            final int border = dotSize + width * getLevel() / 10000;
            barRect.set(0, 0, border, height);
            dstBarRect.set(dstRect.left, dstRect.top, dstRect.left + border, dstRect.bottom);
            canvas.drawBitmap(barBitmap, barRect, dstBarRect, paint);

            canvas.drawBitmap(frameBitmap, frameRect, dstRect, paint);
        }

        private void drawFrameH(
                Canvas canvas,
                int left,
                int top,
                int right,
                int bottom,
                Paint paint,
                Paint highlight,
                Paint shadow) {

            final int ds0 = 0;
            final int ds1 = ScreenSize.getDotSize();
            final int ds2 = ds1 + ds1;
            final int ds3 = ds2 + ds1;
            final int ds4 = ds3 + ds1;
            final int ds5 = ds4 + ds1;

            Rect rect = new Rect();

            // shadow-right-top
            rect.set(right - ds2, top + ds3, right - ds1, top + ds4);
            canvas.drawRect(rect, shadow);
            // shadow-right
            rect.set(right - ds1, top + ds5, right - ds0, bottom - ds4);
            canvas.drawRect(rect, shadow);
            // shadow-bottom-right-right
            rect.set(right - ds2, bottom - ds5, right - ds1, bottom - ds2);
            canvas.drawRect(rect, shadow);
            // shadow-bottom-bottom-right
            rect.set(right - ds5, bottom - ds3, right - ds2, bottom - ds1);
            canvas.drawRect(rect, shadow);
            // shadow-bottom
            rect.set(left + ds5, bottom - ds1, right - ds4, bottom - ds0);
            canvas.drawRect(rect, shadow);


            // left
            rect.set(left + ds0, top + ds4, left + ds1, bottom - ds5);
            canvas.drawRect(rect, paint);
            // left-left-top
            rect.set(left + ds1, top + ds2, left + ds2, top + ds4);
            canvas.drawRect(rect, paint);
            // left-top-top
            rect.set(left + ds2, top + ds1, left + ds4, top + ds2);
            canvas.drawRect(rect, paint);
            // top
            rect.set(left + ds4, top + ds0, right - ds5, top + ds1);
            canvas.drawRect(rect, paint);
            // right-top-top
            rect.set(right - ds5, top + ds1, right - ds3, top + ds2);
            canvas.drawRect(rect, paint);
            // right-top-top
            rect.set(right - ds5, top + ds1, right - ds3, top + ds2);
            canvas.drawRect(rect, paint);
            // right-right-top
            rect.set(right - ds3, top + ds2, right - ds2, top + ds4);
            canvas.drawRect(rect, paint);
            // right
            rect.set(right - ds2, top + ds4, right - ds1, bottom - ds5);
            canvas.drawRect(rect, paint);
            // right-right-bottom
            rect.set(right - ds3, bottom - ds5, right - ds2, bottom - ds3);
            canvas.drawRect(rect, paint);
            // right-bottom-bottom
            rect.set(right - ds5, bottom - ds3, right - ds3, bottom - ds2);
            canvas.drawRect(rect, paint);
            // bottom
            rect.set(left + ds4, bottom - ds2, right - ds5, bottom - ds1);
            canvas.drawRect(rect, paint);
            // left-bottom-bottom
            rect.set(left + ds2, bottom - ds3, left + ds4, bottom - ds2);
            canvas.drawRect(rect, paint);
            // left-left-bottom
            rect.set(left + ds1, bottom - ds5, left + ds2, bottom - ds3);
            canvas.drawRect(rect, paint);


            int highlightBottom = top + ds1 * 8;
            if (highlightBottom > bottom - ds5) {
                highlightBottom = bottom - ds5;
            }
            // highlight-left
            rect.set(left + ds2, top + ds4, left + ds3, highlightBottom);
            canvas.drawRect(rect, highlight);
            // highlight-left-top
            rect.set(left + ds3, top + ds3, left + ds4, top + ds4);
            canvas.drawRect(rect, highlight);
            // highlight-top
            rect.set(left + ds4, top + ds2, right - ds5, top + ds3);
            canvas.drawRect(rect, highlight);
        }

        private void drawBarH(
                Canvas canvas,
                int left,
                int top,
                int right,
                int bottom,
                Paint paint) {

            final int ds1 = ScreenSize.getDotSize();
            final int ds2 = ds1 + ds1;
            final int ds3 = ds2 + ds1;
            final int ds4 = ds3 + ds1;
            final int ds5 = ds4 + ds1;

            Rect rect = new Rect();

            // 0
            rect.set(left + ds1, top + ds4, right - ds2, bottom - ds5);
            canvas.drawRect(rect, paint);
            // 1
            rect.set(left + ds2, top + ds2, right - ds3, bottom - ds3);
            canvas.drawRect(rect, paint);
            // 2
            rect.set(left + ds4, top + ds1, right - ds5, bottom - ds2);
            canvas.drawRect(rect, paint);
        }

        private void drawFrameL(
                Canvas canvas,
                int left,
                int top,
                int right,
                int bottom,
                Paint paint,
                Paint highlight,
                Paint shadow) {

            final int ds0 = 0;
            final int ds1 = ScreenSize.getDotSize();
            final int ds2 = ds1 + ds1;
            final int ds3 = ds2 + ds1;
            final int ds4 = ds3 + ds1;
            final int ds5 = ds4 + ds1;

            Rect rect = new Rect();

            // shadow-right
            rect.set(right - ds1, top + ds2, right - ds0, bottom - ds1);
            canvas.drawRect(rect, shadow);
            // shadow-bottom
            rect.set(left + ds2, bottom - ds2, right - ds1, bottom - ds0);
            canvas.drawRect(rect, shadow);

            // left
            rect.set(left + ds0, top + ds1, left + ds1, bottom - ds2);
            canvas.drawRect(rect, paint);
            // top
            rect.set(left + ds1, top + ds0, right - ds2, top + ds1);
            canvas.drawRect(rect, paint);
            // right
            rect.set(right - ds2, top + ds1, right - ds1, bottom - ds2);
            canvas.drawRect(rect, paint);
            // bottom
            rect.set(left + ds1, bottom - ds2, right - ds2, bottom - ds1);
            canvas.drawRect(rect, paint);
        }

        private void drawBarL(
                Canvas canvas,
                int left,
                int top,
                int right,
                int bottom,
                Paint paint) {

            final int ds1 = ScreenSize.getDotSize();
            final int ds2 = ds1 + ds1;

            Rect rect = new Rect();

            rect.set(left + ds1, top + ds1, right - ds2, bottom - ds2);
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
}
