package com.example.myapplication.Fragment.fragmentUserChild;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Adapter.AdapterMyBookingHistory;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.R;


public class fragment_MybookingHistory extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerviewMyBookinghis;
    private ImageView btnBackFromMybooking;


    public fragment_MybookingHistory() {

    }


    public static fragment_MybookingHistory newInstance() {
        fragment_MybookingHistory fragment = new fragment_MybookingHistory();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment__mybooking_history, container, false);
    }

    private void Anhxa(View view) {
        recyclerviewMyBookinghis = view.findViewById(R.id.recyclerview_myBookinghis);
        btnBackFromMybooking = view.findViewById(R.id.btnBack_fromMybooking);
        btnBackFromMybooking.setOnClickListener(this::onClick);
    }

    private void FildataForView() {
        AdapterMyBookingHistory adapter = new AdapterMyBookingHistory(FbDao.getListHoaDonHenGio());
        recyclerviewMyBookinghis.setAdapter(adapter);
        recyclerviewMyBookinghis.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        FildataForView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack_fromMybooking:
                getActivity().getSupportFragmentManager().popBackStack();
                break;


        }


    }
}