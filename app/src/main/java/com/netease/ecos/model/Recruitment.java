package com.netease.ecos.model;

import java.util.List;

/**
 * @author enlizhang
 * @ClassName: Recruitment
 * @Description: 招募
 * @date 2015年7月25日 下午11:31:09
 */
public class Recruitment {

    /**
     * 招募id
     */
    public String recruitmentId;

    /**
     * 标题
     */
    public String title;

    /**
     * 介绍
     */
    public String decription;

    /**
     * 发起者的用户id{@link User}
     */
    public String userId;

    /**
     * 发起者的用户昵称
     */
    public String nickname;

    /**
     * 发起者云信id
     */
    public String imId;

    /**
     * 发起者性别
     */
    public User.Gender gender;

    /**
     * 发起者头像url
     */
    public String avatarUrl;

    /**
     * 均价
     */
    public String averagePrice;

    /**
     * 封面图本地路径
     */
    public String coverLocalPath;

    /**
     * 封面图url
     */
    public String coverUrl;

    /**
     * 发布时间,是一个时间戳
     */
    public long issueTimeStamp;

    /**
     * 距离，单位千米
     */
    public String distanceKM;

    /**
     * 作品{@link Share}}列表
     */
    public List<Share> shareList;


    /**
     * 根据{@link #issueTimeStamp}获取发布时间描述
     *
     * @return
     */
    public String getDateDescription() {

        return ModelUtils.getDateDesByTimeStamp(issueTimeStamp);
    }


    /**
     * @author enlizhang
     * @ClassName: RecruitType
     * @Description: 招募类别，包括妆娘、摄影、后期、服装、道具、其他
     * @date 2015年7月30日 下午9:37:51
     */
    public static enum RecruitType {
        妆娘("0"),
        摄影("1"),
        后期("2"),
        服装("3"),
        道具("4"),
        其他("5");


        public String value;

        RecruitType(String _value) {
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
        public static RecruitType getRecruitTypeByValue(String value) {

            for (RecruitType recruitType : RecruitType.values()) {
                if (recruitType.getValue().equals(value))
                    return recruitType;
            }

            return null;
        }
    }
}
