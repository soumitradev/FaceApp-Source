package org.rauschig.jarchivelib;

import java.io.IOException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.archivers.arj.ArjArchiveEntry;
import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

abstract class AttributeAccessor<E extends ArchiveEntry> {
    private E entry;

    public static class ArAttributeAccessor extends AttributeAccessor<ArArchiveEntry> {
        public ArAttributeAccessor(ArArchiveEntry entry) {
            super(entry);
        }

        public int getMode() throws IOException {
            return ((ArArchiveEntry) getEntry()).getMode();
        }
    }

    public static class ArjAttributeAccessor extends AttributeAccessor<ArjArchiveEntry> {
        public ArjAttributeAccessor(ArjArchiveEntry entry) {
            super(entry);
        }

        public int getMode() throws IOException {
            return ((ArjArchiveEntry) getEntry()).getMode();
        }
    }

    public static class CpioAttributeAccessor extends AttributeAccessor<CpioArchiveEntry> {
        public CpioAttributeAccessor(CpioArchiveEntry entry) {
            super(entry);
        }

        public int getMode() {
            return (int) ((CpioArchiveEntry) getEntry()).getMode();
        }
    }

    public static class FallbackAttributeAccessor extends AttributeAccessor<ArchiveEntry> {
        public /* bridge */ /* synthetic */ ArchiveEntry getEntry() {
            return super.getEntry();
        }

        protected FallbackAttributeAccessor(ArchiveEntry entry) {
            super(entry);
        }

        public int getMode() {
            return 0;
        }
    }

    public static class TarAttributeAccessor extends AttributeAccessor<TarArchiveEntry> {
        public TarAttributeAccessor(TarArchiveEntry entry) {
            super(entry);
        }

        public int getMode() {
            return ((TarArchiveEntry) getEntry()).getMode();
        }
    }

    public static class ZipAttributeAccessor extends AttributeAccessor<ZipArchiveEntry> {
        public ZipAttributeAccessor(ZipArchiveEntry entry) {
            super(entry);
        }

        public int getMode() {
            return ((ZipArchiveEntry) getEntry()).getUnixMode();
        }
    }

    public abstract int getMode() throws IOException;

    public AttributeAccessor(E entry) {
        this.entry = entry;
    }

    public E getEntry() {
        return this.entry;
    }

    public static AttributeAccessor<?> create(ArchiveEntry entry) {
        if (entry instanceof TarArchiveEntry) {
            return new TarAttributeAccessor((TarArchiveEntry) entry);
        }
        if (entry instanceof ZipArchiveEntry) {
            return new ZipAttributeAccessor((ZipArchiveEntry) entry);
        }
        if (entry instanceof CpioArchiveEntry) {
            return new CpioAttributeAccessor((CpioArchiveEntry) entry);
        }
        if (entry instanceof ArjArchiveEntry) {
            return new ArjAttributeAccessor((ArjArchiveEntry) entry);
        }
        if (entry instanceof ArArchiveEntry) {
            return new ArAttributeAccessor((ArArchiveEntry) entry);
        }
        return new FallbackAttributeAccessor(entry);
    }
}
