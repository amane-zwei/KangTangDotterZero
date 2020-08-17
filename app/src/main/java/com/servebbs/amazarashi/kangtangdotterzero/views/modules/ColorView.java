package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.ScrollView;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class ColorView extends View {
    private final static Paint paint = new Paint();

    private int color = 0xffffa0a0;

    public ColorView(Context context) {
        super(context);
    }

    public void setColor(int color) {
        this.color = color;
        setBackgroundColor(color);
    }

    @Override
    public void onDraw(Canvas canvas) {
        final Rect srcRect = new Rect(0, 0, 16, 16);
        final Rect dstRect = canvas.getClipBounds();

        canvas.drawColor(color);
        canvas.drawBitmap(DotIcon.getBitmap(), srcRect, dstRect, paint);
    }
}
