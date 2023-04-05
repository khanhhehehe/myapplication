package com.example.myapplication.Fragment.fragmentLoginChild;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Dialog.DialogLoading;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragment_Login;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class fragment_Fogot_Password extends Fragment {
    //    khai báo
    private ImageView btn_BackToLogin;
    private AppCompatButton btn_ResetPassword;
    private EditText edUserName;
    private View viewFrag;
    private List<User> list;
    private boolean check = false;
    private FirebaseAuth mAuth;
    public static String idUser;
    private static final String TAG = "fragment_Fogot_Password";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fogot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
//        bắt sự kiện
        clickButon();
    }

    private void clickButon() {
        btn_BackToLogin.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Login()).commit();
        });
        btn_ResetPassword.setOnClickListener(view1 -> {
            if(edUserName.getText().toString().isEmpty()){
                Snackbar snackbar = Snackbar.make(viewFrag,"Bạn chưa nhập vào UserName",2000);
                View snackbar_view = snackbar.getView();
                TextView tv_bar = snackbar_view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv_bar.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.nervous,0);
                snackbar.show();
                return;
            }
            for(User user : list){
                if(user.getName().equalsIgnoreCase(edUserName.getText().toString())){
                    check = true;
                    idUser = user.getId();
                    sendverifyCode(user);
                    return;
                }
            }
            if(check == false){
                Snackbar snackbar = Snackbar.make(viewFrag,"UserName không tồn tại",2000);
                View snackbar_view = snackbar.getView();
                TextView tv_bar = snackbar_view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv_bar.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.nervous,0);
                snackbar.show();
                return;
            }
        });
    }

    private void sendverifyCode(User user) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(user.getPhonenumber())       // Phone number to verify
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
                        DialogLoading.dialogLoading.show();
                        fragment_verify_Phone fragment = new fragment_verify_Phone(user, verificationId, token);
                        fragment.resetPass = true;
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_container, fragment).commit();
                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void anhXa(View view) {
        //        ánh xạ
        mAuth = FirebaseAuth.getInstance();
        list = FbDao.listUser;
        viewFrag = view;
        btn_BackToLogin = view.findViewById(R.id.btn_BackToLogin);
        btn_ResetPassword = view.findViewById(R.id.btn_ResetPassword);
        edUserName = view.findViewById(R.id.ed_userName);
    }
}
