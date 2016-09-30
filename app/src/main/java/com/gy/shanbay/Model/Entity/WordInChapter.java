package com.gy.shanbay.Model.Entity;

/**
 * Created by gy939 on 2016/9/28.
 */
public class WordInChapter implements Cloneable{

    private String word;
    private Position position;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public WordInChapter clone() throws CloneNotSupportedException {
        return (WordInChapter) super.clone();
    }

}
