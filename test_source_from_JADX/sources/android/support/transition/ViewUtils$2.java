package android.support.transition;

import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.Property;
import android.view.View;

class ViewUtils$2 extends Property<View, Rect> {
    ViewUtils$2(Class x0, String x1) {
        super(x0, x1);
    }

    public Rect get(View view) {
        return ViewCompat.getClipBounds(view);
    }

    public void set(View view, Rect clipBounds) {
        ViewCompat.setClipBounds(view, clipBounds);
    }
}
