package com.servebbs.amazarashi.kangtangdotterzero.views.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.domains.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.domains.tools.Tool;
import com.servebbs.amazarashi.kangtangdotterzero.views.MainView;

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

    private final Grid grid;

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

        grid = new Grid();

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

        if (normalizer.getRate() >= 3f) {
            grid.draw(canvas, normalizer, project);
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            normalizer.slideEnd();
        }
        if (event.getPointerCount() > 1) {
            if (tmpTool != null) {
                tmpTool.clear();
                tmpTool = null;
                project.unRedo(0);
            }

            // pinch
            scaleGestureDetector.onTouchEvent(event);

            // slide
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    normalizer.slideBegin(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (normalizer.slide(event.getX(0), event.getY(0), event.getX(1), event.getY(1))) {
                        invalidate();
                    }
                    break;
            }
            return true;
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

}
