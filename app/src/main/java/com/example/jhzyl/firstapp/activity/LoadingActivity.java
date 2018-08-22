package com.example.jhzyl.firstapp.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.view.JumpLoadingView;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    private XRefreshView xrv_loading;
    private ListView iv_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        xrv_loading=findViewById(R.id.xrv_loading);
        xrv_loading.setPullRefreshEnable(true);
        xrv_loading.setPullLoadEnable(false);
        xrv_loading.setPreLoadCount(1500);
        xrv_loading.setCustomHeaderView(new JumpLoadingView(this));

        xrv_loading.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener(){
            @Override
            public void onRefresh(boolean isPullDown) {
                super.onRefresh(isPullDown);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrv_loading.stopRefresh();
                    }
                },6000);
            }
        });


        iv_loading=findViewById(R.id.iv_loading);
        List<String> list=new ArrayList<>();
        for (int i=0;i<20;i++){
            list.add("item"+i);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        iv_loading.setAdapter(adapter);
    }
}
