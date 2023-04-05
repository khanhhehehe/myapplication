package com.example.myapplication.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.R;

public class DialogCountdown extends Dialog {

    TextView tv_minutes;
    TextView tv_seconds;

    private final Context context;

    public DialogCountdown(@NonNull Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_timeup);
        tv_minutes = findViewById(R.id.phut);
        tv_seconds = findViewById(R.id.giay);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }
    
    private static Thread t;

    public void Create() {


    }

    private int phut = 0;
    private int giay = 0;

    public void setTimeout(long milisecond) {

        if (milisecond <= 0) {
            return;
        }
        this.show();

        giay = (int) (milisecond / 1000);

        while (giay >= 60) {

            phut = giay / 60;
            giay = giay % 60;
        }


        t = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!(phut == 0 && giay == 0)) {

                    String textPhut = (phut<10)?("0"+phut):(phut+"");
                    String textGiay = (giay<10)?("0"+giay):(giay+"");
                    
                        tv_minutes.setText(textPhut);
                        tv_seconds.setText(textGiay);
                    if (giay == 0 && phut > 0) {
                        giay = 59;
                        phut = phut - 1;
                    }
                    if (!isShowing()) {
                        break;
                    }

                    try {
                        Thread.sleep(999);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    giay--;
                }
                if (isShowing()) {
                    dismiss();

                }
            }
        });
        t.start();


    }


}
