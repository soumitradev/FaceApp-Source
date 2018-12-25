package org.apache.commons.compress.archivers.ar;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class ArArchiveInputStream extends ArchiveInputStream {
    private static final String BSD_LONGNAME_PATTERN = "^#1/\\d+";
    static final String BSD_LONGNAME_PREFIX = "#1/";
    private static final int BSD_LONGNAME_PREFIX_LEN = BSD_LONGNAME_PREFIX.length();
    private static final String GNU_LONGNAME_PATTERN = "^/\\d+";
    private static final String GNU_STRING_TABLE_NAME = "//";
    private final byte[] FILE_MODE_BUF = new byte[8];
    private final byte[] ID_BUF = new byte[6];
    private final byte[] LAST_MODIFIED_BUF = new byte[12];
    private final byte[] LENGTH_BUF = new byte[10];
    private final byte[] NAME_BUF = new byte[16];
    private boolean closed;
    private ArArchiveEntry currentEntry = null;
    private long entryOffset = -1;
    private final InputStream input;
    private byte[] namebuffer = null;
    private long offset = 0;

    public ArArchiveInputStream(InputStream pInput) {
        this.input = pInput;
        this.closed = false;
    }

    public ArArchiveEntry getNextArEntry() throws IOException {
        byte[] realized;
        StringBuilder stringBuilder;
        if (this.currentEntry != null) {
            IOUtils.skip(this, (this.entryOffset + this.currentEntry.getLength()) - this.offset);
            this.currentEntry = null;
        }
        if (this.offset == 0) {
            byte[] expected = ArchiveUtils.toAsciiBytes(ArArchiveEntry.HEADER);
            realized = new byte[expected.length];
            if (IOUtils.readFully(this, realized) != expected.length) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("failed to read header. Occured at byte: ");
                stringBuilder.append(getBytesRead());
                throw new IOException(stringBuilder.toString());
            }
            for (int i = 0; i < expected.length; i++) {
                if (expected[i] != realized[i]) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("invalid header ");
                    stringBuilder.append(ArchiveUtils.toAsciiString(realized));
                    throw new IOException(stringBuilder.toString());
                }
            }
        }
        if ((this.offset % 2 != 0 && read() < 0) || this.input.available() == 0) {
            return null;
        }
        IOUtils.readFully(this, this.NAME_BUF);
        IOUtils.readFully(this, this.LAST_MODIFIED_BUF);
        IOUtils.readFully(this, this.ID_BUF);
        int userId = asInt(this.ID_BUF, true);
        IOUtils.readFully(this, this.ID_BUF);
        IOUtils.readFully(this, this.FILE_MODE_BUF);
        IOUtils.readFully(this, this.LENGTH_BUF);
        realized = ArchiveUtils.toAsciiBytes(ArArchiveEntry.TRAILER);
        byte[] realized2 = new byte[realized.length];
        if (IOUtils.readFully(this, realized2) != realized.length) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("failed to read entry trailer. Occured at byte: ");
            stringBuilder.append(getBytesRead());
            throw new IOException(stringBuilder.toString());
        }
        for (int i2 = 0; i2 < realized.length; i2++) {
            if (realized[i2] != realized2[i2]) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("invalid entry trailer. not read the content? Occured at byte: ");
                stringBuilder.append(getBytesRead());
                throw new IOException(stringBuilder.toString());
            }
        }
        this.entryOffset = this.offset;
        String temp = ArchiveUtils.toAsciiString(this.NAME_BUF).trim();
        if (isGNUStringTable(temp)) {
            this.currentEntry = readGNUStringTable(this.LENGTH_BUF);
            return getNextArEntry();
        }
        String temp2;
        long len;
        long len2 = asLong(this.LENGTH_BUF);
        if (temp.endsWith("/")) {
            temp2 = temp.substring(null, temp.length() - 1);
        } else if (isGNULongName(temp)) {
            temp2 = getExtendedName(Integer.parseInt(temp.substring(1)));
        } else if (isBSDLongName(temp)) {
            temp2 = getBSDLongName(temp);
            temp = temp2.length();
            long len3 = len2 - ((long) temp);
            this.entryOffset += (long) temp;
            len = len3;
            this.currentEntry = new ArArchiveEntry(temp2, len, userId, asInt(this.ID_BUF, true), asInt(this.FILE_MODE_BUF, 8), asLong(this.LAST_MODIFIED_BUF));
            return this.currentEntry;
        } else {
            temp2 = temp;
        }
        len = len2;
        this.currentEntry = new ArArchiveEntry(temp2, len, userId, asInt(this.ID_BUF, true), asInt(this.FILE_MODE_BUF, 8), asLong(this.LAST_MODIFIED_BUF));
        return this.currentEntry;
    }

    private String getExtendedName(int offset) throws IOException {
        if (this.namebuffer == null) {
            throw new IOException("Cannot process GNU long filename as no // record was found");
        }
        int i = offset;
        while (i < this.namebuffer.length) {
            if (this.namebuffer[i] == (byte) 10) {
                if (this.namebuffer[i - 1] == GeneralMidiConstants.TIMPANI) {
                    i--;
                }
                return ArchiveUtils.toAsciiString(this.namebuffer, offset, i - offset);
            }
            i++;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to read entry: ");
        stringBuilder.append(offset);
        throw new IOException(stringBuilder.toString());
    }

    private long asLong(byte[] input) {
        return Long.parseLong(ArchiveUtils.toAsciiString(input).trim());
    }

    private int asInt(byte[] input) {
        return asInt(input, 10, false);
    }

    private int asInt(byte[] input, boolean treatBlankAsZero) {
        return asInt(input, 10, treatBlankAsZero);
    }

    private int asInt(byte[] input, int base) {
        return asInt(input, base, false);
    }

    private int asInt(byte[] input, int base, boolean treatBlankAsZero) {
        String string = ArchiveUtils.toAsciiString(input).trim();
        if (string.length() == 0 && treatBlankAsZero) {
            return 0;
        }
        return Integer.parseInt(string, base);
    }

    public ArchiveEntry getNextEntry() throws IOException {
        return getNextArEntry();
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.input.close();
        }
        this.currentEntry = null;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int toRead = len;
        if (this.currentEntry != null) {
            long entryEnd = this.entryOffset + this.currentEntry.getLength();
            if (len <= 0 || entryEnd <= this.offset) {
                return -1;
            }
            toRead = (int) Math.min((long) len, entryEnd - this.offset);
        }
        int ret = this.input.read(b, off, toRead);
        count(ret);
        this.offset += ret > 0 ? (long) ret : 0;
        return ret;
    }

    public static boolean matches(byte[] signature, int length) {
        if (length >= 8 && signature[0] == GeneralMidiConstants.ELECTRIC_BASS_FINGER && signature[1] == GeneralMidiConstants.FRENCH_HORN && signature[2] == GeneralMidiConstants.FX_1_SOUNDTRACK && signature[3] == GeneralMidiConstants.STEEL_DRUMS && signature[4] == GeneralMidiConstants.FX_3_ATMOSPHERE && signature[5] == GeneralMidiConstants.SIT_R && signature[6] == GeneralMidiConstants.SYNTHBRASS_0 && signature[7] == (byte) 10) {
            return true;
        }
        return false;
    }

    private static boolean isBSDLongName(String name) {
        return name != null && name.matches(BSD_LONGNAME_PATTERN);
    }

    private String getBSDLongName(String bsdLongName) throws IOException {
        int nameLen = Integer.parseInt(bsdLongName.substring(BSD_LONGNAME_PREFIX_LEN));
        byte[] name = new byte[nameLen];
        int read = IOUtils.readFully(this.input, name);
        count(read);
        if (read == nameLen) {
            return ArchiveUtils.toAsciiString(name);
        }
        throw new EOFException();
    }

    private static boolean isGNUStringTable(String name) {
        return GNU_STRING_TABLE_NAME.equals(name);
    }

    private ArArchiveEntry readGNUStringTable(byte[] length) throws IOException {
        int bufflen = asInt(length);
        this.namebuffer = new byte[bufflen];
        int read = IOUtils.readFully(this, this.namebuffer, 0, bufflen);
        if (read == bufflen) {
            return new ArArchiveEntry(GNU_STRING_TABLE_NAME, (long) bufflen);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to read complete // record: expected=");
        stringBuilder.append(bufflen);
        stringBuilder.append(" read=");
        stringBuilder.append(read);
        throw new IOException(stringBuilder.toString());
    }

    private boolean isGNULongName(String name) {
        return name != null && name.matches(GNU_LONGNAME_PATTERN);
    }
}
