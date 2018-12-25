package com.parrot.freeflight.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {
    public static void copyStream(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int read = is.read(buffer);
            int count = read;
            if (read == -1) {
                os.flush();
                return;
            }
            os.write(buffer, 0, count);
        }
    }
}
