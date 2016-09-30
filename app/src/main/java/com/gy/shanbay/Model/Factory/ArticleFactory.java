package com.gy.shanbay.Model.Factory;

import android.content.Context;
import android.content.res.Resources;

import com.gy.shanbay.Model.Entity.Article;
import com.gy.shanbay.Model.Entity.Lesson;
import com.gy.shanbay.Model.Entity.Unit;
import com.gy.shanbay.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * Created by gy939 on 2016/9/28.
 */
public class ArticleFactory {

    private Article article = new Article();

    public Article getArticle() {
        return article;
    }

    public void decode(Context context){
        Resources resources = context.getResources();
        InputStreamReader is = null;
        is = new InputStreamReader(resources.openRawResource(R.raw.article));
        BufferedReader bufReader = new BufferedReader(is);
        Unit curunit = null;
        Lesson curlesson = null;
        StringType curIn = null;
        StringBuffer conten= new StringBuffer();
        String line="";
        int linecount = 0;
        try {
            while((line = bufReader.readLine()) != null){
                linecount++;
                StringType type = filter(line);
                switch (type){
                    case Unit:
                        //如果不是第一章节保存上一个Unit
                        if (linecount == 1){
                            curunit = new Unit();
                            curunit.setTitle(line);
                            curunit.setUid(article.getUnits().size() + 1);
                            article.getUnits().add(curunit);
                        }else {
                            curunit = curunit.clone();
                            curunit.setTitle(line);
                            curunit.setUid(article.getUnits().size() + 1);
                            article.getUnits().add(curunit);
                        }
                        break;
                    case Lesson:
                        //保存上一个Lesson
                        if (curlesson != null){
                            curlesson.setContent(conten.toString());
                            conten = new StringBuffer();
                            curlesson = curlesson.clone();
                            curlesson.setUid(curunit.getUid());
                            curlesson.setTitle(line);
                            curunit.getLessons().add(curlesson);
                        }else {
                            curlesson = new Lesson();
                            curlesson.setUid(1);
                            curlesson.setTitle(line);
                            curunit.getLessons().add(curlesson);
                        }
                        break;
                    case Text:
                        if (curunit != null&&curlesson != null){
                            conten.append(line + "\n");
                        }else {

                        }
                        break;
                }
            }

            curlesson.setContent(conten.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
                try {
                    if (is != null)
                    is.close();
                    if (bufReader != null)
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private StringType filter(String line) {
        Pattern unitp = Pattern.compile(Unit.MATCH_STR);
        Pattern lessonp =  Pattern.compile(Lesson.MATCH_STR);
        if (unitp.matcher(line).find()){
            return StringType.Unit;
        }else if (lessonp.matcher(line).find()){
            return StringType.Lesson;
        }else {
            return StringType.Text;
        }
    }

    enum StringType {
        Unit,
        Lesson,
        Text
    }

}
