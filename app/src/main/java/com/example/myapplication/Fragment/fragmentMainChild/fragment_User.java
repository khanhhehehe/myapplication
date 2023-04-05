package com.example.myapplication.Fragment.fragmentMainChild;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Dialog.DialogLoading;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragDifferent.FragmentChinhSachBaoMat;
import com.example.myapplication.Fragment.fragDifferent.FragmentHelp;
import com.example.myapplication.Fragment.fragDifferent.FragmentLichSuGiaoDich;
import com.example.myapplication.Fragment.fragDifferent.FragmentNapTien;
import com.example.myapplication.Fragment.fragDifferent.FragmentRegulation;
import com.example.myapplication.Fragment.fragDifferent.FragmentThongBao;
import com.example.myapplication.Fragment.fragmentUserChild.fragment_EditProfile;
import com.example.myapplication.Fragment.fragment_Login;
import com.example.myapplication.R;
import com.example.myapplication.Fragment.fragmentUserChild.*;

import java.util.ArrayList;

public class fragment_User extends Fragment implements View.OnClickListener {
    private TextView tv_Username, tv_UserPhoneNumbers;
    private RelativeLayout layout_Username;
    private LinearLayout btn_Notify, btn_CheckHistory, btn_Help, btn_Regulation, btn_PolicyAndPrivacy, btn_LogOut, btn_napTien;
    private ImageView avaterUserUserFrag;
    private final String TAG = "fragment_User";
    private LinearLayout btnMyBookingHis;


    public fragment_User() {

    }

    // khai baos constructor
    public static fragment_User newInstance() {
        fragment_User fragment = new fragment_User();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (DialogLoading.dialogLoading.isShowing()) {
            DialogLoading.dialogLoading.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        SetdataForView();
        Onclick();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    private void SetdataForView() {
        tv_Username.setText(FbDao.UserLogin.getName());
        String s = FbDao.UserLogin.getPhonenumber();
        tv_UserPhoneNumbers.setText(s);
        if (FbDao.UserLogin.getAvatar() != null) {
            avaterUserUserFrag.setImageBitmap(FbDao.UserLogin.getAvatar());

        }
    }

    private void Onclick() {
        btn_LogOut.setOnClickListener(this::onClick);
        layout_Username.setOnClickListener(this::onClick);
        btn_napTien.setOnClickListener(this::onClick);
        btn_Notify.setOnClickListener(this::onClick);
        btn_CheckHistory.setOnClickListener(this::onClick);
        btn_Help.setOnClickListener(this::onClick);
        btn_PolicyAndPrivacy.setOnClickListener(this::onClick);
        btn_Regulation.setOnClickListener(this::onClick);
        btnMyBookingHis.setOnClickListener(this::onClick);
    }

    private void Anhxa(View view) {
        tv_Username = view.findViewById(R.id.tv_UserfragName);
        tv_UserPhoneNumbers = view.findViewById(R.id.tv_UserfragPhoneNumber);
        btn_Notify = view.findViewById(R.id.btn_Notify);
        btn_CheckHistory = view.findViewById(R.id.btn_CheckHistory);
        btn_Help = view.findViewById(R.id.btn_Help);
        btn_Regulation = view.findViewById(R.id.btn_Regulation);
        btn_PolicyAndPrivacy = view.findViewById(R.id.btn_PolicyAndPrivacy);
        btn_LogOut = view.findViewById(R.id.btn_LogOut);
        layout_Username = view.findViewById(R.id.layout_Username);
        avaterUserUserFrag = view.findViewById(R.id.avaterUserUserFrag);
        btn_napTien = view.findViewById(R.id.btn_nap_tien);
        btnMyBookingHis = view.findViewById(R.id.btn_MyBookingHis);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_LogOut:
                Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_logout);
                TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
                TextView tv_logout = dialog.findViewById(R.id.tv_logout);
                tv_cancel.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
                tv_logout.setOnClickListener(v1 -> {
                    SharedPreferences s = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = s.edit();
                    editor.clear();
                    editor.commit();
                    FbDao.Login = false;
                    FragmentLichSuGiaoDich.hoadonListAgain = new ArrayList<>();
//                    fragment_Uudai.listGame2 = new ArrayList<>();
                    fragment_Uudai.voucherList2 = new ArrayList<>();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Login()).commit();
                    dialog.dismiss();
                });
                dialog.show();
                break;
            case R.id.layout_Username:
                Replace(new fragment_EditProfile());
                break;
            case R.id.btn_nap_tien:
                Replace(new FragmentNapTien());
                break;
            case R.id.btn_Notify:
                Replace(new FragmentThongBao());
                break;
            case R.id.btn_CheckHistory:

                Replace(new FragmentLichSuGiaoDich());
                break;
            case R.id.btn_Help:
                Replace(new FragmentHelp());
                break;
            case R.id.btn_PolicyAndPrivacy:
                Replace(new FragmentChinhSachBaoMat());
                break;
            case R.id.btn_Regulation:
                Replace(new FragmentRegulation());
                break;
            case R.id.btn_MyBookingHis:
                Replace(new fragment_MybookingHistory());
                break;

        }
    }


    private void Replace(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("")
                .replace(R.id.fragment_container, fragment).commit();
    }
}