package com.netease.ecos.model;

/**
 * 类描述：教程
 * Created by enlizhang on 2015/7/22.
 */
public class Course {

    /*** 教程封面图片url */
    public String coverUrl;

    /*** 教程名称 */
    public String courseName;


    /***
     * 教程步骤
     */
    public static class Step{

        /*** 步骤序号 */
        public int stepIndex;

        /*** 步骤对应的图片url */
        public String photoUrl;

        /*** 步骤描述 */
        public String description;

    }
}
