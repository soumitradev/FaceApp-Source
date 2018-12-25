package com.google.gson.internal;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Excluder implements TypeAdapterFactory, Cloneable {
    public static final Excluder DEFAULT = new Excluder();
    private static final double IGNORE_VERSIONS = -1.0d;
    private List<ExclusionStrategy> deserializationStrategies = Collections.emptyList();
    private int modifiers = 136;
    private boolean requireExpose;
    private List<ExclusionStrategy> serializationStrategies = Collections.emptyList();
    private boolean serializeInnerClasses = true;
    private double version = IGNORE_VERSIONS;

    protected Excluder clone() {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public Excluder withVersion(double ignoreVersionsAfter) {
        Excluder result = clone();
        result.version = ignoreVersionsAfter;
        return result;
    }

    public Excluder withModifiers(int... modifiers) {
        Excluder result = clone();
        int i = 0;
        result.modifiers = 0;
        int length = modifiers.length;
        while (i < length) {
            result.modifiers |= modifiers[i];
            i++;
        }
        return result;
    }

    public Excluder disableInnerClassSerialization() {
        Excluder result = clone();
        result.serializeInnerClasses = false;
        return result;
    }

    public Excluder excludeFieldsWithoutExposeAnnotation() {
        Excluder result = clone();
        result.requireExpose = true;
        return result;
    }

    public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean serialization, boolean deserialization) {
        Excluder result = clone();
        if (serialization) {
            result.serializationStrategies = new ArrayList(this.serializationStrategies);
            result.serializationStrategies.add(exclusionStrategy);
        }
        if (deserialization) {
            result.deserializationStrategies = new ArrayList(this.deserializationStrategies);
            result.deserializationStrategies.add(exclusionStrategy);
        }
        return result;
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<?> rawType = type.getRawType();
        boolean skipSerialize = excludeClass(rawType, true);
        boolean skipDeserialize = excludeClass(rawType, false);
        if (!skipSerialize && !skipDeserialize) {
            return null;
        }
        final boolean z = skipDeserialize;
        final boolean z2 = skipSerialize;
        final Gson gson2 = gson;
        final TypeToken<T> typeToken = type;
        return new TypeAdapter<T>() {
            private TypeAdapter<T> delegate;

            public T read(JsonReader in) throws IOException {
                if (!z) {
                    return delegate().read(in);
                }
                in.skipValue();
                return null;
            }

            public void write(JsonWriter out, T value) throws IOException {
                if (z2) {
                    out.nullValue();
                } else {
                    delegate().write(out, value);
                }
            }

            private TypeAdapter<T> delegate() {
                TypeAdapter<T> d = this.delegate;
                if (d != null) {
                    return d;
                }
                TypeAdapter<T> delegateAdapter = gson2.getDelegateAdapter(Excluder.this, typeToken);
                this.delegate = delegateAdapter;
                return delegateAdapter;
            }
        };
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean excludeField(java.lang.reflect.Field r7, boolean r8) {
        /*
        r6 = this;
        r0 = r6.modifiers;
        r1 = r7.getModifiers();
        r0 = r0 & r1;
        r1 = 1;
        if (r0 == 0) goto L_0x000b;
    L_0x000a:
        return r1;
    L_0x000b:
        r2 = r6.version;
        r4 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x002a;
    L_0x0013:
        r0 = com.google.gson.annotations.Since.class;
        r0 = r7.getAnnotation(r0);
        r0 = (com.google.gson.annotations.Since) r0;
        r2 = com.google.gson.annotations.Until.class;
        r2 = r7.getAnnotation(r2);
        r2 = (com.google.gson.annotations.Until) r2;
        r0 = r6.isValidVersion(r0, r2);
        if (r0 != 0) goto L_0x002a;
    L_0x0029:
        return r1;
    L_0x002a:
        r0 = r7.isSynthetic();
        if (r0 == 0) goto L_0x0031;
    L_0x0030:
        return r1;
    L_0x0031:
        r0 = r6.requireExpose;
        if (r0 == 0) goto L_0x004f;
    L_0x0035:
        r0 = com.google.gson.annotations.Expose.class;
        r0 = r7.getAnnotation(r0);
        r0 = (com.google.gson.annotations.Expose) r0;
        if (r0 == 0) goto L_0x004e;
    L_0x003f:
        if (r8 == 0) goto L_0x0048;
    L_0x0041:
        r2 = r0.serialize();
        if (r2 != 0) goto L_0x004f;
    L_0x0047:
        goto L_0x004e;
    L_0x0048:
        r2 = r0.deserialize();
        if (r2 != 0) goto L_0x004f;
    L_0x004e:
        return r1;
    L_0x004f:
        r0 = r6.serializeInnerClasses;
        if (r0 != 0) goto L_0x005e;
    L_0x0053:
        r0 = r7.getType();
        r0 = r6.isInnerClass(r0);
        if (r0 == 0) goto L_0x005e;
    L_0x005d:
        return r1;
    L_0x005e:
        r0 = r7.getType();
        r0 = r6.isAnonymousOrLocal(r0);
        if (r0 == 0) goto L_0x0069;
    L_0x0068:
        return r1;
    L_0x0069:
        if (r8 == 0) goto L_0x006e;
    L_0x006b:
        r0 = r6.serializationStrategies;
        goto L_0x0070;
    L_0x006e:
        r0 = r6.deserializationStrategies;
    L_0x0070:
        r2 = r0.isEmpty();
        if (r2 != 0) goto L_0x0093;
    L_0x0076:
        r2 = new com.google.gson.FieldAttributes;
        r2.<init>(r7);
        r3 = r0.iterator();
    L_0x007f:
        r4 = r3.hasNext();
        if (r4 == 0) goto L_0x0093;
    L_0x0085:
        r4 = r3.next();
        r4 = (com.google.gson.ExclusionStrategy) r4;
        r5 = r4.shouldSkipField(r2);
        if (r5 == 0) goto L_0x0092;
    L_0x0091:
        return r1;
    L_0x0092:
        goto L_0x007f;
    L_0x0093:
        r1 = 0;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.Excluder.excludeField(java.lang.reflect.Field, boolean):boolean");
    }

    public boolean excludeClass(Class<?> clazz, boolean serialize) {
        if (this.version != IGNORE_VERSIONS && !isValidVersion((Since) clazz.getAnnotation(Since.class), (Until) clazz.getAnnotation(Until.class))) {
            return true;
        }
        if ((!this.serializeInnerClasses && isInnerClass(clazz)) || isAnonymousOrLocal(clazz)) {
            return true;
        }
        for (ExclusionStrategy exclusionStrategy : serialize ? this.serializationStrategies : this.deserializationStrategies) {
            if (exclusionStrategy.shouldSkipClass(clazz)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnonymousOrLocal(Class<?> clazz) {
        return !Enum.class.isAssignableFrom(clazz) && (clazz.isAnonymousClass() || clazz.isLocalClass());
    }

    private boolean isInnerClass(Class<?> clazz) {
        return clazz.isMemberClass() && !isStatic(clazz);
    }

    private boolean isStatic(Class<?> clazz) {
        return (clazz.getModifiers() & 8) != 0;
    }

    private boolean isValidVersion(Since since, Until until) {
        return isValidSince(since) && isValidUntil(until);
    }

    private boolean isValidSince(Since annotation) {
        if (annotation == null || annotation.value() <= this.version) {
            return true;
        }
        return false;
    }

    private boolean isValidUntil(Until annotation) {
        if (annotation == null || annotation.value() > this.version) {
            return true;
        }
        return false;
    }
}
