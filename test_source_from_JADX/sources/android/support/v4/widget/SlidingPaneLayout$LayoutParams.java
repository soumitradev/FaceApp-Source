package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

public class SlidingPaneLayout$LayoutParams extends MarginLayoutParams {
    private static final int[] ATTRS = new int[]{16843137};
    Paint dimPaint;
    boolean dimWhenOffset;
    boolean slideable;
    public float weight = 0.0f;

    public SlidingPaneLayout$LayoutParams() {
        super(-1, -1);
    }

    public SlidingPaneLayout$LayoutParams(int width, int height) {
        super(width, height);
    }

    public SlidingPaneLayout$LayoutParams(@NonNull LayoutParams source) {
        super(source);
    }

    public SlidingPaneLayout$LayoutParams(@NonNull MarginLayoutParams source) {
        super(source);
    }

    public SlidingPaneLayout$LayoutParams(@NonNull SlidingPaneLayout$LayoutParams source) {
        super(source);
        this.weight = source.weight;
    }

    public SlidingPaneLayout$LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
        super(c, attrs);
        TypedArray a = c.obtainStyledAttributes(attrs, ATTRS);
        this.weight = a.getFloat(0, 0.0f);
        a.recycle();
    }
}
