package com.parrot.arsdk.arcontroller;

import android.support.annotation.NonNull;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ARControllerArgumentDictionary<V> implements Map<String, V> {
    private static String TAG = "ARControllerArgumentDictionary";
    private boolean initOk = false;
    private long jniDictionary;

    private native long nativeGetArg(long j, String str);

    private native int nativeGetArgType(long j);

    private native double nativeGetArgValueDouble(long j);

    private native int nativeGetArgValueInt(long j);

    private native long nativeGetArgValueLong(long j);

    private native String nativeGetArgValueString(long j);

    private native int nativeGetSize(long j);

    private static native void nativeStaticInit();

    public ARControllerArgumentDictionary(long nativeDictionary) {
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
                if (nativeGetArg(this.jniDictionary, (String) key) == 0) {
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
    public Set<Entry<String, V>> entrySet() {
        return null;
    }

    public V get(Object key) {
        V ret = null;
        synchronized (this) {
            if (this.initOk && (key instanceof String)) {
                long nativeArg = nativeGetArg(this.jniDictionary, (String) key);
                if (nativeArg != 0) {
                    int nativeType = nativeGetArgType(nativeArg);
                    switch (ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM.getFromValue(nativeType)) {
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_U8:
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_I8:
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_U16:
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_I16:
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_U32:
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_I32:
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM:
                            ret = Integer.valueOf(nativeGetArgValueInt(nativeArg));
                            break;
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_U64:
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_I64:
                            ret = Long.valueOf(nativeGetArgValueLong(nativeArg));
                            break;
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_FLOAT:
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_DOUBLE:
                            ret = Double.valueOf(nativeGetArgValueDouble(nativeArg));
                            break;
                        case ARCONTROLLER_DICTIONARY_VALUE_TYPE_STRING:
                            ret = nativeGetArgValueString(nativeArg);
                            break;
                        default:
                            String str = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("valueType ");
                            stringBuilder.append(ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM.getFromValue(nativeType));
                            stringBuilder.append(" not known");
                            ARSALPrint.m532e(str, stringBuilder.toString());
                            break;
                    }
                }
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

    public V put(String key, V v) {
        return null;
    }

    public void putAll(Map<? extends String, ? extends V> map) {
    }

    public V remove(Object key) {
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
    public Collection<V> values() {
        return null;
    }
}
