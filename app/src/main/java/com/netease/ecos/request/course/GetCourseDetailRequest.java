package com.netease.ecos.request.course;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.model.Course;
import com.netease.ecos.model.Course.Assignment;
import com.netease.ecos.model.Course.CourseType;
import com.netease.ecos.model.Course.Step;
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
 * @ClassName: GetCourseDetailRequest
 * @Description: 获取教程详情
 * @date 2015年7月26日 上午11:03:17
 */
public class GetCourseDetailRequest extends BaseRequest {

    //请求参数键
    /**
     * 教程id
     */
    public static final String COURSE_ID = "courseId";

    //响应参数键
    ICourseDetailResponse mCourseDetailRespnce;

	/*{
        limg_urls:图片列表(JSON Arrayt)
		descriptions:内容列表(JSON Array)
		JSON ARRAY
		{
		     JSONObject
		    {
		       userid:作者id
		       nickname：作者名称
		       content：内容
		       imageUrl：图片url
		       authorAvatarUrl：作者头像url
		       issueTimeStamp：发布时间时间戳
		     }：作业
		}作业列表
		commentNum：评论总数
		}*/

    /**
     * 教程id
     */
    public static final String KEY_COURSE_ID = COURSE_ID;

    /**
     * 封面图url
     */
    public static final String KEY_COVER_URL = "coverUrl";

    /**
     * 作者头像url
     */
    public static final String KEY_AVATAR_URL = "authorAvatarUrl";

    /**
     * 作者昵称
     */
    public static final String KEY_NICKNAME = "author";

    /**
     * 作者id
     */
    public static final String KEY_USER_ID = "userId";

    /**
     * 教程类型
     */
    public static final String KEY_COURSE_TYPE = "type";

    /**
     * 标题
     */
    public static final String KEY_TITLE = "title";

    /**
     * 点赞数
     */
    public static final String KEY_PRAISE_NUM = "praiseNum";

    /**
     * 是否已点赞
     */
    public static final String KEY_HAS_PRAISED = "hasPraised";

    /**
     * 教程发布时间时间戳
     */
    public static final String KEY_ISSUE_TIME = "issueTimeStamp";

    /**
     * 步骤图片JA,其顺序与步骤序号一致
     */
    public static final String KEY_STEP_IMG_URLS = "imgUrls";

    /**
     * 步骤描述JA,其顺序与步骤序号一致
     */
    public static final String KEY_STEP_DESCRIPTIONS = "descriptions";


    /**
     * 作业列表JSONArray,内含JSONObject
     */
    public static final String JA_AS = "assigmentList";
    /*** 作业JSONObject */
    //	  public static final String JO_AS_JO = "assignments";

    /**
     * 作业id
     */
    public static final String KEY_AS_ID = "assigmentId";

    /**
     * 作业作者id
     */
    public static final String KEY_AS_USER_ID = "userid";

    /**
     * 作业作者头像url
     */
    public static final String KEY_AS_AVATAR_URL = "authorAvatarUrl";

    /**
     * 作业作者昵称
     */
    public static final String KEY_AS_NICKNAME = "nickname";

    /**
     * 作业内容
     */
    public static final String KEY_AS_CONTENT = "description";

    /**
     * 作业图片url
     */
    public static final String KEY_AS_IMAGE = "imageUrl";

    /**
     * 作业发布时间时间戳
     */
    public static final String KEY_AS_ISSUE_TIME = "issueTimeStamp";

    /**
     * 教程发布时间时间戳
     */
    public static final String KEY_COMMENT_MUN = "commentNum";


    public void request(ICourseDetailResponse courseDetailRespnce, final String courseId) {
        super.initBaseRequest(courseDetailRespnce);
        mCourseDetailRespnce = courseDetailRespnce;
		
		/*if(mCourseDetailRespnce!=null)
		{
			mCourseDetailRespnce.success(getTestCourse());
		}		*/
        MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_COURSE_DETAIL_URL, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = getRequestBasicMap();
                map.put(COURSE_ID, courseId);
                traceNormal(TAG, map.toString());
                traceNormal(TAG, GetCourseDetailRequest.this.getUrl(RequestUrlConstants.GET_COURSE_DETAIL_URL, map));
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getQueue().add(stringRequest);

    }

    @Override
    public void responceSuccess(String jstring) {
        //		traceNormal(TAG, jstring);

        try {
            JSONObject json = new JSONObject(jstring).getJSONObject(KEY_DATA);

            //获取course JSONObject
            JSONObject courseJO = json;

            Course course = new Course();

            course.courseId = getString(courseJO, KEY_COURSE_ID);
            course.userId = getString(courseJO, KEY_USER_ID);

            course.coverUrl = getString(courseJO, KEY_COVER_URL);
            course.authorAvatarUrl = getString(courseJO, KEY_AVATAR_URL);
            course.author = getString(courseJO, KEY_NICKNAME);
            course.title = getString(courseJO, KEY_TITLE);
            course.courseId = getString(courseJO, KEY_COURSE_ID);
            course.userId = getString(courseJO, KEY_USER_ID);
            course.courseType = CourseType.getCourseType(getString(courseJO, KEY_COURSE_TYPE));

            String praiseNum = getString(courseJO, KEY_PRAISE_NUM);
            course.praiseNum = "".equals(praiseNum) ? 0 : Integer.valueOf(praiseNum);

            course.hasPraised = courseJO.getBoolean("hasPraised");

            List<String> stepImageList = new ArrayList<String>();

            //获取步骤图片列表JSONArray
            JSONArray stepImageUrls = new JSONArray(getString(courseJO, KEY_STEP_IMG_URLS));
            //获取步骤描述JSONArray
            JSONArray stepDescriptions = new JSONArray(getString(courseJO, KEY_STEP_DESCRIPTIONS));

            int stepLength = stepImageUrls.length();
            Step step;
            for (int i = 0; i < stepLength; i++) {
                step = new Step(i + 1);
                step.imageUrl = stepImageUrls.getString(i);
                step.description = stepDescriptions.getString(i);

                course.addStep(step);
            }

            if (courseJO.has(JA_AS) && !courseJO.isNull(JA_AS)) {
                JSONArray asJA = courseJO.getJSONArray(JA_AS);
                int assignmentNum = asJA.length();

                Assignment assignment;
                for (int i = 0; i < assignmentNum; i++) {
                    JSONObject asJO = asJA.getJSONObject(i);

                    assignment = new Assignment();

                    assignment.assignmentId = asJO.getString(KEY_AS_ID);
                    assignment.author = asJO.getString(KEY_AS_NICKNAME);
                    assignment.content = asJO.getString(KEY_AS_CONTENT);
                    assignment.imageUrl = asJO.getString(KEY_AS_IMAGE);
                    assignment.authorAvatarUrl = asJO.getString(KEY_AS_AVATAR_URL);
                    assignment.issueTimeStamp = Long.valueOf(asJO.getString(KEY_AS_ISSUE_TIME)).longValue();

                    course.addStep(assignment);
                }
            }


            String commentNum = getString(courseJO, KEY_COMMENT_MUN);
            course.commentNum = "".equals(commentNum) ? 0 : Integer.valueOf(commentNum);

            if (mCourseDetailRespnce != null) {
                mCourseDetailRespnce.success(course);
            } else {
                traceError(TAG, "回调接口为null");
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
     * @ClassName: CourseDetailRespnce
     * @Description: 请求教程详情回调函数
     * @date 2015年7月26日 下午5:06:49
     */
    public interface ICourseDetailResponse extends IBaseResponse {

        /**
         * 请求成功，返回教程详情
         */
        public void success(Course course);

    }


    public Course getTestCourse() {


        Course course;

        List<String> bannerList = new ArrayList<String>();
        bannerList.add("http://u4.tdimg.com/7/203/19/46138657748730920288026757971472766587.jpg");
        bannerList.add("http://www.cnnb.com.cn/pic/0/01/49/86/1498602_864010.jpg");
        bannerList.add("http://u3.tdimg.com/6/88/143/_56696781343356143444965292996172123406.jpg");
        bannerList.add("http://i3.cqnews.net/news/attachement/jpg/site82/2011-07-27/4386628352243053135.jpg");

        course = new Course();
        course.coverUrl = bannerList.get(0);
        course.title = "鸣人系列教程-" + 1;
        course.author = "小鸣人 -" + 1;
        course.authorAvatarUrl = "http://p1.gexing.com/G1/M00/9E/A6/rBACE1J-AI7xPAUWAAAa1SSMm94668_200x200_3.jpg?recache=20131108";
        course.courseId = "" + 1;
        course.userId = "" + 1;
        course.praiseNum = 1 * 100;
        course.hasPraised = true;

        List<String> stepImageList = new ArrayList<String>();
        stepImageList.add("http://images.ali213.net/picfile/pic/2015/03/25/927_2015032554559561.jpg");
        stepImageList.add("http://p4.gexing.com/shaitu/20121006/1240/506fb646b32e5.jpg");
        stepImageList.add("http://img4.duitang.com/uploads/item/201406/07/20140607184856_jvXRz.thumb.700_0.jpeg");
        stepImageList.add("http://i8.topit.me/8/1f/65/1128323017f88651f8l.jpg");
        stepImageList.add("http://img2.duitang.com/uploads/item/201208/29/20120829120608_eymUK.thumb.600_0.jpeg");
        stepImageList.add("http://images.17173.com/2012/news/2012/01/25/y0125cos25s.jpg");

        Step step;
        for (int i = 0; i < 5; i++) {
            step = new Step(i + 1);
            step.imageUrl = stepImageList.get(i);
            step.description = "这是测试" + i;
            course.addStep(step);
        }

        int assignmentNum = 5;
        course.assignmentNum = assignmentNum;

        List<String> imageList = new ArrayList<String>();

        imageList.add("http://img4.duitang.com/uploads/item/201401/05/20140105203829_rwYHC.thumb.700_0.jpeg");
        imageList.add("http://i2.topit.me/2/a1/4a/1187638510f924aa12o.jpg");
        imageList.add("http://dmimg.5054399.com/allimg/optuji/hzcos3/74.jpg");
        imageList.add("http://images.17173.com/2012/news/2012/06/13/y0613cos30s.jpg");
        imageList.add("http://hiphotos.baidu.com/694310353/pic/item/5fee232226a550804423e868.jpg");
        imageList.add("http://images.17173.com/2012/news/2012/03/01/y0301cos05s.jpg");
        imageList.add("http://img4.duitang.com/uploads/item/201411/29/20141129225352_Yt5iL.png");

        Assignment assignment;
        List<Assignment> assignmentList = new ArrayList<Assignment>();
        for (int i = 0; i < assignmentNum; i++) {
            assignment = new Assignment();

            assignment.author = "张恩立" + i;
            assignment.content = "张恩立的描述" + i;
            assignment.imageUrl = imageList.get(i);
            assignment.authorAvatarUrl = "http://p1.gexing.com/G1/M00/9E/A6/rBACE1J-AI7xPAUWAAAa1SSMm94668_200x200_3.jpg?recache=20131108";
            assignment.issueTimeStamp = System.currentTimeMillis();

            assignmentList.add(assignment);
        }
        course.assignmentList = assignmentList;
        course.commentNum = 200;
        return course;
    }
}

