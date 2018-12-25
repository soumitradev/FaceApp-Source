package android.support.v4.content.res;

import android.support.v4.content.res.ResourcesCompat.FontCallback;

class ResourcesCompat$FontCallback$2 implements Runnable {
    final /* synthetic */ FontCallback this$0;
    final /* synthetic */ int val$reason;

    ResourcesCompat$FontCallback$2(FontCallback this$0, int i) {
        this.this$0 = this$0;
        this.val$reason = i;
    }

    public void run() {
        this.this$0.onFontRetrievalFailed(this.val$reason);
    }
}
