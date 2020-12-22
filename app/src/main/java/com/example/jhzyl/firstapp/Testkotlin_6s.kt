package com.example.jhzyl.firstapp

sealed class Testkotlin_6s {//类封闭---类封闭类似java的枚举----关键字sealed
    class counts(val num:Float) : Testkotlin_6s()
    class Integers(val i:Int) : Testkotlin_6s()
    class Strings(val str:String) : Testkotlin_6s()
}