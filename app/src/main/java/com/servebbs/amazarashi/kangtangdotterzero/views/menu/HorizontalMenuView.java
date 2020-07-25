package com.servebbs.amazarashi.kangtangdotterzero.views.menu;

import android.content.Context;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.HorizontalScrollView;
import android.view.GestureDetector;

public class HorizontalMenuView extends HorizontalScrollView {
// implements GestureDetector.OnGestureListener
    private LinearLayout layout;

    private int tmpPos = -1;
    private int height = 0;
    private int length = 0;

    private GestureDetector detector;

    public HorizontalMenuView(Context context){
        super(context);

        setHorizontalScrollBarEnabled(false);

        LinearLayout layout = this.layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        this.addView(layout);

        this.length = 0;
        this.height = height;

//        this.detector = new GestureDetector(context,this);
    }

    public void addItem(View child){
        child.setLayoutParams(new LinearLayout.LayoutParams(128, 128));
        this.layout.addView(child);
        length++;
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
//    // タッチアップ時に呼ばれる
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        onClick( getPosition((int)e.getX()) );
//        return false;
//    }
//
//    //長押し時に呼ばれる
//    @Override
//    public void onLongPress(MotionEvent e) {
//        onLongPress( getPosition((int)e.getX()), (int)e.getX(), (int)e.getY() );
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent e){
//        int x = (int)e.getX();
//        int y = (int)e.getY();
//        int action = e.getAction();
//
//        switch(action)
//        {
//            case MotionEvent.ACTION_DOWN:
//                tmpPos = getPosition(x);
//                if( tmpPos >= 0 )
//                    onDown(tmpPos, x, y);
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                if( onMove(tmpPos, x, y) )
//                    return true;
//                break;
//
//            case MotionEvent.ACTION_UP:
//                onUp(tmpPos);
//                tmpPos = -1;
//                break;
//        }
//
//        if( detector.onTouchEvent(e) || !canScroll() )
//            return true;
//
//        return super.onTouchEvent(e);
//    }
//
//    @Override
//    public boolean onDown(MotionEvent e) {return false;}
//
//    //フリック時に呼ばれる(速度単位はPixel/秒)
//    @Override
//    public boolean onFling(MotionEvent e0,MotionEvent e1, float velocityX, float velocityY) { return false; }
//
//    //スクロール時に呼ばれる
//    @Override
//    public boolean onScroll(MotionEvent e0,MotionEvent e1, float distanceX,float distanceY) { return false; }
//
//    //プレス時(down後moveなし)に呼ばれる
//    @Override
//    public void onShowPress(MotionEvent e) {}
//
//    /*
//        //ダブルタップ時に呼ばれる
//        @Override
//        public boolean onDoubleTap(MotionEvent e) {return false;}
//
//        //ダブルタップイベント時(down,move,up含む)に呼ばれる
//        @Override
//        public boolean onDoubleTapEvent(MotionEvent e) {return false;}
//
//        //シングルタップ時に呼ばれる
//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent e) {return false;}
//    */
//    private boolean canScroll() {
//        View child = getChildAt(0);
//        if (child != null) {
//            int childWidth = child.getWidth();
//            return getWidth() < childWidth + getPaddingLeft() + getPaddingRight() ;
//        }
//        return false;
//    }
}