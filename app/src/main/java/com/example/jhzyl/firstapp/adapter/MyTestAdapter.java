package com.example.jhzyl.firstapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.bean.StudyBean;
import com.example.jhzyl.firstapp.databinding.TestStudyItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MyTestAdapter extends RecyclerView.Adapter<MyTestAdapter.myholder> {

    private Context context;
    private List<StudyBean> lists;
    private List<StudyBean>list2=new ArrayList<>();
    public MyTestAdapter(Context cc, List<StudyBean> datas){
        this.context=cc;
        this.lists=datas;
    }

    public List<StudyBean> getSelectLists2(){
        return list2;
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
        holder.cb_dagou.setChecked(bean.isCheck());
        holder.cb_dagou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    list2.add(bean);
                }else{
                    list2.remove(bean);
                }
            }
        });
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
