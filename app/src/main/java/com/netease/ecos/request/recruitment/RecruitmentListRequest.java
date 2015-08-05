package com.netease.ecos.request.recruitment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.model.Recruitment;
import com.netease.ecos.model.Recruitment.RecruitType;
import com.netease.ecos.model.User.Gender;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author enlizhang
 * @ClassName: RecruitmentListRequest
 * @Description: 招募列表
 * @date 2015年7月30日 下午8:48:26
 */
public class RecruitmentListRequest extends BaseRequest {

    //请求参数键
    /**
     * 招募类型
     */
    public static final String KEY_RECRUITMENT_TYPE = "recruitType";

    /**
     * 城市码
     */
    public static final String KEY_CITY_CODE = "cityCode";

    /**
     * 排列规则
     */
    public static final String KEY_SORT_RULE = "sortRule";


    //响应参数列表
    /**
     * 招募列表JSONArray,内含JSONObject
     */
    public static final String JA_RECRUITMENTS = "recruitments";

    /**
     * 招募id
     */
    public static final String KEY_RECRUITMENT_ID = "recruitId";

    /**
     * 用户id
     */
    public static final String KEY_USER_ID = "userId";

    /**
     * 云信id
     */
    public static final String KEY_IM_ID = "imId";

    /**
     * 标题
     */
    public static final String KEY_TITLE = "title";

    /**
     * 头像url
     */
    public static final String KEY_AVATAR_URL = "avatarUrl";

    /**
     * 昵称
     */
    public static final String KEY_NICKNAME = "nickname";

    /**
     * 性别
     */
    public static final String KEY_GENDER = "gender";

    /**
     * 封面图url
     */
    public static final String KEY_COVER_URL = "coverUrl";

    /**
     * 发布时间时间戳
     */
    public static final String KEY_ISSUE_TIME_STAMP = "issueTimeStamp";

    IRecruitmentListResponse mRecruitmentListResponse;


    public void request(IRecruitmentListResponse recruitmentListResponse, final RecruitType recruitType, final String cityCode,
                        final SortRule sortRule, final int pageIndex) {
        super.initBaseRequest(recruitmentListResponse);
        mRecruitmentListResponse = recruitmentListResponse;

        //		recruitmentListResponse.success(getTestRecruitmentList());

        MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_RECRUITMENT_LIST_URL, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = getRequestBasicMap();

                map.put("isMyself", "false");
                map.put(KEY_RECRUITMENT_TYPE, recruitType.getValue());
                map.put(KEY_CITY_CODE, cityCode);
                map.put(KEY_SORT_RULE, sortRule.getValue());

                map.put(KEY_PAGE_SIZE, String.valueOf(DEFAULT_PAGE_SIZE));
                map.put(KEY_PAGE_INDEX, String.valueOf(pageIndex));

                traceNormal(TAG, map.toString());
                traceNormal(TAG, RecruitmentListRequest.this.getUrl(RequestUrlConstants.GET_RECRUITMENT_LIST_URL, map));
                return map;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getQueue().add(stringRequest);

    }

    public static final String VALUE_MY_RECRUITS = "6";

    public void requestMyself(IRecruitmentListResponse recruitmentListResponse, final int pageIndex) {
        super.initBaseRequest(recruitmentListResponse);
        mRecruitmentListResponse = recruitmentListResponse;

        recruitmentListResponse.success(getTestRecruitmentList());

        MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_RECRUITMENT_LIST_URL, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = getRequestBasicMap();

                map.put("isMyself", "true");
                map.put(KEY_RECRUITMENT_TYPE, "");

                map.put(KEY_PAGE_SIZE, String.valueOf(DEFAULT_PAGE_SIZE));
                map.put(KEY_PAGE_INDEX, String.valueOf(pageIndex));

                traceNormal(TAG, map.toString());
                traceNormal(TAG, RecruitmentListRequest.this.getUrl(RequestUrlConstants.GET_RECRUITMENT_LIST_URL, map));
                return map;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getQueue().add(stringRequest);

    }

    /**
     * 请求其他人招募列表
     *
     * @param recruitmentListResponse
     * @param otherUserId       要查看的人的userId
     * @param pageIndex
     */
    public void requestSomeone(IRecruitmentListResponse recruitmentListResponse, final String otherUserId, final int pageIndex) {
        super.initBaseRequest(recruitmentListResponse);
        mRecruitmentListResponse = recruitmentListResponse;

        recruitmentListResponse.success(getTestRecruitmentList());

        MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_RECRUITMENT_LIST_URL, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = getRequestBasicMap();

                map.put("isMyself", "true");

                map.put(KEY_RECRUITMENT_TYPE, "");

                map.put(KEY_PAGE_SIZE, String.valueOf(DEFAULT_PAGE_SIZE));
                map.put(KEY_PAGE_INDEX, String.valueOf(pageIndex));

                traceNormal(TAG, map.toString());
                traceNormal(TAG, RecruitmentListRequest.this.getUrl(RequestUrlConstants.GET_RECRUITMENT_LIST_URL, map));
                return map;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getQueue().add(stringRequest);

    }

    @Override
    public void responceSuccess(String jstring) {
        traceNormal(TAG, jstring);

        try {
            JSONObject json = new JSONObject(jstring).getJSONObject(KEY_DATA);


            if (json.has(JA_RECRUITMENTS) && !json.isNull(JA_RECRUITMENTS)) {

                JSONArray recruitJA = json.getJSONArray(JA_RECRUITMENTS);
                List<Recruitment> recruitmentList = new ArrayList<Recruitment>();

                for (int i = 0; i < recruitJA.length(); i++) {
                    JSONObject recruitJO = recruitJA.getJSONObject(i);

                    Recruitment recruit = null;
                    recruit = new Recruitment();

                    recruit.recruitmentId = getString(recruitJO, "recruitId");
                    recruit.recruitType = RecruitType.getRecruitTypeByValue(getString(recruitJO, "recruitType"));
                    recruit.averagePrice = getString(recruitJO, "recruitmentId");
                    recruit.description = getString(recruitJO, "description");
                    recruit.coverUrl = getString(recruitJO, "coverUrl");
                    recruit.title = getString(recruitJO, "title");
                    recruit.priceUnit = getString(recruitJO, "priceUnit");
                    recruit.recruitType = RecruitType.getRecruitTypeByValue(getString(recruitJO, "recruitType"));
                    recruit.issueTimeStamp = Long.valueOf(getString(recruitJO, "recruitType")).longValue();

                    recruit.userId = getString(recruitJO, "userId");
                    recruit.imId = getString(recruitJO, "imId");
                    recruit.avatarUrl = getString(recruitJO, "avatarUrl");
                    recruit.nickname = getString(recruitJO, "nickname");
                    recruit.gender = Gender.getGender(getString(recruitJO, "gender"));

                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (mBaseResponse != null) {
                mBaseResponse.doAfterFailedResponse("json异常");
            }
        }

    }


    /**
     * @author enlizhang
     * @ClassName: SortRule
     * @Description: 排序规则，包括智能排序、价格最低、距离最近、最受欢迎
     * @date 2015年7月30日 下午8:49:12
     */
    public static enum SortRule {
        智能排序("intelligent"),
        价格最低("price"),
        距离最近("distance"),
        最受欢迎("popular");

        public String value;

        SortRule(String _value) {
            value = _value;
        }

        public String getValue() {
            return value;
        }

        /**
         * 根据值获取对应枚举
         *
         * @param value
         * @return
         */
        public static SortRule getSortRuleByValue(String value) {

            for (SortRule sortRule : SortRule.values()) {
                if (sortRule.getValue().equals(value))
                    return sortRule;
            }

            return 智能排序;
        }
    }

    /**
     * @author enlizhang
     * @ClassName: IRecruitmentListResponse
     * @Description: 获取招募列表响应回调接口
     * @date 2015年7月30日 下午9:46:41
     */
    public interface IRecruitmentListResponse extends IBaseResponse {

        /**
         * 请求成功回调函数，返回招募列表
         */
        public void success(List<Recruitment> recruitmentList);

    }

    /**
     * 获取测试分享数据
     *
     * @return
     */
    public List<Recruitment> getTestRecruitmentList() {

        List<Recruitment> recruitList = new ArrayList<Recruitment>();

        int length = 4;

        List<String> bannerList = new ArrayList<String>();
        bannerList.add("http://u4.tdimg.com/7/203/19/46138657748730920288026757971472766587.jpg");
        bannerList.add("http://www.cnnb.com.cn/pic/0/01/49/86/1498602_864010.jpg");
        bannerList.add("http://u3.tdimg.com/6/88/143/_56696781343356143444965292996172123406.jpg");
        bannerList.add("http://i3.cqnews.net/news/attachement/jpg/site82/2011-07-27/4386628352243053135.jpg");


        for (int i = 0; i < length; i++) {
            Recruitment recruit = new Recruitment();
            recruit.recruitmentId = "" + i;
            recruit.userId = "" + i;
            recruit.imId = "" + i;
            //			recruit.title = "招募测试"+i;
            recruit.avatarUrl = "http://img1.imgtn.bdimg.com/it/u=1413087,3985996900&fm=21&gp=0.jpg";
            recruit.nickname = "蓝天与白云的故事";
            recruit.gender = Gender.女;
            recruit.coverUrl = bannerList.get(i);
            recruit.issueTimeStamp = System.currentTimeMillis() - i * 24 * 60 * 60 * 1000;
            recruit.distanceKM = "" + (1 + i);
            recruit.averagePrice = "30元/人";

            recruitList.add(recruit);
        }

        return recruitList;
    }
}

