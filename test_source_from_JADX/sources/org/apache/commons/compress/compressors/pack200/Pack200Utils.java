package org.apache.commons.compress.compressors.pack200;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.jar.Pack200.Packer;
import java.util.jar.Pack200.Unpacker;
import org.catrobat.catroid.web.ServerCalls;

public class Pack200Utils {
    private Pack200Utils() {
    }

    public static void normalize(File jar) throws IOException {
        normalize(jar, jar, null);
    }

    public static void normalize(File jar, Map<String, String> props) throws IOException {
        normalize(jar, jar, props);
    }

    public static void normalize(File from, File to) throws IOException {
        normalize(from, to, null);
    }

    public static void normalize(File from, File to, Map<String, String> props) throws IOException {
        JarFile j;
        if (props == null) {
            props = new HashMap();
        }
        props.put("pack.segment.limit", ServerCalls.TOKEN_CODE_INVALID);
        File f = File.createTempFile("commons-compress", "pack200normalize");
        f.deleteOnExit();
        OutputStream os;
        try {
            os = new FileOutputStream(f);
            j = null;
            Packer p = Pack200.newPacker();
            p.properties().putAll(props);
            JarFile jarFile = new JarFile(from);
            j = jarFile;
            p.pack(jarFile, os);
            j = null;
            os.close();
            Unpacker u = Pack200.newUnpacker();
            os = new JarOutputStream(new FileOutputStream(to));
            u.unpack(f, (JarOutputStream) os);
            if (j != null) {
                j.close();
            }
            if (os != null) {
                os.close();
            }
            f.delete();
        } catch (Throwable th) {
            f.delete();
        }
    }
}
