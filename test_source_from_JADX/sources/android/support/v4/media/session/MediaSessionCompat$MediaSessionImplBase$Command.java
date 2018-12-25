package android.support.v4.media.session;

import android.os.Bundle;
import android.os.ResultReceiver;

final class MediaSessionCompat$MediaSessionImplBase$Command {
    public final String command;
    public final Bundle extras;
    public final ResultReceiver stub;

    public MediaSessionCompat$MediaSessionImplBase$Command(String command, Bundle extras, ResultReceiver stub) {
        this.command = command;
        this.extras = extras;
        this.stub = stub;
    }
}
