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

import lombok.AllArgsConstructor;

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

        private static final SeekBarDrawer drawerH = new SeekBarDrawer(DotSeekBarDrawable::drawFrameH, DotSeekBarDrawable::drawBarH);
        private static final SeekBarDrawer drawerM = new SeekBarDrawer(DotSeekBarDrawable::drawFrameM, DotSeekBarDrawable::drawBarM);
        private static final SeekBarDrawer drawerL = new SeekBarDrawer(DotSeekBarDrawable::drawFrameL, DotSeekBarDrawable::drawBarL);

        @Override
        public void onBoundsChange(Rect bounds) {
            dstRect.set(bounds);
            frameRect.set(0, 0, bounds.width(), bounds.height());
            if (bounds.width() < 1 || bounds.height() < 1) {
                return;
            }

            frameBitmap = Bitmap.createBitmap(frameRect.width(), frameRect.height(), Bitmap.Config.ARGB_8888);
            barBitmap = Bitmap.createBitmap(frameRect.width(), frameRect.height(), Bitmap.Config.ARGB_8888);

            SeekBarDrawer drawer;
            final int height = frameRect.height();
            if (height < ScreenSize.getDotSize() * 6) {
                drawer = drawerL;
            } else if (height < ScreenSize.getDotSize() * 12) {
                drawer = drawerM;
            } else {
                drawer = drawerH;
            }

            drawer.frameDrawer.draw(
                    new Canvas(frameBitmap),
                    0,
                    0,
                    frameRect.width(),
                    frameRect.height(),
                    linePaint,
                    highLightPaint,
                    shadowPaint
            );
            drawer.barDrawer.draw(
                    new Canvas(barBitmap),
                    0,
                    0,
                    frameRect.width(),
                    frameRect.height(),
                    mainColorPaint
            );
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

        private static void drawFrameH(
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
            drawParts(canvas, rect, shadow, right - ds2, top + ds3, right - ds1, top + ds4);
            // shadow-right
            drawParts(canvas, rect, shadow, right - ds1, top + ds5, right - ds0, bottom - ds4);
            // shadow-bottom-right-right
            drawParts(canvas, rect, shadow, right - ds2, bottom - ds5, right - ds1, bottom - ds2);
            // shadow-bottom-bottom-right
            drawParts(canvas, rect, shadow, right - ds5, bottom - ds3, right - ds2, bottom - ds1);
            // shadow-bottom
            drawParts(canvas, rect, shadow, left + ds5, bottom - ds1, right - ds4, bottom - ds0);


            // left
            drawParts(canvas, rect, paint, left + ds0, top + ds4, left + ds1, bottom - ds5);
            // left-left-top
            drawParts(canvas, rect, paint, left + ds1, top + ds2, left + ds2, top + ds4);
            // left-top-top
            drawParts(canvas, rect, paint, left + ds2, top + ds1, left + ds4, top + ds2);
            // top
            drawParts(canvas, rect, paint, left + ds4, top + ds0, right - ds5, top + ds1);
            // right-top-top
            drawParts(canvas, rect, paint, right - ds5, top + ds1, right - ds3, top + ds2);
            // right-top-top
            drawParts(canvas, rect, paint, right - ds5, top + ds1, right - ds3, top + ds2);
            // right-right-top
            drawParts(canvas, rect, paint, right - ds3, top + ds2, right - ds2, top + ds4);
            // right
            drawParts(canvas, rect, paint, right - ds2, top + ds4, right - ds1, bottom - ds5);
            // right-right-bottom
            drawParts(canvas, rect, paint, right - ds3, bottom - ds5, right - ds2, bottom - ds3);
            // right-bottom-bottom
            drawParts(canvas, rect, paint, right - ds5, bottom - ds3, right - ds3, bottom - ds2);
            // bottom
            drawParts(canvas, rect, paint, left + ds4, bottom - ds2, right - ds5, bottom - ds1);
            // left-bottom-bottom
            drawParts(canvas, rect, paint, left + ds2, bottom - ds3, left + ds4, bottom - ds2);
            // left-left-bottom
            drawParts(canvas, rect, paint, left + ds1, bottom - ds5, left + ds2, bottom - ds3);


            int highlightBottom = top + ds1 * 8;
            if (highlightBottom > bottom - ds5) {
                highlightBottom = bottom - ds5;
            }
            // highlight-left
            drawParts(canvas, rect, highlight, left + ds2, top + ds4, left + ds3, highlightBottom);
            // highlight-left-top
            drawParts(canvas, rect, highlight, left + ds3, top + ds3, left + ds4, top + ds4);
            // highlight-top
            drawParts(canvas, rect, highlight, left + ds4, top + ds2, right - ds5, top + ds3);
        }

        private static void drawBarH(
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

            drawParts(canvas, rect, paint, left + ds1, top + ds4, right - ds2, bottom - ds5);
            drawParts(canvas, rect, paint, left + ds2, top + ds2, right - ds3, bottom - ds3);
            drawParts(canvas, rect, paint, left + ds4, top + ds1, right - ds5, bottom - ds2);
        }

        private static void drawFrameM(
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

            Rect rect = new Rect();

            // shadow-right
            drawParts(canvas, rect, shadow, right - ds1, top + ds3, right - ds0, bottom - ds2);
            // shadow-right-bottom
            drawParts(canvas, rect, shadow, right - ds3, bottom - ds3, right - ds1, bottom - ds1);
            // shadow-bottom
            drawParts(canvas, rect, shadow, left + ds3, bottom - ds1, right - ds2, bottom - ds0);

            // left
            drawParts(canvas, rect, paint, left + ds0, top + ds2, left + ds1, bottom - ds3);
            // left-top
            drawParts(canvas, rect, paint, left + ds1, top + ds1, left + ds2, top + ds2);
            // top
            drawParts(canvas, rect, paint, left + ds2, top + ds0, right - ds3, top + ds1);
            // top-right
            drawParts(canvas, rect, paint, right - ds3, top + ds1, right - ds2, top + ds2);
            // right
            drawParts(canvas, rect, paint, right - ds2, top + ds2, right - ds1, bottom - ds3);
            // right-bottom
            drawParts(canvas, rect, paint, right - ds3, bottom - ds3, right - ds2, bottom - ds2);
            // bottom
            drawParts(canvas, rect, paint, left + ds2, bottom - ds2, right - ds3, bottom - ds1);
            // left-bottom
            drawParts(canvas, rect, paint, left + ds1, bottom - ds3, left + ds2, bottom - ds2);

            // highlight-left
            drawParts(canvas, rect, highlight, left + ds2, top + ds3, left + ds3, bottom - ds4);
            // highlight-top
            drawParts(canvas, rect, highlight, left + ds3, top + ds2, right - ds4, top + ds3);
        }

        private static void drawBarM(
                Canvas canvas,
                int left,
                int top,
                int right,
                int bottom,
                Paint paint) {

            final int ds1 = ScreenSize.getDotSize();
            final int ds2 = ds1 + ds1;
            final int ds3 = ds2 + ds1;

            Rect rect = new Rect();

            drawParts(canvas, rect, paint, left + ds1, top + ds2, right - ds2, bottom - ds3);
            drawParts(canvas, rect, paint, left + ds2, top + ds1, right - ds3, bottom - ds2);
        }

        private static void drawFrameL(
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

            Rect rect = new Rect();

            // shadow-right
            drawParts(canvas, rect, shadow, right - ds1, top + ds2, right - ds0, bottom - ds1);
            // shadow-bottom
            drawParts(canvas, rect, shadow, left + ds2, bottom - ds2, right - ds1, bottom - ds0);

            // left
            drawParts(canvas, rect, paint, left + ds0, top + ds1, left + ds1, bottom - ds2);
            // top
            drawParts(canvas, rect, paint, left + ds1, top + ds0, right - ds2, top + ds1);
            // right
            drawParts(canvas, rect, paint, right - ds2, top + ds1, right - ds1, bottom - ds2);
            // bottom
            drawParts(canvas, rect, paint, left + ds1, bottom - ds2, right - ds2, bottom - ds1);
        }

        private static void drawBarL(
                Canvas canvas,
                int left,
                int top,
                int right,
                int bottom,
                Paint paint) {

            final int ds1 = ScreenSize.getDotSize();
            final int ds2 = ds1 + ds1;

            Rect rect = new Rect();

            drawParts(canvas, rect, paint, left + ds1, top + ds1, right - ds2, bottom - ds2);
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

        private static void drawParts(Canvas canvas, Rect rect, Paint paint, int left, int top, int right, int bottom) {
            rect.set(left, top, right, bottom);
            canvas.drawRect(rect, paint);
        }
    }

    @AllArgsConstructor
    private static class SeekBarDrawer {
        private final FrameDrawer frameDrawer;
        private final BarDrawer barDrawer;
    }

    private interface FrameDrawer {
        void draw(
                Canvas canvas,
                int left,
                int top,
                int right,
                int bottom,
                Paint paint,
                Paint highlight,
                Paint shadow);
    }

    private interface BarDrawer {
        void draw(
                Canvas canvas,
                int left,
                int top,
                int right,
                int bottom,
                Paint paint);
    }
}
