package com.gy.shanbay.View.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gy.shanbay.Model.Entity.Lesson;
import com.gy.shanbay.Presenter.Base.MainPresenter;
import com.gy.shanbay.R;
import com.gy.shanbay.View.Adapter.IndexAdapter;

import net.gy.SwiftFrameWork.Core.S;
import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.annotation.ContentView;
import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.annotation.OnClick;
import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.annotation.ViewInject;
import net.gy.SwiftFrameWork.Presenter.Presenter;

@ContentView(R.layout.activity_read)
public class ReadActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, ExpandableListView.OnChildClickListener {

    @ViewInject(R.id.content_txt)
    public TextView content;
    @ViewInject(R.id.lesson_title)
    private TextView title;
    private SeekBar seekBar;
    private MaterialDialog.Builder indexDiaBuilder;
    private MaterialDialog.Builder wordsDiaBuilder;
    private MaterialDialog indexDia;
    private MaterialDialog wordsDia;
    private ExpandableListView index;
    private IndexAdapter adapter;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        S.ViewUtils.Inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intView();
    }

    private void intView() {
        index = new ExpandableListView(this);
        index.setGroupIndicator(null);
        indexDiaBuilder = new MaterialDialog.Builder(this);
        indexDiaBuilder.title("目录");
        indexDiaBuilder.backgroundColor(Color.WHITE);
        indexDiaBuilder.titleColor(Color.DKGRAY);
        indexDiaBuilder.customView(index,false);
        indexDiaBuilder.autoDismiss(true);
        seekBar = new SeekBar(this);
        seekBar.setMax(5);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(this);
        wordsDiaBuilder = new MaterialDialog.Builder(this);
        wordsDiaBuilder.title("单词高亮");
        wordsDiaBuilder.backgroundColor(Color.WHITE);
        wordsDiaBuilder.titleColor(Color.DKGRAY);
        wordsDiaBuilder.customView(seekBar,false);
        index.setOnChildClickListener(this);
    }


    @OnClick(R.id.btn_index)
    public void showindex(View view){
        boolean isDecoded = Presenter.find(MainPresenter.class).isDecoded;
        if (isDecoded){
            if (adapter == null) {
                adapter = new IndexAdapter(this,Presenter.find(MainPresenter.class).articleFactory.getArticle());
                index.setAdapter(adapter);
            }
            if (indexDia == null) {
                indexDia = indexDiaBuilder.show();
            }else {
                indexDia.show();
            }
        }else {
            Toast.makeText(this,"解析中....",Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btn_words)
    public void showseekbar(View view){
        if (wordsDia == null) {
            wordsDia = wordsDiaBuilder.show();
        }else {
            wordsDia.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        S.ViewUtils.Remove(this);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.progress = progress;
        if (Presenter.find(MainPresenter.class).showingLesson == null)
            return;
        Presenter.find(MainPresenter.class).showWords(null,progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Lesson lesson = Presenter.find(MainPresenter.class).articleFactory.getArticle().getUnits().get(groupPosition).getLessons().get(childPosition);
        title.setText("Unit"+lesson.getUid()+"Lesson"+lesson.getLid());
        Presenter.find(MainPresenter.class).showWords(lesson,progress);
        indexDia.dismiss();
        return true;
    }
}
