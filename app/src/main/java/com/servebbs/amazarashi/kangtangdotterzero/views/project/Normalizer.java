package com.servebbs.amazarashi.kangtangdotterzero.views.project;

import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;

import lombok.Setter;

class Normalizer {

    @Setter
    Project project;

    private boolean isSliding;
    private final float[] prevX;
    private final float[] prevY;

    private int originX;
    private int originY;

    private int targetX;
    private int targetY;

    private int rate;
    private int normalRate;
    private int projectSize;
    private int screenSize;

    public Normalizer() {
        project = null;

        isSliding = false;
        prevX = new float[2];
        prevY = new float[2];

        originX = 0;
        originY = 0;

        targetX = 0;
        targetY = 0;

        rate = 65536;
        projectSize = 32;
        screenSize = 32;
    }

    public int getProjectX(float screenX) {
        return (int) ((screenX - originX) * 65536 + targetX * rate) / rate;
    }

    public int getProjectY(float screenY) {
        return (int) ((screenY - originY) * 65536 + targetY * rate) / rate;
    }

    public float getScreenX(int paperX) {
        return (paperX * rate - targetX * rate) / 65536f + originX;
    }

    public float getScreenY(int paperY) {
        return (paperY * rate - targetY * rate) / 65536f + originY;
    }

    public float getRate() {
        return ((float) rate) / 65536;
    }

    public Rect setScreenRect(Rect rect) {
        rect.set(
                (int) getScreenX(0),
                (int) getScreenY(0),
                (int) getScreenX(project.getWidth()),
                (int) getScreenY(project.getHeight())
        );
        return rect;
    }

    public Rect setBackShadowRect(Rect rect, int bold) {
        rect.set(
                (int) getScreenX(0) + bold * 3,
                (int) getScreenY(0) + bold * 3,
                (int) getScreenX(project.getWidth()) + bold * 4,
                (int) getScreenY(project.getHeight()) + bold * 4
        );
        return rect;
    }

    public Rect setBackFrameRect(Rect rect, int bold) {
        rect.set(
                (int) getScreenX(0) - bold,
                (int) getScreenY(0) - bold,
                (int) getScreenX(project.getWidth()) + bold,
                (int) getScreenY(project.getHeight()) + bold
        );
        return rect;
    }

    public Rect setShrinkRect(Rect rect, int bold) {
        rect.set(
                rect.left + bold,
                rect.top + bold,
                rect.right - bold,
                rect.bottom - bold
        );
        return rect;
    }

    public void setScreen(int width, int height) {
        originX = width / 2;
        originY = height / 2;

        if (project == null) {
            return;
        }

        targetX = project.getWidth() / 2;
        targetY = project.getHeight() / 2;

        int rateH = (width << 16) / project.getWidth();
        int rateV = (height << 16) / project.getHeight();
        if (rateH < rateV) {
            rate = rateH;
            projectSize = project.getWidth();
            screenSize = width;
        } else {
            rate = rateV;
            projectSize = project.getHeight();
            screenSize = height;
        }
    }

    public void slideBegin(float x0, float y0, float x1, float y1) {
        prevX[0] = x0;
        prevY[0] = y0;
        prevX[1] = x1;
        prevY[1] = y1;
    }

    public boolean slide(float x0, float y0, float x1, float y1) {
        boolean result = false;
        float dx0 = x0 - prevX[0];
        float dy0 = y0 - prevY[0];
        float dx1 = x1 - prevX[1];
        float dy1 = y1 - prevY[1];
        if (isSliding && dx0 * dx1 >= 0f && dy0 * dy1 >= 0f) {
            originX += (int) ((dx0 + dx1) / 2);
            originY += (int) ((dy0 + dy1) / 2);
            result = true;
        }
        slideBegin(x0, y0, x1, y1);
        isSliding = true;
        return result;
    }

    public void slideEnd() {
        isSliding = false;
    }


    public void changeRateBegin() {
        normalRate = rate;
    }

    public void changeRate(int span, int focusX, int focusY) {
        int rate = normalRate + (span << 16) / projectSize;
        if (rate < 1 << 16) {
            rate = 1 << 16;
        } else if (rate > screenSize << 16) {
            rate = screenSize << 16;
        }
        this.rate = rate;
    }
}
