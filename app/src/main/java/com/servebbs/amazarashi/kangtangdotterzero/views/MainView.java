package com.servebbs.amazarashi.kangtangdotterzero.views;

import android.content.Context;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.views.menu.HorizontalMenuView;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.CursorView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class MainView extends FrameLayout {

    private final List<ProjectView> projectViews;

    private Cursor cursor;
    private CursorView cursorView;
    private FrameLayout.LayoutParams cursorViewParams;

    private boolean isCursorMode;
    private final GestureDetector gestureDetector;

    public MainView(Context context) {
        super(context);

        projectViews = new ArrayList<>();
        addProjectView(context).attachProject(Project.get(context));
        addMenuView(context);

        isCursorMode = true;
        gestureDetector = new GestureDetector(context, new GestureListener());

        cursor = null;
        cursorView = null;
        cursorViewParams = null;
        if (isCursorMode) {
            summonCursor();
        }
    }

    private ProjectView addProjectView(Context context) {
        ProjectView projectView = new ProjectView(context);
        projectView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        );
        projectViews.add(projectView);
        this.addView(projectView);
        return projectView;
    }

    private HorizontalMenuView addMenuView(Context context) {
        HorizontalMenuView menuView = new HorizontalMenuView(context);
        menuView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM)
        );
        this.addView(menuView);
        return menuView;
    }

    public void summonCursor() {
        if (cursor == null) {
            cursor = new Cursor();
        }
        if (cursorView == null) {
            final int size = ScreenSize.getIconSize();
            cursorView = new CursorView(getContext());
            cursorView.setLayoutParams(cursorViewParams = new FrameLayout.LayoutParams(
                    size,
                    size)
            );
        }
        isCursorMode = true;
        addView(cursorView);
        flipProjectView(false);
    }

    public void invalidateProjectViews() {
        for (View view : projectViews) {
            view.invalidate();
        }
    }

    private void flipProjectView(boolean consumeEvent) {
        for (ProjectView view : projectViews) {
            view.setConsumeEvent(consumeEvent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isCursorMode) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cursor.set(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                cursor.move(x, y);
                cursorViewParams.leftMargin = (int) cursor.getX();
                cursorViewParams.topMargin = (int) cursor.getY();
                requestLayout();
                break;
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            for (ProjectView projectView : projectViews) {
                if (projectView.click(cursor.getX(), cursor.getY())) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class Cursor {
        @Getter
        private float x;
        @Getter
        private float y;
        private float prevX;
        private float prevY;
        private float startX;
        private float startY;

        public Cursor() {
            reset(0f, 0f);
        }

        public void reset(float x, float y) {
            this.x = x;
            this.y = y;
            set(x, y);
        }

        public void set(float x, float y) {
            prevX = startX = x;
            prevY = startY = y;
        }

        public void move(float x, float y) {
            this.x += x - prevX;
            this.y += y - prevY;

            prevX = x;
            prevY = y;
        }

        public boolean isMove(float x, float y) {
            float dx = x - startX;
            float dy = y - startY;
            return dx * dx + dy * dy > 4f;
        }
    }
}
