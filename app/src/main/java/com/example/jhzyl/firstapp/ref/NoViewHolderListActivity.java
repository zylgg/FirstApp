package com.example.jhzyl.firstapp.ref;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class NoViewHolderListActivity extends AppCompatActivity {

    private PullToRefreshViewBtp prv_no_view_holder;
    private GridView gv_no_view_holder;
    private List<String> datas = new ArrayList<>();
    int count = 0;
    int getview_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_view_holder_list);
        prv_no_view_holder = findViewById(R.id.prv_no_view_holder);
        gv_no_view_holder = findViewById(R.id.gv_no_view_holder);
        for (int i = 0; i < 40; i++) {
            datas.add("itemm:" + i + "_刷新次数-0");
        }
        gv_no_view_holder.setAdapter(new MyAdapter());
        prv_no_view_holder.setNoAddMore(true);
        prv_no_view_holder.setOnHeaderRefreshListener(new PullToRefreshViewBtp.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshViewBtp view) {
                prv_no_view_holder.onHeaderRefreshComplete();
                count++;
                datas.clear();
                for (int i = 0; i < 40; i++) {
                    datas.add("itemm:" + i + "_刷新次数-" +count);
                }
                ((BaseAdapter) gv_no_view_holder.getAdapter()).notifyDataSetChanged();
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        private static final String TAG = "MyAdapter";

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i(TAG, "getView: " + (getview_count++));
            convertView = View.inflate(NoViewHolderListActivity.this, android.R.layout.simple_list_item_1, null);
            TextView text1 = convertView.findViewById(android.R.id.text1);
            text1.setText(datas.get(position));
            return convertView;
        }
    }
}

