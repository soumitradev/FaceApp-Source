package com.facebook;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.GraphMeRequestWithCacheCallback;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

public final class Profile implements Parcelable {
    public static final Creator<Profile> CREATOR = new C04072();
    private static final String FIRST_NAME_KEY = "first_name";
    private static final String ID_KEY = "id";
    private static final String LAST_NAME_KEY = "last_name";
    private static final String LINK_URI_KEY = "link_uri";
    private static final String MIDDLE_NAME_KEY = "middle_name";
    private static final String NAME_KEY = "name";
    private final String firstName;
    private final String id;
    private final String lastName;
    private final Uri linkUri;
    private final String middleName;
    private final String name;

    /* renamed from: com.facebook.Profile$2 */
    static class C04072 implements Creator {
        C04072() {
        }

        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    }

    /* renamed from: com.facebook.Profile$1 */
    static class C08211 implements GraphMeRequestWithCacheCallback {
        C08211() {
        }

        public void onSuccess(JSONObject userInfo) {
            String id = userInfo.optString("id");
            if (id != null) {
                String link = userInfo.optString("link");
                Profile.setCurrentProfile(new Profile(id, userInfo.optString(Profile.FIRST_NAME_KEY), userInfo.optString(Profile.MIDDLE_NAME_KEY), userInfo.optString(Profile.LAST_NAME_KEY), userInfo.optString("name"), link != null ? Uri.parse(link) : null));
            }
        }

        public void onFailure(FacebookException error) {
        }
    }

    public static Profile getCurrentProfile() {
        return ProfileManager.getInstance().getCurrentProfile();
    }

    public static void setCurrentProfile(Profile profile) {
        ProfileManager.getInstance().setCurrentProfile(profile);
    }

    public static void fetchProfileForCurrentAccessToken() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            setCurrentProfile(null);
        } else {
            Utility.getGraphMeRequestWithCacheAsync(accessToken.getToken(), new C08211());
        }
    }

    public Profile(String id, @Nullable String firstName, @Nullable String middleName, @Nullable String lastName, @Nullable String name, @Nullable Uri linkUri) {
        Validate.notNullOrEmpty(id, "id");
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.name = name;
        this.linkUri = linkUri;
    }

    public Uri getProfilePictureUri(int width, int height) {
        return ImageRequest.getProfilePictureUri(this.id, width, height);
    }

    public String getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getName() {
        return this.name;
    }

    public Uri getLinkUri() {
        return this.linkUri;
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
        r1 = r6 instanceof com.facebook.Profile;
        r2 = 0;
        if (r1 != 0) goto L_0x000a;
    L_0x0009:
        return r2;
    L_0x000a:
        r1 = r6;
        r1 = (com.facebook.Profile) r1;
        r3 = r5.id;
        r4 = r1.id;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0022;
    L_0x0017:
        r3 = r5.firstName;
        if (r3 != 0) goto L_0x0022;
    L_0x001b:
        r3 = r1.firstName;
        if (r3 != 0) goto L_0x0020;
    L_0x001f:
        goto L_0x0076;
    L_0x0020:
        r0 = 0;
        goto L_0x0076;
    L_0x0022:
        r3 = r5.firstName;
        r4 = r1.firstName;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0035;
    L_0x002c:
        r3 = r5.middleName;
        if (r3 != 0) goto L_0x0035;
    L_0x0030:
        r3 = r1.middleName;
        if (r3 != 0) goto L_0x0020;
    L_0x0034:
        goto L_0x001f;
    L_0x0035:
        r3 = r5.middleName;
        r4 = r1.middleName;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0048;
    L_0x003f:
        r3 = r5.lastName;
        if (r3 != 0) goto L_0x0048;
    L_0x0043:
        r3 = r1.lastName;
        if (r3 != 0) goto L_0x0020;
    L_0x0047:
        goto L_0x001f;
    L_0x0048:
        r3 = r5.lastName;
        r4 = r1.lastName;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x005b;
    L_0x0052:
        r3 = r5.name;
        if (r3 != 0) goto L_0x005b;
    L_0x0056:
        r3 = r1.name;
        if (r3 != 0) goto L_0x0020;
    L_0x005a:
        goto L_0x001f;
    L_0x005b:
        r3 = r5.name;
        r4 = r1.name;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x006e;
    L_0x0065:
        r3 = r5.linkUri;
        if (r3 != 0) goto L_0x006e;
    L_0x0069:
        r3 = r1.linkUri;
        if (r3 != 0) goto L_0x0020;
    L_0x006d:
        goto L_0x001f;
    L_0x006e:
        r0 = r5.linkUri;
        r2 = r1.linkUri;
        r0 = r0.equals(r2);
    L_0x0076:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Profile.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int result = (17 * 31) + this.id.hashCode();
        if (this.firstName != null) {
            result = (result * 31) + this.firstName.hashCode();
        }
        if (this.middleName != null) {
            result = (result * 31) + this.middleName.hashCode();
        }
        if (this.lastName != null) {
            result = (result * 31) + this.lastName.hashCode();
        }
        if (this.name != null) {
            result = (result * 31) + this.name.hashCode();
        }
        if (this.linkUri != null) {
            return (result * 31) + this.linkUri.hashCode();
        }
        return result;
    }

    JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put(FIRST_NAME_KEY, this.firstName);
            jsonObject.put(MIDDLE_NAME_KEY, this.middleName);
            jsonObject.put(LAST_NAME_KEY, this.lastName);
            jsonObject.put("name", this.name);
            if (this.linkUri != null) {
                jsonObject.put(LINK_URI_KEY, this.linkUri.toString());
            }
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }

    Profile(JSONObject jsonObject) {
        Uri uri = null;
        this.id = jsonObject.optString("id", null);
        this.firstName = jsonObject.optString(FIRST_NAME_KEY, null);
        this.middleName = jsonObject.optString(MIDDLE_NAME_KEY, null);
        this.lastName = jsonObject.optString(LAST_NAME_KEY, null);
        this.name = jsonObject.optString("name", null);
        String linkUriString = jsonObject.optString(LINK_URI_KEY, null);
        if (linkUriString != null) {
            uri = Uri.parse(linkUriString);
        }
        this.linkUri = uri;
    }

    private Profile(Parcel source) {
        this.id = source.readString();
        this.firstName = source.readString();
        this.middleName = source.readString();
        this.lastName = source.readString();
        this.name = source.readString();
        String linkUriString = source.readString();
        this.linkUri = linkUriString == null ? null : Uri.parse(linkUriString);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.middleName);
        dest.writeString(this.lastName);
        dest.writeString(this.name);
        dest.writeString(this.linkUri == null ? null : this.linkUri.toString());
    }
}
