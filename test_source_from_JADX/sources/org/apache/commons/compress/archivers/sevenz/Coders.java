package org.apache.commons.compress.archivers.sevenz;

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.tukaani.xz.ARMOptions;
import org.tukaani.xz.ARMThumbOptions;
import org.tukaani.xz.FilterOptions;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.IA64Options;
import org.tukaani.xz.LZMAInputStream;
import org.tukaani.xz.PowerPCOptions;
import org.tukaani.xz.SPARCOptions;
import org.tukaani.xz.X86Options;

class Coders {
    private static final Map<SevenZMethod, CoderBase> CODER_MAP = new C17331();

    /* renamed from: org.apache.commons.compress.archivers.sevenz.Coders$1 */
    static class C17331 extends HashMap<SevenZMethod, CoderBase> {
        private static final long serialVersionUID = 1664829131806520867L;

        C17331() {
            put(SevenZMethod.COPY, new CopyDecoder());
            put(SevenZMethod.LZMA, new LZMADecoder());
            put(SevenZMethod.LZMA2, new LZMA2Decoder());
            put(SevenZMethod.DEFLATE, new DeflateDecoder());
            put(SevenZMethod.BZIP2, new BZIP2Decoder());
            put(SevenZMethod.AES256SHA256, new AES256SHA256Decoder());
            put(SevenZMethod.BCJ_X86_FILTER, new BCJDecoder(new X86Options()));
            put(SevenZMethod.BCJ_PPC_FILTER, new BCJDecoder(new PowerPCOptions()));
            put(SevenZMethod.BCJ_IA64_FILTER, new BCJDecoder(new IA64Options()));
            put(SevenZMethod.BCJ_ARM_FILTER, new BCJDecoder(new ARMOptions()));
            put(SevenZMethod.BCJ_ARM_THUMB_FILTER, new BCJDecoder(new ARMThumbOptions()));
            put(SevenZMethod.BCJ_SPARC_FILTER, new BCJDecoder(new SPARCOptions()));
            put(SevenZMethod.DELTA_FILTER, new DeltaDecoder());
        }
    }

    private static class DummyByteAddingInputStream extends FilterInputStream {
        private boolean addDummyByte;

        private DummyByteAddingInputStream(InputStream in) {
            super(in);
            this.addDummyByte = true;
        }

        public int read() throws IOException {
            int result = super.read();
            if (result != -1 || !this.addDummyByte) {
                return result;
            }
            this.addDummyByte = false;
            return 0;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            int result = super.read(b, off, len);
            if (result != -1 || !this.addDummyByte) {
                return result;
            }
            this.addDummyByte = false;
            b[off] = (byte) 0;
            return 1;
        }
    }

    static class BCJDecoder extends CoderBase {
        private final FilterOptions opts;

        BCJDecoder(FilterOptions opts) {
            super(new Class[0]);
            this.opts = opts;
        }

        InputStream decode(InputStream in, long uncompressedLength, Coder coder, byte[] password) throws IOException {
            try {
                return this.opts.getInputStream(in);
            } catch (AssertionError e) {
                IOException ex = new IOException("BCJ filter needs XZ for Java > 1.4 - see http://commons.apache.org/proper/commons-compress/limitations.html#7Z");
                ex.initCause(e);
                throw ex;
            }
        }

        OutputStream encode(OutputStream out, Object options) {
            return new FilterOutputStream(this.opts.getOutputStream(new FinishableWrapperOutputStream(out))) {
                public void flush() {
                }
            };
        }
    }

    static class BZIP2Decoder extends CoderBase {
        BZIP2Decoder() {
            super(Number.class);
        }

        InputStream decode(InputStream in, long uncompressedLength, Coder coder, byte[] password) throws IOException {
            return new BZip2CompressorInputStream(in);
        }

        OutputStream encode(OutputStream out, Object options) throws IOException {
            return new BZip2CompressorOutputStream(out, CoderBase.numberOptionOrDefault(options, 9));
        }
    }

    static class CopyDecoder extends CoderBase {
        CopyDecoder() {
            super(new Class[0]);
        }

        InputStream decode(InputStream in, long uncompressedLength, Coder coder, byte[] password) throws IOException {
            return in;
        }

        OutputStream encode(OutputStream out, Object options) {
            return out;
        }
    }

    static class DeflateDecoder extends CoderBase {
        DeflateDecoder() {
            super(Number.class);
        }

        InputStream decode(InputStream in, long uncompressedLength, Coder coder, byte[] password) throws IOException {
            return new InflaterInputStream(new DummyByteAddingInputStream(in), new Inflater(true));
        }

        OutputStream encode(OutputStream out, Object options) {
            return new DeflaterOutputStream(out, new Deflater(CoderBase.numberOptionOrDefault(options, 9), true));
        }
    }

    static class LZMADecoder extends CoderBase {
        LZMADecoder() {
            super(new Class[0]);
        }

        InputStream decode(InputStream in, long uncompressedLength, Coder coder, byte[] password) throws IOException {
            byte propsByte = coder.properties[0];
            int i = 1;
            long dictSize = (long) coder.properties[1];
            while (true) {
                int i2 = i;
                if (i2 >= 4) {
                    break;
                }
                i = i2 + 1;
                dictSize |= (((long) coder.properties[i2 + 1]) & 255) << (i2 * 8);
            }
            if (dictSize > 2147483632) {
                throw new IOException("Dictionary larger than 4GiB maximum size");
            }
            return new LZMAInputStream(in, uncompressedLength, propsByte, (int) dictSize);
        }
    }

    Coders() {
    }

    static CoderBase findByMethod(SevenZMethod method) {
        return (CoderBase) CODER_MAP.get(method);
    }

    static InputStream addDecoder(InputStream is, long uncompressedLength, Coder coder, byte[] password) throws IOException {
        CoderBase cb = findByMethod(SevenZMethod.byId(coder.decompressionMethodId));
        if (cb != null) {
            return cb.decode(is, uncompressedLength, coder, password);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported compression method ");
        stringBuilder.append(Arrays.toString(coder.decompressionMethodId));
        throw new IOException(stringBuilder.toString());
    }

    static OutputStream addEncoder(OutputStream out, SevenZMethod method, Object options) throws IOException {
        CoderBase cb = findByMethod(method);
        if (cb != null) {
            return cb.encode(out, options);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported compression method ");
        stringBuilder.append(method);
        throw new IOException(stringBuilder.toString());
    }
}
