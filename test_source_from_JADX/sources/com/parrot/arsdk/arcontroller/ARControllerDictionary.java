package com.parrot.arsdk.arcontroller;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ARControllerDictionary implements Map<String, ARControllerArgumentDictionary<Object>> {
    public static String ARCONTROLLER_DICTIONARY_SINGLE_KEY;
    private static String TAG = "ARControllerDictionary";
    private boolean initOk = false;
    private long jniDictionary;

    private native long[] nativeGetAllElements(long j);

    private native long nativeGetArg(long j, String str);

    private native int nativeGetArgType(long j);

    private native double nativeGetArgValueDouble(long j);

    private native int nativeGetArgValueInt(long j);

    private native long nativeGetArgValueLong(long j);

    private native String nativeGetArgValueString(long j);

    private native long nativeGetElement(long j, String str);

    private native int nativeGetSize(long j);

    private static native String nativeStaticGetSingleKey();

    static {
        ARCONTROLLER_DICTIONARY_SINGLE_KEY = "";
        ARCONTROLLER_DICTIONARY_SINGLE_KEY = nativeStaticGetSingleKey();
    }

    public ARControllerDictionary(long nativeDictionary) {
        if (nativeDictionary != 0) {
            this.jniDictionary = nativeDictionary;
            this.initOk = true;
        }
    }

    public void dispose() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                this.jniDictionary = 0;
                this.initOk = false;
            }
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public void clear() {
        synchronized (this) {
            boolean z = this.initOk;
        }
    }

    public boolean containsKey(Object key) {
        boolean ret = false;
        synchronized (this) {
            boolean z = true;
            if (this.initOk && (key instanceof String)) {
                if (nativeGetElement(this.jniDictionary, (String) key) == 0) {
                    z = false;
                }
                ret = z;
            }
        }
        return ret;
    }

    public boolean containsValue(Object value) {
        synchronized (this) {
            if (this.initOk) {
                boolean z = value instanceof String;
            }
        }
        if (0 != 0) {
            return true;
        }
        return false;
    }

    @NonNull
    public Set<Entry<String, ARControllerArgumentDictionary<Object>>> entrySet() {
        return null;
    }

    public ARControllerArgumentDictionary<Object> get(Object key) {
        ARControllerArgumentDictionary<Object> ret = null;
        synchronized (this) {
            if (this.initOk && (key instanceof String)) {
                ret = new ARControllerArgumentDictionary(nativeGetElement(this.jniDictionary, (String) key));
            }
        }
        return ret;
    }

    public boolean isEmpty() {
        boolean ret = true;
        synchronized (this) {
            boolean z = true;
            if (this.initOk) {
                if (nativeGetSize(this.jniDictionary) != 0) {
                    z = false;
                }
                ret = z;
            }
        }
        return ret;
    }

    @NonNull
    public Set<String> keySet() {
        return null;
    }

    public ARControllerArgumentDictionary<Object> put(String key, ARControllerArgumentDictionary<Object> aRControllerArgumentDictionary) {
        return null;
    }

    public void putAll(Map<? extends String, ? extends ARControllerArgumentDictionary<Object>> map) {
    }

    public ARControllerArgumentDictionary<Object> remove(Object key) {
        return null;
    }

    public int size() {
        int size = 0;
        synchronized (this) {
            if (this.initOk) {
                size = nativeGetSize(this.jniDictionary);
            }
        }
        return size;
    }

    @NonNull
    public Collection<ARControllerArgumentDictionary<Object>> values() {
        Collection<ARControllerArgumentDictionary<Object>> elements = null;
        synchronized (this) {
            if (this.initOk) {
                long[] nativeElements = nativeGetAllElements(this.jniDictionary);
                if (nativeElements != null) {
                    elements = new ArrayList(nativeElements.length);
                    for (long element : nativeElements) {
                        elements.add(new ARControllerArgumentDictionary(element));
                    }
                }
            }
        }
        return elements;
    }
}
