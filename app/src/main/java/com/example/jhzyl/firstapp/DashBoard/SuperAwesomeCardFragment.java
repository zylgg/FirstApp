/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jhzyl.firstapp.DashBoard;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.jhzyl.firstapp.BaseFragment;
import com.example.jhzyl.firstapp.CustomFooterView;
import com.example.jhzyl.firstapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SuperAwesomeCardFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private XRefreshView xrv_my_refreshview;
    private int position;

    public static SuperAwesomeCardFragment newInstance(int position) {
        SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout, container, false);
        return view;
    }

    public enum PullState {
        whileOpen, whileClose, other;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setXRefreshViewDisallow(PullState state) {
        boolean is_open = (state == PullState.whileOpen);
        Log.i("TAG", "setXRefreshViewDisallow: " + is_open);
        xrv_my_refreshview.disallowInterceptTouchEvent(!is_open);//如果是打开了，不拦截
    }

    RecyclerView rv_my_listView;
    List<String> mLists = new ArrayList<>();
    MyAdapter mAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rv_my_listView = view.findViewById(R.id.rv_my_listView);

        rv_my_listView.setHasFixedSize(true);
        rv_my_listView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mAdapter = new MyAdapter(mLists);
        rv_my_listView.setAdapter(mAdapter);

        xrv_my_refreshview = view.findViewById(R.id.xrv_my_refreshview);
        xrv_my_refreshview.setPinnedTime(1000);
        xrv_my_refreshview.setMoveForHorizontal(true);
        xrv_my_refreshview.setPullRefreshEnable(true);
        xrv_my_refreshview.setAutoLoadMore(true);
        mAdapter.setCustomLoadMoreView(new CustomFooterView(getContext()));
        xrv_my_refreshview.setHideFooterWhenComplete(false);
        xrv_my_refreshview.enableRecyclerViewPullUp(false);

        xrv_my_refreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                refreshData();
                if (xrv_my_refreshview.hasLoadCompleted()) {
                    xrv_my_refreshview.setLoadComplete(false);
                }
            }

            @Override
            public void onLoadMore(boolean isSilence) {
//                if (xrv_my_refreshview.hasLoadCompleted()){
//                    return;
//                }
//                loadMore();
            }
        });
        xrv_my_refreshview.startRefresh();

    }

    RecyclerView.OnScrollListener onScrollListener= new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (is_loading||xrv_my_refreshview.mPullRefreshing){
                return;
            }
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (layoutManager.findLastVisibleItemPosition()>=(layoutManager.getItemCount()-3)){
                if (xrv_my_refreshview.hasLoadCompleted()){
                    return;
                }
                loadMore();
            }
        }
    };
//    @Override
//    protected void onFragmentFirstVisible() {
//
//    }

    private void refreshData() {
        mLists.clear();
        mLists.add("CARD " + position);
        for (int i = 0; i < 25; i++) {
            mLists.add("pos：" + i + "\n");
        }

        if (mLists.size()<25){
            xrv_my_refreshview.setLoadComplete(true);
        }

        xrv_my_refreshview.stopRefresh();
        mAdapter.Refresh(mLists);
        rv_my_listView.addOnScrollListener(onScrollListener);
    }

    private boolean is_loading=false;
    private void loadMore() {
        is_loading=true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                is_loading=false;
                int lastpos = mLists.size() - 1;
                int new_size= (int) (Math.random()*25+1);
                for (int i = lastpos; i < (lastpos + new_size); i++) {
                    mLists.add("pos_new：" + i + "\n");
                }
                mAdapter.Refresh(mLists);
                if (new_size<10) {
                    xrv_my_refreshview.setLoadComplete(true);
                } else {
                    xrv_my_refreshview.stopLoadMore(true);
                }
            }
        }, 500);
    }


    class MyAdapter extends BaseRecyclerAdapter<MyHolder> {

        private List<String> datas;

        public MyAdapter(List<String> mLists) {
            this.datas = mLists;
        }

        public void Refresh(List<String> mLists) {
            this.datas = mLists;
            this.notifyDataSetChanged();
        }

        @Override
        public MyHolder getViewHolder(View view) {
            return new MyHolder(view, false);
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
            return new MyHolder(LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false), true);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position, boolean isItem) {
            holder.tv.setText(datas.get(position));
        }

        @Override
        public int getAdapterItemCount() {
            return datas.size();
        }

    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private boolean isitem;

        public MyHolder(View itemView, boolean isitem) {
            super(itemView);
            if (isitem)
                tv = itemView.findViewById(android.R.id.text1);
        }
    }

}