package android.support.v7.app;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;

class AppCompatDelegateImplV9$6 extends ViewPropertyAnimatorListenerAdapter {
    final /* synthetic */ AppCompatDelegateImplV9 this$0;

    AppCompatDelegateImplV9$6(AppCompatDelegateImplV9 this$0) {
        this.this$0 = this$0;
    }

    public void onAnimationStart(View view) {
        this.this$0.mActionModeView.setVisibility(0);
        this.this$0.mActionModeView.sendAccessibilityEvent(32);
        if (this.this$0.mActionModeView.getParent() instanceof View) {
            ViewCompat.requestApplyInsets((View) this.this$0.mActionModeView.getParent());
        }
    }

    public void onAnimationEnd(View view) {
        this.this$0.mActionModeView.setAlpha(1.0f);
        this.this$0.mFadeAnim.setListener(null);
        this.this$0.mFadeAnim = null;
    }
}
