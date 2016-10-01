package com.gy.shanbay.Presenter.Application;

import android.app.Application;

import com.gy.shanbay.Presenter.Base.MainPresenter;

import net.gy.SwiftFrameWork.Core.S;
import net.gy.SwiftFrameWork.IOC.Core.cache.ClassType;
import net.gy.SwiftFrameWork.IOC.Core.cache.ReflectCacheControl;
import net.gy.SwiftFrameWork.IOC.Core.impl.IOC;
import net.gy.SwiftFrameWork.IOC.Core.parase.AnnotationFactory;
import net.gy.SwiftFrameWork.Presenter.ActivityLifeManager;
import net.gy.SwiftFrameWork.Presenter.Presenter;

/**
 * Created by gy939 on 2016/10/1.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        S.init(this);
        ReflectCacheControl.getInstance().AddpreLoad(ClassType.ACTIVITY, AnnotationFactory.getAllActivity(this));
        ReflectCacheControl.getInstance().preLoad(ClassType.ACTIVITY);
        ActivityLifeManager.getInstance().regist(this);
        ActivityLifeManager.getInstance().preLoad(new Class[]{MainPresenter.class});
        Presenter.establish();
        Presenter.With(null).start(MainPresenter.class);
    }
}
