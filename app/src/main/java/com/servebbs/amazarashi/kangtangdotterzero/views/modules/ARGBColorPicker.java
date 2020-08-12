package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotEditText;

public class ARGBColorPicker extends LinearLayout {
    static GradientDrawable divider;

    static {
        divider = new GradientDrawable();
        divider.setColor(0x00);
        divider.setSize(0, 8);
    }

    private ColorPicker[] colorPickers;
    private OnColorChangeListener onColorChangeListener;

    public ARGBColorPicker(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(0xffffffff);
        setDividerDrawable(divider);
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

        colorPickers = new ColorPicker[4];
        for (int index = 0; index < colorPickers.length; index++) {
            ColorPicker colorPicker = colorPickers[index] = new ColorPicker(context);
            colorPicker.parent = this;
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
            colorPicker.setLayoutParams(layoutParams);
            addView(colorPicker);
        }

        onColorChangeListener = null;
    }

    public void setColor(int color) {
        for (int index = colorPickers.length - 1; index >= 0; index--) {
            ColorPicker colorPicker = colorPickers[index];
            colorPicker.setColor(color & 0xff);
            color >>= 8;
        }
    }

    public int getColor() {
        int color = 0;
        for( int index = 0; index < colorPickers.length; index++) {
            color += colorPickers[index].getColor();
            color <<= 8;
        }
        return color;
    }

    private void onColorChange() {
        if(onColorChangeListener != null) {
            onColorChangeListener.onColorChange(getColor());
        }
    }

    public interface OnColorChangeListener {
        void onColorChange(int color);
    }

    private static class ColorPicker extends LinearLayout {
        private static final InputFilter[] inputFilters = {
                new InputFilter.LengthFilter(3)
        };

        private DotEditText editText;
        private SeekBar seekBar;
        private ARGBColorPicker parent;

        public ColorPicker(Context context) {
            super(context);

            setOrientation(LinearLayout.HORIZONTAL);
            setGravity(Gravity.CENTER_HORIZONTAL);
            setWeightSum(100);
            setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            setFocusableInTouchMode(true);
            {
                DotEditText editText = this.editText = new DotEditText(context);
                editText.setSelectAllOnFocus(true);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setFilters(inputFilters);
                editText.setGravity(Gravity.CENTER);
                int size = (int) (editText.getTextSize() * 2);
                editText.setLayoutParams(
                        new LinearLayoutCompat.LayoutParams(
                                size,
                                size
                        )
                );
                addView(editText);
            }
            {
                SeekBar seekBar = this.seekBar = new SeekBar(context);
                seekBar.setMax(0xff);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(
                                0,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        );
                layoutParams.weight = 85;
                seekBar.setLayoutParams(layoutParams);
                addView(seekBar);
            }
            this.editText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        EditText editText = (EditText) v;
                        int color = normalize(Integer.parseInt(editText.getText().toString()));
                        seekBar.setProgress(color);
                        editText.setText(Integer.toString(color));
                        parent.onColorChange();
                    }
                }
            });
            this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        editText.setText(Integer.toString(normalize(progress)));
                        parent.onColorChange();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }

        public void setColor(int color) {
            color = normalize(color);
            editText.setText(Integer.toString(color));
            seekBar.setProgress(color);
        }

        public int getColor() {
            return seekBar.getProgress();
        }

        private int normalize(int color) {
            if (color < 0) {
                return 0;
            }
            return Math.min(color, 0xff);
        }
    }
}
