package com.example.jhzyl.firstapp;

import org.junit.Test;

import java.util.Arrays;

public class ExampleUnitTest3 {
    int acount=0;//循环次数

    @Test
    public void main(){
         int arr[]={1,6,23,7,2,6,8,4};
         for(int i=0;i<arr.length;i++){
             for (int j=0;j<arr.length-1;j++){//1 6 7 2 6 8 4 23
                 if(arr[j]>arr[j+1]) {
                     int temp = arr[j];
                     arr[j] = arr[j+1];
                     arr[j+1] = temp;
                 }
                 acount++;
             }
             //遍历并输出每个元素
             // [1, 6, 7, 2, 6, 8, 4, 23]
             //[1, 6, 2, 6, 7, 4, 8, 23]
             //[1, 2, 6, 6, 4, 7, 8, 23]
             //[1, 2, 6, 4, 6, 7, 8, 23]
             //[1, 2, 4, 6, 6, 7, 8, 23]
             //[1, 2, 4, 6, 6, 7, 8, 23]
             //[1, 2, 4, 6, 6, 7, 8, 23]
             //[1, 2, 4, 6, 6, 7, 8, 23]
             System.out.println(Arrays.toString(arr));
         }
        System.out.println("acount="+acount);

     }

    public void main2(){
        int arr[]={1,6,23,7,2,6,8,4};
        for(int i=0;i<arr.length;i++){
            System.out.println("-----------");
            for (int j=0;j<arr.length-1-i;j++){
                //1 6 7 2 6 7 8            23  //每次都会排列好一个,so,第二个for每次都要少循环一遍
                //1 6 7 2 6 7            8 23
                //1 6 7 2 6            7 8 23
                //1 6 7 2            6 7 8 23
                //1 6 7            2 6 7 8 23
                //1            6 7 2 6 7 8 23
                if(arr[j]>arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
                acount++;
            }
            //遍历并输出每个元素
            System.out.println(Arrays.toString(arr));
        }

        System.out.println("acount="+acount);

    }
}
