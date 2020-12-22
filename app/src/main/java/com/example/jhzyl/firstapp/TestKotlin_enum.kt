package com.example.jhzyl.firstapp

enum class TestKotlin_enum(val temperature: Int) {

        SPRING(1),
        SUMMER(2),
        AUTUMN(3),
        WINTER(4),


//    println("name: ${Season.SPRING.name} , temperature： ${Season.SPRING.temperature}")
//
//    打印结果：
//    name: SPRING , temperature： 1
}

enum class TestKotlin_enum2(val temperature: Int,val weeks:String) {
        MONDAY(1, "Mon"),
        TUESDAY(2, "Tue"),
        WEDNESDAY(3, "Wed"),
        THURSDAY(4, "Thu"),
        FRIDAY(5, "Fri"),
        SATURDAY(6, "Sat"),
        SUNDAY(7, "Sun")
}