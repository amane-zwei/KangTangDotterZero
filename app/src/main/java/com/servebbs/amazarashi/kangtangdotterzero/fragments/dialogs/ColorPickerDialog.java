package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.util.DisplayMetricsUtil;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ARGBColorPicker;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ColorSelector;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotScroll;

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

            final int iconSize = ScreenSize.getIconSize();
            final int padding = ScreenSize.getPadding();

            setOrientation(LinearLayout.VERTICAL);
            setBackgroundColor(0xffffffff);

            {
                DotScroll dotScroll = new DotScroll(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        iconSize
                );
                dotScroll.setLayoutParams(layoutParams);
                addView(dotScroll);
            }
            {
                ColorSelector colorSelector = new ColorSelector(context);
                colorSelector.setPadding(padding, 0, padding, 0);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0
                );
                layoutParams.weight = 1;
                colorSelector.setLayoutParams(layoutParams);
                addView(colorSelector);
            }
            {
                DotScroll dotScroll = new DotScroll(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        iconSize
                );
                dotScroll.setLayoutParams(layoutParams);
                addView(dotScroll);
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
