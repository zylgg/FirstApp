package com.example.jhzyl.firstapp.Home;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.example.jhzyl.firstapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeThemeFragment extends Fragment {
    public static final String TAG = "HomeThemeFragment";
    private static OnVisibilityTitleListener onVisibilityTitleListener;
    private static Map<Integer, Integer> fragTabMap = new HashMap<>();

    public static HomeThemeFragment getInstance(int pos, OnVisibilityTitleListener listener) {
        HomeThemeFragment fragment = new HomeThemeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        fragment.setArguments(bundle);

        onVisibilityTitleListener = listener;
        return fragment;
    }

    private RecyclerView rv_home_theme_lists;
    private XRefreshView xrv_home_theme;
    private List<String> datas = new ArrayList<>();
    private int pos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_theme_fragment_layout, null);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        Bundle bundle = getArguments();
        pos = bundle.getInt("pos");
        if (fragTabMap.size()==0||fragTabMap.get(pos)==null){
            fragTabMap.put(pos,0);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        for (int i = 0; i < 40; i++) {
            datas.add("itemm:" + i + "_刷新次数-" + fragTabMap.get(pos));
        }
        TextView tv_home_theme_pos = view.findViewById(R.id.tv_home_theme_pos);
        tv_home_theme_pos.setText("pos:" + pos);
        xrv_home_theme = view.findViewById(R.id.xrv_home_theme);
        xrv_home_theme.setXRefreshViewListener(xRefreshViewListener);
        rv_home_theme_lists = view.findViewById(R.id.rv_home_theme_lists);
        rv_home_theme_lists.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_home_theme_lists.setAdapter(new HomeThemeFragment.MyRvAdapter());
        rv_home_theme_lists.addOnScrollListener(onScrollListener);
    }

    private XRefreshView.XRefreshViewListener xRefreshViewListener = new XRefreshView.SimpleXRefreshListener() {
        @Override
        public void onRefresh(boolean isPullDown) {
            super.onRefresh(isPullDown);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (xrv_home_theme.mPullRefreshing) {
                        xrv_home_theme.stopRefresh();

                        int refresh_count = fragTabMap.get(pos);
                        fragTabMap.put(pos, ++refresh_count);

                        datas.clear();
                        for (int i = 0; i < 40; i++) {
                            datas.add("itemm:" + i + "_刷新次数-" + fragTabMap.get(pos));
                        }
                        rv_home_theme_lists.getAdapter().notifyDataSetChanged();
                    }
                }
            }, 1500);
        }
    };

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 40) {//40，单次滚动的偏移（可自己定义）
                //向上滑，隐藏
                if (onVisibilityTitleListener != null) {
                    onVisibilityTitleListener.hide();
                }

            } else if (dy < -40) {
                //向下滑，打开
                if (onVisibilityTitleListener != null) {
                    onVisibilityTitleListener.open();
                }
            }
        }
    };

    private class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.text1.setText(datas.get(position));

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView text1;

            public MyHolder(View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
            }
        }
    }


}
