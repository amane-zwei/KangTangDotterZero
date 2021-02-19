package com.servebbs.amazarashi.kangtangdotterzero.views.modules.files;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.KTDZTheme;
import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotEditText;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotTextView;

public class ProjectSizeView extends LinearLayout {
    private static final int marginLeft = ScreenSize.getDotSize() * 2;
    private static final int marginRight = ScreenSize.getDotSize();

    private final SizeEditView widthView;
    private final SizeEditView heightView;

    public ProjectSizeView(Context context) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);

        {
            SizeEditView sizeEditView = widthView = new SizeEditView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.gravity = Gravity.BOTTOM;
            sizeEditView.setLayoutParams(layoutParams);
            addView(sizeEditView);
        }
        {
            DotTextView dotTextView = new DotTextView(context);
            dotTextView.setText("x");
            dotTextView.setTextColor(KTDZTheme.textColor);
            dotTextView.setGravity(Gravity.BOTTOM);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.leftMargin = marginLeft;
            layoutParams.rightMargin = marginRight;
            dotTextView.setLayoutParams(layoutParams);
            addView(dotTextView);
        }
        {
            SizeEditView sizeEditView = heightView = new SizeEditView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.gravity = Gravity.BOTTOM;
            sizeEditView.setLayoutParams(layoutParams);
            addView(sizeEditView);
        }
    }

    public void set(int width, int height) {
        widthView.setValue(width);
        heightView.setValue(height);
    }

    public int getWidthValue() {
        return widthView.getValue();
    }

    public int getHeightValue() {
        return heightView.getValue();
    }

    private static class SizeEditView extends DotEditText {
        private SizeEditView(Context context) {
            super(context);
            setSelectAllOnFocus(true);
            setInputType(InputType.TYPE_CLASS_NUMBER);
            setGravity(Gravity.CENTER);
            setMinimumWidth(DotFont.numericWidth * 3);
        }

        private int getValue() {
            Editable text = getText();
            if (text == null) {
                return 1;
            }
            return Integer.parseInt(text.toString());
        }

        private void setValue(int value) {
            setText(Integer.toString(value));
        }
    }
}
