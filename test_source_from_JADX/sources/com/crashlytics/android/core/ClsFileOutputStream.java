package com.crashlytics.android.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

class ClsFileOutputStream extends FileOutputStream {
    public static final String IN_PROGRESS_SESSION_FILE_EXTENSION = ".cls_temp";
    public static final String SESSION_FILE_EXTENSION = ".cls";
    public static final FilenameFilter TEMP_FILENAME_FILTER = new C03731();
    private boolean closed;
    private File complete;
    private File inProgress;
    private final String root;

    /* renamed from: com.crashlytics.android.core.ClsFileOutputStream$1 */
    static class C03731 implements FilenameFilter {
        C03731() {
        }

        public boolean accept(File dir, String filename) {
            return filename.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION);
        }
    }

    public ClsFileOutputStream(String dir, String fileRoot) throws FileNotFoundException {
        this(new File(dir), fileRoot);
    }

    public ClsFileOutputStream(File dir, String fileRoot) throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fileRoot);
        stringBuilder.append(IN_PROGRESS_SESSION_FILE_EXTENSION);
        super(new File(dir, stringBuilder.toString()));
        this.closed = false;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(dir);
        stringBuilder2.append(File.separator);
        stringBuilder2.append(fileRoot);
        this.root = stringBuilder2.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.root);
        stringBuilder.append(IN_PROGRESS_SESSION_FILE_EXTENSION);
        this.inProgress = new File(stringBuilder.toString());
    }

    public synchronized void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            super.flush();
            super.close();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.root);
            stringBuilder.append(SESSION_FILE_EXTENSION);
            File complete = new File(stringBuilder.toString());
            if (this.inProgress.renameTo(complete)) {
                this.inProgress = null;
                this.complete = complete;
                return;
            }
            String reason = "";
            if (complete.exists()) {
                reason = " (target already exists)";
            } else if (!this.inProgress.exists()) {
                reason = " (source does not exist)";
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Could not rename temp file: ");
            stringBuilder2.append(this.inProgress);
            stringBuilder2.append(" -> ");
            stringBuilder2.append(complete);
            stringBuilder2.append(reason);
            throw new IOException(stringBuilder2.toString());
        }
    }

    public void closeInProgressStream() throws IOException {
        if (!this.closed) {
            this.closed = true;
            super.flush();
            super.close();
        }
    }

    public File getCompleteFile() {
        return this.complete;
    }

    public File getInProgressFile() {
        return this.inProgress;
    }
}
