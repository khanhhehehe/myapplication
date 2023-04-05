package com.example.myapplication.Fragment.fragDifferent;

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

import com.example.myapplication.Adapter.NotifyAdapter;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Model.Notify;
import com.example.myapplication.R;

import java.util.List;

public class FragmentThongBao extends Fragment {
    private List<Notify> listNotify;
    private NotifyAdapter notifyAdapter;
    private ImageView btnBackToUser;
    private RecyclerView recyclerviewNotify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        fillRecyclerView();
        backltoUser();
    }

    private void backltoUser() {
        btnBackToUser.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void fillRecyclerView() {
        listNotify = FbDao.getListNotify();
        notifyAdapter = new NotifyAdapter(listNotify);
        recyclerviewNotify.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerviewNotify.setAdapter(notifyAdapter);
    }

    public void anhXa(View view) {
        btnBackToUser = view.findViewById(R.id.btn_backNotify);
        recyclerviewNotify = view.findViewById(R.id.recyclerview_notify);
    }
}