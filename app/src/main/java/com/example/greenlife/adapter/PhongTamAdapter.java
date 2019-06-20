package com.example.greenlife.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greenlife.R;
import com.example.greenlife.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhongTamAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayPhongTam;

    public PhongTamAdapter(Context context, ArrayList<SanPham> arrayPhongTam) {
        this.context = context;
        this.arrayPhongTam = arrayPhongTam;
    }


    @Override
    public int getCount() {
        return arrayPhongTam.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayPhongTam.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        ImageView imgPhongTam;
        TextView txtTenPhongTam, txtGiaPhongTam, txtMoTaPhongTam;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_phong_tam,null);
            viewHolder.imgPhongTam    =(ImageView) convertView.findViewById(R.id.imageViewPhongTam);
            viewHolder.txtTenPhongTam       =(TextView) convertView.findViewById(R.id.textViewTenPhongTam);
            viewHolder.txtGiaPhongTam        =(TextView) convertView.findViewById(R.id.textViewGiaPhongTam);
            viewHolder.txtMoTaPhongTam       =(TextView) convertView.findViewById(R.id.textViewMoTaPhongTam);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.txtTenPhongTam.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaPhongTam.setText("Giá: "+decimalFormat.format(sanPham.getGiaSanPham())+" đồng");

        viewHolder.txtMoTaPhongTam.setText(sanPham.getMotaSanPham());
        viewHolder.txtMoTaPhongTam.setMaxLines(2);
        viewHolder.txtMoTaPhongTam.setEllipsize(TextUtils.TruncateAt.END);

        Picasso.with(context).load(sanPham.getHinhSanPham()).placeholder(R.drawable.load).error(R.drawable.error).into(viewHolder.imgPhongTam);

        return convertView;
    }
}
