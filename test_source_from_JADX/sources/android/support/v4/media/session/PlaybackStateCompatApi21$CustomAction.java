package android.support.v4.media.session;

import android.media.session.PlaybackState.CustomAction;
import android.media.session.PlaybackState.CustomAction.Builder;
import android.os.Bundle;

final class PlaybackStateCompatApi21$CustomAction {
    PlaybackStateCompatApi21$CustomAction() {
    }

    public static String getAction(Object customActionObj) {
        return ((CustomAction) customActionObj).getAction();
    }

    public static CharSequence getName(Object customActionObj) {
        return ((CustomAction) customActionObj).getName();
    }

    public static int getIcon(Object customActionObj) {
        return ((CustomAction) customActionObj).getIcon();
    }

    public static Bundle getExtras(Object customActionObj) {
        return ((CustomAction) customActionObj).getExtras();
    }

    public static Object newInstance(String action, CharSequence name, int icon, Bundle extras) {
        Builder customActionObj = new Builder(action, name, icon);
        customActionObj.setExtras(extras);
        return customActionObj.build();
    }
}
