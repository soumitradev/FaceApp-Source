package android.support.v4.media.session;

import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.VolumeProviderCompat.Callback;
import android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase;

class MediaSessionCompat$MediaSessionImplBase$1 extends Callback {
    final /* synthetic */ MediaSessionImplBase this$0;

    MediaSessionCompat$MediaSessionImplBase$1(MediaSessionImplBase this$0) {
        this.this$0 = this$0;
    }

    public void onVolumeChanged(VolumeProviderCompat volumeProvider) {
        if (this.this$0.mVolumeProvider == volumeProvider) {
            this.this$0.sendVolumeInfoChanged(new ParcelableVolumeInfo(this.this$0.mVolumeType, this.this$0.mLocalStream, volumeProvider.getVolumeControl(), volumeProvider.getMaxVolume(), volumeProvider.getCurrentVolume()));
        }
    }
}
