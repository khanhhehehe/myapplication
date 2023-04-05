package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Fragment.fragmentMainChild.fragment_Uudai;
import com.example.myapplication.Interface.OnClickUseNow;
import com.example.myapplication.Model.Game;
import com.example.myapplication.Model.Voucher;
import com.example.myapplication.R;

import java.util.List;

public class VoucherVerticalAdapter2 extends RecyclerView.Adapter<VoucherVerticalAdapter2.VoucherViewHoler2> {
    private List<Voucher> listDanhSachVoucher;
    private List<Game> listGame;
    private Context context;
    private final fragment_Uudai fragment_uudai = new fragment_Uudai();
    private OnClickUseNow onClickUseNow;

    public OnClickUseNow getOnClickUseNow() {
        return onClickUseNow;
    }

    public void setOnClickUseNow(OnClickUseNow onClickUseNow) {
        this.onClickUseNow = onClickUseNow;
    }

    public VoucherVerticalAdapter2() {
    }

    public String getTenGame(int id) {
        String tenGame = "% cho mọi loại Game";
        listGame = FbDao.getListGame();
        for (Game game : listGame) {
            if (game.getId() == id) {
                tenGame = "% cho Game " + game.getTenGame();
            }
        }
        return tenGame;
    }

    public VoucherVerticalAdapter2(Context context) {
        this.context = context;
    }

    public void setListDanhSachVoucher(List<Voucher> listDanhSachVoucher) {
        this.listDanhSachVoucher = listDanhSachVoucher;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherViewHoler2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_vertical2, parent, false);
        return new VoucherViewHoler2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHoler2 holder, int position) {
        Voucher voucher = listDanhSachVoucher.get(position);
        if (voucher == null) {
            return;
        }
        holder.tv_MaVoucher.setText(voucher.getMaVoucher());
        holder.tv_TieuDeVoucher.setText("Giảm " + voucher.getGiamGia() + getTenGame(voucher.getLoaiGame()));
        holder.tv_useNow.setOnClickListener(v -> {
            onClickUseNow.onclickItemVoucher();
        });
    }

    @Override
    public int getItemCount() {
        if (listDanhSachVoucher != null) {
            return listDanhSachVoucher.size();
        }
        return 0;
    }

    public class VoucherViewHoler2 extends RecyclerView.ViewHolder {

        private final ImageView imageView2;
        private final TextView tv_MaVoucher;
        private final TextView tv_TieuDeVoucher;
        private final LinearLayout linearLayout_voucher;
        private final TextView tv_useNow;
        public VoucherViewHoler2(@NonNull View itemView) {
            super(itemView);
            imageView2 = itemView.findViewById(R.id.imageView2);
            tv_TieuDeVoucher = itemView.findViewById(R.id.tv_tieuDeVoucher);
            tv_MaVoucher = itemView.findViewById(R.id.tv_maVoucher);
            linearLayout_voucher = itemView.findViewById(R.id.linear_voucher);
            tv_useNow = itemView.findViewById(R.id.tv_useNow);
        }
    }
}
