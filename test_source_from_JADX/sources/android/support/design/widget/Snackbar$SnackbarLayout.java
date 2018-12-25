package android.support.design.widget;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.widget.BaseTransientBottomBar.SnackbarBaseLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.common.primitives.Ints;

@RestrictTo({Scope.LIBRARY_GROUP})
public final class Snackbar$SnackbarLayout extends SnackbarBaseLayout {
    public Snackbar$SnackbarLayout(Context context) {
        super(context);
    }

    public Snackbar$SnackbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int availableWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getLayoutParams().width == -1) {
                child.measure(MeasureSpec.makeMeasureSpec(availableWidth, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), Ints.MAX_POWER_OF_TWO));
            }
        }
    }
}
