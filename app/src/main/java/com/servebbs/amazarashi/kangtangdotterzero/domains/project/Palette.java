package com.servebbs.amazarashi.kangtangdotterzero.domains.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.domains.bitmap.ColorList;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColorValue;

import lombok.Getter;
import lombok.Setter;

public class Palette extends ColorList {
    private static final int[] defaultColors = {
            0xff000000,
            0xffffffff,
            0xffff0000,
            0xff00ff00,
            0xff0000ff,
            0xffffff00,
            0xff00ffff,
            0xffff00ff,
    };

    public static Palette createDefault(boolean useClearColor) {
        Palette palette = new Palette();
        if (useClearColor) {
            palette.addColor(0x00000000);
            palette.index = 1;
        }
        palette.addColors(defaultColors);
        palette.normalizeIndex(useClearColor);
        return palette;
    }

    private Palette() {
    }

    public Palette(ColorList src) {
        copy(src);
        index = 0;
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
    public DotColorValue getColor() {
        return getColor(index);
    }

    public void setColor(DotColorValue color) {
        setColor(index, color);
    }

    public DotColorValue removeColor() {
        DotColorValue color = removeColor(index);
        if (index >= size() -1) {
            index = size() -1;
        }
        return color;
    }

    public void normalizeIndex(boolean useClearColor) {
        index = useClearColor ? 1 : 0;
    }

    @Getter
    @Setter
    @JsonIgnore
    private int index;
}
