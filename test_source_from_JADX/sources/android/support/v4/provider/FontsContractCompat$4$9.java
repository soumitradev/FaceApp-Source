package android.support.v4.provider;

import android.graphics.Typeface;
import android.support.v4.provider.FontsContractCompat.C00214;

class FontsContractCompat$4$9 implements Runnable {
    final /* synthetic */ C00214 this$0;
    final /* synthetic */ Typeface val$typeface;

    FontsContractCompat$4$9(C00214 this$0, Typeface typeface) {
        this.this$0 = this$0;
        this.val$typeface = typeface;
    }

    public void run() {
        this.this$0.val$callback.onTypefaceRetrieved(this.val$typeface);
    }
}
