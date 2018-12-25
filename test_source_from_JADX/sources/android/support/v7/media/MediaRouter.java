package android.support.v7.media;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompat.OnActiveChangeListener;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.util.Pair;
import android.support.v7.media.MediaRouteProvider.ProviderMetadata;
import android.support.v7.media.MediaRouteProvider.RouteController;
import android.support.v7.media.MediaRouteSelector.Builder;
import android.support.v7.media.RemoteControlClientCompat.PlaybackInfo;
import android.support.v7.media.RemoteControlClientCompat.VolumeCallback;
import android.support.v7.media.SystemMediaRouteProvider.SyncCallback;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.catrobat.catroid.common.Constants;

public final class MediaRouter {
    public static final int AVAILABILITY_FLAG_IGNORE_DEFAULT_ROUTE = 1;
    public static final int AVAILABILITY_FLAG_REQUIRE_MATCH = 2;
    public static final int CALLBACK_FLAG_FORCE_DISCOVERY = 8;
    public static final int CALLBACK_FLAG_PERFORM_ACTIVE_SCAN = 1;
    public static final int CALLBACK_FLAG_REQUEST_DISCOVERY = 4;
    public static final int CALLBACK_FLAG_UNFILTERED_EVENTS = 2;
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String TAG = "MediaRouter";
    public static final int UNSELECT_REASON_DISCONNECTED = 1;
    public static final int UNSELECT_REASON_ROUTE_CHANGED = 3;
    public static final int UNSELECT_REASON_STOPPED = 2;
    public static final int UNSELECT_REASON_UNKNOWN = 0;
    static GlobalMediaRouter sGlobal;
    final ArrayList<CallbackRecord> mCallbackRecords;
    final Context mContext;

    public static abstract class Callback {
        public void onRouteSelected(MediaRouter router, RouteInfo route) {
        }

        public void onRouteUnselected(MediaRouter router, RouteInfo route) {
        }

        public void onRouteUnselected(MediaRouter router, RouteInfo route, int reason) {
            onRouteUnselected(router, route);
        }

        public void onRouteAdded(MediaRouter router, RouteInfo route) {
        }

        public void onRouteRemoved(MediaRouter router, RouteInfo route) {
        }

        public void onRouteChanged(MediaRouter router, RouteInfo route) {
        }

        public void onRouteVolumeChanged(MediaRouter router, RouteInfo route) {
        }

        public void onRoutePresentationDisplayChanged(MediaRouter router, RouteInfo route) {
        }

        public void onProviderAdded(MediaRouter router, ProviderInfo provider) {
        }

        public void onProviderRemoved(MediaRouter router, ProviderInfo provider) {
        }

        public void onProviderChanged(MediaRouter router, ProviderInfo provider) {
        }
    }

    private static final class CallbackRecord {
        public final Callback mCallback;
        public int mFlags;
        public final MediaRouter mRouter;
        public MediaRouteSelector mSelector = MediaRouteSelector.EMPTY;

        public CallbackRecord(MediaRouter router, Callback callback) {
            this.mRouter = router;
            this.mCallback = callback;
        }

        public boolean filterRouteEvent(RouteInfo route) {
            if ((this.mFlags & 2) == 0) {
                if (!route.matchesSelector(this.mSelector)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static abstract class ControlRequestCallback {
        public void onResult(Bundle data) {
        }

        public void onError(String error, Bundle data) {
        }
    }

    public static final class ProviderInfo {
        private MediaRouteProviderDescriptor mDescriptor;
        private final ProviderMetadata mMetadata;
        private final MediaRouteProvider mProviderInstance;
        private Resources mResources;
        private boolean mResourcesNotAvailable;
        private final List<RouteInfo> mRoutes = new ArrayList();

        ProviderInfo(MediaRouteProvider provider) {
            this.mProviderInstance = provider;
            this.mMetadata = provider.getMetadata();
        }

        public MediaRouteProvider getProviderInstance() {
            MediaRouter.checkCallingThread();
            return this.mProviderInstance;
        }

        public String getPackageName() {
            return this.mMetadata.getPackageName();
        }

        public ComponentName getComponentName() {
            return this.mMetadata.getComponentName();
        }

        public List<RouteInfo> getRoutes() {
            MediaRouter.checkCallingThread();
            return this.mRoutes;
        }

        Resources getResources() {
            if (this.mResources == null && !this.mResourcesNotAvailable) {
                String packageName = getPackageName();
                Context context = MediaRouter.sGlobal.getProviderContext(packageName);
                if (context != null) {
                    this.mResources = context.getResources();
                } else {
                    String str = MediaRouter.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to obtain resources for route provider package: ");
                    stringBuilder.append(packageName);
                    Log.w(str, stringBuilder.toString());
                    this.mResourcesNotAvailable = true;
                }
            }
            return this.mResources;
        }

        boolean updateDescriptor(MediaRouteProviderDescriptor descriptor) {
            if (this.mDescriptor == descriptor) {
                return false;
            }
            this.mDescriptor = descriptor;
            return true;
        }

        int findRouteByDescriptorId(String id) {
            int count = this.mRoutes.size();
            for (int i = 0; i < count; i++) {
                if (((RouteInfo) this.mRoutes.get(i)).mDescriptorId.equals(id)) {
                    return i;
                }
            }
            return -1;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MediaRouter.RouteProviderInfo{ packageName=");
            stringBuilder.append(getPackageName());
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class RouteInfo {
        static final int CHANGE_GENERAL = 1;
        static final int CHANGE_PRESENTATION_DISPLAY = 4;
        static final int CHANGE_VOLUME = 2;
        public static final int CONNECTION_STATE_CONNECTED = 2;
        public static final int CONNECTION_STATE_CONNECTING = 1;
        public static final int CONNECTION_STATE_DISCONNECTED = 0;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public static final int DEVICE_TYPE_BLUETOOTH = 3;
        public static final int DEVICE_TYPE_SPEAKER = 2;
        public static final int DEVICE_TYPE_TV = 1;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public static final int DEVICE_TYPE_UNKNOWN = 0;
        public static final int PLAYBACK_TYPE_LOCAL = 0;
        public static final int PLAYBACK_TYPE_REMOTE = 1;
        public static final int PLAYBACK_VOLUME_FIXED = 0;
        public static final int PLAYBACK_VOLUME_VARIABLE = 1;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public static final int PRESENTATION_DISPLAY_ID_NONE = -1;
        static final String SYSTEM_MEDIA_ROUTE_PROVIDER_PACKAGE_NAME = "android";
        private boolean mCanDisconnect;
        private boolean mConnecting;
        private int mConnectionState;
        private final ArrayList<IntentFilter> mControlFilters = new ArrayList();
        private String mDescription;
        MediaRouteDescriptor mDescriptor;
        private final String mDescriptorId;
        private int mDeviceType;
        private boolean mEnabled;
        private Bundle mExtras;
        private Uri mIconUri;
        private String mName;
        private int mPlaybackStream;
        private int mPlaybackType;
        private Display mPresentationDisplay;
        private int mPresentationDisplayId = -1;
        private final ProviderInfo mProvider;
        private IntentSender mSettingsIntent;
        private final String mUniqueId;
        private int mVolume;
        private int mVolumeHandling;
        private int mVolumeMax;

        RouteInfo(ProviderInfo provider, String descriptorId, String uniqueId) {
            this.mProvider = provider;
            this.mDescriptorId = descriptorId;
            this.mUniqueId = uniqueId;
        }

        public ProviderInfo getProvider() {
            return this.mProvider;
        }

        @NonNull
        public String getId() {
            return this.mUniqueId;
        }

        public String getName() {
            return this.mName;
        }

        @Nullable
        public String getDescription() {
            return this.mDescription;
        }

        public Uri getIconUri() {
            return this.mIconUri;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public boolean isConnecting() {
            return this.mConnecting;
        }

        public int getConnectionState() {
            return this.mConnectionState;
        }

        public boolean isSelected() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getSelectedRoute() == this;
        }

        public boolean isDefault() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getDefaultRoute() == this;
        }

        public boolean isBluetooth() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getBluetoothRoute() == this;
        }

        public boolean isDeviceSpeaker() {
            return isDefault() && Resources.getSystem().getText(Resources.getSystem().getIdentifier("default_audio_route_name", "string", "android")).equals(this.mName);
        }

        public List<IntentFilter> getControlFilters() {
            return this.mControlFilters;
        }

        public boolean matchesSelector(@NonNull MediaRouteSelector selector) {
            if (selector == null) {
                throw new IllegalArgumentException("selector must not be null");
            }
            MediaRouter.checkCallingThread();
            return selector.matchesControlFilters(this.mControlFilters);
        }

        public boolean supportsControlCategory(@NonNull String category) {
            if (category == null) {
                throw new IllegalArgumentException("category must not be null");
            }
            MediaRouter.checkCallingThread();
            int count = this.mControlFilters.size();
            for (int i = 0; i < count; i++) {
                if (((IntentFilter) this.mControlFilters.get(i)).hasCategory(category)) {
                    return true;
                }
            }
            return false;
        }

        public boolean supportsControlAction(@NonNull String category, @NonNull String action) {
            if (category == null) {
                throw new IllegalArgumentException("category must not be null");
            } else if (action == null) {
                throw new IllegalArgumentException("action must not be null");
            } else {
                MediaRouter.checkCallingThread();
                int count = this.mControlFilters.size();
                for (int i = 0; i < count; i++) {
                    IntentFilter filter = (IntentFilter) this.mControlFilters.get(i);
                    if (filter.hasCategory(category) && filter.hasAction(action)) {
                        return true;
                    }
                }
                return false;
            }
        }

        public boolean supportsControlRequest(@NonNull Intent intent) {
            if (intent == null) {
                throw new IllegalArgumentException("intent must not be null");
            }
            MediaRouter.checkCallingThread();
            ContentResolver contentResolver = MediaRouter.sGlobal.getContentResolver();
            int count = this.mControlFilters.size();
            for (int i = 0; i < count; i++) {
                if (((IntentFilter) this.mControlFilters.get(i)).match(contentResolver, intent, true, MediaRouter.TAG) >= 0) {
                    return true;
                }
            }
            return false;
        }

        public void sendControlRequest(@NonNull Intent intent, @Nullable ControlRequestCallback callback) {
            if (intent == null) {
                throw new IllegalArgumentException("intent must not be null");
            }
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.sendControlRequest(this, intent, callback);
        }

        public int getPlaybackType() {
            return this.mPlaybackType;
        }

        public int getPlaybackStream() {
            return this.mPlaybackStream;
        }

        public int getDeviceType() {
            return this.mDeviceType;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public boolean isDefaultOrBluetooth() {
            boolean z = true;
            if (!isDefault()) {
                if (this.mDeviceType != 3) {
                    if (!isSystemMediaRouteProvider(this) || !supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO) || supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO)) {
                        z = false;
                    }
                    return z;
                }
            }
            return true;
        }

        boolean isSelectable() {
            return this.mDescriptor != null && this.mEnabled;
        }

        private static boolean isSystemMediaRouteProvider(RouteInfo route) {
            return TextUtils.equals(route.getProviderInstance().getMetadata().getPackageName(), "android");
        }

        public int getVolumeHandling() {
            return this.mVolumeHandling;
        }

        public int getVolume() {
            return this.mVolume;
        }

        public int getVolumeMax() {
            return this.mVolumeMax;
        }

        public boolean canDisconnect() {
            return this.mCanDisconnect;
        }

        public void requestSetVolume(int volume) {
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.requestSetVolume(this, Math.min(this.mVolumeMax, Math.max(0, volume)));
        }

        public void requestUpdateVolume(int delta) {
            MediaRouter.checkCallingThread();
            if (delta != 0) {
                MediaRouter.sGlobal.requestUpdateVolume(this, delta);
            }
        }

        @Nullable
        public Display getPresentationDisplay() {
            MediaRouter.checkCallingThread();
            if (this.mPresentationDisplayId >= 0 && this.mPresentationDisplay == null) {
                this.mPresentationDisplay = MediaRouter.sGlobal.getDisplay(this.mPresentationDisplayId);
            }
            return this.mPresentationDisplay;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getPresentationDisplayId() {
            return this.mPresentationDisplayId;
        }

        @Nullable
        public Bundle getExtras() {
            return this.mExtras;
        }

        @Nullable
        public IntentSender getSettingsIntent() {
            return this.mSettingsIntent;
        }

        public void select() {
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.selectRoute(this);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MediaRouter.RouteInfo{ uniqueId=");
            stringBuilder.append(this.mUniqueId);
            stringBuilder.append(", name=");
            stringBuilder.append(this.mName);
            stringBuilder.append(", description=");
            stringBuilder.append(this.mDescription);
            stringBuilder.append(", iconUri=");
            stringBuilder.append(this.mIconUri);
            stringBuilder.append(", enabled=");
            stringBuilder.append(this.mEnabled);
            stringBuilder.append(", connecting=");
            stringBuilder.append(this.mConnecting);
            stringBuilder.append(", connectionState=");
            stringBuilder.append(this.mConnectionState);
            stringBuilder.append(", canDisconnect=");
            stringBuilder.append(this.mCanDisconnect);
            stringBuilder.append(", playbackType=");
            stringBuilder.append(this.mPlaybackType);
            stringBuilder.append(", playbackStream=");
            stringBuilder.append(this.mPlaybackStream);
            stringBuilder.append(", deviceType=");
            stringBuilder.append(this.mDeviceType);
            stringBuilder.append(", volumeHandling=");
            stringBuilder.append(this.mVolumeHandling);
            stringBuilder.append(", volume=");
            stringBuilder.append(this.mVolume);
            stringBuilder.append(", volumeMax=");
            stringBuilder.append(this.mVolumeMax);
            stringBuilder.append(", presentationDisplayId=");
            stringBuilder.append(this.mPresentationDisplayId);
            stringBuilder.append(", extras=");
            stringBuilder.append(this.mExtras);
            stringBuilder.append(", settingsIntent=");
            stringBuilder.append(this.mSettingsIntent);
            stringBuilder.append(", providerPackageName=");
            stringBuilder.append(this.mProvider.getPackageName());
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        int maybeUpdateDescriptor(MediaRouteDescriptor descriptor) {
            if (this.mDescriptor != descriptor) {
                return updateDescriptor(descriptor);
            }
            return 0;
        }

        int updateDescriptor(MediaRouteDescriptor descriptor) {
            int changes = 0;
            this.mDescriptor = descriptor;
            if (descriptor == null) {
                return 0;
            }
            if (!MediaRouter.equal(this.mName, descriptor.getName())) {
                this.mName = descriptor.getName();
                changes = 0 | 1;
            }
            if (!MediaRouter.equal(this.mDescription, descriptor.getDescription())) {
                this.mDescription = descriptor.getDescription();
                changes |= 1;
            }
            if (!MediaRouter.equal(this.mIconUri, descriptor.getIconUri())) {
                this.mIconUri = descriptor.getIconUri();
                changes |= 1;
            }
            if (this.mEnabled != descriptor.isEnabled()) {
                this.mEnabled = descriptor.isEnabled();
                changes |= 1;
            }
            if (this.mConnecting != descriptor.isConnecting()) {
                this.mConnecting = descriptor.isConnecting();
                changes |= 1;
            }
            if (this.mConnectionState != descriptor.getConnectionState()) {
                this.mConnectionState = descriptor.getConnectionState();
                changes |= 1;
            }
            if (!this.mControlFilters.equals(descriptor.getControlFilters())) {
                this.mControlFilters.clear();
                this.mControlFilters.addAll(descriptor.getControlFilters());
                changes |= 1;
            }
            if (this.mPlaybackType != descriptor.getPlaybackType()) {
                this.mPlaybackType = descriptor.getPlaybackType();
                changes |= 1;
            }
            if (this.mPlaybackStream != descriptor.getPlaybackStream()) {
                this.mPlaybackStream = descriptor.getPlaybackStream();
                changes |= 1;
            }
            if (this.mDeviceType != descriptor.getDeviceType()) {
                this.mDeviceType = descriptor.getDeviceType();
                changes |= 1;
            }
            if (this.mVolumeHandling != descriptor.getVolumeHandling()) {
                this.mVolumeHandling = descriptor.getVolumeHandling();
                changes |= 3;
            }
            if (this.mVolume != descriptor.getVolume()) {
                this.mVolume = descriptor.getVolume();
                changes |= 3;
            }
            if (this.mVolumeMax != descriptor.getVolumeMax()) {
                this.mVolumeMax = descriptor.getVolumeMax();
                changes |= 3;
            }
            if (this.mPresentationDisplayId != descriptor.getPresentationDisplayId()) {
                this.mPresentationDisplayId = descriptor.getPresentationDisplayId();
                this.mPresentationDisplay = null;
                changes |= 5;
            }
            if (!MediaRouter.equal(this.mExtras, descriptor.getExtras())) {
                this.mExtras = descriptor.getExtras();
                changes |= 1;
            }
            if (!MediaRouter.equal(this.mSettingsIntent, descriptor.getSettingsActivity())) {
                this.mSettingsIntent = descriptor.getSettingsActivity();
                changes |= 1;
            }
            if (this.mCanDisconnect == descriptor.canDisconnectAndKeepPlaying()) {
                return changes;
            }
            this.mCanDisconnect = descriptor.canDisconnectAndKeepPlaying();
            return changes | 5;
        }

        String getDescriptorId() {
            return this.mDescriptorId;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public MediaRouteProvider getProviderInstance() {
            return this.mProvider.getProviderInstance();
        }
    }

    private static final class GlobalMediaRouter implements SyncCallback, android.support.v7.media.RegisteredMediaRouteProviderWatcher.Callback {
        final Context mApplicationContext;
        private RouteInfo mBluetoothRoute;
        final CallbackHandler mCallbackHandler = new CallbackHandler();
        private MediaSessionCompat mCompatSession;
        private RouteInfo mDefaultRoute;
        private MediaRouteDiscoveryRequest mDiscoveryRequest;
        private final DisplayManagerCompat mDisplayManager;
        private final boolean mLowRam;
        private MediaSessionRecord mMediaSession;
        final PlaybackInfo mPlaybackInfo = new PlaybackInfo();
        private final ProviderCallback mProviderCallback = new ProviderCallback();
        private final ArrayList<ProviderInfo> mProviders = new ArrayList();
        MediaSessionCompat mRccMediaSession;
        private RegisteredMediaRouteProviderWatcher mRegisteredProviderWatcher;
        private final ArrayList<RemoteControlClientRecord> mRemoteControlClients = new ArrayList();
        private final Map<String, RouteController> mRouteControllerMap = new HashMap();
        final ArrayList<WeakReference<MediaRouter>> mRouters = new ArrayList();
        private final ArrayList<RouteInfo> mRoutes = new ArrayList();
        RouteInfo mSelectedRoute;
        private RouteController mSelectedRouteController;
        private OnActiveChangeListener mSessionActiveListener = new C07061();
        final SystemMediaRouteProvider mSystemProvider;
        private final Map<Pair<String, String>, String> mUniqueIdMap = new HashMap();

        private final class CallbackHandler extends Handler {
            public static final int MSG_PROVIDER_ADDED = 513;
            public static final int MSG_PROVIDER_CHANGED = 515;
            public static final int MSG_PROVIDER_REMOVED = 514;
            public static final int MSG_ROUTE_ADDED = 257;
            public static final int MSG_ROUTE_CHANGED = 259;
            public static final int MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED = 261;
            public static final int MSG_ROUTE_REMOVED = 258;
            public static final int MSG_ROUTE_SELECTED = 262;
            public static final int MSG_ROUTE_UNSELECTED = 263;
            public static final int MSG_ROUTE_VOLUME_CHANGED = 260;
            private static final int MSG_TYPE_MASK = 65280;
            private static final int MSG_TYPE_PROVIDER = 512;
            private static final int MSG_TYPE_ROUTE = 256;
            private final ArrayList<CallbackRecord> mTempCallbackRecords = new ArrayList();

            CallbackHandler() {
            }

            public void post(int msg, Object obj) {
                obtainMessage(msg, obj).sendToTarget();
            }

            public void post(int msg, Object obj, int arg) {
                Message message = obtainMessage(msg, obj);
                message.arg1 = arg;
                message.sendToTarget();
            }

            public void handleMessage(Message msg) {
                int what = msg.what;
                Object obj = msg.obj;
                int arg = msg.arg1;
                if (what == MSG_ROUTE_CHANGED && GlobalMediaRouter.this.getSelectedRoute().getId().equals(((RouteInfo) obj).getId())) {
                    GlobalMediaRouter.this.updateSelectedRouteIfNeeded(true);
                }
                syncWithSystemProvider(what, obj);
                try {
                    int i = GlobalMediaRouter.this.mRouters.size();
                    while (true) {
                        i--;
                        if (i < 0) {
                            break;
                        }
                        MediaRouter router = (MediaRouter) ((WeakReference) GlobalMediaRouter.this.mRouters.get(i)).get();
                        if (router == null) {
                            GlobalMediaRouter.this.mRouters.remove(i);
                        } else {
                            this.mTempCallbackRecords.addAll(router.mCallbackRecords);
                        }
                    }
                    i = this.mTempCallbackRecords.size();
                    for (int i2 = 0; i2 < i; i2++) {
                        invokeCallback((CallbackRecord) this.mTempCallbackRecords.get(i2), what, obj, arg);
                    }
                } finally {
                    this.mTempCallbackRecords.clear();
                }
            }

            private void syncWithSystemProvider(int what, Object obj) {
                if (what != MSG_ROUTE_SELECTED) {
                    switch (what) {
                        case 257:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteAdded((RouteInfo) obj);
                            return;
                        case 258:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteRemoved((RouteInfo) obj);
                            return;
                        case MSG_ROUTE_CHANGED /*259*/:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteChanged((RouteInfo) obj);
                            return;
                        default:
                            return;
                    }
                }
                GlobalMediaRouter.this.mSystemProvider.onSyncRouteSelected((RouteInfo) obj);
            }

            private void invokeCallback(CallbackRecord record, int what, Object obj, int arg) {
                MediaRouter router = record.mRouter;
                Callback callback = record.mCallback;
                int i = 65280 & what;
                if (i == 256) {
                    RouteInfo route = (RouteInfo) obj;
                    if (record.filterRouteEvent(route)) {
                        switch (what) {
                            case 257:
                                callback.onRouteAdded(router, route);
                                break;
                            case 258:
                                callback.onRouteRemoved(router, route);
                                break;
                            case MSG_ROUTE_CHANGED /*259*/:
                                callback.onRouteChanged(router, route);
                                break;
                            case 260:
                                callback.onRouteVolumeChanged(router, route);
                                break;
                            case MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /*261*/:
                                callback.onRoutePresentationDisplayChanged(router, route);
                                break;
                            case MSG_ROUTE_SELECTED /*262*/:
                                callback.onRouteSelected(router, route);
                                break;
                            case 263:
                                callback.onRouteUnselected(router, route, arg);
                                break;
                            default:
                                break;
                        }
                    }
                } else if (i == 512) {
                    ProviderInfo provider = (ProviderInfo) obj;
                    switch (what) {
                        case 513:
                            callback.onProviderAdded(router, provider);
                            return;
                        case 514:
                            callback.onProviderRemoved(router, provider);
                            return;
                        case 515:
                            callback.onProviderChanged(router, provider);
                            return;
                        default:
                            return;
                    }
                }
            }
        }

        private final class MediaSessionRecord {
            private int mControlType;
            private int mMaxVolume;
            private final MediaSessionCompat mMsCompat;
            private VolumeProviderCompat mVpCompat;

            public MediaSessionRecord(Object mediaSession) {
                this.mMsCompat = MediaSessionCompat.fromMediaSession(GlobalMediaRouter.this.mApplicationContext, mediaSession);
            }

            public MediaSessionRecord(MediaSessionCompat mediaSessionCompat) {
                this.mMsCompat = mediaSessionCompat;
            }

            public void configureVolume(int controlType, int max, int current) {
                if (this.mVpCompat != null && controlType == this.mControlType && max == this.mMaxVolume) {
                    this.mVpCompat.setCurrentVolume(current);
                    return;
                }
                this.mVpCompat = new VolumeProviderCompat(controlType, max, current) {
                    public void onSetVolumeTo(final int volume) {
                        GlobalMediaRouter.this.mCallbackHandler.post(new Runnable() {
                            public void run() {
                                if (GlobalMediaRouter.this.mSelectedRoute != null) {
                                    GlobalMediaRouter.this.mSelectedRoute.requestSetVolume(volume);
                                }
                            }
                        });
                    }

                    public void onAdjustVolume(final int direction) {
                        GlobalMediaRouter.this.mCallbackHandler.post(new Runnable() {
                            public void run() {
                                if (GlobalMediaRouter.this.mSelectedRoute != null) {
                                    GlobalMediaRouter.this.mSelectedRoute.requestUpdateVolume(direction);
                                }
                            }
                        });
                    }
                };
                this.mMsCompat.setPlaybackToRemote(this.mVpCompat);
            }

            public void clearVolumeHandling() {
                this.mMsCompat.setPlaybackToLocal(GlobalMediaRouter.this.mPlaybackInfo.playbackStream);
                this.mVpCompat = null;
            }

            public Token getToken() {
                return this.mMsCompat.getSessionToken();
            }
        }

        /* renamed from: android.support.v7.media.MediaRouter$GlobalMediaRouter$1 */
        class C07061 implements OnActiveChangeListener {
            C07061() {
            }

            public void onActiveChanged() {
                if (GlobalMediaRouter.this.mRccMediaSession == null) {
                    return;
                }
                if (GlobalMediaRouter.this.mRccMediaSession.isActive()) {
                    GlobalMediaRouter.this.addRemoteControlClient(GlobalMediaRouter.this.mRccMediaSession.getRemoteControlClient());
                } else {
                    GlobalMediaRouter.this.removeRemoteControlClient(GlobalMediaRouter.this.mRccMediaSession.getRemoteControlClient());
                }
            }
        }

        private final class ProviderCallback extends android.support.v7.media.MediaRouteProvider.Callback {
            ProviderCallback() {
            }

            public void onDescriptorChanged(MediaRouteProvider provider, MediaRouteProviderDescriptor descriptor) {
                GlobalMediaRouter.this.updateProviderDescriptor(provider, descriptor);
            }
        }

        private final class RemoteControlClientRecord implements VolumeCallback {
            private boolean mDisconnected;
            private final RemoteControlClientCompat mRccCompat;

            public RemoteControlClientRecord(Object rcc) {
                this.mRccCompat = RemoteControlClientCompat.obtain(GlobalMediaRouter.this.mApplicationContext, rcc);
                this.mRccCompat.setVolumeCallback(this);
                updatePlaybackInfo();
            }

            public Object getRemoteControlClient() {
                return this.mRccCompat.getRemoteControlClient();
            }

            public void disconnect() {
                this.mDisconnected = true;
                this.mRccCompat.setVolumeCallback(null);
            }

            public void updatePlaybackInfo() {
                this.mRccCompat.setPlaybackInfo(GlobalMediaRouter.this.mPlaybackInfo);
            }

            public void onVolumeSetRequest(int volume) {
                if (!this.mDisconnected && GlobalMediaRouter.this.mSelectedRoute != null) {
                    GlobalMediaRouter.this.mSelectedRoute.requestSetVolume(volume);
                }
            }

            public void onVolumeUpdateRequest(int direction) {
                if (!this.mDisconnected && GlobalMediaRouter.this.mSelectedRoute != null) {
                    GlobalMediaRouter.this.mSelectedRoute.requestUpdateVolume(direction);
                }
            }
        }

        GlobalMediaRouter(Context applicationContext) {
            this.mApplicationContext = applicationContext;
            this.mDisplayManager = DisplayManagerCompat.getInstance(applicationContext);
            this.mLowRam = ActivityManagerCompat.isLowRamDevice((ActivityManager) applicationContext.getSystemService("activity"));
            this.mSystemProvider = SystemMediaRouteProvider.obtain(applicationContext, this);
        }

        public void start() {
            addProvider(this.mSystemProvider);
            this.mRegisteredProviderWatcher = new RegisteredMediaRouteProviderWatcher(this.mApplicationContext, this);
            this.mRegisteredProviderWatcher.start();
        }

        public MediaRouter getRouter(Context context) {
            int i = this.mRouters.size();
            while (true) {
                i--;
                if (i >= 0) {
                    MediaRouter router = (MediaRouter) ((WeakReference) this.mRouters.get(i)).get();
                    if (router == null) {
                        this.mRouters.remove(i);
                    } else if (router.mContext == context) {
                        return router;
                    }
                } else {
                    MediaRouter router2 = new MediaRouter(context);
                    this.mRouters.add(new WeakReference(router2));
                    return router2;
                }
            }
        }

        public ContentResolver getContentResolver() {
            return this.mApplicationContext.getContentResolver();
        }

        public Context getProviderContext(String packageName) {
            if (packageName.equals("android")) {
                return this.mApplicationContext;
            }
            try {
                return this.mApplicationContext.createPackageContext(packageName, 4);
            } catch (NameNotFoundException e) {
                return null;
            }
        }

        public Display getDisplay(int displayId) {
            return this.mDisplayManager.getDisplay(displayId);
        }

        public void sendControlRequest(RouteInfo route, Intent intent, ControlRequestCallback callback) {
            if (!((route == this.mSelectedRoute && this.mSelectedRouteController != null && this.mSelectedRouteController.onControlRequest(intent, callback)) || callback == null)) {
                callback.onError(null, null);
            }
        }

        public void requestSetVolume(RouteInfo route, int volume) {
            if (route == this.mSelectedRoute && this.mSelectedRouteController != null) {
                this.mSelectedRouteController.onSetVolume(volume);
            } else if (!this.mRouteControllerMap.isEmpty()) {
                RouteController controller = (RouteController) this.mRouteControllerMap.get(route.mDescriptorId);
                if (controller != null) {
                    controller.onSetVolume(volume);
                }
            }
        }

        public void requestUpdateVolume(RouteInfo route, int delta) {
            if (route == this.mSelectedRoute && this.mSelectedRouteController != null) {
                this.mSelectedRouteController.onUpdateVolume(delta);
            }
        }

        public RouteInfo getRoute(String uniqueId) {
            Iterator it = this.mRoutes.iterator();
            while (it.hasNext()) {
                RouteInfo info = (RouteInfo) it.next();
                if (info.mUniqueId.equals(uniqueId)) {
                    return info;
                }
            }
            return null;
        }

        public List<RouteInfo> getRoutes() {
            return this.mRoutes;
        }

        List<ProviderInfo> getProviders() {
            return this.mProviders;
        }

        @NonNull
        RouteInfo getDefaultRoute() {
            if (this.mDefaultRoute != null) {
                return this.mDefaultRoute;
            }
            throw new IllegalStateException("There is no default route.  The media router has not yet been fully initialized.");
        }

        RouteInfo getBluetoothRoute() {
            return this.mBluetoothRoute;
        }

        @NonNull
        RouteInfo getSelectedRoute() {
            if (this.mSelectedRoute != null) {
                return this.mSelectedRoute;
            }
            throw new IllegalStateException("There is no currently selected route.  The media router has not yet been fully initialized.");
        }

        void selectRoute(@NonNull RouteInfo route) {
            selectRoute(route, 3);
        }

        void selectRoute(@NonNull RouteInfo route, int unselectReason) {
            String str;
            StringBuilder stringBuilder;
            if (!this.mRoutes.contains(route)) {
                str = MediaRouter.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Ignoring attempt to select removed route: ");
                stringBuilder.append(route);
                Log.w(str, stringBuilder.toString());
            } else if (route.mEnabled) {
                setSelectedRouteInternal(route, unselectReason);
            } else {
                str = MediaRouter.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Ignoring attempt to select disabled route: ");
                stringBuilder.append(route);
                Log.w(str, stringBuilder.toString());
            }
        }

        public boolean isRouteAvailable(MediaRouteSelector selector, int flags) {
            if (selector.isEmpty()) {
                return false;
            }
            if ((flags & 2) == 0 && this.mLowRam) {
                return true;
            }
            int routeCount = this.mRoutes.size();
            for (int i = 0; i < routeCount; i++) {
                RouteInfo route = (RouteInfo) this.mRoutes.get(i);
                if ((flags & 1) == 0 || !route.isDefaultOrBluetooth()) {
                    if (route.matchesSelector(selector)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void updateDiscoveryRequest() {
            boolean discover = false;
            boolean activeScan = false;
            Builder builder = new Builder();
            int i = this.mRouters.size();
            while (true) {
                i--;
                int i2 = 0;
                if (i < 0) {
                    break;
                }
                MediaRouter router = (MediaRouter) ((WeakReference) this.mRouters.get(i)).get();
                if (router == null) {
                    this.mRouters.remove(i);
                } else {
                    int count = router.mCallbackRecords.size();
                    while (i2 < count) {
                        CallbackRecord callback = (CallbackRecord) router.mCallbackRecords.get(i2);
                        builder.addSelector(callback.mSelector);
                        if ((callback.mFlags & 1) != 0) {
                            activeScan = true;
                            discover = true;
                        }
                        if (!((callback.mFlags & 4) == 0 || this.mLowRam)) {
                            discover = true;
                        }
                        if ((callback.mFlags & 8) != 0) {
                            discover = true;
                        }
                        i2++;
                    }
                }
            }
            MediaRouteSelector selector = discover ? builder.build() : MediaRouteSelector.EMPTY;
            if (this.mDiscoveryRequest == null || !this.mDiscoveryRequest.getSelector().equals(selector) || this.mDiscoveryRequest.isActiveScan() != activeScan) {
                if (!selector.isEmpty() || activeScan) {
                    this.mDiscoveryRequest = new MediaRouteDiscoveryRequest(selector, activeScan);
                } else if (this.mDiscoveryRequest != null) {
                    this.mDiscoveryRequest = null;
                } else {
                    return;
                }
                if (MediaRouter.DEBUG) {
                    String str = MediaRouter.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Updated discovery request: ");
                    stringBuilder.append(this.mDiscoveryRequest);
                    Log.d(str, stringBuilder.toString());
                }
                if (discover && !activeScan && this.mLowRam) {
                    Log.i(MediaRouter.TAG, "Forcing passive route discovery on a low-RAM device, system performance may be affected.  Please consider using CALLBACK_FLAG_REQUEST_DISCOVERY instead of CALLBACK_FLAG_FORCE_DISCOVERY.");
                }
                int providerCount = this.mProviders.size();
                while (i2 < providerCount) {
                    ((ProviderInfo) this.mProviders.get(i2)).mProviderInstance.setDiscoveryRequest(this.mDiscoveryRequest);
                    i2++;
                }
            }
        }

        public void addProvider(MediaRouteProvider providerInstance) {
            if (findProviderInfo(providerInstance) < 0) {
                ProviderInfo provider = new ProviderInfo(providerInstance);
                this.mProviders.add(provider);
                if (MediaRouter.DEBUG) {
                    String str = MediaRouter.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Provider added: ");
                    stringBuilder.append(provider);
                    Log.d(str, stringBuilder.toString());
                }
                this.mCallbackHandler.post(513, provider);
                updateProviderContents(provider, providerInstance.getDescriptor());
                providerInstance.setCallback(this.mProviderCallback);
                providerInstance.setDiscoveryRequest(this.mDiscoveryRequest);
            }
        }

        public void removeProvider(MediaRouteProvider providerInstance) {
            int index = findProviderInfo(providerInstance);
            if (index >= 0) {
                providerInstance.setCallback(null);
                providerInstance.setDiscoveryRequest(null);
                ProviderInfo provider = (ProviderInfo) this.mProviders.get(index);
                updateProviderContents(provider, null);
                if (MediaRouter.DEBUG) {
                    String str = MediaRouter.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Provider removed: ");
                    stringBuilder.append(provider);
                    Log.d(str, stringBuilder.toString());
                }
                this.mCallbackHandler.post(514, provider);
                this.mProviders.remove(index);
            }
        }

        void updateProviderDescriptor(MediaRouteProvider providerInstance, MediaRouteProviderDescriptor descriptor) {
            int index = findProviderInfo(providerInstance);
            if (index >= 0) {
                updateProviderContents((ProviderInfo) this.mProviders.get(index), descriptor);
            }
        }

        private int findProviderInfo(MediaRouteProvider providerInstance) {
            int count = this.mProviders.size();
            for (int i = 0; i < count; i++) {
                if (((ProviderInfo) this.mProviders.get(i)).mProviderInstance == providerInstance) {
                    return i;
                }
            }
            return -1;
        }

        private void updateProviderContents(ProviderInfo provider, MediaRouteProviderDescriptor providerDescriptor) {
            GlobalMediaRouter globalMediaRouter = this;
            ProviderInfo providerInfo = provider;
            MediaRouteProviderDescriptor mediaRouteProviderDescriptor = providerDescriptor;
            if (provider.updateDescriptor(providerDescriptor)) {
                boolean selectedRouteDescriptorChanged;
                RouteInfo route;
                int i;
                String str;
                StringBuilder stringBuilder;
                String str2;
                StringBuilder stringBuilder2;
                int targetIndex = 0;
                if (mediaRouteProviderDescriptor != null) {
                    String str3;
                    StringBuilder stringBuilder3;
                    if (providerDescriptor.isValid()) {
                        String uniqueId;
                        StringBuilder stringBuilder4;
                        RouteInfo route2;
                        List<MediaRouteDescriptor> routeDescriptors = providerDescriptor.getRoutes();
                        int routeCount = routeDescriptors.size();
                        List<Pair<RouteInfo, MediaRouteDescriptor>> addedGroups = new ArrayList();
                        List<Pair<RouteInfo, MediaRouteDescriptor>> updatedGroups = new ArrayList();
                        selectedRouteDescriptorChanged = false;
                        boolean selectedRouteDescriptorChanged2 = false;
                        targetIndex = 0;
                        while (targetIndex < routeCount) {
                            List<MediaRouteDescriptor> list;
                            MediaRouteDescriptor routeDescriptor = (MediaRouteDescriptor) routeDescriptors.get(targetIndex);
                            String id = routeDescriptor.getId();
                            boolean sourceIndex = providerInfo.findRouteByDescriptorId(id);
                            if (sourceIndex >= false) {
                                uniqueId = assignRouteUniqueId(providerInfo, id);
                                boolean isGroup = routeDescriptor.getGroupMemberIds() != null;
                                route = isGroup ? new RouteGroup(providerInfo, id, uniqueId) : new RouteInfo(providerInfo, id, uniqueId);
                                boolean targetIndex2 = selectedRouteDescriptorChanged2 + 1;
                                provider.mRoutes.add(selectedRouteDescriptorChanged2, route);
                                globalMediaRouter.mRoutes.add(route);
                                if (isGroup) {
                                    addedGroups.add(new Pair(route, routeDescriptor));
                                    list = routeDescriptors;
                                } else {
                                    route.maybeUpdateDescriptor(routeDescriptor);
                                    if (MediaRouter.DEBUG) {
                                        selectedRouteDescriptorChanged2 = MediaRouter.TAG;
                                        stringBuilder4 = new StringBuilder();
                                        list = routeDescriptors;
                                        stringBuilder4.append("Route added: ");
                                        stringBuilder4.append(route);
                                        Log.d(selectedRouteDescriptorChanged2, stringBuilder4.toString());
                                    } else {
                                        list = routeDescriptors;
                                    }
                                    globalMediaRouter.mCallbackHandler.post(257, route);
                                }
                                selectedRouteDescriptorChanged2 = targetIndex2;
                            } else {
                                list = routeDescriptors;
                                if (sourceIndex < selectedRouteDescriptorChanged2) {
                                    str3 = MediaRouter.TAG;
                                    stringBuilder3 = new StringBuilder();
                                    stringBuilder3.append("Ignoring route descriptor with duplicate id: ");
                                    stringBuilder3.append(routeDescriptor);
                                    Log.w(str3, stringBuilder3.toString());
                                } else {
                                    route = (RouteInfo) provider.mRoutes.get(sourceIndex);
                                    boolean targetIndex3 = selectedRouteDescriptorChanged2 + 1;
                                    Collections.swap(provider.mRoutes, sourceIndex, selectedRouteDescriptorChanged2);
                                    if (route instanceof RouteGroup) {
                                        updatedGroups.add(new Pair(route, routeDescriptor));
                                    } else if (updateRouteDescriptorAndNotify(route, routeDescriptor) && route == globalMediaRouter.mSelectedRoute) {
                                        selectedRouteDescriptorChanged = true;
                                    }
                                    selectedRouteDescriptorChanged2 = targetIndex3;
                                }
                            }
                            targetIndex++;
                            routeDescriptors = list;
                        }
                        for (Pair<RouteInfo, MediaRouteDescriptor> pair : addedGroups) {
                            route2 = pair.first;
                            route2.maybeUpdateDescriptor((MediaRouteDescriptor) pair.second);
                            if (MediaRouter.DEBUG) {
                                uniqueId = MediaRouter.TAG;
                                stringBuilder4 = new StringBuilder();
                                stringBuilder4.append("Route added: ");
                                stringBuilder4.append(route2);
                                Log.d(uniqueId, stringBuilder4.toString());
                            }
                            globalMediaRouter.mCallbackHandler.post(257, route2);
                        }
                        for (Pair<RouteInfo, MediaRouteDescriptor> pair2 : updatedGroups) {
                            route2 = (RouteInfo) pair2.first;
                            if (updateRouteDescriptorAndNotify(route2, (MediaRouteDescriptor) pair2.second) != 0 && route2 == globalMediaRouter.mSelectedRoute) {
                                selectedRouteDescriptorChanged = true;
                            }
                        }
                        targetIndex = selectedRouteDescriptorChanged2;
                        for (i = provider.mRoutes.size() - 1; i >= targetIndex; i--) {
                            route = (RouteInfo) provider.mRoutes.get(i);
                            route.maybeUpdateDescriptor(null);
                            globalMediaRouter.mRoutes.remove(route);
                        }
                        updateSelectedRouteIfNeeded(selectedRouteDescriptorChanged);
                        for (i = provider.mRoutes.size() - 1; i >= targetIndex; i--) {
                            route = (RouteInfo) provider.mRoutes.remove(i);
                            if (MediaRouter.DEBUG) {
                                str = MediaRouter.TAG;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("Route removed: ");
                                stringBuilder.append(route);
                                Log.d(str, stringBuilder.toString());
                            }
                            globalMediaRouter.mCallbackHandler.post(258, route);
                        }
                        if (MediaRouter.DEBUG) {
                            str2 = MediaRouter.TAG;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Provider changed: ");
                            stringBuilder2.append(providerInfo);
                            Log.d(str2, stringBuilder2.toString());
                        }
                        globalMediaRouter.mCallbackHandler.post(515, providerInfo);
                    }
                    str3 = MediaRouter.TAG;
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Ignoring invalid provider descriptor: ");
                    stringBuilder3.append(mediaRouteProviderDescriptor);
                    Log.w(str3, stringBuilder3.toString());
                }
                selectedRouteDescriptorChanged = false;
                for (i = provider.mRoutes.size() - 1; i >= targetIndex; i--) {
                    route = (RouteInfo) provider.mRoutes.get(i);
                    route.maybeUpdateDescriptor(null);
                    globalMediaRouter.mRoutes.remove(route);
                }
                updateSelectedRouteIfNeeded(selectedRouteDescriptorChanged);
                for (i = provider.mRoutes.size() - 1; i >= targetIndex; i--) {
                    route = (RouteInfo) provider.mRoutes.remove(i);
                    if (MediaRouter.DEBUG) {
                        str = MediaRouter.TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Route removed: ");
                        stringBuilder.append(route);
                        Log.d(str, stringBuilder.toString());
                    }
                    globalMediaRouter.mCallbackHandler.post(258, route);
                }
                if (MediaRouter.DEBUG) {
                    str2 = MediaRouter.TAG;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Provider changed: ");
                    stringBuilder2.append(providerInfo);
                    Log.d(str2, stringBuilder2.toString());
                }
                globalMediaRouter.mCallbackHandler.post(515, providerInfo);
            }
        }

        private int updateRouteDescriptorAndNotify(RouteInfo route, MediaRouteDescriptor routeDescriptor) {
            int changes = route.maybeUpdateDescriptor(routeDescriptor);
            if (changes != 0) {
                String str;
                StringBuilder stringBuilder;
                if ((changes & 1) != 0) {
                    if (MediaRouter.DEBUG) {
                        str = MediaRouter.TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Route changed: ");
                        stringBuilder.append(route);
                        Log.d(str, stringBuilder.toString());
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_CHANGED, route);
                }
                if ((changes & 2) != 0) {
                    if (MediaRouter.DEBUG) {
                        str = MediaRouter.TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Route volume changed: ");
                        stringBuilder.append(route);
                        Log.d(str, stringBuilder.toString());
                    }
                    this.mCallbackHandler.post(260, route);
                }
                if ((changes & 4) != 0) {
                    if (MediaRouter.DEBUG) {
                        str = MediaRouter.TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Route presentation display changed: ");
                        stringBuilder.append(route);
                        Log.d(str, stringBuilder.toString());
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED, route);
                }
            }
            return changes;
        }

        private String assignRouteUniqueId(ProviderInfo provider, String routeDescriptorId) {
            String componentName = provider.getComponentName().flattenToShortString();
            String uniqueId = new StringBuilder();
            uniqueId.append(componentName);
            uniqueId.append(":");
            uniqueId.append(routeDescriptorId);
            uniqueId = uniqueId.toString();
            if (findRouteByUniqueId(uniqueId) < 0) {
                this.mUniqueIdMap.put(new Pair(componentName, routeDescriptorId), uniqueId);
                return uniqueId;
            }
            String str = MediaRouter.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Either ");
            stringBuilder.append(routeDescriptorId);
            stringBuilder.append(" isn't unique in ");
            stringBuilder.append(componentName);
            stringBuilder.append(" or we're trying to assign a unique ID for an already added route");
            Log.w(str, stringBuilder.toString());
            int i = 2;
            while (true) {
                String newUniqueId = String.format(Locale.US, "%s_%d", new Object[]{uniqueId, Integer.valueOf(i)});
                if (findRouteByUniqueId(newUniqueId) < 0) {
                    this.mUniqueIdMap.put(new Pair(componentName, routeDescriptorId), newUniqueId);
                    return newUniqueId;
                }
                i++;
            }
        }

        private int findRouteByUniqueId(String uniqueId) {
            int count = this.mRoutes.size();
            for (int i = 0; i < count; i++) {
                if (((RouteInfo) this.mRoutes.get(i)).mUniqueId.equals(uniqueId)) {
                    return i;
                }
            }
            return -1;
        }

        private String getUniqueId(ProviderInfo provider, String routeDescriptorId) {
            return (String) this.mUniqueIdMap.get(new Pair(provider.getComponentName().flattenToShortString(), routeDescriptorId));
        }

        private void updateSelectedRouteIfNeeded(boolean selectedRouteDescriptorChanged) {
            String str;
            Iterator it;
            if (!(this.mDefaultRoute == null || this.mDefaultRoute.isSelectable())) {
                str = MediaRouter.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Clearing the default route because it is no longer selectable: ");
                stringBuilder.append(this.mDefaultRoute);
                Log.i(str, stringBuilder.toString());
                this.mDefaultRoute = null;
            }
            if (this.mDefaultRoute == null && !this.mRoutes.isEmpty()) {
                it = this.mRoutes.iterator();
                while (it.hasNext()) {
                    RouteInfo route = (RouteInfo) it.next();
                    if (isSystemDefaultRoute(route) && route.isSelectable()) {
                        this.mDefaultRoute = route;
                        str = MediaRouter.TAG;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Found default route: ");
                        stringBuilder2.append(this.mDefaultRoute);
                        Log.i(str, stringBuilder2.toString());
                        break;
                    }
                }
            }
            if (!(this.mBluetoothRoute == null || this.mBluetoothRoute.isSelectable())) {
                str = MediaRouter.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Clearing the bluetooth route because it is no longer selectable: ");
                stringBuilder.append(this.mBluetoothRoute);
                Log.i(str, stringBuilder.toString());
                this.mBluetoothRoute = null;
            }
            if (this.mBluetoothRoute == null && !this.mRoutes.isEmpty()) {
                it = this.mRoutes.iterator();
                while (it.hasNext()) {
                    RouteInfo route2 = (RouteInfo) it.next();
                    if (isSystemLiveAudioOnlyRoute(route2) && route2.isSelectable()) {
                        this.mBluetoothRoute = route2;
                        str = MediaRouter.TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Found bluetooth route: ");
                        stringBuilder.append(this.mBluetoothRoute);
                        Log.i(str, stringBuilder.toString());
                        break;
                    }
                }
            }
            if (this.mSelectedRoute != null) {
                if (this.mSelectedRoute.isSelectable()) {
                    if (selectedRouteDescriptorChanged) {
                        if (this.mSelectedRoute instanceof RouteGroup) {
                            List<RouteInfo> routes = ((RouteGroup) this.mSelectedRoute).getRoutes();
                            Set<String> idSet = new HashSet();
                            for (RouteInfo route3 : routes) {
                                idSet.add(route3.mDescriptorId);
                            }
                            Iterator<Entry<String, RouteController>> iter = this.mRouteControllerMap.entrySet().iterator();
                            while (iter.hasNext()) {
                                Entry<String, RouteController> entry = (Entry) iter.next();
                                if (!idSet.contains(entry.getKey())) {
                                    RouteController controller = (RouteController) entry.getValue();
                                    controller.onUnselect();
                                    controller.onRelease();
                                    iter.remove();
                                }
                            }
                            for (RouteInfo route4 : routes) {
                                if (!this.mRouteControllerMap.containsKey(route4.mDescriptorId)) {
                                    RouteController controller2 = route4.getProviderInstance().onCreateRouteController(route4.mDescriptorId, this.mSelectedRoute.mDescriptorId);
                                    controller2.onSelect();
                                    this.mRouteControllerMap.put(route4.mDescriptorId, controller2);
                                }
                            }
                        }
                        updatePlaybackInfoFromSelectedRoute();
                        return;
                    }
                    return;
                }
            }
            str = MediaRouter.TAG;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Unselecting the current route because it is no longer selectable: ");
            stringBuilder3.append(this.mSelectedRoute);
            Log.i(str, stringBuilder3.toString());
            setSelectedRouteInternal(chooseFallbackRoute(), 0);
        }

        RouteInfo chooseFallbackRoute() {
            Iterator it = this.mRoutes.iterator();
            while (it.hasNext()) {
                RouteInfo route = (RouteInfo) it.next();
                if (route != this.mDefaultRoute && isSystemLiveAudioOnlyRoute(route) && route.isSelectable()) {
                    return route;
                }
            }
            return this.mDefaultRoute;
        }

        private boolean isSystemLiveAudioOnlyRoute(RouteInfo route) {
            return route.getProviderInstance() == this.mSystemProvider && route.supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO) && !route.supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO);
        }

        private boolean isSystemDefaultRoute(RouteInfo route) {
            return route.getProviderInstance() == this.mSystemProvider && route.mDescriptorId.equals(SystemMediaRouteProvider.DEFAULT_ROUTE_ID);
        }

        private void setSelectedRouteInternal(@NonNull RouteInfo route, int unselectReason) {
            StringBuilder sb;
            if (MediaRouter.sGlobal == null || (this.mBluetoothRoute != null && route.isDefault())) {
                StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
                sb = new StringBuilder();
                for (int i = 3; i < callStack.length; i++) {
                    StackTraceElement caller = callStack[i];
                    sb.append(caller.getClassName());
                    sb.append(".");
                    sb.append(caller.getMethodName());
                    sb.append(":");
                    sb.append(caller.getLineNumber());
                    sb.append("  ");
                }
                String str;
                StringBuilder stringBuilder;
                if (MediaRouter.sGlobal == null) {
                    str = MediaRouter.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("setSelectedRouteInternal is called while sGlobal is null: pkgName=");
                    stringBuilder.append(this.mApplicationContext.getPackageName());
                    stringBuilder.append(", callers=");
                    stringBuilder.append(sb.toString());
                    Log.w(str, stringBuilder.toString());
                } else {
                    str = MediaRouter.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Default route is selected while a BT route is available: pkgName=");
                    stringBuilder.append(this.mApplicationContext.getPackageName());
                    stringBuilder.append(", callers=");
                    stringBuilder.append(sb.toString());
                    Log.w(str, stringBuilder.toString());
                }
            }
            if (this.mSelectedRoute != route) {
                String str2;
                if (this.mSelectedRoute != null) {
                    if (MediaRouter.DEBUG) {
                        str2 = MediaRouter.TAG;
                        sb = new StringBuilder();
                        sb.append("Route unselected: ");
                        sb.append(this.mSelectedRoute);
                        sb.append(" reason: ");
                        sb.append(unselectReason);
                        Log.d(str2, sb.toString());
                    }
                    this.mCallbackHandler.post(263, this.mSelectedRoute, unselectReason);
                    if (this.mSelectedRouteController != null) {
                        this.mSelectedRouteController.onUnselect(unselectReason);
                        this.mSelectedRouteController.onRelease();
                        this.mSelectedRouteController = null;
                    }
                    if (!this.mRouteControllerMap.isEmpty()) {
                        for (RouteController controller : this.mRouteControllerMap.values()) {
                            controller.onUnselect(unselectReason);
                            controller.onRelease();
                        }
                        this.mRouteControllerMap.clear();
                    }
                }
                this.mSelectedRoute = route;
                this.mSelectedRouteController = route.getProviderInstance().onCreateRouteController(route.mDescriptorId);
                if (this.mSelectedRouteController != null) {
                    this.mSelectedRouteController.onSelect();
                }
                if (MediaRouter.DEBUG) {
                    str2 = MediaRouter.TAG;
                    sb = new StringBuilder();
                    sb.append("Route selected: ");
                    sb.append(this.mSelectedRoute);
                    Log.d(str2, sb.toString());
                }
                this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_SELECTED, this.mSelectedRoute);
                if (this.mSelectedRoute instanceof RouteGroup) {
                    List<RouteInfo> routes = ((RouteGroup) this.mSelectedRoute).getRoutes();
                    this.mRouteControllerMap.clear();
                    for (RouteInfo r : routes) {
                        RouteController controller2 = r.getProviderInstance().onCreateRouteController(r.mDescriptorId, this.mSelectedRoute.mDescriptorId);
                        controller2.onSelect();
                        this.mRouteControllerMap.put(r.mDescriptorId, controller2);
                    }
                }
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        public void onSystemRouteSelectedByDescriptorId(String id) {
            this.mCallbackHandler.removeMessages(CallbackHandler.MSG_ROUTE_SELECTED);
            int providerIndex = findProviderInfo(this.mSystemProvider);
            if (providerIndex >= 0) {
                ProviderInfo provider = (ProviderInfo) this.mProviders.get(providerIndex);
                int routeIndex = provider.findRouteByDescriptorId(id);
                if (routeIndex >= 0) {
                    ((RouteInfo) provider.mRoutes.get(routeIndex)).select();
                }
            }
        }

        public void addRemoteControlClient(Object rcc) {
            if (findRemoteControlClientRecord(rcc) < 0) {
                this.mRemoteControlClients.add(new RemoteControlClientRecord(rcc));
            }
        }

        public void removeRemoteControlClient(Object rcc) {
            int index = findRemoteControlClientRecord(rcc);
            if (index >= 0) {
                ((RemoteControlClientRecord) this.mRemoteControlClients.remove(index)).disconnect();
            }
        }

        public void setMediaSession(Object session) {
            setMediaSessionRecord(session != null ? new MediaSessionRecord(session) : null);
        }

        public void setMediaSessionCompat(MediaSessionCompat session) {
            this.mCompatSession = session;
            if (VERSION.SDK_INT >= 21) {
                setMediaSessionRecord(session != null ? new MediaSessionRecord(session) : null);
            } else if (VERSION.SDK_INT >= 14) {
                if (this.mRccMediaSession != null) {
                    removeRemoteControlClient(this.mRccMediaSession.getRemoteControlClient());
                    this.mRccMediaSession.removeOnActiveChangeListener(this.mSessionActiveListener);
                }
                this.mRccMediaSession = session;
                if (session != null) {
                    session.addOnActiveChangeListener(this.mSessionActiveListener);
                    if (session.isActive()) {
                        addRemoteControlClient(session.getRemoteControlClient());
                    }
                }
            }
        }

        private void setMediaSessionRecord(MediaSessionRecord mediaSessionRecord) {
            if (this.mMediaSession != null) {
                this.mMediaSession.clearVolumeHandling();
            }
            this.mMediaSession = mediaSessionRecord;
            if (mediaSessionRecord != null) {
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        public Token getMediaSessionToken() {
            if (this.mMediaSession != null) {
                return this.mMediaSession.getToken();
            }
            if (this.mCompatSession != null) {
                return this.mCompatSession.getSessionToken();
            }
            return null;
        }

        private int findRemoteControlClientRecord(Object rcc) {
            int count = this.mRemoteControlClients.size();
            for (int i = 0; i < count; i++) {
                if (((RemoteControlClientRecord) this.mRemoteControlClients.get(i)).getRemoteControlClient() == rcc) {
                    return i;
                }
            }
            return -1;
        }

        private void updatePlaybackInfoFromSelectedRoute() {
            if (this.mSelectedRoute != null) {
                int i;
                this.mPlaybackInfo.volume = this.mSelectedRoute.getVolume();
                this.mPlaybackInfo.volumeMax = this.mSelectedRoute.getVolumeMax();
                this.mPlaybackInfo.volumeHandling = this.mSelectedRoute.getVolumeHandling();
                this.mPlaybackInfo.playbackStream = this.mSelectedRoute.getPlaybackStream();
                this.mPlaybackInfo.playbackType = this.mSelectedRoute.getPlaybackType();
                int count = this.mRemoteControlClients.size();
                for (i = 0; i < count; i++) {
                    ((RemoteControlClientRecord) this.mRemoteControlClients.get(i)).updatePlaybackInfo();
                }
                if (this.mMediaSession != null) {
                    if (this.mSelectedRoute != getDefaultRoute()) {
                        if (this.mSelectedRoute != getBluetoothRoute()) {
                            i = 0;
                            if (this.mPlaybackInfo.volumeHandling == 1) {
                                i = 2;
                            }
                            this.mMediaSession.configureVolume(i, this.mPlaybackInfo.volumeMax, this.mPlaybackInfo.volume);
                        }
                    }
                    this.mMediaSession.clearVolumeHandling();
                }
            } else if (this.mMediaSession != null) {
                this.mMediaSession.clearVolumeHandling();
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static class RouteGroup extends RouteInfo {
        private List<RouteInfo> mRoutes = new ArrayList();

        RouteGroup(ProviderInfo provider, String descriptorId, String uniqueId) {
            super(provider, descriptorId, uniqueId);
        }

        public int getRouteCount() {
            return this.mRoutes.size();
        }

        public RouteInfo getRouteAt(int index) {
            return (RouteInfo) this.mRoutes.get(index);
        }

        public List<RouteInfo> getRoutes() {
            return this.mRoutes;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(super.toString());
            sb.append(Constants.REMIX_URL_PREFIX_INDICATOR);
            int count = this.mRoutes.size();
            for (int i = 0; i < count; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.mRoutes.get(i));
            }
            sb.append(Constants.REMIX_URL_SUFIX_INDICATOR);
            return sb.toString();
        }

        int maybeUpdateDescriptor(MediaRouteDescriptor descriptor) {
            boolean changed = false;
            int i = 0;
            if (this.mDescriptor != descriptor) {
                this.mDescriptor = descriptor;
                if (descriptor != null) {
                    List<String> groupMemberIds = descriptor.getGroupMemberIds();
                    List<RouteInfo> routes = new ArrayList();
                    changed = groupMemberIds.size() != this.mRoutes.size();
                    for (String groupMemberId : groupMemberIds) {
                        RouteInfo groupMember = MediaRouter.sGlobal.getRoute(MediaRouter.sGlobal.getUniqueId(getProvider(), groupMemberId));
                        if (groupMember != null) {
                            routes.add(groupMember);
                            if (!(changed || this.mRoutes.contains(groupMember))) {
                                changed = true;
                            }
                        }
                    }
                    if (changed) {
                        this.mRoutes = routes;
                    }
                }
            }
            if (changed) {
                i = 1;
            }
            return super.updateDescriptor(descriptor) | i;
        }
    }

    private MediaRouter(Context context) {
        this.mCallbackRecords = new ArrayList();
        this.mContext = context;
    }

    public static MediaRouter getInstance(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        checkCallingThread();
        if (sGlobal == null) {
            sGlobal = new GlobalMediaRouter(context.getApplicationContext());
            sGlobal.start();
        }
        return sGlobal.getRouter(context);
    }

    public List<RouteInfo> getRoutes() {
        checkCallingThread();
        return sGlobal.getRoutes();
    }

    public List<ProviderInfo> getProviders() {
        checkCallingThread();
        return sGlobal.getProviders();
    }

    @NonNull
    public RouteInfo getDefaultRoute() {
        checkCallingThread();
        return sGlobal.getDefaultRoute();
    }

    public RouteInfo getBluetoothRoute() {
        checkCallingThread();
        return sGlobal.getBluetoothRoute();
    }

    @NonNull
    public RouteInfo getSelectedRoute() {
        checkCallingThread();
        return sGlobal.getSelectedRoute();
    }

    @NonNull
    public RouteInfo updateSelectedRoute(@NonNull MediaRouteSelector selector) {
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateSelectedRoute: ");
            stringBuilder.append(selector);
            Log.d(str, stringBuilder.toString());
        }
        RouteInfo route = sGlobal.getSelectedRoute();
        if (route.isDefaultOrBluetooth() || route.matchesSelector(selector)) {
            return route;
        }
        route = sGlobal.chooseFallbackRoute();
        sGlobal.selectRoute(route);
        return route;
    }

    public void selectRoute(@NonNull RouteInfo route) {
        if (route == null) {
            throw new IllegalArgumentException("route must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("selectRoute: ");
            stringBuilder.append(route);
            Log.d(str, stringBuilder.toString());
        }
        sGlobal.selectRoute(route);
    }

    public void unselect(int reason) {
        if (reason >= 0) {
            if (reason <= 3) {
                checkCallingThread();
                RouteInfo fallbackRoute = sGlobal.chooseFallbackRoute();
                if (sGlobal.getSelectedRoute() != fallbackRoute) {
                    sGlobal.selectRoute(fallbackRoute, reason);
                    return;
                } else {
                    sGlobal.selectRoute(sGlobal.getDefaultRoute(), reason);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Unsupported reason to unselect route");
    }

    public boolean isRouteAvailable(@NonNull MediaRouteSelector selector, int flags) {
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        checkCallingThread();
        return sGlobal.isRouteAvailable(selector, flags);
    }

    public void addCallback(MediaRouteSelector selector, Callback callback) {
        addCallback(selector, callback, 0);
    }

    public void addCallback(@NonNull MediaRouteSelector selector, @NonNull Callback callback, int flags) {
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        } else if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        } else {
            CallbackRecord record;
            checkCallingThread();
            if (DEBUG) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("addCallback: selector=");
                stringBuilder.append(selector);
                stringBuilder.append(", callback=");
                stringBuilder.append(callback);
                stringBuilder.append(", flags=");
                stringBuilder.append(Integer.toHexString(flags));
                Log.d(str, stringBuilder.toString());
            }
            int index = findCallbackRecord(callback);
            if (index < 0) {
                record = new CallbackRecord(this, callback);
                this.mCallbackRecords.add(record);
            } else {
                record = (CallbackRecord) this.mCallbackRecords.get(index);
            }
            boolean updateNeeded = false;
            if (((record.mFlags ^ -1) & flags) != 0) {
                record.mFlags |= flags;
                updateNeeded = true;
            }
            if (!record.mSelector.contains(selector)) {
                record.mSelector = new Builder(record.mSelector).addSelector(selector).build();
                updateNeeded = true;
            }
            if (updateNeeded) {
                sGlobal.updateDiscoveryRequest();
            }
        }
    }

    public void removeCallback(@NonNull Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removeCallback: callback=");
            stringBuilder.append(callback);
            Log.d(str, stringBuilder.toString());
        }
        int index = findCallbackRecord(callback);
        if (index >= 0) {
            this.mCallbackRecords.remove(index);
            sGlobal.updateDiscoveryRequest();
        }
    }

    private int findCallbackRecord(Callback callback) {
        int count = this.mCallbackRecords.size();
        for (int i = 0; i < count; i++) {
            if (((CallbackRecord) this.mCallbackRecords.get(i)).mCallback == callback) {
                return i;
            }
        }
        return -1;
    }

    public void addProvider(@NonNull MediaRouteProvider providerInstance) {
        if (providerInstance == null) {
            throw new IllegalArgumentException("providerInstance must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("addProvider: ");
            stringBuilder.append(providerInstance);
            Log.d(str, stringBuilder.toString());
        }
        sGlobal.addProvider(providerInstance);
    }

    public void removeProvider(@NonNull MediaRouteProvider providerInstance) {
        if (providerInstance == null) {
            throw new IllegalArgumentException("providerInstance must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removeProvider: ");
            stringBuilder.append(providerInstance);
            Log.d(str, stringBuilder.toString());
        }
        sGlobal.removeProvider(providerInstance);
    }

    public void addRemoteControlClient(@NonNull Object remoteControlClient) {
        if (remoteControlClient == null) {
            throw new IllegalArgumentException("remoteControlClient must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("addRemoteControlClient: ");
            stringBuilder.append(remoteControlClient);
            Log.d(str, stringBuilder.toString());
        }
        sGlobal.addRemoteControlClient(remoteControlClient);
    }

    public void removeRemoteControlClient(@NonNull Object remoteControlClient) {
        if (remoteControlClient == null) {
            throw new IllegalArgumentException("remoteControlClient must not be null");
        }
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removeRemoteControlClient: ");
            stringBuilder.append(remoteControlClient);
            Log.d(str, stringBuilder.toString());
        }
        sGlobal.removeRemoteControlClient(remoteControlClient);
    }

    public void setMediaSession(Object mediaSession) {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("addMediaSession: ");
            stringBuilder.append(mediaSession);
            Log.d(str, stringBuilder.toString());
        }
        sGlobal.setMediaSession(mediaSession);
    }

    public void setMediaSessionCompat(MediaSessionCompat mediaSession) {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("addMediaSessionCompat: ");
            stringBuilder.append(mediaSession);
            Log.d(str, stringBuilder.toString());
        }
        sGlobal.setMediaSessionCompat(mediaSession);
    }

    public Token getMediaSessionToken() {
        return sGlobal.getMediaSessionToken();
    }

    static void checkCallingThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("The media router service must only be accessed on the application's main thread.");
        }
    }

    static <T> boolean equal(T a, T b) {
        if (a != b) {
            if (a == null || b == null || !a.equals(b)) {
                return false;
            }
        }
        return true;
    }
}
