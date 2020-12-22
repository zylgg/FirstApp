package com.example.jhzyl.firstapp


//Any不属于java.lang.Object;特别是， 任何其他任何成员，甚至连equals()，hashCode()和toString()都没有
open class TestKotlin_3 : TestKotlin_1() {

    //成员标记为override{：.keyword}的本身是开放的，也就是说，它可以在子类中重写。

    override fun startClass0() {
        super.startClass0()
    }


    //如果你想禁止重写的，使用final
    //即：有类继承TestKotlin_3时 被final修饰的startClass就不能重写了，反之没final有override可以重写
    final override fun startClass() {
        super.startClass()
    }



}