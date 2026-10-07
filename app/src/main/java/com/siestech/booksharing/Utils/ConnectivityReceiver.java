package com.siestech.booksharing.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.siestech.booksharing.MainActivity;

public class ConnectivityReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm == null ? null : cm.getActiveNetworkInfo();
        if (context instanceof MainActivity) ((MainActivity) context).setOnline(ni != null && ni.isConnected());
    }
}
