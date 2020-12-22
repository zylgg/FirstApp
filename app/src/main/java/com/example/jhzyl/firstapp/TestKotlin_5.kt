package com.example.jhzyl.firstapp

class TestKotlin_5 : A2(), B2 {
    override fun eat() {
        TODO("Not yet implemented")
        println("let's to eat 零食")
    }

    override fun eat2() {
        TODO("Not yet implemented")
    }

    override fun f() {
        super.f()
    }
}

class Testkotlin_5s : A2() {
    override fun eat() {
        println("let's to eat 牛肉干")
    }

    override fun eat2() {
        TODO("Not yet implemented")
    }

}

class mainclass {
    fun main() {
        var test: A2 = TestKotlin_5();
        test.eat();
        test = Testkotlin_5s();
        test.eat();
    }
}


//接口
interface B2 {
    fun f() {
        print("B")
    }
}
interface B3 {
    //接口和抽象类不同的是------>1接口不能保存状态；可以有属性但必须是抽象的 或 提供访问实现。
    val property: Int
    val propertyWithImplementation: String
        get() = "foo"


    fun fo1()//类似于 java 8。可以包含抽象方法
    fun fo2() {//，以及方法的实现
        print(property)
    }
}
class Child :B3 {
    override val property: Int=123;

    override fun fo1() {
        TODO("Not yet implemented")
    }

    override fun fo2() {
        super.fo2()
    }

}


//抽象类
abstract class A2 {
    //抽象成员在本类中可以不用实现。 因此，当一些子类继承一个抽象的成员，它并不算是一个实现：
    abstract fun eat() //不需要标注一个抽象类或者函数为 open
    abstract fun eat2()
}


open class Base {
    open fun ffff() {}
}

abstract class Derived : Base() {
    var str: String = "";
    override abstract fun ffff()    //另外：可以重写一个open非抽象成员使之为抽象的。
}

class Derived2 : Derived() {
    override fun ffff() {
        str = "123456";
        TODO("Not yet implemented")
    }
}




