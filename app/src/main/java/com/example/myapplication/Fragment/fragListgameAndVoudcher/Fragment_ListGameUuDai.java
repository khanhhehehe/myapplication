package com.example.myapplication.Fragment.fragListgameAndVoudcher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.GameUuDaiVerticalAdapter;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Model.Game;
import com.example.myapplication.Model.Voucher;
import com.example.myapplication.R;
import com.example.myapplication.Interface.OnclickItemGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ListGameUuDai#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ListGameUuDai extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView_danhmuc_ListGame;
    private ImageView btn_BackToUuDai,btn_Search;
    private GameUuDaiVerticalAdapter gameUuDaiVerticalAdapter;
    private List<Game> listGame,listSearchGame;
    private androidx.appcompat.widget.SearchView searchView_listGameUuDai;
    private TextView tv_title;
    private static final String TAG = "Fragment_ListGameUuDai";
    public Fragment_ListGameUuDai() {

    }



    public static Fragment_ListGameUuDai newInstance() {
        Fragment_ListGameUuDai fragment = new Fragment_ListGameUuDai();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_game_danh_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa(view);
        ShowListGame();

        searchView_listGameUuDai.setVisibility(View.GONE);
        // bắt sự kiện khi click
        btn_BackToUuDai.setOnClickListener(this::onClick);
        btn_Search.setOnClickListener(this::onClick);

        searchGame();
    }
    private void AnhXa(View view){
        recyclerView_danhmuc_ListGame = view.findViewById(R.id.recyclerview_danhmuc_ListGame);
        btn_BackToUuDai = view.findViewById(R.id.btn_BackToUuDai_fragDanhmuc);
        searchView_listGameUuDai = view.findViewById(R.id.searchView_listGameUuDai);
        btn_Search = view.findViewById(R.id.btn_search_fragDanhmuc);
        tv_title = view.findViewById(R.id.tv_tiltle);
        tv_title.setText("Danh mục trò chơi");
    }
    private void ShowListGame(){
        listGame = FbDao.getListGame();
        gameUuDaiVerticalAdapter = new GameUuDaiVerticalAdapter(getActivity(), new OnclickItemGame() {
            @Override
            public void onclickItemGame(Game game) {
                showVocheNameGame(game);
            }
        });
        gameUuDaiVerticalAdapter.setListDanhSachGame(listGame);
        recyclerView_danhmuc_ListGame.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView_danhmuc_ListGame.setAdapter(gameUuDaiVerticalAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_BackToUuDai_fragDanhmuc:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.btn_search_fragDanhmuc:
                if (searchView_listGameUuDai.getVisibility()==View.GONE){
                    btn_Search.setImageResource(R.drawable.ic_baseline_close_24);
                    searchView_listGameUuDai.setVisibility(View.VISIBLE);
                    searchView_listGameUuDai.onActionViewExpanded();
                }else {
                    searchView_listGameUuDai.setVisibility(View.GONE);
                    btn_Search.setImageResource(R.drawable.ic_baseline_search_24);
                }
                break;
        }
    }
    private void searchGame() {
        searchView_listGameUuDai.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setListSerachGame(newText);
                return false;
            }
        });
    }

    private void setListSerachGame(String newText) {
        if ("".equalsIgnoreCase(newText)) {
//            tvthongBao.setVisibility(View.GONE);
            gameUuDaiVerticalAdapter.setListDanhSachGame(listGame);
            recyclerView_danhmuc_ListGame.setAdapter(gameUuDaiVerticalAdapter);
        } else {
            listSearchGame = new ArrayList<>();
            for (Game game : listGame) {
                if (game.getTenGame().toLowerCase().contains(newText.toLowerCase(Locale.ROOT))) {
                    listSearchGame.add(game);
                }
            }
//            if(listGame.isEmpty()){
//                tvthongBao.setVisibility(View.VISIBLE);
//            }else {
//                tvthongBao.setVisibility(View.GONE);
//            }
            gameUuDaiVerticalAdapter.setListDanhSachGame(listSearchGame);
        }
    }
    public void showVocheNameGame(Game game) {
        List<Voucher> listVoucherGameName = new ArrayList<>();
        List<Voucher> voucherList = FbDao.getListVoucher();
        for (Voucher voucher : voucherList) {
            if (voucher.getLoaiGame() == game.getId() || voucher.getLoaiGame() == 0) {
                listVoucherGameName.add(voucher);
            }
        }
        Collections.sort(listVoucherGameName, new Comparator<Voucher>() {
            @Override
            public int compare(Voucher voucher, Voucher t1) {
                if (voucher.getGiamGia() == t1.getGiamGia()) {
                    return 0;
                }
                if (voucher.getGiamGia() > t1.getGiamGia()) {
                    return 1;
                }
                return -1;
            }
        });
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new Fragment_ListVoucherUuDaiTenTroChoi(listVoucherGameName)).addToBackStack(Fragment_ListGameUuDai.TAG).commit();
    }
}