package android.support.v4.media.session;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaSessionCompat.Token;

@RequiresApi(24)
class MediaControllerCompat$MediaControllerImplApi24 extends MediaControllerCompat$MediaControllerImplApi23 {
    public MediaControllerCompat$MediaControllerImplApi24(Context context, MediaSessionCompat session) {
        super(context, session);
    }

    public MediaControllerCompat$MediaControllerImplApi24(Context context, Token sessionToken) throws RemoteException {
        super(context, sessionToken);
    }

    public MediaControllerCompat$TransportControls getTransportControls() {
        Object controlsObj = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
        return controlsObj != null ? new MediaControllerCompat$TransportControlsApi24(controlsObj) : null;
    }
}
