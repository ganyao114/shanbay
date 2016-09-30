package com.gy.shanbay.Model.Entity;

/**
 * Created by gy939 on 2016/9/28.
 */
public class Position implements Cloneable{
    private int start;
    private int end;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public Position clone() throws CloneNotSupportedException {
        return (Position) super.clone();
    }
}
