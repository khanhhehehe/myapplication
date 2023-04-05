package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Model.Game;
import com.example.myapplication.Model.Voucher;
import com.example.myapplication.R;
import com.example.myapplication.Interface.OnclickItemGame;

import java.util.ArrayList;
import java.util.List;

public class GameUuDaiVerticalAdapter extends RecyclerView.Adapter<GameUuDaiVerticalAdapter.VoucherViewHoler> {
    private List<Game> listGame;
    private Context context;
    private OnclickItemGame onclickItemGame;
    public GameUuDaiVerticalAdapter() {
    }

    public GameUuDaiVerticalAdapter(Context context, OnclickItemGame onclickItemGame) {
        this.context = context;
        this.onclickItemGame = onclickItemGame;
    }

    public void setListDanhSachGame(List<Game> listGame) {
        this.listGame = listGame;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_vertical,parent,false);
        return new VoucherViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHoler holder, int position) {
        Game game = listGame.get(position);
        if(game == null){
            return;
        }
        holder.tvTenGame.setText(game.getTenGame());
        holder.linearLayoutItemgame.setOnClickListener(view -> {
            onclickItemGame.onclickItemGame(game);
        });
        holder.imageView2.setImageResource(game.getImgGame());
        List<Voucher> listVoucherGameName = new ArrayList<>();
        List<Voucher> voucherList = FbDao.getListVoucher();
        for (Voucher voucher : voucherList) {
            if (voucher.getLoaiGame() == game.getId() || voucher.getLoaiGame() == 0) {
                listVoucherGameName.add(voucher);
            }
        }
        holder.linearLayoutItemgame.startAnimation(AnimationUtils.loadAnimation(holder.linearLayoutItemgame.getContext(),R.anim.anim_item_game_vertical));
        holder.tv_soLuongUuDai.setText(listVoucherGameName.size()+" ưu đãi");
    }

    @Override
    public int getItemCount() {
        if(listGame!= null){
            return listGame.size();
        }
        return 0;
    }

    public class VoucherViewHoler extends RecyclerView.ViewHolder {
        private final ImageView imageView2;
        private final TextView tvTenGame;
        private final LinearLayout linearLayoutItemgame;
        private final TextView tv_soLuongUuDai;
        public VoucherViewHoler(@NonNull View itemView) {
            super(itemView);
            imageView2 = itemView.findViewById(R.id.imageView2);
            tvTenGame = itemView.findViewById(R.id.tv_tenGame);
            linearLayoutItemgame = itemView.findViewById(R.id.linear_item_game);
            tv_soLuongUuDai = itemView.findViewById(R.id.tv_soLuongUuDai);
        }
    }
}
