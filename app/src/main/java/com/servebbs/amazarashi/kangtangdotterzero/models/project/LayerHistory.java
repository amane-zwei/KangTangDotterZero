package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class LayerHistory {
    @Getter
    private int index;

    private final List<LayerData> list;

    public LayerHistory(LayerData src) {
        list = new ArrayList<>();
        clear(src);
    }

    public LayerData applyIndex(int historyIndex) {
        for (index = list.size(); index > 0; index--) {
            LayerData layerData = list.get(index - 1);
            if (layerData.historyIndex <= historyIndex) {
                return layerData;
            }
        }
        return list.get(0);
    }

    public void addIndex() {
        index++;
    }

    public void add(LayerData layerData) {
        if (index < list.size()) {
            list.subList(index, list.size()).clear();
        }
        list.add(layerData.copy());
        index++;
    }

    public void clear(LayerData layerData) {
        list.clear();
        list.add(layerData.copy());
        index = 0;
    }
}
