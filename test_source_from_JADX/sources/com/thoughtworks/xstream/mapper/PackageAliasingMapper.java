package com.thoughtworks.xstream.mapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PackageAliasingMapper extends MapperWrapper implements Serializable {
    private static final Comparator REVERSE = new C16931();
    protected transient Map nameToPackage = new HashMap();
    private Map packageToName = new TreeMap(REVERSE);

    /* renamed from: com.thoughtworks.xstream.mapper.PackageAliasingMapper$1 */
    static class C16931 implements Comparator {
        C16931() {
        }

        public int compare(Object o1, Object o2) {
            return ((String) o2).compareTo((String) o1);
        }
    }

    public PackageAliasingMapper(Mapper wrapped) {
        super(wrapped);
    }

    public void addPackageAlias(String name, String pkg) {
        if (name.length() > 0 && name.charAt(name.length() - 1) != '.') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(name);
            stringBuilder.append('.');
            name = stringBuilder.toString();
        }
        if (pkg.length() > 0 && pkg.charAt(pkg.length() - 1) != '.') {
            stringBuilder = new StringBuilder();
            stringBuilder.append(pkg);
            stringBuilder.append('.');
            pkg = stringBuilder.toString();
        }
        this.nameToPackage.put(name, pkg);
        this.packageToName.put(pkg, name);
    }

    public String serializedClass(Class type) {
        String className = type.getName();
        int length = className.length();
        int dot;
        do {
            dot = className.lastIndexOf(46, length);
            String alias = (String) this.packageToName.get(dot < 0 ? "" : className.substring(null, dot + 1));
            if (alias != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(alias);
                stringBuilder.append(dot < 0 ? className : className.substring(dot + 1));
                return stringBuilder.toString();
            }
            length = dot - 1;
        } while (dot >= 0);
        return super.serializedClass(type);
    }

    public Class realClass(String elementName) {
        int length = elementName.length();
        int dot;
        do {
            String name;
            dot = elementName.lastIndexOf(46, length);
            if (dot < 0) {
                name = "";
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(elementName.substring(0, dot));
                stringBuilder.append('.');
                name = stringBuilder.toString();
            }
            String packageName = (String) this.nameToPackage.get(name);
            if (packageName != null) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(packageName);
                stringBuilder2.append(dot < 0 ? elementName : elementName.substring(dot + 1));
                elementName = stringBuilder2.toString();
            } else {
                length = dot - 1;
            }
            return super.realClass(elementName);
        } while (dot >= 0);
        return super.realClass(elementName);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(new HashMap(this.packageToName));
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.packageToName = new TreeMap(REVERSE);
        this.packageToName.putAll((Map) in.readObject());
        this.nameToPackage = new HashMap();
        for (Object type : this.packageToName.keySet()) {
            this.nameToPackage.put(this.packageToName.get(type), type);
        }
    }
}
