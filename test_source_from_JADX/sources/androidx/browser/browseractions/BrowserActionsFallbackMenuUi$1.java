package androidx.browser.browseractions;

import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.View;

class BrowserActionsFallbackMenuUi$1 implements OnShowListener {
    final /* synthetic */ BrowserActionsFallbackMenuUi this$0;
    final /* synthetic */ View val$view;

    BrowserActionsFallbackMenuUi$1(BrowserActionsFallbackMenuUi this$0, View view) {
        this.this$0 = this$0;
        this.val$view = view;
    }

    public void onShow(DialogInterface dialogInterface) {
        BrowserActionsFallbackMenuUi.access$000(this.this$0).onMenuShown(this.val$view);
    }
}
