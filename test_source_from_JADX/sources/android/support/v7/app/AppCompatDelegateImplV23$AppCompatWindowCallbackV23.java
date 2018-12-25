package android.support.v7.app;

import android.view.ActionMode;
import android.view.Window.Callback;

class AppCompatDelegateImplV23$AppCompatWindowCallbackV23 extends AppCompatDelegateImplV14$AppCompatWindowCallbackV14 {
    final /* synthetic */ AppCompatDelegateImplV23 this$0;

    AppCompatDelegateImplV23$AppCompatWindowCallbackV23(AppCompatDelegateImplV23 this$0, Callback callback) {
        this.this$0 = this$0;
        super(this$0, callback);
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        if (this.this$0.isHandleNativeActionModesEnabled()) {
            if (type == 0) {
                return startAsSupportActionMode(callback);
            }
        }
        return super.onWindowStartingActionMode(callback, type);
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }
}
