package com.gy.shanbay.Model.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gy939 on 2016/9/28.
 */
public class Lesson implements Cloneable{

    public final static String MATCH_STR = "Lesson [1-9][0-9]?";

    private int uid;
    private int lid;
    private String title;
    private String content;
    private List<WordInChapter> words;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<WordInChapter> getWords() {
        return words;
    }

    public void setWords(List<WordInChapter> words) {
        this.words = words;
    }

    @Override
    public Lesson clone() throws CloneNotSupportedException {
        Lesson lesson = (Lesson) super.clone();
        lesson.setContent(null);
        lesson.setWords(null);
        return lesson;
    }

}
