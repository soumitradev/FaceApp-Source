package android.support.v7.app;

import android.graphics.Rect;
import android.support.v7.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;

class AppCompatDelegateImplV9$3 implements OnFitSystemWindowsListener {
    final /* synthetic */ AppCompatDelegateImplV9 this$0;

    AppCompatDelegateImplV9$3(AppCompatDelegateImplV9 this$0) {
        this.this$0 = this$0;
    }

    public void onFitSystemWindows(Rect insets) {
        insets.top = this.this$0.updateStatusGuard(insets.top);
    }
}
