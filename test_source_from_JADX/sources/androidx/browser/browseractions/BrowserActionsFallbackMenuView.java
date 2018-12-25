package androidx.browser.browseractions;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.customtabs.C0100R;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import com.google.common.primitives.Ints;

@RestrictTo({Scope.LIBRARY_GROUP})
public class BrowserActionsFallbackMenuView extends LinearLayout {
    private final int mBrowserActionsMenuMaxWidthPx = getResources().getDimensionPixelOffset(C0100R.dimen.browser_actions_context_menu_max_width);
    private final int mBrowserActionsMenuMinPaddingPx = getResources().getDimensionPixelOffset(C0100R.dimen.browser_actions_context_menu_min_padding);

    public BrowserActionsFallbackMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(Math.min(getResources().getDisplayMetrics().widthPixels - (this.mBrowserActionsMenuMinPaddingPx * 2), this.mBrowserActionsMenuMaxWidthPx), Ints.MAX_POWER_OF_TWO), heightMeasureSpec);
    }
}
