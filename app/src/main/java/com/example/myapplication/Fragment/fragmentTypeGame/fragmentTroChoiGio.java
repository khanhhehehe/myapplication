
package com.example.myapplication.Fragment.fragmentTypeGame;


import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ListThoiGianAdapter;
import com.example.myapplication.Adapter.VoucherVerticalAdapter;
import com.example.myapplication.BroadcastReciver.ChannelTB;
import com.example.myapplication.BroadcastReciver.ThongBao;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragmentMainChild.fragment_Trangchu;
import com.example.myapplication.Fragment.fragment_Main;
import com.example.myapplication.Model.Game;
import com.example.myapplication.Model.HoaDonHenGio;
import com.example.myapplication.Model.Hoadonchoigame;
import com.example.myapplication.Model.PlayTime;
import com.example.myapplication.Model.Voucher;
import com.example.myapplication.R;
import com.example.myapplication.Interface.OnclickItemTime;
import com.example.myapplication.Interface.OnclickItemVoucher;
import com.google.android.material.snackbar.Snackbar;
import com.example.myapplication.Fragment.fragDifferent.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentTroChoiGio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentTroChoiGio extends Fragment implements View.OnClickListener {
    private TextView tv_nameGame, tv_cost, tv_detailGame, tv_voucherChoose, tv_totalCost;
    private LinearLayout choose_voucher;
    private ImageView imgGame;
    private RecyclerView recyclerView_voucher_gio, recyclerview_choose_time;
    private VoucherVerticalAdapter voucherVerticalAdapter;
    private ListThoiGianAdapter listThoiGianAdapter;
    private final List<PlayTime> list = new ArrayList<>();
    private List<Voucher> voucherListGameChoose;
    private ImageView close_dialog, backToDSGame;
    private Dialog dialog;
    private Voucher voucherChoose;
    private PlayTime playTime_choose;
    private AppCompatButton btn_play;
    private float total = 0;
    private Game game;
    private final int[] arr = {R.drawable.time5, R.drawable.time10, R.drawable.time15, R.drawable.time20, R.drawable.time25, R.drawable.time30, R.drawable.time35, R.drawable.time40, R.drawable.time45, R.drawable.time50, R.drawable.time55, R.drawable.time60};
    //private final int[] arrTime = {5,10,15,20,25,30,35,40,45,50,55,60};//mảng thời gian tính theo phút
    private float sale;
    String pattern = "###,### Poin";
    DecimalFormat df = new DecimalFormat(pattern);
    //Thời gian chơi
    private int time;

    //thời gian hệ thống ( hiện tại )
    private final int presentTimeHours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private final int presentTimeMinutes = Calendar.getInstance().get(Calendar.MINUTE);

    //thời gian hệ thống ( khi chơi )
    private int playingTimeHours = presentTimeHours;//gán trc giá trị là giờ của hệ thống
    private int playingTimeMinutes = presentTimeMinutes;

    //lớp gửi , nhận thông báo
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private List<Hoadonchoigame> hoadonchoigameList;


    public static fragmentTroChoiGio newInstance() {
        fragmentTroChoiGio fragment = new fragmentTroChoiGio();
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
        return inflater.inflate(R.layout.fragment_tro_choi_gio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa(view);
        AddTime();
        ShowThoiGian();
        setThongTin();
        TinhTongTien();
        fragment_Trangchu.gochild = true;
        choose_voucher.setOnClickListener(this::onClick);
        backToDSGame.setOnClickListener(this::onClick);
        btn_play.setOnClickListener(this::onClick);

    }

    private void AnhXa(View view) {
        backToDSGame = view.findViewById(R.id.btn_backToDSGame);
        choose_voucher = view.findViewById(R.id.choose_voucher);
        recyclerview_choose_time = view.findViewById(R.id.recyclerview_choose_time);
        tv_nameGame = view.findViewById(R.id.tv_nameGame);
        tv_cost = view.findViewById(R.id.tv_cost);
        tv_detailGame = view.findViewById(R.id.tv_detailGame);
        tv_voucherChoose = view.findViewById(R.id.tv_voucherChoose);
        tv_totalCost = view.findViewById(R.id.tv_totalCost);
        btn_play = view.findViewById(R.id.btn_play);
        imgGame = view.findViewById(R.id.imgGame);
        btn_play.setEnabled(false);

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    }

    private void FillVoucher() {
        voucherListGameChoose = new ArrayList<>();
        for (Voucher voucher : FbDao.getListVoucher()) {
            if (voucher.getLoaiGame() == game.getId() || voucher.getLoaiGame() == 0) {
                voucherListGameChoose.add(voucher);
            }
        }
    }

    private void ShowListVoucher() {
        FillVoucher();
        voucherVerticalAdapter = new VoucherVerticalAdapter(new OnclickItemVoucher() {
            @Override
            public void onclickItemVoucher(Voucher voucher) {
                onClickItemChooseVoucher(voucher);
            }
        });
        voucherVerticalAdapter.setListDanhSachVoucher(voucherListGameChoose);
        recyclerView_voucher_gio.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView_voucher_gio.setAdapter(voucherVerticalAdapter);
    }

    private void onClickItemChooseVoucher(Voucher voucher) {
        voucherChoose = voucher;
        tv_voucherChoose.setText(voucherChoose.getMaVoucher());
        TinhTongTien();
        dialog.dismiss();
    }

    private void ShowThoiGian() {
        listThoiGianAdapter = new ListThoiGianAdapter(new OnclickItemTime() {
            @Override
            public void onclickItemTime(PlayTime playTime) {
                onClickItemChooseTime(playTime);
            }
        });
        listThoiGianAdapter.setListThoiGian(list);
        recyclerview_choose_time.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_choose_time.setAdapter(listThoiGianAdapter);
    }

    private void onClickItemChooseTime(PlayTime playTime) {
        playTime_choose = playTime;
        TinhTongTien();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_voucher:
                dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_choosevouher_gio);
                recyclerView_voucher_gio = dialog.findViewById(R.id.recyclerview_voucher_gio);
                close_dialog = dialog.findViewById(R.id.close_dialog);
                close_dialog.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
                ShowListVoucher();
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                break;
            case R.id.btn_backToDSGame:
                if (fragment_QRcode.check) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Main()).commit();
                } else {
                    getActivity().getSupportFragmentManager().popBackStack();

                }
                break;
            case R.id.btn_play:
                if (playTime_choose == null) {
                    Snackbar snackbar = Snackbar.make(getView(), "Vui lòng chọn thời gian chơi", 2000);
                    View snackbar_view = snackbar.getView();
                    TextView tv_bar = snackbar_view.findViewById(com.google.android.material.R.id.snackbar_text);
                    tv_bar.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.stop, 0);
                    snackbar.show();
                } else {

                    sendNotifications();
                    if (voucherChoose != null) {
                        for (Object idUser : voucherChoose.getListUserId()
                        ) {
                            if (idUser != null && idUser.toString().equals(FbDao.UserLogin.getId())) {
                                voucherChoose.getListUserId().remove(idUser);
                                FbDao.UpdateVoucher(voucherChoose);
                                break;
                            }
                        }
                    }
                    if (fragment_QRcode.check) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Main()).commit();
                    } else {
                        getActivity().getSupportFragmentManager().popBackStack();

                    }

                }
                break;

        }
    }

    private void AddTime() {
        for (int i = 0; i < arr.length; i++) {
            list.add(new PlayTime(i, arr[i]));
        }
    }

    private void setThongTin() {
        Bundle bundle = getArguments();
        game = (Game) bundle.get("obj_game");
        tv_nameGame.setText(game.getTenGame());
        tv_cost.setText(df.format(game.getGia()) + " / 5 phút");
        tv_detailGame.setText(game.getMoTa());
        imgGame.setImageResource(game.getImgGame());
    }

    private void TinhTongTien() {
        if (playTime_choose != null) {
            if (voucherChoose == null) {
                for (int i = 0; i < arr.length; i++) {
                    if (playTime_choose.getId() == i) {
                        time = i + 1;
                        playingTimeMinutes += time;//gán giá trị tương ứng với số phút trên dánh sách
                        if (playingTimeMinutes >= 60) {
                            int time_temp = playingTimeMinutes - 60;
                            playingTimeMinutes = time_temp;
                            playingTimeHours++;
                        }
                        total = game.getGia() * (i + 1);
                    }
                }
            } else {
                for (int i = 0; i < arr.length; i++) {
                    if (playTime_choose.getId() == i) {
                        time = i + 1;
                        playingTimeMinutes += time;
                        if (playingTimeMinutes >= 60) {
                            int time_temp = playingTimeMinutes - 60;
                            playingTimeMinutes = time_temp;
                            playingTimeHours++;
                        }
                        sale = voucherChoose.getGiamGia();
                        total = game.getGia() * (i + 1) * (1 - (sale / 100));

                    }
                }
            }
        }
        tv_totalCost.setText(df.format(total));
        checkBtndis();
    }

    //hàm nhận và gửi thông báo
    private void sendNotifications() {

        if (playingTimeHours == 24) {
            playingTimeHours = 0;
        }

        int h = playingTimeHours;
        int m = playingTimeMinutes;

        //tạo lớp lưu giờ khi người dùng chọn
        Calendar calendar1 = Calendar.getInstance();//đối tượng lưu thời gian hiện tại
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, presentTimeHours);
        calendar1.set(Calendar.MINUTE, presentTimeMinutes);
        Date timeStart = new Date();
        timeStart.setTime(calendar1.getTimeInMillis());

        Calendar calendar2 = Calendar.getInstance();//đối tượng lưu thời gian chơi
        calendar2.setTimeInMillis(System.currentTimeMillis());
        calendar2.set(Calendar.HOUR_OF_DAY, playingTimeHours);
        calendar2.set(Calendar.MINUTE, playingTimeMinutes);
        Date timeEnd = new Date();
        timeEnd.setTime(calendar2.getTimeInMillis());

        List<HoaDonHenGio> donHenGioList = FbDao.getListHoaDonHenGio();
        boolean xet = true;
        for (HoaDonHenGio item : donHenGioList) {
            SimpleDateFormat b_fmtDay = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {

                Date b_date1 = b_fmtDay.parse(item.getTimeStart());
                Date b_date2 = b_fmtDay.parse(item.getTimeEnd());

                if (item.getGameid().equals(String.valueOf(game.getId())) && item.isSuccess() == false) {
                    int ssDate_a1 = timeStart.compareTo(b_date1);
                    int ssDate_a2 = timeStart.compareTo(b_date2);

                    int ssDate_b1 = timeEnd.compareTo(b_date1);
                    int ssDate_b2 = timeEnd.compareTo(b_date2);


                    if ((ssDate_a1 >= 0 && ssDate_a2 <= 0) || (ssDate_b1 >= 0 && ssDate_b2 <= 0) || (timeStart.before(b_date1) && timeEnd.after(b_date2))) {


                        xet = false;
                        break;
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (xet) {
            String string_presentHours = presentTimeHours < 10 ? "0" + presentTimeHours : String.valueOf(presentTimeHours);
            String string_presentMinutes = presentTimeMinutes < 10 ? "0" + presentTimeMinutes : String.valueOf(presentTimeMinutes);
            String string_presentTime = string_presentHours + ":" + string_presentMinutes;

            String string_playingHours = playingTimeHours < 10 ? "0" + playingTimeHours : String.valueOf(playingTimeHours);
            String string_playingMinutes = playingTimeMinutes < 10 ? "0" + playingTimeMinutes : String.valueOf(playingTimeMinutes);
            String string_playingTime = string_playingHours + ":" + string_playingMinutes;

            int imgGame = game.getImgGame();
            String gameName = game.getTenGame();

            Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), imgGame);
            Bitmap imgApp = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.logo2);
            Notification notification = new NotificationCompat.Builder(getActivity(), ChannelTB.CHANNEL_ID) // khai báo compat
                    .setLargeIcon(imgApp)
                    .setContentTitle("Bắt đầu chơi : " + gameName + "")
                    .setContentText("Thời gian " + string_presentTime + " đến " + string_playingTime + "")
                    .setSmallIcon(R.drawable.logo2)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                    .build();
            NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(getTimeLocal(), notification);

            //nhận thông báo gửi đến cho service
            Intent intent = new Intent(getActivity(), ThongBao.class);
            intent.setAction("MyAction");
            intent.putExtra("time", string_playingTime);
            intent.putExtra("game", game);
            pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent);

            FbDao dao = new FbDao();
            dao.PlaygameGio(time, game.getId() + "", total);
            FbDao.Thanhtoantien(total);
        } else {
            Snackbar snackbar = Snackbar.make(getView(), "Khung giờ này đã có người đặt. Vui lòng chọn game khác", 2000);
            View snackbar_view = snackbar.getView();
            TextView tv_bar = snackbar_view.findViewById(com.google.android.material.R.id.snackbar_text);
            tv_bar.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.stop, 0);
            snackbar.show();
        }


    }

    private int getTimeLocal() {
        return (int) new Date().getTime();
    }

    private void checkBtndis() {
        btn_play.setEnabled(!(total > FbDao.UserLogin.getSodu()) && total != 0);


    }

}