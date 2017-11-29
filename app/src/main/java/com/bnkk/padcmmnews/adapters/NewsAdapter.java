package com.bnkk.padcmmnews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bnkk.padcmmnews.R;
import com.bnkk.padcmmnews.delegates.NewsItemDelegate;
import com.bnkk.padcmmnews.viewholders.NewsViewHolder;

/**
 * Created by E5-575G on 11/4/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private LayoutInflater mLayoutInflater;
    private NewsItemDelegate mNewsItemDelegate;

    public NewsAdapter(Context context, NewsItemDelegate newsItemDelegate) {
        mLayoutInflater = LayoutInflater.from(context);
        mNewsItemDelegate = newsItemDelegate;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        *   We can also get the context by above way
        */
        View newsItemView = mLayoutInflater.inflate(R.layout.view_item_news, parent, false);
        return new NewsViewHolder(newsItemView, mNewsItemDelegate);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
