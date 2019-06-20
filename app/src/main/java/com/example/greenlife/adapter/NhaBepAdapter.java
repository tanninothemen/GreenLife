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

public class NhaBepAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayNhaBep;

    public NhaBepAdapter(Context context, ArrayList<SanPham> arrayNhaBep) {
        this.context = context;
        this.arrayNhaBep = arrayNhaBep;
    }

    @Override
    public int getCount() {
        return arrayNhaBep.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayNhaBep.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        ImageView imgNhaBep;
        TextView txtTenNhaBep, txtGiaNhaBep, txtMoTaNhaBep;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_nha_bep,null);
            viewHolder.imgNhaBep     =(ImageView) convertView.findViewById(R.id.imageViewNhaBep);
            viewHolder.txtTenNhaBep        =(TextView) convertView.findViewById(R.id.textViewTenNhaBep);
            viewHolder.txtGiaNhaBep        =(TextView) convertView.findViewById(R.id.textViewGiaNhaBep);
            viewHolder.txtMoTaNhaBep      =(TextView) convertView.findViewById(R.id.textViewMoTaNhaBep);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.txtTenNhaBep.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaNhaBep.setText("Giá: "+decimalFormat.format(sanPham.getGiaSanPham())+" đồng");

        viewHolder.txtMoTaNhaBep.setText(sanPham.getMotaSanPham());
        viewHolder.txtMoTaNhaBep.setMaxLines(2);
        viewHolder.txtMoTaNhaBep.setEllipsize(TextUtils.TruncateAt.END);

        Picasso.with(context).load(sanPham.getHinhSanPham()).placeholder(R.drawable.load).error(R.drawable.error).into(viewHolder.imgNhaBep);

        return convertView;
    }
}
