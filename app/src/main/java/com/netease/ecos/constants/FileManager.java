package com.netease.ecos.constants;

import android.os.Environment;

import java.io.File;

/**
 * 类描述：文件操作类
 * Created by enlizhang on 2015/7/22.
 */
public class FileManager {

    /** 文件根目录名称 */
    public static final String APP_DIRECTORY  = "Ecos";

    /** 下载图片目录名称 */
    public static final String DOWNLOAD_IMG_FILE_NAME  = "downloadImage";

    /** 图片目录名称 */
    public static final String IMAGE_FILE_NAME  = "iamge";

    /** 图片缓存目录名称 */
    public static final String TEMP_IMG_FILE_NAME  = "tempImage";


    /** 图片文件夹，用来存储所有图片   */
    private File mImgFile;

    /** 下载图片文件夹，用来存储下载过的图片   */
    private File mDownloadImgFile;

    /** 缓存图片文件夹，存储上传图片过程所涉及的缓存图片  */
    private File mTempImgFile;


    private FileManager(){
        initFile();
    }

    /*** 文件管理单例对象 */
    private static FileManager fileManagerInstance;


    /**
     * 获取应用文件管理对象
     * @return
     */
    public synchronized static FileManager getInstance(){
        if(fileManagerInstance == null)
        {
            fileManagerInstance = new FileManager();
        }
        return fileManagerInstance;
    }

    /**
     * 初始化文件对象
     */
    private void initFile()
    {
        final String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        //初始化根文件夹路径
        File appPath = new File(rootPath + APP_DIRECTORY);
        if (!appPath.exists()) {
            appPath.mkdirs();
        }

        //初始化下载图片文件夹路径
        File mImgFile = new File(appPath, IMAGE_FILE_NAME);
        if (!mImgFile.exists()) {
            mImgFile.mkdirs();
        }

        //初始化下载图片文件夹路径
        File mDownloadImgFile = new File(mImgFile, DOWNLOAD_IMG_FILE_NAME);;
        if (!mDownloadImgFile.exists()) {
            mDownloadImgFile.mkdirs();
        }


        //初始化缓存图片文件夹路径
        File mTempImgFile = new File(mImgFile, TEMP_IMG_FILE_NAME);;
        if (!mTempImgFile.exists()) {
            mTempImgFile.mkdirs();
        }
    }


    /**
     * 获取图片文件夹路径
     * @return
     */
    public File getDownloadImgFile() {
        return mDownloadImgFile;
    }

    /**
     * 获取图片文件夹路径
     * @return
     */
    public File getImgFile() {
        return mImgFile;
    }

    /**
     * 获取缓存图片文件夹路径
     * @return
     */
    public File getTempImgFile() {
        return mTempImgFile;
    }
}
