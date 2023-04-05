package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.PlayTime;
import com.example.myapplication.R;
import com.example.myapplication.Interface.OnclickItemTime;

import java.util.List;

public class ListThoiGianAdapter extends RecyclerView.Adapter<ListThoiGianAdapter.ThoiGianViewHoler> {
    private Context context;
    private List<PlayTime> listplayTimes;
    private int choose_item = -1;
    private OnclickItemTime onclickItemTime;

    public ListThoiGianAdapter(OnclickItemTime onclickItemTime) {
        this.onclickItemTime = onclickItemTime;
    }
    public ListThoiGianAdapter() {
    }
    public ListThoiGianAdapter(Context context) {
        this.context = context;
    }

    public void setListThoiGian(List<PlayTime> listplayTimes) {
        this.listplayTimes = listplayTimes;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ThoiGianViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thoigianchoi,parent,false);
        return new ThoiGianViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThoiGianViewHoler holder, int position) {
        PlayTime playTime = listplayTimes.get(position);
        if(playTime == null){
            return;
        }
        holder.img_thoiGian.setImageResource(playTime.getImg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_item=holder.getAdapterPosition();
                onclickItemTime.onclickItemTime(playTime);
                notifyDataSetChanged();
            }
        });
        if(choose_item==holder.getAdapterPosition()){
            holder.itemView.setBackgroundColor(Color.parseColor("#BBDEFB"));
        }else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        if(listplayTimes!= null){
            return listplayTimes.size();
        }
        return 0;
    }

    public class ThoiGianViewHoler extends RecyclerView.ViewHolder {
        private final ImageView img_thoiGian;
        public ThoiGianViewHoler(@NonNull View itemView) {
            super(itemView);
            img_thoiGian = itemView.findViewById(R.id.img_thoiGian);
        }
    }
}
