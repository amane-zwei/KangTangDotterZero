package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.drawables.DividerDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ARGBColorPicker;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ColorSelector;

public class ColorPickerDialog extends KTDZDialogFragment {

    private Palette palette = null;
    private ColorPickerDialogView contentView = null;

    public ColorPickerDialog attachPalette(Palette palette) {
        this.palette = palette;
        if(contentView != null) {
            contentView.attachPalette(palette);
        }
        return this;
    }

    @Override
    public View createContentView(Context context) {
        this.contentView = new ColorPickerDialogView(context);
        this.contentView.attachPalette(palette);
        return contentView;
    }

    public static class ColorPickerDialogView extends LinearLayout {

        private ColorSelector colorSelector;
        private ARGBColorPicker argbColorPicker;

        public ColorPickerDialogView(Context context) {
            super(context);

            final int iconSize = ScreenSize.getIconSize();
            final int dotSize = ScreenSize.getDotSize();
            final int padding = ScreenSize.getPadding();

            setOrientation(LinearLayout.VERTICAL);
            setBackgroundColor(0xfff0f0f0);

            {
                ColorSelector colorSelector = this.colorSelector = new ColorSelector(context);
                colorSelector.setOnColorSelectListener(new ColorSelector.OnColorSelectListener() {
                    @Override
                    public void onColorSelect(int color) {
                        argbColorPicker.applyColor(color);
                    }
                });
                colorSelector.setPadding(padding, padding, padding, padding);
                colorSelector.setStretchMode(GridView.NO_STRETCH);

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
                ARGBColorPicker argbColorPicker = this.argbColorPicker = new ARGBColorPicker(context);
                argbColorPicker.setOnColorChangeListener(new ARGBColorPicker.OnColorChangeListener() {
                    @Override
                    public void onColorChange(int color) {
                        colorSelector.applyColor(color);
                    }
                });
                argbColorPicker.setPadding(padding, padding, padding, padding);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                argbColorPicker.setLayoutParams(layoutParams);
                addView(argbColorPicker);
            }
        }

        public void attachPalette(Palette palette) {
            colorSelector.attachPalette(palette);
            argbColorPicker.applyColor(palette.getColor());
        }
    }
}
