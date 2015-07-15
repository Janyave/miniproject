package com.netease.easecos.activity;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.netease.easecos.dialog.MyAlertDialog;
import com.netease.easecos.dialog.MyProgressDialog;

/**
 * Activity基类，所有activity必须继承该方法
 * Created by enlizhang on 2015/7/15.
 */
public class BaseActivity extends ActionBarActivity {

    private static String TAG = "BaseActivity";

    /***
     * 提示对话框
     */
    private static MyAlertDialog singleMyAlertDialog;

    /***
     * 加载对话框
     */
    MyProgressDialog mMyProgressDialog;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        //设置当前activity
        MyApplication.setCurrentActivity(this);

        //初始化提示对话框

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        //设置当前activity
        MyApplication.setCurrentActivity(this);

        //初始化提示对话框

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    /***
     * 显示加载框以及对应的文字
     * @param title 对框框相关的文字，如果有
     *
     */
    public void showProcessBar(String title)
    {
        Log.i(TAG, "显示加载框");
        if(mMyProgressDialog == null)
        {
            //mMyProgressDialog初始化
        }

       //如果当前加载框未显示，则进行显示

    }

    /***
     * 移除界面上的加载框
     */
    public void dismissProcessBar()
    {
        Log.i(TAG, "销毁加载框");
        //若mMyProgressDialog为null或者已经隐藏了，则return

        //否则将mMyProgressDialog进行dismiss
    }


    /***
     * 获取可用的提示对话框。
     * (由于提示对话框在app中比较常见，而不局限于特定activity，因此我将其写这里，大家也可以自己创建)
     *
     * @return
     */
    public static MyAlertDialog getAlertDialog(){
        Log.i(TAG, "获取对话框");

        return null;
    }

    /***
     * 回收视图资源，例如关闭未关闭对话框
     */
    public void recycle()
    {

        //若提示对话框未关闭，则关闭提示对话框

        //若加载框未关闭，则关闭加载框
    }


    /***
     * 检测网络是否可用
     * @param context
     * @return true:是 false:否
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
