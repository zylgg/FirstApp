package com.example.jhzyl.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LoopLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_layout);

        LooperLayout looperview = findViewById(R.id.looperview);
        looperview.setTipList(generateTips());
    }
    private List<String> generateTips() {
        List<String> tips = new ArrayList<>();
        tips.add("赵丽颖");
        tips.add("杨颖");
        tips.add("郑爽");
        tips.add("杨幂");
        tips.add("刘诗诗");
        tips.add("迪丽热巴");
        tips.add("李沁");
        tips.add("唐嫣");
        tips.add("林心如");
        tips.add("陈乔恩");
        tips.add("范冰冰");
        tips.add("刘亦菲");
        tips.add("李小璐");
        tips.add("佟丽娅");
        return tips;
    }
}
