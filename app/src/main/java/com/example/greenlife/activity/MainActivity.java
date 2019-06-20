package com.example.greenlife.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.greenlife.R;
import com.example.greenlife.adapter.LoaiSPAdapter;
import com.example.greenlife.adapter.SanPhamAdapter;
import com.example.greenlife.model.LoaiSP;
import com.example.greenlife.model.SanPham;
import com.example.greenlife.util.CheckDeviceInternet;
import com.example.greenlife.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Khai báo biến toàn cục
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayoutHome;
    ArrayList<LoaiSP> listLoaiSP;
    LoaiSPAdapter loaiSPAdapter;
    int maLoai=0;
    String tenLoai="";
    String hinhAnhLoaiSp="";
    ArrayList<SanPham> listSanPhamMoi;
    SanPhamAdapter sanPhamMoiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();

        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
            ActionBarHome();
            ActionViewFliperHome();
            GetDuLieuLoaiSP();
            GetSanPhamMoiNhat();
            GetSanPhamTheoLoai();
        }
        else {
            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối internet của bạn");
            finish();
        }


    }

    //gán những giá trị trong thanh menu bên trái
    private void GetSanPhamTheoLoai() {
        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
                            Intent intentHome=new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intentHome);
                        }else {
                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối internet của bạn");
                        }
                        //đóng lại thanh menu bên trái
                        drawerLayoutHome.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
                            Intent intentDoGD=new Intent(MainActivity.this, DoGDActivity.class);
                            intentDoGD.putExtra("idloaisanpham",listLoaiSP.get(position).getId());
                            startActivity(intentDoGD);
                        }else {
                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối internet của bạn");
                        }
                        break;
                    case 2:
                        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
                            Intent intentNhaBep=new Intent(MainActivity.this, NhaBepActivity.class);
                            intentNhaBep.putExtra("idloaisanpham",listLoaiSP.get(position).getId());
                            startActivity(intentNhaBep);
                        }else {
                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối internet của bạn");
                        }
                        break;
                    case 3:
                        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
                            Intent intentPhongTam=new Intent(MainActivity.this, PhongTamActivity.class);
                            intentPhongTam.putExtra("idloaisanpham",listLoaiSP.get(position).getId());
                            startActivity(intentPhongTam);
                        }else {
                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối internet của bạn");
                        }
                        break;
                    case 4:
                        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
                            Intent intentDuLich=new Intent(MainActivity.this, DuLichActivity.class);
                            intentDuLich.putExtra("idloaisanpham",listLoaiSP.get(position).getId());
                            startActivity(intentDuLich);
                        }else {
                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối internet của bạn");
                        }
                        break;
                    case 5:
                        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
                            Intent intentChamSoc=new Intent(MainActivity.this, ChamSocActivity.class);
                            intentChamSoc.putExtra("idloaisanpham",listLoaiSP.get(position).getId());
                            startActivity(intentChamSoc);
                        }else {
                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối internet của bạn");
                        }
                        break;
                    case 6:
                        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
                            Intent intentTreEm=new Intent(MainActivity.this, TreEmActivity.class);
                            intentTreEm.putExtra("idloaisanpham",listLoaiSP.get(position).getId());
                            startActivity(intentTreEm);
                        }else {
                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối internet của bạn");
                        }
                        break;
                    case 7:
                        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
                            Intent intentLienHe=new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intentLienHe);
                        }else {
                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối internet của bạn");
                        }
                        break;
                    case 8:
                        if (CheckDeviceInternet.haveNetworkConnection(getApplicationContext())){
                            Intent intentInfo=new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intentInfo);
                        }else {
                            CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối internet của bạn");
                        }
                        break;
                }
            }
        });
    }

    private void GetSanPhamMoiNhat() {
        final RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest arrayRequest=new JsonArrayRequest(Server.LinkSanPhamMoi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    int ID=0;
                    String Tensanpham="";
                    Integer Giasanpham=0;
                    String Hinhsanpham="";
                    String Motasanpham="";
                    int IDsanpham=0;

                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID=jsonObject.getInt("maSP");
                            Tensanpham=jsonObject.getString("tenSP");
                            Giasanpham=jsonObject.getInt("giaSP");
                            Hinhsanpham=jsonObject.getString("hinhSP");
                            Motasanpham=jsonObject.getString("motaSP");
                            IDsanpham=jsonObject.getInt("idSP");
                            listSanPhamMoi.add(new SanPham(ID,Tensanpham,Giasanpham,Hinhsanpham,Motasanpham,IDsanpham));
                            sanPhamMoiAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(arrayRequest);
    }

    private void GetDuLieuLoaiSP() {
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.LinkLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            maLoai=jsonObject.getInt("maLoaiSP");
                            tenLoai=jsonObject.getString("tenLoaiSP");
                            hinhAnhLoaiSp=jsonObject.getString("hinhAnhLoaiSP");
                            listLoaiSP.add(new LoaiSP(maLoai,tenLoai,hinhAnhLoaiSp));
                            loaiSPAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listLoaiSP.add(7, new LoaiSP(0,"Liên hệ", "https://img.icons8.com/color/48/000000/contacts.png"));
                    listLoaiSP.add(8, new LoaiSP(0,"Thông tin", "https://img.icons8.com/color/48/000000/about.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckDeviceInternet.ShowNotify_Short(getApplicationContext(),error.toString());
            }
        });
        queue.add(jsonArrayRequest);
    }

    //Bắt sự kiện chạy quảng cáo
    private void ActionViewFliperHome(){
        ArrayList<String> mangquangcao=new ArrayList<>();
        mangquangcao.add("http://channel.mediacdn.vn/2019/5/2/photo-1-15567728482831064701738.jpg");
        mangquangcao.add("https://www.moneycrashers.com/wp-content/uploads/2019/01/zero-waste-leaves-1068x713.jpg");
        mangquangcao.add("https://www.arcticgardens.ca/blog/wp-content/uploads/2018/11/ArcticGarden_infographic_English_-01.jpg");
        mangquangcao.add("https://kenh14cdn.com/2018/9/29/web33-1538172809322670923287.jpg");
        mangquangcao.add("https://www.zerowastesaigon.com/wp-content/uploads/2018/11/beauty-box-inside-zero-waste-saigon-giang-oi-facebook.jpeg");
        mangquangcao.add("https://mostlyamelie.com/wp-content/uploads/2018/08/zero-waste.jpg");
        mangquangcao.add("https://thaihabooks.com/wp-content/uploads/elementor/thumbs/Capture-o8urtsieyxa6ewaweo5300bqgc19os2yzz7q5fvseg.png");
        for (int i=0;i<mangquangcao.size();i++){
            ImageView imgQuangCao=new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imgQuangCao);
            imgQuangCao.setScaleType(ImageView.ScaleType.FIT_XY);//gán hình ảnh lớn nhưng không bị mất ảnh
            viewFlipper.addView(imgQuangCao);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animationSlideIn= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_left_to_right);
        Animation animationSlideOut= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_appear_to_left);
        viewFlipper.setInAnimation(animationSlideIn);
        viewFlipper.setOutAnimation(animationSlideOut);
    }

    //Hàm bắt sự kiện cho toolbar
    private void ActionBarHome() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutHome.openDrawer(GravityCompat.START);

            }
        });
    }

    //Hàm ánh xạ
    private void AnhXa() {
        toolbar             =(Toolbar) findViewById(R.id.toolbarHome);
        viewFlipper         =(ViewFlipper) findViewById(R.id.viewFlipperHome);
        recyclerViewHome    =(RecyclerView) findViewById(R.id.recycleViewHome);
        navigationView      =(NavigationView) findViewById(R.id.navigationViewHome);
        listViewHome        =(ListView) findViewById(R.id.listViewMenuHome);
        drawerLayoutHome    =(DrawerLayout) findViewById(R.id.drawerLayoutHome);
        listLoaiSP          =new ArrayList<>();
        listLoaiSP.add(0, new LoaiSP(0,"Trang chủ", "https://img.icons8.com/color/48/000000/home.png"));
        loaiSPAdapter       =new LoaiSPAdapter(listLoaiSP,getApplicationContext());
        listViewHome.setAdapter(loaiSPAdapter);
        listSanPhamMoi=new ArrayList<>();
        sanPhamMoiAdapter=new SanPhamAdapter(getApplicationContext(),listSanPhamMoi);
        recyclerViewHome.setHasFixedSize(true);
        recyclerViewHome.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewHome.setAdapter(sanPhamMoiAdapter);
    }
}
