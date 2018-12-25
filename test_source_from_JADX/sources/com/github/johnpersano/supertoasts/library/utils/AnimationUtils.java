package com.github.johnpersano.supertoasts.library.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;

public class AnimationUtils {
    private static final String ALPHA = "alpha";
    public static final long HIDE_DURATION = 250;
    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    public static final long SHOW_DURATION = 250;
    private static final String TRANSLATION_X = "translationX";
    private static final String TRANSLATION_Y = "translationY";

    public static int getSystemAnimationsResource(int animations) {
        switch (animations) {
            case 1:
                return 16973828;
            case 2:
                return 16973827;
            case 3:
                return 16973826;
            case 4:
                return 16973910;
            default:
                return 16973828;
        }
    }

    public static Animator getShowAnimation(SuperActivityToast superActivityToast) {
        PropertyValuesHolder propertyValuesHolderAlpha = PropertyValuesHolder.ofFloat(ALPHA, new float[]{0.0f, 1.0f});
        PropertyValuesHolder propertyValuesHolderX;
        switch (superActivityToast.getAnimations()) {
            case 1:
                return ObjectAnimator.ofPropertyValuesHolder(superActivityToast.getView(), new PropertyValuesHolder[]{propertyValuesHolderAlpha}).setDuration(250);
            case 2:
                propertyValuesHolderX = PropertyValuesHolder.ofFloat(TRANSLATION_X, new float[]{-500.0f, 0.0f});
                return ObjectAnimator.ofPropertyValuesHolder(superActivityToast.getView(), new PropertyValuesHolder[]{propertyValuesHolderX, propertyValuesHolderAlpha}).setDuration(250);
            case 3:
                propertyValuesHolderX = PropertyValuesHolder.ofFloat(SCALE_X, new float[]{0.0f, 1.0f});
                PropertyValuesHolder propertyValuesHolderScaleY = PropertyValuesHolder.ofFloat(SCALE_Y, new float[]{0.0f, 1.0f});
                return ObjectAnimator.ofPropertyValuesHolder(superActivityToast.getView(), new PropertyValuesHolder[]{propertyValuesHolderX, propertyValuesHolderScaleY, propertyValuesHolderAlpha}).setDuration(250);
            case 4:
                propertyValuesHolderX = PropertyValuesHolder.ofFloat(TRANSLATION_Y, new float[]{250.0f, 0.0f});
                return ObjectAnimator.ofPropertyValuesHolder(superActivityToast.getView(), new PropertyValuesHolder[]{propertyValuesHolderX, propertyValuesHolderAlpha}).setDuration(250);
            default:
                return ObjectAnimator.ofPropertyValuesHolder(superActivityToast.getView(), new PropertyValuesHolder[]{propertyValuesHolderAlpha}).setDuration(250);
        }
    }

    public static Animator getHideAnimation(SuperActivityToast superActivityToast) {
        PropertyValuesHolder propertyValuesHolderAlpha = PropertyValuesHolder.ofFloat(ALPHA, new float[]{1.0f, 0.0f});
        PropertyValuesHolder propertyValuesHolderX;
        switch (superActivityToast.getAnimations()) {
            case 1:
                return ObjectAnimator.ofPropertyValuesHolder(superActivityToast.getView(), new PropertyValuesHolder[]{propertyValuesHolderAlpha}).setDuration(250);
            case 2:
                propertyValuesHolderX = PropertyValuesHolder.ofFloat(TRANSLATION_X, new float[]{0.0f, 500.0f});
                return ObjectAnimator.ofPropertyValuesHolder(superActivityToast.getView(), new PropertyValuesHolder[]{propertyValuesHolderX, propertyValuesHolderAlpha}).setDuration(250);
            case 3:
                propertyValuesHolderX = PropertyValuesHolder.ofFloat(SCALE_X, new float[]{1.0f, 0.0f});
                PropertyValuesHolder propertyValuesHolderScaleY = PropertyValuesHolder.ofFloat(SCALE_Y, new float[]{1.0f, 0.0f});
                return ObjectAnimator.ofPropertyValuesHolder(superActivityToast.getView(), new PropertyValuesHolder[]{propertyValuesHolderX, propertyValuesHolderScaleY, propertyValuesHolderAlpha}).setDuration(250);
            case 4:
                propertyValuesHolderX = PropertyValuesHolder.ofFloat(TRANSLATION_Y, new float[]{0.0f, 250.0f});
                return ObjectAnimator.ofPropertyValuesHolder(superActivityToast.getView(), new PropertyValuesHolder[]{propertyValuesHolderX, propertyValuesHolderAlpha}).setDuration(250);
            default:
                return ObjectAnimator.ofPropertyValuesHolder(superActivityToast.getView(), new PropertyValuesHolder[]{propertyValuesHolderAlpha}).setDuration(250);
        }
    }
}
