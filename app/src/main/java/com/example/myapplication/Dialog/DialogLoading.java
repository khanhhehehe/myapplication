package com.example.myapplication.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.R;

public class DialogLoading extends Dialog {
    Context context;
    public static DialogLoading dialogLoading;

    public DialogLoading(@NonNull Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_loading);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);

    }

    public void Create() {
        dialogLoading = new DialogLoading(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
