package com.example.myapplication.Fragment.fragmentLoginChild;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Fragment.fragment_Login;
import com.example.myapplication.R;


public class fragment_register_success extends Fragment {
    //  khai báo
    private ImageView btn_CancelToLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //khai báo view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_success, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //gọi hàm ánh xạ(truyền view để tìm id trong view đó)
        Anhxa(view);
        //bắt sự kiện khi click
        btn_CancelToLogin.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Login()).commit();
        });
    }

    //    khai báo hàm Anhxa
    private void Anhxa(View view) {
        btn_CancelToLogin = view.findViewById(R.id.btn_CancelToLogin);
    }


}