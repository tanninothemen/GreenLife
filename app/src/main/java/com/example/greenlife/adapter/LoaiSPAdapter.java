package com.example.greenlife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greenlife.R;
import com.example.greenlife.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSPAdapter extends BaseAdapter {

    ArrayList<LoaiSP> arrayLoaiSP;
    Context context;

    public LoaiSPAdapter(ArrayList<LoaiSP> arrayLoaiSP, Context context) {
        this.arrayLoaiSP = arrayLoaiSP;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayLoaiSP.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayLoaiSP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txtTenLoaiSP;
        ImageView imgHinhLoaiSP;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            //Hàm layoutInflater lâý được service lấy được customlayout
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.imgHinhLoaiSP=(ImageView) convertView.findViewById(R.id.imageViewLoaiSP);
            viewHolder.txtTenLoaiSP=(TextView) convertView.findViewById(R.id.textViewLoaiSP);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        LoaiSP loaiSP= (LoaiSP) getItem(position);
        viewHolder.txtTenLoaiSP.setText(loaiSP.getTenLoaiSP());
        Picasso.with(context).load(loaiSP.getHinhAnhLoaiSP()).placeholder(R.drawable.load)
                .error(R.drawable.error).into(viewHolder.imgHinhLoaiSP);
        return convertView;
    }
}
