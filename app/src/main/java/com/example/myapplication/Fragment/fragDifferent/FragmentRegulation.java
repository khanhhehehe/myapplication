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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.myapplication.Dialog.DialogLoading;
import com.example.myapplication.R;
public class FragmentRegulation extends Fragment {
    private Toolbar toolbarDanhMuc;
    private ImageView btnBackRegulation;
    private WebView webViewRegulation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regulation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        fillWebViewHelp();
        backToUser();
    }

    private void anhXa(View view) {
        toolbarDanhMuc = view.findViewById(R.id.toolbar_DanhMuc);
        btnBackRegulation = view.findViewById(R.id.btn_backRegulation);
        webViewRegulation = view.findViewById(R.id.webView_Regulation);
    }
    public void fillWebViewHelp(){
        webViewRegulation.getSettings().setSupportZoom(true);
        webViewRegulation.getSettings().setJavaScriptEnabled(true);
        webViewRegulation.setWebViewClient(new WebViewClient());
        webViewRegulation.loadUrl("https://drive.google.com/file/d/1jaQv7vqUj1S0NZ2MFFk26MkxbXPBEL9R/preview");
        webViewRegulation.setWebChromeClient(new WebChromeClient(){
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
        btnBackRegulation.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }
}