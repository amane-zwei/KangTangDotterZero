package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

import lombok.Getter;
import lombok.Setter;

public class CursorView extends SimpleIconView{
    @Getter
    private float x;
    @Getter
    private float y;
    private float prevX;
    private float prevY;

    @Getter
    @Setter
    private boolean isDown;

    @Setter
    private float screenWidth;
    @Setter
    private float screenHeight;

    public CursorView(Context context) {
        super(context);

        setClickable(false);
        setRect(DotIcon.cursor.createRect());

        reset(0f, 0f);
    }

    public void reset(float x, float y) {
        this.x = x;
        this.y = y;
        prevX = x;
        prevY = y;
    }

    public void begin(float x, float y) {
        prevX = x;
        prevY = y;
    }

    public void move(float x, float y) {
        this.x += x - prevX;
        this.y += y - prevY;

        if (this.x < 0) {
            this.x = 0;
        } else if (this.x > screenWidth) {
            this.x = screenWidth;
        }
        if (this.y < 0) {
            this.y = 0;
        } else if (this.y > screenHeight) {
            this.y = screenHeight;
        }

        prevX = x;
        prevY = y;
    }
}
