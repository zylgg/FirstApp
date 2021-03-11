package com.example.jhzyl.firstapp

class UserDataBean_lazy private constructor() {
    var age = 1;
    var name = "李四"

//        companion object {                                                    //单例新手推荐写法
//        private var instance: UserDataBean_lazy? = null
//
//        fun get(): UserDataBean_lazy{
//            if (instance==null){
//                instance=UserDataBean_lazy();
//            }
//            return instance!!
//        }
//    }

//    companion object{//同上
//        private var instance :UserDataBean_lazy?=null
//        get() {
//            if (field==null){
//                field= UserDataBean_lazy();
//            }
//            return field;
//        }
//        fun get():UserDataBean_lazy{
//            return instance!!;
//        }
//    }

    companion object{//同上
        val get:UserDataBean_lazy by lazy(LazyThreadSafetyMode.NONE) {
            UserDataBean_lazy();
        }
    }
//以上三种写法结果一致。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。

//    companion object{//线程安全的懒加载                                            //单例新手推荐写法
//        private var instance:UserDataBean_lazy?=null
//
//        @Synchronized
//        fun get():UserDataBean_lazy{
//            if (instance==null){
//                instance= UserDataBean_lazy();
//            }
//            return instance!!;
//        }
//
//    }

//    companion object {//线程安全的懒加载
//
//        private var instance: UserDataBean_lazy? = null
//            get() {
//                if (field == null) {
//                    field = UserDataBean_lazy()
//                }
//                return field
//            }
//
//        @Synchronized
//        fun get(): UserDataBean_lazy{
//            return instance!!
//        }
//    }

//    companion object{
//        val gets:UserDataBean_lazy by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
//            UserDataBean_lazy();
//        }
//    }
//以三种（线程安全）写法结果一致。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。




    //    companion object {//双重校验锁式
//
//        @Volatile
//        private var instance: UserDataBean_lazy? = null
//
////        @Synchronized
//        fun get(): UserDataBean_lazy{
//            if (instance==null){
//                synchronized (this){//将synchronize加载方法里面，只有在实例为空的时候，才同步。
//                    if (instance==null) instance=UserDataBean_lazy();
//                }
//
//            }
//            return instance!!
//        }
//    }
//    companion object {
//        fun getInstance() {
//            println("这是_静态内部类式_懒汉式单例");
//        }
//
//        //静态内部类式
//        var gets: UserDataBean_lazy? = Myhandles.holders;
//
//        object Myhandles {
//            var holders = UserDataBean_lazy();
//        }
//    }


}