package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

public class DotScroll extends View {

    public DotScroll(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        final Rect bounds = canvas.getClipBounds();
        final int width = bounds.width();
        final int height = bounds.height();

        // left
        {
            Rect src = new Rect(0, 16, 8, 32);
            Rect dst = new Rect(0, 0, height / 2, height);
            canvas.drawBitmap(DotIcon.getBitmap(), src, dst, paint);
        }
        // middle
        {
            Rect src = new Rect(8, 16, 16, 32);
            Rect dst = new Rect(height / 2, 0, width - height, height);
            canvas.drawBitmap(DotIcon.getBitmap(), src, dst, paint);
        }
        // right
        {
            Rect src = new Rect(16, 16, 32, 32);
            Rect dst = new Rect(width - height, 0, width, height);
            canvas.drawBitmap(DotIcon.getBitmap(), src, dst, paint);
        }
    }
}
