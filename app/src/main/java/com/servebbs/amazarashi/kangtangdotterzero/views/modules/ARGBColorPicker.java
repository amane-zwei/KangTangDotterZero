package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.UnderLineDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColorValue;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotEditText;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotSeekBar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ARGBColorPicker extends LinearLayout {

    private static final ColorData[] colorData = {
            new ColorData(0xff808080, 0xffffffff),
            new ColorData(0xffff4040, 0xffffffff),
            new ColorData(0xff40ff40, 0xffffffff),
            new ColorData(0xff4040ff, 0xffffffff),
    };
    private static final GradientDrawable divider;

    static {
        divider = new GradientDrawable();
        divider.setColor(0x00);
        divider.setSize(0, 2);
    }

    private final ColorPicker[] colorPickers;
    @Setter
    private OnColorChangeListener onColorChangeListener;

    public ARGBColorPicker(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);
        setDividerDrawable(divider);
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

        colorPickers = new ColorPicker[4];
        for (int index = 0; index < colorPickers.length; index++) {
            ColorData data = colorData[index];
            ColorPicker colorPicker = colorPickers[index] = new ColorPicker(context);
            colorPicker.setBarColor(data.getMainColor(), data.getSubColor());
            colorPicker.parent = this;
            colorPicker.setBackground(new UnderLineDrawable(0xff000000));
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            0,
                            1
                    );
            colorPicker.setLayoutParams(layoutParams);
            addView(colorPicker);
        }

        onColorChangeListener = null;
    }

    public void applyColor(DotColorValue color) {
        applyIntColor(color.getValue());
    }

    private void applyIntColor(int intValue) {
        for (int index = colorPickers.length - 1; index >= 0; index--) {
            ColorPicker colorPicker = colorPickers[index];
            colorPicker.setColor(intValue & 0xff);
            intValue >>= 8;
        }
    }

    public DotColorValue getColor() {
        return new DotColorValue(getIntColor());
    }

    private int getIntColor() {
        int intValue = 0;
        for (int index = 0; index < colorPickers.length; index++) {
            intValue <<= 8;
            intValue += colorPickers[index].getColor();
        }
        return intValue;
    }

    private void onColorChange() {
        if (onColorChangeListener != null) {
            onColorChangeListener.onColorChange(getColor());
        }
    }

    public interface OnColorChangeListener {
        void onColorChange(DotColorValue color);
    }

    private static class ColorPicker extends LinearLayout {
        private static final InputFilter[] inputFilters = {
                new InputFilter.LengthFilter(3)
        };

        private final DotEditText editText;
        private final DotSeekBar seekBar;
        private ARGBColorPicker parent;

        public ColorPicker(Context context) {
            super(context);


            setOrientation(LinearLayout.HORIZONTAL);
            setGravity(Gravity.CENTER_HORIZONTAL);
            setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            setFocusableInTouchMode(true);
            {
                final int padding = ScreenSize.getDotSize() * 4;

                DotEditText editText = this.editText = new DotEditText(context);
                editText.setSelectAllOnFocus(true);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setFilters(inputFilters);
                editText.setGravity(Gravity.CENTER);
                editText.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                DotFont.numericWidth * 3 + padding,
                                LayoutParams.MATCH_PARENT
                        ));
                addView(editText);
            }
            {
                final int paddingV = ScreenSize.getDotSize();
                DotSeekBar seekBar = this.seekBar = new DotSeekBar(context);
                seekBar.setMax(0xff);
                seekBar.setPadding(seekBar.getPaddingLeft(), paddingV, seekBar.getPaddingRight(), paddingV);
                seekBar.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                0,
                                LayoutParams.MATCH_PARENT,
                                1
                        ));
                addView(seekBar);
            }
            this.editText.setOnFocusChangeListener((view, hasFocus) -> {
                if (!hasFocus) {
                    EditText editText = (EditText) view;
                    int color = normalize(Integer.parseInt(editText.getText().toString()));
                    seekBar.setProgress(color);
                    editText.setText(Integer.toString(color));
                    parent.onColorChange();
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

        public void setBarColor(int mainColor, int subColor) {
            seekBar.setColor(mainColor, subColor);
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

    @Getter
    @AllArgsConstructor
    private static class ColorData {
        private final int mainColor;
        private final int subColor;
    }
}
