package bolts;

import android.webkit.JavascriptInterface;
import bolts.WebViewAppLinkResolver.C07522;
import org.json.JSONArray;
import org.json.JSONException;

class WebViewAppLinkResolver$2$2 {
    final /* synthetic */ C07522 this$1;
    final /* synthetic */ TaskCompletionSource val$tcs;

    WebViewAppLinkResolver$2$2(C07522 c07522, TaskCompletionSource taskCompletionSource) {
        this.this$1 = c07522;
        this.val$tcs = taskCompletionSource;
    }

    @JavascriptInterface
    public void setValue(String value) {
        try {
            this.val$tcs.trySetResult(new JSONArray(value));
        } catch (JSONException e) {
            this.val$tcs.trySetError(e);
        }
    }
}
