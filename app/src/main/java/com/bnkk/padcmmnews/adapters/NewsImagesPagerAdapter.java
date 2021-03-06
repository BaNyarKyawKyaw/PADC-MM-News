package com.bnkk.padcmmnews.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bnkk.padcmmnews.R;
import com.bnkk.padcmmnews.viewitems.NewsDetailsImageViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E5-575G on 11/11/2017.
 */

public class NewsImagesPagerAdapter extends PagerAdapter {

    private LayoutInflater mLayoutInflater;
    private List<String> mImages;

    public NewsImagesPagerAdapter(Context context) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        mImages = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        /*
        if(view==(View)object)
            return true;
            Above can be written as
        */
        return (view == (View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        NewsDetailsImageViewItem itemView = (NewsDetailsImageViewItem) mLayoutInflater.inflate(R.layout.view_item_news_details_image, container, false);
        itemView.setData(mImages.get(position));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setImages(List<String> images) {
        mImages = images;
        notifyDataSetChanged();
    }
}
