package android.support.v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaSession.Stub;
import android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi21;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.support.v4.media.session.MediaSessionCompat.ResultReceiverWrapper;
import android.view.KeyEvent;
import java.util.List;

class MediaSessionCompat$MediaSessionImplApi21$ExtraSession extends Stub {
    final /* synthetic */ MediaSessionImplApi21 this$0;

    MediaSessionCompat$MediaSessionImplApi21$ExtraSession(MediaSessionImplApi21 this$0) {
        this.this$0 = this$0;
    }

    public void sendCommand(String command, Bundle args, ResultReceiverWrapper cb) {
        throw new AssertionError();
    }

    public boolean sendMediaButton(KeyEvent mediaButton) {
        throw new AssertionError();
    }

    public void registerCallbackListener(IMediaControllerCallback cb) {
        if (!MediaSessionImplApi21.access$600(this.this$0)) {
            MediaSessionImplApi21.access$700(this.this$0).register(cb);
        }
    }

    public void unregisterCallbackListener(IMediaControllerCallback cb) {
        MediaSessionImplApi21.access$700(this.this$0).unregister(cb);
    }

    public String getPackageName() {
        throw new AssertionError();
    }

    public String getTag() {
        throw new AssertionError();
    }

    public PendingIntent getLaunchPendingIntent() {
        throw new AssertionError();
    }

    public long getFlags() {
        throw new AssertionError();
    }

    public ParcelableVolumeInfo getVolumeAttributes() {
        throw new AssertionError();
    }

    public void adjustVolume(int direction, int flags, String packageName) {
        throw new AssertionError();
    }

    public void setVolumeTo(int value, int flags, String packageName) {
        throw new AssertionError();
    }

    public void prepare() throws RemoteException {
        throw new AssertionError();
    }

    public void prepareFromMediaId(String mediaId, Bundle extras) throws RemoteException {
        throw new AssertionError();
    }

    public void prepareFromSearch(String query, Bundle extras) throws RemoteException {
        throw new AssertionError();
    }

    public void prepareFromUri(Uri uri, Bundle extras) throws RemoteException {
        throw new AssertionError();
    }

    public void play() throws RemoteException {
        throw new AssertionError();
    }

    public void playFromMediaId(String mediaId, Bundle extras) throws RemoteException {
        throw new AssertionError();
    }

    public void playFromSearch(String query, Bundle extras) throws RemoteException {
        throw new AssertionError();
    }

    public void playFromUri(Uri uri, Bundle extras) throws RemoteException {
        throw new AssertionError();
    }

    public void skipToQueueItem(long id) {
        throw new AssertionError();
    }

    public void pause() throws RemoteException {
        throw new AssertionError();
    }

    public void stop() throws RemoteException {
        throw new AssertionError();
    }

    public void next() throws RemoteException {
        throw new AssertionError();
    }

    public void previous() throws RemoteException {
        throw new AssertionError();
    }

    public void fastForward() throws RemoteException {
        throw new AssertionError();
    }

    public void rewind() throws RemoteException {
        throw new AssertionError();
    }

    public void seekTo(long pos) throws RemoteException {
        throw new AssertionError();
    }

    public void rate(RatingCompat rating) throws RemoteException {
        throw new AssertionError();
    }

    public void rateWithExtras(RatingCompat rating, Bundle extras) throws RemoteException {
        throw new AssertionError();
    }

    public void setCaptioningEnabled(boolean enabled) throws RemoteException {
        throw new AssertionError();
    }

    public void setRepeatMode(int repeatMode) throws RemoteException {
        throw new AssertionError();
    }

    public void setShuffleModeEnabledRemoved(boolean enabled) throws RemoteException {
    }

    public void setShuffleMode(int shuffleMode) throws RemoteException {
        throw new AssertionError();
    }

    public void sendCustomAction(String action, Bundle args) throws RemoteException {
        throw new AssertionError();
    }

    public MediaMetadataCompat getMetadata() {
        throw new AssertionError();
    }

    public PlaybackStateCompat getPlaybackState() {
        return MediaSessionCompat.access$500(MediaSessionImplApi21.access$800(this.this$0), MediaSessionImplApi21.access$900(this.this$0));
    }

    public List<QueueItem> getQueue() {
        return null;
    }

    public void addQueueItem(MediaDescriptionCompat descriptionCompat) {
        throw new AssertionError();
    }

    public void addQueueItemAt(MediaDescriptionCompat descriptionCompat, int index) {
        throw new AssertionError();
    }

    public void removeQueueItem(MediaDescriptionCompat description) {
        throw new AssertionError();
    }

    public void removeQueueItemAt(int index) {
        throw new AssertionError();
    }

    public CharSequence getQueueTitle() {
        throw new AssertionError();
    }

    public Bundle getExtras() {
        throw new AssertionError();
    }

    public int getRatingType() {
        return this.this$0.mRatingType;
    }

    public boolean isCaptioningEnabled() {
        return this.this$0.mCaptioningEnabled;
    }

    public int getRepeatMode() {
        return this.this$0.mRepeatMode;
    }

    public boolean isShuffleModeEnabledRemoved() {
        return false;
    }

    public int getShuffleMode() {
        return this.this$0.mShuffleMode;
    }

    public boolean isTransportControlEnabled() {
        throw new AssertionError();
    }
}
