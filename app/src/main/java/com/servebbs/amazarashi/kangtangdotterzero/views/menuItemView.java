package com.servebbs.amazarashi.kangtangdotterzero.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.TestFragment;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs.ColorPickerDialog;

public class menuItemView extends View  {

    private GestureDetector gestureDetector;

    public menuItemView(Context context) {
        super(context);

//        this.gestureDetector = new GestureDetector(context, this);
//        setOnTouchListener(this);

        setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                new ColorPickerDialog().show(((AppCompatActivity)view.getContext()).getSupportFragmentManager(), "test_tag");
            }
        });
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(0xffff0000);
    }

//    @Override
//    public boolean onDown(MotionEvent e) {
//        return true;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        android.util.Log.d("hoge", "test+++");
////        Navigation.openTestFragment(((AppCompatActivity)getContext()).getSupportFragmentManager());
//        return true;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        return true;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {
//    }
//
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        return false;
//    }
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }

    public static class TestDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle bundle) {
            Dialog dialog = new Dialog(getActivity());
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            // フルスクリーン
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            dialog.setContentView(new View(getContext()){
                public void onDraw(Canvas canvas) {
                    canvas.drawColor(0xffffffff);
                }
            });
            // 背景を透明にする
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            return dialog;
        }
    }
//    public static class TestDialog extends DialogFragment {
//        @Override
//        public Dialog onCreateDialog(Bundle bundle) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage("test_dialog");
//            builder.setPositiveButton("hogehoge", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
//            return builder.create();
//        }
//    }
}
