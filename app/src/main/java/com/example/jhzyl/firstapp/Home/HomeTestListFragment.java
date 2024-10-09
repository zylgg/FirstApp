package com.example.jhzyl.firstapp.Home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.adapter.CommonAdapter;
import com.example.jhzyl.firstapp.adapter.CommonViewHolder;
import com.example.jhzyl.firstapp.view.CustomFooterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeTestListFragment extends Fragment implements View.OnClickListener {
    private static OnVisibilityTitleListener onVisibilityTitleListener;

    public static HomeTestListFragment getInstance(int pos, OnVisibilityTitleListener listener) {
        HomeTestListFragment fragment = new HomeTestListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        fragment.setArguments(bundle);

        onVisibilityTitleListener = listener;
        return fragment;
    }

    private static final String TAG="HomeTestListFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.home_test_list_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ");
    }


    private MyTestListAdapter myTestListAdapter;
    private List<String> datas=new ArrayList<>();
    private ListView lv_home_test_list;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lv_home_test_list=view.findViewById(R.id.lv_home_test_list);
        TextView tv_home_test_lists=view.findViewById(R.id.tv_home_test_lists);
        tv_home_test_lists.setOnClickListener(this);

        RefreshData();
    }

    private void RefreshData() {
        datas.clear();
        int size=((int)(Math.random()*10+1));
        for (int i=0;i<size;i++){
            datas.add(size+"个-item:"+i);
        }

        if (myTestListAdapter==null){
            myTestListAdapter=new MyTestListAdapter(getContext(),datas);
            lv_home_test_list.setAdapter(myTestListAdapter);
        }else{
            myTestListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        RefreshData();
    }

    private class MyTestListAdapter extends CommonAdapter<String> {
        public MyTestListAdapter(Context context,List<String> datas) {
            this(context, android.R.layout.simple_list_item_1);
            super.lists=datas;
        }
        public MyTestListAdapter(Context context, int contentId) {
            super(context, contentId);
        }

        @Override
        public void setLists(List<String> datas) {

        }

        @Override
        public void fillData(CommonViewHolder holder, int i) {
            TextView view = holder.findView(android.R.id.text1);
            view.setText(super.lists.get(i));
        }
    }



}
