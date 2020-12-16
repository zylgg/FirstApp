package com.example.jhzyl.firstapp

import org.junit.Test
import java.sql.DriverManager
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

    }

}