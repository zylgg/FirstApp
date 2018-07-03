package com.example.jhzyl.firstapp.ref;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.example.jhzyl.firstapp.CustomFooterView;
import com.example.jhzyl.firstapp.Person;
import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoViewHolderListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SimpleAdapter adapter;
    List<Person> personList = new ArrayList<Person>();
    XRefreshView xRefreshView;
    int lastVisibleItem = 0;
    //    GridLayoutManager layoutManager;
    LinearLayoutManager layoutManager;
    private boolean isBottom = false;
    private int mLoadCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_view_holder_list);

        xRefreshView = (XRefreshView) findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_test_rv);
        recyclerView.setHasFixedSize(true);


        adapter = new SimpleAdapter(personList, this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 静默加载模式不能设置footerview
        recyclerView.setAdapter(adapter);
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullRefreshEnable(true);
        xRefreshView.setAutoLoadMore(true);

        adapter.setCustomLoadMoreView(new CustomFooterView(this));
//        xRefreshView.enableReleaseToLoadMore(true);//松开加载更多
        xRefreshView.enableRecyclerViewPullUp(true);//不想让Recyclerview到达底部被继续往上拉起可以这样做:
//        xRefreshView.enablePullUpWhenLoadCompleted(true);//只是想在【数据加载完成】以后才关闭到达底部继续往上拉的功能，而在【数据没有加载完成】的时候能使用松开加载更多的功能的话
        // 设置静默加载模式
//        xRefreshView.setSilenceLoadMore(true);
        //设置静默加载时提前加载的item个数
//        xRefreshView.setPreLoadCount(4);
        xRefreshView.setHideFooterWhenComplete(false);

        initData();
        adapter.notifyDataSetChanged();

        //设置Recyclerview的滑动监听
        xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        personList.clear();
                        initData();
                        adapter.setData(personList);
                        xRefreshView.stopRefresh();
                        if (xRefreshView.hasLoadCompleted()){
                            mLoadCount=0;
                            xRefreshView.setLoadComplete(false);
                        }


                    }
                }, 500);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        for (int i = 0; i < 6; i++) {
//                            adapter.insert(new Person("More ", mLoadCount + "21"),
//                                    adapter.getAdapterItemCount());
                            personList.add(new Person("More ", mLoadCount + "_21"));
                        }
                        adapter.setData(personList);

                        mLoadCount++;
                        if (mLoadCount >= 3) {//模拟没有更多数据的情况
                            xRefreshView.setLoadComplete(true);
                        } else {
                            // 刷新完成必须调用此方法停止加载
                            xRefreshView.stopLoadMore(true);
                            //当数据加载失败 不需要隐藏footerview时，可以调用以下方法，传入false，不传默认为true
                            // 同时在Footerview的onStateFinish(boolean hideFooter)，可以在hideFooter为false时，显示数据加载失败的ui
//                            xRefreshView1.stopLoadMore(false);
                        }
                    }
                }, 1000);
            }
        });
    }
    private void initData() {
        for (int i = 0; i < 3; i++) {
            Person person = new Person("name" + i, "" + i);
            personList.add(person);
        }
    }

}

