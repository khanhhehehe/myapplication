package com.example.myapplication.Fragment.fragmentUserChild;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Dialog.DialogLoading;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragmentLoginChild.fragment_verify_Phone;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


public class fragment_EditProfile extends Fragment implements View.OnClickListener {
    private AppCompatButton btn_SaveProfile;
    private LinearLayout btn_ChangeAvatar;
    private EditText ed_UpdateFullName;
    private EditText ed_UpdatePhoneNumbers;
    private ImageView btnBackToUser;
    private ImageView imageView_editProfile;
    private final static int REQUEST_CODE = 123; // tạo hằng xác định chỉ số
    private final String TAG = "fragment_EditProfile";
    private FirebaseAuth mAuth;

    private Bitmap imgChose = null;
    private boolean imgdif = false;


    public fragment_EditProfile() {

    }

    private final Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!FbDao.UpLoadedAvatar) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            FbDao.LoadAvatarFromID();

            while (!FbDao.LoadedAvatar) {

            }
            FbDao.ReadHistory();
            while ((FbDao.hoadonList.size() == 0)) {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DialogLoading.dialogLoading.dismiss();
            FbDao.UpLoadedAvatar = false;
            FbDao.LoadedAvatar = false;
            getActivity().getSupportFragmentManager().popBackStack();
        }
    });

    public static fragment_EditProfile newInstance(String param1, String param2) {
        fragment_EditProfile fragment = new fragment_EditProfile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment__edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        Onclick();
        btn_SaveProfile.setEnabled(false);

        setFocuschangeEdittext();
        setDataForView();
    }


    private void setFocuschangeEdittext() {
        User u = FbDao.UserLogin.Clone();
        ed_UpdateFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!s.toString().equals(u.getName()) || !ed_UpdatePhoneNumbers.getText().toString().equals(u.getPhonenumber())) {
                    btn_SaveProfile.setEnabled(!((s.length() == 0) || (ed_UpdatePhoneNumbers.getText().toString().length() == 0)) || imgdif);


                } else {
                    btn_SaveProfile.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ed_UpdatePhoneNumbers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!s.toString().equals(u.getPhonenumber()) || !ed_UpdateFullName.getText().toString().equals(u.getName())) {
                    btn_SaveProfile.setEnabled(!((s.length() == 0) || (ed_UpdateFullName.getText().toString().length() == 0)) || imgdif);
                } else {
                    btn_SaveProfile.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void Onclick() {
        btn_ChangeAvatar.setOnClickListener(this::onClick);
        btnBackToUser.setOnClickListener(this::onClick);
        btn_SaveProfile.setOnClickListener(this::onClick);
    }

    private void setDataForView() {
        if (FbDao.UserLogin.getAvatar() != null) {
            imageView_editProfile.setImageBitmap(FbDao.UserLogin.getAvatar());

        }
        String numberPhone = FbDao.UserLogin.getPhonenumber();
        ed_UpdatePhoneNumbers.setText(numberPhone);
        ed_UpdateFullName.setText(FbDao.UserLogin.getName());
    }

    private void Anhxa(View v) {
        imageView_editProfile = v.findViewById(R.id.img_editProfile);
        btnBackToUser = v.findViewById(R.id.btnBackToUser);

        btn_SaveProfile = v.findViewById(R.id.btn_SaveProfile);
        btn_ChangeAvatar = v.findViewById(R.id.btn_ChangeAvatar);
        ed_UpdateFullName = v.findViewById(R.id.ed_UpdateFullName);
        ed_UpdatePhoneNumbers = v.findViewById(R.id.ed_UpdatePhoneNumbers);
    }

    //hàm lấy ảnh từ thiết bị
    private void LayAnh() {
        //cấp quyền từ người dùng
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);
            //cho phép sử dụng
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);//truy cập vào bộ nhớ của máy
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE);
        }
    }


    //lấy ảnh về
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                imgChose = BitmapFactory.decodeStream(inputStream); // lấy ảnh từ bộ nhớ
                imageView_editProfile.setImageBitmap(imgChose);
                imgdif = !imgChose.sameAs(FbDao.UserLogin.getAvatar());
                btn_SaveProfile.setEnabled(imgdif);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackToUser:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.btn_ChangeAvatar:
                LayAnh();
                break;
            case R.id.btn_SaveProfile:


                FbDao dao = new FbDao();
                if (imgdif) {
                    dao.UpLoadavatar(imageView_editProfile);
                    t1.start();
                    DialogLoading.dialogLoading.show();
                }

                User u = FbDao.UserLogin.Clone();
                String phoneNumber = ed_UpdatePhoneNumbers.getText().toString();
                u.setName(ed_UpdateFullName.getText().toString());
                if (FbDao.UserLogin.getPhonenumber().equals(phoneNumber)) {
                    dao.UpdateUser(u);
                    if (!imgdif) {
                        DialogLoading.dialogLoading.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        }, 1000);

                    }
                } else {
                    u.setPhonenumber(phoneNumber);
                    sendverifyCode(u);
                }

                break;

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
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
                        fragment_verify_Phone fragment = new fragment_verify_Phone(user, verificationId, token);
                        fragment.isUpdate = true;
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_container, fragment).commit();
                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}