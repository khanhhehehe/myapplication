package com.example.myapplication.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.Fragment.fragmentMainChild.fragment_Uudai;

public class UpdateService extends IntentService {
    String TAG = "UpdateService";

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG, "loaded: ");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("UpdateService"));
        this.stopSelf();
    }
}
