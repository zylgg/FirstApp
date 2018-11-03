package com.example.jhzyl.firstapp;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.andview.refreshview.XScrollView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.jhzyl.firstapp.Home.ScrollingActivity;
import com.example.jhzyl.firstapp.view.CustomFooterView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ScrollingFragment extends Fragment {
    private RecyclerView rv_scrolling_test;
    private XRefreshView xrv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scrolling_fragment, null);

    }

    /**
     * 下拉刷新拦截
     *
     * @param statu
     */
    @Subscribe
    public void onEventMainThread(ScrollingActivity.ShopAppBarLayoutOpenStatus statu) {
        switch (statu) {
            case wholeOpen:
                xrv.disallowInterceptTouchEvent(false);
                break;
            case wholeClose:
                xrv.disallowInterceptTouchEvent(false);
                break;
            default:
                xrv.disallowInterceptTouchEvent(true);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        xrv = view.findViewById(R.id.xrv);
        xrv.setPullLoadEnable(true);
        xrv.setPinnedTime(1000);
        xrv.setCustomFooterView(new XRefreshViewFooter(getContext()));
        xrv.setHideFooterWhenComplete(false);

        xrv.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                xrv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrv.stopRefresh();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
                xrv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int start_i=mLists.size();
                        for (int i=start_i;i<start_i+20;i++){
                            mLists.add("item"+i);
                        }
                        myRvAdapter.notifyDataSetChanged();
                        if (mLists.size()>60){
                            xrv.setLoadComplete(true);
                        }else{
                            xrv.stopLoadMore();
                        }
                    }
                }, 1000);
            }
        });

        rv_scrolling_test = view.findViewById(R.id.rv_scrolling_test);
        rv_scrolling_test.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_scrolling_test.setNestedScrollingEnabled(false);
        rv_scrolling_test.setHasFixedSize(true);

        if (myRvAdapter == null) {
            myRvAdapter = new MyRvAdapter(getContext());
            for (int i=0;i<20;i++){
                mLists.add("it:"+i);
            }
            rv_scrolling_test.setAdapter(myRvAdapter);
            rv_scrolling_test.setFocusable(false);
        }

    }

    private MyRvAdapter myRvAdapter;
    private List<String> mLists = new ArrayList<>();

    private class MyRvAdapter extends BaseRecyclerAdapter {
        private Context mContext;

        public MyRvAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public RecyclerView.ViewHolder getViewHolder(View view) {
            return new MyHolders(view);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
            View view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
            return new MyHolders(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, boolean isItem) {
            MyHolders holders = (MyHolders) holder;
            ((MyHolders) holder).text1.setText("item-" + position);
        }

        @Override
        public int getAdapterItemCount() {
            return mLists.size();
        }

        class MyHolders extends RecyclerView.ViewHolder {

            TextView text1;

            public MyHolders(View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
