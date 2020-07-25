package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class DotRectView extends View {

    public DotRectView(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawFrame(canvas, 0xff000000, 0xffffffff);
    }

    public static void drawFrame(Canvas canvas, int color, int backColor) {
        final int bold = 8;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Paint paint = new Paint();

        Rect rect = new Rect();

        // draw back
        paint.setColor(backColor);
        drawLine(canvas, rect, paint, bold, bold, width - bold, height - bold);

        paint.setColor(color);

        // draw top
        drawLine(canvas, rect, paint, bold * 2, 0, width - bold * 2, bold);

        // draw top-left
        drawLine(canvas, rect, paint, bold, bold, bold * 2, bold * 2);

        // draw top-right
        drawLine(canvas, rect, paint, width - bold * 2, bold, width - bold, bold * 2);

        // draw left
        drawLine(canvas, rect, paint, 0, bold * 2, bold, height - bold * 2);

        // draw right
        drawLine(canvas, rect, paint, width - bold, bold * 2, width, height - bold * 2);

        // draw bottom-left
        drawLine(canvas, rect, paint, bold, height - bold * 2, bold * 2, height - bold);

        // draw bottom-right
        drawLine(canvas, rect, paint, width - bold * 2, height - bold * 2, width - bold, height - bold);

        // draw bottom
        drawLine(canvas, rect, paint, bold * 2, height - bold, width - bold * 2, height);
    }

    private static void drawLine(Canvas canvas, Rect rect, final Paint paint, int left, int top, int right, int bottom) {
        rect.set(left, top, right, bottom);
        canvas.drawRect(rect, paint);
    }
}
