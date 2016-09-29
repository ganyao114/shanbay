package com.gy.shanbay.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gy.shanbay.Model.Factory.ArticleFactory;
import com.gy.shanbay.Model.Factory.WordsFactory;
import com.gy.shanbay.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArticleFactory factory = new ArticleFactory();
        factory.decode(this);
        WordsFactory wordsFactory = new WordsFactory();
        wordsFactory.decode(this);
    }
}
