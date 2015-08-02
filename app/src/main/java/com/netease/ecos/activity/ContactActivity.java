package com.netease.ecos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.adapter.ContactAdapter;
import com.netease.ecos.model.ModelUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/29.
 */
public class ContactActivity extends Activity implements View.OnClickListener {

    private final String TAG = "Ecos---Contact";
    public static final String TargetUserID = "TargetUserID";
    public static final String TargetUserName = "TargetUserName";
    public static final String TargetUserAvatar = "TargetUserAvatar";

    private String userId = "", userName = "", userAvatar = "";

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


    private List<IMMessage> messageList = new ArrayList<>();
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.inject(this);


        NIMClient.getService(MsgService.class).setChattingAccount(
                "test1",
                SessionTypeEnum.P2P
        );

        regeisterObserver();

        initListener();
        initData();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);

        NIMClient.getService(MsgService.class).setChattingAccount(
                MsgService.MSG_CHATTING_ACCOUNT_NONE,
                SessionTypeEnum.None
        );
    }

    private void initListener() {
        title_left.setOnClickListener(this);
        title_name.setOnClickListener(this);
        title_right.setOnClickListener(this);
        tv_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_right:
                Toast.makeText(ContactActivity.this, "MORE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.title_left:
                finish();
                break;
            case R.id.tv_send:

                IMMessage message = MessageBuilder.createTextMessage(
                        "test2", // 聊天对象的ID，如果是单聊，为用户账号，如果是群聊，为群组ID
                        SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                        et_input.getText().toString() // 文本内容
                );
                NIMClient.getService(MsgService.class).sendMessage(message, false);

                et_input.setText("");
                break;
        }
    }

    private void initData() {
        testMessageHistory("test2");
    }

    private void initList() {
        contactAdapter = new ContactAdapter(this, messageList);
        lv_list.setAdapter(contactAdapter);
        lv_list.setSelection(contactAdapter.getCount() + 1);
    }

    private void addList(IMMessage message) {
        contactAdapter.add(message);
        lv_list.setSelection(contactAdapter.getCount() + 1);
    }


    private void regeisterObserver() {

        //注册接收信息监听器
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);

        //发送消息状态监听器
        NIMClient.getService(MsgServiceObserve.class).observeMsgStatus(new Observer<IMMessage>() {

            public void onEvent(IMMessage message) {
                // 参数为有状态发生改变的消息对象，其msgStatus和attachStatus均为最新状态。
                // 发送消息和接收消息的状态监听均可以通过此接口完成。
                Log.i("发送消息状态回掉", "消息内容：" + message.getContent());
                Log.i("发送消息状态回掉", "消息来自：" + message.getFromAccount());
                Log.i("发送消息状态回掉", "消息接收：" + message.getSessionId());
                Log.i("发送消息状态回掉", "会话类型：" + message.getSessionType());
                Log.i("发送消息状态回掉", "消息类型：" + message.getMsgType().name());
                Log.i("发送消息状态回掉", "消息状态：" + message.getStatus());

                Log.i("发送消息状态回掉", "消息方向：" + message.getDirect());
                Log.i("发送消息状态回掉", "当前是发出去:" + message.getDirect().compareTo(MsgDirectionEnum.Out));
                Log.i("发送消息状态回掉", "当前是收到:" + message.getDirect().compareTo(MsgDirectionEnum.In));
                Log.i("发送消息状态回掉", "消息类型：" + message.getMsgType().name());

                /**Add**/
                Toast.makeText(ContactActivity.this, message.getStatus().toString(), Toast.LENGTH_SHORT).show();
                addList(message);

            }
        }
                , true);


    }

    //信息收听接收器
    Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {

                    for (IMMessage message : messages) {

                        /**Add**/
                        addList(message);

                        Log.i("收到消息", "----------------------------------------------------------");
                        if (message.getMsgType().compareTo(MsgTypeEnum.text) == 0) {
//                            ((TextView)findViewById(R.id.tv_received_text)).setText(message.getContent());

                            Log.i("发送消息状态回掉", "消息内容：" + message.getContent());
                            Log.i("发送消息状态回掉", "消息来自：" + message.getFromAccount());
                            Log.i("发送消息状态回掉", "消息接收：" + message.getSessionId());
                            Log.i("发送消息状态回掉", "会话类型：" + message.getSessionType());
                            Log.i("发送消息状态回掉", "消息类型：" + message.getMsgType().name());
                            Log.i("发送消息状态回掉", "消息状态：" + message.getStatus());
                            Log.i("发送消息状态回掉", "时间：" + ModelUtils.getDateDetailByTimeStamp(message.getTime()));

                            Log.i("发送消息状态回掉", "消息方向：" + message.getDirect());
                            Log.i("发送消息状态回掉", "当前是发出去:" + message.getDirect().compareTo(MsgDirectionEnum.Out));
                            Log.i("发送消息状态回掉", "当前是收到:" + message.getDirect().compareTo(MsgDirectionEnum.In));
                            Log.i("发送消息状态回掉", "消息类型：" + message.getMsgType().name());
                        }

                    }
                }
            };


    public void testMessageHistory(String toAccid) {

        IMMessage endMessage = MessageBuilder.createEmptyMessage(toAccid, SessionTypeEnum.P2P, 0);

        NIMClient.getService(MsgService.class).pullMessageHistory(endMessage, 80, true)
                .setCallback(new RequestCallback<List<IMMessage>>() {
                    @Override
                    public void onSuccess(List<IMMessage> msgList) {

                        /**Add**/
                        messageList = msgList;
                        Collections.reverse(messageList);
                        initList();

                        Log.e("拉取信息", "拉去信息的条数" + msgList.size());

                        Log.e("历史信息", "聊天------");
                        for (int i = 0; i < 10; i++) {

                            IMMessage message = msgList.get(i);

                            Log.e("历史记录", message.getFromAccount().equals("test1") ? "我：" : "  蓝天：");
                            Log.e("历史记录", message.getContent());
                            Log.e("历史记录", ModelUtils.getDateDetailByTimeStamp(message.getTime()));
                            Log.e("历史记录", ("\n"));

                        }

                    }

                    @Override
                    public void onFailed(int code) {

                        Log.e("拉取信息", "拉取失败");
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Log.e("拉取信息", "拉取异常");
                    }
                });
    }


}
