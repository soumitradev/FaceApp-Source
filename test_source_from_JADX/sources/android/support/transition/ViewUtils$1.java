package android.support.transition;

import android.util.Property;
import android.view.View;

class ViewUtils$1 extends Property<View, Float> {
    ViewUtils$1(Class x0, String x1) {
        super(x0, x1);
    }

    public Float get(View view) {
        return Float.valueOf(ViewUtils.getTransitionAlpha(view));
    }

    public void set(View view, Float alpha) {
        ViewUtils.setTransitionAlpha(view, alpha.floatValue());
    }
}
