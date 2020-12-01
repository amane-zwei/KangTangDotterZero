package com.servebbs.amazarashi.kangtangdotterzero.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.models.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Tool;

import lombok.Setter;

public class ProjectView extends View {

    private static final Paint paint = new Paint();
    private static final Paint backFramePaint;
    private static final Paint backFrameWhitePaint;
    private static final Paint backShadowPaint;

    static {
        backFramePaint = new Paint();
        backFramePaint.setColor(0xff000000);

        backFrameWhitePaint = new Paint();
        backFrameWhitePaint.setColor(0xffffffff);

        backShadowPaint = new Paint();
        backShadowPaint.setColor(0x2f000000);
    }

    private Project project;
    private MainView.Cursor cursor;
    private Tool tmpTool;

    private final Paint[] gridPaint;

    private final Rect dstRect;

    private final Normalizer normalizer;

    private final ScaleGestureDetector scaleGestureDetector;
    private final GestureDetector gestureDetector;

    public ProjectView(Context context) {
        super(context);

        dstRect = new Rect();
        setBackgroundColor(0xffa0a0ff);

        normalizer = new Normalizer();

        project = null;
        cursor = null;
        tmpTool = null;

        gridPaint = new Paint[2];
        gridPaint[0] = new Paint();
        gridPaint[0].setColor(0x80000000);
        gridPaint[1] = new Paint();
        gridPaint[1].setColor(0x40ffffff);

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetectorListener());
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public ProjectView attachProject(Project project) {
        this.project = project;
        normalizer.setProject(project);
        return this;
    }

    public void attachCursor(MainView.Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        normalizer.setScreen(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public void onDraw(Canvas canvas) {
        int bold = ScreenSize.getDotSize();

        canvas.drawRect(normalizer.setBackShadowRect(dstRect, bold), backShadowPaint);

        canvas.drawRect(normalizer.setBackFrameRect(dstRect, 2), backFramePaint);
        canvas.drawRect(normalizer.setShrinkRect(dstRect, 1), backFrameWhitePaint);

        canvas.drawBitmap(
                project.renderBitmap(),
                project.createRect(),
                normalizer.setScreenRect(dstRect),
                paint);

        if (normalizer.rate >= 3 << 16) {
            drawGrid(canvas, project);
        }
    }

    private void drawGrid(Canvas canvas, Project project) {
        float left = normalizer.getScreenX(0);
        float top = normalizer.getScreenY(0);
        float right = normalizer.getScreenX(project.getWidth());
        float bottom = normalizer.getScreenY(project.getHeight());

        for (int index = 0; index < gridPaint.length; index++) {
            Paint paint = gridPaint[index];

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            if (tmpTool != null) {
                tmpTool.clear();
                tmpTool = null;
                project.unRedo(0);
            }
            return scaleGestureDetector.onTouchEvent(event);
        }

        if (cursor == null) {
            return action(event.getX(), event.getY(), event.getAction());
        } else {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cursor.begin(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    cursor.move(x, y);
                    if (cursor.isDown()) {
                        action(cursor.getX(), cursor.getY(), MotionEvent.ACTION_MOVE);
                    }
                    break;
            }
            gestureDetector.onTouchEvent(event);
            return true;
        }
    }

    private boolean action(float x, float y, int action) {
        Context context = getContext();
        Tool tool = null;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                tool = tmpTool = GlobalContext.get(context).getTool();
                break;
            case MotionEvent.ACTION_MOVE:
                tool = tmpTool;
                break;
            case MotionEvent.ACTION_UP:
                tool = tmpTool;
                tmpTool = null;
                break;
        }
        if (tool != null && tool.touch(
                new Tool.Event(
                        project,
                        action,
                        normalizer.getProjectX(x),
                        normalizer.getProjectY(y)
                ))) {
            invalidate();
            return true;
        }
        return false;
    }

    public boolean down(float x, float y) {
        cursor.setDown(true);
        return action(x, y, MotionEvent.ACTION_DOWN);
    }

    public boolean up(float x, float y) {
        cursor.setDown(false);
        return action(x, y, MotionEvent.ACTION_UP);
    }

    public boolean click(float screenX, float screenY) {
        Context context = getContext();
        Tool tool = GlobalContext.get(context).getTool();
        int projectX = normalizer.getProjectX(screenX);
        int projectY = normalizer.getProjectY(screenY);
        if (
                tool.touch(
                        new Tool.Event(
                                project,
                                MotionEvent.ACTION_DOWN,
                                projectX,
                                projectY
                        ))
                        ||
                        tool.touch(
                                new Tool.Event(
                                        project,
                                        MotionEvent.ACTION_UP,
                                        projectX,
                                        projectY
                                ))
        ) {
            invalidate();
            return true;
        }
        return false;
    }

    private class ScaleGestureDetectorListener implements ScaleGestureDetector.OnScaleGestureListener {
        private float tmpSpan = 0f;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            normalizer.changeRate(
                    (int) (detector.getCurrentSpan() - tmpSpan),
                    (int) detector.getFocusX(),
                    (int) detector.getFocusY());
            invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            tmpSpan = detector.getCurrentSpan();
            normalizer.changeRateBegin();
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return click(cursor.getX(), cursor.getY());
        }
    }

    private static class Normalizer {

        @Setter
        Project project;

        private int originX;
        private int originY;

        private int targetX;
        private int targetY;

        private int rate;
        private int normalRate;
        private int baseSize;

        public Normalizer() {
            project = null;

            originX = 0;
            originY = 0;

            targetX = 0;
            targetY = 0;

            rate = 65536;
            baseSize = 32;
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
                baseSize = project.getWidth();
            } else {
                rate = rateV;
                baseSize = project.getHeight();
            }
        }

        public void changeRateBegin() {
            normalRate = rate;
        }

        public void changeRate(int span, int focusX, int focusY) {
            int rate = normalRate + (span << 16) / baseSize;
            if (rate < 1 << 16) {
                rate = 1 << 16;
            } else if (rate > baseSize << 16) {
                rate = baseSize << 16;
            }
            this.rate = rate;
        }
    }
}
