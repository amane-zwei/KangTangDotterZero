package com.servebbs.amazarashi.kangtangdotterzero.views.menu;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.UnRedoAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.tools.Bucket;
import com.servebbs.amazarashi.kangtangdotterzero.domains.tools.Eraser;
import com.servebbs.amazarashi.kangtangdotterzero.domains.tools.Pen;
import com.servebbs.amazarashi.kangtangdotterzero.views.actionviews.ActionView;
import com.servebbs.amazarashi.kangtangdotterzero.views.actionviews.CallColorPickerDialogView;
import com.servebbs.amazarashi.kangtangdotterzero.views.actionviews.CallDrawerView;
import com.servebbs.amazarashi.kangtangdotterzero.views.actionviews.CallLoadProjectDialogView;
import com.servebbs.amazarashi.kangtangdotterzero.views.actionviews.CallSaveProjectDialogView;
import com.servebbs.amazarashi.kangtangdotterzero.views.actionviews.SelectToolView;
import com.servebbs.amazarashi.kangtangdotterzero.views.actionviews.UnRedoView;

public class HorizontalMenuView extends HorizontalScrollView {
    // implements GestureDetector.OnGestureListener
    private final LinearLayout layout;

    private int tmpPos = -1;
    private int width;

    private final GestureDetector detector;

    public HorizontalMenuView(Context context) {
        super(context);

        setHorizontalScrollBarEnabled(false);
        width = ScreenSize.getIconSize();

        LinearLayout layout = this.layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        this.addView(layout);

        addItem(new SelectToolView(context).applyTool(new Pen()));
        addItem(new SelectToolView(context).applyTool(new Eraser()));
        addItem(new SelectToolView(context).applyTool(new Bucket()));
        addItem(new CallColorPickerDialogView(context));
        addItem(new UnRedoView(context).applyDelta(UnRedoAction.DELTA_UNDO));
        addItem(new UnRedoView(context).applyDelta(UnRedoAction.DELTA_REDO));
        addItem(new CallSaveProjectDialogView(context));
        addItem(new CallLoadProjectDialogView(context));
        addItem(new CallDrawerView(context));

        this.detector = new GestureDetector(context, new GestureListener());
    }

    public void addItem(ActionView child) {
        final int size = width;
        child.setLayoutParams(
                new LinearLayout.LayoutParams(
                        size,
                        size));
        this.layout.addView(child);
    }

    //
//    public LinearLayout getLayout(){ return layout; }
//
//    public void    onDown(int position, int x, int y){}
//    public boolean onMove(int position, int x, int y){return false;}
//    public void    onUp  (int position){}
//    public void    onClick(int position){}
//    public boolean onLongPress(int position, int x, int y){return false;}
//
//    private int getPosition(int x){
//        int position = (this.getScrollX() + x) / height;
//        if( position >= length )
//            return -1;
//
//        return position;
//    }
//
    private ActionView findView(int x) {
        return (ActionView) layout.getChildAt((getScrollX() + x) / width);
    }

    private boolean canScroll() {
        View child = getChildAt(0);
        if (child != null) {
            int childWidth = child.getWidth();
            return getWidth() < childWidth + getPaddingLeft() + getPaddingRight();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        int action = e.getAction();

//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                tmpPos = getPosition(x);
//                if (tmpPos >= 0)
//                    onDown(tmpPos, x, y);
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                if (onMove(tmpPos, x, y))
//                    return true;
//                break;
//
//            case MotionEvent.ACTION_UP:
//                onUp(tmpPos);
//                tmpPos = -1;
//                break;
//        }

        if (detector.onTouchEvent(e) || !canScroll())
            return true;

        return super.onTouchEvent(e);
    }

    private class GestureListener implements GestureDetector.OnGestureListener {
        // タッチアップ時に呼ばれる
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            ActionView view = findView((int) e.getX());
            if (view != null) {
                view.getOnClickAction().action(getContext());
                return true;
            }
            return false;
        }

        //長押し時に呼ばれる
        @Override
        public void onLongPress(MotionEvent e) {
//            onLongPress(getPosition((int) e.getX()), (int) e.getX(), (int) e.getY());
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        //フリック時に呼ばれる(速度単位はPixel/秒)
        @Override
        public boolean onFling(MotionEvent e0, MotionEvent e1, float velocityX, float velocityY) {
            return false;
        }

        //スクロール時に呼ばれる
        @Override
        public boolean onScroll(MotionEvent e0, MotionEvent e1, float distanceX, float distanceY) {
            return false;
        }

        //プレス時(down後moveなし)に呼ばれる
        @Override
        public void onShowPress(MotionEvent e) {
        }

        /*
            //ダブルタップ時に呼ばれる
            @Override
            public boolean onDoubleTap(MotionEvent e) {return false;}

            //ダブルタップイベント時(down,move,up含む)に呼ばれる
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {return false;}

            //シングルタップ時に呼ばれる
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {return false;}
        */
    }
}