package com.servebbs.amazarashi.kangtangdotterzero.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.models.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Tool;

import lombok.Setter;

public class ProjectView extends View {

    private static final Paint paint = new Paint();

    private Project project;

    @Setter
    private boolean consumeEvent;

    private final Rect dstRect;

    private final Normalizer normalizer;

    private final ScaleGestureDetector scaleGestureDetector;

    public ProjectView(Context context) {
        super(context);

        consumeEvent = true;
        dstRect = new Rect();
        setBackgroundColor(0xffa0a0ff);

        normalizer = new Normalizer();

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetectorListener());
    }

    public void attachProject(Project project) {
        this.project = project;
        normalizer.setProject(project);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(
                project.renderBitmap(),
                project.createRect(),
                normalizer.setScreenRect(dstRect),
                paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (scaleGestureDetector.onTouchEvent(event)) {
//            return true;
//        }
        return consumeEvent && touch(event.getAction(), event.getX(), event.getY());
    }

    public boolean touch(int action, float x, float y) {
        Context context = getContext();
        if (GlobalContext.get(context).getTool().touch(
                new Tool.Event(
                        project,
                        action,
                        normalizer.getPaperX(x),
                        normalizer.getPaperY(y)
                ))) {
            invalidate();
            return true;
        }
        return false;
    }

    public boolean click(float x, float y) {
        Context context = getContext();
        Tool tool = GlobalContext.get(context).getTool();
        if (
                tool.touch(
                        new Tool.Event(
                                project,
                                MotionEvent.ACTION_DOWN,
                                normalizer.getPaperX(x),
                                normalizer.getPaperY(y)
                        ))
                        ||
                        tool.touch(
                                new Tool.Event(
                                        project,
                                        MotionEvent.ACTION_UP,
                                        normalizer.getPaperX(x),
                                        normalizer.getPaperY(y)
                                ))
        ) {
            invalidate();
            return true;
        }
        return false;
    }

    private class ScaleGestureDetectorListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

    private static class Normalizer {

        @Setter
        Project project;

        int positionX;
        int positionY;

        int targetX;
        int targetY;

        int rate;

        public Normalizer() {
            project = null;

            positionX = 48;
            positionY = 96;

            targetX = 0;
            targetY = 0;

            rate = 4 * 65536;
        }

        public int getPaperX(float screenX) {
            return (int) ((screenX - positionX) * 65536 + targetX * rate) / rate;
        }

        public int getPaperY(float screenY) {
            return (int) ((screenY - positionY) * 65536 + targetY * rate) / rate;
        }

        public int getScreenX(int paperX) {
            return (paperX * rate - (int) (targetX * rate)) / 65536 + positionX;
        }

        public int getScreenY(int paperY) {
            return (paperY * rate - (int) (targetY * rate)) / 65536 + positionY;
        }

        public Rect setScreenRect(Rect rect) {
            rect.set(
                    getScreenX(0),
                    getScreenY(0),
                    getScreenX(project.getWidth()),
                    getScreenY(project.getHeight())
            );
            return rect;
        }
    }
}
