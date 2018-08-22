package com.example.jhzyl.firstapp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.jhzyl.firstapp.utils.DensityUtil;
import com.example.jhzyl.firstapp.R;

public class ScrollProgressActivity extends AppCompatActivity {

    private RecyclerView rv_scroll_progress;
    private View v_scroll_progress;
    private LinearLayout ll_scroll_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_progress);
        rv_scroll_progress = findViewById(R.id.rv_scroll_progress);
        v_scroll_progress=findViewById(R.id.v_scroll_progress);

        ll_scroll_progress=findViewById(R.id.ll_scroll_progress);
        rv_scroll_progress.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));
        rv_scroll_progress.setAdapter(new MyRecyclerViewAdapter());
        rv_scroll_progress.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int offset = recyclerView.computeHorizontalScrollOffset();
                int extent = recyclerView.computeHorizontalScrollExtent();
                int range = recyclerView.computeHorizontalScrollRange();
                Log.i("onScrolled", "滑动距离" + offset);
                Log.i("onScrolled", "控件宽度" + extent);
                Log.i("onScrolled", "总的宽度" + range);

                int p_width = ll_scroll_progress.getWidth();
                int v_width = v_scroll_progress.getWidth();

                float pb_offset=offset*1.0f/(range-extent) *(p_width-v_width);

                v_scroll_progress.setTranslationX(pb_offset);

            }
        });
    }
    private void dialog1(){
        Dialog dialog=new Dialog(this,R.style.dialog);
        TextView textView=new TextView(this);
        textView.setBackgroundColor(getResources().getColor(R.color.pink));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);

        textView.setText("dialog1");
        dialog.setContentView(textView);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes  = window.getAttributes();
        attributes.width=LayoutParams.MATCH_PARENT;
        attributes.height= DensityUtil.dip2px(this,100);
        attributes.gravity=Gravity.BOTTOM;
        dialog.show();
    }
    @SuppressLint("ResourceType")
    private void dialog2(){
        Dialog dialog2=new Dialog(this,R.style.dialog);
        TextView textView=new TextView(this);
        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);

        textView.setText("dialog2");
        dialog2.setContentView(textView);
        Window window = dialog2.getWindow();
        window.setWindowAnimations(R.style.toast_anim);
        WindowManager.LayoutParams attributes  = window.getAttributes();
        attributes.width=LayoutParams.MATCH_PARENT;
        attributes.height=DensityUtil.dip2px(this,100);
        attributes.gravity=Gravity.BOTTOM;
        dialog2.show();
    }


    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.Myholder> {
        @Override
        public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ScrollProgressActivity.this).inflate(R.layout.scroll_prgress_recycler_item, null);
            return new Myholder(view);
        }

        @Override
        public void onBindViewHolder(Myholder holder, int position) {
            holder.tv_scrollprogress_recycler_item.setText("item:" + position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class Myholder extends RecyclerView.ViewHolder {
            TextView tv_scrollprogress_recycler_item;

            public Myholder(View itemView) {
                super(itemView);
                tv_scrollprogress_recycler_item = itemView.findViewById(R.id.tv_scrollprogress_recycler_item);
            }
        }
    }
}
