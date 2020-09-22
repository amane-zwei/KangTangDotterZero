package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.ColorList;

import lombok.Getter;
import lombok.Setter;

public class Palette extends ColorList implements Cloneable {
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

    public int getColor() {
        return getColor(index);
    }

    public void setColor(int color) {
        setColor(index, color);
    }

    public boolean equals(Palette palette) {
        return this.index == palette.index && super.equals(palette);
    }

    @Getter
    @Setter
    private int index;

    @Override
    public Palette clone() {
        try {
            Palette palette = (Palette)super.clone();
            palette.index = this.index;
            return palette;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return empty();
        }
    }
}
