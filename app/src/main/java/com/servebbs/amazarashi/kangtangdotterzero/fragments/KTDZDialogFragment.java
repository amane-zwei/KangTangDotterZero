package com.servebbs.amazarashi.kangtangdotterzero.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.DialogFragment;

public class KTDZDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(createMainView(getContext()));
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    public View createContentView(Context context) {
        return new View(context) {
            @Override
            public void onDraw(Canvas canvas) {
                canvas.drawColor(0xffffa0af);
            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                setMeasuredDimension(
                        MeasureSpec.getSize(widthMeasureSpec),256
                );
            }
        };
    }

    public View createMainView(Context context) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        LinearLayout mainLayout = new LinearLayout(context);
        {
            View contentView = createContentView(context);
            mainLayout.addView(contentView);
        }

        {
            Button button = new Button(context);
            button.setText("sample");
            mainLayout.addView(button);
        }

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        mainLayout.setLayoutParams(mainParams);
        mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(mainLayout);
        return relativeLayout;
    }
}
