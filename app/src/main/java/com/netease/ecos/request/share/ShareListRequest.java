package com.netease.ecos.request.share;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.model.Comment;
import com.netease.ecos.model.Comment.CommentType;
import com.netease.ecos.model.Course;
import com.netease.ecos.model.Course.Assignment;
import com.netease.ecos.model.Recruitment;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

/***
 *
 * @ClassName: ShareListRequest
 * @Description: 获取分享列表
 * @author enlizhang
 * @date 2015年7月26日 下午12:43:32
 *
 */
public class ShareListRequest extends BaseRequest{

    //请求参数键
    /** 获取教程列表方式，包括推荐和筛选 */
    public static final String TYPE = "type";

    /** 关键字(仅在{@link #TYPE}为推荐时有效) */
    public static final String KEY_WORD = "keyWord";


    //响应参数列表
    /*** 分享列表JSONArray,内含JSONObject */
    public static final String JA_SHARE = "detailShareVo";

    /** 分享id */
    public static final String KEY_SHARE_ID = "shareId";

    /** 封面图url */
    public static final String KEY_COVER_URL = "coverUrl";

    /** 作者头像url */
    public static final String KEY_AVATAR_URL = "authorAvatarUrl";

    /** 作者昵称 */
    public static final String KEY_NICKNAME = "nickname";

    /** 作者id */
    public static final String KEY_USER_ID = "userId";

    /** 是否已评论，true:是 false:否 */
    public static final String KEY_HAS_FOLLOWED= "hasFollowed";

    /** 是否已评论，true:是 false:否 */
    public static final String KEY_HAS_Praised= "hasPraised";

    /** 作品图片总数 */
    public static final String KEY_TOTAL_IMAGES = "totalImages";


    /** 标题 */
    public static final String KEY_TITLE = "title";

    /*** 分享发布时间戳 */
    public static final String KEY_ISSUE_TIME = "issueTimeStamp";

    /*** 点赞数 */
    public static final String KEY_PRAISE_NUM = "praiseNum";

    /*** 评论数 */
    public static final String KEY_COMMENT_NUM = "commentNum";

    /*** 评论列表JSONArray,内含JSONObject */
    public static final String JA_COMMENTS = "comments";

    /** 评论id */
    public static final String KEY_COMMENT_ID = "commentId";

    /** 评论者头像url */
    public static final String KEY_COMMENT_AVATAR_URL = "authorAvatarUrl";

    /** 评论内容  */
    public static final String KEY_COMMENT_CONTENT = "content";

    /** 评论类型{@link CommentType}、包括{@link Course}、作业{@link Assignment}、分享{@link Share}、招募{@link Recruitment} */
    public static final String KEY_COMMENT_TYPE = "commentType";

    /** 某种评论类型{@link CommentType}的id，不能为空 */
    public static final String KEY_COMMENT_TYPE_ID = "commentTypeId";

    /** 评论者id */
    public static final String KEY_COMMENT_FROM_ID = "fromId";

    /** 评论者名称 */
    public static final String KEY_COMMENT_USER_NICKNAME = "fromNickName";

    /** 父评论id */
    public static final String KEY_COMMENT_PARENT_ID= "parentId";

    /** 父评论用户昵称 */
    public static final String KEY_COMMENT_PARENT_NICKNAME= "parentNickname";

    /** 评论时间时间戳 */
    public static final String KEY_COMMENT_TIME_STAMP = "commentTimeStamp";



    IShareListResponse mShareListResponse;

    /**
     *
     * @param shareListResponse
     * @param type {@link ShareType},如果是请求所有类型，则传null
     * @param keyWord
     * @param pageIndex
     */
    public void request(IShareListResponse shareListResponse, final ShareType shareType, final String keyWord,
                        final int pageIndex)
    {
        super.initBaseRequest(shareListResponse);
        mShareListResponse = shareListResponse;

        //		shareListResponse.success(getTestShareList());

        MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_SHARE_LIST_URL,  this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = getRequestBasicMap();

                map.put(TYPE, shareType!=null?shareType.getValue():"all");

                map.put(KEY_WORD, keyWord);
                map.put(KEY_PAGE_SIZE, String.valueOf(5 ) );
                //	        	map.put(KEY_PAGE_SIZE, String.valueOf( DEFAULT_PAGE_SIZE ) );
                map.put(KEY_PAGE_INDEX, String.valueOf( pageIndex ) );

                traceNormal(TAG, map.toString());
                traceNormal(TAG, ShareListRequest.this.getUrl(RequestUrlConstants.GET_SHARE_LIST_URL, map));
                return map;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getQueue().add(stringRequest);

    }

    public void requestMyShareList(IShareListResponse shareListResponse, final int pageIndex)
    {
        super.initBaseRequest(shareListResponse);
        mShareListResponse = shareListResponse;

        //		shareListResponse.success(getTestShareList());

        MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_SHARE_LIST_URL,  this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = getRequestBasicMap();

                map.put(TYPE, "myself");

                map.put(KEY_PAGE_SIZE, String.valueOf( DEFAULT_PAGE_SIZE ) );
                map.put(KEY_PAGE_INDEX, String.valueOf( pageIndex ) );

                traceNormal(TAG, map.toString());
                traceNormal(TAG, ShareListRequest.this.getUrl(RequestUrlConstants.GET_SHARE_LIST_URL, map));
                return map;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getQueue().add(stringRequest);

    }

    @Override
    public void responceSuccess(String jstring) {
        //		traceNormal(TAG, jstring);

        try {
            JSONObject json = new JSONObject(jstring).getJSONObject(KEY_DATA);
            JSONArray shareJA = json.getJSONArray(JA_SHARE);
            int length = shareJA.length();

            List<Share> shareList = new ArrayList<Share>();
            for(int i=0;i<length;i++){
                JSONObject shareJO = shareJA.getJSONObject(i);
                Share share = new Share();
                share.shareId = getString(shareJO, KEY_SHARE_ID);
                share.userId = getString(shareJO, KEY_USER_ID);
                share.avatarUrl = getString(shareJO, KEY_AVATAR_URL);
                share.nickname = getString(shareJO, KEY_NICKNAME);
                share.hasAttention = Boolean.valueOf(getString(shareJO, KEY_HAS_FOLLOWED));
                share.hasPraised = Boolean.valueOf(getString(shareJO,KEY_HAS_Praised));
                share.coverUrl = getString(shareJO, KEY_COVER_URL);
                share.title = getString(shareJO, KEY_TITLE);
                share.issueTimeStamp = Long.valueOf(shareJO.getString(KEY_ISSUE_TIME)).longValue();

                String totalPics = getString(shareJO, KEY_TOTAL_IMAGES);
                share.totalPageNumber = "".equals(totalPics)?0:Integer.valueOf(totalPics);

                String praiseNum = getString(shareJO, KEY_PRAISE_NUM);
                share.praiseNum = "".equals(praiseNum)?0:Integer.valueOf(praiseNum);

                String commentNum = getString(shareJO, KEY_COMMENT_NUM);
                share.commentNum = "".equals(commentNum)?0:Integer.valueOf(commentNum);

                //设置评论数据
                List<Comment> commentList = new ArrayList<Comment>();
                if(shareJO.has(JA_COMMENTS)){
                    JSONArray commentJA = shareJO.getJSONArray(JA_COMMENTS);

                    int commentsLength = commentJA.length();
                    Comment comment;
                    for(int commentIndex=0;i<commentsLength;i++){

                        JSONObject commentJO = commentJA.getJSONObject(commentIndex);

                        comment = new Comment();
                        comment.commentId = getString(commentJO, KEY_COMMENT_ID);
                        comment.avatarUrl = getString(commentJO, KEY_COMMENT_AVATAR_URL);
                        comment.content = getString(commentJO, KEY_COMMENT_CONTENT);
                        comment.commentType = CommentType.getCommentTypeByValue(getString(commentJO, KEY_COMMENT_TYPE));
                        comment.commentTypeId = getString(commentJO, KEY_COMMENT_TYPE_ID);
                        comment.fromId = getString(commentJO, KEY_COMMENT_FROM_ID);
                        comment.fromNickName = getString(commentJO, KEY_COMMENT_USER_NICKNAME);
                        comment.targetId = getString(commentJO, KEY_COMMENT_PARENT_ID);
                        comment.targetNickname = getString(commentJO, KEY_COMMENT_PARENT_NICKNAME);
                        comment.commitTimeStamp = Long.valueOf(commentJO.getString(KEY_COMMENT_TIME_STAMP)).longValue();
                        commentList.add(comment);
                    }

                }
                share.commentList = commentList;
                shareList.add(share);
            }


            if(mShareListResponse!=null)
            {
                mShareListResponse.success(shareList);
            }
            else
            {
                traceError(TAG,"回调接口为null");
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if(mBaseResponse!=null)
            {
                mBaseResponse.doAfterFailedResponse("json异常");
            }
        }

    }

    /***
     *
     * @ClassName: Type
     * @Description: 分享类型
     * @author enlizhang
     * @date 2015年7月26日 下午7:48:42
     *
     */
    public static enum ShareType
    {
        所有("all"),
        推荐("recommended"),
        新人("transparent"),
        关注("follow");

        public String type;

        ShareType(String _type){
            type = _type;
        }

        public String getValue(){
            return type;
        }

        public static ShareType getShareTypeByValue(String value){

            for(ShareType shareType:ShareType.values()){
                if(shareType.getValue().equals(value))
                    return shareType;
            }

            return null;
        }
    }

    /**
     *
     * @ClassName: ShareListRespnce
     * @Description: 获取教程请求回调接口
     * @author enlizhang
     * @date 2015年7月26日 下午7:42:45
     *
     */
    public interface IShareListResponse extends IBaseResponse{

        /** 请求成功回调函数，返回分享列表 */
        public void success(List<Share> shareList);

    }

    /***
     * 获取测试分享数据
     * @return
     */
    public List<Share> getTestShareList(){
        List<Share> shareList = new ArrayList<Share>();

        int length = 2;

        List<String> bannerList = new ArrayList<String>();
        bannerList.add("http://u4.tdimg.com/7/203/19/46138657748730920288026757971472766587.jpg");
        bannerList.add("http://www.cnnb.com.cn/pic/0/01/49/86/1498602_864010.jpg");
        bannerList.add("http://u3.tdimg.com/6/88/143/_56696781343356143444965292996172123406.jpg");
        bannerList.add("http://i3.cqnews.net/news/attachement/jpg/site82/2011-07-27/4386628352243053135.jpg");

        for(int i=0;i<length;i++){
            Share share = new Share();
            share.shareId = ""+i;
            share.avatarUrl = "http://img3.imgtn.bdimg.com/it/u=3310376763,3294662014&fm=21&gp=0.jpg";
            share.nickname = "栗子" + i;
            share.hasAttention = (i==0);
            share.hasPraised = (i==0);
            share.coverUrl = bannerList.get(i);
            share.title = "栗子cos" + i;
            share.issueTimeStamp = System.currentTimeMillis();

            String totalPics = "2";
            share.totalPageNumber = "".equals(totalPics)?0:Integer.valueOf(totalPics);

            String praiseNum = "100";
            share.praiseNum = "".equals(praiseNum)?0:Integer.valueOf(praiseNum);

            String commentNum = "2";
            share.commentNum = "".equals(commentNum)?0:Integer.valueOf(commentNum);

            //设置评论数据
            List<Comment> commentList = new ArrayList<Comment>();

            int commentsLength = 2;
            Comment comment;
            for(int commentIndex=0;i<commentsLength;i++){

                comment = new Comment();
                comment.commentId = ""+commentIndex;
                comment.avatarUrl = "http://p2.gexing.com/touxiang/20120812/2335/5027cd5ea61c8.jpg";
                comment.content ="不错";

                comment.commentType = CommentType.getCommentTypeByValue("2");

                comment.commentTypeId = ""+i;
                comment.fromId = ""+i;
                comment.fromNickName = "蓝天";
                comment.targetId = ""+i;
                comment.targetNickname = ""+i;
                comment.commitTimeStamp = System.currentTimeMillis();
                commentList.add(comment);
            }

            share.commentList = commentList;
            shareList.add(share);
        }

        return shareList;
    }


}

