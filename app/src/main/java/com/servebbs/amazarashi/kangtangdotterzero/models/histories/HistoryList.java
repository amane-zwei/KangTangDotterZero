package com.servebbs.amazarashi.kangtangdotterzero.models.histories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class HistoryList {
    @JsonIgnore
    @Getter
    @Setter
    private int index;
    @Getter
    private final List<History> list;

    public boolean applyHistory(Project project, int delta) {
        int end = this.index + delta;
        if (end < 0 || list.size() < end) {
            return false;
        }
        applyHistory(project, delta < 0 ? project.restoreLayerHistory(end) : this.index, end);
        this.index = end;
        return true;
    }

    private boolean applyHistory(Project project, int start, int end) {
        for ( ; start < end; start++) {
            History history = list.get(start);
            history.draw(project.findLayer(history.getLayerId()));
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
    public void clear() {
        list.clear();
        index = 0;
    }

    public HistoryList() {
        list = new ArrayList<>();
        index = 0;
    }
}
