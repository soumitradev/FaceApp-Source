package org.apache.commons.compress.archivers.zip;

public final class ZipArchiveOutputStream$UnicodeExtraFieldPolicy {
    public static final ZipArchiveOutputStream$UnicodeExtraFieldPolicy ALWAYS = new ZipArchiveOutputStream$UnicodeExtraFieldPolicy("always");
    public static final ZipArchiveOutputStream$UnicodeExtraFieldPolicy NEVER = new ZipArchiveOutputStream$UnicodeExtraFieldPolicy("never");
    public static final ZipArchiveOutputStream$UnicodeExtraFieldPolicy NOT_ENCODEABLE = new ZipArchiveOutputStream$UnicodeExtraFieldPolicy("not encodeable");
    private final String name;

    private ZipArchiveOutputStream$UnicodeExtraFieldPolicy(String n) {
        this.name = n;
    }

    public String toString() {
        return this.name;
    }
}
