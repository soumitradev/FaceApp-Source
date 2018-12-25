package android.support.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.view.View;

class TranslationAnimationCreator {
    TranslationAnimationCreator() {
    }

    static Animator createAnimation(View view, TransitionValues values, int viewPosX, int viewPosY, float startX, float startY, float endX, float endY, TimeInterpolator interpolator) {
        float startX2;
        float startY2;
        View view2 = view;
        TransitionValues transitionValues = values;
        float terminalX = view.getTranslationX();
        float terminalY = view.getTranslationY();
        int[] startPosition = (int[]) transitionValues.view.getTag(C0147R.id.transition_position);
        if (startPosition != null) {
            startX2 = ((float) (startPosition[0] - viewPosX)) + terminalX;
            startY2 = ((float) (startPosition[1] - viewPosY)) + terminalY;
        } else {
            startX2 = startX;
            startY2 = startY;
        }
        int startPosX = viewPosX + Math.round(startX2 - terminalX);
        int startPosY = viewPosY + Math.round(startY2 - terminalY);
        view2.setTranslationX(startX2);
        view2.setTranslationY(startY2);
        if (startX2 == endX && startY2 == endY) {
            return null;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[2];
        propertyValuesHolderArr[0] = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, new float[]{startX2, endX});
        propertyValuesHolderArr[1] = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, new float[]{startY2, endY});
        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(view2, propertyValuesHolderArr);
        TranslationAnimationCreator$TransitionPositionListener listener = new TranslationAnimationCreator$TransitionPositionListener(view2, transitionValues.view, startPosX, startPosY, terminalX, terminalY, null);
        ObjectAnimator anim2 = anim;
        anim2.addListener(listener);
        AnimatorUtils.addPauseListener(anim2, listener);
        anim2.setInterpolator(interpolator);
        return anim2;
    }
}
