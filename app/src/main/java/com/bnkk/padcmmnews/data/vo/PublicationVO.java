package com.bnkk.padcmmnews.data.vo;

import android.content.ContentValues;

import com.bnkk.padcmmnews.persistence.NewsContract;
import com.google.gson.annotations.SerializedName;

/**
 * Created by E5-575G on 12/3/2017.
 */

public class PublicationVO {

    @SerializedName("publication-id")
    private String publicationId;

    @SerializedName("title")
    private String title;

    @SerializedName("logo")
    private String logo;

    public String getPublicationId() {
        return publicationId;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
    }

    public ContentValues parseToContentValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put(NewsContract.PublicationsEntry.COLUMN_PUBLICATION_ID, publicationId);
        contentValues.put(NewsContract.PublicationsEntry.COLUMN_TITLE, title);
        contentValues.put(NewsContract.PublicationsEntry.COLUMN_LOGO, logo);

        return contentValues;
    }
}
