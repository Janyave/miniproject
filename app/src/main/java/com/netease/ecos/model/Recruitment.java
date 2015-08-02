package com.netease.ecos.model;

import com.netease.ecos.model.User.Gender;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName: Recruitment
 * @Description: 招募
 * @author enlizhang
 * @date 2015年7月25日 下午11:31:09
 *
 */
public class Recruitment {

    /*** 招募id */
    public String recruitmentId;

    /*** 分享id */
    public String shareId;

    /*** 标题 */
    public String  title;

    /*** 介绍 */
    public String description;

    /*** 发起者的用户id{@link User} */
    public String userId;

    /*** 发起者的用户昵称 */
    public String nickname;

    /*** 发起者云信id */
    public String imId;

    /*** 发起者性别 */
    public Gender gender;

    /*** 发起者头像url */
    public String avatarUrl;

    /*** 均价 */
    public String averagePrice;

    /*** 单位 */
    public String priceUnit;

    /*** 封面图本地路径 */
    public String coverLocalPath;

    /*** 封面图url */
    public String  coverUrl;

    /** 发布时间,是一个时间戳 */
    public long issueTimeStamp ;

    /*** 距离，单位千米 */
    public String distanceKM;

    /*** 作品{@link Share}}列表 */
    public List<Share> shareList;

    public Recruitment(){
        gender = Gender.男;
        shareList = new ArrayList<Share>();
    }


    /***
     *
     * @ClassName: RecruitType
     * @Description: 招募类别，包括妆娘、摄影、后期、服装、道具、其他
     * @author enlizhang
     * @date 2015年7月30日 下午9:37:51
     *
     */
    public static enum RecruitType
    {
        妆娘("0"),
        摄影("1"),
        后期("2"),
        服装("3"),
        道具("4"),
        其他("5"),
        个人("6"),;


        public String value;

        RecruitType(String _value){
            value = _value;
        }

        public String getValue(){
            return value;
        }

        /***
         * 根据值获取对应枚举
         * @param value
         * @return
         */
        public static RecruitType getRecruitTypeByValue(String value){

            for(RecruitType recruitType:RecruitType.values()){
                if(recruitType.getValue().equals(value))
                    return recruitType;
            }

            return null;
        }
    }
}
