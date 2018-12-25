package android.support.v4.app;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.Builder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class NotificationCompat$Action$Builder {
    private boolean mAllowGeneratedReplies;
    private final Bundle mExtras;
    private final int mIcon;
    private final PendingIntent mIntent;
    private ArrayList<RemoteInput> mRemoteInputs;
    private final CharSequence mTitle;

    public NotificationCompat$Action$Builder(int icon, CharSequence title, PendingIntent intent) {
        this(icon, title, intent, new Bundle(), null, true);
    }

    public NotificationCompat$Action$Builder(Action action) {
        this(action.icon, action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies());
    }

    private NotificationCompat$Action$Builder(int icon, CharSequence title, PendingIntent intent, Bundle extras, RemoteInput[] remoteInputs, boolean allowGeneratedReplies) {
        ArrayList arrayList;
        this.mAllowGeneratedReplies = true;
        this.mIcon = icon;
        this.mTitle = Builder.limitCharSequenceLength(title);
        this.mIntent = intent;
        this.mExtras = extras;
        if (remoteInputs == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList(Arrays.asList(remoteInputs));
        }
        this.mRemoteInputs = arrayList;
        this.mAllowGeneratedReplies = allowGeneratedReplies;
    }

    public NotificationCompat$Action$Builder addExtras(Bundle extras) {
        if (extras != null) {
            this.mExtras.putAll(extras);
        }
        return this;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public NotificationCompat$Action$Builder addRemoteInput(RemoteInput remoteInput) {
        if (this.mRemoteInputs == null) {
            this.mRemoteInputs = new ArrayList();
        }
        this.mRemoteInputs.add(remoteInput);
        return this;
    }

    public NotificationCompat$Action$Builder setAllowGeneratedReplies(boolean allowGeneratedReplies) {
        this.mAllowGeneratedReplies = allowGeneratedReplies;
        return this;
    }

    public NotificationCompat$Action$Builder extend(NotificationCompat$Action$Extender extender) {
        extender.extend(this);
        return this;
    }

    public Action build() {
        List<RemoteInput> dataOnlyInputs = new ArrayList();
        List<RemoteInput> textInputs = new ArrayList();
        if (this.mRemoteInputs != null) {
            Iterator it = this.mRemoteInputs.iterator();
            while (it.hasNext()) {
                RemoteInput input = (RemoteInput) it.next();
                if (input.isDataOnly()) {
                    dataOnlyInputs.add(input);
                } else {
                    textInputs.add(input);
                }
            }
        }
        RemoteInput[] remoteInputArr = null;
        RemoteInput[] dataOnlyInputsArr = dataOnlyInputs.isEmpty() ? null : (RemoteInput[]) dataOnlyInputs.toArray(new RemoteInput[dataOnlyInputs.size()]);
        if (!textInputs.isEmpty()) {
            remoteInputArr = (RemoteInput[]) textInputs.toArray(new RemoteInput[textInputs.size()]);
        }
        return new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, remoteInputArr, dataOnlyInputsArr, this.mAllowGeneratedReplies);
    }
}
