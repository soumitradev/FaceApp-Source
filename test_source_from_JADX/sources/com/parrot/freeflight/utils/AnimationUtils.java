package com.parrot.freeflight.utils;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

@SuppressLint({"NewApi"})
public final class AnimationUtils {

    /* renamed from: com.parrot.freeflight.utils.AnimationUtils$1 */
    class C16331 implements AnimationListener {
        private final /* synthetic */ View val$view;

        C16331(View view) {
            this.val$view = view;
        }

        public void onAnimationEnd(Animation animation) {
            this.val$view.setVisibility(4);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    /* renamed from: com.parrot.freeflight.utils.AnimationUtils$2 */
    class C16342 implements AnimationListener {
        private final /* synthetic */ View val$view;

        C16342(View view) {
            this.val$view = view;
        }

        public void onAnimationEnd(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
            this.val$view.setVisibility(0);
        }
    }

    private AnimationUtils() {
    }

    public static Animation makeInvisibleAnimated(View view) {
        Animation a = new AlphaAnimation(1.0f, 0.0f);
        a.setDuration(500);
        a.setAnimationListener(getFadeOutListener(view));
        view.startAnimation(a);
        return a;
    }

    @SuppressLint({"NewApi"})
    public static Animation makeVisibleAnimated(View view) {
        Animation a = new AlphaAnimation(0.0f, 1.0f);
        a.setDuration(500);
        a.setAnimationListener(getFadeInListener(view));
        view.startAnimation(a);
        return a;
    }

    private static AnimationListener getFadeOutListener(View view) {
        return new C16331(view);
    }

    private static AnimationListener getFadeInListener(View view) {
        return new C16342(view);
    }
}
