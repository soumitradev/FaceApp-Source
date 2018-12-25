package org.tukaani.xz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.common.EncoderUtil;

class BlockOutputStream extends FinishableOutputStream {
    private final Check check;
    private final long compressedSizeLimit;
    private FinishableOutputStream filterChain;
    private final int headerSize;
    private final OutputStream out;
    private final CountingOutputStream outCounted;
    private final byte[] tempBuf = new byte[1];
    private long uncompressedSize = 0;

    public BlockOutputStream(OutputStream outputStream, FilterEncoder[] filterEncoderArr, Check check) throws IOException {
        this.out = outputStream;
        this.check = check;
        this.outCounted = new CountingOutputStream(outputStream);
        this.filterChain = this.outCounted;
        for (int length = filterEncoderArr.length - 1; length >= 0; length--) {
            this.filterChain = filterEncoderArr[length].getOutputStream(this.filterChain);
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(filterEncoderArr.length - 1);
        for (int i = 0; i < filterEncoderArr.length; i++) {
            EncoderUtil.encodeVLI(byteArrayOutputStream, filterEncoderArr[i].getFilterID());
            byte[] filterProps = filterEncoderArr[i].getFilterProps();
            EncoderUtil.encodeVLI(byteArrayOutputStream, (long) filterProps.length);
            byteArrayOutputStream.write(filterProps);
        }
        while ((byteArrayOutputStream.size() & 3) != 0) {
            byteArrayOutputStream.write(0);
        }
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        this.headerSize = toByteArray.length + 4;
        if (this.headerSize > 1024) {
            throw new UnsupportedOptionsException();
        }
        toByteArray[0] = (byte) (toByteArray.length / 4);
        outputStream.write(toByteArray);
        EncoderUtil.writeCRC32(outputStream, toByteArray);
        this.compressedSizeLimit = (9223372036854775804L - ((long) this.headerSize)) - ((long) check.getSize());
    }

    private void validate() throws IOException {
        long size = this.outCounted.getSize();
        if (size >= 0 && size <= this.compressedSizeLimit) {
            if (this.uncompressedSize >= 0) {
                return;
            }
        }
        throw new XZIOException("XZ Stream has grown too big");
    }

    public void finish() throws IOException {
        this.filterChain.finish();
        validate();
        for (long size = this.outCounted.getSize(); (size & 3) != 0; size++) {
            this.out.write(0);
        }
        this.out.write(this.check.finish());
    }

    public void flush() throws IOException {
        this.filterChain.flush();
        validate();
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public long getUnpaddedSize() {
        return (((long) this.headerSize) + this.outCounted.getSize()) + ((long) this.check.getSize());
    }

    public void write(int i) throws IOException {
        this.tempBuf[0] = (byte) i;
        write(this.tempBuf, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.filterChain.write(bArr, i, i2);
        this.check.update(bArr, i, i2);
        this.uncompressedSize += (long) i2;
        validate();
    }
}
