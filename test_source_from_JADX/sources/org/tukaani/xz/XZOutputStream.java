package org.tukaani.xz;

import java.io.IOException;
import java.io.OutputStream;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.common.EncoderUtil;
import org.tukaani.xz.common.StreamFlags;
import org.tukaani.xz.index.IndexEncoder;

public class XZOutputStream extends FinishableOutputStream {
    private BlockOutputStream blockEncoder;
    private final Check check;
    private IOException exception;
    private FilterEncoder[] filters;
    private boolean filtersSupportFlushing;
    private boolean finished;
    private final IndexEncoder index;
    private OutputStream out;
    private final StreamFlags streamFlags;
    private final byte[] tempBuf;

    public XZOutputStream(OutputStream outputStream, FilterOptions filterOptions) throws IOException {
        this(outputStream, filterOptions, 4);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions filterOptions, int i) throws IOException {
        this(outputStream, new FilterOptions[]{filterOptions}, i);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions[] filterOptionsArr) throws IOException {
        this(outputStream, filterOptionsArr, 4);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions[] filterOptionsArr, int i) throws IOException {
        this.streamFlags = new StreamFlags();
        this.index = new IndexEncoder();
        this.blockEncoder = null;
        this.exception = null;
        this.finished = false;
        this.tempBuf = new byte[1];
        this.out = outputStream;
        updateFilters(filterOptionsArr);
        this.streamFlags.checkType = i;
        this.check = Check.getInstance(i);
        encodeStreamHeader();
    }

    private void encodeStreamFlags(byte[] bArr, int i) {
        bArr[i] = (byte) 0;
        bArr[i + 1] = (byte) this.streamFlags.checkType;
    }

    private void encodeStreamFooter() throws IOException {
        byte[] bArr = new byte[6];
        long indexSize = (this.index.getIndexSize() / 4) - 1;
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) ((int) (indexSize >>> (i * 8)));
        }
        encodeStreamFlags(bArr, 4);
        EncoderUtil.writeCRC32(this.out, bArr);
        this.out.write(bArr);
        this.out.write(XZ.FOOTER_MAGIC);
    }

    private void encodeStreamHeader() throws IOException {
        this.out.write(XZ.HEADER_MAGIC);
        byte[] bArr = new byte[2];
        encodeStreamFlags(bArr, 0);
        this.out.write(bArr);
        EncoderUtil.writeCRC32(this.out, bArr);
    }

    public void close() throws IOException {
        if (this.out != null) {
            try {
                finish();
            } catch (IOException e) {
            }
            try {
                this.out.close();
            } catch (IOException e2) {
                if (this.exception == null) {
                    this.exception = e2;
                }
            }
            this.out = null;
        }
        if (this.exception != null) {
            throw this.exception;
        }
    }

    public void endBlock() throws IOException {
        if (this.exception != null) {
            throw this.exception;
        } else if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        } else if (this.blockEncoder != null) {
            try {
                this.blockEncoder.finish();
                this.index.add(this.blockEncoder.getUnpaddedSize(), this.blockEncoder.getUncompressedSize());
                this.blockEncoder = null;
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void finish() throws IOException {
        if (!this.finished) {
            endBlock();
            try {
                this.index.encode(this.out);
                encodeStreamFooter();
                this.finished = true;
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void flush() throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:20:0x002d in {3, 7, 14, 15, 17, 19, 23} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r2 = this;
        r0 = r2.exception;
        if (r0 == 0) goto L_0x0007;
    L_0x0004:
        r0 = r2.exception;
        throw r0;
    L_0x0007:
        r0 = r2.finished;
        if (r0 == 0) goto L_0x0013;
    L_0x000b:
        r0 = new org.tukaani.xz.XZIOException;
        r1 = "Stream finished or closed";
        r0.<init>(r1);
        throw r0;
    L_0x0013:
        r0 = r2.blockEncoder;	 Catch:{ IOException -> 0x002e }
        if (r0 == 0) goto L_0x002a;	 Catch:{ IOException -> 0x002e }
    L_0x0017:
        r0 = r2.filtersSupportFlushing;	 Catch:{ IOException -> 0x002e }
        if (r0 == 0) goto L_0x0021;	 Catch:{ IOException -> 0x002e }
    L_0x001b:
        r0 = r2.blockEncoder;	 Catch:{ IOException -> 0x002e }
        r0.flush();	 Catch:{ IOException -> 0x002e }
        return;	 Catch:{ IOException -> 0x002e }
    L_0x0021:
        r2.endBlock();	 Catch:{ IOException -> 0x002e }
        r0 = r2.out;	 Catch:{ IOException -> 0x002e }
    L_0x0026:
        r0.flush();	 Catch:{ IOException -> 0x002e }
        return;	 Catch:{ IOException -> 0x002e }
    L_0x002a:
        r0 = r2.out;	 Catch:{ IOException -> 0x002e }
        goto L_0x0026;
        return;
    L_0x002e:
        r0 = move-exception;
        r2.exception = r0;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.XZOutputStream.flush():void");
    }

    public void updateFilters(FilterOptions filterOptions) throws XZIOException {
        updateFilters(new FilterOptions[]{filterOptions});
    }

    public void updateFilters(FilterOptions[] filterOptionsArr) throws XZIOException {
        if (this.blockEncoder != null) {
            throw new UnsupportedOptionsException("Changing filter options in the middle of a XZ Block not implemented");
        }
        if (filterOptionsArr.length >= 1) {
            if (filterOptionsArr.length <= 4) {
                this.filtersSupportFlushing = true;
                Object[] objArr = new FilterEncoder[filterOptionsArr.length];
                for (int i = 0; i < filterOptionsArr.length; i++) {
                    objArr[i] = filterOptionsArr[i].getFilterEncoder();
                    this.filtersSupportFlushing &= objArr[i].supportsFlushing();
                }
                RawCoder.validate(objArr);
                this.filters = objArr;
                return;
            }
        }
        throw new UnsupportedOptionsException("XZ filter chain must be 1-4 filters");
    }

    public void write(int i) throws IOException {
        this.tempBuf[0] = (byte) i;
        write(this.tempBuf, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (i >= 0 && i2 >= 0) {
            int i3 = i + i2;
            if (i3 >= 0) {
                if (i3 <= bArr.length) {
                    if (this.exception != null) {
                        throw this.exception;
                    } else if (this.finished) {
                        throw new XZIOException("Stream finished or closed");
                    } else {
                        try {
                            if (this.blockEncoder == null) {
                                this.blockEncoder = new BlockOutputStream(this.out, this.filters, this.check);
                            }
                            this.blockEncoder.write(bArr, i, i2);
                            return;
                        } catch (IOException e) {
                            this.exception = e;
                            throw e;
                        }
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
