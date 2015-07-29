package com.netease.ecos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.ContactListAdapter;
import com.netease.ecos.views.ExtensibleListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/28.
 */
public class NewActivityActivity extends Activity implements View.OnClickListener {
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;
    @InjectView(R.id.activityCoverImgVw)
    ImageView coverImgVw;
    @InjectView(R.id.activityNameEdTx)
    EditText activityNameEdTx;
    @InjectView(R.id.activityTypeSpinner)
    Spinner activityTypeSpinner;
    @InjectView(R.id.activityProvinceSpinner)
    Spinner activityProvinceSpinner;
    @InjectView(R.id.activityCitySpinner)
    Spinner activityCitySpinner;
    @InjectView(R.id.addressEdTx)
    EditText addressEdTx;
    @InjectView(R.id.beginDateSpinner)
    Spinner beginDateSpinner;
    @InjectView(R.id.endDateSpinner)
    Spinner endDateSpinner;
    @InjectView(R.id.beginTimeSpinner)
    Spinner beginTimeSpinner;
    @InjectView(R.id.endTimeSpinner)
    Spinner endTimeSpinner;
    @InjectView(R.id.expenseEdTx)
    EditText expenseEdTx;
    @InjectView(R.id.newIcon)
    ImageView newIcon;
    @InjectView(R.id.activityDesrpEdTx)
    EditText activityDesrpEdTx;
    @InjectView(R.id.contactListView)
    ExtensibleListView contactListView;

    private ContactListAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_layout);
        ButterKnife.inject(this);
        titleTxVw.setText("发布活动");
        rightButton.setText("发布");
        backTxVw.setText("取消");
        backTxVw.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        coverImgVw.setOnClickListener(this);
        newIcon.setOnClickListener(this);
        contactListAdapter = new ContactListAdapter(this);
        contactListView.setAdapter(contactListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right_action:
                //TODO:send the activity information to the server.
                //get all items in the contact list view.

//                NewActivityActivity.this.finish();
                break;
            case R.id.tv_left:
                NewActivityActivity.this.finish();
                break;
            case R.id.activityCoverImgVw:
                //TODO:choose the local picture.
                break;
            case R.id.newIcon:
                contactListAdapter.addItem(getDataFromListView());
                break;

        }
    }

    ArrayList<com.netease.ecos.model.Activity.ContactWay> getDataFromListView() {
        /*clear all the data in the contactWaysList;
        * read the data from the listview and add them in the contactWayList.
        */
        View view;
        ArrayList<com.netease.ecos.model.Activity.ContactWay> contactWayArrayList = new ArrayList<>();
        for (int i = 0; i < contactListAdapter.getCount(); i++) {
            view = contactListView.getChildAt(i);
            Spinner spinner = (Spinner) view.findViewById(R.id.contactTypeSpinner);
            EditText editText = (EditText) view.findViewById(R.id.contactDetailEdTx);
            Log.d("test", "spinner " + i + ":" + spinner.getSelectedItemId());
            Log.d("test", "edittext:" + editText.getText());
            com.netease.ecos.model.Activity.ContactWay contactWay = ContactListAdapter.contactWays[(int) spinner.getSelectedItemId()];
            contactWay.setValue(editText.getText().toString());
            contactWayArrayList.add(contactWay);
        }
        return contactWayArrayList;
    }
}
