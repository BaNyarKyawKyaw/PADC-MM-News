package com.bnkk.padcmmnews.data.models;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.bnkk.padcmmnews.MMNewsApp;
import com.bnkk.padcmmnews.data.vo.NewsVO;
import com.bnkk.padcmmnews.events.RestApiEvents;
import com.bnkk.padcmmnews.network.MMNewsDataAgentImpl;
import com.bnkk.padcmmnews.persistence.NewsContract;
import com.bnkk.padcmmnews.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E5-575G on 12/3/2017.
 */

public class NewsModel {

    private static NewsModel objInstance;

    private List<NewsVO> mNews;
    private int mmPageIndex = 1;

    private NewsModel() {
        EventBus.getDefault().register(this);
        mNews = new ArrayList<>();
    }

    public static NewsModel getObjInstance() {
        if (objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    public void startLoadingNews(Context context) {
        MMNewsDataAgentImpl.getObjInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmPageIndex, context);
    }

    public List<NewsVO> getNews() {
        return mNews;
    }

    public void loadMoreNews(Context context) {
        MMNewsDataAgentImpl.getObjInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmPageIndex, context);
    }

    @Subscribe
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadedNews());
        mmPageIndex = event.getLoadedPageIndex() + 1;

        //TODO Logic to save the data in Persistence Layer
        ContentValues[] newsCVs = new ContentValues[event.getLoadedNews().size()];
        for (int index = 0; index < newsCVs.length; index++) {
            newsCVs[index] = event.getLoadedNews().get(index).parseToContentValues();
        }

        int insertedRows = event.getContext().getContentResolver().bulkInsert(NewsContract.NewsEntry.CONTENT_URI,
                newsCVs);

        Log.d(MMNewsApp.LOG_TAG, "Inserted Row " + insertedRows);
    }

    public void forceRefreshNews(Context context) {
        mNews = new ArrayList<>();
        mmPageIndex = 1;
        startLoadingNews(context);
    }
}
