package org.apache.commons.compress.archivers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

public final class Lister {
    private static final ArchiveStreamFactory factory = new ArchiveStreamFactory();

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            usage();
            return;
        }
        ArchiveInputStream ais;
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Analysing ");
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
        InputStream fis = new BufferedInputStream(new FileInputStream(f));
        if (args.length > 1) {
            ais = factory.createArchiveInputStream(args[1], fis);
        } else {
            ais = factory.createArchiveInputStream(fis);
        }
        PrintStream printStream3 = System.out;
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("Created ");
        stringBuilder3.append(ais.toString());
        printStream3.println(stringBuilder3.toString());
        while (true) {
            ArchiveEntry nextEntry = ais.getNextEntry();
            ArchiveEntry ae = nextEntry;
            if (nextEntry != null) {
                System.out.println(ae.getName());
            } else {
                ais.close();
                fis.close();
                return;
            }
        }
    }

    private static void usage() {
        System.out.println("Parameters: archive-name [archive-type]");
    }
}
