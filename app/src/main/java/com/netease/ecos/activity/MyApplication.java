package com.netease.ecos.activity;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.netease.ecos.R;
import com.netease.ecos.model.User;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.utils.MyMediaScanner;
import com.netease.ecos.utils.yunxin.ScreenUtil;
import com.netease.ecos.utils.yunxin.SystemUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;


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
    private static Application mApplication;



    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;

        Log.e("初始化", "初始化");
        NIMClient.init(this, getLoginInfo(), getOptions());


        //模拟存储用户数据.
        saveTestUserData();

        Log.e("tag","---------------" + UserDataService.getSingleUserDataService(this).getUser().toString());
    }

    private void saveTestUserData() {

        User user = new User();

        user.userId = "1";
        user.nickname = "蓝天";
        user.avatarUrl = "http://image.tianjimedia.com/uploadImages/upload/20140912/upload/201409/w4qlbtkmqrapng.png";
        user.characterSignature = "我老我骄傲。笑我你算老几";
        user.cityCode = "12";
        user.cityName = "杭州";
        user.coverUrl = "http://comic.people.com.cn/NMediaFile/2013/0314/MAIN201303140932000189269100561.jpg";
        user.gender = User.Gender.女;

        user.followOtherNum = "12";
        user.fansNum = "20";

        UserDataService.getSingleUserDataService(this).saveUser(user);
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


    public static RequestQueue volleyQueue;

    public static synchronized RequestQueue getRequestQueue()
    {
        if(volleyQueue == null)
        {
            volleyQueue = Volley.newRequestQueue(mApplication.getApplicationContext());
        }
        return volleyQueue;
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


    /***
     * ------------------------------------------------------
     * 云信SDK初始化开始------------------------------------------
     * ------------------------------------------------------
     */

    private LoginInfo getLoginInfo() {
        /*String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            // open db
            DatabaseManager.getInstance().open(this);
            return new LoginInfo(account, token);
        } else {
            return null;
        }*/

        return null;
    }


    private SDKOptions getOptions() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给SDK完成，需要添加以下配置。
        // 其中notificationSmallIconId必须提供
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class;
        config.notificationSmallIconId = R.mipmap.ic_launcher;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.ecos/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log等数据的目录
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置数据库加密秘钥
        options.databaseEncryptKey = "NETEASE";

        // 配置是否需要预下载附件缩略图
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小，
        options.thumbnailSize = ScreenUtil.getScreenMin() / 2;

        return options;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    /***
     * ------------------------------------------------------
     * 云信SDK初始化结束------------------------------------------
     * ------------------------------------------------------
     */
}
