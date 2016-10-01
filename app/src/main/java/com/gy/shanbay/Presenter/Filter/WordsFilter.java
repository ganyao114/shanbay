package com.gy.shanbay.Presenter.Filter;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.gy.shanbay.Model.Entity.Lesson;
import com.gy.shanbay.Model.Entity.Position;
import com.gy.shanbay.Model.Entity.WordInChapter;
import com.gy.shanbay.Presenter.Listener.OnClickSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gy939 on 2016/9/30.
 */
public class WordsFilter {

    private OnClickSpan onClickSpan;

    public void setOnClickSpan(OnClickSpan onClickSpan) {
        this.onClickSpan = onClickSpan;
    }

    public List<WordInChapter> getWordsInChapter(String str, Map<String,Integer> WordsMap) throws Exception {

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

    public void setSpan(String content,TextView tv,List<WordInChapter> words,int level){
        SpannableString ss = new SpannableString(content);
        for (final WordInChapter word:words){
            if (word.getLevel() > level)
                continue;
            @ColorInt int color = Color.RED;
            switch (word.getLevel()){
                case 0:
                    color = Color.MAGENTA;
                    break;
                case 1:
                    color = Color.RED;
                    break;
                case 2:
                    color = Color.BLUE;
                    break;
                case 3:
                    color = Color.GREEN;
                    break;
                case 4:
                    color = Color.YELLOW;
                    break;
            }
            final int finalColor = color;
            ss.setSpan(new ClickableSpan() {

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(finalColor);
                    ds.setUnderlineText(true);
                }

                @Override
                public void onClick(View widget) {
                    if (onClickSpan != null)
                        onClickSpan.onWordClicked(word);
                }
            },word.getPosition().getStart(),word.getPosition().getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public boolean isEnglish(char c){
        if ((c >= 'A' && c <= 'Z') ||(c >= 'a' && c <= 'z')||c == '-'){
            return true;
        }else {
            return false;
        }
    }

}
