package com.servebbs.amazarashi.kangtangdotterzero.views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.views.menu.HorizontalMenuView;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.CursorButtonView;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.CursorView;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.FloatingButtonView;

import java.util.ArrayList;
import java.util.List;

public class MainView extends FrameLayout {

    private final List<ProjectView> projectViews;
    private final ProjectView currentProjectView;

    private Cursor cursorView;
    private FloatingButtonView cursorButtonView;

    private boolean isCursorMode;

    public MainView(Context context) {
        super(context);

        projectViews = new ArrayList<>();
        currentProjectView = addProjectView(context).attachProject(Project.get(context));
        addMenuView(context);

        isCursorMode = false;

        {
            final int size = ScreenSize.getIconSize();
            cursorView = new Cursor(getContext());
            cursorView.setLayoutParams(new FrameLayout.LayoutParams(
                    size,
                    size)
            );
        }
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
        if (cursorButtonView == null) {
            final int size = ScreenSize.getIconSize();
            cursorButtonView = new FloatingButtonView(getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.bottomMargin = size;
            cursorButtonView.setLayoutParams(params);
            {
                CursorButtonView contentView = new CursorButtonView(getContext());
                contentView.setLayoutParams(new LinearLayout.LayoutParams(
                        size,
                        size
                ));
                cursorButtonView.addView(contentView);
            }
        }
        isCursorMode = true;
        addView(cursorButtonView);
        addView(cursorView);
        currentProjectView.attachCursor(cursorView);
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

    public boolean keyDown() {
        currentProjectView.down(cursorView.getX(), cursorView.getY());
        return true;
    }

    public boolean keyUp() {
        currentProjectView.up(cursorView.getX(), cursorView.getY());
        return true;
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
