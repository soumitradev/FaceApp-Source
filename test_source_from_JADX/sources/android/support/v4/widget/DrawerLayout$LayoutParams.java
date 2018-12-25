package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

public class DrawerLayout$LayoutParams extends MarginLayoutParams {
    private static final int FLAG_IS_CLOSING = 4;
    private static final int FLAG_IS_OPENED = 1;
    private static final int FLAG_IS_OPENING = 2;
    public int gravity;
    boolean isPeeking;
    float onScreen;
    int openState;

    public DrawerLayout$LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
        super(c, attrs);
        this.gravity = 0;
        TypedArray a = c.obtainStyledAttributes(attrs, DrawerLayout.LAYOUT_ATTRS);
        this.gravity = a.getInt(0, 0);
        a.recycle();
    }

    public DrawerLayout$LayoutParams(int width, int height) {
        super(width, height);
        this.gravity = 0;
    }

    public DrawerLayout$LayoutParams(int width, int height, int gravity) {
        this(width, height);
        this.gravity = gravity;
    }

    public DrawerLayout$LayoutParams(@NonNull DrawerLayout$LayoutParams source) {
        super(source);
        this.gravity = 0;
        this.gravity = source.gravity;
    }

    public DrawerLayout$LayoutParams(@NonNull LayoutParams source) {
        super(source);
        this.gravity = 0;
    }

    public DrawerLayout$LayoutParams(@NonNull MarginLayoutParams source) {
        super(source);
        this.gravity = 0;
    }
}
