package com.example.jhzyl.firstapp.DashBoard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.view.CustomFooterView;

public class SuperAwesomeCardFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.super_awesome_card_fragment2,null);
    }

    private XRefreshView qgp_goods_shop_xrfv;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        qgp_goods_shop_xrfv=view.findViewById(R.id.qgp_goods_shop_xrfv);
        qgp_goods_shop_xrfv.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                qgp_goods_shop_xrfv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        qgp_goods_shop_xrfv.stopLoadMore();
                    }
                }, 2000);
            }

            @Override
            public void onRefresh(boolean isPullDown) {
                qgp_goods_shop_xrfv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        qgp_goods_shop_xrfv.stopRefresh();
                    }
                }, 2000);
            }
        });
        qgp_goods_shop_xrfv.setAutoRefresh(false);
        qgp_goods_shop_xrfv.setPullLoadEnable(true);
        qgp_goods_shop_xrfv.setPinnedTime(1000);
        qgp_goods_shop_xrfv.setAutoLoadMore(false);
        qgp_goods_shop_xrfv.setCustomFooterView(new CustomFooterView(getContext()));
    }
}
