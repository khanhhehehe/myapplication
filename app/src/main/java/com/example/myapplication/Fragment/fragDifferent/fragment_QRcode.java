package com.example.myapplication.Fragment.fragDifferent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.myapplication.Dialog.DialogLoading;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Model.Game;
import com.example.myapplication.R;
import com.google.zxing.Result;

import com.example.myapplication.Fragment.fragmentTypeGame.*;

public class fragment_QRcode extends Fragment {
    private CodeScannerView qrcodeScaner;
    private ImageView btn_backToHome;
    private final String TAG = "fragment_QRcode";
    public static boolean check = false;
    public static String trangThai = null;

    public fragment_QRcode() {

    }


    public static fragment_QRcode newInstance() {
        fragment_QRcode fragment = new fragment_QRcode();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment__q_rcode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        setUpQrcode();
        backToHome();
    }

    private void Anhxa(View v) {
        qrcodeScaner = v.findViewById(R.id.qrcodeScaner);
        btn_backToHome = v.findViewById(R.id.btn_backToHome);
    }

    private void backToHome() {
        btn_backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });
    }

    private CodeScanner codeScanner;

    public void setUpQrcode() {
        codeScanner = new CodeScanner(getActivity(), qrcodeScaner);
        codeScanner.startPreview();

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                Log.d(TAG, "onDecoded: " + result);
                for (Game g : FbDao.getListGame()
                ) {
                    if ((g.getId() + "").equals(result.toString())) {
                        if (g.getTrangThai().equalsIgnoreCase("Đang được chơi")) {

                            trangThai = "Đang được chơi";
                            getActivity().getSupportFragmentManager().popBackStack();
                            return;

                        } else if (g.getTrangThai().equalsIgnoreCase("Bảo trì")) {
                            trangThai = "Bảo trì";
                            getActivity().getSupportFragmentManager().popBackStack();
                            return;
                        } else {
                            check = true;
                            Bundle bundle = new Bundle();
                            if (g.getKieu().equalsIgnoreCase("thời gian")) {
                                bundle.putSerializable("obj_game", g);
                                fragmentTroChoiGio fragmentTroChoiGio = new fragmentTroChoiGio();
                                fragmentTroChoiGio.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentTroChoiGio).commit();
                                return;

                            } else {
                                fragmentTroChoiLuot fragmentTroChoiLuot = new fragmentTroChoiLuot();
                                bundle.putSerializable("obj_game", g);
                                fragmentTroChoiLuot.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentTroChoiLuot).commit();
                                return;

                            }
                        }

                    }

                }
                trangThai = "Không đúng qr code";
                getActivity().getSupportFragmentManager().popBackStack();


            }
        });


    }

}
