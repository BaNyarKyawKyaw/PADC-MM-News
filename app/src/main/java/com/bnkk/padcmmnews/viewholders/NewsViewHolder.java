package com.bnkk.padcmmnews.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bnkk.padcmmnews.delegates.NewsItemDelegate;

/**
 * Created by E5-575G on 11/4/2017.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private NewsItemDelegate mNewsItemDelegate;

    public NewsViewHolder(View itemView, NewsItemDelegate newsItemDelegate) {
        super(itemView);

        mNewsItemDelegate = newsItemDelegate;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewsItemDelegate.onTapNews();
            }
        });
    }
}
