package com.bnkk.padcmmnews.data.vo;

import android.content.ContentValues;
import android.database.Cursor;

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

    public static PublicationVO parseFromCursor(Cursor cursor) {

        PublicationVO publicationVO = new PublicationVO();

        publicationVO.publicationId = cursor.getString(cursor.getColumnIndex(NewsContract.PublicationsEntry.COLUMN_PUBLICATION_ID));
        publicationVO.title = cursor.getString(cursor.getColumnIndex(NewsContract.PublicationsEntry.COLUMN_TITLE));
        publicationVO.logo = cursor.getString(cursor.getColumnIndex(NewsContract.PublicationsEntry.COLUMN_LOGO));

        return publicationVO;
    }

    public static PublicationVO dummyPublication() {
        PublicationVO publicationVO = new PublicationVO();
        publicationVO.publicationId = "pub719";
        publicationVO.title = "BBC Burmese";
        publicationVO.logo = "http://www.bbc.co.uk/news/special/2015/newsspec_11063/burmese_1024x576.png";
        return publicationVO;
    }
}
