package org.tukaani.xz;

import java.io.InputStream;
import org.tukaani.xz.simple.ARM;
import org.tukaani.xz.simple.ARMThumb;
import org.tukaani.xz.simple.IA64;
import org.tukaani.xz.simple.PowerPC;
import org.tukaani.xz.simple.SPARC;
import org.tukaani.xz.simple.SimpleFilter;
import org.tukaani.xz.simple.X86;

class BCJDecoder extends BCJCoder implements FilterDecoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static /* synthetic */ Class class$org$tukaani$xz$BCJDecoder = class$("org.tukaani.xz.BCJDecoder");
    private final long filterID;
    private final int startOffset;

    static {
        if (class$org$tukaani$xz$BCJDecoder == null) {
        } else {
            Class cls = class$org$tukaani$xz$BCJDecoder;
        }
    }

    BCJDecoder(long j, byte[] bArr) throws UnsupportedOptionsException {
        this.filterID = j;
        int i = 0;
        if (bArr.length == 0) {
            this.startOffset = 0;
        } else if (bArr.length == 4) {
            int i2 = 0;
            while (i < 4) {
                i2 |= (bArr[i] & 255) << (i * 8);
                i++;
            }
            this.startOffset = i2;
        } else {
            throw new UnsupportedOptionsException("Unsupported BCJ filter properties");
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public InputStream getInputStream(InputStream inputStream) {
        SimpleFilter x86 = this.filterID == 4 ? new X86(false, this.startOffset) : this.filterID == 5 ? new PowerPC(false, this.startOffset) : this.filterID == 6 ? new IA64(false, this.startOffset) : this.filterID == 7 ? new ARM(false, this.startOffset) : this.filterID == 8 ? new ARMThumb(false, this.startOffset) : this.filterID == 9 ? new SPARC(false, this.startOffset) : null;
        return new SimpleInputStream(inputStream, x86);
    }

    public int getMemoryUsage() {
        return SimpleInputStream.getMemoryUsage();
    }
}
