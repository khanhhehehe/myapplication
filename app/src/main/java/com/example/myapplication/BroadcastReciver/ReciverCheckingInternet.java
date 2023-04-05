package com.example.myapplication.BroadcastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class ReciverCheckingInternet extends BroadcastReceiver implements DialogInterface.OnClickListener {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean status = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = true;
            } else
                status = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        if (MainActivity.alertDialog == null) {

            MainActivity.alertDialog = builder.create();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_lostconecttion, null);
            MainActivity.alertDialog.setView(view);

        }
        MainActivity.alertDialog.setCancelable(false);

        if (status) {
            MainActivity.alertDialog.cancel();

        } else {
            MainActivity.alertDialog.show();

        }


    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
