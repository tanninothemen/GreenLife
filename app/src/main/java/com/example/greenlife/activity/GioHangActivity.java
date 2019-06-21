package com.example.greenlife.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.greenlife.R;
import com.example.greenlife.adapter.GioHangAdapter;
import com.example.greenlife.ultil.CheckDeviceInternet;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.greenlife.activity.MainActivity.gioHangArrayList;

public class GioHangActivity extends AppCompatActivity {

    ListView lvGioHang;
    TextView txtThongBaoGioHang;
    static TextView txtTongTien;
    Button btnThanhToan, btnTiepTucMuaHang;
    Toolbar toolbarGioHang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        AnhXa();
        ActionToolBar();
        CheckData();
        EventUltils();
        DeleteOnItemListView();
        EvenButton();
    }

    private void EvenButton() {
        btnTiepTucMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.gioHangArrayList.size()>0){
                    Intent intent=new Intent(getApplicationContext(),ThongTinKHActivity.class);
                    startActivity(intent);

                }else {
                    CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Giỏ hàng của bạn hiện chưa có sản phẩm");
                }
            }
        });
    }

    private void DeleteOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xóa Sản Phẩm");
                builder.setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.gioHangArrayList.size()<=0){
                            txtThongBaoGioHang.setVisibility(View.VISIBLE);

                        }else {
                            MainActivity.gioHangArrayList.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUltils();
                            if (MainActivity.gioHangArrayList.size()<=0){
                                txtThongBaoGioHang.setVisibility(View.VISIBLE);
                            }else {
                                txtThongBaoGioHang.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUltils();
                            }
                        }
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUltils();

                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltils() {
        long tongtien=0;
        for (int i = 0; i< gioHangArrayList.size(); i++){
            tongtien+= gioHangArrayList.get(i).getGiaSP();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien)+" đồng");
    }

    private void CheckData() {
        if (gioHangArrayList.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongBaoGioHang.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        }
        else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBaoGioHang.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        lvGioHang=(ListView) findViewById(R.id.listViewGioHang);
        txtThongBaoGioHang=(TextView) findViewById(R.id.textViewThongBaoGioHangTrong);
        txtTongTien=(TextView) findViewById(R.id.textViewTongTien);
        btnThanhToan=(Button) findViewById(R.id.buttonThanhToanGioHang);
        btnTiepTucMuaHang=(Button) findViewById(R.id.buttonTiepTucGMuaHang);
        toolbarGioHang=(Toolbar) findViewById(R.id.toolbarGioHang);
        gioHangAdapter=new GioHangAdapter(GioHangActivity.this, gioHangArrayList);
        lvGioHang.setAdapter(gioHangAdapter);
    }
}
