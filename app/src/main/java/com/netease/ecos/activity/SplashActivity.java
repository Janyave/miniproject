package com.netease.ecos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.database.CityDBService;
import com.netease.ecos.database.ProvinceDBService;
import com.netease.ecos.model.City;
import com.netease.ecos.model.ConfigurationService;
import com.netease.ecos.model.Province;
import com.netease.ecos.request.VolleyErrorParser;
import com.netease.ecos.request.initial.GetCityListRequest;
import com.netease.ecos.request.initial.GetProvinceListRequest;
import com.netease.ecos.request.initial.InitialRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 * Created by hzjixinyu on 2015/7/30.
 */
public class SplashActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.tv_login)
    TextView tv_login;
    @InjectView(R.id.tv_regist)
    TextView tv_regist;

    private boolean initialDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplash);
        ButterKnife.inject(this);

        initListener();
        initData();
    }

    private void initListener() {
        tv_login.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
    }

    private void initData() {
        requestInitialData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                break;
            case R.id.tv_regist:
                startActivityForResult(new Intent(SplashActivity.this, VerifyCodeActivity.class), 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Intent intent=new Intent(SplashActivity.this, RegistActivity.class);
            intent.putExtra("phone",data.getStringExtra("phone"));
            startActivity(intent);
        }
    }

    List<InitialRequest> mInitialRequestList;

    /***
     * 从服务器请求基本的数据
     */
    private void requestInitialData() {

        mInitialRequestList = new ArrayList<InitialRequest>();

        if(!ConfigurationService.getConfigurationService(this).getProvinceDataDownloaded())
        {
            GetProvinceListRequest getProvinceListRequest = new GetProvinceListRequest();
            mInitialRequestList.add(getProvinceListRequest);
            getProvinceListRequest.requestCityList(responseCallBack);

        }
        else
        {
            Log.i(TAG, "省市数据已经加载过了");
        }

        if(!ConfigurationService.getConfigurationService(this).getCityDataDownloaded())
        {
            GetCityListRequest getCitysRequest = new GetCityListRequest();
            mInitialRequestList.add(getCitysRequest);
            getCitysRequest.requestCityList(responseCallBack);

        }
        else
        {
            Log.i(TAG, "省市数据已经加载过了");
        }


        //无基础数据需要加载
        if(mInitialRequestList.size()==0)
        {
            initialDataLoaded = true;
//            startLoginActivity();
        }
    }


    /**
     * 请求成功后的操作。检查是否其他请求都已经完成，若已经完成则进行登陆
     */
    public void doAfterInitialResponse()
    {
        if(mInitialRequestList == null)
            Toast.makeText(this, "mInitialRequestList == null", Toast.LENGTH_LONG).show();

        //若有请求未结束，则不进行后续操作
        for(InitialRequest request:mInitialRequestList)
        {
            if(!request.isFinished())
                return;
        }

        //若有请求未成功，则显示对话框提示相应信息
        for(InitialRequest request:mInitialRequestList)
        {
            if(!request.isSuccess())
            {
                Toast.makeText(this,VolleyErrorParser.parseVolleyError(request.getError()),Toast.LENGTH_LONG).show();
                return ;
            }
        }

        initialDataLoaded = true;

        List<City> cityList = CityDBService.getCityDBServiceInstance(MyApplication.getContext())
                .getCityList();

        for(City city:cityList){
            Log.e("城市", city.toString());
        }

        List<Province> provinceList = ProvinceDBService.getProvinceDBServiceInstance(MyApplication.getContext())
                .getProvinceList();

        for(Province province:provinceList){
            Log.e("省", province.toString());
        }
    }

    /**
     * 获取配置信息响应请求结束回调接口
     * @author enlizhang
     * 2014.11.28
     */
    public interface InitialInfoResponse
    {
        public void doAfterResponse( );

    }

    /***
     * 请求结束后的回调接口
     */
    InitialInfoResponse responseCallBack = new InitialInfoResponse()
    {
        @Override
        public void doAfterResponse() {
            doAfterInitialResponse();
        }
    };
}
