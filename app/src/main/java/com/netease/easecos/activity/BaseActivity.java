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
 * Activity���࣬����activity����̳и÷���
 * Created by enlizhang on 2015/7/15.
 */
public class BaseActivity extends ActionBarActivity {

    private static String TAG = "BaseActivity";

    /***
     * ��ʾ�Ի���
     */
    private static MyAlertDialog singleMyAlertDialog;

    /***
     * ���ضԻ���
     */
    MyProgressDialog mMyProgressDialog;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        //���õ�ǰactivity
        MyApplication.setCurrentActivity(this);

        //��ʼ����ʾ�Ի���

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        //���õ�ǰactivity
        MyApplication.setCurrentActivity(this);

        //��ʼ����ʾ�Ի���

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
     * ��ʾ���ؿ��Լ���Ӧ������
     * @param title �Կ����ص����֣������
     *
     */
    public void showProcessBar(String title)
    {
        Log.i(TAG, "��ʾ���ؿ�");
        if(mMyProgressDialog == null)
        {
            //mMyProgressDialog��ʼ��
        }

       //�����ǰ���ؿ�δ��ʾ���������ʾ

    }

    /***
     * �Ƴ������ϵļ��ؿ�
     */
    public void dismissProcessBar()
    {
        Log.i(TAG, "���ټ��ؿ�");
        //��mMyProgressDialogΪnull�����Ѿ������ˣ���return

        //����mMyProgressDialog����dismiss
    }


    /***
     * ��ȡ���õ���ʾ�Ի���
     * (������ʾ�Ի�����app�бȽϳ����������������ض�activity������ҽ���д������Ҳ�����Լ�����)
     *
     * @return
     */
    public static MyAlertDialog getAlertDialog(){
        Log.i(TAG, "��ȡ�Ի���");

        return null;
    }

    /***
     * ������ͼ��Դ������ر�δ�رնԻ���
     */
    public void recycle()
    {

        //����ʾ�Ի���δ�رգ���ر���ʾ�Ի���

        //�����ؿ�δ�رգ���رռ��ؿ�
    }


    /***
     * ��������Ƿ����
     * @param context
     * @return true:�� false:��
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
