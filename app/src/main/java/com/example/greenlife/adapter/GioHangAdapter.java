package com.example.greenlife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greenlife.R;
import com.example.greenlife.activity.GioHangActivity;
import com.example.greenlife.activity.MainActivity;
import com.example.greenlife.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> gioHangArrayList;

    public GioHangAdapter(Context context, ArrayList<GioHang> gioHangArrayList) {
        this.context = context;
        this.gioHangArrayList = gioHangArrayList;
    }

    @Override
    public int getCount() {
        return gioHangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHoder{
        public ImageView imgGioHang;
        public TextView txtTenGioHang, txtGiaGioHang;
        public Button btnDecrease, btnValues, btnIncrease;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder=null;
        if (convertView==null){
            viewHoder=new ViewHoder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_gio_hang,null);
            viewHoder.imgGioHang=(ImageView) convertView.findViewById(R.id.imageViewGioHang);
            viewHoder.txtTenGioHang=(TextView) convertView.findViewById(R.id.textViewTenGioHang);
            viewHoder.txtGiaGioHang=(TextView) convertView.findViewById(R.id.textViewGiaGioHang);
            viewHoder.btnDecrease=(Button) convertView.findViewById(R.id.buttonDecrease);
            viewHoder.btnValues=(Button) convertView.findViewById(R.id.buttonValues);
            viewHoder.btnIncrease=(Button) convertView.findViewById(R.id.buttonIncrease);
            convertView.setTag(viewHoder);
        }else {
            viewHoder= (ViewHoder) convertView.getTag();
        }
        GioHang gioHang= (GioHang) getItem(position);
        viewHoder.txtTenGioHang.setText(gioHang.getTenSP());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHoder.txtGiaGioHang.setText("Giá: "+decimalFormat.format(gioHang.getGiaSP())+" đồng");
        Picasso.with(context).load(gioHang.getHinhSP()).placeholder(R.drawable.load).
                error(R.drawable.error).into(viewHoder.imgGioHang);
        viewHoder.btnValues.setText(gioHang.getSoluongSP()+"");
        int sl= Integer.parseInt(viewHoder.btnValues.getText().toString());
        if (sl>=10){
            viewHoder.btnIncrease.setVisibility(View.INVISIBLE);
            viewHoder.btnDecrease.setVisibility(View.VISIBLE);
        }else if (sl<=1){
            viewHoder.btnDecrease.setVisibility(View.INVISIBLE);
        }else if (sl>=1){
            viewHoder.btnDecrease.setVisibility(View.VISIBLE);
            viewHoder.btnIncrease.setVisibility(View.VISIBLE);
        }
        final ViewHoder finalViewHoder = viewHoder;
        viewHoder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmoinhat=Integer.parseInt(finalViewHoder.btnValues.getText().toString())+1;
                int soluonghientai= MainActivity.gioHangArrayList.get(position).getSoluongSP();
                long giahientai=MainActivity.gioHangArrayList.get(position).getGiaSP();

                MainActivity.gioHangArrayList.get(position).setSoluongSP(soluongmoinhat);
                long giamoinhat=(giahientai*soluongmoinhat)/soluonghientai;
                MainActivity.gioHangArrayList.get(position).setGiaSP(giamoinhat);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                finalViewHoder.txtGiaGioHang.setText("Giá: "+decimalFormat.format(giamoinhat)+" đồng");
                GioHangActivity.EventUltils();
                if (soluongmoinhat>9){
                    finalViewHoder.btnIncrease.setVisibility(View.INVISIBLE);
                    finalViewHoder.btnDecrease.setVisibility(View.VISIBLE);
                    finalViewHoder.btnValues.setText(String.valueOf(soluongmoinhat));
                }else {
                    finalViewHoder.btnDecrease.setVisibility(View.VISIBLE);
                    finalViewHoder.btnIncrease.setVisibility(View.VISIBLE);
                    finalViewHoder.btnValues.setText(String.valueOf(soluongmoinhat));
                }
            }
        });
        viewHoder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmoinhat=Integer.parseInt(finalViewHoder.btnValues.getText().toString())-1;
                int soluonghientai= MainActivity.gioHangArrayList.get(position).getSoluongSP();
                long giahientai=MainActivity.gioHangArrayList.get(position).getGiaSP();

                MainActivity.gioHangArrayList.get(position).setSoluongSP(soluongmoinhat);
                long giamoinhat=(giahientai*soluongmoinhat)/soluonghientai;
                MainActivity.gioHangArrayList.get(position).setGiaSP(giamoinhat);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                finalViewHoder.txtGiaGioHang.setText("Giá: "+decimalFormat.format(giamoinhat)+" đồng");
                GioHangActivity.EventUltils();
                if (soluongmoinhat<2){
                    finalViewHoder.btnIncrease.setVisibility(View.  VISIBLE);
                    finalViewHoder.btnDecrease.setVisibility(View.INVISIBLE);
                    finalViewHoder.btnValues.setText(String.valueOf(soluongmoinhat));
                }else {
                    finalViewHoder.btnDecrease.setVisibility(View.VISIBLE);
                    finalViewHoder.btnIncrease.setVisibility(View.VISIBLE);
                    finalViewHoder.btnValues.setText(String.valueOf(soluongmoinhat));
                }
            }
        });
        return convertView;
    }
}
