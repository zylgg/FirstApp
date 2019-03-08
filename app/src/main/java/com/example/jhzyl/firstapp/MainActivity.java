package com.example.jhzyl.firstapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhzyl.firstapp.DashBoard.DashBoardFragment;
import com.example.jhzyl.firstapp.Home.HomeFragment;
import com.example.jhzyl.firstapp.adapter.MyVpAdapter;
import com.example.jhzyl.firstapp.utils.StatusBarUtils;
import com.taobao.sophix.SophixManager;

public class MainActivity extends AppCompatActivity implements OnChangeStatusTextColorListener {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION =10000;
    private ViewPager vp_content;
    private RadioGroup rg_bottom_menu;
    private FloatingActionButton fab＿query,fab_test,fab_cleanLog;
    private TextView tv_repair_log;

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId==R.id.rb_home){
                vp_content.setCurrentItem(0);
            }else if (checkedId==R.id.rb_dashboard){
                vp_content.setCurrentItem(1);
            }else{
                vp_content.setCurrentItem(2);
            }
//            MyToast.T(MainActivity.this,"checkedId:"+checkedId);
        }
    };
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int checkedRadioButtonId = rg_bottom_menu.getCheckedRadioButtonId();
            RadioButton lastRB = rg_bottom_menu.findViewById(checkedRadioButtonId);
            lastRB.setChecked(false);
            ((RadioButton)rg_bottom_menu.getChildAt(position)).setChecked(true);
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtils.setTransparent(this);
        if (Build.VERSION.SDK_INT >= 23) {
            requestExternalStoragePermission();
        }


        tv_repair_log=findViewById(R.id.tv_repair_log);
        updateConsole(MySophixApplication.cacheMsg.toString());

        fab＿query=findViewById(R.id.fab＿query);
        fab_test=findViewById(R.id.fab_test);
        fab_cleanLog=findViewById(R.id.fab_cleanLog);
        vp_content = findViewById(R.id.vp_content);
        rg_bottom_menu = findViewById(R.id.rg_bottom_menu);
        rg_bottom_menu.setOnCheckedChangeListener(onCheckedChangeListener);

        MyVpAdapter myVpAdapter = new MyVpAdapter(getSupportFragmentManager());
        myVpAdapter.addFragment(HomeFragment.getInstance(this));
        myVpAdapter.addFragment(DashBoardFragment.getInstance(this));
//        myVpAdapter.addFragment(new SuperAwesomeCardFragment2());
        vp_content.setAdapter(myVpAdapter);
        vp_content.addOnPageChangeListener(onPageChangeListener);
        vp_content.setOffscreenPageLimit(1);

        ((RadioButton)rg_bottom_menu.getChildAt(0)).setChecked(true);
        onChange(true);
        fab＿query.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                SophixManager.getInstance().queryAndLoadNewPatch();
            }
        });
        fab_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"我又改了一个bug！",Toast.LENGTH_LONG).show();
            }
        });
        fab_cleanLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStatusStr="";
                tv_repair_log.setText("");
            }
        });


        MySophixApplication.msgDisplayListener = new MySophixApplication.MsgDisplayListener() {
            @Override
            public void handle(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateConsole(msg);
                    }
                });
            }
        };
    }

    private String mStatusStr="";
    /**
     * 更新监控台的输出信息
     *
     * @param content 更新内容
     */
    private void updateConsole(String content) {
        mStatusStr += content + "\n";
        if (tv_repair_log != null) {
            tv_repair_log.setText(mStatusStr);
        }
    }

    @Override
    public void onChange(boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int color = isBlack ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_VISIBLE;
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | color);
        }
    }

    /**
     * 如果本地补丁放在了外部存储卡中, 6.0以上需要申请读外部存储卡权限才能够使用. 应用内部存储则不受影响
     */

    private void requestExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE_PERMISSION);
        }else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"没有选择读取外部sd卡权限！",Toast.LENGTH_LONG).show();
                }else{
                }
                break;
            default:
        }
    }

}

