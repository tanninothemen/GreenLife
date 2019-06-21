package com.example.greenlife.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.greenlife.R;
import com.example.greenlife.model.GioHang;
import com.example.greenlife.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ThongTinSanPhamActivity extends AppCompatActivity {

    Toolbar toolbarChiTiet;
    ImageView imgChiTiet;
    TextView txtTenChiTiet, txtGiaChiTiet, txtMoTaChiTiet;
    Spinner spinner;
    Button btnDatMua;
    int maChiTiet=0;
    String tenChiTiet="";
    int giaChiTiet=0;
    String hinhChiTiet="";
    String moTaChiTIet="";
    int idSanPham=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_san_pham);
        AnhXa();
        ActionToolBar();
        GetInfomation();
        CatchEvenSpinner();
        EventButton();

    }

    private void EventButton() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if (MainActivity.gioHangArrayList.size()>0){
                    int sl=Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists=false;
                    for (int i=0;i<MainActivity.gioHangArrayList.size();i++){
                        if (MainActivity.gioHangArrayList.get(i).getMaSP()==maChiTiet){
                            MainActivity.gioHangArrayList.get(i).setSoluongSP(MainActivity.gioHangArrayList.get(i).getSoluongSP()+sl);
                            if (MainActivity.gioHangArrayList.get(i).getSoluongSP()>=10){
                                MainActivity.gioHangArrayList.get(i).setSoluongSP(10);
                            }
                            MainActivity.gioHangArrayList.get(i).setGiaSP(giaChiTiet*MainActivity.gioHangArrayList.get(i).getSoluongSP());
                            exists =true;
                        }

                    }
                    if (exists==false){
                        int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi=soluong*giaChiTiet;
                        MainActivity.gioHangArrayList.add(new GioHang(maChiTiet,tenChiTiet,giamoi,hinhChiTiet,soluong));
                    }
                }else {
                    int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi=soluong*giaChiTiet;
                     MainActivity.gioHangArrayList.add(new GioHang(maChiTiet,tenChiTiet,giamoi,hinhChiTiet,soluong));
                }
                Intent intent=new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }

        });
    }

    private void CatchEvenSpinner() {
        Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInfomation() {


        SanPham sanPham= (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        maChiTiet=sanPham.getId();
        tenChiTiet=sanPham.getTenSanPham();
        giaChiTiet=sanPham.getGiaSanPham();
        hinhChiTiet=sanPham.getHinhSanPham();
        moTaChiTIet=sanPham.getMotaSanPham();
        idSanPham=sanPham.getIdSanPham();

        txtTenChiTiet.setText(tenChiTiet);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtGiaChiTiet.setText("Giá: "+decimalFormat.format(sanPham.giaSanPham)+" đồng");
        txtMoTaChiTiet.setText(moTaChiTIet);
        Picasso.with(getApplicationContext()).load(hinhChiTiet).placeholder(R.drawable.load).
                error(R.drawable.error).into(imgChiTiet);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarChiTiet=(Toolbar) findViewById(R.id.toolbarChiTietSanPham);
        imgChiTiet=(ImageView) findViewById(R.id.imageViewChiTietSanPham);
        txtTenChiTiet=(TextView) findViewById(R.id.textTenChiTietSanPham);
        txtGiaChiTiet=(TextView) findViewById(R.id.textGiaChiTietSanPham);
        txtMoTaChiTiet=(TextView) findViewById(R.id.textMoTaChiTietSanPham);
        spinner=(Spinner) findViewById(R.id.spinner);
        btnDatMua=(Button) findViewById(R.id.buttonDatMua);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
