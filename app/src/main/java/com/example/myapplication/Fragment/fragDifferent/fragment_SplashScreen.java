package com.example.myapplication.Fragment.fragDifferent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragment_Login;
import com.example.myapplication.Model.Hoadonchoigame;
import com.example.myapplication.R;
public class fragment_SplashScreen extends Fragment {
    private ImageView imageView;
    private TextView textView;
    public fragment_SplashScreen() {
        Hoadonchoigame hoadonchoigame = new Hoadonchoigame();
    }
    public static fragment_SplashScreen newInstance() {
        fragment_SplashScreen fragment = new fragment_SplashScreen();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }
    String TAG = "fragment_SplashScreen";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!FbDao.LoadedUser) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Login()).commit();
            }
        });
        thread.start();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    private void Anhxa(View v) {
        imageView = v.findViewById(R.id.buiqwiuiqubi);
        textView = v.findViewById(R.id.asnwqnjasnjqoe);
        imageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        textView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
    }
}