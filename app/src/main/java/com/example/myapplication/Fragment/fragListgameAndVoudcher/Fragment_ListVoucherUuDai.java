package com.example.myapplication.Fragment.fragListgameAndVoudcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.VoucherVerticalAdapter;
import com.example.myapplication.Adapter.VoucherVerticalAdapter2;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragmentMainChild.fragment_Trangchu;
import com.example.myapplication.Fragment.fragment_Main;
import com.example.myapplication.Interface.OnClickUseNow;
import com.example.myapplication.Interface.OnclickItemVoucher;
import com.example.myapplication.Model.Voucher;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Fragment_ListVoucherUuDai extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView_voucher_ListGame;
    private ImageView btn_BackToUuDai_fragVoucher, btn_Search_fragVoucher;
    private VoucherVerticalAdapter2 voucherVerticalAdapter;
    private List<Voucher> listVoucher,listSearchVoucher;
    private androidx.appcompat.widget.SearchView searchView_listVoucherUuDai;

    public Fragment_ListVoucherUuDai() {
        // Required empty public constructor
    }

    public static Fragment_ListVoucherUuDai newInstance() {
        Fragment_ListVoucherUuDai fragment = new Fragment_ListVoucherUuDai();
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
        return inflater.inflate(R.layout.fragment__list_voucher_uu_dai, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa(view);
        ShowListVoucher();

        searchView_listVoucherUuDai.setVisibility(View.GONE);
        // bắt sự kiện khi click
        btn_BackToUuDai_fragVoucher.setOnClickListener(this::onClick);
        btn_Search_fragVoucher.setOnClickListener(this::onClick);

        searchVoucher();
    }

    private void searchVoucher() {
        searchView_listVoucherUuDai.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setListSerachVoucher(newText);
                return false;
            }
        });
    }

    private void setListSerachVoucher(String newText) {
        if ("".equalsIgnoreCase(newText)) {
//            tvthongBao.setVisibility(View.GONE);
            voucherVerticalAdapter.setListDanhSachVoucher(listVoucher);
            recyclerView_voucher_ListGame.setAdapter(voucherVerticalAdapter);
        } else {
            listSearchVoucher = new ArrayList<>();
            for (Voucher voucher : listVoucher) {
                if (voucher.getMaVoucher().toLowerCase().contains(newText.toLowerCase())){
                    listSearchVoucher.add(voucher);
                }
            }
//            if(listGame.isEmpty()){
//                tvthongBao.setVisibility(View.VISIBLE);
//            }else {
//                tvthongBao.setVisibility(View.GONE);
//            }
            voucherVerticalAdapter.setListDanhSachVoucher(listSearchVoucher);
        }
    }

    private void AnhXa(View view) {
        recyclerView_voucher_ListGame = view.findViewById(R.id.recyclerview_voucher_ListGame);
        btn_BackToUuDai_fragVoucher = view.findViewById(R.id.btn_BackToUuDai_fragVoucher);
        searchView_listVoucherUuDai = view.findViewById(R.id.searchView_listVoucherUuDai);
        btn_Search_fragVoucher = view.findViewById(R.id.btn_search_fragVoucher);
    }

    private void ShowListVoucher() {
        listVoucher = FbDao.getListVoucher();
        voucherVerticalAdapter = new VoucherVerticalAdapter2(getContext());
        voucherVerticalAdapter.setOnClickUseNow(new OnClickUseNow() {
            @Override
            public void onclickItemVoucher() {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, Fragment_ListDanhSachTroChoi.newInstance()).addToBackStack("").commit();
            }
        });
        voucherVerticalAdapter.setListDanhSachVoucher(listVoucher);
        recyclerView_voucher_ListGame.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView_voucher_ListGame.setAdapter(voucherVerticalAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_BackToUuDai_fragVoucher:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.btn_search_fragVoucher:
                if (searchView_listVoucherUuDai.getVisibility() == View.GONE) {
                    searchView_listVoucherUuDai.setVisibility(View.VISIBLE);
                    searchView_listVoucherUuDai.onActionViewExpanded();
                } else {
                    searchView_listVoucherUuDai.setVisibility(View.GONE);
                }
                break;
        }
    }
}