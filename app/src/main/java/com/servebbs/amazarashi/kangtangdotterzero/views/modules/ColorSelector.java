package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotColor;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;

import lombok.Setter;

public class ColorSelector extends LinearLayout {

    private Palette palette = null;
    private final ColorGridView colorGridView;
    @Setter
    private OnColorSelectListener onColorSelectListener = null;

    public ColorSelector(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);

        {
            MenuView menuView = new MenuView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.gravity = Gravity.RIGHT;
            menuView.setLayoutParams(layoutParams);
            addView(menuView);
        }

        {
            ColorGridView colorGridView = this.colorGridView = new ColorGridView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0
            );
            layoutParams.weight = 1;
            colorGridView.setLayoutParams(layoutParams);
            addView(colorGridView);
        }
    }

    public void attachPalette(Palette palette) {
        this.palette = palette;

        GridAdapter adapter = new GridAdapter(palette);
        colorGridView.setAdapter(adapter);
        colorGridView.setSelection(palette.getIndex());
    }

    public void applyColor(DotColor color) {
        palette.setColor(color);
        colorGridView.invalidateViews();
    }

    private void minusColor() {
        palette.removeColor();
        colorGridView.invalidateViews();
    }

    private class ColorGridView extends GridView {
        public ColorGridView(Context context) {
            super(context);

            final int iconSize = ScreenSize.getIconSize();

            setStretchMode(GridView.NO_STRETCH);
            setNumColumns(GridView.AUTO_FIT);
            setColumnWidth(iconSize);
            setHorizontalSpacing(0);
            setVerticalSpacing(0);

            setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                if (position == palette.size()) {
                    palette.addColor(0xffffffff);
                }
                palette.setIndex(position);
                if (onColorSelectListener != null) {
                    onColorSelectListener.onColorSelect(palette.getColor());
                }
                invalidateViews();
            });
        }
    }

    private class MenuView extends LinearLayout {
        public MenuView(Context context) {
            super(context);

            final int iconSize = ScreenSize.getIconSize();
            final int dotSize = ScreenSize.getDotSize();

            setOrientation(LinearLayout.HORIZONTAL);
            setPadding(0,0,iconSize / 2, 0);

            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setStroke(dotSize, 0xff000000);
            gradientDrawable.setColor(0xffc0c0ff);
            setBackground(gradientDrawable);

            {
                SimpleIconView iconView = new SimpleIconView(context);
                iconView.setRect(DotIcon.minusColor.createRect());
                iconView.setOnClickListener((View view) -> minusColor() );
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        iconSize,
                        iconSize
                );
                iconView.setLayoutParams(layoutParams);
                addView(iconView);
            }
        }
    }

    public interface OnColorSelectListener {
        void onColorSelect(DotColor color);
    }

    private class GridAdapter extends BaseAdapter {

        private final Palette palette;

        private GridAdapter(Palette palette) {
            super();
            this.palette = palette;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ColorView colorView;
            if (convertView == null) {
                final int iconSize = ScreenSize.getIconSize();

                colorView = new ColorView(getContext());
                colorView.setPalette(palette);
                GridView.LayoutParams layoutParams = new GridView.LayoutParams(
                        iconSize,
                        iconSize
                );
                colorView.setLayoutParams(layoutParams);
            } else {
                colorView = (ColorView) convertView;
            }

            if (position == palette.size()) {
                colorView.setIndex(-1);
            } else {
                colorView.setIndex(position);
            }

            return colorView;
        }

        @Override
        public int getCount() {
            return palette.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return palette.getColor(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
