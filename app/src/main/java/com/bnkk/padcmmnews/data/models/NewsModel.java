package com.bnkk.padcmmnews.data.models;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.bnkk.padcmmnews.MMNewsApp;
import com.bnkk.padcmmnews.data.vo.CommentsVO;
import com.bnkk.padcmmnews.data.vo.FavouriteActionVO;
import com.bnkk.padcmmnews.data.vo.NewsVO;
import com.bnkk.padcmmnews.data.vo.PublicationVO;
import com.bnkk.padcmmnews.data.vo.SendToVO;
import com.bnkk.padcmmnews.events.RestApiEvents;
import com.bnkk.padcmmnews.network.MMNewsDataAgentImpl;
import com.bnkk.padcmmnews.persistence.NewsContract;
import com.bnkk.padcmmnews.utils.AppConstants;
import com.bnkk.padcmmnews.utils.ConfigUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E5-575G on 12/3/2017.
 */

public class NewsModel {

    private static NewsModel objInstance;

    private List<NewsVO> mNews;

    private NewsModel() {
        EventBus.getDefault().register(this);
        mNews = new ArrayList<>();
    }

    public static NewsModel getObjInstance() {
        if (objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    public void startLoadingNews(Context context) {
        MMNewsDataAgentImpl.getObjInstance().loadMMNews(AppConstants.ACCESS_TOKEN,
                ConfigUtils.getObjInstance().loadPageIndex(), context);
    }

    public List<NewsVO> getNews() {
        return mNews;
    }

    public void loadMoreNews(Context context) {
        int pageIndex = ConfigUtils.getObjInstance().loadPageIndex();
        MMNewsDataAgentImpl.getObjInstance().loadMMNews(AppConstants.ACCESS_TOKEN,
                pageIndex, context);
    }

    public void forceRefreshNews(Context context) {
        mNews = new ArrayList<>();
        ConfigUtils.getObjInstance().savePageIndex(1);
        startLoadingNews(context);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadedNews());
        ConfigUtils.getObjInstance().savePageIndex(event.getLoadedPageIndex() + 1);

        // Logic to save the data in Persistence Layer
        ContentValues[] newsCVs = new ContentValues[event.getLoadedNews().size()];
        List<ContentValues> publicationCVList = new ArrayList<>();
        List<ContentValues> imagesInNewsCVList = new ArrayList<>();
        List<ContentValues> favouriteInNewsCVList = new ArrayList<>();
        List<ContentValues> userInActionCVList = new ArrayList<>();
        List<ContentValues> commentsInNewsCVList = new ArrayList<>();
        List<ContentValues> sendToInNewsCVList = new ArrayList<>();

        for (int index = 0; index < newsCVs.length; index++) {
            //newsCVs[index] = event.getLoadedNews().get(index).parseToContentValues();
            NewsVO news = event.getLoadedNews().get(index);
            newsCVs[index] = news.parseToContentValues();

            PublicationVO publication = news.getPublication();
            publicationCVList.add(publication.parseToContentValues());

            for (String imageUrl : news.getImages()) {
                ContentValues imagesInNewsCV = new ContentValues();
                imagesInNewsCV.put(NewsContract.ImagesEntry.COLUMN_NEWS_ID, news.getNewsId());
                imagesInNewsCV.put(NewsContract.ImagesEntry.COLUMN_IMAGE_URL, imageUrl);
                imagesInNewsCVList.add(imagesInNewsCV);
            }

            for (FavouriteActionVO favouriteAction : news.getFavouriteActions()) {
                ContentValues favoriteActionsCV = favouriteAction.parseToContentValues(news.getNewsId());
                favouriteInNewsCVList.add(favoriteActionsCV);

                ContentValues userInActionCV = favouriteAction.getActedUser().parseToContentValues();
                userInActionCVList.add(userInActionCV);
            }

            for (CommentsVO commentAction : news.getComments()) {
                ContentValues commentsCV = commentAction.parseToContentValues(news.getNewsId());
                commentsInNewsCVList.add(commentsCV);

                ContentValues useInActionCV = commentAction.getActedUser().parseToContentValues();
                userInActionCVList.add(useInActionCV);
            }

            for (SendToVO sendTo : news.getSendTos()) {
                ContentValues commentsCV = sendTo.parseToContentValues(news.getNewsId());
                sendToInNewsCVList.add(commentsCV);

                ContentValues sender = sendTo.getSender().parseToContentValues();
                userInActionCVList.add(sender);

                ContentValues receiverCV = sendTo.getReceiver().parseToContentValues();
                userInActionCVList.add(receiverCV);
            }
        }

        int insertedPublications = event.getContext().getContentResolver().bulkInsert(NewsContract.PublicationsEntry.CONTENT_URI,
                publicationCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "Inserted Publication" + insertedPublications);

        int insertedImages = event.getContext().getContentResolver().bulkInsert(NewsContract.ImagesEntry.CONTENT_URI,
                imagesInNewsCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "insertedImages" + insertedImages);

        int insertedFavoriteInAction = event.getContext().getContentResolver().bulkInsert(NewsContract.FavouriteActionsEntry.CONTENT_URI,
                favouriteInNewsCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "insertedFavoriteInAction" + insertedFavoriteInAction);

        int insertedUserInAction = event.getContext().getContentResolver().bulkInsert(NewsContract.ActedUserEntry.CONTENT_URI,
                userInActionCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "insertedUserInAction" + insertedUserInAction);

        int insertedCommentsInNews = event.getContext().getContentResolver().bulkInsert(NewsContract.CommentActionsEntry.CONTENT_URI,
                commentsInNewsCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "insertedCommentsInNews" + insertedCommentsInNews);

        int insertedSendToInNews = event.getContext().getContentResolver().bulkInsert(NewsContract.SendToEntry.CONTENT_URI,
                sendToInNewsCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "insertedSendToInNews" + insertedSendToInNews);

        int insertedNews = event.getContext().getContentResolver().bulkInsert(NewsContract.NewsEntry.CONTENT_URI,
                newsCVs);
        Log.d(MMNewsApp.LOG_TAG, "Inserted Row" + insertedNews);

    }
}
