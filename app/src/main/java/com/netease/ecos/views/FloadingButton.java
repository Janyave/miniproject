package com.netease.ecos.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 主页悬浮按钮
 * Created by hzjixinyu on 2015/7/23.
 */
public class FloadingButton extends Button{

    public FloadingButton(Context context) {
        super(context);
    }

    public FloadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * button appear
     * @param doAfterAnimation
     */
    public void appear(AnimationHelper.DoAfterAnimation doAfterAnimation){
        AnimationHelper.setViewMoveAnimation(this, 0, -300, 0, 1, 500, doAfterAnimation);
    }

    /**
     * button disappear
     * @param doAfterAnimation
     */
    public void disappear(AnimationHelper.DoAfterAnimation doAfterAnimation){
        AnimationHelper.setViewMoveAnimation(this, 0, 300, 1, 0, 500,doAfterAnimation);
    }
}
