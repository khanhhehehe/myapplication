package com.example.myapplication;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Dialog.DialogLoading;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragDifferent.fragment_SplashScreen;
import com.example.myapplication.BroadcastReciver.ReciverCheckingInternet;

import com.example.myapplication.Dialog.*;

public class MainActivity extends AppCompatActivity {
    public static AlertDialog alertDialog;
    public static DialogLoading dialogLoading;
    // backPressed to Exit
    private long backPressedTime;
    private Toast mToast;
    ReciverCheckingInternet broadcastReceiver = new ReciverCheckingInternet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        set startus bar transparent color
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_SplashScreen()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DialogLoading dialogLoading = new DialogLoading(this);
        dialogLoading.Create();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, filter);
        new FbDao(this);
        DialogCountdown dialogCountdown = new DialogCountdown(this);
      dialogCountdown.Create();
    }

    @Override
    public void onBackPressed() {
        //         Toast de thoat
//        if (backPressedTime + 2000 > System.currentTimeMillis()) {
//            mToast.cancel();
        super.onBackPressed();
//            return;
//        } else {
//            mToast = Toast.makeText(MainActivity.this, R.string.backPressed, Toast.LENGTH_SHORT);
//            mToast.show();
//        }
//        backPressedTime = System.currentTimeMillis();


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}