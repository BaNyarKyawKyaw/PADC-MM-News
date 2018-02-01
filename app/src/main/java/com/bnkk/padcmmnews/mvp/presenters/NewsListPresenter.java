package com.bnkk.padcmmnews.mvp.presenters;

import android.content.Context;
import android.database.Cursor;

import com.bnkk.padcmmnews.MMNewsApp;
import com.bnkk.padcmmnews.data.models.NewsModel;
import com.bnkk.padcmmnews.data.vo.NewsVO;
import com.bnkk.padcmmnews.delegates.NewsItemDelegate;
import com.bnkk.padcmmnews.mvp.views.NewsListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by E5-575G on 2/1/2018.
 */

public class NewsListPresenter extends BasePresenter<NewsListView> implements NewsItemDelegate {

    @Inject
    NewsModel mNewsModel;

    public NewsListPresenter() {

    }

    @Override
    public void onCreate(NewsListView view) {
        super.onCreate(view);
        MMNewsApp sfcNewsApp = (MMNewsApp) mView.getContext();
        sfcNewsApp.getAppComponent().inject(this);
    }

    @Override
    public void onStart() {
        List<NewsVO> newsList = mNewsModel.getNews();
        if (!newsList.isEmpty()) {
            mView.displayNewsList(newsList);
        } else {
            mView.showLoading();
        }
    }

    @Override
    public void onStop() {

    }

    public void onNewsListEndReach(Context context) {
        mNewsModel.loadMoreNews(context);
    }

    public void onForceRefresh(Context context) {
        mNewsModel.forceRefreshNews(context);
    }

    public void onDataLoaded(Context context, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<NewsVO> newsList = new ArrayList<>();

            do {
                NewsVO news = NewsVO.parseFromCursor(context, data);
                newsList.add(news);
            } while (data.moveToNext());

            mView.displayNewsList(newsList);
        }
    }

    @Override
    public void onTapComment() {

    }

    @Override
    public void onTapSendTo() {

    }

    @Override
    public void onTapFavourite() {

    }

    @Override
    public void onTapStatistics() {

    }

    @Override
    public void onTapNews(NewsVO news) {
        mView.navigateToNewsDetails(news);
    }
}
