package com.thoughtworks.xstream;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.ConverterRegistry;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.javabean.JavaBeanProvider;
import com.thoughtworks.xstream.converters.reflection.FieldKeySorter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.security.TypeHierarchyPermission;
import com.thoughtworks.xstream.security.TypePermission;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.datatype.DatatypeFactory;

public class XStreamer {
    private static final TypePermission[] PERMISSIONS;

    static {
        TypePermission[] typePermissionArr = new TypePermission[16];
        typePermissionArr[0] = new TypeHierarchyPermission(ConverterMatcher.class);
        typePermissionArr[1] = new TypeHierarchyPermission(Mapper.class);
        typePermissionArr[2] = new TypeHierarchyPermission(XStream.class);
        typePermissionArr[3] = new TypeHierarchyPermission(ReflectionProvider.class);
        typePermissionArr[4] = new TypeHierarchyPermission(JavaBeanProvider.class);
        typePermissionArr[5] = new TypeHierarchyPermission(FieldKeySorter.class);
        typePermissionArr[6] = new TypeHierarchyPermission(ConverterLookup.class);
        typePermissionArr[7] = new TypeHierarchyPermission(ConverterRegistry.class);
        typePermissionArr[8] = new TypeHierarchyPermission(HierarchicalStreamDriver.class);
        typePermissionArr[9] = new TypeHierarchyPermission(MarshallingStrategy.class);
        typePermissionArr[10] = new TypeHierarchyPermission(MarshallingContext.class);
        typePermissionArr[11] = new TypeHierarchyPermission(UnmarshallingContext.class);
        typePermissionArr[12] = new TypeHierarchyPermission(NameCoder.class);
        typePermissionArr[13] = new TypeHierarchyPermission(TypePermission.class);
        String[] strArr = new String[1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JVM.class.getPackage().getName());
        stringBuilder.append(".**");
        strArr[0] = stringBuilder.toString();
        typePermissionArr[14] = new WildcardTypePermission(strArr);
        typePermissionArr[15] = new TypeHierarchyPermission(DatatypeFactory.class);
        PERMISSIONS = typePermissionArr;
    }

    public String toXML(XStream xstream, Object obj) throws ObjectStreamException {
        Writer writer = new StringWriter();
        try {
            toXML(xstream, obj, writer);
            return writer.toString();
        } catch (ObjectStreamException e) {
            throw e;
        } catch (IOException e2) {
            throw new ConversionException("Unexpected IO error from a StringWriter", e2);
        }
    }

    public void toXML(XStream xstream, Object obj, Writer out) throws IOException {
        ObjectOutputStream oos = new XStream().createObjectOutputStream(out);
        try {
            oos.writeObject(xstream);
            oos.flush();
            xstream.toXML(obj, out);
        } finally {
            oos.close();
        }
    }

    public Object fromXML(String xml) throws ClassNotFoundException, ObjectStreamException {
        try {
            return fromXML(new StringReader(xml));
        } catch (ObjectStreamException e) {
            throw e;
        } catch (IOException e2) {
            throw new ConversionException("Unexpected IO error from a StringReader", e2);
        }
    }

    public Object fromXML(String xml, TypePermission[] permissions) throws ClassNotFoundException, ObjectStreamException {
        try {
            return fromXML(new StringReader(xml), permissions);
        } catch (ObjectStreamException e) {
            throw e;
        } catch (IOException e2) {
            throw new ConversionException("Unexpected IO error from a StringReader", e2);
        }
    }

    public Object fromXML(HierarchicalStreamDriver driver, String xml) throws ClassNotFoundException, ObjectStreamException {
        try {
            return fromXML(driver, new StringReader(xml));
        } catch (ObjectStreamException e) {
            throw e;
        } catch (IOException e2) {
            throw new ConversionException("Unexpected IO error from a StringReader", e2);
        }
    }

    public Object fromXML(HierarchicalStreamDriver driver, String xml, TypePermission[] permissions) throws ClassNotFoundException, ObjectStreamException {
        try {
            return fromXML(driver, new StringReader(xml), permissions);
        } catch (ObjectStreamException e) {
            throw e;
        } catch (IOException e2) {
            throw new ConversionException("Unexpected IO error from a StringReader", e2);
        }
    }

    public Object fromXML(Reader xml) throws IOException, ClassNotFoundException {
        return fromXML(new XppDriver(), xml);
    }

    public Object fromXML(Reader xml, TypePermission[] permissions) throws IOException, ClassNotFoundException {
        return fromXML(new XppDriver(), xml, permissions);
    }

    public Object fromXML(HierarchicalStreamDriver driver, Reader xml) throws IOException, ClassNotFoundException {
        return fromXML(driver, xml, new TypePermission[]{AnyTypePermission.ANY});
    }

    public java.lang.Object fromXML(com.thoughtworks.xstream.io.HierarchicalStreamDriver r7, java.io.Reader r8, com.thoughtworks.xstream.security.TypePermission[] r9) throws java.io.IOException, java.lang.ClassNotFoundException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Assign predecessor not found for B:6:? from B:22:?
	at jadx.core.dex.visitors.ssa.EliminatePhiNodes.replaceMerge(EliminatePhiNodes.java:102)
	at jadx.core.dex.visitors.ssa.EliminatePhiNodes.replaceMergeInstructions(EliminatePhiNodes.java:68)
	at jadx.core.dex.visitors.ssa.EliminatePhiNodes.visit(EliminatePhiNodes.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r6 = this;
        r0 = new com.thoughtworks.xstream.XStream;
        r0.<init>(r7);
        r1 = 0;
    L_0x0006:
        r2 = r9.length;
        if (r1 >= r2) goto L_0x0011;
    L_0x0009:
        r2 = r9[r1];
        r0.addPermission(r2);
        r1 = r1 + 1;
        goto L_0x0006;
    L_0x0011:
        r1 = r7.createReader(r8);
        r2 = r0.createObjectInputStream(r1);
        r3 = r2.readObject();	 Catch:{ all -> 0x0033 }
        r3 = (com.thoughtworks.xstream.XStream) r3;	 Catch:{ all -> 0x0033 }
        r4 = r3.createObjectInputStream(r1);	 Catch:{ all -> 0x0033 }
        r5 = r4.readObject();	 Catch:{ all -> 0x002e }
        r4.close();	 Catch:{ all -> 0x0033 }
        r2.close();
        return r5;
    L_0x002e:
        r5 = move-exception;
        r4.close();	 Catch:{ all -> 0x0033 }
        throw r5;	 Catch:{ all -> 0x0033 }
    L_0x0033:
        r3 = move-exception;
        r2.close();
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thoughtworks.xstream.XStreamer.fromXML(com.thoughtworks.xstream.io.HierarchicalStreamDriver, java.io.Reader, com.thoughtworks.xstream.security.TypePermission[]):java.lang.Object");
    }

    public static TypePermission[] getDefaultPermissions() {
        return (TypePermission[]) PERMISSIONS.clone();
    }
}
