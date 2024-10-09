package com.example.jhzyl.firstapp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebSettings
import android.widget.TextView
import com.example.jhzyl.firstapp.adapter.MyTestAdapter
import com.example.jhzyl.firstapp.adapter.MyTestAdapter2
import com.example.jhzyl.firstapp.bean.StudyBean
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

        var ListOnes= ArrayList<StudyBean>();
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


            ListOnes.add(StudyBean("湖北武汉江汉区","小张",false))
            ListOnes.add(StudyBean("湖北武汉江汉区","李四",false))
            ListOnes.add(StudyBean("湖北武汉江汉区","王五",false))
            ListOnes.add(StudyBean("湖北武汉江汉区","六六",false))
            ListOnes.add(StudyBean("湖北武汉江汉区","77",false))
            ListOnes.add(StudyBean("湖北武汉江汉区","88",false))
            ListOnes.add(StudyBean("湖北武汉江汉区","99",false))
            ListOnes.add(StudyBean("湖北武汉江汉区","00",false))
            val myTestAdapter = MyTestAdapter(context, ListOnes)
            bind.rvMytest.adapter=myTestAdapter
            myTestAdapter.notifyDataSetChanged()

            bind.btKtCrop.setOnClickListener {
                val selectLists2 = myTestAdapter.selectLists2
                if (selectLists2.isEmpty())return@setOnClickListener

                val myTestAdapter2 = MyTestAdapter2(context, selectLists2);
                bind.rvMytest2.adapter= myTestAdapter2
                myTestAdapter2.notifyDataSetChanged()
            }
            bind.btKtCropOk.setOnClickListener {
                //裁剪名字
                myTestAdapter.selectLists2[0].path="----"
                val myTestAdapter2 = MyTestAdapter2(context,myTestAdapter.selectLists2);
                bind.rvMytest2.adapter= myTestAdapter2
                myTestAdapter2.notifyDataSetChanged()
            }


            testkotlinBuilder.setContentView(view)
            testkotlinBuilder.setCancelable(this.isCancelOnBack)
            testkotlinBuilder.setCanceledOnTouchOutside(this.isCancelOutSide)
            return testkotlinBuilder;
        }

    }

}