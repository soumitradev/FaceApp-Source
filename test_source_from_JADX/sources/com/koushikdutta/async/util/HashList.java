package com.koushikdutta.async.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class HashList<T> {
    Hashtable<String, TaggedList<T>> internal = new Hashtable();

    public Set<String> keySet() {
        return this.internal.keySet();
    }

    public synchronized <V> V tag(String key) {
        TaggedList<T> list = (TaggedList) this.internal.get(key);
        if (list == null) {
            return null;
        }
        return list.tag();
    }

    public synchronized <V> void tag(String key, V tag) {
        TaggedList<T> list = (TaggedList) this.internal.get(key);
        if (list == null) {
            list = new TaggedList();
            this.internal.put(key, list);
        }
        list.tag(tag);
    }

    public synchronized ArrayList<T> remove(String key) {
        return (ArrayList) this.internal.remove(key);
    }

    public synchronized int size() {
        return this.internal.size();
    }

    public synchronized ArrayList<T> get(String key) {
        return (ArrayList) this.internal.get(key);
    }

    public synchronized boolean contains(String key) {
        boolean z;
        ArrayList<T> check = get(key);
        z = check != null && check.size() > 0;
        return z;
    }

    public synchronized void add(String key, T value) {
        ArrayList<T> ret = get(key);
        if (ret == null) {
            ArrayList<T> put = new TaggedList();
            ret = put;
            this.internal.put(key, put);
        }
        ret.add(value);
    }

    public synchronized T pop(String key) {
        TaggedList<T> values = (TaggedList) this.internal.get(key);
        if (values == null) {
            return null;
        }
        if (values.size() == 0) {
            return null;
        }
        return values.remove(values.size() - 1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean removeItem(java.lang.String r4, T r5) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.internal;	 Catch:{ all -> 0x001b }
        r0 = r0.get(r4);	 Catch:{ all -> 0x001b }
        r0 = (com.koushikdutta.async.util.TaggedList) r0;	 Catch:{ all -> 0x001b }
        r1 = 0;
        if (r0 != 0) goto L_0x000e;
    L_0x000c:
        monitor-exit(r3);
        return r1;
    L_0x000e:
        r0.remove(r5);	 Catch:{ all -> 0x001b }
        r2 = r0.size();	 Catch:{ all -> 0x001b }
        if (r2 != 0) goto L_0x0019;
    L_0x0017:
        r1 = 1;
    L_0x0019:
        monitor-exit(r3);
        return r1;
    L_0x001b:
        r4 = move-exception;
        monitor-exit(r3);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.util.HashList.removeItem(java.lang.String, java.lang.Object):boolean");
    }
}
