package com.example.myapplication.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Game;
import com.example.myapplication.R;
import com.example.myapplication.Interface.OnclickItemGame;

import java.util.List;

public class DanhSachGameAdapter extends RecyclerView.Adapter<DanhSachGameAdapter.DanhSachGameViewHoler> {
    private List<Game> listGame;
    private OnclickItemGame onclickItemGame;


    public DanhSachGameAdapter(OnclickItemGame onclickItemGame) {
        this.onclickItemGame = onclickItemGame;
    }

    public DanhSachGameAdapter() {
    }

    public void setListGame(List<Game> listGame) {
        this.listGame = listGame;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DanhSachGameViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_sach_tro_choi, parent, false);
        return new DanhSachGameViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachGameViewHoler holder, int position) {
        Game game = listGame.get(position);
        if (game == null) {
            return;
        }
        holder.tvTenGame.setText(game.getTenGame());
        holder.tvTrangThai.setText(game.getTrangThai());

        holder.img_AvatarGame.setImageResource(game.getImgGame());
        if (game.getTrangThai().equalsIgnoreCase("Bảo trì")) {
            holder.tvTrangThai.setTextColor(Color.parseColor("#E04119"));

        } else if (game.getTrangThai().equalsIgnoreCase("Đang được chơi")) {

            holder.tvTrangThai.setTextColor(Color.parseColor("#FFE15D"));


        } else {
            holder.tvTrangThai.setTextColor(Color.parseColor("#2FC863"));
        }
        holder.linearLayoutDanhSachGame.setOnClickListener(view -> {
            onclickItemGame.onclickItemGame(game);
        });

    }

    @Override
    public int getItemCount() {
        if (listGame != null) {
            return listGame.size();
        }
        return 0;
    }


    public class DanhSachGameViewHoler extends RecyclerView.ViewHolder {
        private final TextView tvTenGame;
        private final ImageView img_AvatarGame;
        private final TextView tvTrangThai;
        private final LinearLayout linearLayoutDanhSachGame;

        public DanhSachGameViewHoler(@NonNull View itemView) {
            super(itemView);
            img_AvatarGame = itemView.findViewById(R.id.img_AvatarGame);
            tvTenGame = itemView.findViewById(R.id.tv_ten_game);
            tvTrangThai = itemView.findViewById(R.id.tv_trang_thai);
            linearLayoutDanhSachGame = itemView.findViewById(R.id.linear_danh_sach_game);
        }
    }
}
