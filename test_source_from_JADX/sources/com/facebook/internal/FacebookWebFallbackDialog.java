package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookWebFallbackDialog extends WebDialog {
    private static final int OS_BACK_BUTTON_RESPONSE_TIMEOUT_MILLISECONDS = 1500;
    private static final String TAG = FacebookWebFallbackDialog.class.getName();
    private boolean waitingForDialogToClose;

    /* renamed from: com.facebook.internal.FacebookWebFallbackDialog$1 */
    class C04341 implements Runnable {
        C04341() {
        }

        public void run() {
            super.cancel();
        }
    }

    public FacebookWebFallbackDialog(Context context, String url, String expectedRedirectUrl) {
        super(context, url);
        setExpectedRedirectUrl(expectedRedirectUrl);
    }

    protected Bundle parseResponseUri(String url) {
        Bundle queryParams = Utility.parseUrlQueryString(Uri.parse(url).getQuery());
        String bridgeArgsJSONString = queryParams.getString(ServerProtocol.FALLBACK_DIALOG_PARAM_BRIDGE_ARGS);
        queryParams.remove(ServerProtocol.FALLBACK_DIALOG_PARAM_BRIDGE_ARGS);
        if (Utility.isNullOrEmpty(bridgeArgsJSONString)) {
            Bundle methodResults = null;
        } else {
            try {
                queryParams.putBundle(NativeProtocol.EXTRA_PROTOCOL_BRIDGE_ARGS, BundleJSONConverter.convertToBundle(new JSONObject(bridgeArgsJSONString)));
            } catch (JSONException je) {
                Utility.logd(TAG, "Unable to parse bridge_args JSON", je);
            }
        }
        String methodResultsJSONString = queryParams.getString(ServerProtocol.FALLBACK_DIALOG_PARAM_METHOD_RESULTS);
        queryParams.remove(ServerProtocol.FALLBACK_DIALOG_PARAM_METHOD_RESULTS);
        if (!Utility.isNullOrEmpty(methodResultsJSONString)) {
            try {
                queryParams.putBundle(NativeProtocol.EXTRA_PROTOCOL_METHOD_RESULTS, BundleJSONConverter.convertToBundle(new JSONObject(Utility.isNullOrEmpty(methodResultsJSONString) ? "{}" : methodResultsJSONString)));
            } catch (JSONException je2) {
                Utility.logd(TAG, "Unable to parse bridge_args JSON", je2);
            }
        }
        queryParams.remove(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION);
        queryParams.putInt(NativeProtocol.EXTRA_PROTOCOL_VERSION, NativeProtocol.getLatestKnownVersion());
        return queryParams;
    }

    public void cancel() {
        WebView webView = getWebView();
        if (!(!isPageFinished() || isListenerCalled() || webView == null)) {
            if (webView.isShown()) {
                if (!this.waitingForDialogToClose) {
                    this.waitingForDialogToClose = true;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("javascript:");
                    stringBuilder.append("(function() {  var event = document.createEvent('Event');  event.initEvent('fbPlatformDialogMustClose',true,true);  document.dispatchEvent(event);})();");
                    webView.loadUrl(stringBuilder.toString());
                    new Handler(Looper.getMainLooper()).postDelayed(new C04341(), 1500);
                    return;
                }
                return;
            }
        }
        super.cancel();
    }
}
