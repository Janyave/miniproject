package com.netease.ecos.activity;

import android.app.Activity;
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
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.recruitment.CreateRecruitmentRequest;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/31.
 */
public class NewRecruitmentActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "Ecos---NewRecruitment";
    public static final String RecruitmentType = "RecruitmentType";
    private Recruitment.RecruitType recruitType;

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
    private ICreateRecruitmentResponce responce;

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
        priceTxVw.setText(recruitType.getPriceUnit());
        btn_right_action.setText(NewRecruitmentActivity.this.getResources().getString(R.string.auction));
        //set adapter
        displayLsVw.setAdapter(newDisplayListAdater);
        //set listener
        btn_right_action.setOnClickListener(this);
    }

    void initData() {
        recruitType = Recruitment.RecruitType.getRecruitTypeByValue(getIntent().getExtras().getString(RecruitmentType));
        //init the adapter
        newDisplayListAdater = new NewDisplayListAdater(this);
        //for request
        request = new CreateRecruitmentRequest();
        responce = new ICreateRecruitmentResponce();

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
                Recruitment recruitment = new Recruitment();
                recruitment.averagePrice = price;
                recruitment.priceUnit = recruitType.getPriceUnit();
                recruitment.description = descrp;
                //TODO:share id
                recruitment.recruitmentId = "" + 1;
                recruitment.issueTimeStamp = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000;
                request.request(responce, recruitment);
                break;
        }
    }

    class ICreateRecruitmentResponce extends BaseResponceImpl implements CreateRecruitmentRequest.ICreateRecruitmentResponce {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
        }

        @Override
        public void success(Recruitment recruit) {
            Toast.makeText(NewRecruitmentActivity.this, getResources().getString(R.string.uploadSuccessfully), Toast.LENGTH_SHORT).show();
            NewRecruitmentActivity.this.finish();
        }
    }
}
