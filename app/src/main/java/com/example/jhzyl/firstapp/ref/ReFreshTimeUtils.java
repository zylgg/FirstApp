package com.example.jhzyl.firstapp.ref;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 列表刷新时间
 * @author 098
 *
 */
public class ReFreshTimeUtils {
	/** SharedPreferences 成员变量 **/
	private SharedPreferences sp;
	/** Editor 编辑器 **/
	private Editor editor;
	/**
	 * 
	 * @param context
	 * @param name
	 * @param mode
	 */
	public ReFreshTimeUtils(Context context, String name, int mode){
		this(context,name,mode,true);
	}
	
	/**
	 * 构造函数
	 * @param context context
	 * @param name  name
	 * @param mode mode
	 * @param isInitEditor 是否编辑
	 */
	public ReFreshTimeUtils(Context context, String name, int mode,
			boolean isInitEditor) {
		sp = context.getSharedPreferences(name, mode);
		initEditor(isInitEditor);
	}
	
	/**
	 *  初始化编辑器 
	 *
	 * @param isInitEditor 是否初始化
	 */
	public void initEditor(boolean isInitEditor) {
		if (isInitEditor) {
			editor = sp.edit();
		}
	}
	
	/***
	 * 存储String类型的数据(默认直接提交)
	 * 
	 * @param key key 
	 * @param value value
	 */
	public void saveStringKey(String key, String value) {
		saveStringKey(key,value,true);
	}
	
	/**
	 * 存储String类型的数据
	 * @param key key
	 * @param value value
	 * @param isCommit  是否提交
	 */
	public void saveStringKey(String key, String value,boolean isCommit) {
		editor.putString(key, value);
		commitEditor(isCommit);
		
	}
	/**
	 * 获取String型的值
	 * @param key key
	 * @param defValue 默认值
	 * @return String型的值
	 */
	public String loadStringKey(String key, String defValue) {
		return sp.getString(key, defValue);
	}
	
	/**
	 * 是否提交，储存数据
	 * @param isCommit 是否提交
	 */
	private void commitEditor(boolean isCommit) {
		if(isCommit){
			editor.commit();
		}
	}
}
