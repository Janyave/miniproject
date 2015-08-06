package com.netease.ecos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.netease.ecos.R;
import com.netease.ecos.dialog.SetPhotoDialog;
import com.netease.ecos.model.User;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.NorResponce;
import com.netease.ecos.request.user.UpdateUserInfoRequest;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.utils.SDImageCache;
import com.netease.ecos.utils.SetPhotoHelper;
import com.netease.ecos.utils.UploadImageTools;
import com.netease.ecos.views.RoundedNetworkImageView;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PersonalInfoSettingActivity extends BaseActivity {

    private static final String[] gender = {"男", "女", "保密"};

    //show
    private LinearLayout mReturn;
    private RoundImageView mAvatarImg;
    private ImageView mSetAvatar;
    private TextView mSetName;
    private TextView mSetGender;
    private TextView mSetIntro;
    private Switch mSetMsgAlert;
    private Button mLogOut;
    private LinearLayout ll_tagsList;

    //click
    @InjectView(R.id.ll_name)
    LinearLayout ll_name;
    @InjectView(R.id.ll_gender)
    LinearLayout ll_gender;
    @InjectView(R.id.ll_signature)
    LinearLayout ll_signature;
    @InjectView(R.id.ll_password)
    LinearLayout ll_password;
    @InjectView(R.id.ll_tags)
    LinearLayout ll_tags;

//    @InjectView(R.id.niv_personal_user_avatar)
//    LinearLayout niv_personal_user_avatar;

    @InjectView(R.id.niv_personal_user_avatar)
    RoundedNetworkImageView niv_personal_user_avatar;

    @InjectView(R.id.tv_tag)
    TextView tv_tag;

//    private RoundAngleImageView iv;

    private SetPhotoHelper mSetPhotoHelper;

    public boolean isSettingAvatart = false;

    public String mAvatarLocalPath = "";

    public String mAvatarUrl = "";

    private User user;
    private UpdateUserInfoRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_setting);
        ButterKnife.inject(this);
        onBoundView();
        onBoundLinster();

        mSetPhotoHelper = new SetPhotoHelper(this, null);
        //图片裁剪后输出宽度
        final int outPutWidth = 200;
        //图片裁剪后输出高度
        final int outPutHeight = 200;
        mSetPhotoHelper.setAspect(1, 1);
        mSetPhotoHelper.setOutput(outPutWidth, outPutHeight);

        user = UserDataService.getSingleUserDataService(this).getUser();
        request = new UpdateUserInfoRequest();
//        iv = (RoundAngleImageView) findViewById(R.id.picasso_test);
//        iv.setImageFromUrl("http://pic4.nipic.com/20090803/2618170_095921092_2.jpg");

        if(user.avatarUrl!=null)
        {
            mAvatarUrl = user.avatarUrl;
            ImageLoader imageLoader = new ImageLoader(MyApplication.getRequestQueue(), new SDImageCache());
            niv_personal_user_avatar.setImageUrl(mAvatarUrl,imageLoader);
        }

    }


    private void onBoundView() {
        mReturn = (LinearLayout) findViewById(R.id.lly_left_action);
        mSetName = (TextView) findViewById(R.id.personal_info_set_name);
        mSetGender = (TextView) findViewById(R.id.personal_info_set_gender);
        mSetIntro = (TextView) findViewById(R.id.personal_info_set_intro);
        mSetMsgAlert = (Switch) findViewById(R.id.personal_info_set_Msg_alert);
        mLogOut = (Button) findViewById(R.id.personal_info_logout);
        ll_tagsList = (LinearLayout) findViewById(R.id.ll_tagsList);  //add tags in this layout



    }

    private void onBoundLinster() {
        //bound button linster
        ButtonListener listener = new ButtonListener();
        mReturn.setOnClickListener(listener);
        mLogOut.setOnClickListener(listener);

        ll_name.setOnClickListener(listener);
        ll_gender.setOnClickListener(listener);
        ll_signature.setOnClickListener(listener);
        ll_password.setOnClickListener(listener);
        ll_tags.setOnClickListener(listener);
        niv_personal_user_avatar.setOnClickListener(listener);

        //bound spinner linster
        bonudSpinner();
        //bound swith linster
        mSetMsgAlert.setOnCheckedChangeListener(new SwitchCheckedListener());

    }

    private void bonudSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gender);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SetPhotoHelper.REQUEST_BEFORE_CROP:
                    //当前是设置封面
                    if (isSettingAvatart) {
                        mSetPhotoHelper.setmSetPhotoCallBack(new SetPhotoHelper.SetPhotoCallBack() {

                            @Override
                            public void success(String imagePath) {
                                Log.i("裁剪后图片路径", "-----------path:" + imagePath);
                                mAvatarLocalPath = imagePath;
                                isSettingAvatart = false;

                                setAndUpdateAvatar();

                            }
                        });
                        mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_BEFORE_CROP, data);
                        return;
                    }


                    break;
                case SetPhotoHelper.REQUEST_AFTER_CROP:
                    mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_AFTER_CROP, data);
                    break;
                default:
                    isSettingAvatart = false;
                    Log.e("CLASS_TAG", "onActivityResult() 无对应");
            }


        } else {
            Log.e(CLASS_TAG, "操作取消");
        }
    }

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lly_left_action:
                    finish();
                    break;
                case R.id.niv_personal_user_avatar:
                    //TODO 调出相册和相机
                    SetPhotoDialog dialog = new SetPhotoDialog(PersonalInfoSettingActivity.this,
                            new SetPhotoDialog.ISetPhoto() {

                                @Override
                                public void choosePhotoFromLocal() {
                                    Toast.makeText(PersonalInfoSettingActivity.this, "选择本地图片", Toast.LENGTH_LONG).show();
                                    mSetPhotoHelper.choosePhotoFromLocal();
                                    isSettingAvatart = true;
                                }

                                @Override
                                public void takePhoto() {
                                    Toast.makeText(PersonalInfoSettingActivity.this, "拍照", Toast.LENGTH_LONG).show();
                                    mSetPhotoHelper.takePhoto(false);
                                    isSettingAvatart = true;

                                }
                            });
                    dialog.showSetPhotoDialog();
                    break;
                case R.id.ll_name:
                    Intent intent2 = new Intent(PersonalInfoSettingActivity.this, PersonSetInformationNormalActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt(PersonSetInformationNormalActivity.ACTICITY_TYPE, PersonSetInformationNormalActivity.TYPE_NAME);
                    intent2.putExtras(bundle2);
                    startActivity(intent2);
                    break;
                case R.id.ll_gender:
                    startActivity(new Intent(PersonalInfoSettingActivity.this, PersonSetGenderActivity.class));
                    break;
                case R.id.ll_tags:
                    startActivity(new Intent(PersonalInfoSettingActivity.this, PersonSetTagsActivity.class));
                    break;
                case R.id.ll_signature:
                    Intent intent3 = new Intent(PersonalInfoSettingActivity.this, PersonSetInformationNormalActivity.class);
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt(PersonSetInformationNormalActivity.ACTICITY_TYPE, PersonSetInformationNormalActivity.TYPE_SIGNATURE);
                    intent3.putExtras(bundle3);
                    startActivity(intent3);
                    break;
                case R.id.ll_password:
                    Intent intent1 = new Intent(PersonalInfoSettingActivity.this, PersonSetInformationNormalActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(PersonSetInformationNormalActivity.ACTICITY_TYPE, PersonSetInformationNormalActivity.TYPE_PASSWORD);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                    break;
                case R.id.personal_info_logout:
                    Intent intent = new Intent(PersonalInfoSettingActivity.this, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            //TODO 选中后要做的事情
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class SwitchCheckedListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                //TODO 接收消息
            } else {
                //TODO 关闭接收
            }
        }
    }

    private void sendUser(User user) {
        request.request(new NorResponce() {
            @Override
            public void success() {
                setUserData();
            }

            @Override
            public void doAfterFailedResponse(String message) {
                Toast.makeText(PersonalInfoSettingActivity.this, "网络异常，个人信息更新失败", Toast.LENGTH_LONG);
            }

            @Override
            public void responseNoGrant() {

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, user);
    }

    private void setUserData() {
        User SetUser = UserDataService.getSingleUserDataService(PersonalInfoSettingActivity.this).getUser();
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(SetUser.avatarUrl);
//        if (bitmap != null)
//            personal_info_set_avatar_pic.setImageBitmap(bitmap);

        mSetName.setText(SetUser.nickname);
        mSetGender.setText(SetUser.gender.getValue());

        for (User.RoleType roleType : SetUser.roleTypeSet)
            System.out.println(roleType.getBelongs());

        mSetIntro.setText(SetUser.characterSignature);
    }


    /***
     * 设置并上传头像
     */
    public void setAndUpdateAvatar(){
        if(mAvatarLocalPath!=null && !"".equals(mAvatarLocalPath)){
            UploadImageTools.uploadImageSys(new File(mAvatarLocalPath), new UploadImageTools.UploadCallBack() {

                @Override
                public void success(String originUrl, String thumbUrl) {
                    Log.e(TAG, "图片url:" + originUrl);
                    ImageLoader imageLoader = new ImageLoader(MyApplication.getRequestQueue(), new SDImageCache());
                    niv_personal_user_avatar.setImageUrl(originUrl, imageLoader);


                    UpdateUserInfoRequest request = new UpdateUserInfoRequest();
                    User user = UserDataService.getSingleUserDataService(MyApplication.getContext()).getUser();
                    user.avatarUrl = originUrl;
                    request.request(new UpdateUserInfoResponse(),user);
                }

                @Override
                public void fail() {
                    dismissProcessBar();
                }

                @Override
                public void onProcess(Object fileParam, long current, long total) {

                }
            }, PersonalInfoSettingActivity.this, false);
        }
    }


    class UpdateUserInfoResponse extends BaseResponceImpl implements NorResponce{

        @Override
        public void success() {

        }

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }
}
