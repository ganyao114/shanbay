package com.gy.shanbay.Model.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gy939 on 2016/9/28.
 */
public class Article {
    private List<Unit> units = new ArrayList<>();

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }
}
