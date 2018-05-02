package com.bnkk.padcmmnews.dagger;

import com.bnkk.padcmmnews.MMNewsApp;
import com.bnkk.padcmmnews.activities.AddNewsActivity;
import com.bnkk.padcmmnews.activities.NewsListActivity;
import com.bnkk.padcmmnews.data.models.NewsModel;
import com.bnkk.padcmmnews.mvp.presenters.AddNewsPresenter;
import com.bnkk.padcmmnews.mvp.presenters.NewsListPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by E5-575G on 2/1/2018.
 */

@Component(modules = {AppModule.class, UtilsModule.class, NetworkModule.class})
@Singleton
public interface AppComponent {

    void inject(MMNewsApp app); // means SFCNewsApp is a consumer

    void inject(NewsModel newsModel);

    void inject(NewsListPresenter newsListPresenter);

    void inject(NewsListActivity newsListActivity);

    void inject(AddNewsPresenter addNewsPresenter);

    void inject(AddNewsActivity addNewsActivity);
}
