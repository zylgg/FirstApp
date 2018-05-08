package com.example.jhzyl.firstapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MyToast {

    public static void T(Context context, String message) {
        View view = View.inflate(context, R.layout.toast_decor_layout, null);
        LinearLayout ll_toast_decor_root = view.findViewById(R.id.ll_toast_decor_root);
        ll_toast_decor_root.getLayoutParams().width = SystemAppUtils.getScreenWidth(context);

        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.TOP, 0, 0);
        TextView tv_toast_decor_text = view.findViewById(R.id.tv_toast_decor_text);
        tv_toast_decor_text.setText(message);

        try {
            Object mTN = null;
            mTN = getField(toast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
                }
            }
        } catch (Exception e) {
            Log.i("error", "T: Exception");
        }

        toast.show();
    }

    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }
}
