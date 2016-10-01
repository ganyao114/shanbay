package com.gy.shanbay.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gy.shanbay.Model.Entity.WordInChapter;
import com.gy.shanbay.Presenter.Factory.ArticleFactory;
import com.gy.shanbay.Presenter.Factory.WordsFactory;
import com.gy.shanbay.Presenter.Filter.WordsFilter;
import com.gy.shanbay.Presenter.Listener.OnClickSpan;
import com.gy.shanbay.R;
import com.gy.shanbay.View.Adapter.IndexAdapter;

public class MainActivity extends AppCompatActivity implements OnClickSpan{

    private ExpandableListView index;
    private IndexAdapter adapter;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        index = (ExpandableListView) findViewById(R.id.index);
        content = (TextView) findViewById(R.id.content);
        index.setGroupIndicator(null);
        ArticleFactory factory = new ArticleFactory();
        factory.decode(this);
        WordsFactory wordsFactory = new WordsFactory();
        wordsFactory.decode(this);
        adapter = new IndexAdapter(this,factory.getArticle());
        index.setAdapter(adapter);
        WordsFilter filter = new WordsFilter();
        filter.setOnClickSpan(this);
        try {
            filter.decodeWordInChapter(factory.getArticle().getUnits().get(0).getLessons().get(0),wordsFactory.getWordsMap(),wordsFactory.getWordGroupsMap());
            filter.setSpan(factory.getArticle().getUnits().get(0).getLessons().get(0).getContent(),content,factory.getArticle().getUnits().get(0).getLessons().get(0).getWords(),4);
            Log.e("gy","单词数量"+factory.getArticle().getUnits().get(0).getLessons().get(0).getWords().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWordClicked(WordInChapter word) {
        Toast.makeText(this,word.getWord(),Toast.LENGTH_LONG).show();
    }
}
