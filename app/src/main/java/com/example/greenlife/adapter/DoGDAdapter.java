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

public class DoGDAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham>  arrayDoGD;

    public DoGDAdapter(Context context, ArrayList<SanPham> arrayDienThoai) {
        this.context = context;
        this.arrayDoGD = arrayDienThoai;
    }

    @Override
    public int getCount() {
        return arrayDoGD.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDoGD.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        ImageView imgDoGiaDung;
        TextView txtTenDGD, txtGiaDGD, txtMoTaDGD;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_do_gia_dung,null);
            viewHolder.imgDoGiaDung     =(ImageView) convertView.findViewById(R.id.imageViewDoGiaDung);
            viewHolder.txtTenDGD        =(TextView) convertView.findViewById(R.id.textViewTenDGD);
            viewHolder.txtGiaDGD        =(TextView) convertView.findViewById(R.id.textViewGiaDGG);
            viewHolder.txtMoTaDGD       =(TextView) convertView.findViewById(R.id.textViewMoTaDGD);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.txtTenDGD.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaDGD.setText("Giá: "+decimalFormat.format(sanPham.getGiaSanPham())+" đồng");

        viewHolder.txtMoTaDGD.setText(sanPham.getMotaSanPham());
        viewHolder.txtMoTaDGD.setMaxLines(2);
        viewHolder.txtMoTaDGD.setEllipsize(TextUtils.TruncateAt.END);

        Picasso.with(context).load(sanPham.getHinhSanPham()).placeholder(R.drawable.load).error(R.drawable.error).into(viewHolder.imgDoGiaDung);

        return convertView;
    }
}