package com.example.jhzyl.firstapp;

public class singleton_test {
    private static singleton_test single;

    private singleton_test(){}

    public static void getInstance(){
        if (single==null){
            synchronized (singleton_test.class){
                if (single==null){
                    single=new singleton_test();
                }
            }
         }
    }
}
