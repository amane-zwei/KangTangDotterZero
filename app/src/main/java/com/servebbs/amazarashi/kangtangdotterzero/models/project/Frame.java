package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.models.histories.History;
import com.servebbs.amazarashi.kangtangdotterzero.models.histories.HistoryList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class Frame {

    @Getter
    private int width;
    @Getter
    private int height;
    @Getter
    @Setter
    private int interval = 100;

    @JsonIgnore
    private int index;
    @Getter
    private ArrayList<Layer> layers = new ArrayList<>();
    @JsonIgnore
    private Project project;

    public Frame(Project project, int width, int height) {
        this.project = project;
        this.width = width;
        this.height = height;

        addLayer();
        index = 0;
    }

    @JsonIgnore
    public Layer getLayer() {
        return layers.get(this.index);
    }

    public void addLayer() {
        Layer layer = new Layer(project.generateId(), width, height);
        layers.add(layer);
        project.putLayer(layer);
    }

    public Canvas render(Canvas destination) {

        int length = layers.size();
        for (int idx = 0; idx < length; idx++) {
            layers.get(idx).render(destination);
        }
        return destination;
    }
}
