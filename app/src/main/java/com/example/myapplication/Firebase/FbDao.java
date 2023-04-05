
package com.example.myapplication.Firebase;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.myapplication.Model.Game;
import com.example.myapplication.Model.HoaDonHenGio;
import com.example.myapplication.Model.Hoadon;
import com.example.myapplication.Model.Hoadonchoigame;
import com.example.myapplication.Model.Hoadonnaptien;
import com.example.myapplication.Model.Notify;
import com.example.myapplication.Model.User;
import com.example.myapplication.Model.Voucher;
import com.example.myapplication.R;
import com.example.myapplication.Service.UpdateService;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FbDao {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static FirebaseAuth mAuth;
    private static final String TAG = "Firebase Dao";
    public static List<Game> listGame;
    public static List<User> listUser;
    public static List<Voucher> listVoucher;
    public static List<Notify> listNotify;
    public static List<Hoadon> hoadonList;
    public static FirebaseStorage storageFireBase;
    public static StorageReference avatatRef;

    private static final int[] imageAvatarGame = new int[]{R.drawable.game_ghost_house, R.drawable.game_bounce_house, R.drawable.racingcar, R.drawable.gun, R.drawable.game_nhun_nhay, R.drawable.game_bao_nha, R.drawable.game_jumping_house, R.drawable.game_cau_truot, R.drawable.game_suc_cac, R.drawable.game_xich_du};


    private static List<Hoadonnaptien> hoadonnaptienList;

    private static List<HoaDonHenGio> hoadonhenGioList;
    private static List<Hoadonchoigame> hoadonchoigameList;

    //    private static List<Hoadonchoigame> hoadonchoigameListRecently;     lấy hóa đơn chơi game của trò chơi đang chơi
    public static List<Notify> getListNotify() {
        return listNotify;
    }

    public static List<Game> getListGame() {
        return listGame;
    }

    public static List<Hoadonchoigame> ListgamePlaying;

    public static List<Voucher> getListVoucher() {
        return listVoucher;
    }

    public static java.util.List<User> getList() {
        return listUser;
    }

    public static List<HoaDonHenGio> getListHoaDonHenGio() {
        return hoadonhenGioList;
    }

//    public static List<Hoadonchoigame> getHoadonchoigameList() {
//        return hoadonchoigameList;
//    }
//
//    public static List<Hoadonchoigame> getHoadonchoigameListRecently() {
//        return hoadonchoigameListRecently;
//    }


    public static User UserLogin;
    public static Activity activity;
    public static boolean Login = false;
    //trả về trạng thái khi load dữ liệu xong thằng nào đụng vào đấm chết
    public static boolean LoadedUser = false;
    public static boolean LoadedAvatar = false;
    public static boolean UpdatedUser = false;
    public static boolean UpLoadedAvatar = false;
    public static boolean LoadedVoucher = false;


    //hàm khởi tạo để trả về userId
    public FbDao(Activity context) {
        hoadonList = new ArrayList<>();
        activity = context;
        storageFireBase = FirebaseStorage.getInstance();
        avatatRef = storageFireBase.getReference().child("avatar");
        ReadUser();

        ReadGame();
        ReadNotify();
        ReadTimePlayGame();
        mAuth = FirebaseAuth.getInstance();
    }

    public FbDao() {

    }


    public static String getNameGameFromID(int id) {
        String tenGame = "";
        for (Game game : listGame) {
            if (game.getId() == id) {
                tenGame = game.getTenGame();
            }
        }
        return tenGame;
    }

    public static Game getGameFromID(int id) {

        for (Game game : listGame) {
            if (game.getId() == id) {
                return game;
            }
        }
        return null;
    }

    public static String getNameUserFromID(String id) {
        String userName = "";
        for (User item : listUser) {
            if (item.getId().equals(id)) {
                userName = item.getName();
                break;
            }
        }
        return userName;
    }

    //hàm update avatatar cho user
    public void UpLoadavatar(ImageView imageView) {
        UpLoadedAvatar = false;
        String id = UserLogin.getId();
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = avatatRef.child(id).putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            Log.e(TAG, "onFailure: to upload ", null);
            UpLoadedAvatar = true;
        }).addOnSuccessListener(taskSnapshot -> {
            Log.e(TAG, "onSuccess: to upload ", null);
            UpLoadedAvatar = true;
        });
    }
    //hàm load avatar

    public static void Thanhtoantien(float money) {
        money = UserLogin.getSodu() - money;
        Map<String, Object> map = new HashMap<>();
        map.put("sodu", money);
        DatabaseReference userRef = database.getReference("Users").child(UserLogin.getId());
        userRef.updateChildren(map, (error, ref) -> Log.d(TAG, "Thanh toán thành công"));

    }

    public static void updatePass(String id, String pass) {
        Map<String, Object> map = new HashMap<>();
        map.put("password", pass);
        DatabaseReference userRef = database.getReference("Users").child(id);
        userRef.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.d(TAG, "Update thành công");
            }
        });
    }


    public static void LoadAvatarFromID() {
        LoadedAvatar = false;
        StorageReference avartarRef = avatatRef.child((UserLogin.getId()));
        final long FIRE_MEGABYTE = 1024 * 1024 * 5;
        avartarRef.getBytes(FIRE_MEGABYTE).addOnSuccessListener(bytes -> {

            Bitmap avatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Log.d(TAG, "LoadAvatarFromID: loaded");
            if (UserLogin.getAvatar() == null || !UserLogin.getAvatar().sameAs(avatar)) {
                UserLogin.setAvatar(avatar);
                LoadedAvatar = true;


            } else {


                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadAvatarFromID();
                    }
                }, 1000);

            }
        }).addOnFailureListener(exception -> {
            Log.e(TAG, "onFailure:Loadavatar ", null);
            LoadedAvatar = true;
        });
    }

    //hàm này trả về ref của hoá đơn chơi game
    private static String getReferenceToday() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        String s = dateFormat.format(date);
        return s;
    }

    //hàm chơi game ko hiểu đừng đọc
    public void PlaygameGio(int minute, String idGame, float cost) {
        long milisecond = minute * 60 * 1000;
        Date curenTime = new Date();
        Date endTime = new Date(milisecond + curenTime.getTime());
        getReferenceToday();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String curenTimetoString = dateFormat.format(curenTime);
        String endTimetoString = dateFormat.format(endTime);
        DatabaseReference hoadonchoigameRef = database.getReference("Hoadonchoigame").child(getReferenceToday());
        Hoadonchoigame hoadon = new Hoadonchoigame();
        hoadon.setDateStart(curenTimetoString);
        hoadon.setUserid(UserLogin.getId());
        hoadon.setCost(cost);
        hoadon.setGameid(idGame);
        hoadon.setDateEnd(endTimetoString);
        hoadonchoigameRef.push().setValue(hoadon);
        Map<String, Object> map = new HashMap<>();
        map.put("trangThai", "Đang được chơi");
        database.getReference("Game").child(idGame).updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(activity, "Chơi thành công", Toast.LENGTH_SHORT).show();

            }
        });
    }


    //hàm login và bắt data cho userLogin để userLogin thay đổi data theo thời gian thực
    public static void Login(String id) {

        DatabaseReference myRef = database.getReference("Users");
        DatabaseReference userRef = myRef.child(id);
        ReadHistory();
        ReadVoucher(id);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserLogin = snapshot.getValue(User.class);
                UserLogin.setId(snapshot.getKey());
                Login = true;
                Log.d(TAG, "onDataChange: " + "Login");
                LoadAvatarFromID();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: " + error, null);
            }
        });
    }

    //hàm đọc về dữ liệu game
    private void ReadGame() {
        listGame = new ArrayList<>();
        DatabaseReference myRef = database.getReference("Game");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listGame.clear();

                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Game u = dt.getValue(Game.class);
                    if (u == null) {
                        continue;
                    }
                    listGame.add(u);

                }
                setImgGame();
                Log.d(TAG, "Đã nhận dữ liệu Game: ");
                activity.startService(new Intent(activity, UpdateService.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.toString()
                );
            }
        });
    }

    public static void setImgGame() {
        listGame = FbDao.getListGame();
        for (int i = 1; i < listGame.size() + 1; i++) {
            Game game = listGame.get(i - 1);
            if (game.getId() == i) {
                game.setImgGame(imageAvatarGame[i - 1]);
            }
        }
    }

    public static void ReadHistory() {
        hoadonchoigameList = new ArrayList<>();
        hoadonnaptienList = new ArrayList<>();

        DatabaseReference myRef = database.getReference("Hoadonchoigame");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hoadonchoigameList.clear();
                ListgamePlaying = new ArrayList<>();
                Log.d(TAG, "Hoadonchoigame: loaded");
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    for (DataSnapshot data : dt.getChildren()) {
                        for (DataSnapshot d : data.getChildren()
                        ) {
                            Hoadonchoigame u = d.getValue(Hoadonchoigame.class);
                            if (u == null) {
                                continue;
                            }
                            if (u.getUserid().equals(UserLogin.getId())) {
                                hoadonchoigameList.add(u);

                            }
                            if (!u.isSuccess()) {
                                ListgamePlaying.add(u);

                            }
                        }

                    }
                }
                hoadonList.clear();
                hoadonList.addAll(hoadonchoigameList);
                hoadonList.addAll(hoadonnaptienList);
                activity.startService(new Intent(activity, UpdateService.class));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.toString()
                );
            }
        });
        DatabaseReference myRef1 = database.getReference("HoaDonNapTien");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hoadonnaptienList.clear();
                Log.d(TAG, "HoaDonNapTien: loaded");

                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Hoadonnaptien u = dt.getValue(Hoadonnaptien.class);
                    if (u == null) {
                        continue;
                    }
                    if (u.getUserId().equals(UserLogin.getId()) && u.isTrangThai()) {
                        hoadonnaptienList.add(u);
                    }

                }
                hoadonList.clear();
                hoadonList.addAll(hoadonchoigameList);
                hoadonList.addAll(hoadonnaptienList);
                activity.startService(new Intent(activity, UpdateService.class));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.toString()
                );
            }
        });
    }


    //hàm đọc về dữ liệu voutcher
    private static void ReadVoucher(String id) {
        Log.d(TAG, "ReadVoucher: ");
        listVoucher = new ArrayList<>();
        DatabaseReference myRef = database.getReference("Voucher");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listVoucher.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Voucher u = dt.getValue(Voucher.class);
                    if (u == null) {
                        continue;
                    }
                    u.setId(dt.getKey());
                    u.setListUserId(u.getIDUser());
                    for (Object idvc : u.getListUserId()
                    ) {

                        if (idvc != null && idvc.toString().equals(id)) {
                            listVoucher.add(u);
                            break;
                        }

                    }
                }
                LoadedVoucher = true;
                Log.d(TAG, "Đã nhận dữ liệu voucher: ");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.toString()
                );
            }
        });
    }


    private void ReadNotify() {
        Log.d(TAG, "ReadVoucher: ");
        listNotify = new ArrayList<>();
        DatabaseReference myRef = database.getReference("Notify");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listNotify.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Notify u = dt.getValue(Notify.class);
                    if (u == null) {
                        continue;
                    }
                    listNotify.add(u);
                }
                Log.d(TAG, "Đã nhận dữ liệu voucher: ");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.toString()
                );
            }
        });

    }

    public static void UpdateVoucher(Voucher voucher) {
        Log.d(TAG, "ReadVoucher: ");
        listVoucher = new ArrayList<>();
        DatabaseReference myRef = database.getReference("Voucher").child(voucher.getId()).child("IDUser");
        myRef.setValue(voucher.getListUserId());
        ReadVoucher(UserLogin.getId());

    }

    public void ReadTimePlayGame() {
        hoadonhenGioList = new ArrayList<>();
        DatabaseReference myRef = database.getReference();

        Query query = myRef.child("HoaDonHenGio").orderByChild("cancel").equalTo(false);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoadonhenGioList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HoaDonHenGio hd = dataSnapshot.getValue(HoaDonHenGio.class);
                    if (hd == null) {
                        continue;
                    }
                    hd.setId(dataSnapshot.getKey());
                    hoadonhenGioList.add(hd);
                    activity.startService(new Intent(activity, UpdateService.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //hàm thêm user khi đăng kí
    public void AddUser(User user) {
        DatabaseReference myRef = database.getReference("Users");
        myRef.push().setValue(user);
    }

    public static void AddNotify(Notify notify) {
        DatabaseReference myRef = database.getReference();
        myRef.child("Notify").push().setValue(notify);
    }

    public static void AddHoaDonNap(Hoadonnaptien hoadonnaptien) {
        DatabaseReference myRef = database.getReference();
        myRef.child("HoaDonNapTien").push().setValue(hoadonnaptien);
    }

    public static void AddHoaDonHenGio(HoaDonHenGio hoaDonHenGio) {
        DatabaseReference myRef = database.getReference();
        myRef.child("HoaDonHenGio").push().setValue(hoaDonHenGio);
    }

    public static void HuyDatGio(HoaDonHenGio hoadon) {

        DatabaseReference hoadonRef = database.getReference().child("HoaDonHenGio").child(hoadon.getId());
        Map<String, Object> mapUpdateHoadon = new HashMap<>();
        mapUpdateHoadon.put("cancel", true);

        hoadonRef.updateChildren(mapUpdateHoadon, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
        DatabaseReference userRef = database.getReference().child("Users").child(UserLogin.getId());
        Map<String, Object> mapUpdateUser = new HashMap<>();
        mapUpdateUser.put("sodu", UserLogin.getSodu() + hoadon.getCost());
        userRef.updateChildren(mapUpdateUser);

    }

    //hàm cập nhạt lại user
    public void UpdateUser(User user1) {
        DatabaseReference myRef = database.getReference("Users").child(user1.getId());
        User user = user1;
        user.setAvatar(null);
        user.setId(null);
        myRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                UpdatedUser = true;
                LoadAvatarFromID();
            }
        });
    }

    //hàm đọc thông tin user
    public void ReadUser() {
        Log.d(TAG, "ReadUser: ");
        listUser = new ArrayList<>();
        DatabaseReference myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listUser.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    User u = dt.getValue(User.class);
                    if (u == null) {
                        continue;
                    }
                    u.setId(dt.getKey());
                    listUser.add(u);
                }
                LoadedUser = true;
                Log.d(TAG, "Đã nhận dữ liệu User");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.toString()
                );
            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////


}