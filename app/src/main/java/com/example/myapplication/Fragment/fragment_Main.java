package com.example.myapplication.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragmentMainChild.fragment_Trangchu;
import com.example.myapplication.Fragment.fragmentMainChild.fragment_User;
import com.example.myapplication.Fragment.fragmentMainChild.fragment_Uudai;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
public class fragment_Main extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    //khai báo khi vào màn hình chính sẽ tự động select Trang Chủ
    private int item_selected = R.id.nav_Home;
    //khai báo BottomNavigation
    private BottomNavigationView bottom_Nav;
    //khai báo view
    String TAG = "fragment_Main";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        switch (item_selected) {
            //trang chủ
            case R.id.nav_Home:
                replaceFragment(new fragment_Trangchu());
                break;
            //cá nhân
            case R.id.nav_User:
                replaceFragment(new fragment_User());
                break;
            //ưu đãi
            case R.id.nav_Endow:
                replaceFragment(new fragment_Uudai());
                break;
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }
    //chọn item trong bottomNavigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //nếu item chọn trùng với item đã chọn thì không thay đổi
        if (item.getItemId() == item_selected) {
            return true;
        }
        switch (item.getItemId()) {
            //trang chủ
            case R.id.nav_Home:
                replaceFragment(new fragment_Trangchu());
                break;
            //cá nhân
            case R.id.nav_User:
                replaceFragment(new fragment_User());
                break;
            //ưu đãi
            case R.id.nav_Endow:
                replaceFragment(new fragment_Uudai());
                break;
        }
        item_selected = item.getItemId();
        return true;
    }
    //    khai báo hàm Anhxa
    private void Anhxa(View v) {
        bottom_Nav = v.findViewById(R.id.bottomNav);
        bottom_Nav.getMenu().findItem(R.id.nav_Home).setChecked(true);
        bottom_Nav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        replaceFragment(new fragment_Trangchu());
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAG", "onPause: ");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAG", "onStart: ");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //khai báo constructor rỗng
    public fragment_Main() {
    }
    //ko biết
    public static fragment_Main newInstance() {
        fragment_Main fragment = new fragment_Main();
        return fragment;
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment).commit();
    }
}
