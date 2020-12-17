package com.example.jhzyl.firstapp

class TestKotlin_4 : TestKotlin_3() {

}

open class A {
    open fun f() { println("A") }

    fun a() { println("a") }

}



interface B {
    fun f() { println("B") } // interfacemembers are 'open' by default

    fun b() { println("b") }

}



class C() : A(), B{
    // 从中继承的实现而采取的父类型，我们使用super{：.keyword}在尖括号，如规范的父名super<Base>：
    override fun f() {
        super<A>.f() // 调用A类的f() 方法

        super<B>.f() // 调用B类的f() 方法

    }

    override fun b() {//实现b接口的方法
        super.b()
    }
}