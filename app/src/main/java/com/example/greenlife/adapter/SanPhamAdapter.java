package com.example.greenlife.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greenlife.R;
import com.example.greenlife.activity.ThongTinSanPhamActivity;
import com.example.greenlife.model.SanPham;
import com.example.greenlife.ultil.CheckDeviceInternet;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

//bản vẽ đặc trưng của RecycleView
public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham> arraySP;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arraySP) {
        this.context = context;
        this.arraySP = arraySP;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_sanphammoi,null);
        ItemHolder itemHolder=new ItemHolder(view);

        return itemHolder;
    }

    //thuộc tính reset và get những thuộc tính gán lên layout
    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        SanPham sanPham=arraySP.get(i);
        itemHolder.txtTenSanPham.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        itemHolder.txtGiaSanPham.setText("Giá: "+decimalFormat.format(sanPham.getGiaSanPham())+" đồng");
        Picasso.with(context).load(sanPham.getHinhSanPham()).placeholder(R.drawable.load).error(R.drawable.error).into(itemHolder.imgHinhSanPham);
    }

    @Override
    public int getItemCount() {
        return arraySP.size()   ;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhSanPham;
        public TextView txtTenSanPham, txtGiaSanPham;

        public ItemHolder(@NonNull final View itemView) {
            super(itemView);
            imgHinhSanPham=(ImageView) itemView.findViewById(R.id.imageViewSPMoi);
            txtTenSanPham=(TextView) itemView.findViewById(R.id.textViewSanPhamMoi);
            txtGiaSanPham=(TextView) itemView.findViewById(R.id.textViewGiaSPMoi);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ThongTinSanPhamActivity.class);
                    intent.putExtra("thongtinsanpham",arraySP.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckDeviceInternet.ShowNotify_Short(context,arraySP.get(getPosition()).tenSanPham);
                    context.startActivity(intent);
                }
            });
        }
    }
}
