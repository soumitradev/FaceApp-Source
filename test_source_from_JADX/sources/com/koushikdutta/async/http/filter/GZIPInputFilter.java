package com.koushikdutta.async.http.filter;

import android.support.v4.internal.view.SupportMenu;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.PushParser;
import com.koushikdutta.async.PushParser.ParseCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.DataCallback.NullDataCallback;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Locale;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public class GZIPInputFilter extends InflaterInputFilter {
    private static final int FCOMMENT = 16;
    private static final int FEXTRA = 4;
    private static final int FHCRC = 2;
    private static final int FNAME = 8;
    protected CRC32 crc = new CRC32();
    boolean mNeedsHeader = true;

    static short peekShort(byte[] src, int offset, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            return (short) ((src[offset] << 8) | (src[offset + 1] & 255));
        }
        return (short) ((src[offset + 1] << 8) | (src[offset] & 255));
    }

    public GZIPInputFilter() {
        super(new Inflater(true));
    }

    public static int unsignedToBytes(byte b) {
        return b & 255;
    }

    public void onDataAvailable(final DataEmitter emitter, ByteBufferList bb) {
        if (this.mNeedsHeader) {
            final PushParser parser = new PushParser(emitter);
            parser.readByteArray(10, new ParseCallback<byte[]>() {
                int flags;
                boolean hcrc;

                /* renamed from: com.koushikdutta.async.http.filter.GZIPInputFilter$1$1 */
                class C11611 implements ParseCallback<byte[]> {

                    /* renamed from: com.koushikdutta.async.http.filter.GZIPInputFilter$1$1$1 */
                    class C11601 implements ParseCallback<byte[]> {
                        C11601() {
                        }

                        public void parsed(byte[] buf) {
                            if (C11641.this.hcrc) {
                                GZIPInputFilter.this.crc.update(buf, 0, buf.length);
                            }
                            C11641.this.next();
                        }
                    }

                    C11611() {
                    }

                    public void parsed(byte[] header) {
                        if (C11641.this.hcrc) {
                            GZIPInputFilter.this.crc.update(header, 0, 2);
                        }
                        parser.readByteArray(GZIPInputFilter.peekShort(header, 0, ByteOrder.LITTLE_ENDIAN) & SupportMenu.USER_MASK, new C11601());
                    }
                }

                /* renamed from: com.koushikdutta.async.http.filter.GZIPInputFilter$1$2 */
                class C11622 implements DataCallback {
                    C11622() {
                    }

                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                        if (C11641.this.hcrc) {
                            while (bb.size() > 0) {
                                ByteBuffer b = bb.remove();
                                GZIPInputFilter.this.crc.update(b.array(), b.arrayOffset() + b.position(), b.remaining());
                                ByteBufferList.reclaim(b);
                            }
                        }
                        bb.recycle();
                        C11641.this.done();
                    }
                }

                /* renamed from: com.koushikdutta.async.http.filter.GZIPInputFilter$1$3 */
                class C11633 implements ParseCallback<byte[]> {
                    C11633() {
                    }

                    public void parsed(byte[] header) {
                        if (((short) ((int) GZIPInputFilter.this.crc.getValue())) != GZIPInputFilter.peekShort(header, 0, ByteOrder.LITTLE_ENDIAN)) {
                            GZIPInputFilter.this.report(new IOException("CRC mismatch"));
                            return;
                        }
                        GZIPInputFilter.this.crc.reset();
                        GZIPInputFilter.this.mNeedsHeader = false;
                        GZIPInputFilter.this.setDataEmitter(emitter);
                    }
                }

                public void parsed(byte[] header) {
                    boolean z = true;
                    if (GZIPInputFilter.peekShort(header, 0, ByteOrder.LITTLE_ENDIAN) != (short) -29921) {
                        GZIPInputFilter.this.report(new IOException(String.format(Locale.ENGLISH, "unknown format (magic number %x)", new Object[]{Short.valueOf(magic)})));
                        emitter.setDataCallback(new NullDataCallback());
                        return;
                    }
                    this.flags = header[3];
                    if ((this.flags & 2) == 0) {
                        z = false;
                    }
                    this.hcrc = z;
                    if (this.hcrc) {
                        GZIPInputFilter.this.crc.update(header, 0, header.length);
                    }
                    if ((this.flags & 4) != 0) {
                        parser.readByteArray(2, new C11611());
                    } else {
                        next();
                    }
                }

                private void next() {
                    PushParser parser = new PushParser(emitter);
                    DataCallback summer = new C11622();
                    if ((this.flags & 8) != 0) {
                        parser.until((byte) 0, summer);
                    } else if ((this.flags & 16) != 0) {
                        parser.until((byte) 0, summer);
                    } else {
                        done();
                    }
                }

                private void done() {
                    if (this.hcrc) {
                        parser.readByteArray(2, new C11633());
                        return;
                    }
                    GZIPInputFilter.this.mNeedsHeader = false;
                    GZIPInputFilter.this.setDataEmitter(emitter);
                }
            });
            return;
        }
        super.onDataAvailable(emitter, bb);
    }
}
