package android.support.v7.app;

import android.support.v7.widget.ContentFrameLayout.OnAttachListener;

class AppCompatDelegateImplV9$4 implements OnAttachListener {
    final /* synthetic */ AppCompatDelegateImplV9 this$0;

    AppCompatDelegateImplV9$4(AppCompatDelegateImplV9 this$0) {
        this.this$0 = this$0;
    }

    public void onAttachedFromWindow() {
    }

    public void onDetachedFromWindow() {
        this.this$0.dismissPopups();
    }
}
