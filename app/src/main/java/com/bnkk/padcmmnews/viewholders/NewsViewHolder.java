package com.bnkk.padcmmnews.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bnkk.padcmmnews.data.vo.NewsVO;
import com.bnkk.padcmmnews.delegates.NewsItemDelegate;
import com.bnkk.padcmmnews.events.TapNewsEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by E5-575G on 11/4/2017.
 */

public class NewsViewHolder extends BaseViewHolder<NewsVO> {

    private NewsItemDelegate mNewsItemDelegate;

    public NewsViewHolder(View itemView, NewsItemDelegate newsItemDelegate) {
        super(itemView);

        mNewsItemDelegate = newsItemDelegate;
    }

    @Override
    public void setData(NewsVO data) {

    }

    @Override
    public void onClick(View v) {
        //mNewsItemDelegate.onTapNews();

        EventBus.getDefault().post(new TapNewsEvent("News"));
    }
}
