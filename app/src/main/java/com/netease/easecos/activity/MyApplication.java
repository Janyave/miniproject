package com.netease.easecos.activity;

import android.app.Application;
import android.content.Context;

import com.netease.easecos.utils.MyMediaScanner;

/**
 * 类描述：应用类
 * Created by enlizhang on 2015/7/15.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    /***
     * 程序当前的Activity，在每次启动activity时，在onCreate()中赋值。
     * 在继承BaseActivity后，由于BaseActivity中已经进行设置，所以在调用super.onCreate()时相当于设置了
     *
     * 对于此变量设置的用途:由于应用中很多地方需要当前的activity引用，例如最多的就是对话框，因此该静态变量的设定
     * 使得在非activity类中创建对话框这些控件前，无需额外传入activity对象，例如Adapter。
     */
    public static BaseActivity msCurrentActivity;


    /***
     * 当前Application对象引用
     */
    private static Application  mApplication;



    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
    }


    /***
     * 设置当前activity
     * @param currentActivity 当前activity
     */
    public static void setCurrentActivity(BaseActivity currentActivity){
        msCurrentActivity = currentActivity;
    }

    /***
     * 获取当前Activity对象
     * @return
     */
    public static BaseActivity getCurrentActivity(){
        return msCurrentActivity;
    }

    /****
     * 获取当前Context对象，与{@link #msCurrentActivity}有点类似，后续用到Context对象时，
     *     例如SharePreference，数据库等地方可以直接用本方法获取Context对象，减少代码复杂度
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
     * 多媒体扫描对象，用于保存图片到图库后，图库无法自动刷新
     */
    private static MyMediaScanner myMediaScanner;

    /***
     * 采用单例获取可用的{@link #myMediaScanner}
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
