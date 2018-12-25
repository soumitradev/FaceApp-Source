package com.koushikdutta.async.http.body;

import com.koushikdutta.async.http.BasicNameValuePair;
import com.koushikdutta.async.http.NameValuePair;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FilePart extends StreamPart {
    File file;

    /* renamed from: com.koushikdutta.async.http.body.FilePart$1 */
    class C06521 extends ArrayList<NameValuePair> {
        final /* synthetic */ File val$file;

        C06521(File file) {
            this.val$file = file;
            add(new BasicNameValuePair("filename", this.val$file.getName()));
        }
    }

    public FilePart(String name, File file) {
        super(name, (long) ((int) file.length()), new C06521(file));
        this.file = file;
    }

    protected InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }
}
