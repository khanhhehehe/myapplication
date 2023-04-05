package com.example.myapplication.Fragment.fragmentLoginChild;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragment_Login;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputLayout;

public class FragmentPassMoi extends Fragment implements OnClickListener {
    private ImageView btnBackToLogin;
    private TextInputLayout textPassword;
    private TextInputLayout textRePassword;
    private AppCompatButton btnResetPassword;
    private LinearLayout layoutContact;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pass_moi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        OntextChange(textPassword);
        OntextChange(textRePassword);
        btnResetPassword.setOnClickListener(this::onClick);
        btnBackToLogin.setOnClickListener(this::onClick);
    }

    private void anhXa(View view) {
        textPassword = view.findViewById(R.id.text_Password);
        btnBackToLogin = view.findViewById(R.id.btn_BackToLogin);
        textRePassword = view.findViewById(R.id.text_rePassword);
        btnResetPassword = view.findViewById(R.id.btn_ResetPassword);
        layoutContact = view.findViewById(R.id.layout_contact);

    }

    private void OntextChange(TextInputLayout textInputLayout) {
        //  Khai báo editText và gán giá trị bằng giá trị của textInputLayout
        EditText editText = textInputLayout.getEditText();
        //  chọn theo id của textInputLayout
        switch (textInputLayout.getId()) {
            // chọn nhập Mật khẩu
            case R.id.text_Password:
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().length() == 0) {
                            textInputLayout.setHelperText("Bắt buộc");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                        } else if (s.length() < 6) {
                            textInputLayout.setHelperText("Mật khẩu không được nhỏ hơn 6 kí tự");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                        } else {
                            textInputLayout.setHelperText("✔");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_700)));
                        }
                        if (textRePassword.getEditText().getText().toString().equals(s.toString())) {
                            textRePassword.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_700)));
                            textRePassword.setHelperText("✔");
                        } else {
                            textRePassword.setHelperText("Mật khẩu nhập lại không đúng*");
                            textRePassword.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                        }
                        btnResetPassword.setEnabled(CheckBtn());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                break;
            // chọn nhập lại Mật khẩu
            case R.id.text_rePassword:
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            textInputLayout.setHelperText("Bắt buộc*");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                        } else if (s.toString().equals(textPassword.getEditText().getText().toString())) {
                            textInputLayout.setHelperText("✔");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_700)));
                        } else {
                            textInputLayout.setHelperText("Mật khẩu xác nhận phải trùng với mật khẩu*");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                        }
                        btnResetPassword.setEnabled(CheckBtn());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
        }
    }
    private boolean CheckBtn() {
        if (textPassword.getHelperTextCurrentTextColor() == getResources().getColor(R.color.red)) {
            return false;
        }
        return textRePassword.getHelperTextCurrentTextColor() != getResources().getColor(R.color.red);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_BackToLogin:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Login()).commit();
                break;
            case R.id.btn_ResetPassword:
                FbDao.updatePass(fragment_Fogot_Password.idUser,textPassword.getEditText().getText().toString());
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_container, new fragment_respass_success()).commit();
                break;
        }
    }
}