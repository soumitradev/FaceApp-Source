package com.facebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.GraphMeRequestWithCacheCallback;
import com.facebook.internal.Validate;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AccessToken implements Parcelable {
    public static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String APPLICATION_ID_KEY = "application_id";
    public static final Creator<AccessToken> CREATOR = new C03962();
    private static final int CURRENT_JSON_FORMAT = 1;
    private static final String DECLINED_PERMISSIONS_KEY = "declined_permissions";
    private static final AccessTokenSource DEFAULT_ACCESS_TOKEN_SOURCE = AccessTokenSource.FACEBOOK_APPLICATION_WEB;
    private static final Date DEFAULT_EXPIRATION_TIME = MAX_DATE;
    private static final Date DEFAULT_LAST_REFRESH_TIME = new Date();
    private static final String EXPIRES_AT_KEY = "expires_at";
    public static final String EXPIRES_IN_KEY = "expires_in";
    private static final String LAST_REFRESH_KEY = "last_refresh";
    private static final Date MAX_DATE = new Date(Long.MAX_VALUE);
    private static final String PERMISSIONS_KEY = "permissions";
    private static final String SOURCE_KEY = "source";
    private static final String TOKEN_KEY = "token";
    public static final String USER_ID_KEY = "user_id";
    private static final String VERSION_KEY = "version";
    private final String applicationId;
    private final Set<String> declinedPermissions;
    private final Date expires;
    private final Date lastRefresh;
    private final Set<String> permissions;
    private final AccessTokenSource source;
    private final String token;
    private final String userId;

    /* renamed from: com.facebook.AccessToken$2 */
    static class C03962 implements Creator {
        C03962() {
        }

        public AccessToken createFromParcel(Parcel source) {
            return new AccessToken(source);
        }

        public AccessToken[] newArray(int size) {
            return new AccessToken[size];
        }
    }

    public interface AccessTokenCreationCallback {
        void onError(FacebookException facebookException);

        void onSuccess(AccessToken accessToken);
    }

    public interface AccessTokenRefreshCallback {
        void OnTokenRefreshFailed(FacebookException facebookException);

        void OnTokenRefreshed(AccessToken accessToken);
    }

    public AccessToken(String accessToken, String applicationId, String userId, @Nullable Collection<String> permissions, @Nullable Collection<String> declinedPermissions, @Nullable AccessTokenSource accessTokenSource, @Nullable Date expirationTime, @Nullable Date lastRefreshTime) {
        Validate.notNullOrEmpty(accessToken, "accessToken");
        Validate.notNullOrEmpty(applicationId, "applicationId");
        Validate.notNullOrEmpty(userId, "userId");
        this.expires = expirationTime != null ? expirationTime : DEFAULT_EXPIRATION_TIME;
        this.permissions = Collections.unmodifiableSet(permissions != null ? new HashSet(permissions) : new HashSet());
        this.declinedPermissions = Collections.unmodifiableSet(declinedPermissions != null ? new HashSet(declinedPermissions) : new HashSet());
        this.token = accessToken;
        this.source = accessTokenSource != null ? accessTokenSource : DEFAULT_ACCESS_TOKEN_SOURCE;
        this.lastRefresh = lastRefreshTime != null ? lastRefreshTime : DEFAULT_LAST_REFRESH_TIME;
        this.applicationId = applicationId;
        this.userId = userId;
    }

    public static AccessToken getCurrentAccessToken() {
        return AccessTokenManager.getInstance().getCurrentAccessToken();
    }

    public static void setCurrentAccessToken(AccessToken accessToken) {
        AccessTokenManager.getInstance().setCurrentAccessToken(accessToken);
    }

    public static void refreshCurrentAccessTokenAsync() {
        AccessTokenManager.getInstance().refreshCurrentAccessToken(null);
    }

    public static void refreshCurrentAccessTokenAsync(AccessTokenRefreshCallback callback) {
        AccessTokenManager.getInstance().refreshCurrentAccessToken(callback);
    }

    public String getToken() {
        return this.token;
    }

    public Date getExpires() {
        return this.expires;
    }

    public Set<String> getPermissions() {
        return this.permissions;
    }

    public Set<String> getDeclinedPermissions() {
        return this.declinedPermissions;
    }

    public AccessTokenSource getSource() {
        return this.source;
    }

    public Date getLastRefresh() {
        return this.lastRefresh;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public String getUserId() {
        return this.userId;
    }

    public static void createFromNativeLinkingIntent(Intent intent, final String applicationId, final AccessTokenCreationCallback accessTokenCallback) {
        Validate.notNull(intent, "intent");
        if (intent.getExtras() == null) {
            accessTokenCallback.onError(new FacebookException("No extras found on intent"));
            return;
        }
        final Bundle extras = new Bundle(intent.getExtras());
        String accessToken = extras.getString("access_token");
        if (accessToken != null) {
            if (!accessToken.isEmpty()) {
                String userId = extras.getString(USER_ID_KEY);
                if (userId != null) {
                    if (!userId.isEmpty()) {
                        accessTokenCallback.onSuccess(createFromBundle(null, extras, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), applicationId));
                        return;
                    }
                }
                Utility.getGraphMeRequestWithCacheAsync(accessToken, new GraphMeRequestWithCacheCallback() {
                    public void onSuccess(JSONObject userInfo) {
                        try {
                            extras.putString(AccessToken.USER_ID_KEY, userInfo.getString(ShareConstants.WEB_DIALOG_PARAM_ID));
                            accessTokenCallback.onSuccess(AccessToken.createFromBundle(null, extras, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), applicationId));
                        } catch (JSONException e) {
                            accessTokenCallback.onError(new FacebookException("Unable to generate access token due to missing user id"));
                        }
                    }

                    public void onFailure(FacebookException error) {
                        accessTokenCallback.onError(error);
                    }
                });
                return;
            }
        }
        accessTokenCallback.onError(new FacebookException("No access token found on intent"));
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{AccessToken");
        builder.append(" token:");
        builder.append(tokenToString());
        appendPermissions(builder);
        builder.append("}");
        return builder.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r6) {
        /*
        r5 = this;
        r0 = 1;
        if (r5 != r6) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = r6 instanceof com.facebook.AccessToken;
        r2 = 0;
        if (r1 != 0) goto L_0x000a;
    L_0x0009:
        return r2;
    L_0x000a:
        r1 = r6;
        r1 = (com.facebook.AccessToken) r1;
        r3 = r5.expires;
        r4 = r1.expires;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0063;
    L_0x0017:
        r3 = r5.permissions;
        r4 = r1.permissions;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0063;
    L_0x0021:
        r3 = r5.declinedPermissions;
        r4 = r1.declinedPermissions;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0063;
    L_0x002b:
        r3 = r5.token;
        r4 = r1.token;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0063;
    L_0x0035:
        r3 = r5.source;
        r4 = r1.source;
        if (r3 != r4) goto L_0x0063;
    L_0x003b:
        r3 = r5.lastRefresh;
        r4 = r1.lastRefresh;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0063;
    L_0x0045:
        r3 = r5.applicationId;
        if (r3 != 0) goto L_0x004e;
    L_0x0049:
        r3 = r1.applicationId;
        if (r3 != 0) goto L_0x0063;
    L_0x004d:
        goto L_0x0058;
    L_0x004e:
        r3 = r5.applicationId;
        r4 = r1.applicationId;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0063;
    L_0x0058:
        r3 = r5.userId;
        r4 = r1.userId;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0063;
    L_0x0062:
        goto L_0x0064;
    L_0x0063:
        r0 = 0;
    L_0x0064:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.AccessToken.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return (((((((((((((((17 * 31) + this.expires.hashCode()) * 31) + this.permissions.hashCode()) * 31) + this.declinedPermissions.hashCode()) * 31) + this.token.hashCode()) * 31) + this.source.hashCode()) * 31) + this.lastRefresh.hashCode()) * 31) + (this.applicationId == null ? 0 : this.applicationId.hashCode())) * 31) + this.userId.hashCode();
    }

    @SuppressLint({"FieldGetter"})
    static AccessToken createFromRefresh(AccessToken current, Bundle bundle) {
        if (current.source == AccessTokenSource.FACEBOOK_APPLICATION_WEB || current.source == AccessTokenSource.FACEBOOK_APPLICATION_NATIVE || current.source == AccessTokenSource.FACEBOOK_APPLICATION_SERVICE) {
            Date expires = Utility.getBundleLongAsDate(bundle, EXPIRES_IN_KEY, new Date(0));
            String token = bundle.getString("access_token");
            if (Utility.isNullOrEmpty(token)) {
                return null;
            }
            return new AccessToken(token, current.applicationId, current.getUserId(), current.getPermissions(), current.getDeclinedPermissions(), current.source, expires, new Date());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid token source: ");
        stringBuilder.append(current.source);
        throw new FacebookException(stringBuilder.toString());
    }

    static AccessToken createFromLegacyCache(Bundle bundle) {
        List<String> permissions = getPermissionsFromBundle(bundle, LegacyTokenHelper.PERMISSIONS_KEY);
        List<String> declinedPermissions = getPermissionsFromBundle(bundle, LegacyTokenHelper.DECLINED_PERMISSIONS_KEY);
        String applicationId = LegacyTokenHelper.getApplicationId(bundle);
        if (Utility.isNullOrEmpty(applicationId)) {
            applicationId = FacebookSdk.getApplicationId();
        }
        String applicationId2 = applicationId;
        String tokenString = LegacyTokenHelper.getToken(bundle);
        try {
            return new AccessToken(tokenString, applicationId2, Utility.awaitGetGraphMeRequestWithCache(tokenString).getString(ShareConstants.WEB_DIALOG_PARAM_ID), permissions, declinedPermissions, LegacyTokenHelper.getSource(bundle), LegacyTokenHelper.getDate(bundle, LegacyTokenHelper.EXPIRATION_DATE_KEY), LegacyTokenHelper.getDate(bundle, LegacyTokenHelper.LAST_REFRESH_DATE_KEY));
        } catch (JSONException e) {
            return null;
        }
    }

    static List<String> getPermissionsFromBundle(Bundle bundle, String key) {
        List<String> originalPermissions = bundle.getStringArrayList(key);
        if (originalPermissions == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList(originalPermissions));
    }

    public boolean isExpired() {
        return new Date().after(this.expires);
    }

    JSONObject toJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", 1);
        jsonObject.put("token", this.token);
        jsonObject.put("expires_at", this.expires.getTime());
        jsonObject.put("permissions", new JSONArray(this.permissions));
        jsonObject.put(DECLINED_PERMISSIONS_KEY, new JSONArray(this.declinedPermissions));
        jsonObject.put(LAST_REFRESH_KEY, this.lastRefresh.getTime());
        jsonObject.put("source", this.source.name());
        jsonObject.put(APPLICATION_ID_KEY, this.applicationId);
        jsonObject.put(USER_ID_KEY, this.userId);
        return jsonObject;
    }

    static AccessToken createFromJSONObject(JSONObject jsonObject) throws JSONException {
        JSONObject jSONObject = jsonObject;
        if (jSONObject.getInt("version") > 1) {
            throw new FacebookException("Unknown AccessToken serialization format.");
        }
        String token = jSONObject.getString("token");
        Date expiresAt = new Date(jSONObject.getLong("expires_at"));
        JSONArray permissionsArray = jSONObject.getJSONArray("permissions");
        JSONArray declinedPermissionsArray = jSONObject.getJSONArray(DECLINED_PERMISSIONS_KEY);
        Date lastRefresh = new Date(jSONObject.getLong(LAST_REFRESH_KEY));
        AccessTokenSource source = AccessTokenSource.valueOf(jSONObject.getString("source"));
        String applicationId = jSONObject.getString(APPLICATION_ID_KEY);
        return new AccessToken(token, applicationId, jSONObject.getString(USER_ID_KEY), Utility.jsonArrayToStringList(permissionsArray), Utility.jsonArrayToStringList(declinedPermissionsArray), source, expiresAt, lastRefresh);
    }

    private static AccessToken createFromBundle(List<String> requestedPermissions, Bundle bundle, AccessTokenSource source, Date expirationBase, String applicationId) {
        Bundle bundle2 = bundle;
        String token = bundle2.getString("access_token");
        Date expires = Utility.getBundleLongAsDate(bundle2, EXPIRES_IN_KEY, expirationBase);
        String userId = bundle2.getString(USER_ID_KEY);
        if (!Utility.isNullOrEmpty(token)) {
            if (expires != null) {
                return new AccessToken(token, applicationId, userId, requestedPermissions, null, source, expires, new Date());
            }
        }
        return null;
    }

    private String tokenToString() {
        if (this.token == null) {
            return "null";
        }
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
            return this.token;
        }
        return "ACCESS_TOKEN_REMOVED";
    }

    private void appendPermissions(StringBuilder builder) {
        builder.append(" permissions:");
        if (this.permissions == null) {
            builder.append("null");
            return;
        }
        builder.append("[");
        builder.append(TextUtils.join(", ", this.permissions));
        builder.append("]");
    }

    AccessToken(Parcel parcel) {
        this.expires = new Date(parcel.readLong());
        ArrayList<String> permissionsList = new ArrayList();
        parcel.readStringList(permissionsList);
        this.permissions = Collections.unmodifiableSet(new HashSet(permissionsList));
        permissionsList.clear();
        parcel.readStringList(permissionsList);
        this.declinedPermissions = Collections.unmodifiableSet(new HashSet(permissionsList));
        this.token = parcel.readString();
        this.source = AccessTokenSource.valueOf(parcel.readString());
        this.lastRefresh = new Date(parcel.readLong());
        this.applicationId = parcel.readString();
        this.userId = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.expires.getTime());
        dest.writeStringList(new ArrayList(this.permissions));
        dest.writeStringList(new ArrayList(this.declinedPermissions));
        dest.writeString(this.token);
        dest.writeString(this.source.name());
        dest.writeLong(this.lastRefresh.getTime());
        dest.writeString(this.applicationId);
        dest.writeString(this.userId);
    }
}
