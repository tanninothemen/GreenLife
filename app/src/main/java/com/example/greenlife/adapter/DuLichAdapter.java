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

public class DuLichAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayDuLich;

    public DuLichAdapter(Context context, ArrayList<SanPham> arrayDuLich) {
        this.context = context;
        this.arrayDuLich = arrayDuLich;
    }

    @Override
    public int getCount() {
        return arrayDuLich.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDuLich.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        ImageView imgDuLich;
        TextView txtTenDuLich, txtGiaDuLich, txtMoTaDuLich;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_du_lich,null);
            viewHolder.imgDuLich     =(ImageView) convertView.findViewById(R.id.imageViewDuLich);
            viewHolder.txtTenDuLich        =(TextView) convertView.findViewById(R.id.textViewTenDuLich);
            viewHolder.txtGiaDuLich       =(TextView) convertView.findViewById(R.id.textViewGiaDuLich);
            viewHolder.txtMoTaDuLich       =(TextView) convertView.findViewById(R.id.textViewMoTaDuLich);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.txtTenDuLich.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaDuLich.setText("Giá: "+decimalFormat.format(sanPham.getGiaSanPham())+" đồng");

        viewHolder.txtMoTaDuLich.setText(sanPham.getMotaSanPham());
        viewHolder.txtMoTaDuLich.setMaxLines(2);
        viewHolder.txtMoTaDuLich.setEllipsize(TextUtils.TruncateAt.END);

        Picasso.with(context).load(sanPham.getHinhSanPham()).placeholder(R.drawable.load).error(R.drawable.error).into(viewHolder.imgDuLich);

        return convertView;
    }
}
