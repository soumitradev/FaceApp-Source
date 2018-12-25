package android.support.v4.media;

import android.media.VolumeProvider;
import android.support.v4.media.VolumeProviderCompatApi21.Delegate;

class VolumeProviderCompatApi21$1 extends VolumeProvider {
    final /* synthetic */ Delegate val$delegate;

    VolumeProviderCompatApi21$1(int x0, int x1, int x2, Delegate delegate) {
        this.val$delegate = delegate;
        super(x0, x1, x2);
    }

    public void onSetVolumeTo(int volume) {
        this.val$delegate.onSetVolumeTo(volume);
    }

    public void onAdjustVolume(int direction) {
        this.val$delegate.onAdjustVolume(direction);
    }
}
