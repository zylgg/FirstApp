package com.example.jhzyl.firstapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestListIterator {

    public void testKotlin_1(){
        TestKotlin_1 kotlinBean=new TestKotlin_1();
//        int i = kotlinBean.sum(kotlinBean.getS(), kotlinBean.getS2());
//        String fuzhi = kotlinBean.fuzhi("null");
//        boolean withs = kotlinBean.withss(kotlinBean.getA1(), kotlinBean.getB1());
//        String getstrsss = kotlinBean.getstrsss();
        String printssss = kotlinBean.printssss();
        System.out.println("R_E_S_U_L_T:"+printssss);
    }

    public static void main(String[] args) {
        TestListIterator iterator=new TestListIterator();
        iterator.testKotlin_1();


//        List<student> mLists=new ArrayList<>();
//        mLists.add(new student("小张"));
//        mLists.add(null);
//        mLists.add(new student("小李"));
//        mLists.add(null);
//        mLists.add(new student("小华"));
//        mLists.add(null);
//        mLists.add(new student("小李s"));
//
//
//        System.out.println(""+mLists.size());
//        for (student s:mLists){
//            if (s==null)continue;
//            System.out.println(""+s.getName());
//        }
//        TestListIterator.bubbleSort(NUMBERS);

    }
    // 排序原始数据
    private static final int[] NUMBERS ={49, 38, 65, 97, 76, 13, 27};

    public static void bubbleSort(int[] array) {
        int temp = 0;
        for (int i = 0; i < array.length - 1; i++) {//0-6
            for (int j = array.length - 2; j >=0; j--) {//0-5 0-4  0-3  0-2  0-1 0
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(array) + " bubbleSort");
    }
}
