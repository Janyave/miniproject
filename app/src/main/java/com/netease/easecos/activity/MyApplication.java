package com.netease.easecos.activity;

import android.app.Application;
import android.content.Context;

import com.netease.easecos.utils.MyMediaScanner;

/**
 * ��������Ӧ����
 * Created by enlizhang on 2015/7/15.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    /***
     * ����ǰ��Activity����ÿ������activityʱ����onCreate()�и�ֵ��
     * �ڼ̳�BaseActivity������BaseActivity���Ѿ��������ã������ڵ���super.onCreate()ʱ�൱��������
     *
     * ���ڴ˱������õ���;:����Ӧ���кܶ�ط���Ҫ��ǰ��activity���ã��������ľ��ǶԻ�����˸þ�̬�������趨
     * ʹ���ڷ�activity���д����Ի�����Щ�ؼ�ǰ��������⴫��activity��������Adapter��
     */
    public static BaseActivity msCurrentActivity;


    /***
     * ��ǰApplication��������
     */
    private static Application  mApplication;



    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
    }


    /***
     * ���õ�ǰactivity
     * @param currentActivity ��ǰactivity
     */
    public static void setCurrentActivity(BaseActivity currentActivity){
        msCurrentActivity = currentActivity;
    }

    /***
     * ��ȡ��ǰActivity����
     * @return
     */
    public static BaseActivity getCurrentActivity(){
        return msCurrentActivity;
    }

    /****
     * ��ȡ��ǰContext������{@link #msCurrentActivity}�е����ƣ������õ�Context����ʱ��
     *     ����SharePreference�����ݿ�ȵط�����ֱ���ñ�������ȡContext���󣬼��ٴ��븴�Ӷ�
     * @return
     */
    public static Context getContext()
    {
        return mApplication.getApplicationContext();
    }


    public static Application getDemoApplication()
    {
        return mApplication;
    }


    /***
     * ��ý��ɨ��������ڱ���ͼƬ��ͼ���ͼ���޷��Զ�ˢ��
     */
    private static MyMediaScanner myMediaScanner;

    /***
     * ���õ�����ȡ���õ�{@link #myMediaScanner}
     * @return
     */
    public static synchronized MyMediaScanner getMediaScanner()
    {
        if(myMediaScanner == null )
        {
            myMediaScanner =new MyMediaScanner(getContext());
        }

        return myMediaScanner;
    }
}
