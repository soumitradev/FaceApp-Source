package org.tukaani.xz.rangecoder;

import android.support.v4.view.ViewCompat;
import java.io.DataInputStream;
import java.io.IOException;
import org.tukaani.xz.CorruptedInputException;

public final class RangeDecoderFromBuffer extends RangeDecoder {
    private static final int INIT_SIZE = 5;
    private final byte[] buf;
    private int end = 0;
    private int pos = 0;

    public RangeDecoderFromBuffer(int i) {
        this.buf = new byte[(i - 5)];
    }

    public boolean isFinished() {
        return this.pos == this.end && this.code == 0;
    }

    public boolean isInBufferOK() {
        return this.pos <= this.end;
    }

    public void normalize() throws IOException {
        if ((this.range & ViewCompat.MEASURED_STATE_MASK) == 0) {
            try {
                int i = this.code << 8;
                byte[] bArr = this.buf;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.code = i | (bArr[i2] & 255);
                this.range <<= 8;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new CorruptedInputException();
            }
        }
    }

    public void prepareInputBuffer(DataInputStream dataInputStream, int i) throws IOException {
        if (i < 5) {
            throw new CorruptedInputException();
        } else if (dataInputStream.readUnsignedByte() != 0) {
            throw new CorruptedInputException();
        } else {
            this.code = dataInputStream.readInt();
            this.range = -1;
            this.pos = 0;
            this.end = i - 5;
            dataInputStream.readFully(this.buf, 0, this.end);
        }
    }
}
