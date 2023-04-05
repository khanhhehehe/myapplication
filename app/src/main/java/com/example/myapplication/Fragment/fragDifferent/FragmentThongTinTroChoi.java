package com.example.myapplication.Fragment.fragDifferent;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Fragment.fragListgameAndVoudcher.Fragment_ListDanhSachTroChoi;
import com.example.myapplication.Model.Game;
import com.example.myapplication.R;

public class FragmentThongTinTroChoi extends Fragment {
    private TextView tvTemGameFragThongTin;
    private TextView tvMoTa;
    private TextView tvTrangThaiFragThongTin;
    private TextView tvLoaTroChoi,tv_giaTien;
    private Button btnChoi;
    private Toolbar toolbarDanhSachGame;
    private ImageView btnBackToDanhSachGame;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_tin_tro_choi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
//        thongTinGame();
        backToDanhSach();
    }

    public void anhXa(View view) {
        tvTemGameFragThongTin = view.findViewById(R.id.tv_temGame_frag_thong_tin);
        tvMoTa = view.findViewById(R.id.tv_mo_ta);
        tvTrangThaiFragThongTin = view.findViewById(R.id.tv_trang_thai_frag_thong_tin);
        tvLoaTroChoi = view.findViewById(R.id.tv_loa_tro_choi);
        btnChoi = view.findViewById(R.id.btn_choi);
        toolbarDanhSachGame = view.findViewById(R.id.toolbar_DanhSachGame);
        btnBackToDanhSachGame = view.findViewById(R.id.btn_backToDanhSachGame);
        tv_giaTien = view.findViewById(R.id.tv_giaTien);
    }
    public void backToDanhSach(){
        btnBackToDanhSachGame.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame,new Fragment_ListDanhSachTroChoi()).commit();
        });
    }
    public void thongTinGame() {
        Bundle bundle = getArguments();
        Game game = (Game) bundle.get("obj_game");
        if(game.getKieu().equalsIgnoreCase("lượt")){
            tv_giaTien.setText(game.getGia() + " / 1 lượt");
        }else {
            tv_giaTien.setText(game.getGia() + " / 15 phút");
        }
        tvLoaTroChoi.setText(game.getKieu());
        tvMoTa.setText(game.getMoTa());
        tvTemGameFragThongTin.setText(game.getTenGame());
        tvTrangThaiFragThongTin.setText(game.getTrangThai());
        if(game.getTrangThai().equalsIgnoreCase("Đang Hoạt Động")){
            tvTrangThaiFragThongTin.setTextColor(Color.parseColor("#2FC863"));
            btnChoi.setEnabled(true);
        }else {
            tvTrangThaiFragThongTin.setTextColor(Color.parseColor("#E04119"));
            btnChoi.setEnabled(false);
        }
    }
}