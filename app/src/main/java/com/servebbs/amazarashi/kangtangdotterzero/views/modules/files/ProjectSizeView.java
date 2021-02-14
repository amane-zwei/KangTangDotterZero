package com.servebbs.amazarashi.kangtangdotterzero.views.modules.files;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotEditText;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotTextView;

public class ProjectSizeView extends LinearLayout {
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
            dotTextView.setTextColor(0xff000000);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.gravity = Gravity.BOTTOM;
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
            return Integer.parseInt(getText().toString());
        }

        private void setValue(int value) {
            setText(Integer.toString(value));
        }
    }
}
