package android.support.v4.content.res;

import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat.FontCallback;

class ResourcesCompat$FontCallback$1 implements Runnable {
    final /* synthetic */ FontCallback this$0;
    final /* synthetic */ Typeface val$typeface;

    ResourcesCompat$FontCallback$1(FontCallback this$0, Typeface typeface) {
        this.this$0 = this$0;
        this.val$typeface = typeface;
    }

    public void run() {
        this.this$0.onFontRetrieved(this.val$typeface);
    }
}
