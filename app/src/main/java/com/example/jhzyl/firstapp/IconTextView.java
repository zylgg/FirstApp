package com.example.jhzyl.firstapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by nono on 2018/4/6.
 */
@SuppressLint("AppCompatCustomView")
public class IconTextView extends TextView {
    private final String namespace = "http://com.demo0.nono";
    private int resourceId = 0;
    private Bitmap bitmap;
    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        resourceId = attrs.getAttributeResourceValue(namespace, "iconSrc", 0);
        if(resourceId>0) {
            bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(bitmap!=null) {
            Rect src = new Rect();
            Rect target = new Rect();
            src.left = 0;
            src.top = 0;
            src.right = bitmap.getWidth();
            src.bottom = bitmap.getHeight();
            target.top = 0;
            target.left = 0;
            target.bottom = getMeasuredHeight();
            int wid = (getMeasuredHeight()*bitmap.getWidth())/bitmap.getHeight();
            target.right = wid;
            canvas.drawBitmap(bitmap, src, target, getPaint());
            canvas.translate(wid,0);
        }
        super.onDraw(canvas);
    }

}