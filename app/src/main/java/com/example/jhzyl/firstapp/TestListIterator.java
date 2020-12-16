package com.example.jhzyl.firstapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestListIterator {

    public static void main(String[] args) {
        TestKotlin_1 kotlinBean = new TestKotlin_1();
//        int i = kotlinBean.sum(kotlinBean.getS(), kotlinBean.getS2());
//        String fuzhi = kotlinBean.fuzhi("null");
//        boolean withs = kotlinBean.withss(kotlinBean.getA1(), kotlinBean.getB1());
////        String getstrsss = kotlinBean.getstrsss();
//        kotlinBean.printCharSS(kotlinBean.getChars1());
//        String printssss = kotlinBean.printIntArrays();
//        System.out.println(""+printssss);

//        String s = kotlinBean.printWhenOther2(6);
//        System.out.println(s);
//          kotlinBean.printForPrint();
          kotlinBean.returnFoo0();

//        String s1 = kotlinBean.printWhen(1);
//        String s2 = kotlinBean.printWhen(2);
//        kotlinBean.printWhenInteger(0);
//        kotlinBean.printWhenInteger(2);
//        kotlinBean.printWhenInteger(3);
//        kotlinBean.printWhenInteger(5);

//        kotlinBean.printWhenOther(2);
//        kotlinBean.printWhenOther("Hello World");
//        kotlinBean.printWhenOther(123);
//        kotlinBean.printWhenOther(3F);
//        kotlinBean.printWhenOther("");

//        System.out.println("_____RESULT_____:" + s1);
//        System.out.println("_____RESULT_____:" + s2);
//        kotlinBean.printWhileS();


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
    private static final int[] NUMBERS = {49, 38, 65, 97, 76, 13, 27};

    public static void bubbleSort(int[] array) {
        int temp = 0;
        for (int i = 0; i < array.length - 1; i++) {//0-6
            for (int j = array.length - 2; j >= 0; j--) {//0-5 0-4  0-3  0-2  0-1 0
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
