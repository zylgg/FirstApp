package com.example.jhzyl.firstapp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebSettings
import android.widget.TextView
import com.example.jhzyl.firstapp.databinding.KotlinBuilderLayoutBinding
import java.awt.font.TextAttribute

class TestKotlin_builder(context: Context, themeResId: Int) : Dialog(context, themeResId) {


    class Builder(val context: Context){
        //初始化一些默认属性
        private var textColor : Int = Color.BLUE;
        private var textSize : Int=16
        private var text:String="Hello world"
        private var isCancelOnBack:Boolean=false
        private var viewBackgroundColor:Int=Color.WHITE;
        private var isCancelOutSide:Boolean=false;
        private var isOnClickListener:View.OnClickListener?=null;

        fun setTextColor(color:Int): Builder{
            this.textColor=color;
            return this;
        }
        fun setTextSize(size:Int):Builder{
            this.textSize=size;
            return this;
        }
        fun setText(text:String):Builder{
            this.text=text;
            return this;
        }
        fun setCancelOnBack(isCancel:Boolean):Builder{
            this.isCancelOnBack=isCancel;
            return this;
        }
        fun setViewBackgroundColor(color:Int) :Builder{
            this.viewBackgroundColor=color;
            return this;
        }
        fun setIsCancelOutSide(isCancel:Boolean) :Builder{
            this.isCancelOutSide=isCancel
            return this;
        }
        fun setOnClickListener(listener:View.OnClickListener):Builder{
            this.isOnClickListener=listener
            return this;
        }
        fun create():TestKotlin_builder{
            val testkotlinBuilder = TestKotlin_builder(context, R.style.dialog)
            var view=LayoutInflater.from(context).inflate(R.layout.kotlin_builder_layout,null,false);
//            val text = view.findViewById<TextView>(R.id.tv_kt_text)
            val bind = KotlinBuilderLayoutBinding.bind(view);
            bind.root.setBackgroundColor(this.viewBackgroundColor)
            bind.tvKtText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,this.textSize.toFloat());
            bind.tvKtText.setTextColor(this.textColor)
            bind.tvKtText.setText(this.text);
            bind.btKtOk.setOnClickListener { v ->
                testkotlinBuilder.dismiss()
                if (this.isOnClickListener != null) {
                    this.isOnClickListener!!.onClick(v)
                }
            }

            testkotlinBuilder.setContentView(view)
            testkotlinBuilder.setCancelable(this.isCancelOnBack)
            testkotlinBuilder.setCanceledOnTouchOutside(this.isCancelOutSide)
            return testkotlinBuilder;
        }

    }

}