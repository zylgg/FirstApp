package com.example.jhzyl.firstapp

object UserDataBean_hungry {
    val age:Int=1;
    val names:String="小张"
    val heights:Float=180f;
    fun getInstance(){
        println("这是饿汉式单例");
    }
}