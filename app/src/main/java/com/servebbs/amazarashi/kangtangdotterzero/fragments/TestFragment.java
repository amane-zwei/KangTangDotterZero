package com.servebbs.amazarashi.kangtangdotterzero.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.R;

public class TestFragment extends KTDZDialogFragment {

//    private static TestFragment instance = new TestFragment();
//    public static TestFragment get() {
//        return instance;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        android.util.Log.d("hoge", "onAttach");
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        android.util.Log.d("hoge", "onCreate");
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        android.util.Log.d("hoge", "onStart");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        android.util.Log.d("hoge", "onPause");
//    }
//
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////
////        Log.d("hoge", "onCreateView");
////
////        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
////        frameLayout.setBackgroundColor(0x80000000);
////        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
////
////        TestIcon icon = new TestIcon(inflater.getContext());
////        icon.setLayoutParams(new FrameLayout.LayoutParams(128, 128, Gravity.CENTER));
////        frameLayout.addView(icon);
////
////        return frameLayout;
////    }
//
//    @Override
//    public View createMainView(Context context) {
//        return new TestIcon(context);
//    }
//
//    public static class TestIcon extends View {
//        public TestIcon(Context context) {
//            super(context);
//        }
//
//        @Override
//        public void onDraw(Canvas canvas) {
//            canvas.drawColor(0xffffa00a);
//        }
//
////        @Override
////        public void onMeasure(int widthMesureSpec, int heightMeasureSpec) {
////            setMeasuredDimension(128, 128);
////            setPadding(120, 120, 0, 0);
////        }
//    }
//
//    public static class TestView extends View {
//
//        Bitmap bitmap;
//
//        public TestView(Context context) {
//            super(context);
//
//            bitmap = BitmapFactory.decodeResource(
//                    context.getResources(),
//                    R.drawable.icon
//            );
//        }
//
//        @Override
//        public void onDraw(Canvas canvas) {
//            canvas.drawColor(0x8fffff00);
//
//            canvas.drawBitmap(
//                    bitmap,
//                    new Rect(0, 32, 24, 32+24),
//                    new Rect(32, 32, 32+72, 32+72),
//                    new Paint());
//            canvas.drawBitmap(
//                    bitmap,
//                    new Rect(24, 32, 24+24, 32+24),
//                    new Rect(128, 32, 128+72, 32+72),
//                    new Paint());
//
//        }
//    }
}
