package android.support.v4.widget;

import android.view.animation.Interpolator;

class ViewDragHelper$1 implements Interpolator {
    ViewDragHelper$1() {
    }

    public float getInterpolation(float t) {
        t -= 1.0f;
        return ((((t * t) * t) * t) * t) + 1.0f;
    }
}
