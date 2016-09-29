package com.gy.shanbay.Model.Entity;

/**
 * Created by gy939 on 2016/9/28.
 */
public class WordInChapter implements Cloneable{

    private Word word;
    private Position position;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    protected WordInChapter clone() throws CloneNotSupportedException {
        return (WordInChapter) super.clone();
    }

}
