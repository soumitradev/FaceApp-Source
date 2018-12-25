package com.google.android.gms.internal;

import java.util.Comparator;

public final class zzeek<A extends Comparable<A>> implements Comparator<A> {
    private static zzeek zza = new zzeek();

    private zzeek() {
    }

    public static <T extends Comparable<T>> zzeek<T> zza(Class<T> cls) {
        return zza;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((Comparable) obj).compareTo((Comparable) obj2);
    }
}
