package android.support.v7.app;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;

class AppCompatDelegateImplV9$5 implements Runnable {
    final /* synthetic */ AppCompatDelegateImplV9 this$0;

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV9$5$1 */
    class C12081 extends ViewPropertyAnimatorListenerAdapter {
        C12081() {
        }

        public void onAnimationStart(View view) {
            AppCompatDelegateImplV9$5.this.this$0.mActionModeView.setVisibility(0);
        }

        public void onAnimationEnd(View view) {
            AppCompatDelegateImplV9$5.this.this$0.mActionModeView.setAlpha(1.0f);
            AppCompatDelegateImplV9$5.this.this$0.mFadeAnim.setListener(null);
            AppCompatDelegateImplV9$5.this.this$0.mFadeAnim = null;
        }
    }

    AppCompatDelegateImplV9$5(AppCompatDelegateImplV9 this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.mActionModePopup.showAtLocation(this.this$0.mActionModeView, 55, 0, 0);
        this.this$0.endOnGoingFadeAnimation();
        if (this.this$0.shouldAnimateActionModeView()) {
            this.this$0.mActionModeView.setAlpha(0.0f);
            this.this$0.mFadeAnim = ViewCompat.animate(this.this$0.mActionModeView).alpha(1.0f);
            this.this$0.mFadeAnim.setListener(new C12081());
            return;
        }
        this.this$0.mActionModeView.setAlpha(1.0f);
        this.this$0.mActionModeView.setVisibility(0);
    }
}
