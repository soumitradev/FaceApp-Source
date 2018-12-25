package android.support.v4.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@RequiresApi(16)
class TaskStackBuilder$TaskStackBuilderApi16Impl extends TaskStackBuilder$TaskStackBuilderBaseImpl {
    TaskStackBuilder$TaskStackBuilderApi16Impl() {
    }

    public PendingIntent getPendingIntent(Context context, Intent[] intents, int requestCode, int flags, Bundle options) {
        intents[0] = new Intent(intents[0]).addFlags(268484608);
        return PendingIntent.getActivities(context, requestCode, intents, flags, options);
    }
}
