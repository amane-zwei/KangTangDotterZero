package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;

public class FloatingButtonView extends LinearLayout {
    private FrameLayout.LayoutParams layoutParams;
    private final HeaderBar headerBar;

    private boolean isFloating;

    public FloatingButtonView(Context context) {
        super(context);

        int size = ScreenSize.getIconSize();
        int padding = ScreenSize.getDotSize();

        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(0xffffffff);
        setPadding(padding, padding, padding, padding);

        isFloating = false;

        {
            HeaderBar headerBar = this.headerBar = new HeaderBar(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    size,
                    size/4
            );
            headerBar.setLayoutParams(params);
            addView(headerBar);
        }
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        FrameLayout.LayoutParams tmpParams
                = this.layoutParams = (FrameLayout.LayoutParams) layoutParams;
        super.setLayoutParams(layoutParams);
    }

    private void move(float dx, float dy) {
        layoutParams.leftMargin += (int) dx;

        if (layoutParams.gravity == Gravity.TOP) {
            layoutParams.topMargin += (int) dy;
        } else {
            layoutParams.bottomMargin -= (int) dy;
        }
        requestLayout();
    }

    private class HeaderBar extends View {

        private final GestureDetector gestureDetector;

        private float prevX;
        private float prevY;

        public HeaderBar(Context context) {
            super(context);

            setBackgroundColor(0xffa0a0ff);

            gestureDetector = new GestureDetector(context, new OnLongPressListener());
        }

        @Override
        public void onDraw(Canvas canvas) {
            setBackgroundColor(isFloating ? 0xffffa0a0 : 0xffa0a0ff);
            super.onDraw(canvas);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    prevX = x;
                    prevY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isFloating) {
                        move((int) (x - prevX), (int)(y - prevY));
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            gestureDetector.onTouchEvent(event);
            return true;
        }
    }

    private class OnLongPressListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            isFloating ^= true;
            headerBar.invalidate();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
