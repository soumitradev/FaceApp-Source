package org.apache.commons.compress.archivers.sevenz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.Constants;

public class CLI {
    private static final byte[] BUF = new byte[8192];

    private enum Mode {
        LIST("Analysing") {
            public void takeAction(SevenZFile archive, SevenZArchiveEntry entry) {
                System.out.print(entry.getName());
                if (entry.isDirectory()) {
                    System.out.print(" dir");
                } else {
                    PrintStream printStream = System.out;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(FormatHelper.SPACE);
                    stringBuilder.append(entry.getCompressedSize());
                    stringBuilder.append("/");
                    stringBuilder.append(entry.getSize());
                    printStream.print(stringBuilder.toString());
                }
                if (entry.getHasLastModifiedDate()) {
                    printStream = System.out;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(FormatHelper.SPACE);
                    stringBuilder.append(entry.getLastModifiedDate());
                    printStream.print(stringBuilder.toString());
                } else {
                    System.out.print(" no last modified date");
                }
                if (entry.isDirectory()) {
                    System.out.println("");
                    return;
                }
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(getContentMethods(entry));
                printStream.println(stringBuilder.toString());
            }

            private String getContentMethods(SevenZArchiveEntry entry) {
                StringBuilder sb = new StringBuilder();
                boolean first = true;
                for (SevenZMethodConfiguration m : entry.getContentMethods()) {
                    if (!first) {
                        sb.append(", ");
                    }
                    first = false;
                    sb.append(m.getMethod());
                    if (m.getOptions() != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(Constants.OPENING_BRACE);
                        stringBuilder.append(m.getOptions());
                        stringBuilder.append(")");
                        sb.append(stringBuilder.toString());
                    }
                }
                return sb.toString();
            }
        },
        EXTRACT("Extracting") {
            public void takeAction(SevenZFile archive, SevenZArchiveEntry entry) throws IOException {
                Throwable th;
                SevenZFile sevenZFile;
                File outFile = new File(entry.getName());
                PrintStream printStream;
                StringBuilder stringBuilder;
                if (!entry.isDirectory()) {
                    printStream = System.out;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("extracting to ");
                    stringBuilder.append(outFile);
                    printStream.println(stringBuilder.toString());
                    File parent = outFile.getParentFile();
                    if (parent == null || parent.exists() || parent.mkdirs()) {
                        FileOutputStream fos = new FileOutputStream(outFile);
                        try {
                            long total = entry.getSize();
                            long off = 0;
                            while (off < total) {
                                try {
                                    int bytesRead = archive.read(CLI.BUF, 0, (int) Math.min(total - off, (long) CLI.BUF.length));
                                    if (bytesRead < 1) {
                                        StringBuilder stringBuilder2 = new StringBuilder();
                                        stringBuilder2.append("reached end of entry ");
                                        stringBuilder2.append(entry.getName());
                                        stringBuilder2.append(" after ");
                                        stringBuilder2.append(off);
                                        stringBuilder2.append(" bytes, expected ");
                                        stringBuilder2.append(total);
                                        throw new IOException(stringBuilder2.toString());
                                    }
                                    long off2 = off + ((long) bytesRead);
                                    fos.write(CLI.BUF, 0, bytesRead);
                                    off = off2;
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            }
                            sevenZFile = archive;
                            fos.close();
                            return;
                        } catch (Throwable th3) {
                            th = th3;
                            sevenZFile = archive;
                            Throwable th4 = th;
                            fos.close();
                            throw th4;
                        }
                    }
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Cannot create ");
                    stringBuilder3.append(parent);
                    throw new IOException(stringBuilder3.toString());
                } else if (outFile.isDirectory() || outFile.mkdirs()) {
                    printStream = System.out;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("created directory ");
                    stringBuilder.append(outFile);
                    printStream.println(stringBuilder.toString());
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot create directory ");
                    stringBuilder.append(outFile);
                    throw new IOException(stringBuilder.toString());
                }
            }
        };
        
        private final String message;

        public abstract void takeAction(SevenZFile sevenZFile, SevenZArchiveEntry sevenZArchiveEntry) throws IOException;

        private Mode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            usage();
            return;
        }
        Mode mode = grabMode(args);
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mode.getMessage());
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(args[0]);
        printStream.println(stringBuilder.toString());
        File f = new File(args[0]);
        if (!f.isFile()) {
            PrintStream printStream2 = System.err;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(f);
            stringBuilder2.append(" doesn't exist or is a directory");
            printStream2.println(stringBuilder2.toString());
        }
        SevenZFile archive = new SevenZFile(f);
        while (true) {
            try {
                SevenZArchiveEntry nextEntry = archive.getNextEntry();
                SevenZArchiveEntry ae = nextEntry;
                if (nextEntry == null) {
                    break;
                }
                mode.takeAction(archive, ae);
            } finally {
                archive.close();
            }
        }
    }

    private static void usage() {
        System.out.println("Parameters: archive-name [list|extract]");
    }

    private static Mode grabMode(String[] args) {
        if (args.length < 2) {
            return Mode.LIST;
        }
        return (Mode) Enum.valueOf(Mode.class, args[1].toUpperCase());
    }
}
