package com.gy.shanbay.Model.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gy939 on 2016/9/28.
 */
public class Unit implements Cloneable{

    public final static String MATCH_STR = "Unit [1-9][0-9]?";

    private int uid;
    private String title;
    private List<Lesson> lessons = new ArrayList<>();

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public Unit clone() throws CloneNotSupportedException {
        Unit unit = (Unit) super.clone();
        unit.setLessons(new ArrayList<Lesson>());
        unit.setTitle(null);
        unit.setUid(0);
        return unit;
    }
}
