package android.support.v4.media.session;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaSessionCompat.Token;

@RequiresApi(23)
class MediaControllerCompat$MediaControllerImplApi23 extends MediaControllerCompat$MediaControllerImplApi21 {
    public MediaControllerCompat$MediaControllerImplApi23(Context context, MediaSessionCompat session) {
        super(context, session);
    }

    public MediaControllerCompat$MediaControllerImplApi23(Context context, Token sessionToken) throws RemoteException {
        super(context, sessionToken);
    }

    public MediaControllerCompat$TransportControls getTransportControls() {
        Object controlsObj = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
        return controlsObj != null ? new MediaControllerCompat$TransportControlsApi23(controlsObj) : null;
    }
}
