package com.bnkk.padcmmnews.mvp.views;

import android.content.Context;

import com.bnkk.padcmmnews.data.vo.NewsVO;

import java.util.List;

/**
 * Created by E5-575G on 2/1/2018.
 */

public interface NewsListView {

    void displayNewsList(List<NewsVO> newsList);

    void showLoading();

    void navigateToNewsDetails(NewsVO news);

    Context getContext();

    void showAddNewsScreen();

    void signInGoogle();
}
