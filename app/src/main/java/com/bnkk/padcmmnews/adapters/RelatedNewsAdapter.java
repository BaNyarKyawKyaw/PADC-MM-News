package com.bnkk.padcmmnews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bnkk.padcmmnews.R;
import com.bnkk.padcmmnews.viewholders.RelatedNewsViewHolder;

/**
 * Created by E5-575G on 11/16/2017.
 */

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsViewHolder> {

    private LayoutInflater mLayoutInfalter;

    public RelatedNewsAdapter(Context context) {
        mLayoutInfalter = LayoutInflater.from(context);
    }

    @Override
    public RelatedNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInfalter.inflate(R.layout.view_item_related_news, parent, false);
        return new RelatedNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RelatedNewsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
