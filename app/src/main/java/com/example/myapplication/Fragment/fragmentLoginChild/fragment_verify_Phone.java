package com.example.myapplication.Fragment.fragmentLoginChild;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class fragment_verify_Phone extends Fragment implements View.OnClickListener {
    //  khai báo
    private ImageView btn_BackToRegister;
    private EditText edCode1, edCode2, edCode3, edCode4, edCode5, edCode6;
    private TextView tv_SendVerifyAgain;
    private AppCompatButton btn_CheckOTP;
    private String mPhonenumber;
    private final String TAG = "fragment_verify_Phone";
    // khai báo firebase
    private final FirebaseAuth mAuth;
    // khai báo biến username & password giá trị rỗng
    private String Username = "", Password = "", verificationId = "";
    // ko biet
    private PhoneAuthCredential phoneAuthCredential;
    private final PhoneAuthProvider.ForceResendingToken token;
    private int second = 60;
    Thread runReloadtv;
    User user;
    public boolean isUpdate = false;
    public boolean resetPass = false;
    // khởi tạo constructor (truyền tham số)
    public fragment_verify_Phone(User user, String verificationId, PhoneAuthProvider.ForceResendingToken token) {
        this.mPhonenumber = mPhonenumber;
        mAuth = FirebaseAuth.getInstance();
        this.Username = user.getName();
        this.Password = user.getPassword();
        this.verificationId = verificationId;
        this.token = token;
        this.user = user;
        if(DialogLoading.dialogLoading.isShowing()){
            DialogLoading.dialogLoading.dismiss();
        }
        Handler handler = new Handler();
        runReloadtv = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                while (second >= 0) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_SendVerifyAgain.setText(second + "");
                            tv_SendVerifyAgain.setEnabled(false);
                        }
                    }, 0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (second == 0) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tv_SendVerifyAgain.setText("Gửi lại");
                                tv_SendVerifyAgain.setEnabled(true);

                            }
                        }, 0);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    second--;
                }

            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //  khai báo view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_verify_phone_numbers, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //gọi hàm ánh xạ(truyền view để tìm id trong view đó)
        Anhxa(view);
        setupEditext();
        runReloadtv.start();

    }


    //  ko biết
    public static fragment_verify_Phone newInstance() {
        fragment_verify_Phone fragment = new fragment_verify_Phone(null, null, null);

        return fragment;
    }

    // khai báo hàm String getCodefromEdittext
    private String getCodefromEdittext() {
        String code = "";
        code += edCode1.getText().toString();
        code += edCode2.getText().toString();
        code += edCode3.getText().toString();
        code += edCode4.getText().toString();
        code += edCode5.getText().toString();
        code += edCode6.getText().toString();
        return code;
    }

    // khai báo hàm Anhxa
    private void Anhxa(View view) {
        edCode1 = view.findViewById(R.id.edCode1);
        edCode2 = view.findViewById(R.id.edCode2);
        edCode3 = view.findViewById(R.id.edCode3);
        edCode4 = view.findViewById(R.id.edCode4);
        edCode5 = view.findViewById(R.id.edCode5);
        edCode6 = view.findViewById(R.id.edCode6);

        btn_BackToRegister = view.findViewById(R.id.btn_BackToRegister);
        tv_SendVerifyAgain = view.findViewById(R.id.tv_SendVerifyAgain);
        btn_CheckOTP = view.findViewById(R.id.btn_CheckOTP);

        btn_CheckOTP.setOnClickListener(this::onClick);
        tv_SendVerifyAgain.setOnClickListener(this::onClick);
        btn_BackToRegister.setOnClickListener(this::onClick);

    }

    //    khai báo hàm sendverifyCode phạm vi truy cập trong Class này
    private void sendverifyCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setForceResendingToken(token)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {

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
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                super.onCodeSent(verificationId, token);
                                Log.d(TAG, "onCodeSent:" + verificationId);
                                fragment_verify_Phone.this.verificationId = verificationId;
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    // khai báo hàm setupEditext
    private void setupEditext() {
        edCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    edCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    edCode3.requestFocus();
                } else {
                    edCode1.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    edCode4.requestFocus();
                } else {
                    edCode2.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    edCode5.requestFocus();
                } else {
                    edCode3.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    edCode6.requestFocus();
                } else {
                    edCode4.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {

                } else {
                    edCode5.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    // khai báo hàm signInWithPhoneAuthCredential
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        if (isUpdate) {
            mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Log.d(TAG, "update:success");
                        FbDao fbDao = new FbDao(getActivity());
                        fbDao.UpdateUser(user);
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else {

                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                        }
                    }
                }
            });
            return;
        }
        if (resetPass) {
            mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "reset:success");
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_container, new FragmentPassMoi()).commit();
                    } else {

                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                        }
                    }
                }
            });
            return;
        }
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Log.d(TAG, "signInWithCredential:success");
                    FbDao fbDao = new FbDao(getActivity());
                    fbDao.AddUser(user);
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_container, new fragment_register_success()).commit();

                } else {

                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                    }
                }
            }
        });
        
    }

    /// hàm bắt sự kiện nút bấm trong fragment Regesiter
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_BackToRegister:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.tv_SendVerifyAgain:


                sendverifyCode(mPhonenumber);
                runReloadtv.stop();
                runReloadtv.start();
                break;
            case R.id.btn_CheckOTP:
                phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, getCodefromEdittext());
                signInWithPhoneAuthCredential(phoneAuthCredential);
                break;

        }

    }
}