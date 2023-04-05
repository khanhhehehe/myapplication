package com.example.myapplication.Fragment.fragmentMainChild;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.DanhSachGameAdapter;
import com.example.myapplication.Adapter.SliderAdapter;
import com.example.myapplication.Dialog.DialogCountdown;
import com.example.myapplication.Dialog.DialogLoading;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragListgameAndVoudcher.Fragment_ListDanhSachTroChoi;
import com.example.myapplication.Fragment.fragDifferent.fragment_QRcode;
import com.example.myapplication.Fragment.fragmentTypeGame.fragmentHenTroChoiGio;
import com.example.myapplication.Fragment.fragmentTypeGame.fragmentHenTroChoiLuot;
import com.example.myapplication.Fragment.fragmentTypeGame.fragmentTroChoiGio;
import com.example.myapplication.Fragment.fragmentTypeGame.fragmentTroChoiLuot;
import com.example.myapplication.Interface.OnclickItemGame;
import com.example.myapplication.Model.Game;
import com.example.myapplication.Model.Hoadon;
import com.example.myapplication.Model.Hoadonchoigame;
import com.example.myapplication.Model.User;
import com.example.myapplication.Model.Voucher;
import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.SliderView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class fragment_Trangchu extends Fragment implements View.OnClickListener {
    private static final int REQUETCODE = 100;
    //  khai báo
    private SliderView image_Slider;
    private LinearLayout layout_troChoi, layout_thanhToan, layout_soDu;
    private Toolbar toolbar;
    private ImageView avaterUserHomeFrag;
    private TextView fragHomeTvUsername;
    private TextView fragHomeTvSodu;
    private ImageView hideshowSoduHomefrag;
    private LinearLayout goTofragQr;
    List<Game> listGame = new ArrayList<>();
    private Dialog dialog;
    private RecyclerView recyclerView_game;
    private ImageView close_dialog;
    private List<Game> listDanhSachGame;
    private DanhSachGameAdapter danhSachGameAdapter;
    private View viewFrag = null;

    private static final String TAG = "FRAGMENT_TRANG_CHU";
    public static boolean gochild = false;
    private boolean show = true;
    private final boolean chk = Fragment_ListDanhSachTroChoi.chk;

    //khai báo view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);


        return inflater.inflate(R.layout.fragment_trangchu, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

    private View viewContainer;

    public fragment_Trangchu() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //lấy hóa đơn từ firebase


        //gọi hàm ánh xạ(truyền view để tìm id trong view đó)
        if (gochild) {
            replaceFragment(new Fragment_ListDanhSachTroChoi());
            gochild = !gochild;
        }
        Anhxa(view);
        // gọi hàm animation (truyền vào các tham số)
        animation(image_Slider, layout_troChoi, layout_thanhToan, layout_soDu);

        // khai báo mảng ảnh và gán giá trị src ảnh
        int[] img = new int[]{R.drawable.banner_main, R.drawable.banner_main_2, R.drawable.banner_main_3};

        List<User> list = FbDao.getList();
        // khai báo SliderAdapter và gán giá trị bằng img
        SliderAdapter adapter = new SliderAdapter(img);

        List<Voucher> listVoucher = FbDao.getListVoucher();
        // set lên slideAdapter
        image_Slider.setSliderAdapter(adapter);
        //    toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        SetDataForView();
        if (DialogLoading.dialogLoading.isShowing()) {
            DialogLoading.dialogLoading.dismiss();
        }
        onClickLayout();
        viewFrag = view;

    }

    private void SetDataForView() {
        User u = FbDao.UserLogin.Clone();
        String pattern = "###,###,###,###,###,### Poin";
        DecimalFormat df = new DecimalFormat(pattern);
        fragHomeTvSodu.setText(df.format(u.getSodu()));
        fragHomeTvUsername.setText(u.getName());
        if (FbDao.UserLogin.getAvatar() != null) {
            avaterUserHomeFrag.setImageBitmap(FbDao.UserLogin.getAvatar());

        }
    }

    // toolbar
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
////        inflater.inflate(R.menu.toolbar_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
////        switch (item.getItemId()) {
////
////            case R.id.toolbar_search:
////                Toast.makeText(getActivity(), "Toát", Toast.LENGTH_LONG).show();
////                break;
////        }
//        return super.onOptionsItemSelected(item);
//    }

    //    khai báo hàm Anhxa
    private void Anhxa(View view) {
        image_Slider = view.findViewById(R.id.image_Slider);
        layout_troChoi = view.findViewById(R.id.layout_troChoi);
        layout_thanhToan = view.findViewById(R.id.layout_thanhToan);
        layout_soDu = view.findViewById(R.id.layout_soDu);
        toolbar = view.findViewById(R.id.toolbar);
        avaterUserHomeFrag = view.findViewById(R.id.avaterUserHomeFrag);
        fragHomeTvUsername = view.findViewById(R.id.fragHome_tvUsername);
        fragHomeTvSodu = view.findViewById(R.id.fragHome_tvSodu);
        hideshowSoduHomefrag = view.findViewById(R.id.hideshowSoduHomefrag);
        goTofragQr = view.findViewById(R.id.goTofragQr);

    }

    // khai báo hàm animation
    private void animation(SliderView image_Slider, LinearLayout layout_troChoi, LinearLayout layout_thanhToan, LinearLayout layout_soDu) {
        image_Slider.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.lefttoright));
        layout_soDu.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.lefttoright));
        layout_thanhToan.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
        layout_troChoi.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ShowListGame();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //khai báo constructor rỗng
    //ko biết
    public static fragment_Trangchu newInstance() {
        fragment_Trangchu fragment = new fragment_Trangchu();
        return fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_troChoi:
                replaceFragment(new Fragment_ListDanhSachTroChoi());
                break;
            case R.id.hideshowSoduHomefrag:
                if (show) {
                    fragHomeTvSodu.setText("****** Poin");
                    hideshowSoduHomefrag.setImageResource(R.drawable.ic_baseline_remove_red_eye_24px);
                } else {
                    User u = FbDao.UserLogin;
                    String pattern = "###,###,###,###,###,### Poin";
                    DecimalFormat df = new DecimalFormat(pattern);
                    fragHomeTvSodu.setText(df.format(u.getSodu()));

                    hideshowSoduHomefrag.setImageResource(R.drawable.ic_baseline_visibility_off_24px);
                }
                show = !show;
                break;
            case R.id.goTofragQr:
                phanQuyen();
                break;
            case R.id.layout_soDu:
                dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_hen_trochoi);
                recyclerView_game = dialog.findViewById(R.id.recyclerview_voucher_gio);
                close_dialog = dialog.findViewById(R.id.close_dialog);
                close_dialog.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
                ShowListGame();
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                break;
        }
    }

    private void ShowListGame() {
        if (recyclerView_game == null) {
            return;
        }
        listDanhSachGame = FbDao.getListGame();
        danhSachGameAdapter = new DanhSachGameAdapter(new OnclickItemGame() {
            @Override
            public void onclickItemGame(Game game) {
                onClickItem(game);
            }
        });
        danhSachGameAdapter.setListGame(listDanhSachGame);
        recyclerView_game.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView_game.setAdapter(danhSachGameAdapter);
    }


    private void onClickItem(Game game) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (game.getTrangThai().equalsIgnoreCase("Bảo trì")) {
            Snackbar snackbar = Snackbar.make(viewFrag, "Hiện trò chơi đang bảo trì. Hãy thử lại vào lần sau nhé", 2000);
            View snackbar_view = snackbar.getView();
            TextView tv_bar = snackbar_view.findViewById(com.google.android.material.R.id.snackbar_text);
            tv_bar.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.nervous, 0);
            snackbar.show();
            return;
        }

        if (!game.getKieu().equalsIgnoreCase("lượt")) {
            fragmentHenTroChoiGio fragTroChoigio = new fragmentHenTroChoiGio();
            Bundle bundle = new Bundle();
            bundle.putSerializable("obj_game", game);
            fragTroChoigio.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragTroChoigio).addToBackStack("").commit();
            dialog.dismiss();
        } else {
            fragmentHenTroChoiLuot fragTroChoiluot = new fragmentHenTroChoiLuot();
            Bundle bundle = new Bundle();
            bundle.putSerializable("obj_game", game);
            fragTroChoiluot.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragTroChoiluot).addToBackStack("").commit();
            dialog.dismiss();
        }
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack(fragment_Trangchu.TAG).commit();
    }

    public void onClickLayout() {
        layout_troChoi.setOnClickListener(this::onClick);
        goTofragQr.setOnClickListener(this::onClick);
        layout_soDu.setOnClickListener(this::onClick);
        hideshowSoduHomefrag.setOnClickListener(this::onClick);
    }

    private void phanQuyen() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            ) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_QRcode()).addToBackStack("").commit();


            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUETCODE);
            }
        }

    }

    private void checkQrcode() {
        if (fragment_QRcode.trangThai == null) {
            return;
        }
        Snackbar snackbar;
        View snackbar_view;
        TextView tv_bar;

        switch (fragment_QRcode.trangThai) {

            case "Đang được chơi":
                snackbar = Snackbar.make(viewFrag, "Hiện trò chơi đang được người khác chơi, hãy thử lại vào lần sau nhé", 2000);
                snackbar_view = snackbar.getView();
                tv_bar = snackbar_view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv_bar.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.stop, 0);
                snackbar.show();

                break;
            case "Bảo trì":
                snackbar = Snackbar.make(viewFrag, "Hiện trò chơi đang được bảo trì, hãy thử lại vào lần sau nhé", 2000);
                snackbar_view = snackbar.getView();
                tv_bar = snackbar_view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv_bar.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.nervous, 0);
                snackbar.show();
                break;


        }
        fragment_QRcode.trangThai = null;

    }

    @Override
    public void onResume() {
        super.onResume();
        checkQrcode();
        Log.d(TAG, "onResume: avatar" + FbDao.UserLogin.getAvatar());
        SetDataForView();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter("UpdateService"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        if (requestCode == REQUETCODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: thanh cong");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new fragment_QRcode()).commit();


            } else {
                Log.d(TAG, "onRequestPermissionsResult: that bai");
            }

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        FbDao.ReadHistory();
    }

}