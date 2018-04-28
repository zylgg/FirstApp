package com.example.jhzyl.firstapp.Home.bean;

import java.util.ArrayList;
import java.util.List;

public class SuperEntity {
    public static final int BigItemType = 0, MiddleItemType = 1, SmallItemType = 2;
    private int pos;
    private int type;
    private int refresh_count=0;
    public static List<SuperEntity> mDatas=new ArrayList<>();
    static {
       for (int i=0;i<6;i++){
           mDatas.add(getType(i));

           mDatas.add(getType1(i));
           mDatas.add(getType1(i));
           mDatas.add(getType1(i));
           mDatas.add(getType1(i));

           mDatas.add(getType2(i));
           mDatas.add(getType2(i));
           mDatas.add(getType2(i));
           mDatas.add(getType2(i));
           mDatas.add(getType2(i));
           mDatas.add(getType2(i));
       }
    }
    private static SuperEntity getType(int pos){
        return new SuperEntity(pos,BigItemType);
    }
    private static SuperEntity getType1(int pos){
        return new SuperEntity(pos,MiddleItemType);
    }
    private static SuperEntity getType2(int pos){
        return new SuperEntity(pos,SmallItemType);
    }

    public int getRefresh_count() {
        return refresh_count;
    }

    public void setRefresh_count(int refresh_count) {
        this.refresh_count = refresh_count;
    }

    public SuperEntity(int pos, int type) {
        this.pos = pos;
        this.type = type;
        this.refresh_count=0;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
