package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColorValue;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Palette;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotRoundRectDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ARGBColorPicker;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ColorSelector;

import lombok.Getter;

public class ColorPickerDialog extends KTDZDialogFragment {

    @Getter
    private Palette palette = null;
    private ColorPickerDialogView contentView = null;

    public ColorPickerDialog attachPalette(Palette palette) {
        this.palette = palette;
        if (contentView != null) {
            contentView.attachPalette(palette);
        }
        return this;
    }

    public ColorPickerDialog setOnPositive(OnPositiveButtonListener onPositive) {
        setOnPositiveButton(() -> onPositive.onPositiveButton(palette));
        return this;
    }

    @Override
    public String getTitle() {
        return "COLOR PICKER";
    }

    @Override
    public View createContentView(Context context) {
        this.contentView = new ColorPickerDialogView(context);
        this.contentView.attachPalette(palette);
        return contentView;
    }

    @FunctionalInterface
    public interface OnPositiveButtonListener {
        boolean onPositiveButton(Palette palette);
    }

    public static class ColorPickerDialogView extends LinearLayout {

        private final ColorSelector colorSelector;
        private ARGBColorPicker argbColorPicker;

        public ColorPickerDialogView(Context context) {
            super(context);

            final int iconSize = ScreenSize.getIconSize();
            final int dotSize = ScreenSize.getDotSize();
            final int padding = ScreenSize.getPadding();

            setOrientation(LinearLayout.VERTICAL);

            {
                ColorSelector colorSelector = this.colorSelector = new ColorSelector(context);
                colorSelector.setBackground(new DotRoundRectDrawable());
                colorSelector.setOnColorSelectListener((DotColorValue color) -> argbColorPicker.applyColor(color));
                colorSelector.setPadding(padding, padding, padding, padding);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );
                layoutParams.leftMargin = dotSize;
                colorSelector.setLayoutParams(layoutParams);
                addView(colorSelector);
            }
            {
                int height = iconSize * 4 + padding * 2;
                if (height > ScreenSize.getHeight() / 3) {
                    height = ScreenSize.getHeight() / 3;
                }
                ARGBColorPicker argbColorPicker = this.argbColorPicker = new ARGBColorPicker(context);
                argbColorPicker.setBackground(new DotRoundRectDrawable());
                argbColorPicker.setOnColorChangeListener(colorSelector::applyColor);
                argbColorPicker.setPadding(padding, padding, padding, padding);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        height
                );
                layoutParams.leftMargin = dotSize;
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
