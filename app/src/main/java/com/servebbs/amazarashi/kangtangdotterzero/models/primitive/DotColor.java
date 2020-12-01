package com.servebbs.amazarashi.kangtangdotterzero.models.primitive;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DotColor {
    private int value;

    public void set(int value) {
        this.value = value;
    }

    public int intValue() {
        return value;
    }
}
