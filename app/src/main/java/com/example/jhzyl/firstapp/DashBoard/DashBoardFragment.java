package com.example.jhzyl.firstapp.DashBoard;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jhzyl.firstapp.R;

public class DashBoardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dash_board_fragment_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView tv_dash_board_title = view.findViewById(R.id.tv_dash_board_title);
        if (Build.VERSION.SDK_INT >= 19) {
            tv_dash_board_title.getLayoutParams().height=tv_dash_board_title.getLayoutParams().height+dip2px(getContext(),25);//25dp是状态栏高度

            tv_dash_board_title.setPadding(0,dip2px(getContext(),25),0,0);
        }
        tv_dash_board_title.setGravity(Gravity.CENTER);
        tv_dash_board_title.setText("DashBoard");
    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
