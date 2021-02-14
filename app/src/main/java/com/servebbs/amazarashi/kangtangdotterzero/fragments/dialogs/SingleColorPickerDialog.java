package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColorValue;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotRoundRectDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ARGBColorPicker;

import lombok.Getter;

public class SingleColorPickerDialog extends KTDZDialogFragment {

    @Getter
    private DotColorValue color;
    private ColorPickerDialogView contentView = null;

    public SingleColorPickerDialog applyColor(DotColorValue color) {
        this.color = color;
        if (contentView != null) {
            contentView.applyColor(color);
        }
        return this;
    }

    public SingleColorPickerDialog setOnPositive(OnPositiveButtonListener onPositive) {
        setOnPositiveButton(() -> onPositive.onPositiveButton(contentView.getColor()));
        return this;
    }

    @Override
    public String getTitle() {
        return "COLOR PICKER";
    }

    @Override
    public View createContentView(Context context) {
        this.contentView = new ColorPickerDialogView(context);
        this.contentView.applyColor(color);
        return contentView;
    }

    @FunctionalInterface
    public interface OnPositiveButtonListener {
        boolean onPositiveButton(DotColorValue color);
    }

    public static class ColorPickerDialogView extends LinearLayout {

        private final ColorView colorView;
        private final ARGBColorPicker argbColorPicker;

        public ColorPickerDialogView(Context context) {
            super(context);

            final int iconSize = ScreenSize.getIconSize();
            final int dotSize = ScreenSize.getDotSize();
            final int padding = ScreenSize.getPadding();

            setOrientation(LinearLayout.VERTICAL);

            {
                ColorView colorView = this.colorView = new ColorView(context);
                colorView.setBackground(new DotRoundRectDrawable());
                colorView.setPadding(padding, padding, padding, padding);

                LayoutParams layoutParams = new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.gravity = Gravity.CENTER;
                colorView.setLayoutParams(layoutParams);
                addView(colorView);
            }
            {
                int height = iconSize * 4 + padding * 2;
                if (height > ScreenSize.getHeight() / 3) {
                    height = ScreenSize.getHeight() / 3;
                }
                ARGBColorPicker argbColorPicker = this.argbColorPicker = new ARGBColorPicker(context);
                argbColorPicker.setBackground(new DotRoundRectDrawable());
                argbColorPicker.setOnColorChangeListener(colorView::applyColor);
                argbColorPicker.setPadding(padding, padding, padding, padding);

                LayoutParams layoutParams = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        height
                );
                layoutParams.leftMargin = dotSize;
                argbColorPicker.setLayoutParams(layoutParams);
                addView(argbColorPicker);
            }
        }

        public DotColorValue getColor() {
            return colorView.getColor();
        }

        public void applyColor(DotColorValue color) {
            colorView.applyColor(color);
            argbColorPicker.applyColor(color);
        }
    }

    private static class ColorView extends LinearLayout {
        private static final int size = ScreenSize.getIconSize();
        private static final int padding = ScreenSize.getIconSize() / 2;

        private DotColorValue color;
        private final DotRoundRectDrawable colorDrawable;

        public ColorView(Context context) {
            super(context);

            {
                View view = new View(context);
                DotRoundRectDrawable colorDrawable = this.colorDrawable = new DotRoundRectDrawable();
                view.setBackground(colorDrawable);
                view.setPadding(padding, padding, padding, padding);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        size, size);
                view.setLayoutParams(layoutParams);
                addView(view);
            }
        }

        public DotColorValue getColor() {
            return color;
        }

        public void applyColor(DotColorValue color) {
            if (!color.equals(this.color)) {
                this.color = color;
                colorDrawable.setBackgroundColor(color.getValue());
                getChildAt(0).invalidate();
            }
        }
    }
}
