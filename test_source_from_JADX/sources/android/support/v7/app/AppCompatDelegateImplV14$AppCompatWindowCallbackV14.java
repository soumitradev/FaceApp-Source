package android.support.v7.app;

import android.support.v7.view.SupportActionModeWrapper.CallbackWrapper;
import android.view.ActionMode;
import android.view.Window.Callback;

class AppCompatDelegateImplV14$AppCompatWindowCallbackV14 extends AppCompatDelegateImplBase$AppCompatWindowCallbackBase {
    final /* synthetic */ AppCompatDelegateImplV14 this$0;

    AppCompatDelegateImplV14$AppCompatWindowCallbackV14(AppCompatDelegateImplV14 this$0, Callback callback) {
        this.this$0 = this$0;
        super(this$0, callback);
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        if (this.this$0.isHandleNativeActionModesEnabled()) {
            return startAsSupportActionMode(callback);
        }
        return super.onWindowStartingActionMode(callback);
    }

    final ActionMode startAsSupportActionMode(ActionMode.Callback callback) {
        CallbackWrapper callbackWrapper = new CallbackWrapper(this.this$0.mContext, callback);
        android.support.v7.view.ActionMode supportActionMode = this.this$0.startSupportActionMode(callbackWrapper);
        if (supportActionMode != null) {
            return callbackWrapper.getActionModeWrapper(supportActionMode);
        }
        return null;
    }
}
