package android.support.v4.widget;

import android.annotation.TargetApi;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.WindowInsets;

class DrawerLayout$1 implements OnApplyWindowInsetsListener {
    final /* synthetic */ DrawerLayout this$0;

    DrawerLayout$1(DrawerLayout this$0) {
        this.this$0 = this$0;
    }

    @TargetApi(21)
    public WindowInsets onApplyWindowInsets(View view, WindowInsets insets) {
        ((DrawerLayout) view).setChildInsets(insets, insets.getSystemWindowInsetTop() > 0);
        return insets.consumeSystemWindowInsets();
    }
}
