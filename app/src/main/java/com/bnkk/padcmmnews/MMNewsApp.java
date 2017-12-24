package com.bnkk.padcmmnews;

import android.app.Application;

import com.bnkk.padcmmnews.data.models.NewsModel;
import com.bnkk.padcmmnews.utils.ConfigUtils;

/**
 * Created by E5-575G on 11/4/2017.
 */

public class MMNewsApp extends Application {

    public static final String LOG_TAG = "MMNewsApp";

    @Override
    public void onCreate() {
        super.onCreate();
        ConfigUtils.initConfigUtils(getApplicationContext());
        NewsModel.getObjInstance().startLoadingNews(getApplicationContext());
    }
}
