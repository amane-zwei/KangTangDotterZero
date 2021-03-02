package com.servebbs.amazarashi.kangtangdotterzero.views.project;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;

public class Grid {

    private final Paint[] paints;

    public Grid() {
        paints = new Paint[2];
        paints[0] = new Paint();
        paints[0].setColor(0x80000000);
        paints[1] = new Paint();
        paints[1].setColor(0x40ffffff);
    }

    public void draw(Canvas canvas, Normalizer normalizer, Project project) {
        float left = normalizer.getScreenX(0);
        float top = normalizer.getScreenY(0);
        float right = normalizer.getScreenX(project.getWidth());
        float bottom = normalizer.getScreenY(project.getHeight());

        for (int index = 0; index < paints.length; index++) {
            Paint paint = paints[index];

            for (int x = 0; x <= project.getWidth(); x++) {
                float screenX = normalizer.getScreenX(x);
                canvas.drawLine(screenX, top, screenX, bottom, paint);
            }

            for (int y = 0; y <= project.getHeight(); y++) {
                float screenY = normalizer.getScreenY(y);
                canvas.drawLine(left, screenY, right, screenY, paint);
            }
        }
    }
}
