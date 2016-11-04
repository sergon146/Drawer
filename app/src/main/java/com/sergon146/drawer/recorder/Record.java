package com.sergon146.drawer.recorder;

import com.sergon146.drawer.model.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergon on 27.10.16.
 */

public class Record implements Serializable {
    private List<List<Drawable>> record;
    private int id;

    public Record() {
        record = new ArrayList<>();
        id = 0;
    }

    public void addList(List<Drawable> list) {
        record.add(list);
    }

    public int nextList() {
        if (id == record.size() - 1)
            id = 0;
        else id++;
        return id;
    }

    public int prevList() {
        if (id == 0)
            id = record.size() - 1;
        else id--;
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Drawable> getList(int id) {
        return record.get(id);
    }

    public void clear() {
        record.clear();
    }

    public int size() {
        return record.size();
    }

    public List<Drawable> getMorfList(double t, int idd, boolean backToTheFuture) {
        id = idd;
        List<Drawable> prevList ;
        List<Drawable> nextList;
        if (backToTheFuture) {
            prevList = record.get(id);
            nextList = record.get(nextList());
        } else{
            prevList = record.get(id);
            nextList = record.get(prevList());
        }
        List<Drawable> currentList = new ArrayList<>();
        Drawable nextFigure;
        for (int i = 0; i < nextList.size(); i++) {
            nextFigure = nextList.get(i);

            for (int j = 0; j < prevList.size(); j++) {
                if (prevList.get(j).getId() == nextFigure.getId()) {
                    currentList.add(prevList.get(j).morf(t, nextList.get(i)));
                    break;
                }
                if (j == prevList.size()-1)
                    currentList.add(nextList.get(i));
            }




        }
        return currentList;
    }
}
