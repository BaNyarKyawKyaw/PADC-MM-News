package com.bnkk.padcmmnews.data.models;


import com.bnkk.padcmmnews.data.vo.NewsVO;
import com.bnkk.padcmmnews.events.RestApiEvents;
import com.bnkk.padcmmnews.network.MMNewsDataAgentImpl;
import com.bnkk.padcmmnews.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    public void StartLoadingNews() {
        MMNewsDataAgentImpl.getObjInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmPageIndex);
    }

    @Subscribe
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadedNews());
        mmPageIndex = event.getLoadedPageIndex() + 1;
    }
}
