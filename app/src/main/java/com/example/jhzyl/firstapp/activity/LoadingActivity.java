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
import java.util.Random;

public class LoadingActivity extends AppCompatActivity {

    private XRefreshView xrv_loading;
    private ListView iv_loading;
    private List<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        xrv_loading = findViewById(R.id.xrv_loading);
        xrv_loading.setPullRefreshEnable(true);
        xrv_loading.setPullLoadEnable(false);
        xrv_loading.setPinnedTime(3000);
        xrv_loading.setCustomHeaderView(new JumpLoadingView(this));

        xrv_loading.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                super.onRefresh(isPullDown);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int count = (int) (Math.random() * 5)+1;
                        List<String> mlists=new ArrayList<>();
                        for (int i = 0; i < count; i++) {
                            mlists.add("新数据" + i);
                        }
                        mlists.addAll(list);

                        list.clear();
                        list.addAll(mlists);
                        adapter.notifyDataSetChanged();
                        xrv_loading.stopRefresh();
                    }
                }, 3000);
            }
        });


        iv_loading = findViewById(R.id.iv_loading);
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item" + i);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        iv_loading.setAdapter(adapter);
    }
}
