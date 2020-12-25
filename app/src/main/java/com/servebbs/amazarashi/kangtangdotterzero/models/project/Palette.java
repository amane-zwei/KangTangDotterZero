package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.ColorList;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotColor;

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

    @JsonIgnore
    public DotColor getColor() {
        return getColor(index);
    }

    public void setColor(DotColor color) {
        setColor(index, color);
    }

    public DotColor removeColor() {
        DotColor color = removeColor(index);
        if (index >= size() -1) {
            index = size() -1;
        }
        return color;
    }

    @Getter
    @Setter
    private int index;
}
