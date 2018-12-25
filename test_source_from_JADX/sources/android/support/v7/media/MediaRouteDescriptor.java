package android.support.v7.media;

import android.content.IntentFilter;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class MediaRouteDescriptor {
    static final String KEY_CAN_DISCONNECT = "canDisconnect";
    static final String KEY_CONNECTING = "connecting";
    static final String KEY_CONNECTION_STATE = "connectionState";
    static final String KEY_CONTROL_FILTERS = "controlFilters";
    static final String KEY_DESCRIPTION = "status";
    static final String KEY_DEVICE_TYPE = "deviceType";
    static final String KEY_ENABLED = "enabled";
    static final String KEY_EXTRAS = "extras";
    static final String KEY_GROUP_MEMBER_IDS = "groupMemberIds";
    static final String KEY_ICON_URI = "iconUri";
    static final String KEY_ID = "id";
    static final String KEY_MAX_CLIENT_VERSION = "maxClientVersion";
    static final String KEY_MIN_CLIENT_VERSION = "minClientVersion";
    static final String KEY_NAME = "name";
    static final String KEY_PLAYBACK_STREAM = "playbackStream";
    static final String KEY_PLAYBACK_TYPE = "playbackType";
    static final String KEY_PRESENTATION_DISPLAY_ID = "presentationDisplayId";
    static final String KEY_SETTINGS_INTENT = "settingsIntent";
    static final String KEY_VOLUME = "volume";
    static final String KEY_VOLUME_HANDLING = "volumeHandling";
    static final String KEY_VOLUME_MAX = "volumeMax";
    final Bundle mBundle;
    List<IntentFilter> mControlFilters;

    public static final class Builder {
        private final Bundle mBundle;
        private ArrayList<IntentFilter> mControlFilters;
        private ArrayList<String> mGroupMemberIds;

        public Builder(String id, String name) {
            this.mBundle = new Bundle();
            setId(id);
            setName(name);
        }

        public Builder(MediaRouteDescriptor descriptor) {
            if (descriptor == null) {
                throw new IllegalArgumentException("descriptor must not be null");
            }
            this.mBundle = new Bundle(descriptor.mBundle);
            descriptor.ensureControlFilters();
            if (!descriptor.mControlFilters.isEmpty()) {
                this.mControlFilters = new ArrayList(descriptor.mControlFilters);
            }
        }

        public Builder setId(String id) {
            this.mBundle.putString("id", id);
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Builder addGroupMemberId(String groupMemberId) {
            if (TextUtils.isEmpty(groupMemberId)) {
                throw new IllegalArgumentException("groupMemberId must not be empty");
            }
            if (this.mGroupMemberIds == null) {
                this.mGroupMemberIds = new ArrayList();
            }
            if (!this.mGroupMemberIds.contains(groupMemberId)) {
                this.mGroupMemberIds.add(groupMemberId);
            }
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Builder addGroupMemberIds(Collection<String> groupMemberIds) {
            if (groupMemberIds == null) {
                throw new IllegalArgumentException("groupMemberIds must not be null");
            }
            if (!groupMemberIds.isEmpty()) {
                for (String groupMemberId : groupMemberIds) {
                    addGroupMemberId(groupMemberId);
                }
            }
            return this;
        }

        public Builder setName(String name) {
            this.mBundle.putString("name", name);
            return this;
        }

        public Builder setDescription(String description) {
            this.mBundle.putString("status", description);
            return this;
        }

        public Builder setIconUri(Uri iconUri) {
            if (iconUri == null) {
                throw new IllegalArgumentException("iconUri must not be null");
            }
            this.mBundle.putString(MediaRouteDescriptor.KEY_ICON_URI, iconUri.toString());
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            this.mBundle.putBoolean(MediaRouteDescriptor.KEY_ENABLED, enabled);
            return this;
        }

        @Deprecated
        public Builder setConnecting(boolean connecting) {
            this.mBundle.putBoolean(MediaRouteDescriptor.KEY_CONNECTING, connecting);
            return this;
        }

        public Builder setConnectionState(int connectionState) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_CONNECTION_STATE, connectionState);
            return this;
        }

        public Builder setCanDisconnect(boolean canDisconnect) {
            this.mBundle.putBoolean(MediaRouteDescriptor.KEY_CAN_DISCONNECT, canDisconnect);
            return this;
        }

        public Builder setSettingsActivity(IntentSender is) {
            this.mBundle.putParcelable(MediaRouteDescriptor.KEY_SETTINGS_INTENT, is);
            return this;
        }

        public Builder addControlFilter(IntentFilter filter) {
            if (filter == null) {
                throw new IllegalArgumentException("filter must not be null");
            }
            if (this.mControlFilters == null) {
                this.mControlFilters = new ArrayList();
            }
            if (!this.mControlFilters.contains(filter)) {
                this.mControlFilters.add(filter);
            }
            return this;
        }

        public Builder addControlFilters(Collection<IntentFilter> filters) {
            if (filters == null) {
                throw new IllegalArgumentException("filters must not be null");
            }
            if (!filters.isEmpty()) {
                for (IntentFilter filter : filters) {
                    addControlFilter(filter);
                }
            }
            return this;
        }

        public Builder setPlaybackType(int playbackType) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_PLAYBACK_TYPE, playbackType);
            return this;
        }

        public Builder setPlaybackStream(int playbackStream) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_PLAYBACK_STREAM, playbackStream);
            return this;
        }

        public Builder setDeviceType(int deviceType) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_DEVICE_TYPE, deviceType);
            return this;
        }

        public Builder setVolume(int volume) {
            this.mBundle.putInt("volume", volume);
            return this;
        }

        public Builder setVolumeMax(int volumeMax) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_VOLUME_MAX, volumeMax);
            return this;
        }

        public Builder setVolumeHandling(int volumeHandling) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_VOLUME_HANDLING, volumeHandling);
            return this;
        }

        public Builder setPresentationDisplayId(int presentationDisplayId) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_PRESENTATION_DISPLAY_ID, presentationDisplayId);
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mBundle.putBundle("extras", extras);
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Builder setMinClientVersion(int minVersion) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_MIN_CLIENT_VERSION, minVersion);
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Builder setMaxClientVersion(int maxVersion) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_MAX_CLIENT_VERSION, maxVersion);
            return this;
        }

        public MediaRouteDescriptor build() {
            if (this.mControlFilters != null) {
                this.mBundle.putParcelableArrayList(MediaRouteDescriptor.KEY_CONTROL_FILTERS, this.mControlFilters);
            }
            if (this.mGroupMemberIds != null) {
                this.mBundle.putStringArrayList(MediaRouteDescriptor.KEY_GROUP_MEMBER_IDS, this.mGroupMemberIds);
            }
            return new MediaRouteDescriptor(this.mBundle, this.mControlFilters);
        }
    }

    MediaRouteDescriptor(Bundle bundle, List<IntentFilter> controlFilters) {
        this.mBundle = bundle;
        this.mControlFilters = controlFilters;
    }

    public String getId() {
        return this.mBundle.getString("id");
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public List<String> getGroupMemberIds() {
        return this.mBundle.getStringArrayList(KEY_GROUP_MEMBER_IDS);
    }

    public String getName() {
        return this.mBundle.getString("name");
    }

    public String getDescription() {
        return this.mBundle.getString("status");
    }

    public Uri getIconUri() {
        String iconUri = this.mBundle.getString(KEY_ICON_URI);
        return iconUri == null ? null : Uri.parse(iconUri);
    }

    public boolean isEnabled() {
        return this.mBundle.getBoolean(KEY_ENABLED, true);
    }

    @Deprecated
    public boolean isConnecting() {
        return this.mBundle.getBoolean(KEY_CONNECTING, false);
    }

    public int getConnectionState() {
        return this.mBundle.getInt(KEY_CONNECTION_STATE, 0);
    }

    public boolean canDisconnectAndKeepPlaying() {
        return this.mBundle.getBoolean(KEY_CAN_DISCONNECT, false);
    }

    public IntentSender getSettingsActivity() {
        return (IntentSender) this.mBundle.getParcelable(KEY_SETTINGS_INTENT);
    }

    public List<IntentFilter> getControlFilters() {
        ensureControlFilters();
        return this.mControlFilters;
    }

    void ensureControlFilters() {
        if (this.mControlFilters == null) {
            this.mControlFilters = this.mBundle.getParcelableArrayList(KEY_CONTROL_FILTERS);
            if (this.mControlFilters == null) {
                this.mControlFilters = Collections.emptyList();
            }
        }
    }

    public int getPlaybackType() {
        return this.mBundle.getInt(KEY_PLAYBACK_TYPE, 1);
    }

    public int getPlaybackStream() {
        return this.mBundle.getInt(KEY_PLAYBACK_STREAM, -1);
    }

    public int getDeviceType() {
        return this.mBundle.getInt(KEY_DEVICE_TYPE);
    }

    public int getVolume() {
        return this.mBundle.getInt("volume");
    }

    public int getVolumeMax() {
        return this.mBundle.getInt(KEY_VOLUME_MAX);
    }

    public int getVolumeHandling() {
        return this.mBundle.getInt(KEY_VOLUME_HANDLING, 0);
    }

    public int getPresentationDisplayId() {
        return this.mBundle.getInt(KEY_PRESENTATION_DISPLAY_ID, -1);
    }

    public Bundle getExtras() {
        return this.mBundle.getBundle("extras");
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int getMinClientVersion() {
        return this.mBundle.getInt(KEY_MIN_CLIENT_VERSION, 1);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int getMaxClientVersion() {
        return this.mBundle.getInt(KEY_MAX_CLIENT_VERSION, Integer.MAX_VALUE);
    }

    public boolean isValid() {
        ensureControlFilters();
        if (!(TextUtils.isEmpty(getId()) || TextUtils.isEmpty(getName()))) {
            if (!this.mControlFilters.contains(null)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("MediaRouteDescriptor{ ");
        result.append("id=");
        result.append(getId());
        result.append(", groupMemberIds=");
        result.append(getGroupMemberIds());
        result.append(", name=");
        result.append(getName());
        result.append(", description=");
        result.append(getDescription());
        result.append(", iconUri=");
        result.append(getIconUri());
        result.append(", isEnabled=");
        result.append(isEnabled());
        result.append(", isConnecting=");
        result.append(isConnecting());
        result.append(", connectionState=");
        result.append(getConnectionState());
        result.append(", controlFilters=");
        result.append(Arrays.toString(getControlFilters().toArray()));
        result.append(", playbackType=");
        result.append(getPlaybackType());
        result.append(", playbackStream=");
        result.append(getPlaybackStream());
        result.append(", deviceType=");
        result.append(getDeviceType());
        result.append(", volume=");
        result.append(getVolume());
        result.append(", volumeMax=");
        result.append(getVolumeMax());
        result.append(", volumeHandling=");
        result.append(getVolumeHandling());
        result.append(", presentationDisplayId=");
        result.append(getPresentationDisplayId());
        result.append(", extras=");
        result.append(getExtras());
        result.append(", isValid=");
        result.append(isValid());
        result.append(", minClientVersion=");
        result.append(getMinClientVersion());
        result.append(", maxClientVersion=");
        result.append(getMaxClientVersion());
        result.append(" }");
        return result.toString();
    }

    public Bundle asBundle() {
        return this.mBundle;
    }

    public static MediaRouteDescriptor fromBundle(Bundle bundle) {
        return bundle != null ? new MediaRouteDescriptor(bundle, null) : null;
    }
}
