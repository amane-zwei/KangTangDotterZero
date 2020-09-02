package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.ColorList;

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
                0xffff00ff
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

    private int index;
    public int getIndex() { return index; }
}
