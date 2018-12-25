package org.apache.commons.compress.archivers.zip;

import java.util.zip.ZipException;

public class UnsupportedZipFeatureException extends ZipException {
    private static final long serialVersionUID = 20130101;
    private final ZipArchiveEntry entry;
    private final Feature reason;

    public static class Feature {
        public static final Feature DATA_DESCRIPTOR = new Feature("data descriptor");
        public static final Feature ENCRYPTION = new Feature("encryption");
        public static final Feature METHOD = new Feature("compression method");
        public static final Feature SPLITTING = new Feature("splitting");
        private final String name;

        private Feature(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public UnsupportedZipFeatureException(Feature reason, ZipArchiveEntry entry) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unsupported feature ");
        stringBuilder.append(reason);
        stringBuilder.append(" used in entry ");
        stringBuilder.append(entry.getName());
        super(stringBuilder.toString());
        this.reason = reason;
        this.entry = entry;
    }

    public UnsupportedZipFeatureException(ZipMethod method, ZipArchiveEntry entry) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unsupported feature method '");
        stringBuilder.append(method.name());
        stringBuilder.append("' used in entry ");
        stringBuilder.append(entry.getName());
        super(stringBuilder.toString());
        this.reason = Feature.METHOD;
        this.entry = entry;
    }

    public UnsupportedZipFeatureException(Feature reason) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unsupported feature ");
        stringBuilder.append(reason);
        stringBuilder.append(" used in archive.");
        super(stringBuilder.toString());
        this.reason = reason;
        this.entry = null;
    }

    public Feature getFeature() {
        return this.reason;
    }

    public ZipArchiveEntry getEntry() {
        return this.entry;
    }
}
