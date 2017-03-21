package com.qs.sdk.common.util;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 常用的动画
 *
 * @author Administrator
 */
public class UtilByAnimation {
    private final static int timeScroll = 300;
    /**
     * 当需要与其他动画一直使用时 监听 myViewFlip.setInAnimation(inFromLeft);
     * myViewFlip.setOutAnimation(outToRight);
     */
//	protected Animation inFromRight, inFromLeft, outToRight, outToLeft;

    /**
     * 从上到下的动画
     *
     * @param context
     * @return
     */
    public static Animation getTopToBottomAnimation(Context context) {
//		context.obtainStyledAttributes(R.attr.top_bommom, com.sq.sdk.common.R.attr.);
        Animation topToBottom = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                +1.0f);
        topToBottom.setDuration(timeScroll);
        topToBottom.setInterpolator(new AccelerateInterpolator());
        return topToBottom;
    }

    /**
     * 从下到上的动画
     *
     * @param context
     * @return
     */
    public static Animation getBottomToTopAnimation(Context context) {
//		context.obtainStyledAttributes(R.attr.top_bommom, com.sq.sdk.common.R.attr.);
        Animation bottomTotop = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT,
                0.0f);
        bottomTotop.setDuration(timeScroll);
        bottomTotop.setInterpolator(new AccelerateInterpolator());
        return bottomTotop;
    }

    /**
     * 定义从右侧进入的动画效果
     *
     * @return
     */
    protected Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.0f);
        inFromRight.setDuration(timeScroll);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    /**
     * 定义从左侧退出的动画效果
     *
     * @return
     */
    protected Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(timeScroll);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    /**
     * 定义从左侧进入的动画效果
     *
     * @return
     */
    protected Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT,
                0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(timeScroll);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    /**
     * 定义从右侧退出时的动画效果
     *
     * @return
     */
    protected Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(timeScroll);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }
}
