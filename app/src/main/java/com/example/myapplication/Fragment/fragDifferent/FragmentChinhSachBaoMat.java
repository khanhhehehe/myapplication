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

public class FragmentChinhSachBaoMat extends Fragment {
    private Toolbar toolbarDanhMuc;
    private ImageView btnBackBaoMat;
    private WebView webViewBaoMat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chinh_sach_bao_mat, container, false);
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
        btnBackBaoMat = view.findViewById(R.id.btn_backBaoMat);
        webViewBaoMat = view.findViewById(R.id.webView_baoMat);

    }
    public void fillWebViewHelp(){
        webViewBaoMat.getSettings().setSupportZoom(true);
        webViewBaoMat.getSettings().setJavaScriptEnabled(true);
        webViewBaoMat.setWebViewClient(new WebViewClient());
        webViewBaoMat.loadUrl("https://policies.google.com/privacy?hl=vi");
        webViewBaoMat.setWebChromeClient(new WebChromeClient(){
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
        btnBackBaoMat.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }
}