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

public class TreEmAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayTreEm;

    public TreEmAdapter(Context context, ArrayList<SanPham> arrayTreEm) {
        this.context = context;
        this.arrayTreEm = arrayTreEm;
    }

    @Override
    public int getCount() {
        return arrayTreEm.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayTreEm.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        ImageView imgTreEm;
        TextView txtTenTreEm, txtGiaTreEm, txtMoTaTreEm;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_tre_em,null);
            viewHolder.imgTreEm     =(ImageView) convertView.findViewById(R.id.imageViewTreEm);
            viewHolder.txtTenTreEm      =(TextView) convertView.findViewById(R.id.textViewTenTreEm);
            viewHolder.txtGiaTreEm       =(TextView) convertView.findViewById(R.id.textViewGiaTreEm);
            viewHolder.txtMoTaTreEm      =(TextView) convertView.findViewById(R.id.textViewMoTaTreEm);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.txtTenTreEm.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaTreEm.setText("Giá: "+decimalFormat.format(sanPham.getGiaSanPham())+" đồng");

        viewHolder.txtMoTaTreEm.setText(sanPham.getMotaSanPham());
        viewHolder.txtMoTaTreEm.setMaxLines(2);
        viewHolder.txtMoTaTreEm.setEllipsize(TextUtils.TruncateAt.END);

        Picasso.with(context).load(sanPham.getHinhSanPham()).placeholder(R.drawable.load).error(R.drawable.error).into(viewHolder.imgTreEm);

        return convertView;
    }
}
