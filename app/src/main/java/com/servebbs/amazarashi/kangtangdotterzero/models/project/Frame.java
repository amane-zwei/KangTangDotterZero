package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Frame {

    private int width;
    private int height;
    private int interval = 100;

    private int index;
    private ArrayList<Layer> layers = new ArrayList<Layer>();

    public Frame(int width, int height){
        this.width = width;
        this.height = height;

        addLayer();
        index = 0;
    }

    public Layer getLayer() { return layers.get(this.index); }

    public void addLayer() {
        layers.add(new Layer(width, height));
    }

    public Canvas render(Canvas destination){

        int length = layers.size();
        for(int idx=0; idx<length; idx++ ) {
            layers.get(idx).render(destination);
        }
        return destination;
    }
}
