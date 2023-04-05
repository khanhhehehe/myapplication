package com.example.myapplication.Fragment.fragListgameAndVoudcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.DanhSachGameAdapter;
import com.example.myapplication.Adapter.SliderAdapter;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragmentTypeGame.fragmentTroChoiGio;
import com.example.myapplication.Fragment.fragmentTypeGame.fragmentTroChoiLuot;
import com.example.myapplication.Fragment.fragmentMainChild.fragment_Trangchu;
import com.example.myapplication.Model.Game;
import com.example.myapplication.Model.Hoadonchoigame;
import com.example.myapplication.R;
import com.example.myapplication.Interface.OnclickItemGame;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.SliderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.myapplication.Dialog.*;

public class Fragment_ListDanhSachTroChoi extends Fragment implements View.OnClickListener {
    private FrameLayout fragmentDanhMucListGameUuDai;
    private Toolbar toolbarDanhMuc;
    private ImageView btnBackToTrangChu;
    private ImageView btnSearchTroChoi;
    private SearchView searchViewListGame;
    private SliderView imageSlider;
    private RecyclerView recyclerviewListGame;
    private List<Game> listDanhSachGame;
    private List<Game> gameSearchList;
    private DanhSachGameAdapter danhSachGameAdapter;
    private TextView tvthongBao;
    private static final String TAG = "FRAGMENT_TRO_CHOI";
    private View viewFrag = null;
    public static boolean chk = false;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static String endTime;

    public Fragment_ListDanhSachTroChoi() {

    }

    public static Fragment_ListDanhSachTroChoi newInstance() {
        Fragment_ListDanhSachTroChoi fragment = new Fragment_ListDanhSachTroChoi();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("UpdateService"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_danh_sach_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa(view);
        ShowListVoucher();
        viewFrag = view;
        // khai báo mảng ảnh và gán giá trị src ảnh
        int[] img = new int[]{R.drawable.banner11, R.drawable.banner20, R.drawable.banner12, R.drawable.chrismas};
        SliderAdapter adapter = new SliderAdapter(img);
        // set lên slideAdapter
        imageSlider.setSliderAdapter(adapter);
        animation(imageSlider);
        searchViewListGame.setVisibility(View.GONE);
        // bắt sự kiện khi click
        btnBackToTrangChu.setOnClickListener(this::onClick);
        btnSearchTroChoi.setOnClickListener(this::onClick);
        searchGame();
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            listDanhSachGame = FbDao.getListGame();
            Log.d(TAG, "onReceive: " + listDanhSachGame.get(0).getTrangThai());
            danhSachGameAdapter = new DanhSachGameAdapter(new OnclickItemGame() {
                @Override
                public void onclickItemGame(Game game) {
                    onClickItem(game);
                }
            });
            danhSachGameAdapter.setListGame(listDanhSachGame);
            recyclerviewListGame.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerviewListGame.setAdapter(danhSachGameAdapter);
        }
    };

    private void AnhXa(View view) {
        fragmentDanhMucListGameUuDai = view.findViewById(R.id.fragmentDanhMuc_ListGameUuDai);
        toolbarDanhMuc = view.findViewById(R.id.toolbar_DanhMuc);
        btnBackToTrangChu = view.findViewById(R.id.btn_backToTrangChu);
        btnSearchTroChoi = view.findViewById(R.id.btn_search_troChoi);
        searchViewListGame = view.findViewById(R.id.searchView_listGame);
        imageSlider = view.findViewById(R.id.image_slider);
        recyclerviewListGame = view.findViewById(R.id.recyclerview_ListGame);
        tvthongBao = view.findViewById(R.id.tv_thong_bao);
        tvthongBao.setVisibility(View.GONE);
    }

    private void ShowListVoucher() {
        Log.d(TAG, "ShowListVoucher: ");
        listDanhSachGame = FbDao.getListGame();
        danhSachGameAdapter = new DanhSachGameAdapter(new OnclickItemGame() {
            @Override
            public void onclickItemGame(Game game) {
                onClickItem(game);
            }
        });
        danhSachGameAdapter.setListGame(listDanhSachGame);
        recyclerviewListGame.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerviewListGame.setAdapter(danhSachGameAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_backToTrangChu:
                getActivity().getSupportFragmentManager().popBackStack();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new fragment_Trangchu()).commit();
                break;
            case R.id.btn_search_troChoi:
                if (searchViewListGame.getVisibility() == View.GONE) {
                    btnSearchTroChoi.setImageResource(R.drawable.ic_baseline_close_24);
                    searchViewListGame.setVisibility(View.VISIBLE);
                    searchViewListGame.onActionViewExpanded();
                } else {
                    searchViewListGame.setVisibility(View.GONE);
                    btnSearchTroChoi.setImageResource(R.drawable.ic_baseline_search_24);
                }
                break;
        }
    }

    private void animation(SliderView imageSlider) {
        imageSlider.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
    }

    public void searchGame() {
        searchViewListGame.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setListSearchGame(newText);
                return false;
            }
        });
    }

    private void setListSearchGame(String query) {
        if ("".equalsIgnoreCase(query)) {
            tvthongBao.setVisibility(View.GONE);
            danhSachGameAdapter.setListGame(listDanhSachGame);
            recyclerviewListGame.setAdapter(danhSachGameAdapter);
        } else {
            gameSearchList = new ArrayList<>();
            for (Game game : listDanhSachGame) {
                if (game.getTenGame().toLowerCase().contains(query.toLowerCase(Locale.ROOT))) {
                    gameSearchList.add(game);
                }
            }
            if (gameSearchList.isEmpty()) {
                tvthongBao.setVisibility(View.VISIBLE);
            } else {
                tvthongBao.setVisibility(View.GONE);
            }
            danhSachGameAdapter.setListGame(gameSearchList);
        }
    }

    public void onClickItem(Game game) {
        if (game.getTrangThai().equalsIgnoreCase("Bảo trì")) {
            Snackbar snackbar = Snackbar.make(viewFrag, "Hiện trò chơi đang được bảo trì, hãy thử lại vào lần sau nhé", 2000);
            View snackbar_view = snackbar.getView();
            TextView tv_bar = snackbar_view.findViewById(com.google.android.material.R.id.snackbar_text);
            tv_bar.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.nervous, 0);
            snackbar.show();
            return;
        }
        if (game.getTrangThai().equalsIgnoreCase("Đang được chơi")) {
            for (Hoadonchoigame hd : FbDao.ListgamePlaying
            ) {
                if (hd.getGameid().equals(game.getId() + "")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    try {
                        Date dateEnd = dateFormat.parse(hd.getDateEnd());
                        Date now = new Date();
                        Log.d("vailon" + (dateEnd.getTime() - now.getTime()), "");
                        DialogCountdown dialogCountdown = new DialogCountdown(getContext());

                        dialogCountdown.setTimeout(dateEnd.getTime() - now.getTime());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

            }

//            Snackbar snackbar = Snackbar.make(viewFrag, "Hiện trò chơi đã được chơi xin, quý khách hãy đăng kí game khác", 2000);
//            View snackbar_view = snackbar.getView();
//            TextView tv_bar = snackbar_view.findViewById(com.google.android.material.R.id.snackbar_text);
//            tv_bar.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.stop, 0);
//            snackbar.show();


            return;
        }
        chonGameLuot(game);
    }

    private void chonGameLuot(Game game) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (!game.getKieu().equalsIgnoreCase("lượt")) {
            fragmentTroChoiGio fragmentTroChoigio = new fragmentTroChoiGio();
            Bundle bundle = new Bundle();
            bundle.putSerializable("obj_game", game);
            fragmentTroChoigio.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragmentTroChoigio).addToBackStack(Fragment_ListDanhSachTroChoi.TAG).commit();
        } else {
            fragmentTroChoiLuot fragmentTroChoiluot = new fragmentTroChoiLuot();
            Bundle bundle = new Bundle();
            bundle.putSerializable("obj_game", game);
            fragmentTroChoiluot.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragmentTroChoiluot).addToBackStack(Fragment_ListDanhSachTroChoi.TAG).commit();
        }
    }
}