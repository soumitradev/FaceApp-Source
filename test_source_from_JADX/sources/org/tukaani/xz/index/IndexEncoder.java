package org.tukaani.xz.index;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import org.tukaani.xz.XZIOException;
import org.tukaani.xz.common.EncoderUtil;

public class IndexEncoder extends IndexBase {
    private final ArrayList records = new ArrayList();

    public IndexEncoder() {
        super(new XZIOException("XZ Stream or its Index has grown too big"));
    }

    public void add(long j, long j2) throws XZIOException {
        super.add(j, j2);
        this.records.add(new IndexRecord(j, j2));
    }

    public void encode(OutputStream outputStream) throws IOException {
        Object crc32 = new CRC32();
        OutputStream checkedOutputStream = new CheckedOutputStream(outputStream, crc32);
        int i = 0;
        checkedOutputStream.write(0);
        EncoderUtil.encodeVLI(checkedOutputStream, this.recordCount);
        Iterator it = this.records.iterator();
        while (it.hasNext()) {
            IndexRecord indexRecord = (IndexRecord) it.next();
            EncoderUtil.encodeVLI(checkedOutputStream, indexRecord.unpadded);
            EncoderUtil.encodeVLI(checkedOutputStream, indexRecord.uncompressed);
        }
        for (int indexPaddingSize = getIndexPaddingSize(); indexPaddingSize > 0; indexPaddingSize--) {
            checkedOutputStream.write(0);
        }
        long value = crc32.getValue();
        while (i < 4) {
            outputStream.write((byte) ((int) (value >>> (i * 8))));
            i++;
        }
    }
}
