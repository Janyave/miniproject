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
		 
		 //��MD5�ӹ����url���ϡ�.png����Ϊ�ļ�����ȥ����ͼƬ�ļ��в����Ƿ��ж�Ӧ���ļ�������ʹ�ø�ͼƬ����imageView
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
	            // �����ʵ�ʿ�ߺ�Ŀ���ߵı���
	            final int heightRatio = Math.round((float) height / (float) reqHeight);
	            final int widthRatio = Math.round((float) width / (float) reqWidth);
	            // ѡ���͸�����С�ı�����ΪinSampleSize��ֵ���������Ա�֤����ͼƬ�Ŀ�͸�
	            // һ��������ڵ���Ŀ��Ŀ�͸ߡ�
	            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	        }

		Log.i(TAG, "inSampleSize:" + inSampleSize);
		Matrix matrix = new Matrix();

		matrix.postScale(1.0f/inSampleSize, 1.0f/inSampleSize);

		bitmap = bitmap.createBitmap(bitmap, 0, 0, width,
				                                  height, matrix, false);

		File imageFile = FileManager.getInstance().getImgFile();

		//ͨ��MD5�ӹ����url����".png"��Ϊ��ͼƬ���ڱ��ص��ļ��������ڱ��غ�
		File file = new File(imageFile,StringUtils.hashKeyForDisk(url)+".png");
		ImageTools.saveBitmap(bitmap,file,false);


		//ˢ��ͼƬ���еĸ�ͼƬ
		
		MyApplication.getMediaScanner().scanFile(file.getAbsolutePath(),"image/png");
	}

}
