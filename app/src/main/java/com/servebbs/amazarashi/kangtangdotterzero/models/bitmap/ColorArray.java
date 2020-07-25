package com.servebbs.amazarashi.kangtangdotterzero.models.bitmap;

import java.util.Vector;

public class ColorArray {
    public final int colorMax = 255;

    private Vector<Integer> array = new Vector<Integer>();

    public ColorArray(){}
    public ColorArray(int[] colors){ addColors(colors); }
    public ColorArray(ColorArray src){ addColors(src); }

    public void addColor(){ addColor(0xffffffff); }
    public void addColor(int color){ array.add(color); }
    public void addColors(int[] colors){
        for( int idx=0; idx<colors.length; idx++ ){
            array.add(colors[idx]);
        }
    }
    public void addColors(ColorArray src){
        for( int idx=0; idx<src.array.size(); idx++ ){
            array.add(src.array.elementAt(idx));
        }
    }

    public Vector<Integer> getArray(){ return array; }
    public int size(){ return array.size(); }
    public void clear(){ array.clear(); }

    public int getColor(int index) { return array.get(index); }
    public void setColor(int index, int color){ array.setElementAt(color, index); }

    public int findIndex(int color) {
        int idx;
        int length = array.size();
        for(idx=0; idx<length; idx++ ){
            if( color == array.elementAt(idx) )
                return idx;
        }
        if( canAddColor() ){
            array.add(color);
            return idx;
        }
        return 0;
    }

    public boolean canAddColor(){ return array.size() < colorMax; }
}
