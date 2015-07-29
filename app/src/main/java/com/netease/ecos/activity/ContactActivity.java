package com.netease.ecos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.adapter.ContactAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/29.
 */
public class ContactActivity extends Activity implements View.OnClickListener{

    @InjectView(R.id.title_left)
    ImageView title_left;
    @InjectView(R.id.title_name)
    TextView title_name;
    @InjectView(R.id.title_right)
    TextView title_right;

    @InjectView(R.id.lv_list)
    ListView lv_list;
    @InjectView(R.id.tv_send)
    TextView tv_send;
    @InjectView(R.id.et_input)
    EditText et_input;


    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.inject(this);

        initListener();
        initData();
    }

    private void initListener() {
        title_left.setOnClickListener(this);
        title_name.setOnClickListener(this);
        title_right.setOnClickListener(this);
        tv_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_right:
                Toast.makeText(ContactActivity.this,"MORE",Toast.LENGTH_SHORT).show();
                break;
            case R.id.title_left:
                finish();
                break;
            case R.id.tv_send:
                Toast.makeText(ContactActivity.this,"SEND",Toast.LENGTH_SHORT).show();
                et_input.setText("");
                break;
        }
    }

    private void initData() {
        contactAdapter=new ContactAdapter(this);
        lv_list.setAdapter(contactAdapter);
        lv_list.setSelection(contactAdapter.getCount()+1);
    }


}
