package com.bnkk.padcmmnews.dagger;

import android.content.Context;

import com.bnkk.padcmmnews.MMNewsApp;
import com.bnkk.padcmmnews.data.models.NewsModel;
import com.bnkk.padcmmnews.mvp.presenters.NewsListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by E5-575G on 2/1/2018.
 */

@Module
public class AppModule {

    private MMNewsApp mApp;

    public AppModule(MMNewsApp application) {
        mApp = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    @Singleton
    public NewsModel provideNewsModel(Context context) {
        return new NewsModel(context);
    }

    @Provides
    public NewsListPresenter provideNewsListPresenter() {
        return new NewsListPresenter();
    }
}
