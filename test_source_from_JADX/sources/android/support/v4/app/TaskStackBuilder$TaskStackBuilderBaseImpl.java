package android.support.v4.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

class TaskStackBuilder$TaskStackBuilderBaseImpl {
    TaskStackBuilder$TaskStackBuilderBaseImpl() {
    }

    public PendingIntent getPendingIntent(Context context, Intent[] intents, int requestCode, int flags, Bundle options) {
        intents[0] = new Intent(intents[0]).addFlags(268484608);
        return PendingIntent.getActivities(context, requestCode, intents, flags);
    }
}
