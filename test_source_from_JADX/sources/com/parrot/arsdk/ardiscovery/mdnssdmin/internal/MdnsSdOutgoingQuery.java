package com.parrot.arsdk.ardiscovery.mdnssdmin.internal;

import java.io.ByteArrayOutputStream;

public class MdnsSdOutgoingQuery {
    private final String[] questions;

    public MdnsSdOutgoingQuery(String[] questions) {
        this.questions = questions;
    }

    private static void writeU16(ByteArrayOutputStream outputStream, int val) {
        outputStream.write(val >> 8);
        outputStream.write(val);
    }

    private static void writeName(ByteArrayOutputStream outputStream, String name) {
        for (String s : name.split("\\.")) {
            outputStream.write(s.length());
            for (char ch : s.toCharArray()) {
                outputStream.write(ch);
            }
        }
        outputStream.write(0);
    }

    public byte[] encode() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int i = 0;
        writeU16(os, 0);
        writeU16(os, 0);
        writeU16(os, this.questions.length);
        writeU16(os, 0);
        writeU16(os, 0);
        writeU16(os, 0);
        String[] strArr = this.questions;
        int length = strArr.length;
        while (i < length) {
            writeName(os, strArr[i]);
            writeU16(os, 12);
            writeU16(os, 1);
            i++;
        }
        return os.toByteArray();
    }
}
