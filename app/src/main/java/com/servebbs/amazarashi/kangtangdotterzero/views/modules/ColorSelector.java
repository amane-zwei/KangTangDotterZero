package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;

public class ColorSelector extends GridView {
    public ColorSelector(Context context) {
        super(context);
        setBackgroundColor(0xffffff00);

        final int iconSize = ScreenSize.getIconSize();
        final int margin = ScreenSize.getMargin();

        final int[] colors = {
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,
                0xffffa0a0,
                0xffa0ffa0,
                0xffa0a0ff,
                0xffa0ffff,};

        setNumColumns(GridView.AUTO_FIT);
        setColumnWidth(iconSize);
        setHorizontalSpacing(margin);
        setVerticalSpacing(margin);
        setAdapter(
                new GridAdapter(colors)
        );
    }

    class GridAdapter extends BaseAdapter {

        private int[] colorList;

        GridAdapter(int[] colorList) {
            super();
            this.colorList = colorList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                final int iconSize = ScreenSize.getIconSize();

                convertView = new ColorView(getContext());
                GridView.LayoutParams layoutParams = new GridView.LayoutParams(
                        iconSize,
                        iconSize
                );
                convertView.setLayoutParams(layoutParams);
            }

            ((ColorView) convertView).setColor(colorList[position]);

            return convertView;
        }

        @Override
        public int getCount() {
            return colorList.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        setMeasuredDimension(widthSize, heightSize);
//    }
}
