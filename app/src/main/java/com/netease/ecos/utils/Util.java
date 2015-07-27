package com.netease.ecos.utils;

import android.app.Activity;
import android.os.Environment;

/**
 * 该类封装了一些小工具
 */

public class Util {
	private static Util util;
	public static int flag = 0;	// 正在运行的异步线程个数
	private Util(){
		
	}
	
	public static Util getInstance(){
		if(util == null){
			util = new Util();
		}
		return util;
	}
	
	/**
	 * 判断是否有sdcard
	 * @return
	 */
	public boolean hasSDCard(){
		boolean b = false;
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			b = true;
		}
		return b;
	}
	
	/**
	 * 得到sdcard路径
	 * @return
	 */
	public String getExtPath(){
		String path = "";
		if(hasSDCard()){
			path = Environment.getExternalStorageDirectory().getPath();
		}
		return path;
	}
	
	/**
	 * 得到/data/data/package包(yanbin.imagelazyload) 目录
	 * @param mActivity
	 * @return
	 */
	public String getPackagePath(Activity mActivity){
		return mActivity.getFilesDir().toString();
	}

	/**
	 * 根据path得到图片名
	 * @param url
	 * @return
	 */
	public String getImageName(String url) {
		String imageName = "";
		if(url != null){
			imageName = url.substring(url.lastIndexOf("/") + 1);
		}
		return imageName;
	}
}
