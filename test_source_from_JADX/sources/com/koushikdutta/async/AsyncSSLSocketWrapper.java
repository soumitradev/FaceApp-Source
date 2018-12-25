package com.koushikdutta.async;

import android.os.Build.VERSION;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.DataCallback.NullDataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.util.Allocator;
import com.koushikdutta.async.wrapper.AsyncSocketWrapper;
import java.nio.ByteBuffer;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class AsyncSSLSocketWrapper implements AsyncSocketWrapper, AsyncSSLSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static SSLContext defaultSSLContext;
    boolean clientMode;
    final DataCallback dataCallback = new C10845();
    SSLEngine engine;
    boolean finishedHandshake;
    HandshakeCallback handshakeCallback;
    HostnameVerifier hostnameVerifier;
    DataCallback mDataCallback;
    CompletedCallback mEndCallback;
    Exception mEndException;
    boolean mEnded;
    private String mHost;
    private int mPort;
    BufferedDataSink mSink;
    AsyncSocket mSocket;
    boolean mUnwrapping;
    private boolean mWrapping;
    WritableCallback mWriteableCallback;
    X509Certificate[] peerCertificates;
    final ByteBufferList pending = new ByteBufferList();
    TrustManager[] trustManagers;
    ByteBufferList writeList = new ByteBufferList();

    /* renamed from: com.koushikdutta.async.AsyncSSLSocketWrapper$1 */
    static class C06271 implements X509TrustManager {
        C06271() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
            for (X509Certificate cert : certs) {
                if (!(cert == null || cert.getCriticalExtensionOIDs() == null)) {
                    cert.getCriticalExtensionOIDs().remove("2.5.29.15");
                }
            }
        }
    }

    /* renamed from: com.koushikdutta.async.AsyncSSLSocketWrapper$6 */
    class C06286 implements Runnable {
        C06286() {
        }

        public void run() {
            if (AsyncSSLSocketWrapper.this.mWriteableCallback != null) {
                AsyncSSLSocketWrapper.this.mWriteableCallback.onWriteable();
            }
        }
    }

    public interface HandshakeCallback {
        void onHandshakeCompleted(Exception exception, AsyncSSLSocket asyncSSLSocket);
    }

    /* renamed from: com.koushikdutta.async.AsyncSSLSocketWrapper$3 */
    class C10823 implements WritableCallback {
        C10823() {
        }

        public void onWriteable() {
            if (AsyncSSLSocketWrapper.this.mWriteableCallback != null) {
                AsyncSSLSocketWrapper.this.mWriteableCallback.onWriteable();
            }
        }
    }

    /* renamed from: com.koushikdutta.async.AsyncSSLSocketWrapper$4 */
    class C10834 implements CompletedCallback {
        C10834() {
        }

        public void onCompleted(Exception ex) {
            if (!AsyncSSLSocketWrapper.this.mEnded) {
                AsyncSSLSocketWrapper.this.mEnded = true;
                AsyncSSLSocketWrapper.this.mEndException = ex;
                if (!(AsyncSSLSocketWrapper.this.pending.hasRemaining() || AsyncSSLSocketWrapper.this.mEndCallback == null)) {
                    AsyncSSLSocketWrapper.this.mEndCallback.onCompleted(ex);
                }
            }
        }
    }

    /* renamed from: com.koushikdutta.async.AsyncSSLSocketWrapper$5 */
    class C10845 implements DataCallback {
        final Allocator allocator = new Allocator().setMinAlloc(8192);
        final ByteBufferList buffered = new ByteBufferList();

        C10845() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onDataAvailable(com.koushikdutta.async.DataEmitter r11, com.koushikdutta.async.ByteBufferList r12) {
            /*
            r10 = this;
            r0 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;
            r0 = r0.mUnwrapping;
            if (r0 == 0) goto L_0x0007;
        L_0x0006:
            return;
        L_0x0007:
            r0 = 0;
            r1 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;	 Catch:{ SSLException -> 0x00d4 }
            r2 = 1;
            r1.mUnwrapping = r2;	 Catch:{ SSLException -> 0x00d4 }
            r1 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r12.get(r1);	 Catch:{ SSLException -> 0x00d4 }
            r1 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r1 = r1.hasRemaining();	 Catch:{ SSLException -> 0x00d4 }
            if (r1 == 0) goto L_0x0025;
        L_0x001a:
            r1 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r1 = r1.getAll();	 Catch:{ SSLException -> 0x00d4 }
            r3 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r3.add(r1);	 Catch:{ SSLException -> 0x00d4 }
        L_0x0025:
            r1 = com.koushikdutta.async.ByteBufferList.EMPTY_BYTEBUFFER;	 Catch:{ SSLException -> 0x00d4 }
        L_0x0027:
            r3 = r1.remaining();	 Catch:{ SSLException -> 0x00d4 }
            if (r3 != 0) goto L_0x003c;
        L_0x002d:
            r3 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r3 = r3.size();	 Catch:{ SSLException -> 0x00d4 }
            if (r3 <= 0) goto L_0x003c;
        L_0x0035:
            r3 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r3 = r3.remove();	 Catch:{ SSLException -> 0x00d4 }
            r1 = r3;
        L_0x003c:
            r3 = r1.remaining();	 Catch:{ SSLException -> 0x00d4 }
            r4 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;	 Catch:{ SSLException -> 0x00d4 }
            r4 = r4.pending;	 Catch:{ SSLException -> 0x00d4 }
            r4 = r4.remaining();	 Catch:{ SSLException -> 0x00d4 }
            r5 = r10.allocator;	 Catch:{ SSLException -> 0x00d4 }
            r5 = r5.allocate();	 Catch:{ SSLException -> 0x00d4 }
            r6 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;	 Catch:{ SSLException -> 0x00d4 }
            r6 = r6.engine;	 Catch:{ SSLException -> 0x00d4 }
            r6 = r6.unwrap(r1, r5);	 Catch:{ SSLException -> 0x00d4 }
            r7 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;	 Catch:{ SSLException -> 0x00d4 }
            r8 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;	 Catch:{ SSLException -> 0x00d4 }
            r8 = r8.pending;	 Catch:{ SSLException -> 0x00d4 }
            r7.addToPending(r8, r5);	 Catch:{ SSLException -> 0x00d4 }
            r7 = r10.allocator;	 Catch:{ SSLException -> 0x00d4 }
            r8 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;	 Catch:{ SSLException -> 0x00d4 }
            r8 = r8.pending;	 Catch:{ SSLException -> 0x00d4 }
            r8 = r8.remaining();	 Catch:{ SSLException -> 0x00d4 }
            r8 = r8 - r4;
            r8 = (long) r8;	 Catch:{ SSLException -> 0x00d4 }
            r7.track(r8);	 Catch:{ SSLException -> 0x00d4 }
            r5 = r6.getStatus();	 Catch:{ SSLException -> 0x00d4 }
            r7 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW;	 Catch:{ SSLException -> 0x00d4 }
            if (r5 != r7) goto L_0x0085;
        L_0x0076:
            r5 = r10.allocator;	 Catch:{ SSLException -> 0x00d4 }
            r7 = r10.allocator;	 Catch:{ SSLException -> 0x00d4 }
            r7 = r7.getMinAlloc();	 Catch:{ SSLException -> 0x00d4 }
            r7 = r7 * 2;
            r5.setMinAlloc(r7);	 Catch:{ SSLException -> 0x00d4 }
            r3 = -1;
            goto L_0x00ab;
        L_0x0085:
            r5 = r6.getStatus();	 Catch:{ SSLException -> 0x00d4 }
            r7 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW;	 Catch:{ SSLException -> 0x00d4 }
            if (r5 != r7) goto L_0x00ab;
        L_0x008d:
            r5 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r5.addFirst(r1);	 Catch:{ SSLException -> 0x00d4 }
            r5 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r5 = r5.size();	 Catch:{ SSLException -> 0x00d4 }
            if (r5 > r2) goto L_0x009b;
        L_0x009a:
            goto L_0x00ca;
        L_0x009b:
            r3 = -1;
            r5 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r5 = r5.getAll();	 Catch:{ SSLException -> 0x00d4 }
            r1 = r5;
            r5 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r5.addFirst(r1);	 Catch:{ SSLException -> 0x00d4 }
            r5 = com.koushikdutta.async.ByteBufferList.EMPTY_BYTEBUFFER;	 Catch:{ SSLException -> 0x00d4 }
            r1 = r5;
        L_0x00ab:
            r5 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;	 Catch:{ SSLException -> 0x00d4 }
            r7 = r6.getHandshakeStatus();	 Catch:{ SSLException -> 0x00d4 }
            r5.handleHandshakeStatus(r7);	 Catch:{ SSLException -> 0x00d4 }
            r5 = r1.remaining();	 Catch:{ SSLException -> 0x00d4 }
            if (r5 != r3) goto L_0x00d0;
        L_0x00ba:
            r5 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;	 Catch:{ SSLException -> 0x00d4 }
            r5 = r5.pending;	 Catch:{ SSLException -> 0x00d4 }
            r5 = r5.remaining();	 Catch:{ SSLException -> 0x00d4 }
            if (r4 != r5) goto L_0x00d0;
        L_0x00c4:
            r2 = r10.buffered;	 Catch:{ SSLException -> 0x00d4 }
            r2.addFirst(r1);	 Catch:{ SSLException -> 0x00d4 }
        L_0x00ca:
            r2 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;	 Catch:{ SSLException -> 0x00d4 }
            r2.onDataAvailable();	 Catch:{ SSLException -> 0x00d4 }
            goto L_0x00dd;
        L_0x00d0:
            goto L_0x0027;
        L_0x00d2:
            r1 = move-exception;
            goto L_0x00e3;
        L_0x00d4:
            r1 = move-exception;
            r1.printStackTrace();	 Catch:{ all -> 0x00d2 }
            r2 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;	 Catch:{ all -> 0x00d2 }
            r2.report(r1);	 Catch:{ all -> 0x00d2 }
        L_0x00dd:
            r1 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;
            r1.mUnwrapping = r0;
            return;
        L_0x00e3:
            r2 = com.koushikdutta.async.AsyncSSLSocketWrapper.this;
            r2.mUnwrapping = r0;
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.AsyncSSLSocketWrapper.5.onDataAvailable(com.koushikdutta.async.DataEmitter, com.koushikdutta.async.ByteBufferList):void");
        }
    }

    static {
        try {
            if (VERSION.SDK_INT <= 15) {
                throw new Exception();
            }
            defaultSSLContext = SSLContext.getInstance("Default");
        } catch (Exception ex) {
            try {
                defaultSSLContext = SSLContext.getInstance("TLS");
                defaultSSLContext.init(null, new TrustManager[]{new C06271()}, null);
            } catch (Exception ex2) {
                ex.printStackTrace();
                ex2.printStackTrace();
            }
        }
    }

    public static SSLContext getDefaultSSLContext() {
        return defaultSSLContext;
    }

    public static void handshake(AsyncSocket socket, String host, int port, SSLEngine sslEngine, TrustManager[] trustManagers, HostnameVerifier verifier, boolean clientMode, HandshakeCallback callback) {
        final HandshakeCallback handshakeCallback = callback;
        AsyncSSLSocketWrapper wrapper = new AsyncSSLSocketWrapper(socket, host, port, sslEngine, trustManagers, verifier, clientMode);
        wrapper.handshakeCallback = handshakeCallback;
        socket.setClosedCallback(new CompletedCallback() {
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    handshakeCallback.onHandshakeCompleted(ex, null);
                } else {
                    handshakeCallback.onHandshakeCompleted(new SSLException("socket closed during handshake"), null);
                }
            }
        });
        try {
            wrapper.engine.beginHandshake();
            wrapper.handleHandshakeStatus(wrapper.engine.getHandshakeStatus());
        } catch (SSLException e) {
            wrapper.report(e);
        }
    }

    private AsyncSSLSocketWrapper(AsyncSocket socket, String host, int port, SSLEngine sslEngine, TrustManager[] trustManagers, HostnameVerifier verifier, boolean clientMode) {
        this.mSocket = socket;
        this.hostnameVerifier = verifier;
        this.clientMode = clientMode;
        this.trustManagers = trustManagers;
        this.engine = sslEngine;
        this.mHost = host;
        this.mPort = port;
        this.engine.setUseClientMode(clientMode);
        this.mSink = new BufferedDataSink(socket);
        this.mSink.setWriteableCallback(new C10823());
        this.mSocket.setEndCallback(new C10834());
        this.mSocket.setDataCallback(this.dataCallback);
    }

    public void onDataAvailable() {
        Util.emitAllData(this, this.pending);
        if (this.mEnded && !this.pending.hasRemaining() && this.mEndCallback != null) {
            this.mEndCallback.onCompleted(this.mEndException);
        }
    }

    public SSLEngine getSSLEngine() {
        return this.engine;
    }

    void addToPending(ByteBufferList out, ByteBuffer mReadTmp) {
        mReadTmp.flip();
        if (mReadTmp.hasRemaining()) {
            out.add(mReadTmp);
        } else {
            ByteBufferList.reclaim(mReadTmp);
        }
    }

    public void end() {
        this.mSocket.end();
    }

    public String getHost() {
        return this.mHost;
    }

    public int getPort() {
        return this.mPort;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleHandshakeStatus(javax.net.ssl.SSLEngineResult.HandshakeStatus r15) {
        /*
        r14 = this;
        r0 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_TASK;
        if (r15 != r0) goto L_0x000d;
    L_0x0004:
        r0 = r14.engine;
        r0 = r0.getDelegatedTask();
        r0.run();
    L_0x000d:
        r0 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP;
        if (r15 != r0) goto L_0x0016;
    L_0x0011:
        r0 = r14.writeList;
        r14.write(r0);
    L_0x0016:
        r0 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
        if (r15 != r0) goto L_0x0024;
    L_0x001a:
        r0 = r14.dataCallback;
        r1 = new com.koushikdutta.async.ByteBufferList;
        r1.<init>();
        r0.onDataAvailable(r14, r1);
    L_0x0024:
        r0 = r14.finishedHandshake;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        if (r0 != 0) goto L_0x0112;
    L_0x0028:
        r0 = r14.engine;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r0 = r0.getHandshakeStatus();	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r1 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        if (r0 == r1) goto L_0x003c;
    L_0x0032:
        r0 = r14.engine;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r0 = r0.getHandshakeStatus();	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r1 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        if (r0 != r1) goto L_0x0112;
    L_0x003c:
        r0 = r14.clientMode;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r1 = 1;
        r2 = 0;
        if (r0 == 0) goto L_0x00eb;
    L_0x0042:
        r0 = r14.trustManagers;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        if (r0 != 0) goto L_0x0059;
    L_0x0046:
        r3 = javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm();	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r3 = javax.net.ssl.TrustManagerFactory.getInstance(r3);	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r4 = r2;
        r4 = (java.security.KeyStore) r4;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r3.init(r4);	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r4 = r3.getTrustManagers();	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r0 = r4;
    L_0x0059:
        r3 = 0;
        r4 = 0;
        r5 = r0.length;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r6 = 0;
        r7 = r4;
        r4 = 0;
    L_0x005f:
        if (r4 >= r5) goto L_0x00d7;
    L_0x0061:
        r8 = r0[r4];	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r9 = r8;
        r9 = (javax.net.ssl.X509TrustManager) r9;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r10 = r14.engine;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r10 = r10.getSession();	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r10 = r10.getPeerCertificates();	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r10 = (java.security.cert.X509Certificate[]) r10;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r14.peerCertificates = r10;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r10 = r14.peerCertificates;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r11 = "SSL";
        r9.checkServerTrusted(r10, r11);	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r10 = r14.mHost;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        if (r10 == 0) goto L_0x00cc;
    L_0x007f:
        r10 = r14.hostnameVerifier;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        if (r10 != 0) goto L_0x009e;
    L_0x0083:
        r10 = new org.apache.http.conn.ssl.StrictHostnameVerifier;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r10.<init>();	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r11 = r14.mHost;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r12 = r14.peerCertificates;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r12 = r12[r6];	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r12 = org.apache.http.conn.ssl.StrictHostnameVerifier.getCNs(r12);	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r13 = r14.peerCertificates;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r13 = r13[r6];	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r13 = org.apache.http.conn.ssl.StrictHostnameVerifier.getDNSSubjectAlts(r13);	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r10.verify(r11, r12, r13);	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        goto L_0x00cc;
    L_0x009e:
        r10 = r14.hostnameVerifier;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r11 = r14.mHost;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r12 = r14.engine;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r12 = r12.getSession();	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r10 = r10.verify(r11, r12);	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        if (r10 != 0) goto L_0x00cc;
    L_0x00ae:
        r10 = new javax.net.ssl.SSLException;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r11 = new java.lang.StringBuilder;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r11.<init>();	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r12 = "hostname <";
        r11.append(r12);	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r12 = r14.mHost;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r11.append(r12);	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r12 = "> has been denied";
        r11.append(r12);	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r11 = r11.toString();	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        r10.<init>(r11);	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
        throw r10;	 Catch:{ GeneralSecurityException -> 0x00d1, SSLException -> 0x00ce, NoSuchAlgorithmException -> 0x0114, AsyncSSLException -> 0x0109 }
    L_0x00cc:
        r3 = 1;
        goto L_0x00d7;
    L_0x00ce:
        r9 = move-exception;
        r7 = r9;
        goto L_0x00d4;
    L_0x00d1:
        r9 = move-exception;
        r7 = r9;
    L_0x00d4:
        r4 = r4 + 1;
        goto L_0x005f;
    L_0x00d7:
        r14.finishedHandshake = r1;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        if (r3 != 0) goto L_0x00ea;
    L_0x00db:
        r1 = new com.koushikdutta.async.AsyncSSLException;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r1.<init>(r7);	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r14.report(r1);	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r4 = r1.getIgnore();	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        if (r4 != 0) goto L_0x00ea;
    L_0x00e9:
        throw r1;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
    L_0x00ea:
        goto L_0x00ed;
    L_0x00eb:
        r14.finishedHandshake = r1;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
    L_0x00ed:
        r0 = r14.handshakeCallback;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r0.onHandshakeCompleted(r2, r14);	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r14.handshakeCallback = r2;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r0 = r14.mSocket;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r0.setClosedCallback(r2);	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r0 = r14.getServer();	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r1 = new com.koushikdutta.async.AsyncSSLSocketWrapper$6;	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r1.<init>();	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r0.post(r1);	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        r14.onDataAvailable();	 Catch:{ NoSuchAlgorithmException -> 0x0114, GeneralSecurityException -> 0x010e, AsyncSSLException -> 0x0109 }
        goto L_0x0112;
    L_0x0109:
        r0 = move-exception;
        r14.report(r0);
        goto L_0x0113;
    L_0x010e:
        r0 = move-exception;
        r14.report(r0);
    L_0x0113:
        return;
    L_0x0114:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r1.<init>(r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.AsyncSSLSocketWrapper.handleHandshakeStatus(javax.net.ssl.SSLEngineResult$HandshakeStatus):void");
    }

    int calculateAlloc(int remaining) {
        int alloc = (remaining * 3) / 2;
        if (alloc == 0) {
            return 8192;
        }
        return alloc;
    }

    public void write(ByteBufferList bb) {
        if (!this.mWrapping && this.mSink.remaining() <= 0) {
            this.mWrapping = true;
            SSLEngineResult res = null;
            ByteBuffer writeBuf = ByteBufferList.obtain(calculateAlloc(bb.remaining()));
            do {
                if (!this.finishedHandshake || bb.remaining() != 0) {
                    int remaining = bb.remaining();
                    try {
                        ByteBuffer[] arr = bb.getAllArray();
                        res = this.engine.wrap(arr, writeBuf);
                        bb.addAll(arr);
                        writeBuf.flip();
                        this.writeList.add(writeBuf);
                        if (this.writeList.remaining() > 0) {
                            this.mSink.write(this.writeList);
                        }
                        int previousCapacity = writeBuf.capacity();
                        if (res.getStatus() == Status.BUFFER_OVERFLOW) {
                            writeBuf = ByteBufferList.obtain(previousCapacity * 2);
                            remaining = -1;
                        } else {
                            writeBuf = ByteBufferList.obtain(calculateAlloc(bb.remaining()));
                            handleHandshakeStatus(res.getHandshakeStatus());
                        }
                    } catch (SSLException e) {
                        report(e);
                    }
                    if (remaining == bb.remaining() && (res == null || res.getHandshakeStatus() != HandshakeStatus.NEED_WRAP)) {
                        break;
                    }
                } else {
                    break;
                }
            } while (this.mSink.remaining() == 0);
            this.mWrapping = false;
            ByteBufferList.reclaim(writeBuf);
        }
    }

    public void setWriteableCallback(WritableCallback handler) {
        this.mWriteableCallback = handler;
    }

    public WritableCallback getWriteableCallback() {
        return this.mWriteableCallback;
    }

    private void report(Exception e) {
        HandshakeCallback hs = this.handshakeCallback;
        if (hs != null) {
            this.handshakeCallback = null;
            this.mSocket.setDataCallback(new NullDataCallback());
            this.mSocket.end();
            this.mSocket.setClosedCallback(null);
            this.mSocket.close();
            hs.onHandshakeCompleted(e, null);
            return;
        }
        CompletedCallback cb = getEndCallback();
        if (cb != null) {
            cb.onCompleted(e);
        }
    }

    public void setDataCallback(DataCallback callback) {
        this.mDataCallback = callback;
    }

    public DataCallback getDataCallback() {
        return this.mDataCallback;
    }

    public boolean isChunked() {
        return this.mSocket.isChunked();
    }

    public boolean isOpen() {
        return this.mSocket.isOpen();
    }

    public void close() {
        this.mSocket.close();
    }

    public void setClosedCallback(CompletedCallback handler) {
        this.mSocket.setClosedCallback(handler);
    }

    public CompletedCallback getClosedCallback() {
        return this.mSocket.getClosedCallback();
    }

    public void setEndCallback(CompletedCallback callback) {
        this.mEndCallback = callback;
    }

    public CompletedCallback getEndCallback() {
        return this.mEndCallback;
    }

    public void pause() {
        this.mSocket.pause();
    }

    public void resume() {
        this.mSocket.resume();
        onDataAvailable();
    }

    public boolean isPaused() {
        return this.mSocket.isPaused();
    }

    public AsyncServer getServer() {
        return this.mSocket.getServer();
    }

    public AsyncSocket getSocket() {
        return this.mSocket;
    }

    public DataEmitter getDataEmitter() {
        return this.mSocket;
    }

    public X509Certificate[] getPeerCertificates() {
        return this.peerCertificates;
    }

    public String charset() {
        return null;
    }
}
