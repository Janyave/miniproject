package com.netease.ecos.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.netease.ecos.R;
import com.netease.ecos.adapter.ContactListAdapter;
import com.netease.ecos.model.ActivityModel;
import com.netease.ecos.views.ExtensibleListView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/28.
 */
public class NewActivityActivity extends Activity implements View.OnClickListener, View.OnTouchListener {
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
    @InjectView(R.id.beginDateEdTx)
    EditText beginDateEdTx;
    @InjectView(R.id.endDateEdTx)
    EditText endDateEdTx;
    @InjectView(R.id.beginTimeEdTx)
    EditText beginTimeEdTx;
    @InjectView(R.id.endTimeEdTx)
    EditText endTimeEdTx;
    @InjectView(R.id.expenseEdTx)
    EditText expenseEdTx;
    @InjectView(R.id.newIcon)
    ImageView newIcon;
    @InjectView(R.id.activityDesrpEdTx)
    EditText activityDesrpEdTx;
    @InjectView(R.id.contactListView)
    ExtensibleListView contactListView;


    private ContactListAdapter contactListAdapter;

    //定义显示时间控件
    private Calendar calendar; //通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

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
        beginDateEdTx.setOnTouchListener(this);
        endDateEdTx.setOnTouchListener(this);
        beginTimeEdTx.setOnTouchListener(this);
        endTimeEdTx.setOnTouchListener(this);
        calendar = Calendar.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right_action:
                //TODO:send the activity information to the server.
                //get all items in the contact list view.

                NewActivityActivity.this.finish();
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.beginDateEdTx:
                    setDate(beginDateEdTx);
                    break;
                case R.id.endDateEdTx:
                    setDate(endDateEdTx);
                    break;
                case R.id.beginTimeEdTx:
                    setTime(beginTimeEdTx);
                    break;
                case R.id.endTimeEdTx:
                    setTime(endTimeEdTx);
                    break;
            }
        }
        return true;
    }

    void setDate(final EditText editText) {
        //点击日期按钮布局 设置日期
        new DatePickerDialog(NewActivityActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
                //更新EditText控件日期 小于10加0
                editText.setText(new StringBuilder().append(mYear).append("-")
                        .append((mMonth + 1) < 10 ? 0 + (mMonth + 1) : (mMonth + 1))
                        .append("-")
                        .append((mDay < 10) ? 0 + mDay : mDay));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    void setTime(final EditText editText) {
        //点击时间按钮布局 设置时间
        new TimePickerDialog(NewActivityActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                // TODO Auto-generated method stub
                mHour = hour;
                mMinute = minute;
                //更新EditText控件时间 小于10加0
                editText.setText(new StringBuilder()
                        .append(mHour < 10 ? 0 + mHour : mHour).append(":")
                        .append(mMinute < 10 ? 0 + mMinute : mMinute).append(":00"));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true).show();
    }


    ArrayList<ActivityModel.ContactWay> getDataFromListView() {
        /*clear all the data in the contactWaysList;
        * read the data from the listview and add them in the contactWayList.
        */
        View view;
        ArrayList<ActivityModel.ContactWay> contactWayArrayList = new ArrayList<>();
        for (int i = 0; i < contactListAdapter.getCount(); i++) {
            view = contactListView.getChildAt(i);
            Spinner spinner = (Spinner) view.findViewById(R.id.contactTypeSpinner);
            EditText editText = (EditText) view.findViewById(R.id.contactDetailEdTx);
            Log.d("test", "spinner " + i + ":" + spinner.getSelectedItemId());
            Log.d("test", "edittext:" + editText.getText());
            ActivityModel.ContactWay contactWay = ContactListAdapter.contactWays[(int) spinner.getSelectedItemId()];
            contactWay.setValue(editText.getText().toString());
            contactWayArrayList.add(contactWay);
        }
        return contactWayArrayList;
    }
}
