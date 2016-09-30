package com.gy.shanbay.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.gy.shanbay.Model.Entity.WordInChapter;
import com.gy.shanbay.Model.Factory.ArticleFactory;
import com.gy.shanbay.Model.Factory.WordsFactory;
import com.gy.shanbay.Presenter.Filter.WordsFilter;
import com.gy.shanbay.R;
import com.gy.shanbay.View.Adapter.IndexAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView index;
    private IndexAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        index = (ExpandableListView) findViewById(R.id.index);
        ArticleFactory factory = new ArticleFactory();
        factory.decode(this);
        WordsFactory wordsFactory = new WordsFactory();
        wordsFactory.decode(this);
        adapter = new IndexAdapter(this,factory.getArticle());
        index.setAdapter(adapter);
        WordsFilter filter = new WordsFilter();
        try {
            filter.decodeWordInChapter(factory.getArticle().getUnits().get(0).getLessons().get(0),wordsFactory.getWordsMap(),wordsFactory.getWordGroupsMap());
            Log.e("gy","单词数量"+factory.getArticle().getUnits().get(0).getLessons().get(0).getWords().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
