package com.example.myapplication.Fragment.fragDifferent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.myapplication.Dialog.DialogLoading;
import com.example.myapplication.R;

public class FragmentHelp extends Fragment {
    private Toolbar toolbarDanhMuc;
    private ImageView btnBackHelp;
    private WebView webViewHelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        backToUser();
        fillWebViewHelp();
    }

    private void anhXa(View view) {
        toolbarDanhMuc = view.findViewById(R.id.toolbar_DanhMuc);
        btnBackHelp = view.findViewById(R.id.btn_backHelp);
        webViewHelp = view.findViewById(R.id.webView_help);
    }
    public void fillWebViewHelp(){
        webViewHelp.getSettings().setSupportZoom(true);
        webViewHelp.getSettings().setJavaScriptEnabled(true);
        webViewHelp.setWebViewClient(new WebViewClient());
        webViewHelp.loadUrl("https://support.google.com/accounts?hl=vi#topic=3382296");
        webViewHelp.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                DialogLoading.dialogLoading.show();
                if(newProgress == 100){
                    DialogLoading.dialogLoading.dismiss();
                }
            }
        });
    }
    public void backToUser() {
        btnBackHelp.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }
}