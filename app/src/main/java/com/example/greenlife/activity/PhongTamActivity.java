package com.example.greenlife.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.greenlife.adapter.DoGDAdapter;
import com.example.greenlife.adapter.NhaBepAdapter;
import com.example.greenlife.adapter.PhongTamAdapter;
import com.example.greenlife.model.SanPham;
import com.example.greenlife.util.CheckDeviceInternet;
import com.example.greenlife.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhongTamActivity extends AppCompatActivity {
    Toolbar tlbPhongTam;
    ListView lvPhongTam;
    PhongTamAdapter phongTamAdapter;
    ArrayList<SanPham> arrayListPhongTam;
    int idPhongTam=0;
    int page=1;
    View footerview;
    boolean isLoading=false;
    boolean limitData=false;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_tam);

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
        tlbPhongTam=(Toolbar) findViewById(R.id.toolbarPhongTam);
        lvPhongTam=(ListView) findViewById(R.id.listviewPhongTam);
        arrayListPhongTam=new ArrayList<>();
        phongTamAdapter=new PhongTamAdapter(getApplicationContext(),arrayListPhongTam);
        lvPhongTam.setAdapter(phongTamAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview=inflater.inflate(R.layout.progressbar, null);
        handler=new Handler();
    }

    private void GetIdLoaiSP() {
        idPhongTam=getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisp",idPhongTam+ "");

    }

    private void ActionToolbar() {
        setSupportActionBar(tlbPhongTam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlbPhongTam.setNavigationOnClickListener(new View.OnClickListener() {
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
                        int maPhongTam=0;
                        String tenPhongTam="";
                        Integer giaPhongTam=0;
                        String hinhPhongTam="";
                        String motaPhongTam="";
                        int idPhongTam=0;
                        if (response!=null && response.length()!=2){
                            lvPhongTam.removeFooterView(footerview);
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    maPhongTam=jsonObject.getInt("maSP");
                                    tenPhongTam=jsonObject.getString("tenSP");
                                    giaPhongTam=jsonObject.getInt("giaSP");
                                    hinhPhongTam=jsonObject.getString("hinhSP");
                                    motaPhongTam=jsonObject.getString("motaSP");
                                    idPhongTam=jsonObject.getInt("idSP");
                                    arrayListPhongTam.add(new SanPham(maPhongTam,tenPhongTam,giaPhongTam,hinhPhongTam,motaPhongTam,idPhongTam));
                                    phongTamAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            limitData=true;
                            lvPhongTam.removeFooterView(footerview);
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
                param.put("idsp",String.valueOf(idPhongTam));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LoadMoreData() {
        lvPhongTam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ThongTinSanPhamActivity.class);
                intent.putExtra("thongtinsanpham",arrayListPhongTam.get(position));
                startActivity(intent);
            }
        });
        lvPhongTam.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                    lvPhongTam.addFooterView(footerview);
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
}
