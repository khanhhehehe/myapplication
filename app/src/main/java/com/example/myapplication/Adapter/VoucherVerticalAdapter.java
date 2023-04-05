package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Model.Game;
import com.example.myapplication.Model.Voucher;
import com.example.myapplication.R;
import com.example.myapplication.Interface.OnclickItemVoucher;

import java.util.List;

public class VoucherVerticalAdapter extends RecyclerView.Adapter<VoucherVerticalAdapter.VoucherViewHoler> {
    private List<Voucher> listDanhSachVoucher;
    private List<Game> listGame;
    private Context context;
    private OnclickItemVoucher onclickItemVoucher;

    public VoucherVerticalAdapter(OnclickItemVoucher onclickItemVoucher) {
        this.onclickItemVoucher = onclickItemVoucher;
    }

    public VoucherVerticalAdapter() {
    }
    public String getTenGame(int id){
        String tenGame = "% cho mọi loại Game";
        listGame = FbDao.getListGame();
        for(Game game : listGame){
            if(game.getId() == id){
                tenGame = "% cho Game " + game.getTenGame();
            }
        }
        return tenGame;
    }
    public VoucherVerticalAdapter(Context context) {
        this.context = context;
    }

    public void setListDanhSachVoucher(List<Voucher> listDanhSachVoucher) {
        this.listDanhSachVoucher = listDanhSachVoucher;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_vertical,parent,false);
        return new VoucherViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHoler holder, int position) {
        Voucher voucher = listDanhSachVoucher.get(position);
        if(voucher == null){
            return;
        }
        holder.tv_MaVoucher.setText(voucher.getMaVoucher());
        holder.tv_TieuDeVoucher.setText("Giảm " + voucher.getGiamGia() + getTenGame(voucher.getLoaiGame()));
        holder.linearLayout_voucher.setOnClickListener(v -> {
            onclickItemVoucher.onclickItemVoucher(voucher);
        });

    }

    @Override
    public int getItemCount() {
        if(listDanhSachVoucher!= null){
            return listDanhSachVoucher.size();
        }
        return 0;
    }

    public class VoucherViewHoler extends RecyclerView.ViewHolder {
        private final ImageView imageView2;
        private final TextView tv_MaVoucher;
        private final TextView tv_TieuDeVoucher;
        private final LinearLayout linearLayout_voucher;
        public VoucherViewHoler(@NonNull View itemView) {
            super(itemView);
            imageView2 = itemView.findViewById(R.id.imageView2);
            tv_TieuDeVoucher = itemView.findViewById(R.id.tv_tieuDeVoucher);
            tv_MaVoucher = itemView.findViewById(R.id.tv_maVoucher);
            linearLayout_voucher = itemView.findViewById(R.id.linear_voucher);
        }
    }
}
