package androidx.browser.browseractions;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.view.View;

@VisibleForTesting
@RestrictTo({Scope.LIBRARY_GROUP})
interface BrowserActionsFallbackMenuUi$BrowserActionsFallMenuUiListener {
    void onMenuShown(View view);
}
