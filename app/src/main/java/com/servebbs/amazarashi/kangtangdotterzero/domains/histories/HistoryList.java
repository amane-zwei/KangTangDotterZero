package com.servebbs.amazarashi.kangtangdotterzero.domains.histories;

import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class HistoryList {
    @Getter
    private int index;
    private final List<History> list;

    public boolean applyHistory(Project project) {
        return applyHistory(project, list.size());
    }

    public boolean applyHistory(Project project, int delta) {
        int end = this.index + delta;
        if (end < 0 || list.size() < end) {
            return false;
        }
        applyHistory(project, delta > 0 ? this.index : project.restoreLayerHistory(end), end);
        this.index = end;
        return true;
    }

    private boolean applyHistory(Project project, int start, int end) {
        for (; start < end; start++) {
            History history = list.get(start);
            Layer layer = project.findLayer(history.getLayerId());
            history.draw(layer, project.getPalette());
            layer.touch();
        }
        return true;
    }

    public History get(int index) {
        return list.get(index);
    }

    public void add(History history) {
        if (list.size() > index) {
            list.subList(index, list.size()).clear();
        }
        list.add(history);
        index++;
    }

    public void setHistory(History[] histories) {
        if (histories == null) {
            return;
        }
        for (History history : histories) {
            history.restore();
        }
        list.addAll(Arrays.asList(histories));
    }

    public void clear() {
        list.clear();
        index = 0;
    }

    public HistoryList() {
        list = new ArrayList<>();
        index = 0;
    }

    public History[] toArray() {
        return list.toArray(new History[0]);
    }
}
