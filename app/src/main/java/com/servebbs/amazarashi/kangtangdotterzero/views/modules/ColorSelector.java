package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;

import lombok.Setter;

public class ColorSelector extends GridView {

    private Palette palette = null;
    @Setter
    private OnColorSelectListener onColorSelectListener = null;

    public ColorSelector(Context context) {
        super(context);

        final int iconSize = ScreenSize.getIconSize();

        setNumColumns(GridView.AUTO_FIT);
        setColumnWidth(iconSize);
        setHorizontalSpacing(0);
        setVerticalSpacing(0);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                palette.setIndex(position);
                if (onColorSelectListener != null) {
                    onColorSelectListener.onColorSelect(palette.getColor());
                }
                invalidateViews();
            }
        });
    }

    public void attachPalette(Palette palette) {
        this.palette = palette;
        setAdapter(new GridAdapter(palette));
        setSelection(palette.getIndex());
    }

    public void applyColor(int color) {
        palette.setColor(color);
        invalidateViews();
    }

    public interface OnColorSelectListener {
        void onColorSelect(int color);
    }

    class GridAdapter extends BaseAdapter {

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
            colorView.setIndex(position);

            return colorView;
        }

        @Override
        public int getCount() {
            return palette.size();
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
