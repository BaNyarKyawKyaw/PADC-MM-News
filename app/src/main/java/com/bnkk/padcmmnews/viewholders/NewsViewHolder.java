package com.bnkk.padcmmnews.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bnkk.padcmmnews.R;
import com.bnkk.padcmmnews.data.vo.NewsVO;
import com.bnkk.padcmmnews.delegates.NewsItemDelegate;
import com.bnkk.padcmmnews.events.TapNewsEvent;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by E5-575G on 11/4/2017.
 */

public class NewsViewHolder extends BaseViewHolder<NewsVO> {

    @BindView(R.id.iv_publication_logo)
    ImageView ivPublicationLogo;

    @BindView(R.id.tv_publication_name)
    TextView tvPublicationName;

    @BindView(R.id.tv_published_date)
    TextView tvPublishedDate;

    @BindView(R.id.tv_brief_news)
    TextView tvBriefNews;

    @BindView(R.id.iv_news_hero_image)
    ImageView ivNewsHeroImage;

    @BindView(R.id.tv_news_statistical_data)
    TextView tvNewsStaticalData;

    private NewsItemDelegate mNewsItemDelegate;

    public NewsViewHolder(View itemView, NewsItemDelegate newsItemDelegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mNewsItemDelegate = newsItemDelegate;
    }

    @Override
    public void setData(NewsVO data) {
        mData = data;

        if (data != null) {

            if (data.getPublication().getLogo() != null) {
                ivPublicationLogo.setVisibility(View.VISIBLE);
                Glide
                        .with(ivPublicationLogo.getContext())
                        .load(data.getPublication().getLogo())
                        .into(ivPublicationLogo);
            } else {
                ivPublicationLogo.setVisibility(View.GONE);
            }

            if (data.getPublication().getTitle() != null) {
                tvPublicationName.setVisibility(View.VISIBLE);
                tvPublicationName.setText(data.getPublication().getTitle());
            } else {
                tvPublicationName.setVisibility(View.GONE);
            }

            if (data.getPostedDate() != null) {
                tvPublishedDate.setVisibility(View.VISIBLE);
                tvPublishedDate.setText(data.getPostedDate());
            } else {
                tvPublishedDate.setVisibility(View.GONE);
            }

            if (data.getBrief() != null) {
                tvBriefNews.setVisibility(View.VISIBLE);
                tvBriefNews.setText(data.getBrief());
            } else {
                tvBriefNews.setVisibility(View.GONE);
            }

            if (!data.getImages().isEmpty()) {
                ivNewsHeroImage.setVisibility(View.VISIBLE);
                Glide
                        .with(ivNewsHeroImage.getContext())
                        .load(data.getImages().get(0))
                        .into(ivNewsHeroImage);
            } else {
                ivNewsHeroImage.setVisibility(View.GONE);
            }

            String newsStatistics = "";
            if (data.getFavouriteActions() != null) {
                newsStatistics += String.valueOf(data.getFavouriteActions().size()) + " likes - ";
            } else {
                newsStatistics += "0 likes - ";
            }
            if (data.getComments() != null) {
                newsStatistics += String.valueOf(data.getComments().size()) + " comments - ";
            } else {
                newsStatistics += "0 comments - ";
            }
            if (data.getSendTos() != null) {
                newsStatistics += "Send to " + String.valueOf(data.getSendTos().size()) + " people";
            } else {
                newsStatistics += "Send to 0 people";
            }
            tvNewsStaticalData.setText(newsStatistics);
        }
    }

    @Override
    public void onClick(View v) {
        mNewsItemDelegate.onTapNews(mData);
    }
}
