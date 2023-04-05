package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Model.HoaDonHenGio;
import com.example.myapplication.Model.Hoadonchoigame;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewBookingHoler> {
    private final Context context;
    private List<HoaDonHenGio> list = new ArrayList<>();
    private final FirebaseStorage firebaseStorage;
    private final StorageReference avatatRef;

    public BookingHistoryAdapter(Context context) {
        this.context = context;
        firebaseStorage = FirebaseStorage.getInstance();
        avatatRef = firebaseStorage.getReference().child("avatar");

    }

    public void setList(List<HoaDonHenGio> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewBookingHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_history, parent, false);

        return new ViewBookingHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBookingHoler holder, int position) {
        HoaDonHenGio game = list.get(position);
        if (game == null) {
            return;
        }
        String TAG = "BookingHistoryAdapter";
        final long FIRE_MEGABYTE = 1024 * 1024 * 5;

        avatatRef.child(game.getUserId()).getBytes(FIRE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap avatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Log.d(TAG, "LoadAvatarFromID: loaded");
            if (avatar != null) {
                holder.img_AvatarUser.setImageBitmap(avatar);
            }
        }).addOnFailureListener(exception -> {
            Log.e(TAG, "onFailure:Loadavatar ", null);

        });
        String ten = FbDao.getNameUserFromID(game.getUserId());
        holder.name.setText("Tên khách hàng: " + ten);
        holder.dateStart.setText("Từ: " + game.getTimeStart());
        holder.dateEnd.setText("Đến: " + game.getTimeEnd());
    }

    private void Loadavatar() {

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ViewBookingHoler extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView dateStart;
        private final TextView dateEnd;
        private final ImageView img_AvatarUser;

        public ViewBookingHoler(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_user_booking);
            dateStart = itemView.findViewById(R.id.tv_date_start);
            img_AvatarUser = itemView.findViewById(R.id.img_AvatarUser);
            dateEnd = itemView.findViewById(R.id.tv_date_end);
        }
    }

}
