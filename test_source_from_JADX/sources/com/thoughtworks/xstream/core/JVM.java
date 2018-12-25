package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.core.util.CustomObjectOutputStream;
import com.thoughtworks.xstream.core.util.DependencyInjectionFactory;
import com.thoughtworks.xstream.core.util.PresortedMap;
import com.thoughtworks.xstream.core.util.PresortedSet;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.AttributedString;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

public class JVM implements Caching {
    private static final float DEFAULT_JAVA_VERSION = 1.4f;
    private static final boolean canAllocateWithUnsafe;
    private static final boolean canCreateDerivedObjectOutputStream;
    private static final boolean canParseUTCDateFormat;
    private static final boolean canWriteWithUnsafe;
    private static final boolean isAWTAvailable = (loadClassForName("java.awt.Color", false) != null);
    private static final boolean isSQLAvailable;
    private static final boolean isSwingAvailable = (loadClassForName("javax.swing.LookAndFeel", false) != null);
    private static final float majorJavaVersion = getMajorJavaVersion();
    private static final boolean optimizedTreeMapPutAll;
    private static final boolean optimizedTreeSetAddAll;
    private static final Class reflectionProviderType;
    private static final boolean reverseFieldOrder = false;
    private static final String vendor = System.getProperty("java.vm.vendor");
    private ReflectionProvider reflectionProvider;

    /* renamed from: com.thoughtworks.xstream.core.JVM$1 */
    static class C16801 implements Comparator {
        C16801() {
        }

        public int compare(Object o1, Object o2) {
            throw new RuntimeException();
        }
    }

    static class Test {
        /* renamed from: b */
        private byte f1725b;
        private boolean bool;
        /* renamed from: c */
        private char f1726c;
        /* renamed from: d */
        private double f1727d;
        /* renamed from: f */
        private float f1728f;
        /* renamed from: i */
        private int f1729i;
        /* renamed from: l */
        private long f1730l;
        /* renamed from: o */
        private Object f1731o;
        /* renamed from: s */
        private short f1732s;

        Test() {
            throw new UnsupportedOperationException();
        }
    }

    static {
        Class unsafeClass;
        boolean test;
        Object unsafe = null;
        boolean z = false;
        try {
            unsafeClass = Class.forName("sun.misc.Unsafe");
            Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = unsafeField.get(null);
            Method allocateInstance = unsafeClass.getDeclaredMethod("allocateInstance", new Class[]{Class.class});
            allocateInstance.setAccessible(true);
            test = allocateInstance.invoke(unsafe, new Object[]{Test.class}) != null;
        } catch (Exception e) {
            test = false;
        } catch (Error e2) {
            test = false;
        }
        canAllocateWithUnsafe = test;
        test = false;
        unsafeClass = PureJavaReflectionProvider.class;
        if (canUseSunUnsafeReflectionProvider()) {
            Class cls = loadClassForName("com.thoughtworks.xstream.converters.reflection.SunUnsafeReflectionProvider");
            if (cls != null) {
                try {
                    ReflectionProvider provider = (ReflectionProvider) DependencyInjectionFactory.newInstance(cls, null);
                    Test t = (Test) provider.newInstance(Test.class);
                    try {
                        provider.writeField(t, "o", "object", Test.class);
                        provider.writeField(t, "c", new Character('c'), Test.class);
                        provider.writeField(t, "b", new Byte((byte) 1), Test.class);
                        provider.writeField(t, "s", new Short((short) 1), Test.class);
                        provider.writeField(t, "i", new Integer(1), Test.class);
                        provider.writeField(t, "l", new Long(1), Test.class);
                        provider.writeField(t, "f", new Float(1.0f), Test.class);
                        provider.writeField(t, "d", new Double(1.0d), Test.class);
                        provider.writeField(t, "bool", Boolean.TRUE, Test.class);
                        test = true;
                    } catch (IncompatibleClassChangeError e3) {
                        cls = null;
                    } catch (ObjectAccessException e4) {
                        cls = null;
                    }
                    if (cls == null) {
                        cls = loadClassForName("com.thoughtworks.xstream.converters.reflection.SunLimitedUnsafeReflectionProvider");
                    }
                    unsafeClass = cls;
                } catch (ObjectAccessException e5) {
                }
            }
        }
        reflectionProviderType = unsafeClass;
        canWriteWithUnsafe = test;
        Comparator comparator = new C16801();
        SortedMap map = new PresortedMap(comparator);
        map.put("one", null);
        map.put("two", null);
        try {
            new TreeMap(comparator).putAll(map);
            test = true;
        } catch (RuntimeException e6) {
            test = false;
        }
        optimizedTreeMapPutAll = test;
        SortedSet set = new PresortedSet(comparator);
        set.addAll(map.keySet());
        try {
            new TreeSet(comparator).addAll(set);
            test = true;
        } catch (RuntimeException e7) {
            test = false;
        }
        optimizedTreeSetAddAll = test;
        try {
            new SimpleDateFormat(CompressorStreamFactory.f1737Z).parse("UTC");
            test = true;
        } catch (ParseException e8) {
            test = false;
        }
        canParseUTCDateFormat = test;
        try {
            test = new CustomObjectOutputStream(null) != null;
        } catch (RuntimeException e9) {
            test = false;
        } catch (IOException e10) {
            test = false;
        }
        canCreateDerivedObjectOutputStream = test;
        if (loadClassForName("java.sql.Date") != null) {
            z = true;
        }
        isSQLAvailable = z;
    }

    private static final float getMajorJavaVersion() {
        try {
            return isAndroid() ? 1.5f : Float.parseFloat(System.getProperty("java.specification.version"));
        } catch (NumberFormatException e) {
            return DEFAULT_JAVA_VERSION;
        }
    }

    public static boolean is14() {
        return majorJavaVersion >= DEFAULT_JAVA_VERSION;
    }

    public static boolean is15() {
        return majorJavaVersion >= 1.5f;
    }

    public static boolean is16() {
        return majorJavaVersion >= 1.6f;
    }

    public static boolean is17() {
        return majorJavaVersion >= 1.7f;
    }

    public static boolean is18() {
        return majorJavaVersion >= 1.8f;
    }

    private static boolean isIBM() {
        return vendor.indexOf("IBM") != -1;
    }

    private static boolean isAndroid() {
        return vendor.indexOf("Android") != -1;
    }

    public static Class loadClassForName(String name) {
        return loadClassForName(name, true);
    }

    public Class loadClass(String name) {
        return loadClassForName(name, true);
    }

    public static Class loadClassForName(String name, boolean initialize) {
        try {
            return Class.forName(name, initialize, JVM.class.getClassLoader());
        } catch (LinkageError e) {
            return null;
        } catch (ClassNotFoundException e2) {
            return null;
        }
    }

    public Class loadClass(String name, boolean initialize) {
        return loadClassForName(name, initialize);
    }

    public static ReflectionProvider newReflectionProvider() {
        return (ReflectionProvider) DependencyInjectionFactory.newInstance(reflectionProviderType, null);
    }

    public static ReflectionProvider newReflectionProvider(FieldDictionary dictionary) {
        return (ReflectionProvider) DependencyInjectionFactory.newInstance(reflectionProviderType, new Object[]{dictionary});
    }

    public static Class getStaxInputFactory() throws ClassNotFoundException {
        if (!is16()) {
            return null;
        }
        if (isIBM()) {
            return Class.forName("com.ibm.xml.xlxp.api.stax.XMLInputFactoryImpl");
        }
        return Class.forName("com.sun.xml.internal.stream.XMLInputFactoryImpl");
    }

    public static Class getStaxOutputFactory() throws ClassNotFoundException {
        if (!is16()) {
            return null;
        }
        if (isIBM()) {
            return Class.forName("com.ibm.xml.xlxp.api.stax.XMLOutputFactoryImpl");
        }
        return Class.forName("com.sun.xml.internal.stream.XMLOutputFactoryImpl");
    }

    public synchronized ReflectionProvider bestReflectionProvider() {
        if (this.reflectionProvider == null) {
            this.reflectionProvider = newReflectionProvider();
        }
        return this.reflectionProvider;
    }

    private static boolean canUseSunUnsafeReflectionProvider() {
        return canAllocateWithUnsafe && is14();
    }

    private static boolean canUseSunLimitedUnsafeReflectionProvider() {
        return canWriteWithUnsafe;
    }

    public static boolean reverseFieldDefinition() {
        return false;
    }

    public static boolean isAWTAvailable() {
        return isAWTAvailable;
    }

    public boolean supportsAWT() {
        return isAWTAvailable;
    }

    public static boolean isSwingAvailable() {
        return isSwingAvailable;
    }

    public boolean supportsSwing() {
        return isSwingAvailable;
    }

    public static boolean isSQLAvailable() {
        return isSQLAvailable;
    }

    public boolean supportsSQL() {
        return isSQLAvailable;
    }

    public static boolean hasOptimizedTreeSetAddAll() {
        return optimizedTreeSetAddAll;
    }

    public static boolean hasOptimizedTreeMapPutAll() {
        return optimizedTreeMapPutAll;
    }

    public static boolean canParseUTCDateFormat() {
        return canParseUTCDateFormat;
    }

    public static boolean canCreateDerivedObjectOutputStream() {
        return canCreateDerivedObjectOutputStream;
    }

    public void flushCache() {
    }

    public static void main(String[] args) {
        boolean reverseLocal;
        int i;
        String staxOutputFactory;
        String staxInputFactory;
        PrintStream printStream;
        StringBuilder stringBuilder;
        PrintStream printStream2;
        StringBuilder stringBuilder2;
        boolean reverseJDK = false;
        Field[] fields = AttributedString.class.getDeclaredFields();
        boolean z = false;
        int i2 = 0;
        while (i2 < fields.length) {
            if (fields[i2].getName().equals("text")) {
                reverseJDK = i2 > 3;
                reverseLocal = false;
                fields = Test.class.getDeclaredFields();
                i = 0;
                while (i < fields.length) {
                    if (fields[i].getName().equals("o")) {
                        i++;
                    } else {
                        reverseLocal = i <= 3;
                        staxOutputFactory = null;
                        staxInputFactory = null;
                        staxInputFactory = getStaxInputFactory().getName();
                        staxOutputFactory = getStaxOutputFactory().getName();
                        System.out.println("XStream JVM diagnostics");
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("java.specification.version: ");
                        stringBuilder.append(System.getProperty("java.specification.version"));
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("java.specification.vendor: ");
                        stringBuilder.append(System.getProperty("java.specification.vendor"));
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("java.specification.name: ");
                        stringBuilder.append(System.getProperty("java.specification.name"));
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("java.vm.vendor: ");
                        stringBuilder.append(vendor);
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("java.vendor: ");
                        stringBuilder.append(System.getProperty("java.vendor"));
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("java.vm.name: ");
                        stringBuilder.append(System.getProperty("java.vm.name"));
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Version: ");
                        stringBuilder.append(majorJavaVersion);
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("XStream support for enhanced Mode: ");
                        stringBuilder.append(canUseSunUnsafeReflectionProvider());
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("XStream support for reduced Mode: ");
                        stringBuilder.append(canUseSunLimitedUnsafeReflectionProvider());
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Supports AWT: ");
                        stringBuilder.append(isAWTAvailable());
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Supports Swing: ");
                        stringBuilder.append(isSwingAvailable());
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Supports SQL: ");
                        stringBuilder.append(isSQLAvailable());
                        printStream.println(stringBuilder.toString());
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Java Beans EventHandler present: ");
                        if (loadClassForName("java.beans.EventHandler") != null) {
                            z = true;
                        }
                        stringBuilder.append(z);
                        printStream.println(stringBuilder.toString());
                        printStream2 = System.out;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Standard StAX XMLInputFactory: ");
                        stringBuilder2.append(staxInputFactory);
                        printStream2.println(stringBuilder2.toString());
                        printStream2 = System.out;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Standard StAX XMLOutputFactory: ");
                        stringBuilder2.append(staxOutputFactory);
                        printStream2.println(stringBuilder2.toString());
                        printStream2 = System.out;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Optimized TreeSet.addAll: ");
                        stringBuilder2.append(hasOptimizedTreeSetAddAll());
                        printStream2.println(stringBuilder2.toString());
                        printStream2 = System.out;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Optimized TreeMap.putAll: ");
                        stringBuilder2.append(hasOptimizedTreeMapPutAll());
                        printStream2.println(stringBuilder2.toString());
                        printStream2 = System.out;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Can parse UTC date format: ");
                        stringBuilder2.append(canParseUTCDateFormat());
                        printStream2.println(stringBuilder2.toString());
                        printStream2 = System.out;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Can create derive ObjectOutputStream: ");
                        stringBuilder2.append(canCreateDerivedObjectOutputStream());
                        printStream2.println(stringBuilder2.toString());
                        printStream2 = System.out;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Reverse field order detected for JDK: ");
                        stringBuilder2.append(reverseJDK);
                        printStream2.println(stringBuilder2.toString());
                        printStream2 = System.out;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Reverse field order detected (only if JVM class itself has been compiled): ");
                        stringBuilder2.append(reverseLocal);
                        printStream2.println(stringBuilder2.toString());
                    }
                }
                staxOutputFactory = null;
                staxInputFactory = null;
                staxInputFactory = getStaxInputFactory().getName();
                staxOutputFactory = getStaxOutputFactory().getName();
                System.out.println("XStream JVM diagnostics");
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.specification.version: ");
                stringBuilder.append(System.getProperty("java.specification.version"));
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.specification.vendor: ");
                stringBuilder.append(System.getProperty("java.specification.vendor"));
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.specification.name: ");
                stringBuilder.append(System.getProperty("java.specification.name"));
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.vm.vendor: ");
                stringBuilder.append(vendor);
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.vendor: ");
                stringBuilder.append(System.getProperty("java.vendor"));
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.vm.name: ");
                stringBuilder.append(System.getProperty("java.vm.name"));
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Version: ");
                stringBuilder.append(majorJavaVersion);
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("XStream support for enhanced Mode: ");
                stringBuilder.append(canUseSunUnsafeReflectionProvider());
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("XStream support for reduced Mode: ");
                stringBuilder.append(canUseSunLimitedUnsafeReflectionProvider());
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Supports AWT: ");
                stringBuilder.append(isAWTAvailable());
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Supports Swing: ");
                stringBuilder.append(isSwingAvailable());
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Supports SQL: ");
                stringBuilder.append(isSQLAvailable());
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Java Beans EventHandler present: ");
                if (loadClassForName("java.beans.EventHandler") != null) {
                    z = true;
                }
                stringBuilder.append(z);
                printStream.println(stringBuilder.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Standard StAX XMLInputFactory: ");
                stringBuilder2.append(staxInputFactory);
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Standard StAX XMLOutputFactory: ");
                stringBuilder2.append(staxOutputFactory);
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Optimized TreeSet.addAll: ");
                stringBuilder2.append(hasOptimizedTreeSetAddAll());
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Optimized TreeMap.putAll: ");
                stringBuilder2.append(hasOptimizedTreeMapPutAll());
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Can parse UTC date format: ");
                stringBuilder2.append(canParseUTCDateFormat());
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Can create derive ObjectOutputStream: ");
                stringBuilder2.append(canCreateDerivedObjectOutputStream());
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Reverse field order detected for JDK: ");
                stringBuilder2.append(reverseJDK);
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Reverse field order detected (only if JVM class itself has been compiled): ");
                stringBuilder2.append(reverseLocal);
                printStream2.println(stringBuilder2.toString());
            }
            i2++;
        }
        reverseLocal = false;
        fields = Test.class.getDeclaredFields();
        i = 0;
        while (i < fields.length) {
            if (fields[i].getName().equals("o")) {
                i++;
            } else {
                if (i <= 3) {
                }
                reverseLocal = i <= 3;
                staxOutputFactory = null;
                staxInputFactory = null;
                staxInputFactory = getStaxInputFactory().getName();
                staxOutputFactory = getStaxOutputFactory().getName();
                System.out.println("XStream JVM diagnostics");
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.specification.version: ");
                stringBuilder.append(System.getProperty("java.specification.version"));
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.specification.vendor: ");
                stringBuilder.append(System.getProperty("java.specification.vendor"));
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.specification.name: ");
                stringBuilder.append(System.getProperty("java.specification.name"));
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.vm.vendor: ");
                stringBuilder.append(vendor);
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.vendor: ");
                stringBuilder.append(System.getProperty("java.vendor"));
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("java.vm.name: ");
                stringBuilder.append(System.getProperty("java.vm.name"));
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Version: ");
                stringBuilder.append(majorJavaVersion);
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("XStream support for enhanced Mode: ");
                stringBuilder.append(canUseSunUnsafeReflectionProvider());
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("XStream support for reduced Mode: ");
                stringBuilder.append(canUseSunLimitedUnsafeReflectionProvider());
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Supports AWT: ");
                stringBuilder.append(isAWTAvailable());
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Supports Swing: ");
                stringBuilder.append(isSwingAvailable());
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Supports SQL: ");
                stringBuilder.append(isSQLAvailable());
                printStream.println(stringBuilder.toString());
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Java Beans EventHandler present: ");
                if (loadClassForName("java.beans.EventHandler") != null) {
                    z = true;
                }
                stringBuilder.append(z);
                printStream.println(stringBuilder.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Standard StAX XMLInputFactory: ");
                stringBuilder2.append(staxInputFactory);
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Standard StAX XMLOutputFactory: ");
                stringBuilder2.append(staxOutputFactory);
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Optimized TreeSet.addAll: ");
                stringBuilder2.append(hasOptimizedTreeSetAddAll());
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Optimized TreeMap.putAll: ");
                stringBuilder2.append(hasOptimizedTreeMapPutAll());
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Can parse UTC date format: ");
                stringBuilder2.append(canParseUTCDateFormat());
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Can create derive ObjectOutputStream: ");
                stringBuilder2.append(canCreateDerivedObjectOutputStream());
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Reverse field order detected for JDK: ");
                stringBuilder2.append(reverseJDK);
                printStream2.println(stringBuilder2.toString());
                printStream2 = System.out;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Reverse field order detected (only if JVM class itself has been compiled): ");
                stringBuilder2.append(reverseLocal);
                printStream2.println(stringBuilder2.toString());
            }
        }
        staxOutputFactory = null;
        staxInputFactory = null;
        try {
            staxInputFactory = getStaxInputFactory().getName();
        } catch (ClassNotFoundException e) {
            staxInputFactory = e.getMessage();
        } catch (NullPointerException e2) {
        }
        try {
            staxOutputFactory = getStaxOutputFactory().getName();
        } catch (ClassNotFoundException e3) {
            staxOutputFactory = e3.getMessage();
        } catch (NullPointerException e4) {
        }
        System.out.println("XStream JVM diagnostics");
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("java.specification.version: ");
        stringBuilder.append(System.getProperty("java.specification.version"));
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("java.specification.vendor: ");
        stringBuilder.append(System.getProperty("java.specification.vendor"));
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("java.specification.name: ");
        stringBuilder.append(System.getProperty("java.specification.name"));
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("java.vm.vendor: ");
        stringBuilder.append(vendor);
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("java.vendor: ");
        stringBuilder.append(System.getProperty("java.vendor"));
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("java.vm.name: ");
        stringBuilder.append(System.getProperty("java.vm.name"));
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Version: ");
        stringBuilder.append(majorJavaVersion);
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("XStream support for enhanced Mode: ");
        stringBuilder.append(canUseSunUnsafeReflectionProvider());
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("XStream support for reduced Mode: ");
        stringBuilder.append(canUseSunLimitedUnsafeReflectionProvider());
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Supports AWT: ");
        stringBuilder.append(isAWTAvailable());
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Supports Swing: ");
        stringBuilder.append(isSwingAvailable());
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Supports SQL: ");
        stringBuilder.append(isSQLAvailable());
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Java Beans EventHandler present: ");
        if (loadClassForName("java.beans.EventHandler") != null) {
            z = true;
        }
        stringBuilder.append(z);
        printStream.println(stringBuilder.toString());
        printStream2 = System.out;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Standard StAX XMLInputFactory: ");
        stringBuilder2.append(staxInputFactory);
        printStream2.println(stringBuilder2.toString());
        printStream2 = System.out;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Standard StAX XMLOutputFactory: ");
        stringBuilder2.append(staxOutputFactory);
        printStream2.println(stringBuilder2.toString());
        printStream2 = System.out;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Optimized TreeSet.addAll: ");
        stringBuilder2.append(hasOptimizedTreeSetAddAll());
        printStream2.println(stringBuilder2.toString());
        printStream2 = System.out;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Optimized TreeMap.putAll: ");
        stringBuilder2.append(hasOptimizedTreeMapPutAll());
        printStream2.println(stringBuilder2.toString());
        printStream2 = System.out;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Can parse UTC date format: ");
        stringBuilder2.append(canParseUTCDateFormat());
        printStream2.println(stringBuilder2.toString());
        printStream2 = System.out;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Can create derive ObjectOutputStream: ");
        stringBuilder2.append(canCreateDerivedObjectOutputStream());
        printStream2.println(stringBuilder2.toString());
        printStream2 = System.out;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Reverse field order detected for JDK: ");
        stringBuilder2.append(reverseJDK);
        printStream2.println(stringBuilder2.toString());
        printStream2 = System.out;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Reverse field order detected (only if JVM class itself has been compiled): ");
        stringBuilder2.append(reverseLocal);
        printStream2.println(stringBuilder2.toString());
    }
}
