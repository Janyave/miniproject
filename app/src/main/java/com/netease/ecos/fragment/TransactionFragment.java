package com.netease.ecos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.RecruitmentCategoryActivity;
import com.netease.ecos.model.Recruitment;

/**
 * 交易页面
 */
public class TransactionFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //all the widget in this page.
    private View mainView;
    private ImageView makeupBtn, photographyBtn, backstageBtn, costumeBtn, propBtn, othersBtn;
    private TextView releaseRecruitmentTxVw;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionFragment newInstance(String param1, String param2) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_transaction, container, false);
        makeupBtn = (ImageView) mainView.findViewById(R.id.makeuper_btn);
        photographyBtn = (ImageView) mainView.findViewById(R.id.photography_btn);
        backstageBtn = (ImageView) mainView.findViewById(R.id.backstage_btn);
        costumeBtn = (ImageView) mainView.findViewById(R.id.costume_btn);
        propBtn = (ImageView) mainView.findViewById(R.id.prop_btn);
        othersBtn = (ImageView) mainView.findViewById(R.id.others_btn);
        releaseRecruitmentTxVw = (TextView) mainView.findViewById(R.id.releaseRecruitmentTxVw);
        makeupBtn.setOnClickListener(this);
        photographyBtn.setOnClickListener(this);
        backstageBtn.setOnClickListener(this);
        costumeBtn.setOnClickListener(this);
        propBtn.setOnClickListener(this);
        othersBtn.setOnClickListener(this);
        return mainView;
    }

    @Override
    public void onClick(View v) {
        Recruitment.RecruitType recruitType = Recruitment.RecruitType.妆娘;
        Intent intent;
        switch (v.getId()) {
            case R.id.photography_btn:
                recruitType = Recruitment.RecruitType.摄影;
                break;
            case R.id.backstage_btn:
                recruitType = Recruitment.RecruitType.后期;
                break;
            case R.id.costume_btn:
                recruitType = Recruitment.RecruitType.服装;
                break;
            case R.id.prop_btn:
                recruitType = Recruitment.RecruitType.道具;
                break;
            case R.id.others_btn:
                recruitType = Recruitment.RecruitType.其他;
                break;
        }
        intent = new Intent(getActivity(), RecruitmentCategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(RecruitmentCategoryActivity.TRecruitmentType, recruitType.name());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
