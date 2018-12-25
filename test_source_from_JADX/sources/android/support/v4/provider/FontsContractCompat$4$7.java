package android.support.v4.provider;

import android.support.v4.provider.FontsContractCompat.C00214;

class FontsContractCompat$4$7 implements Runnable {
    final /* synthetic */ C00214 this$0;
    final /* synthetic */ int val$resultCode;

    FontsContractCompat$4$7(C00214 this$0, int i) {
        this.this$0 = this$0;
        this.val$resultCode = i;
    }

    public void run() {
        this.this$0.val$callback.onTypefaceRequestFailed(this.val$resultCode);
    }
}
