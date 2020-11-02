package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.ColorList;

import lombok.Getter;
import lombok.Setter;

public class Palette extends ColorList {
    public static Palette createDefault() {
        final int[] colors = {
                0xff000000,
                0xffffffff,
                0xffff0000,
                0xff00ff00,
                0xff0000ff,
                0xffffff00,
                0xff00ffff,
                0xffff00ff,
        };
        return new Palette(colors);
    }

    public static Palette empty() {
        return new Palette(new int[0]);
    }

    public Palette(int[] colors) {
        super(colors);
        index = 0;
    }

    public Palette(Palette src) {
        super(src);
        index = src.index;
    }

    public int getColor() {
        return getColor(index);
    }

    public void setColor(int color) {
        setColor(index, color);
    }

    public int removeColor() {
        int color = removeColor(index);
        if (index >= size() -1) {
            index = size() -1;
        }
        return color;
    }

    public boolean equals(Palette palette) {
        return this.index == palette.index && super.equals(palette);
    }

    @Getter
    @Setter
    private int index;
}
