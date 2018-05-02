package com.bnkk.padcmmnews.mvp.views;

import android.content.Context;

/**
 * Created by E5-575G on 2/2/2018.
 */

public interface AddNewsView {

    Context getContext();

    void showUploadedNewsPhoto(String photoPath);

    void showErrorMsg(String msg);
}
