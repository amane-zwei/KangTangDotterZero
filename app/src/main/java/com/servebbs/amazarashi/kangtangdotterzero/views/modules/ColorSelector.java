package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.servebbs.amazarashi.kangtangdotterzero.drawables.CursorDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;

public class ColorSelector extends GridView {

    Palette palette = null;

    public ColorSelector(Context context) {
        super(context);

        final int iconSize = ScreenSize.getIconSize();
        final int margin = ScreenSize.getMargin();

        setSelector(new CursorDrawable());
        setDrawSelectorOnTop(true);
        setClipToPadding(false);

        setNumColumns(GridView.AUTO_FIT);
        setColumnWidth(iconSize);
        setHorizontalSpacing(margin);
        setVerticalSpacing(margin);

        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                android.util.Log.d("hogehoge", "on selected:::" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                android.util.Log.d("hogehoge", "on nothing:::");
            }
        });
//        setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//android.util.Log.d("hogehoge", "on clicked" + position);
//            }
//        });
    }

    public void attachPalette(Palette palette) {
        this.palette = palette;
        setAdapter(new GridAdapter(palette));
        setSelection(palette.getIndex());
    }

    class GridAdapter extends BaseAdapter {

        private Palette palette;

        private GridAdapter(Palette palette) {
            super();
            this.palette = palette;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                final int iconSize = ScreenSize.getIconSize();

                convertView = new ColorView(getContext());
                convertView.setClickable(false);
                convertView.setFocusable(false);
                convertView.setFocusableInTouchMode(false);
                GridView.LayoutParams layoutParams = new GridView.LayoutParams(
                        iconSize,
                        iconSize
                );
                convertView.setLayoutParams(layoutParams);
            }

            ((ColorView) convertView).setColor(palette.getColor(position));

            return convertView;
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
