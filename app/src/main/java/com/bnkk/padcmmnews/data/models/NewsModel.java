package com.bnkk.padcmmnews.data.models;


import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bnkk.padcmmnews.MMNewsApp;
import com.bnkk.padcmmnews.data.vo.CommentsVO;
import com.bnkk.padcmmnews.data.vo.FavouriteActionVO;
import com.bnkk.padcmmnews.data.vo.NewsVO;
import com.bnkk.padcmmnews.data.vo.PublicationVO;
import com.bnkk.padcmmnews.data.vo.SendToVO;
import com.bnkk.padcmmnews.events.RestApiEvents;
import com.bnkk.padcmmnews.network.MMNewDataAgent;
import com.bnkk.padcmmnews.persistence.NewsContract;
import com.bnkk.padcmmnews.utils.AppConstants;
import com.bnkk.padcmmnews.utils.ConfigUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by E5-575G on 12/3/2017.
 */

public class NewsModel {

    private List<NewsVO> mNews;

    private static final String MM_News = "mm_news";

    @Inject
    MMNewDataAgent mDataAgent;

    @Inject
    ConfigUtils mConfigUtils;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public NewsModel(Context context) {
        EventBus.getDefault().register(this);
        mNews = new ArrayList<>();

        MMNewsApp sfcNewsApp = (MMNewsApp) context.getApplicationContext();
        sfcNewsApp.getAppComponent().inject(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    public void startLoadingNews(final Context context) {
        /*
        mDataAgent.loadMMNews(AppConstants.ACCESS_TOKEN,
                mConfigUtils.loadPageIndex(),
                context);
                */

        DatabaseReference mmNewsDBR = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mmNewsNodeDBR = mmNewsDBR.child(MM_News);
        mmNewsNodeDBR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<NewsVO> newsList = new ArrayList<>();
                for (DataSnapshot newsDSS : dataSnapshot.getChildren()) {
                    NewsVO news = newsDSS.getValue(NewsVO.class);
                    newsList.add(news);
                }

                Log.d(MMNewsApp.LOG_TAG, "newsList size : " + newsList.size());

                saveNewsData(context, newsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<NewsVO> getNews() {
        return mNews;
    }

    public void loadMoreNews(Context context) {
        /*
        mDataAgent.loadMMNews(AppConstants.ACCESS_TOKEN,
                mConfigUtils.loadPageIndex(),
                context);
                */
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadedNews());
        mConfigUtils.savePageIndex(event.getLoadedPageIndex() + 1);

        saveNewsData(event.getContext(), event.getLoadedNews());
    }

    public void forceRefreshNews(Context context) {
        mNews = new ArrayList<>();
        mConfigUtils.savePageIndex(1);
        startLoadingNews(context);
    }

    public void authenticateUserWithGoogleAccount(final GoogleSignInAccount signInAccount, final UserAuthenticateDelegate delegate) {
        Log.d(MMNewsApp.LOG_TAG, "signInAccount Id :" + signInAccount.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(MMNewsApp.LOG_TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(MMNewsApp.LOG_TAG, "signInWithCredential", task.getException());
                            delegate.onFailureAuthenticate(task.getException().getMessage());
                        } else {
                            Log.d(MMNewsApp.LOG_TAG, "signInWithCredential - successful");
                            mFirebaseUser = mFirebaseAuth.getCurrentUser();
                            delegate.onSuccessAuthenticate(signInAccount);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(MMNewsApp.LOG_TAG, "OnFailureListener : " + e.getMessage());
                        delegate.onFailureAuthenticate(e.getMessage());
                    }
                });
    }

    private void saveNewsData(Context context, List<NewsVO> newsList) {

        // Logic to save the data in Persistence Layer
        ContentValues[] newsCVs = new ContentValues[newsList.size()];
        List<ContentValues> publicationCVList = new ArrayList<>();
        List<ContentValues> imagesInNewsCVList = new ArrayList<>();

        List<ContentValues> favouriteInNewsCVList = new ArrayList<>();
        List<ContentValues> userInActionCVList = new ArrayList<>();
        List<ContentValues> commentsInNewsCVList = new ArrayList<>();
        List<ContentValues> sendToInNewsCVList = new ArrayList<>();

        for (int index = 0; index < newsCVs.length; index++) {
            //newsCVs[index] = event.getLoadedNews().get(index).parseToContentValues();
            NewsVO news = newsList.get(index);
            newsCVs[index] = news.parseToContentValues();

            PublicationVO publication = news.getPublication();
            if (publication != null) {
                publicationCVList.add(publication.parseToContentValues());
            }
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

                ContentValues senderCV = sendTo.getSender().parseToContentValues();
                userInActionCVList.add(senderCV);

                ContentValues receiverCV = sendTo.getReceiver().parseToContentValues();
                userInActionCVList.add(receiverCV);
            }
        }

        int insertedPublications = context.getContentResolver().bulkInsert(NewsContract.PublicationsEntry.CONTENT_URI,
                publicationCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "Inserted Publication" + insertedPublications);

        int insertedImages = context.getContentResolver().bulkInsert(NewsContract.ImagesEntry.CONTENT_URI,
                imagesInNewsCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "insertedImages" + insertedImages);

        int insertedFavoriteInAction = context.getContentResolver().bulkInsert(NewsContract.FavouriteActionsEntry.CONTENT_URI,
                favouriteInNewsCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "insertedFavoriteInAction" + insertedFavoriteInAction);

        int insertedCommentsInNews = context.getContentResolver().bulkInsert(NewsContract.CommentActionsEntry.CONTENT_URI,
                commentsInNewsCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "insertedCommentsInNews" + insertedCommentsInNews);

        int insertedSendToInNews = context.getContentResolver().bulkInsert(NewsContract.SendToEntry.CONTENT_URI,
                sendToInNewsCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "insertedSendToInNews" + insertedSendToInNews);

        int insertedUserInAction = context.getContentResolver().bulkInsert(NewsContract.ActedUserEntry.CONTENT_URI,
                userInActionCVList.toArray(new ContentValues[0]));
        Log.d(MMNewsApp.LOG_TAG, "insertedUserInAction" + insertedUserInAction);

        int insertedNews = context.getContentResolver().bulkInsert(NewsContract.NewsEntry.CONTENT_URI,
                newsCVs);
        Log.d(MMNewsApp.LOG_TAG, "Inserted Row" + insertedNews);
    }

    public void uploadFile(String fileToUpload, final UploadFileCallback uploadFileCallback) {
        Uri file = Uri.parse(fileToUpload);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference pathToUpload = storage.getReferenceFromUrl(AppConstants.URL_USER_NEWS_IMAGE);

        StorageReference uploadingFile = pathToUpload.child(file.getLastPathSegment());
        UploadTask uploadTask = uploadingFile.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadFileCallback.onUploadFailed(e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uploadedImageUrl = taskSnapshot.getDownloadUrl();
                Log.d(MMNewsApp.LOG_TAG, "Uploaded Image Url : " + uploadedImageUrl);

                uploadFileCallback.onUploadSucceeded(uploadedImageUrl.toString());
            }
        });
    }

    public boolean isUserAuthentiacte() {
        return mFirebaseUser != null;
    }

    public void publishNews(final String photoPath, final String newsContent) {
        uploadFile(photoPath, new UploadFileCallback() {
            @Override
            public void onUploadSucceeded(String uploadedPaths) {
                List<String> images = new ArrayList<>();
                images.add(uploadedPaths);
                NewsVO newsToPublish = new NewsVO(newsContent, newsContent, images, new Date().toString());

                newsToPublish.setPublication(PublicationVO.dummyPublication());

                DatabaseReference mmNewsDBR = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mmNewsNodeDBR = mmNewsDBR.child(MM_News);
                mmNewsNodeDBR.child(newsToPublish.getPostedDate()).setValue(newsToPublish);
            }

            @Override
            public void onUploadFailed(String msg) {

            }
        });
    }

    public interface UserAuthenticateDelegate {
        void onSuccessAuthenticate(GoogleSignInAccount signInAccount);

        void onFailureAuthenticate(String errorMsg);
    }

    public interface UploadFileCallback {
        void onUploadSucceeded(String uploadedPaths);

        void onUploadFailed(String msg);
    }
}
