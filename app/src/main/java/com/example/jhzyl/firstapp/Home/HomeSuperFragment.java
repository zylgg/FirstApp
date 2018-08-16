package com.example.jhzyl.firstapp.Home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.example.jhzyl.firstapp.Home.bean.SuperEntity;
import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.SystemAppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class HomeSuperFragment extends Fragment {
    private static OnVisibilityTitleListener onVisibilityTitleListener;
    private RecyclerView rv_super_theme_lists;
    private XRefreshView xrv_super_theme;
    private List<SuperEntity> datas = new ArrayList<>();
    private static Map<Integer, Integer> fragTabMap = new HashMap<>();
    private int pos;

    public static HomeSuperFragment getInstance(int pos, OnVisibilityTitleListener listener) {
        HomeSuperFragment fragment = new HomeSuperFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        fragment.setArguments(bundle);

        onVisibilityTitleListener = listener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_super_fragment_layout, null);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        Bundle bundle = getArguments();
        pos = bundle.getInt("pos");
        if (fragTabMap.size() == 0 || fragTabMap.get(pos) == null) {
            fragTabMap.put(pos, 0);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        datas = SuperEntity.mDatas;

        TextView tv_home_theme_pos = view.findViewById(R.id.tv_super_theme_pos);
        tv_home_theme_pos.setText("pos:" + pos);
        xrv_super_theme = view.findViewById(R.id.xrv_super_theme);
        xrv_super_theme.setXRefreshViewListener(xRefreshViewListener);
        rv_super_theme_lists = view.findViewById(R.id.rv_super_theme_lists);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6, GridLayoutManager.VERTICAL, false);
//        StaggeredGridLayoutManager manager2 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_super_theme_lists.setLayoutManager(gridLayoutManager);

        rv_super_theme_lists.setAdapter(new HomeSuperFragment.MyRvAdapter());
        rv_super_theme_lists.addOnScrollListener(RvScrollListener.getInstance(onVisibilityTitleListener));
    }

    private XRefreshView.XRefreshViewListener xRefreshViewListener = new XRefreshView.SimpleXRefreshListener() {
        @Override
        public void onRefresh(boolean isPullDown) {
            super.onRefresh(isPullDown);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (xrv_super_theme.mPullRefreshing) {
                        xrv_super_theme.stopRefresh();

                        int refresh_count = fragTabMap.get(pos);
                        fragTabMap.put(pos, ++refresh_count);

                        for (SuperEntity entity : datas) {
                            entity.setRefresh_count(fragTabMap.get(pos));
                        }

                        rv_super_theme_lists.getAdapter().notifyDataSetChanged();
                    }
                }
            }, 1500);
        }
    };

    private class MyRvAdapter extends RecyclerView.Adapter<HomeSuperFragment.MyRvAdapter.MyHolder> {

        @Override
        public HomeSuperFragment.MyRvAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.home_super_fragment_gv_item, null);
//            view.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
            return new HomeSuperFragment.MyRvAdapter.MyHolder(view);
        }

        int count=0;
        @Override
        public void onBindViewHolder(HomeSuperFragment.MyRvAdapter.MyHolder holder, int position) {
            Log.i("move_count:", ""+(count++));
            SuperEntity entity = datas.get(position);
//            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
//            if (entity.getType() == SuperEntity.BigItemType) {
//                layoutParams.setFullSpan(true);
//                layoutParams.height = SystemAppUtils.dip2px(getContext(), 80);
//            }else{
//                layoutParams.setFullSpan(false);
//                int result = (int) (Math.random() * 2 + 1) + 3;//结果是1-10的随机数
//                layoutParams.height = (int) (result / 2.0f * SystemAppUtils.dip2px(getContext(), 80));
//            }

            holder.text1.setText("item:" + position + "\n" + datas.get(position).getRefresh_count());
//            holder.itemView.setLayoutParams(layoutParams);
        }

        @Override
        public int getItemViewType(int position) {
            SuperEntity entity = datas.get(position);
            return entity.getType();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {

            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int type = getItemViewType(position);
                        switch (type) {
                            case SuperEntity.BigItemType:
                                return 6;
                            case SuperEntity.MiddleItemType:
                                return 3;
                            case SuperEntity.SmallItemType:
                                return 2;
                            case SuperEntity.MaxSmallItemType:
                                return 1;
                        }
                        return 2;
                    }
                });
            }
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
