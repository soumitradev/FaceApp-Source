package android.support.v4.provider;

import android.support.v4.provider.FontsContractCompat.C00214;

class FontsContractCompat$4$1 implements Runnable {
    final /* synthetic */ C00214 this$0;

    FontsContractCompat$4$1(C00214 this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.val$callback.onTypefaceRequestFailed(-1);
    }
}
