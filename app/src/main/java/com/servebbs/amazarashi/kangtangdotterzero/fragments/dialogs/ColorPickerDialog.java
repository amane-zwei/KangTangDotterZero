package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ARGBColorPicker;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ColorSelector;

public class ColorPickerDialog extends KTDZDialogFragment {
    @Override
    public View createContentView(Context context) {
        return new ColorPickerDialogView(context);
    }

    public static class ColorPickerDialogView extends LinearLayout {
        private static GradientDrawable divider;

        static {
            divider = new GradientDrawable();
            divider.setColor(0xff000000);
            divider.setSize(0, 8);
        }

        public ColorPickerDialogView(Context context) {
            super(context);

            setOrientation(LinearLayout.VERTICAL);
            setDividerDrawable(divider);
            setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

            {
                ColorSelector colorSelector = new ColorSelector(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0
                );
                layoutParams.weight = 1;
                colorSelector.setLayoutParams(layoutParams);
                addView(colorSelector);
            }
            {
                ARGBColorPicker argbColorPicker = new ARGBColorPicker(context);
                argbColorPicker.setColor(0xfff0a080);
                argbColorPicker.setLayoutParams(
                        new LinearLayoutCompat.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                );
                addView(argbColorPicker);
            }
        }
    }
}
