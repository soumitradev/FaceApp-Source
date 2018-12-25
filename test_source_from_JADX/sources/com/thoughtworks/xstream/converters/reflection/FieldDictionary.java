package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.core.Caching;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FieldDictionary implements Caching {
    private transient Map keyedByFieldKeyCache;
    private transient Map keyedByFieldNameCache;
    private final FieldKeySorter sorter;

    private java.util.Map buildMap(java.lang.Class r19, boolean r20) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.fixSplitterBlock(BlockFinish.java:63)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:34)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r18 = this;
        r1 = r18;
        r2 = r19;
        r3 = r2;
        monitor-enter(r18);
        r4 = r1.keyedByFieldNameCache;	 Catch:{ all -> 0x0102 }
        r4 = r4.containsKey(r2);	 Catch:{ all -> 0x0102 }
        if (r4 != 0) goto L_0x00f2;	 Catch:{ all -> 0x0102 }
    L_0x000e:
        r4 = new java.util.ArrayList;	 Catch:{ all -> 0x0102 }
        r4.<init>();	 Catch:{ all -> 0x0102 }
    L_0x0013:
        r5 = java.lang.Object.class;	 Catch:{ all -> 0x0102 }
        r5 = r5.equals(r3);	 Catch:{ all -> 0x0102 }
        r6 = 0;	 Catch:{ all -> 0x0102 }
        if (r5 != 0) goto L_0x0027;	 Catch:{ all -> 0x0102 }
    L_0x001c:
        if (r3 == 0) goto L_0x0027;	 Catch:{ all -> 0x0102 }
    L_0x001e:
        r4.add(r6, r3);	 Catch:{ all -> 0x0102 }
        r5 = r3.getSuperclass();	 Catch:{ all -> 0x0102 }
        r3 = r5;	 Catch:{ all -> 0x0102 }
        goto L_0x0013;	 Catch:{ all -> 0x0102 }
    L_0x0027:
        r5 = java.util.Collections.EMPTY_MAP;	 Catch:{ all -> 0x0102 }
        r7 = java.util.Collections.EMPTY_MAP;	 Catch:{ all -> 0x0102 }
        r8 = r4.iterator();	 Catch:{ all -> 0x0102 }
    L_0x002f:
        r9 = r8.hasNext();	 Catch:{ all -> 0x0102 }
        if (r9 == 0) goto L_0x00e9;	 Catch:{ all -> 0x0102 }
    L_0x0035:
        r9 = r8.next();	 Catch:{ all -> 0x0102 }
        r9 = (java.lang.Class) r9;	 Catch:{ all -> 0x0102 }
        r3 = r9;	 Catch:{ all -> 0x0102 }
        r9 = r1.keyedByFieldNameCache;	 Catch:{ all -> 0x0102 }
        r9 = r9.containsKey(r3);	 Catch:{ all -> 0x0102 }
        if (r9 != 0) goto L_0x00d0;	 Catch:{ all -> 0x0102 }
    L_0x0044:
        r9 = new java.util.HashMap;	 Catch:{ all -> 0x0102 }
        r9.<init>(r5);	 Catch:{ all -> 0x0102 }
        r10 = new com.thoughtworks.xstream.core.util.OrderRetainingMap;	 Catch:{ all -> 0x0102 }
        r10.<init>(r7);	 Catch:{ all -> 0x0102 }
        r11 = r3.getDeclaredFields();	 Catch:{ all -> 0x0102 }
        r12 = com.thoughtworks.xstream.core.JVM.reverseFieldDefinition();	 Catch:{ all -> 0x0102 }
        r13 = 1;	 Catch:{ all -> 0x0102 }
        if (r12 == 0) goto L_0x006d;	 Catch:{ all -> 0x0102 }
    L_0x0059:
        r12 = r11.length;	 Catch:{ all -> 0x0102 }
        r12 = r12 >> r13;	 Catch:{ all -> 0x0102 }
    L_0x005b:
        r14 = r12 + -1;	 Catch:{ all -> 0x0102 }
        if (r12 <= 0) goto L_0x006d;	 Catch:{ all -> 0x0102 }
    L_0x005f:
        r12 = r11.length;	 Catch:{ all -> 0x0102 }
        r12 = r12 - r14;	 Catch:{ all -> 0x0102 }
        r12 = r12 - r13;	 Catch:{ all -> 0x0102 }
        r15 = r11[r14];	 Catch:{ all -> 0x0102 }
        r16 = r11[r12];	 Catch:{ all -> 0x0102 }
        r11[r14] = r16;	 Catch:{ all -> 0x0102 }
        r11[r12] = r15;	 Catch:{ all -> 0x0102 }
        r12 = r14;	 Catch:{ all -> 0x0102 }
        goto L_0x005b;	 Catch:{ all -> 0x0102 }
    L_0x006d:
        r12 = 0;	 Catch:{ all -> 0x0102 }
    L_0x006e:
        r14 = r11.length;	 Catch:{ all -> 0x0102 }
        if (r12 >= r14) goto L_0x00bb;	 Catch:{ all -> 0x0102 }
    L_0x0071:
        r14 = r11[r12];	 Catch:{ all -> 0x0102 }
        r15 = r14.isAccessible();	 Catch:{ all -> 0x0102 }
        if (r15 != 0) goto L_0x007c;	 Catch:{ all -> 0x0102 }
    L_0x0079:
        r14.setAccessible(r13);	 Catch:{ all -> 0x0102 }
    L_0x007c:
        r6 = new com.thoughtworks.xstream.converters.reflection.FieldKey;	 Catch:{ all -> 0x0102 }
        r13 = r14.getName();	 Catch:{ all -> 0x0102 }
        r17 = r4;	 Catch:{ all -> 0x0102 }
        r4 = r14.getDeclaringClass();	 Catch:{ all -> 0x0102 }
        r6.<init>(r13, r4, r12);	 Catch:{ all -> 0x0102 }
        r4 = r6;	 Catch:{ all -> 0x0102 }
        r6 = r14.getName();	 Catch:{ all -> 0x0102 }
        r6 = r9.get(r6);	 Catch:{ all -> 0x0102 }
        r6 = (java.lang.reflect.Field) r6;	 Catch:{ all -> 0x0102 }
        if (r6 == 0) goto L_0x00aa;	 Catch:{ all -> 0x0102 }
    L_0x0098:
        r13 = r6.getModifiers();	 Catch:{ all -> 0x0102 }
        r13 = r13 & 8;	 Catch:{ all -> 0x0102 }
        if (r13 != 0) goto L_0x00aa;	 Catch:{ all -> 0x0102 }
    L_0x00a0:
        if (r6 == 0) goto L_0x00b1;	 Catch:{ all -> 0x0102 }
    L_0x00a2:
        r13 = r14.getModifiers();	 Catch:{ all -> 0x0102 }
        r13 = r13 & 8;	 Catch:{ all -> 0x0102 }
        if (r13 != 0) goto L_0x00b1;	 Catch:{ all -> 0x0102 }
    L_0x00aa:
        r13 = r14.getName();	 Catch:{ all -> 0x0102 }
        r9.put(r13, r14);	 Catch:{ all -> 0x0102 }
    L_0x00b1:
        r10.put(r4, r14);	 Catch:{ all -> 0x0102 }
        r12 = r12 + 1;	 Catch:{ all -> 0x0102 }
        r4 = r17;	 Catch:{ all -> 0x0102 }
        r6 = 0;	 Catch:{ all -> 0x0102 }
        r13 = 1;	 Catch:{ all -> 0x0102 }
        goto L_0x006e;	 Catch:{ all -> 0x0102 }
    L_0x00bb:
        r17 = r4;	 Catch:{ all -> 0x0102 }
        r4 = r1.sorter;	 Catch:{ all -> 0x0102 }
        r4 = r4.sort(r3, r10);	 Catch:{ all -> 0x0102 }
        r6 = r1.keyedByFieldNameCache;	 Catch:{ all -> 0x0102 }
        r6.put(r3, r9);	 Catch:{ all -> 0x0102 }
        r6 = r1.keyedByFieldKeyCache;	 Catch:{ all -> 0x0102 }
        r6.put(r3, r4);	 Catch:{ all -> 0x0102 }
        r5 = r9;	 Catch:{ all -> 0x0102 }
        r7 = r4;	 Catch:{ all -> 0x0102 }
        goto L_0x00e4;	 Catch:{ all -> 0x0102 }
    L_0x00d0:
        r17 = r4;	 Catch:{ all -> 0x0102 }
        r4 = r1.keyedByFieldNameCache;	 Catch:{ all -> 0x0102 }
        r4 = r4.get(r3);	 Catch:{ all -> 0x0102 }
        r4 = (java.util.Map) r4;	 Catch:{ all -> 0x0102 }
        r5 = r4;	 Catch:{ all -> 0x0102 }
        r4 = r1.keyedByFieldKeyCache;	 Catch:{ all -> 0x0102 }
        r4 = r4.get(r3);	 Catch:{ all -> 0x0102 }
        r4 = (java.util.Map) r4;	 Catch:{ all -> 0x0102 }
        r7 = r4;	 Catch:{ all -> 0x0102 }
    L_0x00e4:
        r4 = r17;	 Catch:{ all -> 0x0102 }
        r6 = 0;	 Catch:{ all -> 0x0102 }
        goto L_0x002f;	 Catch:{ all -> 0x0102 }
    L_0x00e9:
        r17 = r4;	 Catch:{ all -> 0x0102 }
        if (r20 == 0) goto L_0x00ef;	 Catch:{ all -> 0x0102 }
    L_0x00ed:
        r4 = r7;	 Catch:{ all -> 0x0102 }
        goto L_0x00f0;	 Catch:{ all -> 0x0102 }
    L_0x00ef:
        r4 = r5;	 Catch:{ all -> 0x0102 }
    L_0x00f0:
        monitor-exit(r18);	 Catch:{ all -> 0x0102 }
        return r4;	 Catch:{ all -> 0x0102 }
    L_0x00f2:
        monitor-exit(r18);	 Catch:{ all -> 0x0102 }
        if (r20 == 0) goto L_0x00fc;
    L_0x00f5:
        r4 = r1.keyedByFieldKeyCache;
    L_0x00f7:
        r4 = r4.get(r2);
        goto L_0x00ff;
    L_0x00fc:
        r4 = r1.keyedByFieldNameCache;
        goto L_0x00f7;
    L_0x00ff:
        r4 = (java.util.Map) r4;
        return r4;
    L_0x0102:
        r0 = move-exception;
        r4 = r3;
    L_0x0104:
        r3 = r0;
        monitor-exit(r18);	 Catch:{ all -> 0x0107 }
        throw r3;
    L_0x0107:
        r0 = move-exception;
        goto L_0x0104;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thoughtworks.xstream.converters.reflection.FieldDictionary.buildMap(java.lang.Class, boolean):java.util.Map");
    }

    public FieldDictionary() {
        this(new ImmutableFieldKeySorter());
    }

    public FieldDictionary(FieldKeySorter sorter) {
        this.sorter = sorter;
        init();
    }

    private void init() {
        this.keyedByFieldNameCache = new HashMap();
        this.keyedByFieldKeyCache = new HashMap();
        this.keyedByFieldNameCache.put(Object.class, Collections.EMPTY_MAP);
        this.keyedByFieldKeyCache.put(Object.class, Collections.EMPTY_MAP);
    }

    public Iterator serializableFieldsFor(Class cls) {
        return fieldsFor(cls);
    }

    public Iterator fieldsFor(Class cls) {
        return buildMap(cls, true).values().iterator();
    }

    public Field field(Class cls, String name, Class definedIn) {
        Field field = fieldOrNull(cls, name, definedIn);
        if (field != null) {
            return field;
        }
        throw new MissingFieldException(cls.getName(), name);
    }

    public Field fieldOrNull(Class cls, String name, Class definedIn) {
        return (Field) buildMap(cls, definedIn != null ? true : null).get(definedIn != null ? new FieldKey(name, definedIn, -1) : name);
    }

    public synchronized void flushCache() {
        Set objectTypeSet = Collections.singleton(Object.class);
        this.keyedByFieldNameCache.keySet().retainAll(objectTypeSet);
        this.keyedByFieldKeyCache.keySet().retainAll(objectTypeSet);
        if (this.sorter instanceof Caching) {
            ((Caching) this.sorter).flushCache();
        }
    }

    protected Object readResolve() {
        init();
        return this;
    }
}
