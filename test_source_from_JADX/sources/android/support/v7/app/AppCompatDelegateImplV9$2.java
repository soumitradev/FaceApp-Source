package android.support.v7.app;

import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;

class AppCompatDelegateImplV9$2 implements OnApplyWindowInsetsListener {
    final /* synthetic */ AppCompatDelegateImplV9 this$0;

    AppCompatDelegateImplV9$2(AppCompatDelegateImplV9 this$0) {
        this.this$0 = this$0;
    }

    public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
        int top = insets.getSystemWindowInsetTop();
        int newTop = this.this$0.updateStatusGuard(top);
        if (top != newTop) {
            insets = insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), newTop, insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
        }
        return ViewCompat.onApplyWindowInsets(v, insets);
    }
}
