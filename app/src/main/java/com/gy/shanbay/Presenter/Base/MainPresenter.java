package com.gy.shanbay.Presenter.Base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.gy.shanbay.Model.Entity.Lesson;
import com.gy.shanbay.Model.Entity.WordInChapter;
import com.gy.shanbay.Presenter.Factory.ArticleFactory;
import com.gy.shanbay.Presenter.Factory.WordsFactory;
import com.gy.shanbay.Presenter.Filter.WordsFilter;
import com.gy.shanbay.Presenter.Listener.OnClickSpan;
import com.gy.shanbay.View.Activity.MainActivity;
import com.gy.shanbay.View.Activity.ReadActivity;

import net.gy.SwiftFrameWork.IOC.Core.impl.IOC;
import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.annotation.ContentView;
import net.gy.SwiftFrameWork.Presenter.ActivityLife;
import net.gy.SwiftFrameWork.Presenter.ActivityLifeType;
import net.gy.SwiftFrameWork.Presenter.IPresenter;
import net.gy.SwiftFrameWork.Presenter.Presenter;
import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.OnPublisher;
import net.gy.SwiftFrameWork.Reactive.annotation.RunContext;
import net.gy.SwiftFrameWork.Reactive.entity.RunContextType;
import net.gy.SwiftFrameWork.Reactive.impl.Publisher;

import java.lang.ref.WeakReference;

/**
 * Created by gy939 on 2016/10/1.
 */
public class MainPresenter extends Presenter implements OnClickSpan{

    public ArticleFactory articleFactory = new ArticleFactory();
    public WordsFactory wordsFactory = new WordsFactory();
    private WordsFilter filter;
    public Lesson showingLesson;

    public boolean isDecoded = false;

    private WeakReference<ReadActivity> readViewRef;

    @Override
    public void onPresenterInit(IPresenter context) {
        super.onPresenterInit(context);
        filter = new WordsFilter();
        filter.setOnClickSpan(this);
        decode();
    }

    @Override
    public void onPresenterDestory(IPresenter context) {
        super.onPresenterDestory(context);
    }

    @ActivityLife(lifeType = ActivityLifeType.OnCreate,activity = ReadActivity.class)
    public void onReadViewCreate(Activity activity,Bundle savedInstanceState){
        readViewRef = new WeakReference<ReadActivity>((ReadActivity) activity);
    }


    @ActivityLife(lifeType = ActivityLifeType.OnDestory,activity = ReadActivity.class)
    public void onReadViewDestory(Activity activity){
        readViewRef.clear();
        Toast.makeText(activity,"destroy",Toast.LENGTH_LONG).show();
    }

    public void showWords(Lesson ls, final int level){
        final Lesson lesson;
        if (ls == null){
            lesson = showingLesson;
        }else {
            showingLesson = ls;
            lesson = ls;
        }
        TextView view = readViewRef.get().content;
        if (view == null)
            return;
        if (level == 0){
            view.setText(lesson.getContent());
            return;
        }
        if (lesson.getWords() == null){
            Publisher.<Lesson>getInstance().create(new OnPublisher<Lesson>() {
                @RunContext(RunContextType.Calculate)
                @Override
                public void call(OnObserver<Lesson> observer) {
                    filter.decodeWordInChapter(lesson,wordsFactory.getWordsMap(),wordsFactory.getWordGroupsMap());
                    observer.onSuccess(lesson);
                }
            }).bind(new OnObserver<Lesson>() {
                @RunContext(RunContextType.MainThread)
                @Override
                public void onSuccess(Lesson lesson) {
                    TextView view = readViewRef.get().content;
                    filter.setSpan(lesson.getContent(),view,lesson.getWords(),level - 1);
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onFinished() {

                }
            }).post();
            return;
        }
        filter.setSpan(lesson.getContent(),view,lesson.getWords(),level - 1);
    }

    private void decode(){
        Publisher.getInstance().create(new OnPublisher<Object>() {
            @Override
            @RunContext(RunContextType.Calculate)
            public void call(OnObserver<Object> observer) {
                articleFactory.decode(IOC.getInstance().getApplication());
                wordsFactory.decode(IOC.getInstance().getApplication());
                observer.onSuccess(null);
            }
        }).bind(new OnObserver<Object>() {
            @RunContext(RunContextType.MainThread)
            @Override
            public void onSuccess(Object o) {
                isDecoded = true;
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onFinished() {

            }
        }).post();
    }

    @Override
    public void onWordClicked(WordInChapter word) {
        Context context = readViewRef.get();
        if (context != null)
            Toast.makeText(context,"点击了: "+word.getWord() + "等级" + word.getLevel(),Toast.LENGTH_LONG).show();
    }
}
