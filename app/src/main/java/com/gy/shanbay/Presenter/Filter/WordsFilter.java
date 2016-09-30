package com.gy.shanbay.Presenter.Filter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.gy.shanbay.Model.Entity.Lesson;
import com.gy.shanbay.Model.Entity.Position;
import com.gy.shanbay.Model.Entity.WordInChapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gy939 on 2016/9/30.
 */
public class WordsFilter {

    public List<WordInChapter> getWordsInChapter(String str,Map<String,Integer> WordsMap) throws Exception {

        WordInChapter w = new WordInChapter();
        Position p = new Position();
        List<WordInChapter> wordInChapters = new ArrayList<>();
        String words = "";

        for (int i = 0;i < str.length();i ++){
            char c = str.charAt(i);
            if (isEnglish(c)){
                words = words + c;
            }else {
                if (!words.equals("")){
                    if (!WordsMap.containsKey(words.toLowerCase())){
                        words = "";
                        continue;
                    }
                    WordInChapter wordInChapter = w.clone();
                    wordInChapter.setWord(words);
                    wordInChapter.setLevel(WordsMap.get(words.toLowerCase()));
                    Position position = p.clone();
                    position.setStart(i - words.length());
                    position.setEnd(i);
                    wordInChapter.setPosition(position);
                    wordInChapters.add(wordInChapter);
                    words = "";
                }else {
                    continue;
                }
            }
        }
        return wordInChapters;
    }

    public List<WordInChapter> getWordGroupsInChapter(String str,Map<String,Integer> WordGroupsMap) throws Exception{
        String content = str.toLowerCase();
        List<WordInChapter> wordInChapters = new ArrayList<>();
        for (Map.Entry<String,Integer> entry:WordGroupsMap.entrySet()){
            int p = content.indexOf(entry.getKey());
            while (p >= 0){
                WordInChapter wordInChapter = new WordInChapter();
                wordInChapter.setLevel(entry.getValue());
                Position position = new Position();
                position.setStart(p);
                position.setEnd(p + entry.getKey().length());
                wordInChapter.setPosition(position);
                wordInChapter.setWord(entry.getKey());
                wordInChapters.add(wordInChapter);
                p = str.indexOf(entry.getKey(),p+1);
            }
        }
        return wordInChapters;
    }

    public void decodeWordInChapter(Lesson lesson,Map<String,Integer> WordsMap,Map<String,Integer> WordGroupsMap){
        List<WordInChapter> res = new ArrayList<>();
        List<WordInChapter> wordsGroup = null;
        List<WordInChapter> words = null;
        try {
            words = getWordsInChapter(lesson.getContent(),WordsMap);
            wordsGroup = getWordGroupsInChapter(lesson.getContent(),WordGroupsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (words != null)
            res.addAll(words);
        if (wordsGroup != null)
            res.addAll(wordsGroup);
        lesson.setWords(res);
    }

    public void setSpan(String content,TextView tv,List<WordInChapter> words){
        SpannableString ss = new SpannableString(content);
        for (WordInChapter word:words){
            ss.setSpan(new ForegroundColorSpan(Color.RED),word.getPosition().getStart(),word.getPosition().getEnd(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        tv.setText(ss);
    }

    public boolean isEnglish(char c){
        if ((c >= 'A' && c <= 'Z') ||(c >= 'a' && c <= 'z') ){
            return true;
        }else {
            return false;
        }
    }

}
