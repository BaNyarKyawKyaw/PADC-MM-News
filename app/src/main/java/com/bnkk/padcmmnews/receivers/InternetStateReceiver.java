package com.bnkk.padcmmnews.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by E5-575G on 11/25/2017.
 */

public class InternetStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo.isConnected()) {
            Toast.makeText(context, "Your Device is connected to Internet", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Your Device is not connected to internet", Toast.LENGTH_LONG).show();
        }
    }
}
