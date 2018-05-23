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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.example.jhzyl.firstapp.R;

import java.util.ArrayList;
import java.util.List;


public class SuperAwesomeCardFragment extends Fragment {

    private static final String ARG_POSITION = "position";


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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        XRefreshView xrv_my_refreshview=view.findViewById(R.id.xrv_my_refreshview);
        xrv_my_refreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener(){
            @Override
            public void onRefresh(boolean isPullDown) {
                super.onRefresh(isPullDown);
            }
        });

        RecyclerView rv_my_listView = view.findViewById(R.id.rv_my_listView);
        rv_my_listView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> mLists = new ArrayList<>();
        mLists.add("CARD " + position);
        for (int i = 0; i < 50; i++) {
            mLists.add("pos：" + i + "\n");
        }
        rv_my_listView.setAdapter(new MyAdapter(mLists));
    }

    class MyAdapter extends RecyclerView.Adapter {
        private List<String> mLists = new ArrayList<>();

        public MyAdapter(List<String> mLists) {
            this.mLists = mLists;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyHolder H = (MyHolder) holder;
            H.tv.setGravity(Gravity.CENTER_VERTICAL);
            H.tv.setText(mLists.get(position));
        }

        @Override
        public int getItemCount() {
            return mLists.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(android.R.id.text1);
        }
    }

}