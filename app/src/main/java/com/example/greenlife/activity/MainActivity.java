package com.example.greenlife.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.example.greenlife.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Khai báo biến toàn cục
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayoutHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();

        ActionBarHome();

        ActionViewFliperHome();
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
    }
}
