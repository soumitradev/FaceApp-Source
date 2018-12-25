package com.thoughtworks.xstream.security;

public class CGLIBProxyTypePermission implements TypePermission {
    public static final TypePermission PROXIES = new CGLIBProxyTypePermission();

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean allows(java.lang.Class r4) {
        /*
        r3 = this;
        if (r4 == 0) goto L_0x0039;
    L_0x0002:
        r0 = java.lang.Object.class;
        if (r4 == r0) goto L_0x0039;
    L_0x0006:
        r0 = r4.isInterface();
        if (r0 != 0) goto L_0x0039;
    L_0x000c:
        r0 = net.sf.cglib.proxy.Proxy.isProxyClass(r4);
        if (r0 != 0) goto L_0x0037;
    L_0x0012:
        r0 = r4.getName();
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = net.sf.cglib.proxy.Proxy.class;
        r2 = r2.getPackage();
        r2 = r2.getName();
        r1.append(r2);
        r2 = ".";
        r1.append(r2);
        r1 = r1.toString();
        r0 = r0.startsWith(r1);
        if (r0 == 0) goto L_0x0039;
    L_0x0037:
        r0 = 1;
        goto L_0x003a;
    L_0x0039:
        r0 = 0;
    L_0x003a:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thoughtworks.xstream.security.CGLIBProxyTypePermission.allows(java.lang.Class):boolean");
    }

    public int hashCode() {
        return 19;
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == CGLIBProxyTypePermission.class;
    }
}
