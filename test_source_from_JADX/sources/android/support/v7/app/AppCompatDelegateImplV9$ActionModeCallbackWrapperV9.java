package android.support.v7.app;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

class AppCompatDelegateImplV9$ActionModeCallbackWrapperV9 implements Callback {
    private Callback mWrapped;
    final /* synthetic */ AppCompatDelegateImplV9 this$0;

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV9$ActionModeCallbackWrapperV9$1 */
    class C12091 extends ViewPropertyAnimatorListenerAdapter {
        C12091() {
        }

        public void onAnimationEnd(View view) {
            AppCompatDelegateImplV9$ActionModeCallbackWrapperV9.this.this$0.mActionModeView.setVisibility(8);
            if (AppCompatDelegateImplV9$ActionModeCallbackWrapperV9.this.this$0.mActionModePopup != null) {
                AppCompatDelegateImplV9$ActionModeCallbackWrapperV9.this.this$0.mActionModePopup.dismiss();
            } else if (AppCompatDelegateImplV9$ActionModeCallbackWrapperV9.this.this$0.mActionModeView.getParent() instanceof View) {
                ViewCompat.requestApplyInsets((View) AppCompatDelegateImplV9$ActionModeCallbackWrapperV9.this.this$0.mActionModeView.getParent());
            }
            AppCompatDelegateImplV9$ActionModeCallbackWrapperV9.this.this$0.mActionModeView.removeAllViews();
            AppCompatDelegateImplV9$ActionModeCallbackWrapperV9.this.this$0.mFadeAnim.setListener(null);
            AppCompatDelegateImplV9$ActionModeCallbackWrapperV9.this.this$0.mFadeAnim = null;
        }
    }

    public AppCompatDelegateImplV9$ActionModeCallbackWrapperV9(AppCompatDelegateImplV9 this$0, Callback wrapped) {
        this.this$0 = this$0;
        this.mWrapped = wrapped;
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return this.mWrapped.onCreateActionMode(mode, menu);
    }

    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return this.mWrapped.onPrepareActionMode(mode, menu);
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return this.mWrapped.onActionItemClicked(mode, item);
    }

    public void onDestroyActionMode(ActionMode mode) {
        this.mWrapped.onDestroyActionMode(mode);
        if (this.this$0.mActionModePopup != null) {
            this.this$0.mWindow.getDecorView().removeCallbacks(this.this$0.mShowActionModePopup);
        }
        if (this.this$0.mActionModeView != null) {
            this.this$0.endOnGoingFadeAnimation();
            this.this$0.mFadeAnim = ViewCompat.animate(this.this$0.mActionModeView).alpha(0.0f);
            this.this$0.mFadeAnim.setListener(new C12091());
        }
        if (this.this$0.mAppCompatCallback != null) {
            this.this$0.mAppCompatCallback.onSupportActionModeFinished(this.this$0.mActionMode);
        }
        this.this$0.mActionMode = null;
    }
}
