package android.support.v7.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup;

public class ActionMenuView$LayoutParams extends LayoutParams {
    @ExportedProperty
    public int cellsUsed;
    @ExportedProperty
    public boolean expandable;
    boolean expanded;
    @ExportedProperty
    public int extraPixels;
    @ExportedProperty
    public boolean isOverflowButton;
    @ExportedProperty
    public boolean preventEdgeOffset;

    public ActionMenuView$LayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public ActionMenuView$LayoutParams(ViewGroup.LayoutParams other) {
        super(other);
    }

    public ActionMenuView$LayoutParams(ActionMenuView$LayoutParams other) {
        super((ViewGroup.LayoutParams) other);
        this.isOverflowButton = other.isOverflowButton;
    }

    public ActionMenuView$LayoutParams(int width, int height) {
        super(width, height);
        this.isOverflowButton = false;
    }

    ActionMenuView$LayoutParams(int width, int height, boolean isOverflowButton) {
        super(width, height);
        this.isOverflowButton = isOverflowButton;
    }
}
