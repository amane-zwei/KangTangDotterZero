package com.servebbs.amazarashi.kangtangdotterzero.views.modules.files;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.servebbs.amazarashi.kangtangdotterzero.KTDZTheme;
import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColorValue;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.SingleDividerDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs.SingleColorPickerDialog;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotCheckBox;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotTextView;

public class ProjectColorView extends LinearLayout {
    private final BackgroundColorView backgroundColorView;
    private final DotCheckBox isIndexedColorView;

    public ProjectColorView(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);
        setDividerDrawable(new SingleDividerDrawable(0x40000000));
        setShowDividers(SHOW_DIVIDER_MIDDLE);
        setDividerPadding(ScreenSize.getDotSize() * 4);

        {
            BackgroundColorView backgroundColorView = this.backgroundColorView = new BackgroundColorView(context);
            backgroundColorView.setText("background color");
            backgroundColorView.setFocusable(true);
            backgroundColorView.setOnClickListener(this::onClick);
            LayoutParams layoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            backgroundColorView.setLayoutParams(layoutParams);
            addView(backgroundColorView);
        }
        {
            DotCheckBox isIndexedColorView = this.isIndexedColorView = new DotCheckBox(context);
            isIndexedColorView.setText("use indexed-color");
            LayoutParams layoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            isIndexedColorView.setLayoutParams(layoutParams);
            addView(isIndexedColorView);
        }
    }

    public void set(DotColorValue backgroundColor, boolean isIndexedColor) {
        backgroundColorView.applyColor(backgroundColor);
        isIndexedColorView.setChecked(isIndexedColor);
    }

    public DotColorValue getBackgroundColor() {
        return backgroundColorView.getColor();
    }

    public boolean useIndexedColor() {
        return isIndexedColorView.isChecked();
    }

    public void onClick(View view) {
        new SingleColorPickerDialog()
                .applyColor(getBackgroundColor())
                .setOnPositive((DotColorValue color) -> {
                    backgroundColorView.applyColor(color);
                    return true;
                })
                .show(((AppCompatActivity) view.getContext()).getSupportFragmentManager(), "new_project_color_picker");
    }

    private static class BackgroundColorView extends DotTextView {
        private static final int iconSize = ScreenSize.getIconSize() / 2;
        private static final int textSize = DotFont.normalHeight / 2;
        private static final int padding = ScreenSize.getDotSize() * 2;

        private DotColorValue color;
        private final GradientDrawable colorDrawable;

        public BackgroundColorView(Context context) {
            super(context);

            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            setTextColor(KTDZTheme.textColor);
            setPadding(padding, 0, 0, 0);
            setGravity(Gravity.CENTER_VERTICAL);

            GradientDrawable colorDrawable = this.colorDrawable = new GradientDrawable();
            colorDrawable.setStroke(ScreenSize.getDotSize() / 2, 0xff000000);
            colorDrawable.setBounds(0, 0, DotCheckBox.size, DotCheckBox.size);
            setCompoundDrawables(colorDrawable, null, null, null);
            setCompoundDrawablePadding(padding);
        }

        public void applyColor(DotColorValue color) {
            this.color = color;
            colorDrawable.setColor(color.getValue());
        }

        public DotColorValue getColor() {
            return color;
        }
    }
}
