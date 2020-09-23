package com.servebbs.amazarashi.kangtangdotterzero.models.bitmap;

import java.util.ArrayList;
import java.util.List;

public class ColorList {
    public static final int colorMax = 256;

    private List<Integer> array = new ArrayList<>();

    public ColorList(){}
    public ColorList(int[] colors){ addColors(colors); }
    public ColorList(ColorList src){ addColors(src); }

    public void addColor(){ addColor(0xffffffff); }
    public void addColor(int color){ array.add(color); }
    public void addColors(int[] colors){
        for( int idx=0; idx < colors.length; idx++ ){
            array.add(colors[idx]);
        }
    }
    public void addColors(ColorList src){
        this.array.addAll(src.array);
    }

    public int removeColor(int index) {
        if (array.size() < 2) {
            return 0;
        }
        return this.array.remove(index);
    }

    public List<Integer> getList(){ return array; }
    public int size(){ return array.size(); }

    public int getColor(int index) { return array.get(index); }
    public void setColor(int index, int color){ array.set(index, color); }

    public int findIndex(int color) {
        int index = array.indexOf(color);
        if (index >= 0) {
            return index;
        }
        if( canAddColor() ){
            array.add(color);
            return array.size() - 1;
        }
        return 0;
    }

    public boolean canAddColor(){ return array.size() < colorMax; }

    public boolean equals(ColorList colorList) {
        if (this.size() != colorList.size()) {
            return false;
        }
        for (int index = 0; index < this.size(); index++) {
            if (!this.array.get(index).equals(colorList.array.get(index))) {
                return false;
            }
        }
        return true;
    }
}
