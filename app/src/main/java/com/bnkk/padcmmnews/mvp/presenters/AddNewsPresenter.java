package com.bnkk.padcmmnews.mvp.presenters;

import com.bnkk.padcmmnews.MMNewsApp;
import com.bnkk.padcmmnews.data.models.NewsModel;
import com.bnkk.padcmmnews.mvp.views.AddNewsView;

import javax.inject.Inject;

/**
 * Created by E5-575G on 2/2/2018.
 */

public class AddNewsPresenter extends BasePresenter<AddNewsView> {

    @Inject
    NewsModel mNewsModel;

    @Override
    public void onCreate(AddNewsView view) {
        super.onCreate(view);
        MMNewsApp sfcNewsApp = (MMNewsApp) mView.getContext();
        sfcNewsApp.getAppComponent().inject(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void onTapPublish(final String photoPath, String newsContent) {
        mNewsModel.publishNews(photoPath, newsContent);
        /*
        mNewsModel.uploadFile(photoPath, new NewsModel.UploadFileCallback() {
            @Override
            public void onUploadSucceeded(String uploadedPaths) {
                mView.showUploadedNewsPhoto(uploadedPaths);
            }

            @Override
            public void onUploadFailed(String msg) {

            }
        });
        */
    }
}
