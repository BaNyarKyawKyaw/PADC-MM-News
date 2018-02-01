package com.bnkk.padcmmnews;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bnkk.padcmmnews.dagger.AppComponent;
import com.bnkk.padcmmnews.dagger.AppModule;
import com.bnkk.padcmmnews.dagger.DaggerAppComponent;
import com.bnkk.padcmmnews.dagger.NetworkModule;
import com.bnkk.padcmmnews.dagger.UtilsModule;
import com.bnkk.padcmmnews.data.models.NewsModel;
import com.bnkk.padcmmnews.utils.ConfigUtils;

import javax.inject.Inject;

/**
 * Created by E5-575G on 11/4/2017.
 */

public class MMNewsApp extends Application {

    public static final String LOG_TAG = "MMNewsApp";

    private AppComponent mAppComponent;

    @Inject
    Context mContext;

    @Inject
    NewsModel mNewsModel;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = initDagger();   // dagger int
        mAppComponent.inject(this); //register consumer (SFCNewsApp)

        mNewsModel.startLoadingNews(getApplicationContext());

        Log.d(LOG_TAG, "mContext :" + mContext);
    }

    private AppComponent initDagger() {
        //return null;
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule())
                .networkModule(new NetworkModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
