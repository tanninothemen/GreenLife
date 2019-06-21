package com.example.greenlife.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greenlife.R;
import com.example.greenlife.adapter.ChamSocAdapter;
import com.example.greenlife.model.SanPham;
import com.example.greenlife.ultil.CheckDeviceInternet;
import com.example.greenlife.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChamSocActivity extends AppCompatActivity {
    Toolbar tlbChamsoc;
    ListView lvChamsoc;
    ChamSocAdapter chamSocAdapter;
    ArrayList<SanPham> arrayListChamsoc;
    int idChamsoc=0;
    int page=1;
    View footerview;
    boolean isLoading=false;
    boolean limitData=false;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_soc);
        
        AnhXa();
        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
            GetIdLoaiSP();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }
        else
            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối Internet của bạn.");
    }

    private void AnhXa() {
        tlbChamsoc=(Toolbar) findViewById(R.id.toolbarChamSoc);
        lvChamsoc=(ListView) findViewById(R.id.listviewChamSoc);
        arrayListChamsoc=new ArrayList<>();
        chamSocAdapter=new ChamSocAdapter(getApplicationContext(),arrayListChamsoc);
        lvChamsoc.setAdapter(chamSocAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview=inflater.inflate(R.layout.progressbar, null);
        handler=new Handler();
    }

    private void GetIdLoaiSP() {
        idChamsoc=getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisp",idChamsoc+ "");

    }

    private void ActionToolbar() {
        setSupportActionBar(tlbChamsoc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlbChamsoc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetData(int Page) {
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String linkSanPham= Server.LinkDoGiaDung+String.valueOf(page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, linkSanPham,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int maChamSoc=0;
                        String tenChamSoc="";
                        Integer giaChamSoc=0;
                        String hinhChamSoc="";
                        String motaChamSoc="";
                        int idChamSoc=0;
                        if (response!=null && response.length()!=2){
                            lvChamsoc.removeFooterView(footerview);
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    maChamSoc=jsonObject.getInt("maSP");
                                    tenChamSoc=jsonObject.getString("tenSP");
                                    giaChamSoc=jsonObject.getInt("giaSP");
                                    hinhChamSoc=jsonObject.getString("hinhSP");
                                    motaChamSoc=jsonObject.getString("motaSP");
                                    idChamSoc=jsonObject.getInt("idSP");
                                    arrayListChamsoc.add(new SanPham(maChamSoc,tenChamSoc,giaChamSoc,hinhChamSoc,motaChamSoc,idChamSoc));
                                    chamSocAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            limitData=true;
                            lvChamsoc.removeFooterView(footerview);
                            Toast.makeText(getApplicationContext(), "Đã hết dữ liệu", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String, String>();
                param.put("idsp",String.valueOf(idChamsoc));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LoadMoreData() {
        lvChamsoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ThongTinSanPhamActivity.class);
                intent.putExtra("thongtinsanpham",arrayListChamsoc.get(position));
                startActivity(intent);
            }
        });
        lvChamsoc.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount==totalItemCount && totalItemCount!=0 && isLoading==false && limitData==false){
                    isLoading=true;
                    ThreadData threadData=new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    public class Handler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    lvChamsoc.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading=false;
                    break;
            }
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            super.run();
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=handler.obtainMessage(1);
            handler.sendMessage(message);
        }
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
