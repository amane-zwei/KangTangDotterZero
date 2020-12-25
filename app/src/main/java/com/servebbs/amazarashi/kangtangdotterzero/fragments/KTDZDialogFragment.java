package com.servebbs.amazarashi.kangtangdotterzero.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.servebbs.amazarashi.kangtangdotterzero.util.DisplayMetricsUtil;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotButton;

public class KTDZDialogFragment extends DialogFragment {

    private final OnButtonFunction[] onButtonFunctions = new OnButtonFunction[2];

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
        return new View(context);
    }

    protected void setOnPositiveButton(OnButtonFunction onPositiveButton) {
        onButtonFunctions[1] = onPositiveButton;
    }

    public View createButtonAreaView(Context context) {
        final int margin = DisplayMetricsUtil.calcPixel(context, 10);

        final String[] texts = {"Cancel", "OK"};

        LinearLayout buttonAreaLayout = new LinearLayout(context);
        buttonAreaLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonAreaLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        for (int index = 0; index < texts.length; index++) {
            DotButton button = new DotButton(context);
            final OnButtonFunction onButtonFunction = onButtonFunctions[index];
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
            layoutParams.setMargins(margin, 0, 0, 0);
            button.setLayoutParams(layoutParams);
            button.setText(texts[index]);
            button.setOnClickListener((View v) -> {
                if (onButtonFunction != null) {
                    onButtonFunction.accept();
                }
                dismiss();
            });
            buttonAreaLayout.addView(button);
        }
        return buttonAreaLayout;
    }

    public View createMainView(Context context) {
        final int topMargin = DisplayMetricsUtil.calcPixel(context, 30);
        final int margin = DisplayMetricsUtil.calcPixel(context, 10);

        ConstraintLayout constraintLayout = new ConstraintLayout(context);

        int contentViewId = View.generateViewId();
        int buttonAreaViewId = View.generateViewId();

        {
            View contentView = createContentView(context);
            contentView.setId(contentViewId);
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.bottomToTop = buttonAreaViewId;
            layoutParams.topMargin = topMargin;
            layoutParams.constrainedHeight = true;
            contentView.setLayoutParams(layoutParams);
            constraintLayout.addView(contentView);
        }

        {
            View buttonAreaView = createButtonAreaView(context);
            buttonAreaView.setId(buttonAreaViewId);
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToBottom = contentViewId;
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topMargin = margin;
            layoutParams.bottomMargin = topMargin;
            buttonAreaView.setLayoutParams(layoutParams);
            constraintLayout.addView(buttonAreaView);
        }

        return constraintLayout;
    }

    @FunctionalInterface
    public interface OnButtonFunction {
        void accept();
    }
}
