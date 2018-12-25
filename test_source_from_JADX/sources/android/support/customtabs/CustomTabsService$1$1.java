package android.support.customtabs;

import android.os.IBinder.DeathRecipient;
import android.support.customtabs.CustomTabsService.C00751;

class CustomTabsService$1$1 implements DeathRecipient {
    final /* synthetic */ C00751 this$1;
    final /* synthetic */ CustomTabsSessionToken val$sessionToken;

    CustomTabsService$1$1(C00751 this$1, CustomTabsSessionToken customTabsSessionToken) {
        this.this$1 = this$1;
        this.val$sessionToken = customTabsSessionToken;
    }

    public void binderDied() {
        this.this$1.this$0.cleanUpSession(this.val$sessionToken);
    }
}
