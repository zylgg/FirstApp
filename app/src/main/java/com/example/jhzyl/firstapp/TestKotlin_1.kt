package com.example.jhzyl.firstapp

import java.sql.DriverManager
import java.sql.DriverManager.println

class TestKotlin_1 {
//    var s:Int =123;
//    var s2:Int =456;
//    var s3:String= "11";
//
//
//    val a: Int = 1000
//    val b: Int = 1000
////    println(a==b)    //true
////    println(a===b)   //true
////上面返回的都是true，因为a,b它们都是以原始类型存储的，类似于Java中的基本数字类型。
//
//    fun withss(s:Int,s2:Int) :Boolean{
//        return s==s2;
//    }
//    fun withsss(s:Int?,s2:Int?) :Boolean{
//        return s===s2;
//    }
//    //
//    val a1: Int? = 1000
//    val b1: Int? = 1000
////    println(a1==b1)    //true
////    println(a1===b1)   //false
//
//    fun sum(s:Int, s2:Int) :Int{
//        return s+s2;
//    }
//
//    fun fuzhi(s:String) :String? {
//        s3=s;
//        return s3;
//    }

    val i = 1

    val str = "i =$i" // evaluates to "i = 1"

//

    val s ="abc"

    val str2 = "$s.length is ${s.length}"// evaluates to "abc.length is 3"


    fun getstrsss() :String{
        return (str+"_______"+str2);
    };

    val arrays:Array<String> = arrayOf("11","22");
    val arrays2 = arrayOfNulls<String>(10);

    fun printssss() : String? {
        var w:String="";
//        for (c in arrays){
//            w=w+"__"+c;
//        }

        arrays2.set(0,"a");
        arrays2.set(1,"a");
        arrays2.set(2,"b");
        arrays2.set(3,"c");
        arrays2.set(4,"d");
        arrays2.set(5,"a");
        arrays2.set(6,"b");
        arrays2.set(7,"c");
        arrays2.set(8,"d");
        arrays2.set(9,"e");

        for (c in arrays2){
            w=w+"__"+c;
        }
        return w;
    }














}