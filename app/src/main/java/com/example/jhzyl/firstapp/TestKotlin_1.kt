package com.example.jhzyl.firstapp

import android.app.Activity
import kotlin.random.Random

open class TestKotlin_1 {

    //主构造函数   在Kotlin中的类可以有主构造函数 和一个或多个二级构造函数。
    class TestKotlin_1 constructor(name: String) {
        var names: String = "";
        var old: Int = 0;
        var high: Float = 1f;

        init {//这个主构造函数不能包含任何的代码。初始化的代码可以被放置在（初始的模块），以init为前缀
            this.names = name;
        }

        //二级构造
        constructor(name: String, old: Int) : this(name) {
            this.old = old;
        }

        //二级构造
        constructor(name: String, old: Int, high: Float) : this(name, old) {
            this.high = high;
        }
    }

    //加 open 子类就能重写了
    open fun startClass0() {
        println("可被重写")
    }

    //加了open子类可以重写我，
    open fun startClass() {
        var p1 = TestKotlin_1("张三")
        println("姓名" + p1.names)
        var p2 = TestKotlin_1("李四", 18, 180f);
        println("姓名" + p2.names + "_年龄" + p2.old + "_身高" + p2.high)
    }

    //没加 open 子类就不能重写了
    fun startClass2() {
        println("我不能被重写哦")
    }

    //延迟初始化属性
    lateinit var subject: TestKotlin_1

    fun setup() {
        subject = TestKotlin_1("小二")

    }

    fun test() {
//         subject.method()  // dereference directly
    }


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


    //模板表达式，即一些小段代码，会求值并把结果合并到字符串中
    val i = 1

    val str = "i =$i" // evaluates to "i = 1"

//包含一个简单的名称；或者用花括号扩起来，内部可以是任意表达式：

    val s = "abc"

    val str2 = "$s.length is ${s.length}"// evaluates to "abc.length is 3"


    fun getstrsss(): String {
        return (str + "_______" + str2);
    };

    private var class11: List<*> = mutableListOf("", "");
    private var return011: Int = 0
        get() = if (field < 0) 0 else field;


    val arrays: Array<String> = arrayOf("11", "22");
    val arrays2 = arrayOfNulls<String>(10);
    var arrays3 = Array(10, { i -> (i + 1) });
    var arrays4 = (0..9).toList();
    var intArrays = intArrayOf(0, 1, 2, 3, 4);

    fun printIntArrays(): String {
        var s = "";
        for (c in arrays3) {
            s = s + "_" + c;
        }
        return s;
    }

    var chars1: Char = '9';
    fun printCharSS(c: Char): String {
        if (c !in '0'..'9') throw IllegalArgumentException("Out of range")
        println(c.toInt());
        println("0".toInt());
        println("9".toInt());
        return "" + (c.toInt() - '0'.toInt());
    }


    fun printssss(): String? {
        var w: String = "";

        arrays2.set(0, "a");
        arrays2.set(1, "b");
        arrays2.set(2, "c");
        arrays2.set(3, "d");

        for (c in arrays2) {
            w = w + "__" + c;
        }
        return w;
    }

    fun printWhen(a: Int): String =
            when (a) {
                1 -> "111";
                2 -> "2222";
                else -> "else";
            }

    fun printWhenInteger(a: Int) {
        println(when (a) {
            1, 2, 3, 4, 5, 6, 7, 8, 9 -> "123456789";
            else -> "else integer";
        })
    }

    fun printWhenOther(x: Any) {
        when (x) {
            in (1..9) -> println("在自然数1到9里");
            "Hello World" -> println("hello world");
            is Int -> println("is integer")
            is Float -> println("this is float");
            else -> println("nothing");

        }
    }

    fun printWhenOther2(x: Int): String {
        when {
            x == 6 -> return "x==6";
            x == 7 -> return "x==7"
            else -> return "else"
        }
    }

    fun printForPrint() {
        loop@ for (i in 11..15) {
            println("1次循环" + i)
            for (j in 1..5) {

                if (j == 3)
                    continue@loop

                println("2次循环" + j)
            }

        }
    }

    fun returnFoo0() {
        var i = (11..15)
//        i.forEach {
//            if (it == 0) return@a1
//            println("第一层循环"+it)
//        }
        i.forEach(fun(value: Int) {
            if (value == 13) return
            println(value)
        })
    }

    fun returnFoo() {
        var i = (11..15)
        var its = (-3..5)
        i.forEach {
            println("第一层循环" + it)

            its.forEach() {
                if (it == 0) return
                println("第二层循环" + it)
            }
        }
    }

    //     Range默认是自增长的，如果是for(i in 10..0)则不会做任何事情，这时可以使用downTo，如：
    fun printDownTo() {
        for (i in 10 downTo  0) {
            println(i);
        }
    }

    //     我们还可以 使用step来定义间隙，如：
    fun printStep() {
        for (i in 1..4 step 2) {
            println("i--->"+i)
        }
    }
//     上面的代码只会遍历到1、3


    //  如果想创建一个开区间，可以使用until，如：
    fun printUntil() {
        for (i in 0 until 4) {
            print(i)
        }
    }


    fun returnFoo1() {
        var i = (11..15)
        var its = (-3..5)
        i.forEach lit@{
            println("第一层循环" + it)

            its.forEach() {
                if (it == 0) return@lit
                println("第二层循环" + it)
            }
        }
    }

    fun returnFoo2() {
        var i = (11..15)
        var its = (-3..5)
        i.forEach {
            println("第一层循环" + it)

            its.forEach() lit@{
                if (it == 0) return@lit
                println("第二层循环" + it)
            }
        }
    }


    //计算一下30%出现的概率有多高，，，
    fun printWhileS(): String {
        var k = 0
        var sum_30: Int = 0;
        var sum_70: Int = 0;
        while (k < 10) {
            var a: Int = Random.nextInt(10);

            if (a < 4) sum_30++ else sum_70++

            k++;
        }
        return sum_30.toString() + "_" + sum_70

    }

    fun printWhileS2() {
        var k = 0
        while (k < 10) {
            var a: Int = Random.nextInt(10);
            println(a)
            val m = if (a < 4) "30%" else "70%"
            println("概率是：" + m)
            k++;
        }
    }


}