package com.netease.ecos.model;

import android.os.Parcel;
import android.os.Parcelable;

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
    public static class Step implements Parcelable {

        public Step(int index){
            stepIndex = index;
        }

        /*** 步骤序号 */
        public int stepIndex;

        /*** 步骤对应的图片存储路径 */
        public String imagePath;

        /*** 步骤对应的图片url */
        public String photoUrl;

        /*** 步骤描述 */
        public String description;


        @Override
        public void writeToParcel(Parcel out, int flags)
        {
            out.writeInt(stepIndex);
            out.writeString(imagePath);
            out.writeString(photoUrl);
            out.writeString(description);

        }

        public static final Parcelable.Creator<Step> CREATOR = new Creator<Step>()
        {
            @Override
            public Step[] newArray(int size)
            {
                return new Step[size];
            }

            @Override
            public Step createFromParcel(Parcel in)
            {
                return new Step(in);
            }
        };

        public Step(Parcel in)
        {
            stepIndex = in.readInt();
            imagePath = in.readString();
            photoUrl = in.readString();
            description = in.readString();

        }

        @Override
        public int describeContents() {
            // TODO Auto-generated method stub
            return 0;
        }

        /***
         * {@link #stepIndex}、{@link #imagePath}、{@link #description}是否有为空的(null或空串)
         * @return true:除{@link #photoUrl}外数据都不为空 false:除{@link #photoUrl}外数据有数据为空
         */
        public boolean isSomeEmpty(){

            boolean result = (imagePath==null || "".equals(imagePath)
                    ||description==null || "".equals(description));

            return  result;
        }

        @Override
        public String toString() {
            return "Step{" +
                    "stepIndex=" + stepIndex +
                    ", imagePath='" + imagePath + '\'' +
                    ", photoUrl='" + photoUrl + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
