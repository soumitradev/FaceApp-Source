package com.thoughtworks.xstream.core.util;

import java.lang.reflect.Constructor;
import java.util.Comparator;

public class DependencyInjectionFactory {

    /* renamed from: com.thoughtworks.xstream.core.util.DependencyInjectionFactory$1 */
    static class C16831 implements Comparator {
        C16831() {
        }

        public int compare(Object o1, Object o2) {
            return ((Constructor) o2).getParameterTypes().length - ((Constructor) o1).getParameterTypes().length;
        }
    }

    private static class TypedValue {
        final Class type;
        final Object value;

        public TypedValue(Class type, Object value) {
            this.type = type;
            this.value = value;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.type.getName());
            stringBuilder.append(":");
            stringBuilder.append(this.value);
            return stringBuilder.toString();
        }
    }

    public static Object newInstance(Class type, Object[] dependencies) {
        return newInstance(type, dependencies, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object newInstance(java.lang.Class r34, java.lang.Object[] r35, java.util.BitSet r36) {
        /*
        r1 = r35;
        r2 = r36;
        if (r1 == 0) goto L_0x0013;
    L_0x0006:
        r3 = r1.length;
        r4 = 63;
        if (r3 <= r4) goto L_0x0013;
    L_0x000b:
        r3 = new java.lang.IllegalArgumentException;
        r4 = "More than 63 arguments are not supported";
        r3.<init>(r4);
        throw r3;
    L_0x0013:
        r3 = 0;
        r4 = new java.util.ArrayList;
        r4.<init>();
        r5 = 0;
        r6 = 0;
        r8 = 0;
        r12 = 1;
        if (r1 == 0) goto L_0x0201;
    L_0x0021:
        r13 = r1.length;
        if (r13 <= 0) goto L_0x0201;
    L_0x0024:
        r13 = r34.getConstructors();
        r14 = r13.length;
        if (r14 <= r12) goto L_0x0033;
    L_0x002b:
        r14 = new com.thoughtworks.xstream.core.util.DependencyInjectionFactory$1;
        r14.<init>();
        java.util.Arrays.sort(r13, r14);
    L_0x0033:
        r14 = r1.length;
        r14 = new com.thoughtworks.xstream.core.util.DependencyInjectionFactory.TypedValue[r14];
        r16 = 0;
    L_0x0038:
        r17 = r16;
        r12 = r1.length;
        r15 = r17;
        if (r15 >= r12) goto L_0x0067;
    L_0x003f:
        r12 = r1[r15];
        r10 = r12.getClass();
        r11 = r10.isPrimitive();
        if (r11 == 0) goto L_0x0050;
    L_0x004b:
        r10 = com.thoughtworks.xstream.core.util.Primitives.box(r10);
        goto L_0x005c;
    L_0x0050:
        r11 = com.thoughtworks.xstream.core.util.TypedNull.class;
        if (r10 != r11) goto L_0x005c;
    L_0x0054:
        r11 = r12;
        r11 = (com.thoughtworks.xstream.core.util.TypedNull) r11;
        r10 = r11.getType();
        r12 = 0;
    L_0x005c:
        r11 = new com.thoughtworks.xstream.core.util.DependencyInjectionFactory$TypedValue;
        r11.<init>(r10, r12);
        r14[r15] = r11;
        r16 = r15 + 1;
        r12 = 1;
        goto L_0x0038;
    L_0x0067:
        r10 = 0;
        r11 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r12 = r5;
        r5 = r3;
        r3 = 0;
    L_0x006e:
        if (r5 != 0) goto L_0x01c5;
    L_0x0070:
        r15 = r13.length;
        if (r3 >= r15) goto L_0x01c5;
    L_0x0073:
        r15 = r13[r3];
        r21 = r5;
        r5 = r15.getParameterTypes();
        r22 = r6;
        r6 = r5.length;
        r7 = r1.length;
        if (r6 <= r7) goto L_0x0082;
    L_0x0081:
        goto L_0x009b;
    L_0x0082:
        r6 = r5.length;
        if (r6 != 0) goto L_0x0095;
    L_0x0085:
        if (r10 != 0) goto L_0x008f;
    L_0x0087:
        r6 = r15;
        r3 = r6;
        r31 = r13;
        r19 = 1;
        goto L_0x01cf;
    L_0x008f:
        r31 = r13;
        r19 = 1;
        goto L_0x01cd;
    L_0x0095:
        r6 = r5.length;
        if (r11 <= r6) goto L_0x00a6;
    L_0x0098:
        if (r10 == 0) goto L_0x00a5;
    L_0x009b:
        r31 = r13;
        r5 = r21;
        r6 = r22;
        r19 = 1;
        goto L_0x01bd;
    L_0x00a5:
        r11 = r5.length;
    L_0x00a6:
        r6 = 0;
    L_0x00a7:
        r7 = r5.length;
        if (r6 >= r7) goto L_0x00bd;
    L_0x00aa:
        r7 = r5[r6];
        r7 = r7.isPrimitive();
        if (r7 == 0) goto L_0x00ba;
    L_0x00b2:
        r7 = r5[r6];
        r7 = com.thoughtworks.xstream.core.util.Primitives.box(r7);
        r5[r6] = r7;
    L_0x00ba:
        r6 = r6 + 1;
        goto L_0x00a7;
    L_0x00bd:
        r4.clear();
        r6 = 0;
        r16 = 0;
        r32 = r6;
        r7 = r16;
        r16 = r32;
        r6 = 0;
    L_0x00cb:
        r1 = r5.length;
        if (r7 >= r1) goto L_0x0102;
    L_0x00ce:
        r1 = r5.length;
        r1 = r1 + r6;
        r1 = r1 - r7;
        r24 = r11;
        r11 = r14.length;
        if (r1 > r11) goto L_0x0104;
    L_0x00d6:
        r1 = r5[r7];
        r11 = r14[r6];
        r11 = r11.type;
        r1 = r1.isAssignableFrom(r11);
        if (r1 == 0) goto L_0x00fb;
    L_0x00e2:
        r1 = r14[r6];
        r1 = r1.value;
        r4.add(r1);
        r19 = 1;
        r22 = r19 << r6;
        r25 = r16 | r22;
        r7 = r7 + 1;
        r1 = r5.length;
        if (r7 != r1) goto L_0x00f9;
    L_0x00f4:
        r1 = r15;
        r6 = r25;
        goto L_0x0108;
    L_0x00f9:
        r16 = r25;
    L_0x00fb:
        r6 = r6 + 1;
        r11 = r24;
        r1 = r35;
        goto L_0x00cb;
    L_0x0102:
        r24 = r11;
    L_0x0104:
        r6 = r16;
        r1 = r21;
    L_0x0108:
        if (r1 != 0) goto L_0x01b1;
    L_0x010a:
        r11 = 1;
        r27 = r1;
        r1 = r14.length;
        r1 = new com.thoughtworks.xstream.core.util.DependencyInjectionFactory.TypedValue[r1];
        r28 = r6;
        r6 = r1.length;
        r7 = 0;
        java.lang.System.arraycopy(r14, r7, r1, r7, r6);
        r4.clear();
        r16 = 0;
        r6 = 0;
    L_0x011d:
        r7 = r5.length;
        if (r6 >= r7) goto L_0x0190;
    L_0x0120:
        r7 = -1;
        r18 = r7;
        r7 = 0;
    L_0x0124:
        r30 = r11;
        r11 = r1.length;
        if (r7 >= r11) goto L_0x016e;
    L_0x0129:
        r11 = r1[r7];
        if (r11 != 0) goto L_0x0131;
        r31 = r13;
        goto L_0x0167;
    L_0x0131:
        r11 = r1[r7];
        r11 = r11.type;
        r31 = r13;
        r13 = r5[r6];
        if (r11 != r13) goto L_0x013e;
    L_0x013b:
        r18 = r7;
        goto L_0x0170;
    L_0x013e:
        r11 = r5[r6];
        r13 = r1[r7];
        r13 = r13.type;
        r11 = r11.isAssignableFrom(r13);
        if (r11 == 0) goto L_0x0167;
    L_0x014a:
        if (r18 < 0) goto L_0x0164;
    L_0x014c:
        r11 = r1[r18];
        r11 = r11.type;
        r13 = r1[r7];
        r13 = r13.type;
        if (r11 == r13) goto L_0x0167;
    L_0x0156:
        r11 = r1[r18];
        r11 = r11.type;
        r13 = r1[r7];
        r13 = r13.type;
        r11 = r11.isAssignableFrom(r13);
        if (r11 == 0) goto L_0x0167;
    L_0x0164:
        r11 = r7;
        r18 = r11;
    L_0x0167:
        r7 = r7 + 1;
        r11 = r30;
        r13 = r31;
        goto L_0x0124;
    L_0x016e:
        r31 = r13;
    L_0x0170:
        if (r18 < 0) goto L_0x018c;
    L_0x0172:
        r7 = r1[r18];
        r7 = r7.value;
        r4.add(r7);
        r19 = 1;
        r21 = r19 << r18;
        r25 = r16 | r21;
        r7 = 0;
        r1[r18] = r7;
        r6 = r6 + 1;
        r16 = r25;
        r11 = r30;
        r13 = r31;
        r7 = 0;
        goto L_0x011d;
    L_0x018c:
        r19 = 1;
        r11 = 0;
        goto L_0x0196;
    L_0x0190:
        r30 = r11;
        r31 = r13;
        r19 = 1;
    L_0x0196:
        if (r11 == 0) goto L_0x01aa;
    L_0x0198:
        if (r10 == 0) goto L_0x019f;
    L_0x019a:
        r6 = (r16 > r8 ? 1 : (r16 == r8 ? 0 : -1));
        if (r6 < 0) goto L_0x019f;
    L_0x019e:
        goto L_0x01aa;
    L_0x019f:
        r6 = r15;
        r7 = r4.clone();
        r12 = r7;
        r12 = (java.util.List) r12;
        r8 = r16;
        r10 = r6;
    L_0x01aa:
        r6 = r16;
        r11 = r24;
        r5 = r27;
        goto L_0x01bd;
    L_0x01b1:
        r27 = r1;
        r28 = r6;
        r31 = r13;
        r19 = 1;
        r11 = r24;
        r5 = r27;
    L_0x01bd:
        r3 = r3 + 1;
        r13 = r31;
        r1 = r35;
        goto L_0x006e;
    L_0x01c5:
        r21 = r5;
        r22 = r6;
        r31 = r13;
        r19 = 1;
    L_0x01cd:
        r3 = r21;
    L_0x01cf:
        if (r3 != 0) goto L_0x01fe;
    L_0x01d1:
        if (r10 != 0) goto L_0x01f5;
    L_0x01d3:
        r5 = 0;
        r1 = new com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r13 = "Cannot construct ";
        r7.append(r13);
        r13 = r34.getName();
        r7.append(r13);
        r13 = ", none of the dependencies match any constructor's parameters";
        r7.append(r13);
        r7 = r7.toString();
        r1.<init>(r7);
        throw r1;
    L_0x01f5:
        r3 = r10;
        r4.clear();
        r4.addAll(r12);
        r6 = r8;
        goto L_0x0204;
    L_0x01fe:
        r6 = r22;
        goto L_0x0204;
    L_0x0201:
        r19 = 1;
        r12 = r5;
    L_0x0204:
        if (r3 != 0) goto L_0x021c;
    L_0x0206:
        r1 = r34.newInstance();	 Catch:{ InstantiationException -> 0x0218, IllegalAccessException -> 0x0214, InvocationTargetException -> 0x0211, SecurityException -> 0x020e, ExceptionInInitializerError -> 0x020b }
        goto L_0x0224;
    L_0x020b:
        r0 = move-exception;
        r1 = r0;
        goto L_0x0243;
    L_0x020e:
        r0 = move-exception;
        r1 = r0;
        goto L_0x025f;
    L_0x0211:
        r0 = move-exception;
        r1 = r0;
        goto L_0x027b;
    L_0x0214:
        r0 = move-exception;
        r1 = r0;
        goto L_0x0297;
    L_0x0218:
        r0 = move-exception;
        r1 = r0;
        goto L_0x02b3;
    L_0x021c:
        r1 = r4.toArray();	 Catch:{ InstantiationException -> 0x0218, IllegalAccessException -> 0x0214, InvocationTargetException -> 0x0211, SecurityException -> 0x020e, ExceptionInInitializerError -> 0x020b }
        r1 = r3.newInstance(r1);	 Catch:{ InstantiationException -> 0x0218, IllegalAccessException -> 0x0214, InvocationTargetException -> 0x0211, SecurityException -> 0x020e, ExceptionInInitializerError -> 0x020b }
    L_0x0224:
        if (r2 == 0) goto L_0x0242;
    L_0x0226:
        r36.clear();	 Catch:{ InstantiationException -> 0x0218, IllegalAccessException -> 0x0214, InvocationTargetException -> 0x0211, SecurityException -> 0x020e, ExceptionInInitializerError -> 0x020b }
        r5 = 0;
    L_0x022b:
        r10 = r19;
        r13 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1));
        if (r13 >= 0) goto L_0x0242;
    L_0x0231:
        r13 = r6 & r10;
        r15 = 0;
        r17 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1));
        if (r17 <= 0) goto L_0x023c;
    L_0x0239:
        r2.set(r5);	 Catch:{ InstantiationException -> 0x0218, IllegalAccessException -> 0x0214, InvocationTargetException -> 0x0211, SecurityException -> 0x020e, ExceptionInInitializerError -> 0x020b }
    L_0x023c:
        r13 = 1;
        r19 = r10 << r13;
        r5 = r5 + 1;
        goto L_0x022b;
    L_0x0242:
        return r1;
        r5 = new com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Cannot construct ";
        r10.append(r11);
        r11 = r34.getName();
        r10.append(r11);
        r10 = r10.toString();
        r5.<init>(r10, r1);
        throw r5;
        r5 = new com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Cannot construct ";
        r10.append(r11);
        r11 = r34.getName();
        r10.append(r11);
        r10 = r10.toString();
        r5.<init>(r10, r1);
        throw r5;
        r5 = new com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Cannot construct ";
        r10.append(r11);
        r11 = r34.getName();
        r10.append(r11);
        r10 = r10.toString();
        r5.<init>(r10, r1);
        throw r5;
        r5 = new com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Cannot construct ";
        r10.append(r11);
        r11 = r34.getName();
        r10.append(r11);
        r10 = r10.toString();
        r5.<init>(r10, r1);
        throw r5;
        r5 = new com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Cannot construct ";
        r10.append(r11);
        r11 = r34.getName();
        r10.append(r11);
        r10 = r10.toString();
        r5.<init>(r10, r1);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thoughtworks.xstream.core.util.DependencyInjectionFactory.newInstance(java.lang.Class, java.lang.Object[], java.util.BitSet):java.lang.Object");
    }
}
