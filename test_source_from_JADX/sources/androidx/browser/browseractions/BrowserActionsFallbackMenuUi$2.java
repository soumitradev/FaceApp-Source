package androidx.browser.browseractions;

import android.support.v4.widget.TextViewCompat;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

class BrowserActionsFallbackMenuUi$2 implements OnClickListener {
    final /* synthetic */ BrowserActionsFallbackMenuUi this$0;
    final /* synthetic */ TextView val$urlTextView;

    BrowserActionsFallbackMenuUi$2(BrowserActionsFallbackMenuUi this$0, TextView textView) {
        this.this$0 = this$0;
        this.val$urlTextView = textView;
    }

    public void onClick(View view) {
        if (TextViewCompat.getMaxLines(this.val$urlTextView) == Integer.MAX_VALUE) {
            this.val$urlTextView.setMaxLines(1);
            this.val$urlTextView.setEllipsize(TruncateAt.END);
            return;
        }
        this.val$urlTextView.setMaxLines(Integer.MAX_VALUE);
        this.val$urlTextView.setEllipsize(null);
    }
}
