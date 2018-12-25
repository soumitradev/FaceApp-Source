package com.crashlytics.android.answers;

import java.util.HashSet;
import java.util.Set;

class SamplingEventFilter implements EventFilter {
    static final Set<Type> EVENTS_TYPE_TO_SAMPLE = new C03681();
    final int samplingRate;

    /* renamed from: com.crashlytics.android.answers.SamplingEventFilter$1 */
    static class C03681 extends HashSet<Type> {
        C03681() {
            add(Type.START);
            add(Type.RESUME);
            add(Type.PAUSE);
            add(Type.STOP);
        }
    }

    public SamplingEventFilter(int samplingRate) {
        this.samplingRate = samplingRate;
    }

    public boolean skipEvent(SessionEvent sessionEvent) {
        boolean canBeSampled = EVENTS_TYPE_TO_SAMPLE.contains(sessionEvent.type) && sessionEvent.sessionEventMetadata.betaDeviceToken == null;
        boolean isSampledId = Math.abs(sessionEvent.sessionEventMetadata.installationId.hashCode() % this.samplingRate) != 0;
        if (canBeSampled && isSampledId) {
            return true;
        }
        return false;
    }
}
