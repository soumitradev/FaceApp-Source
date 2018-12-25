package com.koushikdutta.async.stream;

import com.koushikdutta.async.AsyncServer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileDataSink extends OutputStreamDataSink {
    File file;

    public FileDataSink(AsyncServer server, File file) {
        super(server);
        this.file = file;
    }

    public OutputStream getOutputStream() throws IOException {
        OutputStream ret = super.getOutputStream();
        if (ret != null) {
            return ret;
        }
        ret = new FileOutputStream(this.file);
        setOutputStream(ret);
        return ret;
    }
}
