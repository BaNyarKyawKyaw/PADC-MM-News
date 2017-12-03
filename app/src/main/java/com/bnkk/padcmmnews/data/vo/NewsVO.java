package com.bnkk.padcmmnews.data.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by E5-575G on 12/2/2017.
 */

public class NewsVO {

    @SerializedName("news-id")
    private String newsId;

    @SerializedName("brief")
    private String brief;

    @SerializedName("details")
    private String details;

    @SerializedName("images")
    private List<String> images;

    @SerializedName("posted-date")
    private String postedDate;

    @SerializedName("publication")
    private PublicationVO publication;

    @SerializedName("favorites")
    private List<FavouriteActionVO> favouriteActions;

    @SerializedName("comments")
    private List<CommentsVO> comments;

    @SerializedName("send-tos")
    private List<SendToVO> sendTos;

    public String getNewsId() {
        return newsId;
    }

    public String getBrief() {
        return brief;
    }

    public String getDetails() {
        return details;
    }

    public List<String> getImages() {
        return images;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public PublicationVO getPublication() {
        return publication;
    }

    public List<FavouriteActionVO> getFavouriteActions() {
        return favouriteActions;
    }

    public List<CommentsVO> getComments() {
        return comments;
    }

    public List<SendToVO> getSendTos() {
        return sendTos;
    }
}
