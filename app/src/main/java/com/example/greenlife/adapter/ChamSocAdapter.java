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

public class ChamSocAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayChamSoc;

    public ChamSocAdapter(Context context, ArrayList<SanPham> arrayChamSoc) {
        this.context = context;
        this.arrayChamSoc = arrayChamSoc;
    }

    @Override
    public int getCount() {
        return arrayChamSoc.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayChamSoc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        ImageView imgChamSoc;
        TextView txtTenChamSoc, txtGiaChamSoc, txtMoTaChamSoc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_cham_soc,null);
            viewHolder.imgChamSoc     =(ImageView) convertView.findViewById(R.id.imageViewChamSoc);
            viewHolder.txtTenChamSoc        =(TextView) convertView.findViewById(R.id.textViewTenChamSoc);
            viewHolder.txtGiaChamSoc        =(TextView) convertView.findViewById(R.id.textViewGiaChamSoc);
            viewHolder.txtMoTaChamSoc       =(TextView) convertView.findViewById(R.id.textViewMoTaChamSoc);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.txtTenChamSoc.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaChamSoc.setText("Giá: "+decimalFormat.format(sanPham.getGiaSanPham())+" đồng");

        viewHolder.txtMoTaChamSoc.setText(sanPham.getMotaSanPham());
        viewHolder.txtMoTaChamSoc.setMaxLines(2);
        viewHolder.txtMoTaChamSoc.setEllipsize(TextUtils.TruncateAt.END);

        Picasso.with(context).load(sanPham.getHinhSanPham()).placeholder(R.drawable.load).error(R.drawable.error).into(viewHolder.imgChamSoc);

        return convertView;
    }
}
