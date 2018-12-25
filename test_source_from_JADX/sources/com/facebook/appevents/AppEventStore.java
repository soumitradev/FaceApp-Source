package com.facebook.appevents;

import android.content.Context;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

class AppEventStore {
    private static final String PERSISTED_EVENTS_FILENAME = "AppEventsLogger.persistedevents";
    private static final String TAG = AppEventStore.class.getName();

    private static class MovedClassObjectInputStream extends ObjectInputStream {
        private static final String ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair$SerializationProxyV1";
        private static final String APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AppEvent$SerializationProxyV1";

        public MovedClassObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

        protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
            ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();
            if (resultClassDescriptor.getName().equals(ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME)) {
                return ObjectStreamClass.lookup(SerializationProxyV1.class);
            }
            if (resultClassDescriptor.getName().equals(APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME)) {
                return ObjectStreamClass.lookup(SerializationProxyV1.class);
            }
            return resultClassDescriptor;
        }
    }

    AppEventStore() {
    }

    public static synchronized void persistEvents(AccessTokenAppIdPair accessTokenAppIdPair, SessionEventsState appEvents) {
        synchronized (AppEventStore.class) {
            assertIsNotMainThread();
            PersistedEvents persistedEvents = readAndClearStore();
            if (persistedEvents.containsKey(accessTokenAppIdPair)) {
                persistedEvents.get(accessTokenAppIdPair).addAll(appEvents.getEventsToPersist());
            } else {
                persistedEvents.addEvents(accessTokenAppIdPair, appEvents.getEventsToPersist());
            }
            saveEventsToDisk(persistedEvents);
        }
    }

    public static synchronized void persistEvents(AppEventCollection eventsToPersist) {
        synchronized (AppEventStore.class) {
            assertIsNotMainThread();
            PersistedEvents persistedEvents = readAndClearStore();
            for (AccessTokenAppIdPair accessTokenAppIdPair : eventsToPersist.keySet()) {
                persistedEvents.addEvents(accessTokenAppIdPair, eventsToPersist.get(accessTokenAppIdPair).getEventsToPersist());
            }
            saveEventsToDisk(persistedEvents);
        }
    }

    public static synchronized com.facebook.appevents.PersistedEvents readAndClearStore() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.facebook.appevents.AppEventStore.readAndClearStore():com.facebook.appevents.PersistedEvents. bs: [B:9:0x0027, B:20:0x003d]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r0 = com.facebook.appevents.AppEventStore.class;
        monitor-enter(r0);
        assertIsNotMainThread();	 Catch:{ all -> 0x008b }
        r1 = 0;	 Catch:{ all -> 0x008b }
        r2 = 0;	 Catch:{ all -> 0x008b }
        r3 = com.facebook.FacebookSdk.getApplicationContext();	 Catch:{ all -> 0x008b }
        r4 = "AppEventsLogger.persistedevents";	 Catch:{ FileNotFoundException -> 0x006d, Exception -> 0x003c }
        r4 = r3.openFileInput(r4);	 Catch:{ FileNotFoundException -> 0x006d, Exception -> 0x003c }
        r5 = new com.facebook.appevents.AppEventStore$MovedClassObjectInputStream;	 Catch:{ FileNotFoundException -> 0x006d, Exception -> 0x003c }
        r6 = new java.io.BufferedInputStream;	 Catch:{ FileNotFoundException -> 0x006d, Exception -> 0x003c }
        r6.<init>(r4);	 Catch:{ FileNotFoundException -> 0x006d, Exception -> 0x003c }
        r5.<init>(r6);	 Catch:{ FileNotFoundException -> 0x006d, Exception -> 0x003c }
        r1 = r5;	 Catch:{ FileNotFoundException -> 0x006d, Exception -> 0x003c }
        r5 = r1.readObject();	 Catch:{ FileNotFoundException -> 0x006d, Exception -> 0x003c }
        r5 = (com.facebook.appevents.PersistedEvents) r5;	 Catch:{ FileNotFoundException -> 0x006d, Exception -> 0x003c }
        r2 = r5;
        com.facebook.internal.Utility.closeQuietly(r1);	 Catch:{ all -> 0x008b }
        r4 = "AppEventsLogger.persistedevents";	 Catch:{ Exception -> 0x0031 }
        r4 = r3.getFileStreamPath(r4);	 Catch:{ Exception -> 0x0031 }
        r4.delete();	 Catch:{ Exception -> 0x0031 }
    L_0x0030:
        goto L_0x0081;
    L_0x0031:
        r4 = move-exception;
        r5 = TAG;	 Catch:{ all -> 0x008b }
        r6 = "Got unexpected exception when removing events file: ";	 Catch:{ all -> 0x008b }
    L_0x0036:
        android.util.Log.w(r5, r6, r4);	 Catch:{ all -> 0x008b }
        goto L_0x0081;
    L_0x003a:
        r4 = move-exception;
        goto L_0x0057;
    L_0x003c:
        r4 = move-exception;
        r5 = TAG;	 Catch:{ all -> 0x003a }
        r6 = "Got unexpected exception while reading events: ";	 Catch:{ all -> 0x003a }
        android.util.Log.w(r5, r6, r4);	 Catch:{ all -> 0x003a }
        com.facebook.internal.Utility.closeQuietly(r1);	 Catch:{ all -> 0x008b }
        r4 = "AppEventsLogger.persistedevents";	 Catch:{ Exception -> 0x0051 }
        r4 = r3.getFileStreamPath(r4);	 Catch:{ Exception -> 0x0051 }
        r4.delete();	 Catch:{ Exception -> 0x0051 }
        goto L_0x0030;
    L_0x0051:
        r4 = move-exception;
        r5 = TAG;	 Catch:{ all -> 0x008b }
        r6 = "Got unexpected exception when removing events file: ";	 Catch:{ all -> 0x008b }
        goto L_0x0036;	 Catch:{ all -> 0x008b }
    L_0x0057:
        com.facebook.internal.Utility.closeQuietly(r1);	 Catch:{ all -> 0x008b }
        r5 = "AppEventsLogger.persistedevents";	 Catch:{ Exception -> 0x0064 }
        r5 = r3.getFileStreamPath(r5);	 Catch:{ Exception -> 0x0064 }
        r5.delete();	 Catch:{ Exception -> 0x0064 }
        goto L_0x006c;
    L_0x0064:
        r5 = move-exception;
        r6 = TAG;	 Catch:{ all -> 0x008b }
        r7 = "Got unexpected exception when removing events file: ";	 Catch:{ all -> 0x008b }
        android.util.Log.w(r6, r7, r5);	 Catch:{ all -> 0x008b }
    L_0x006c:
        throw r4;	 Catch:{ all -> 0x008b }
    L_0x006d:
        r4 = move-exception;	 Catch:{ all -> 0x008b }
        com.facebook.internal.Utility.closeQuietly(r1);	 Catch:{ all -> 0x008b }
        r4 = "AppEventsLogger.persistedevents";	 Catch:{ Exception -> 0x007b }
        r4 = r3.getFileStreamPath(r4);	 Catch:{ Exception -> 0x007b }
        r4.delete();	 Catch:{ Exception -> 0x007b }
        goto L_0x0030;
    L_0x007b:
        r4 = move-exception;
        r5 = TAG;	 Catch:{ all -> 0x008b }
        r6 = "Got unexpected exception when removing events file: ";	 Catch:{ all -> 0x008b }
        goto L_0x0036;	 Catch:{ all -> 0x008b }
    L_0x0081:
        if (r2 != 0) goto L_0x0089;	 Catch:{ all -> 0x008b }
    L_0x0083:
        r4 = new com.facebook.appevents.PersistedEvents;	 Catch:{ all -> 0x008b }
        r4.<init>();	 Catch:{ all -> 0x008b }
        r2 = r4;
    L_0x0089:
        monitor-exit(r0);
        return r2;
    L_0x008b:
        r1 = move-exception;
        monitor-exit(r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.AppEventStore.readAndClearStore():com.facebook.appevents.PersistedEvents");
    }

    private static void saveEventsToDisk(PersistedEvents eventsToPersist) {
        ObjectOutputStream oos = null;
        Context context = FacebookSdk.getApplicationContext();
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput(PERSISTED_EVENTS_FILENAME, 0)));
            oos.writeObject(eventsToPersist);
        } catch (Exception e) {
            Log.w(TAG, "Got unexpected exception while persisting events: ", e);
            try {
                context.getFileStreamPath(PERSISTED_EVENTS_FILENAME).delete();
            } catch (Exception e2) {
            }
        } catch (Throwable th) {
            Utility.closeQuietly(oos);
        }
        Utility.closeQuietly(oos);
    }

    private static void assertIsNotMainThread() {
    }
}
