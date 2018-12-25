package org.tukaani.xz.index;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.CheckedInputStream;
import org.tukaani.xz.CorruptedInputException;
import org.tukaani.xz.XZIOException;
import org.tukaani.xz.check.CRC32;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.check.SHA256;
import org.tukaani.xz.common.DecoderUtil;

public class IndexHash extends IndexBase {
    private Check hash;

    public IndexHash() {
        super(new CorruptedInputException());
        try {
            this.hash = new SHA256();
        } catch (NoSuchAlgorithmException e) {
            this.hash = new CRC32();
        }
    }

    public void add(long j, long j2) throws XZIOException {
        super.add(j, j2);
        ByteBuffer allocate = ByteBuffer.allocate(16);
        allocate.putLong(j);
        allocate.putLong(j2);
        this.hash.update(allocate.array());
    }

    public void validate(InputStream inputStream) throws IOException {
        Object crc32 = new java.util.zip.CRC32();
        int i = 0;
        crc32.update(0);
        InputStream checkedInputStream = new CheckedInputStream(inputStream, crc32);
        if (DecoderUtil.decodeVLI(checkedInputStream) != this.recordCount) {
            throw new CorruptedInputException("XZ Index is corrupt");
        }
        IndexHash indexHash = new IndexHash();
        long j = 0;
        while (j < this.recordCount) {
            try {
                indexHash.add(DecoderUtil.decodeVLI(checkedInputStream), DecoderUtil.decodeVLI(checkedInputStream));
                if (indexHash.blocksSum <= this.blocksSum && indexHash.uncompressedSum <= this.uncompressedSum) {
                    if (indexHash.indexListSize <= this.indexListSize) {
                        j++;
                    }
                }
                throw new CorruptedInputException("XZ Index is corrupt");
            } catch (XZIOException e) {
                throw new CorruptedInputException("XZ Index is corrupt");
            }
        }
        if (indexHash.blocksSum == this.blocksSum && indexHash.uncompressedSum == this.uncompressedSum && indexHash.indexListSize == this.indexListSize) {
            if (Arrays.equals(indexHash.hash.finish(), this.hash.finish())) {
                DataInputStream dataInputStream = new DataInputStream(checkedInputStream);
                for (int indexPaddingSize = getIndexPaddingSize(); indexPaddingSize > 0; indexPaddingSize--) {
                    if (dataInputStream.readUnsignedByte() != 0) {
                        throw new CorruptedInputException("XZ Index is corrupt");
                    }
                }
                long value = crc32.getValue();
                while (i < 4) {
                    if (((value >>> (i * 8)) & 255) != ((long) dataInputStream.readUnsignedByte())) {
                        throw new CorruptedInputException("XZ Index is corrupt");
                    }
                    i++;
                }
                return;
            }
        }
        throw new CorruptedInputException("XZ Index is corrupt");
    }
}
