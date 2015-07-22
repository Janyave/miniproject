package com.netease.ecos.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.netease.ecos.activity.MyApplication;
import com.netease.ecos.constants.FileManager;

import java.io.File;


public class SDImageCache implements ImageLoader.ImageCache {
	
	private static final String TAG = "SDImageCache";
	@Override
	public Bitmap getBitmap(String url) {
//		System.out.println("getBitmap(String url, Bitmap bitmap)");
		File imageFile = FileManager.getInstance().getImgFile();
		 
		 //用MD5加工后的url加上”.png“作为文件名，去下载图片文件中查找是否有对应的文件吗，有则使用该图片设置imageView
		File file = new File(imageFile,StringUtils.hashKeyForDisk(url)+".png");
		if(ImageTools.isImageExist(file))
		{
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			return bitmap;
		}
		return null;
	}


	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		System.out.println("putBitmap(String url, Bitmap bitmap)");


		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		System.out.println("width:"+width+"  ,height"+height);


		int reqWidth =150;
		int reqHeight =150;
		int inSampleSize = 1;
		 if (height > reqHeight && width > reqWidth) {
	            // 计算出实际宽高和目标宽高的比率
	            final int heightRatio = Math.round((float) height / (float) reqHeight);
	            final int widthRatio = Math.round((float) width / (float) reqWidth);
	            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
	            // 一定都会大于等于目标的宽和高。
	            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	        }

		Log.i(TAG, "inSampleSize:" + inSampleSize);
		Matrix matrix = new Matrix();

		matrix.postScale(1.0f/inSampleSize, 1.0f/inSampleSize);

		bitmap = bitmap.createBitmap(bitmap, 0, 0, width,
				                                  height, matrix, false);

		File imageFile = FileManager.getInstance().getImgFile();

		//通过MD5加工后的url加上".png"作为该图片存于本地的文件名，存于本地后
		File file = new File(imageFile,StringUtils.hashKeyForDisk(url)+".png");
		ImageTools.saveBitmap(bitmap,file,false);


		//刷新图片库中的该图片
		
		MyApplication.getMediaScanner().scanFile(file.getAbsolutePath(),"image/png");
	}

}
