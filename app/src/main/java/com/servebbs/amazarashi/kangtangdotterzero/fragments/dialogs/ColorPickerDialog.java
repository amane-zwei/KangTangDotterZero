package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.drawables.DividerDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ARGBColorPicker;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ColorSelector;

public class ColorPickerDialog extends KTDZDialogFragment {
    @Override
    public View createContentView(Context context) {
        return new ColorPickerDialogView(context);
    }

    public static class ColorPickerDialogView extends LinearLayout {

        public ColorPickerDialogView(Context context) {
            super(context);

            final int iconSize = ScreenSize.getIconSize();
            final int padding = ScreenSize.getPadding();

            setOrientation(LinearLayout.VERTICAL);
            setBackgroundColor(0xfff0f0f0);

            {
                ColorSelector colorSelector = new ColorSelector(context);
                colorSelector.setPadding(padding, padding, padding, padding);
                colorSelector.setBackgroundColor(0xff800080);

                GradientDrawable stroke = new GradientDrawable();
                stroke.setStroke(iconSize / 16, 0xff000000);
                colorSelector.setBackground(stroke);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0
                );
                layoutParams.weight = 1;
                layoutParams.setMargins(padding, padding, padding, padding);
                colorSelector.setLayoutParams(layoutParams);
                addView(colorSelector);
            }
            {
                View divider = new View(context);
                DividerDrawable drawable = new DividerDrawable(0xffa0a0ff);
                divider.setBackground(drawable);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        iconSize / 4
                );
                layoutParams.setMargins(padding * 2, 0, padding * 2, 0);
                divider.setLayoutParams(layoutParams);
                addView(divider);
            }
            {
                ARGBColorPicker argbColorPicker = new ARGBColorPicker(context);
                argbColorPicker.setColor(0xfff0a080);
                argbColorPicker.setPadding(padding, padding, padding, padding);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                argbColorPicker.setLayoutParams(layoutParams);
                addView(argbColorPicker);
            }
        }
    }
}
