package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Notify;
import com.example.myapplication.R;

import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.NotifyViewHoler> {
    private final List<Notify> list;

    public NotifyAdapter(List<Notify> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NotifyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify,
                parent, false);
        return new NotifyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewHoler holder, int position) {
        Notify notify = list.get(position);
        if (notify != null) {
            holder.tvTiltle.setText(notify.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class NotifyViewHoler extends RecyclerView.ViewHolder {
        private final LinearLayout linearDanhSachGame;
        private final TextView tvTiltle;

        public NotifyViewHoler(@NonNull View itemView) {
            super(itemView);
            linearDanhSachGame = itemView.findViewById(R.id.linear_danh_sach_game);
            tvTiltle = itemView.findViewById(R.id.tv_tiltle);
        }
    }
}
