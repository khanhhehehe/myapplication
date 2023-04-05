package com.example.myapplication.BroadcastReciver;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.myapplication.R;

public class ChannelTB extends Application {
    public static final String CHANNEL_ID ="CHANNEL_ID";
    public static final String CHANNEL_ID_2 ="CHANNEL_ID_2";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        //khai báo file trong Manifest
    }

    private void createNotificationChannel() {
        //check phiên bản để thông báo , từ 26 trở lên thì phải qua kênh , còn lại thì không ,nên dưới 26 vẫn có nhé
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //name 1 (Gửi thông báo khi ấn chơi)
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            //name 2 (Nhận thông báo khi hết giờ chơi )
            CharSequence name2 = getString(R.string.channel_name);
            String description2 = getString(R.string.channel_description);
            int importance2 = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID_2, name2, importance2);
            channel.setDescription(description2);

            //tạo thông báo
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.createNotificationChannel(channel2);
        }
    }
}
