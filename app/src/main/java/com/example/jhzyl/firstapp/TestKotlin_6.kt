package com.example.jhzyl.firstapp

class TestKotlin_6 {
    var mmmm: IntArray? = null;//当companion object中调用非静态的成员变量也是调用不到的。

    companion object { //companion object 修饰为伴生对象,伴生对象在类中只能存在一个

        var mmmm2: IntArray = intArrayOf(1, 2);//将所引用的成员变量也修饰静态的，这样就可以引用到了
        fun init() {
//            mmmm = intArrayOf(1, 2, 4);  mmmm报错了
            mmmm2 = intArrayOf(1, 2, 3, 4, 5);//mmmm2正常了
        }
    }


    var setterVisibility: String = "abc" // Initializer required, not a nullable type 需要初始化式，不是可空类型
        private set // the setter is private and hasthe default implementation setter是私有的，具有默认实现  外部引用时无法set()赋值

    class Person(name: String) {
        var name: String = name
            get() = field.toUpperCase()
            set(value) {
                field = value
            }

        var sexx: String = "nan"
            get() = field.toLowerCase();
            set(value) {
                field = value + "--->女女女女"
            }

        private var _table:Map<String, Int>? = null
        public val table:Map<String, Int>
            get() {
//                if (_table == null)
//                    _table = HashMap() // 推断类型参数
                return _table ?: throw AssertionError("Set to null by another thread")//被另一个线程设置为null
            }
    }


    fun main() {
        setterVisibility = "123";

        var customer: Person = Person("xiaomin")

        println(customer.name)   // XIAOMIN

        customer.sexx = "lei"

        println(customer.sexx)   // LEI

        println(customer.table);

    }


    fun getCountsByNet(a: Testkotlin_6s): Any {
        when (a) {
            is Testkotlin_6s.counts -> return a.num;
            is Testkotlin_6s.Integers -> return a.i;
            is Testkotlin_6s.Strings -> return a.str;
            else -> return "else";
        }
    }


}