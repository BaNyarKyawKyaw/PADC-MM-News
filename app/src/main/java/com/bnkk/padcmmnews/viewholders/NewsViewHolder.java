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
        if (data != null) {
            Glide
                    .with(ivPublicationLogo.getContext())
                    .load(data.getPublication().getLogo())
                    .into(ivPublicationLogo);
            tvPublicationName.setText(data.getPublication().getTitle());

            tvPublishedDate.setText(data.getPostedDate());

            tvBriefNews.setText(data.getBrief());
            Glide
                    .with(ivNewsHeroImage.getContext())
                    .load(data.getImages())
                    .into(ivNewsHeroImage);

//            tvNewsStaticalData.setText(Integer.toString(data.getFavouriteActions().size())
//                    + " likes - " + Integer.toString(data.getComments().size()) + " comments - Sent to "
//                    + Integer.toString(data.getSendTos().size()) + " people");
        }
    }

    @Override
    public void onClick(View v) {
        //mNewsItemDelegate.onTapNews();

        EventBus.getDefault().post(new TapNewsEvent("News"));
    }
}
