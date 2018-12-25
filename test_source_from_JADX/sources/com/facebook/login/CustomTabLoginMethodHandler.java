package com.facebook.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookSdk;
import com.facebook.internal.CustomTab;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient.Request;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomTabLoginMethodHandler extends WebLoginMethodHandler {
    private static final String CHROME_PACKAGE = "com.android.chrome";
    private static final String[] CHROME_PACKAGES = new String[]{CHROME_PACKAGE, "com.chrome.beta", "com.chrome.dev"};
    public static final Creator<CustomTabLoginMethodHandler> CREATOR = new C04521();
    private static final String CUSTOM_TABS_SERVICE_ACTION = "android.support.customtabs.action.CustomTabsService";
    private static final String OAUTH_DIALOG = "oauth";
    private String currentPackage;
    private CustomTab customTab;

    /* renamed from: com.facebook.login.CustomTabLoginMethodHandler$1 */
    static class C04521 implements Creator {
        C04521() {
        }

        public CustomTabLoginMethodHandler createFromParcel(Parcel source) {
            return new CustomTabLoginMethodHandler(source);
        }

        public CustomTabLoginMethodHandler[] newArray(int size) {
            return new CustomTabLoginMethodHandler[size];
        }
    }

    CustomTabLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    String getNameForLogging() {
        return "custom_tab";
    }

    AccessTokenSource getTokenSource() {
        return AccessTokenSource.CHROME_CUSTOM_TAB;
    }

    protected String getSSODevice() {
        return "chrome_custom_tab";
    }

    boolean tryAuthorize(Request request) {
        if (!isCustomTabsAllowed()) {
            return false;
        }
        Bundle parameters = addExtraParameters(getParameters(request), request);
        Activity activity = this.loginClient.getActivity();
        this.customTab = new CustomTab(OAUTH_DIALOG, parameters);
        this.customTab.openCustomTab(activity, getChromePackage());
        return true;
    }

    protected void putChallengeParam(JSONObject param) throws JSONException {
        if (this.loginClient.getFragment() instanceof LoginFragment) {
            param.put("7_challenge", ((LoginFragment) this.loginClient.getFragment()).getChallengeParam());
        }
    }

    private boolean isCustomTabsAllowed() {
        return isCustomTabsEnabled() && getChromePackage() != null && Validate.hasCustomTabRedirectActivity(FacebookSdk.getApplicationContext());
    }

    private boolean isCustomTabsEnabled() {
        FetchedAppSettings settings = Utility.getAppSettingsWithoutQuery(Utility.getMetadataApplicationId(this.loginClient.getActivity()));
        return settings != null && settings.getCustomTabsEnabled();
    }

    private String getChromePackage() {
        if (this.currentPackage != null) {
            return this.currentPackage;
        }
        Context context = this.loginClient.getActivity();
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentServices(new Intent("android.support.customtabs.action.CustomTabsService"), 0);
        if (resolveInfos != null) {
            Set<String> chromePackages = new HashSet(Arrays.asList(CHROME_PACKAGES));
            for (ResolveInfo resolveInfo : resolveInfos) {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                if (serviceInfo != null && chromePackages.contains(serviceInfo.packageName)) {
                    this.currentPackage = serviceInfo.packageName;
                    return this.currentPackage;
                }
            }
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    CustomTabLoginMethodHandler(Parcel source) {
        super(source);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
