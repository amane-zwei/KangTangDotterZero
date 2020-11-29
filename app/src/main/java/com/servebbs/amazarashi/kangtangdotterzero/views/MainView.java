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
import lombok.Setter;

public class MainView extends FrameLayout {

    private final List<ProjectView> projectViews;
    private final ProjectView mainProjectView;

    private Cursor cursorView;

    private boolean isCursorMode;

    public MainView(Context context) {
        super(context);

        projectViews = new ArrayList<>();
        mainProjectView = addProjectView(context).attachProject(Project.get(context));
        addMenuView(context);

        isCursorMode = true;

        cursorView = null;
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
        if (cursorView == null) {
            final int size = ScreenSize.getIconSize();
            cursorView = new Cursor(getContext());
            cursorView.setLayoutParams(new FrameLayout.LayoutParams(
                    size,
                    size)
            );
        }
        isCursorMode = true;
        addView(cursorView);
        mainProjectView.attachCursor(cursorView);
    }

    public void invalidateProjectViews() {
        for (View view : projectViews) {
            view.invalidate();
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cursorView.setScreenWidth(getMeasuredWidth());
        cursorView.setScreenHeight(getMeasuredHeight());
    }

    public static class Cursor extends CursorView {
        private FrameLayout.LayoutParams params;

        public Cursor(Context context) {
            super(context);
        }

        @Override
        public void setLayoutParams(ViewGroup.LayoutParams params) {
            super.setLayoutParams(params);
            this.params = (FrameLayout.LayoutParams) params;
        }

        public void move(float x, float y) {
            super.move(x, y);
            params.leftMargin = (int) getX();
            params.topMargin = (int) getY();
            requestLayout();
        }
    }
}
