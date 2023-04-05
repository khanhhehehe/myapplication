package com.example.myapplication.Fragment.fragmentLoginChild;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Dialog.DialogLoading;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragment_Login;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class fragment_Regesiter extends Fragment implements View.OnClickListener {
    //  khai báo
    private ImageView btn_BackToLogin;
    private TextView tv_Conditions;
    private TextInputLayout text_Username;
    private TextInputLayout text_Phonenumber;
    private TextInputLayout text_Password;
    private TextInputLayout text_rePassword;
    private CheckBox chk_CheckConditions;
    private LinearLayout layout_Conditions,layout_contact;
    private AppCompatButton btn_Register;
    // khai báo firebase
    private FirebaseAuth mAuth;
    private final List<User> list;
    private final String TAG = "fragment_Regesiter";

    public fragment_Regesiter(List<User> list) {
        this.list = list;
    }

    //  khai báo view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regesiter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //gọi hàm ánh xạ(truyền view để tìm id trong view đó)
        Anhxa(view);
        // khởi tạo biến mAuth Firebase
        mAuth = FbDao.mAuth;
        // gọi hàm animation (truyền vào các tham số)
        Animation(text_Username, text_Phonenumber, text_Password, text_rePassword, layout_Conditions, btn_Register);
        // gọi hàm OntextChange(truyền vào TextInputLayout)
        OntextChange(text_Username);
        OntextChange(text_Password);
        OntextChange(text_rePassword);
        OntextChange(text_Phonenumber);
        // bắt sự kiện khi click
        tv_Conditions.setOnClickListener(this::onClick);
        btn_BackToLogin.setOnClickListener(this::onClick);
        btn_Register.setOnClickListener(this::onClick);
        chk_CheckConditions.setOnClickListener(this::onClick);


    }

    // khai báo hàm animation
    private void Animation(TextInputLayout text_Username, TextInputLayout text_Phonenumber, TextInputLayout text_Password, TextInputLayout text_rePassword, LinearLayout layout_Conditions, AppCompatButton btn_Register) {
        text_Username.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        text_Phonenumber.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        text_Password.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        text_rePassword.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        layout_Conditions.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        layout_Conditions.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        btn_Register.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        layout_contact.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.fadein));

    }

    // khai báo hàm Anhxa
    private void Anhxa(View view) {
        btn_BackToLogin = view.findViewById(R.id.btn_BackToLogin);
        layout_Conditions = view.findViewById(R.id.layout_Conditions);
        text_Username = view.findViewById(R.id.text_Username);
        text_Phonenumber = view.findViewById(R.id.text_Phonenumber);
        text_Password = view.findViewById(R.id.text_Password);
        text_rePassword = view.findViewById(R.id.text_rePassword);
        chk_CheckConditions = view.findViewById(R.id.chk_CheckConditions);
        tv_Conditions = view.findViewById(R.id.tv_Conditions);
        btn_Register = view.findViewById(R.id.btn_Register);
        layout_contact = view.findViewById(R.id.layout_contact);


    }

    //    khai báo hàm OntextChange phạm vi truy cập trong Class này
    @SuppressLint("NonConstantResourceId")
    private void OntextChange(TextInputLayout textInputLayout) {
        //  Khai báo editText và gán giá trị bằng giá trị của textInputLayout
        EditText editText = textInputLayout.getEditText();
        //  chọn theo id của textInputLayout
        switch (textInputLayout.getId()) {
            // chọn nhập Username
            case R.id.text_Username:
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            textInputLayout.setHelperText("Bắt buộc*");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                        } else {
                            textInputLayout.setHelperText("✔");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_700)));
                        }

                        for (User u : list
                        ) {
                            if (u.getName().equals(s.toString())) {
                                textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                                textInputLayout.setHelperText("Tên đã tồn tại*");

                            }
                            break;
                        }
                        btn_Register.setEnabled(CheckBtn());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                btn_Register.setEnabled(CheckBtn());
                break;
            // chọn nhập Số điện thoại

            case R.id.text_Phonenumber:
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {

                            textInputLayout.setHelperText("Bắt buộc*");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                        } else {
                            textInputLayout.setHelperText("✔");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_700)));
                        }
                        btn_Register.setEnabled(CheckBtn());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                btn_Register.setEnabled(CheckBtn());
                break;

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
                        if (text_rePassword.getEditText().getText().toString().equals(s.toString())) {
                            text_rePassword.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_700)));
                            text_rePassword.setHelperText("✔");
                        } else {
                            text_rePassword.setHelperText("Mật khẩu nhập lại không đúng*");
                            text_rePassword.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                        }
                        btn_Register.setEnabled(CheckBtn());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                btn_Register.setEnabled(CheckBtn());
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
                        } else if (s.toString().equals(text_Password.getEditText().getText().toString())) {
                            textInputLayout.setHelperText("✔");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_700)));
                        } else {
                            textInputLayout.setHelperText("Mật khẩu xác nhận phải trùng với mật khẩu*");
                            textInputLayout.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                        }
                        btn_Register.setEnabled(CheckBtn());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
        }
    }

    //ko biét
    private boolean CheckBtn() {
        Log.d(TAG, "CheckBtn: ");
        if (text_Phonenumber.getHelperTextCurrentTextColor() == getResources().getColor(R.color.red)) {
            return false;
        }
        if (text_rePassword.getHelperTextCurrentTextColor() == getResources().getColor(R.color.red)) {
            return false;
        }
        if (text_Password.getHelperTextCurrentTextColor() == getResources().getColor(R.color.red)) {
            return false;
        }
        if (text_Username.getHelperTextCurrentTextColor() == getResources().getColor(R.color.red)) {
            return false;
        }

        return chk_CheckConditions.isChecked();
    }

    // hàm bắt sự kiện nút bấm trong fragment Regesiter
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_BackToLogin:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.tv_Conditions:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View viewDialog = inflater.inflate(R.layout.dialog_conditions, null);
                //        builder view
                builder.setView(viewDialog);
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            case R.id.btn_Register:
                sendverifyCode("+84" + text_Phonenumber.getEditText().getText().toString());

                break;
            case R.id.chk_CheckConditions:
                btn_Register.setEnabled(CheckBtn());
                break;
        }
    }

    //    hàm đăng nhập với số điện thoại fireBase
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, String Username, String Password) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_container, new fragment_Login(Username, Password)).commit();

                } else {

                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                    }
                }
            }
        });

    }

    //    hàm gửi mã OTP về số điện thoại
    private void sendverifyCode(String phoneNumber) {
        String Username = text_Username.getEditText().getText().toString();
        String Password = text_Password.getEditText().getText().toString();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(getActivity())                 // Activity (for callback binding)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        Log.d(TAG, "onVerificationCompleted:" + credential);


                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Log.w(TAG, "onVerificationFailed", e);
                        Toast.makeText(getActivity(), "Gửi mã xác minh thất bại! Hãy liên hệ với quản trị viên để được giúp đỡ", Toast.LENGTH_SHORT).show();
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {

                        } else if (e instanceof FirebaseTooManyRequestsException) {

                        }


                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        super.onCodeSent(verificationId, token);
                        Log.d(TAG, "onCodeSent:" + verificationId);
                        User user = new User(text_Username.getEditText().getText().toString(), Password, phoneNumber, 0);
                        DialogLoading.dialogLoading.show();
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_container, new fragment_verify_Phone(user, verificationId, token)).commit();
                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }
}
