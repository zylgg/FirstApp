package com.example.jhzyl.firstapp

import org.junit.Test
import kotlin.random.Random

class ExampleUnitTest2 {

    //计算一下30%出现的概率有多高，，，
    fun printWhileS() :String {
        var k = 0
        var sum_30 :Int=0;
        var sum_70 :Int=0;
        while (k<10) {
            var a: Int = Random.nextInt(10);

            if (a < 4) sum_30++ else sum_70++

            k++;
        }
        return sum_30.toString()+"_"+sum_70

    }

    fun printWhileS2() {
        var k = 0
        while (k<10) {
            val a: Int = Random.nextInt(10);
            println(a)
            val m=if (a < 4) "30%" else "70%"
            println("概率是：" + m)
            k++;
        }
    }


    @Test
    fun main(){
//        printWhileS2()
//        println(printWhileS());
//        foreach(TestKotlin_6.mmmm2);
//        TestKotlin_6.init();
//        println("静态初始化");
//        foreach(TestKotlin_6.mmmm2)

//        var testkotlin6=TestKotlin_6();
//        val countsByNet = testkotlin6.getCountsByNet(Testkotlin_6s.counts(1F))
//        println(countsByNet);


        //测试枚举
//        println("name: ${TestKotlin_enum.SPRING.name}" +
//                ",ordinal:${TestKotlin_enum.SPRING.ordinal} " +
//                ", temperature： ${TestKotlin_enum.SPRING.temperature}")
//
//        println("name: ${TestKotlin_enum2.SATURDAY.name}" +
//                ",ordinal:${TestKotlin_enum2.SATURDAY.ordinal} " +
//                ", temperature： ${TestKotlin_enum2.SATURDAY.temperature}")
//        test()
        //单例测试
//        UserDataBean_lazy.getInstance();
//        val gets = UserDataBean_lazy.gets;
//        var str=gets!!.age.toString()+"--"+gets.name;
//        println(str);
//
//
//        UserDataBean_hungry.getInstance();
//        var age2=UserDataBean_hungry.age;
//        var heights=UserDataBean_hungry.heights;
//        println(age2.toString()+"--"+heights);

//        var datas= mutableListOf<Int>(1,2,3,4,5,6,7,8,9);
//        datas.addStrByLastIndex(datas);

        val testkotlin1 = TestKotlin_1()
        testkotlin1.printDownTo();
        testkotlin1.printStep();
        testkotlin1.printUntil()

    }

    //一个对数组的扩展函数   --->增加最后一个数的值
    fun MutableList<Int>.addStrByLastIndex(aa:MutableList<Int>){
        for ((i,value) in aa.withIndex()){
            if (i==aa.size-1) {
                aa[i]=aa[i]+100;
                println("lastValue:"+value)
                break;
            }
        }
        aa.forEach {
            print("--"+it)
        }
    }


    fun isOdd(x: Int) = x % 2 != 0

    fun test() {
        var list = listOf(1, 2, 3, 4, 5)
        println(list.filter(this::isOdd))
    }


    fun foreach(array:IntArray){
        array.forEach {
            println(it)
        }
    }

}

