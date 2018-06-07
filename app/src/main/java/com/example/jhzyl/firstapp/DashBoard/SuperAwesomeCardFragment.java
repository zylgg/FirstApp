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
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
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
import com.example.jhzyl.firstapp.CustomFooterView;
import com.example.jhzyl.firstapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


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
        ViewGroup viewParent = (ViewGroup) view.getParent();
        if (viewParent != null) {
            viewParent.removeView(view);
        }
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rv_my_listView = view.findViewById(R.id.rv_my_listView);
        rv_my_listView.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_my_listView.setHasFixedSize(true);
        xrv_my_refreshview = view.findViewById(R.id.xrv_my_refreshview);
        if (mAdapter == null) {
            mAdapter = new MyAdapter(mLists);
            mAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
            rv_my_listView.setAdapter(mAdapter);
        }

        xrv_my_refreshview.setPinnedTime(1000);
        xrv_my_refreshview.setPullRefreshEnable(true);
        xrv_my_refreshview.setPullLoadEnable(true);
        xrv_my_refreshview.setAutoLoadMore(false);
        xrv_my_refreshview.enableReleaseToLoadMore(true);
        xrv_my_refreshview.enableRecyclerViewPullUp(true);
        xrv_my_refreshview.enablePullUpWhenLoadCompleted(true);

        xrv_my_refreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadMore();
            }
        });

        xrv_my_refreshview.startRefresh();
    }

    List<String> mLists = new ArrayList<>();
    MyAdapter mAdapter;

    private void refreshData() {
        mLists.clear();
        mLists.add("CARD " + position);
        for (int i = 0; i < 20; i++) {
            mLists.add("pos：" + i + "\n");
        }
        mAdapter.notifyDataSetChanged();
        xrv_my_refreshview.stopRefresh();
    }

    private void loadMore() {
        for (int i = 0; i < 20; i++) {
            mAdapter.insert("pos_new：" + i + "\n",
                    mAdapter.getAdapterItemCount());
        }
        xrv_my_refreshview.stopLoadMore(true);
    }


    class MyAdapter extends BaseRecyclerAdapter<MyHolder> {
        private List<String> mLists = new ArrayList<>();

        public MyAdapter(List<String> mLists) {
            this.mLists = mLists;
        }

        @Override
        public MyHolder getViewHolder(View view) {
            return new MyHolder(view, false);
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
            View view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new MyHolder(view, true);
        }

        @Override
        public void onBindViewHolder(MyHolder H, int position, boolean isItem) {
            H.tv.setGravity(Gravity.CENTER_VERTICAL);
            H.tv.setText(mLists.get(position));
        }

        @Override
        public int getAdapterItemViewType(int position) {
            return 0;
        }

        public void insert(String person, int position) {
            insert(mLists, person, position);
        }

        @Override
        public int getAdapterItemCount() {
            return mLists.size();
        }

    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem)
                tv = itemView.findViewById(android.R.id.text1);
        }
    }

}