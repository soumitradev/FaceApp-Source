package com.facebook.internal;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsIntent$Builder;

public class CustomTab {
    private Uri uri;

    public CustomTab(String action, Bundle parameters) {
        if (parameters == null) {
            parameters = new Bundle();
        }
        String dialogAuthority = ServerProtocol.getDialogAuthority();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServerProtocol.getAPIVersion());
        stringBuilder.append("/");
        stringBuilder.append(ServerProtocol.DIALOG_PATH);
        stringBuilder.append(action);
        this.uri = Utility.buildUri(dialogAuthority, stringBuilder.toString(), parameters);
    }

    public void openCustomTab(Activity activity, String packageName) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent$Builder().build();
        customTabsIntent.intent.setPackage(packageName);
        customTabsIntent.launchUrl(activity, this.uri);
    }
}
