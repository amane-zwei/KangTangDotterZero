package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;

import lombok.Setter;

public class ColorSelector extends LinearLayout {

    private Palette palette = null;
    private ColorGridView colorGridView;
    @Setter
    private OnColorSelectListener onColorSelectListener = null;

    public ColorSelector(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);

        {
            MenuView menuView = new MenuView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    32
            );
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

    public void applyColor(int color) {
        palette.setColor(color);
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

            setOrientation(LinearLayout.HORIZONTAL);
            addView(new View(context) {
                public void onDraw(Canvas canvas) {
                    canvas.drawColor(0xffff0000);
                }
            });
        }
    }

    public interface OnColorSelectListener {
        void onColorSelect(int color);
    }

    private class GridAdapter extends BaseAdapter {

        private Palette palette;

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
                colorView.setIndex(0);
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
