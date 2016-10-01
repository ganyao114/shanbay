package com.gy.shanbay.Presenter.Factory;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.gy.shanbay.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/9/29.
 */
public class WordsFactory {

    private Map<String,Integer> WordsMap = new ConcurrentHashMap<>();

    private Map<String,Integer> WordGroupsMap = new ConcurrentHashMap<>();

    public Map<String, Integer> getWordsMap() {
        return WordsMap;
    }

    public Map<String, Integer> getWordGroupsMap() {
        return WordGroupsMap;
    }

    public void decode(Context context) {
        Resources resources = context.getResources();
        InputStreamReader is = null;
        is = new InputStreamReader(resources.openRawResource(R.raw.nce4_words));
        BufferedReader bufReader = new BufferedReader(is);

        String line = "";
        int linecount = 0;

        try {
            while((line = bufReader.readLine()) != null){
                linecount++;
                if (linecount <= 1)
                    continue;
                String[] s = line.split("\\s+");
                Integer level = Integer.parseInt(s[s.length - 1]);
                String word = null;
                if (s.length > 2){
                    word = line.split("\\s+"+s[s.length - 1])[0];
                    WordGroupsMap.put(word,level);
                }else {
                    word = s[0];
                    WordsMap.put(word,level);
                }
            }
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
        Log.e("gy",WordsMap.size()+"");
        Log.e("gy",WordGroupsMap.size()+"");
    }
}
