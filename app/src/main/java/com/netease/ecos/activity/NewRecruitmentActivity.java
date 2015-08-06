package com.netease.ecos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.NewDisplayListAdater;
import com.netease.ecos.model.Recruitment;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.recruitment.CreateRecruitmentRequest;
import com.netease.ecos.request.share.ShareListRequest;
import com.netease.ecos.views.ExtensibleListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/31.
 */
public class NewRecruitmentActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "Ecos---NewRecruitment";
    public static final String RecruitmentType = "RecruitmentType";
    private Recruitment.RecruitType mRecruitType;

    @InjectView(R.id.displayListView)
    ExtensibleListView displayLsVw;
    @InjectView(R.id.priceTxVw)
    TextView priceTxVw;
    @InjectView(R.id.btn_right_action)
    Button btn_right_action;
    @InjectView(R.id.priceEdTx)
    EditText priceEdTx;
    @InjectView(R.id.descrpEdTx)
    EditText descrpEdTx;
    private NewDisplayListAdater newDisplayListAdater;

    //for request
    private CreateRecruitmentRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recruitment_layout);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    void initView() {
        //set the data
        priceTxVw.setText(mRecruitType.getPriceUnit());
        btn_right_action.setText(NewRecruitmentActivity.this.getResources().getString(R.string.auction));
        //set adapter
        displayLsVw.setAdapter(newDisplayListAdater);
        //set listener
        btn_right_action.setOnClickListener(this);


    }

    void initData() {
        mRecruitType = Recruitment.RecruitType.getRecruitTypeByValue(getIntent().getExtras().getString(RecruitmentType));
        //init the adapter
        newDisplayListAdater = new NewDisplayListAdater(this);
        //for request
        request = new CreateRecruitmentRequest();
//        responce = new ICreateRecruitmentResponce();

        getPersonalShareList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right_action:
                String price = priceEdTx.getText().toString();
                String descrp = descrpEdTx.getText().toString();
                //TODO：get the chosen cover.
                if (price.equals("") || descrp.equals("")) {
                    Toast.makeText(NewRecruitmentActivity.this, this.getResources().getString(R.string.notAlreadyFinished), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!newDisplayListAdater.isThereCoverUrl()){
                    Toast.makeText(NewRecruitmentActivity.this, "请选择作品封面", Toast.LENGTH_SHORT).show();
                    return;
                }

                Recruitment recruitment = new Recruitment();
                recruitment.averagePrice = price;
                recruitment.priceUnit = mRecruitType.getPriceUnit();
                recruitment.description = descrp;
                recruitment.coverUrl = newDisplayListAdater.getCheckedCoverUrl();
                recruitment.recruitType = mRecruitType;

//                recruitment.recruitmentId = "" + 1;
//                recruitment.issueTimeStamp = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000;
                showProcessBar("挂牌中...");
                request.request(new CreateRecruitmentResponce(), recruitment);
                break;
        }
    }

    class CreateRecruitmentResponce extends BaseResponceImpl implements CreateRecruitmentRequest.ICreateRecruitmentResponce {

        @Override
        public void doAfterFailedResponse(String message) {
            dismissProcessBar();
            Toast.makeText(NewRecruitmentActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            dismissProcessBar();
            Toast.makeText(NewRecruitmentActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void success(Recruitment recruit) {
            dismissProcessBar();
            Toast.makeText(NewRecruitmentActivity.this, getResources().getString(R.string.uploadSuccessfully), Toast.LENGTH_SHORT).show();
            NewRecruitmentActivity.this.finish();

        }
    }


    /***
     * 获取个人分享列表
     */
    public void getPersonalShareList() {
        showProcessBar("加载中...");
        ShareListRequest request =  new ShareListRequest();
        Share.Tag tags = Share.Tag.getTagByRecruitType(mRecruitType);

        request.requestMyShareWithTag(new ShareListResponse(), tags,1);

    }

    /***
     *
     * @ClassName: GetAssignmetnDetailResponse
     * @Description:
     * @author enlizhang
     * @date 2015年7月28日 下午5:24:35
     *
     */
    class ShareListResponse extends BaseResponceImpl implements ShareListRequest.IShareListResponse{

        @Override
        public void doAfterFailedResponse(String message) {
            dismissProcessBar();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            dismissProcessBar();
        }

        @Override
        public void success(List<Share> shareList) {
            newDisplayListAdater.reflesh(shareList);
            dismissProcessBar();

            if(shareList.size()==0){
                Toast.makeText(NewRecruitmentActivity.this,"你目前没有分享",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

}
