package com.example.myapplication.BroadcastReciver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Model.Game;
import com.example.myapplication.R;

import java.util.Date;

public class ThongBao extends BroadcastReceiver {
    //lớp tiếp nhận thông báo
    @Override
    public void onReceive(Context context, Intent intent) {
        Game game = (Game) intent.getSerializableExtra("game");
        String time = intent.getStringExtra("time");
        String action = intent.getAction();
        switch (action){
            case "MyAction":
                Bitmap bitmap = (game==null)?BitmapFactory.decodeResource(context.getResources(),R.drawable.loading):BitmapFactory.decodeResource(context.getResources(),game.getImgGame());
                Bitmap imgApp = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo2);
                Notification notification = new NotificationCompat.Builder(context, ChannelTB.CHANNEL_ID) // khai báo compat
                        .setLargeIcon(imgApp)
                        .setContentTitle("Hết giờ rùi : "+time+"")
                        .setContentText("Hy vọng bạn thích trò chơi của chúng tôi !")
                        .setSmallIcon(R.drawable.logo2)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                        .build();
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(getTimeLocal(), notification);
                break;
            case "MyAction2":
                Bundle bundle = intent.getExtras();
                int turn = bundle.getInt("turn");
                float total = bundle.getFloat("total");
                Game game1 = (Game) bundle.getSerializable("GameTurn");
                FbDao fbDao = new FbDao();
                fbDao.PlaygameGio(turn,game1.getId() + "",total);
                break;
            case "MyAction3":
                Bundle bundle2 = intent.getExtras();
                int time2 = bundle2.getInt("time2");
                float total2 = bundle2.getFloat("total2");
                Game game2 = (Game) bundle2.getSerializable("GameTime");
                FbDao fbDao2 = new FbDao();
                fbDao2.PlaygameGio(time2,game2.getId() + "",total2);
                break;
        }

    }

    private int getTimeLocal() {
        return (int) new Date().getTime();
    }
}
