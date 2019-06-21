package com.example.greenlife.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greenlife.R;
import com.example.greenlife.ultil.CheckDeviceInternet;
import com.example.greenlife.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKHActivity extends AppCompatActivity {

    EditText edtTenKH, edtEmailKH, edtSDTKH;
    Button btnXacNhan, btnQuayLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_kh);

        AnhXa();

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else {
            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối internet");
        }

    }

    private void EventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tenKH=edtTenKH.getText().toString().trim();
                final String sdtKH=edtSDTKH.getText().toString().trim();
                final String emailKH=edtEmailKH.getText().toString().trim();
                if (tenKH.length()!=0 && sdtKH.length()!=0 && emailKH.length()!=0){
                    final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkDonHang,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(final String maDonHang) {
                                    Log.d("MaDonHang",maDonHang);
                                    if (Integer.parseInt(maDonHang)>0){
                                        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                                        StringRequest request=new StringRequest(Request.Method.POST, Server.LinkChiTietDonHang,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.equals("1")){
                                                            MainActivity.gioHangArrayList.clear();
                                                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Bạn đã đặt mua và thanh toán thành công");
                                                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                                            startActivity(intent);
                                                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Bạn có thể mua tiếp những sản phẩm tiếp theo");
                                                        }else {
                                                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Dữ liệu giỏ hàng bị lỗi");
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
                                                JSONArray jsonArray=new JSONArray();
                                                for (int i=0;i<MainActivity.gioHangArrayList.size();i++){
                                                    JSONObject jsonObject=new JSONObject();
                                                    try {
                                                        jsonObject.put("MaDonHang", maDonHang);
                                                        jsonObject.put("MaSanPham", MainActivity.gioHangArrayList.get(i).getMaSP());
                                                        jsonObject.put("TenSanPham", MainActivity.gioHangArrayList.get(i).getTenSP());
                                                        jsonObject.put("GiaSanPham", MainActivity.gioHangArrayList.get(i).getGiaSP());
                                                        jsonObject.put("SoLuongSanPham", MainActivity.gioHangArrayList.get(i).getSoluongSP());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    jsonArray.put(jsonObject);
                                                }
                                                HashMap<String,String> hashMap=new HashMap<>();
                                                hashMap.put("json",jsonArray.toString());
                                                return hashMap;
                                            }
                                        };
                                        queue.add(request);
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
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("tenkhachhang", tenKH);
                            hashMap.put("sodienthoai", sdtKH);
                            hashMap.put("emailkhachhang", emailKH);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else{
                    CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Bạn chưa nhập đầy đủ thông tin");
                }
            }
        });
    }

    private void AnhXa() {
        edtEmailKH=(EditText) findViewById(R.id.editTextEmailKhachHang);
        edtSDTKH=(EditText) findViewById(R.id.editTextSDTKhachHang);
        edtTenKH=(EditText) findViewById(R.id.editTextTenKhachHang);
        btnXacNhan=(Button) findViewById(R.id.buttonXacNhan);
        btnQuayLai=(Button) findViewById(R.id.buttonQuayLai);
    }
}
