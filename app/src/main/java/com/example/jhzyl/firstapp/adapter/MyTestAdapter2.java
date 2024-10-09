package com.example.jhzyl.firstapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.bean.StudyBean;

import java.util.ArrayList;
import java.util.List;

public class MyTestAdapter2 extends RecyclerView.Adapter<MyTestAdapter2.myholder> {

    private Context context;
    private List<StudyBean> lists;
    public MyTestAdapter2(Context cc, List<StudyBean> datas){
        this.context=cc;
        this.lists=datas;
    }


    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myholder(LayoutInflater.from(context).inflate(R.layout.test_study_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull myholder holder, int position) {
        StudyBean bean=lists.get(position);
        holder.path.setText(bean.getPath());
        holder.name.setText(bean.getName());
        holder.cb_dagou.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class myholder extends RecyclerView.ViewHolder{

        private TextView path,name;
        private CheckBox cb_dagou;
        public myholder(View itemView) {
            super(itemView);
            path=itemView.findViewById(R.id.tv_path);
            name=itemView.findViewById(R.id.tv_name);
            cb_dagou=itemView.findViewById(R.id.cb_dagou);
        }
    }
}
