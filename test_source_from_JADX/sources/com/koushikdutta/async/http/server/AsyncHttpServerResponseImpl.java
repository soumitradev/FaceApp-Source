package com.koushikdutta.async.http.server;

import android.text.TextUtils;
import com.badlogic.gdx.net.HttpStatus;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.CompletedCallback.NullCompletedCallback;
import com.koushikdutta.async.callback.DataCallback.NullDataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.HttpUtil;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.filter.ChunkedOutputFilter;
import com.koushikdutta.async.util.StreamUtility;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import org.json.JSONObject;

public class AsyncHttpServerResponseImpl implements AsyncHttpServerResponse {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    CompletedCallback closedCallback;
    int code = 200;
    boolean ended;
    boolean headWritten = false;
    private long mContentLength = -1;
    boolean mEnded;
    private Headers mRawHeaders = new Headers();
    AsyncHttpServerRequestImpl mRequest;
    DataSink mSink;
    AsyncSocket mSocket;
    WritableCallback writable;

    /* renamed from: com.koushikdutta.async.http.server.AsyncHttpServerResponseImpl$2 */
    class C11762 implements CompletedCallback {
        C11762() {
        }

        public void onCompleted(Exception ex) {
            AsyncHttpServerResponseImpl.this.onEnd();
        }
    }

    public Headers getHeaders() {
        return this.mRawHeaders;
    }

    public AsyncSocket getSocket() {
        return this.mSocket;
    }

    AsyncHttpServerResponseImpl(AsyncSocket socket, AsyncHttpServerRequestImpl req) {
        this.mSocket = socket;
        this.mRequest = req;
        if (HttpUtil.isKeepAlive(Protocol.HTTP_1_1, req.getHeaders())) {
            this.mRawHeaders.set("Connection", "Keep-Alive");
        }
    }

    public void write(ByteBufferList bb) {
        if (!this.headWritten) {
            initFirstWrite();
        }
        if (bb.remaining() != 0 && this.mSink != null) {
            this.mSink.write(bb);
        }
    }

    void initFirstWrite() {
        if (!this.headWritten) {
            boolean isChunked;
            this.headWritten = true;
            String currentEncoding = this.mRawHeaders.get("Transfer-Encoding");
            if ("".equals(currentEncoding)) {
                this.mRawHeaders.removeAll("Transfer-Encoding");
            }
            boolean canUseChunked = ("Chunked".equalsIgnoreCase(currentEncoding) || currentEncoding == null) && !"close".equalsIgnoreCase(this.mRawHeaders.get("Connection"));
            if (this.mContentLength < 0) {
                String contentLength = this.mRawHeaders.get("Content-Length");
                if (!TextUtils.isEmpty(contentLength)) {
                    this.mContentLength = Long.valueOf(contentLength).longValue();
                }
            }
            if (this.mContentLength >= 0 || !canUseChunked) {
                isChunked = false;
            } else {
                this.mRawHeaders.set("Transfer-Encoding", "Chunked");
                isChunked = true;
            }
            Util.writeAll(this.mSocket, this.mRawHeaders.toPrefixString(String.format(Locale.ENGLISH, "HTTP/1.1 %s %s", new Object[]{Integer.valueOf(this.code), AsyncHttpServer.getResponseCodeDescription(this.code)})).getBytes(), new CompletedCallback() {

                /* renamed from: com.koushikdutta.async.http.server.AsyncHttpServerResponseImpl$1$1 */
                class C06601 implements Runnable {
                    C06601() {
                    }

                    public void run() {
                        WritableCallback wb = AsyncHttpServerResponseImpl.this.getWriteableCallback();
                        if (wb != null) {
                            wb.onWriteable();
                        }
                    }
                }

                public void onCompleted(Exception ex) {
                    if (ex != null) {
                        AsyncHttpServerResponseImpl.this.report(ex);
                        return;
                    }
                    if (isChunked) {
                        ChunkedOutputFilter chunked = new ChunkedOutputFilter(AsyncHttpServerResponseImpl.this.mSocket);
                        chunked.setMaxBuffer(0);
                        AsyncHttpServerResponseImpl.this.mSink = chunked;
                    } else {
                        AsyncHttpServerResponseImpl.this.mSink = AsyncHttpServerResponseImpl.this.mSocket;
                    }
                    AsyncHttpServerResponseImpl.this.mSink.setClosedCallback(AsyncHttpServerResponseImpl.this.closedCallback);
                    AsyncHttpServerResponseImpl.this.closedCallback = null;
                    AsyncHttpServerResponseImpl.this.mSink.setWriteableCallback(AsyncHttpServerResponseImpl.this.writable);
                    AsyncHttpServerResponseImpl.this.writable = null;
                    if (AsyncHttpServerResponseImpl.this.ended) {
                        AsyncHttpServerResponseImpl.this.end();
                    } else {
                        AsyncHttpServerResponseImpl.this.getServer().post(new C06601());
                    }
                }
            });
        }
    }

    public void setWriteableCallback(WritableCallback handler) {
        if (this.mSink != null) {
            this.mSink.setWriteableCallback(handler);
        } else {
            this.writable = handler;
        }
    }

    public WritableCallback getWriteableCallback() {
        if (this.mSink != null) {
            return this.mSink.getWriteableCallback();
        }
        return this.writable;
    }

    public void end() {
        if (!this.ended) {
            this.ended = true;
            if (!this.headWritten || this.mSink != null) {
                if (!this.headWritten) {
                    this.mRawHeaders.remove("Transfer-Encoding");
                }
                if (this.mSink instanceof ChunkedOutputFilter) {
                    ((ChunkedOutputFilter) this.mSink).setMaxBuffer(Integer.MAX_VALUE);
                    this.mSink.write(new ByteBufferList());
                    onEnd();
                } else if (this.headWritten) {
                    onEnd();
                } else if (this.mRequest.getMethod().equalsIgnoreCase("HEAD")) {
                    writeHead();
                    onEnd();
                } else {
                    send("text/html", "");
                }
            }
        }
    }

    public void writeHead() {
        initFirstWrite();
    }

    public void setContentType(String contentType) {
        this.mRawHeaders.set("Content-Type", contentType);
    }

    public void send(String contentType, byte[] bytes) {
        this.mContentLength = (long) bytes.length;
        this.mRawHeaders.set("Content-Length", Integer.toString(bytes.length));
        this.mRawHeaders.set("Content-Type", contentType);
        Util.writeAll((DataSink) this, bytes, new C11762());
    }

    public void send(String contentType, String string) {
        try {
            send(contentType, string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    protected void onEnd() {
        this.mEnded = true;
    }

    protected void report(Exception e) {
    }

    public void send(String string) {
        String contentType = this.mRawHeaders.get("Content-Type");
        if (contentType == null) {
            contentType = "text/html; charset=utf-8";
        }
        send(contentType, string);
    }

    public void send(JSONObject json) {
        send("application/json; charset=utf-8", json.toString());
    }

    public void sendStream(InputStream inputStream, long totalLength) {
        final InputStream inputStream2 = inputStream;
        long start = 0;
        long end = totalLength - 1;
        String range = this.mRequest.getHeaders().get("Range");
        if (range != null) {
            String[] parts = range.split("=");
            if (parts.length == 2) {
                if ("bytes".equals(parts[0])) {
                    String[] parts2 = parts[1].split("-");
                    long end2;
                    try {
                        if (parts2.length > 2) {
                            throw new MalformedRangeException();
                        }
                        if (!TextUtils.isEmpty(parts2[0])) {
                            start = Long.parseLong(parts2[0]);
                        }
                        if (parts2.length != 2 || TextUtils.isEmpty(parts2[1])) {
                            end2 = totalLength - 1;
                        } else {
                            end2 = Long.parseLong(parts2[1]);
                        }
                        end = end2;
                        code(HttpStatus.SC_PARTIAL_CONTENT);
                        getHeaders().set("Content-Range", String.format(Locale.ENGLISH, "bytes %d-%d/%d", new Object[]{Long.valueOf(start), Long.valueOf(end), Long.valueOf(totalLength)}));
                    } catch (Exception e) {
                        parts = end;
                        end2 = 0;
                        start = e;
                        code(HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                        end();
                        return;
                    }
                }
            }
            code(HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
            end();
            return;
        }
        try {
            if (start != inputStream2.skip(start)) {
                throw new StreamSkipException("skip failed to skip requested amount");
            }
            r1.mContentLength = (end - start) + 1;
            r1.mRawHeaders.set("Content-Length", String.valueOf(r1.mContentLength));
            r1.mRawHeaders.set("Accept-Ranges", "bytes");
            if (r1.mRequest.getMethod().equals("HEAD")) {
                writeHead();
                onEnd();
                return;
            }
            Util.pump(inputStream2, r1.mContentLength, r1, new CompletedCallback() {
                public void onCompleted(Exception ex) {
                    StreamUtility.closeQuietly(inputStream2);
                    AsyncHttpServerResponseImpl.this.onEnd();
                }
            });
        } catch (Exception e2) {
            code(500);
            end();
        }
    }

    public void sendFile(File file) {
        try {
            if (this.mRawHeaders.get("Content-Type") == null) {
                this.mRawHeaders.set("Content-Type", AsyncHttpServer.getContentType(file.getAbsolutePath()));
            }
            sendStream(new BufferedInputStream(new FileInputStream(file), SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_DEFAULT), file.length());
        } catch (FileNotFoundException e) {
            code(HttpStatus.SC_NOT_FOUND);
            end();
        }
    }

    public void proxy(final AsyncHttpResponse remoteResponse) {
        code(remoteResponse.code());
        remoteResponse.headers().removeAll("Transfer-Encoding");
        remoteResponse.headers().removeAll("Content-Encoding");
        remoteResponse.headers().removeAll("Connection");
        getHeaders().addAll(remoteResponse.headers());
        remoteResponse.headers().set("Connection", "close");
        Util.pump((DataEmitter) remoteResponse, (DataSink) this, new CompletedCallback() {
            public void onCompleted(Exception ex) {
                remoteResponse.setEndCallback(new NullCompletedCallback());
                remoteResponse.setDataCallback(new NullDataCallback());
                AsyncHttpServerResponseImpl.this.end();
            }
        });
    }

    public AsyncHttpServerResponse code(int code) {
        this.code = code;
        return this;
    }

    public int code() {
        return this.code;
    }

    public void redirect(String location) {
        code(HttpStatus.SC_MOVED_TEMPORARILY);
        this.mRawHeaders.set("Location", location);
        end();
    }

    public void onCompleted(Exception ex) {
        end();
    }

    public boolean isOpen() {
        if (this.mSink != null) {
            return this.mSink.isOpen();
        }
        return this.mSocket.isOpen();
    }

    public void setClosedCallback(CompletedCallback handler) {
        if (this.mSink != null) {
            this.mSink.setClosedCallback(handler);
        } else {
            this.closedCallback = handler;
        }
    }

    public CompletedCallback getClosedCallback() {
        if (this.mSink != null) {
            return this.mSink.getClosedCallback();
        }
        return this.closedCallback;
    }

    public AsyncServer getServer() {
        return this.mSocket.getServer();
    }

    public String toString() {
        if (this.mRawHeaders == null) {
            return super.toString();
        }
        return this.mRawHeaders.toPrefixString(String.format(Locale.ENGLISH, "HTTP/1.1 %s %s", new Object[]{Integer.valueOf(this.code), AsyncHttpServer.getResponseCodeDescription(this.code)}));
    }
}
