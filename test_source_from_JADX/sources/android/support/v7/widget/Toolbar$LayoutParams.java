package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar.LayoutParams;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;

public class Toolbar$LayoutParams extends LayoutParams {
    static final int CUSTOM = 0;
    static final int EXPANDED = 2;
    static final int SYSTEM = 1;
    int mViewType;

    public Toolbar$LayoutParams(@NonNull Context c, AttributeSet attrs) {
        super(c, attrs);
        this.mViewType = 0;
    }

    public Toolbar$LayoutParams(int width, int height) {
        super(width, height);
        this.mViewType = 0;
        this.gravity = 8388627;
    }

    public Toolbar$LayoutParams(int width, int height, int gravity) {
        super(width, height);
        this.mViewType = 0;
        this.gravity = gravity;
    }

    public Toolbar$LayoutParams(int gravity) {
        this(-2, -1, gravity);
    }

    public Toolbar$LayoutParams(Toolbar$LayoutParams source) {
        super(source);
        this.mViewType = 0;
        this.mViewType = source.mViewType;
    }

    public Toolbar$LayoutParams(LayoutParams source) {
        super(source);
        this.mViewType = 0;
    }

    public Toolbar$LayoutParams(MarginLayoutParams source) {
        super(source);
        this.mViewType = 0;
        copyMarginsFromCompat(source);
    }

    public Toolbar$LayoutParams(ViewGroup.LayoutParams source) {
        super(source);
        this.mViewType = 0;
    }

    void copyMarginsFromCompat(MarginLayoutParams source) {
        this.leftMargin = source.leftMargin;
        this.topMargin = source.topMargin;
        this.rightMargin = source.rightMargin;
        this.bottomMargin = source.bottomMargin;
    }
}
