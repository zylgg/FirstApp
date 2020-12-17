package com.example.jhzyl.firstapp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes
//open{：.keyword}标注可以理解为Java中final{：.keyword}的反面，它允许其他他类 从这个类中继承
open class TestKotlin_2 : View {
    //如果子类没有主构造，那么每个二级构造函数初始化基类型，使用super{：.keyword}关键字，或委托给另一个构造函数做到这一点。
    constructor(ctx:Context) : super(ctx){

    }
    constructor(attr: AttributeSet, ctx:Context):super(ctx,attr){

    }

    open fun getNewWidth(){

    }

    fun getTz(){

    }


//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        setMeasuredDimension(getDefaultSize(100,widthMeasureSpec), getDefaultSize(200,widthMeasureSpec))
//    }
}