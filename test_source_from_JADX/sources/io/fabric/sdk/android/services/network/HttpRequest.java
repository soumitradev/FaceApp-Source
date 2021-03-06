package io.fabric.sdk.android.services.network;

import com.badlogic.gdx.net.HttpStatus;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;
import kotlin.text.Typography;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class HttpRequest {
    private static final String BOUNDARY = "00content0boundary00";
    public static final String CHARSET_UTF8 = "UTF-8";
    private static ConnectionFactory CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data; boundary=00content0boundary00";
    private static final String CRLF = "\r\n";
    private static final String[] EMPTY_STRINGS = new String[0];
    public static final String ENCODING_GZIP = "gzip";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_DATE = "Date";
    public static final String HEADER_ETAG = "ETag";
    public static final String HEADER_EXPIRES = "Expires";
    public static final String HEADER_IF_NONE_MATCH = "If-None-Match";
    public static final String HEADER_LAST_MODIFIED = "Last-Modified";
    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";
    public static final String HEADER_REFERER = "Referer";
    public static final String HEADER_SERVER = "Server";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_TRACE = "TRACE";
    public static final String PARAM_CHARSET = "charset";
    private int bufferSize = 8192;
    private HttpURLConnection connection = null;
    private boolean form;
    private String httpProxyHost;
    private int httpProxyPort;
    private boolean ignoreCloseExceptions = true;
    private boolean multipart;
    private RequestOutputStream output;
    private final String requestMethod;
    private boolean uncompress = false;
    public final URL url;

    public static class Base64 {
        private static final byte EQUALS_SIGN = (byte) 61;
        private static final String PREFERRED_ENCODING = "US-ASCII";
        private static final byte[] _STANDARD_ALPHABET = new byte[]{GeneralMidiConstants.ALTO_SAX, GeneralMidiConstants.TENOR_SAX, GeneralMidiConstants.BARITONE_SAX, GeneralMidiConstants.OBOE, GeneralMidiConstants.ENGLISH_HORN, GeneralMidiConstants.BASSOON, GeneralMidiConstants.CLARINET, GeneralMidiConstants.PICCOLO, GeneralMidiConstants.FLUTE, GeneralMidiConstants.RECORDER, (byte) 75, (byte) 76, GeneralMidiConstants.SHAKUHACHI, GeneralMidiConstants.WHISTLE, GeneralMidiConstants.OCARINA, GeneralMidiConstants.LEAD_0_SQUARE, GeneralMidiConstants.LEAD_1_SAWTOOTH, GeneralMidiConstants.LEAD_2_CALLIOPE, (byte) 83, GeneralMidiConstants.LEAD_4_CHARANG, GeneralMidiConstants.LEAD_5_VOICE, GeneralMidiConstants.LEAD_6_FIFTHS, GeneralMidiConstants.LEAD_7_BASS_LEAD, (byte) 88, GeneralMidiConstants.PAD_1_WARM, GeneralMidiConstants.PAD_2_POLYSYNTH, GeneralMidiConstants.FX_1_SOUNDTRACK, GeneralMidiConstants.FX_2_CRYSTAL, GeneralMidiConstants.FX_3_ATMOSPHERE, GeneralMidiConstants.FX_4_BRIGHTNESS, GeneralMidiConstants.FX_5_GOBLINS, GeneralMidiConstants.FX_6_ECHOES, (byte) 103, GeneralMidiConstants.SIT_R, GeneralMidiConstants.BANJO, GeneralMidiConstants.SHAMISEN, GeneralMidiConstants.KOTO, GeneralMidiConstants.KALIMBA, GeneralMidiConstants.BAG_PIPE, GeneralMidiConstants.FIDDLE, GeneralMidiConstants.SHANAI, GeneralMidiConstants.TINKLE_BELL, GeneralMidiConstants.AGOGO, GeneralMidiConstants.STEEL_DRUMS, GeneralMidiConstants.WOODBLOCK, GeneralMidiConstants.TAIKO_DRUM, GeneralMidiConstants.MELODIC_TOM, GeneralMidiConstants.SYNTH_DRUM, GeneralMidiConstants.REVERSE_CYMBAL, (byte) 120, GeneralMidiConstants.BREATH_NOISE, GeneralMidiConstants.SEASHORE, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, GeneralMidiConstants.TRUMPET, GeneralMidiConstants.TROMBONE, GeneralMidiConstants.CONTRABASS, GeneralMidiConstants.TIMPANI};

        private Base64() {
        }

        private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset) {
            byte[] ALPHABET = _STANDARD_ALPHABET;
            int inBuff = 0;
            int i = (numSigBytes > 0 ? (source[srcOffset] << 24) >>> 8 : 0) | (numSigBytes > 1 ? (source[srcOffset + 1] << 24) >>> 16 : 0);
            if (numSigBytes > 2) {
                inBuff = (source[srcOffset + 2] << 24) >>> 24;
            }
            inBuff |= i;
            switch (numSigBytes) {
                case 1:
                    destination[destOffset] = ALPHABET[inBuff >>> 18];
                    destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                    destination[destOffset + 2] = (byte) 61;
                    destination[destOffset + 3] = (byte) 61;
                    return destination;
                case 2:
                    destination[destOffset] = ALPHABET[inBuff >>> 18];
                    destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                    destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 63];
                    destination[destOffset + 3] = (byte) 61;
                    return destination;
                case 3:
                    destination[destOffset] = ALPHABET[inBuff >>> 18];
                    destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                    destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 63];
                    destination[destOffset + 3] = ALPHABET[inBuff & 63];
                    return destination;
                default:
                    return destination;
            }
        }

        public static String encode(String string) {
            byte[] bytes;
            try {
                bytes = string.getBytes("US-ASCII");
            } catch (UnsupportedEncodingException e) {
                bytes = string.getBytes();
            }
            return encodeBytes(bytes);
        }

        public static String encodeBytes(byte[] source) {
            return encodeBytes(source, 0, source.length);
        }

        public static String encodeBytes(byte[] source, int off, int len) {
            byte[] encoded = encodeBytesToBytes(source, off, len);
            try {
                return new String(encoded, "US-ASCII");
            } catch (UnsupportedEncodingException e) {
                return new String(encoded);
            }
        }

        public static byte[] encodeBytesToBytes(byte[] source, int off, int len) {
            if (source == null) {
                throw new NullPointerException("Cannot serialize a null array.");
            } else if (off < 0) {
                r1 = new StringBuilder();
                r1.append("Cannot have negative offset: ");
                r1.append(off);
                throw new IllegalArgumentException(r1.toString());
            } else if (len < 0) {
                r1 = new StringBuilder();
                r1.append("Cannot have length offset: ");
                r1.append(len);
                throw new IllegalArgumentException(r1.toString());
            } else if (off + len > source.length) {
                throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Cannot have offset of %d and length of %d with array of length %d", new Object[]{Integer.valueOf(off), Integer.valueOf(len), Integer.valueOf(source.length)}));
            } else {
                int i = 4;
                int encLen = (len / 3) * 4;
                if (len % 3 <= 0) {
                    i = 0;
                }
                byte[] outBuff = new byte[(encLen + i)];
                int d = 0;
                int e = 0;
                int len2 = len - 2;
                while (d < len2) {
                    encode3to4(source, d + off, 3, outBuff, e);
                    d += 3;
                    e += 4;
                }
                if (d < len) {
                    encode3to4(source, d + off, len - d, outBuff, e);
                    e += 4;
                }
                if (e > outBuff.length - 1) {
                    return outBuff;
                }
                byte[] finalOut = new byte[e];
                System.arraycopy(outBuff, 0, finalOut, 0, e);
                return finalOut;
            }
        }
    }

    public interface ConnectionFactory {
        public static final ConnectionFactory DEFAULT = new C20791();

        /* renamed from: io.fabric.sdk.android.services.network.HttpRequest$ConnectionFactory$1 */
        static class C20791 implements ConnectionFactory {
            C20791() {
            }

            public HttpURLConnection create(URL url) throws IOException {
                return (HttpURLConnection) url.openConnection();
            }

            public HttpURLConnection create(URL url, Proxy proxy) throws IOException {
                return (HttpURLConnection) url.openConnection(proxy);
            }
        }

        HttpURLConnection create(URL url) throws IOException;

        HttpURLConnection create(URL url, Proxy proxy) throws IOException;
    }

    public static class HttpRequestException extends RuntimeException {
        private static final long serialVersionUID = -1170466989781746231L;

        protected HttpRequestException(IOException cause) {
            super(cause);
        }

        public IOException getCause() {
            return (IOException) super.getCause();
        }
    }

    protected static abstract class Operation<V> implements Callable<V> {
        protected abstract void done() throws IOException;

        protected abstract V run() throws HttpRequestException, IOException;

        protected Operation() {
        }

        public V call() throws HttpRequestException {
            boolean thrown = false;
            try {
                V run = run();
                try {
                    done();
                } catch (IOException e) {
                    if (null == null) {
                        throw new HttpRequestException(e);
                    }
                }
                return run;
            } catch (HttpRequestException e2) {
                throw e2;
            } catch (IOException e3) {
                thrown = true;
                throw new HttpRequestException(e3);
            } catch (Throwable th) {
                try {
                    done();
                } catch (IOException e4) {
                    if (!thrown) {
                        throw new HttpRequestException(e4);
                    }
                }
            }
        }
    }

    public static class RequestOutputStream extends BufferedOutputStream {
        private final CharsetEncoder encoder;

        public RequestOutputStream(OutputStream stream, String charset, int bufferSize) {
            super(stream, bufferSize);
            this.encoder = Charset.forName(HttpRequest.getValidCharset(charset)).newEncoder();
        }

        public RequestOutputStream write(String value) throws IOException {
            ByteBuffer bytes = this.encoder.encode(CharBuffer.wrap(value));
            super.write(bytes.array(), 0, bytes.limit());
            return this;
        }
    }

    protected static abstract class CloseOperation<V> extends Operation<V> {
        private final Closeable closeable;
        private final boolean ignoreCloseExceptions;

        protected CloseOperation(Closeable closeable, boolean ignoreCloseExceptions) {
            this.closeable = closeable;
            this.ignoreCloseExceptions = ignoreCloseExceptions;
        }

        protected void done() throws IOException {
            if (this.closeable instanceof Flushable) {
                ((Flushable) this.closeable).flush();
            }
            if (this.ignoreCloseExceptions) {
                try {
                    this.closeable.close();
                } catch (IOException e) {
                }
            } else {
                this.closeable.close();
            }
        }
    }

    protected static abstract class FlushOperation<V> extends Operation<V> {
        private final Flushable flushable;

        protected FlushOperation(Flushable flushable) {
            this.flushable = flushable;
        }

        protected void done() throws IOException {
            this.flushable.flush();
        }
    }

    private static String getValidCharset(String charset) {
        if (charset == null || charset.length() <= 0) {
            return "UTF-8";
        }
        return charset;
    }

    private static StringBuilder addPathSeparator(String baseUrl, StringBuilder result) {
        if (baseUrl.indexOf(58) + 2 == baseUrl.lastIndexOf(47)) {
            result.append('/');
        }
        return result;
    }

    private static StringBuilder addParamPrefix(String baseUrl, StringBuilder result) {
        int queryStart = baseUrl.indexOf(63);
        int lastChar = result.length() - 1;
        if (queryStart == -1) {
            result.append('?');
        } else if (queryStart < lastChar && baseUrl.charAt(lastChar) != Typography.amp) {
            result.append(Typography.amp);
        }
        return result;
    }

    public static void setConnectionFactory(ConnectionFactory connectionFactory) {
        if (connectionFactory == null) {
            CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
        } else {
            CONNECTION_FACTORY = connectionFactory;
        }
    }

    public static String encode(CharSequence url) throws HttpRequestException {
        try {
            URL parsed = new URL(url.toString());
            String host = parsed.getHost();
            int port = parsed.getPort();
            if (port != -1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(host);
                stringBuilder.append(':');
                stringBuilder.append(Integer.toString(port));
                host = stringBuilder.toString();
            }
            try {
                String encoded = new URI(parsed.getProtocol(), host, parsed.getPath(), parsed.getQuery(), null).toASCIIString();
                int paramsStart = encoded.indexOf(63);
                if (paramsStart <= 0 || paramsStart + 1 >= encoded.length()) {
                    return encoded;
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(encoded.substring(0, paramsStart + 1));
                stringBuilder2.append(encoded.substring(paramsStart + 1).replace("+", "%2B"));
                return stringBuilder2.toString();
            } catch (URISyntaxException e) {
                IOException io = new IOException("Parsing URI failed");
                io.initCause(e);
                throw new HttpRequestException(io);
            }
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public static String append(CharSequence url, Map<?, ?> params) {
        String baseUrl = url.toString();
        if (params != null) {
            if (!params.isEmpty()) {
                StringBuilder result = new StringBuilder(baseUrl);
                addPathSeparator(baseUrl, result);
                addParamPrefix(baseUrl, result);
                Iterator<?> iterator = params.entrySet().iterator();
                Entry<?, ?> entry = (Entry) iterator.next();
                result.append(entry.getKey().toString());
                result.append('=');
                Object value = entry.getValue();
                if (value != null) {
                    result.append(value);
                }
                while (iterator.hasNext()) {
                    result.append(Typography.amp);
                    entry = (Entry) iterator.next();
                    result.append(entry.getKey().toString());
                    result.append('=');
                    value = entry.getValue();
                    if (value != null) {
                        result.append(value);
                    }
                }
                return result.toString();
            }
        }
        return baseUrl;
    }

    public static String append(CharSequence url, Object... params) {
        String baseUrl = url.toString();
        if (params != null) {
            if (params.length != 0) {
                int i = 2;
                if (params.length % 2 != 0) {
                    throw new IllegalArgumentException("Must specify an even number of parameter names/values");
                }
                StringBuilder result = new StringBuilder(baseUrl);
                addPathSeparator(baseUrl, result);
                addParamPrefix(baseUrl, result);
                result.append(params[0]);
                result.append('=');
                Object value = params[1];
                if (value != null) {
                    result.append(value);
                }
                while (i < params.length) {
                    result.append(Typography.amp);
                    result.append(params[i]);
                    result.append('=');
                    value = params[i + 1];
                    if (value != null) {
                        result.append(value);
                    }
                    i += 2;
                }
                return result.toString();
            }
        }
        return baseUrl;
    }

    public static HttpRequest get(CharSequence url) throws HttpRequestException {
        return new HttpRequest(url, "GET");
    }

    public static HttpRequest get(URL url) throws HttpRequestException {
        return new HttpRequest(url, "GET");
    }

    public static HttpRequest get(CharSequence baseUrl, Map<?, ?> params, boolean encode) {
        String url = append(baseUrl, (Map) params);
        return get(encode ? encode(url) : url);
    }

    public static HttpRequest get(CharSequence baseUrl, boolean encode, Object... params) {
        String url = append(baseUrl, params);
        return get(encode ? encode(url) : url);
    }

    public static HttpRequest post(CharSequence url) throws HttpRequestException {
        return new HttpRequest(url, "POST");
    }

    public static HttpRequest post(URL url) throws HttpRequestException {
        return new HttpRequest(url, "POST");
    }

    public static HttpRequest post(CharSequence baseUrl, Map<?, ?> params, boolean encode) {
        String url = append(baseUrl, (Map) params);
        return post(encode ? encode(url) : url);
    }

    public static HttpRequest post(CharSequence baseUrl, boolean encode, Object... params) {
        String url = append(baseUrl, params);
        return post(encode ? encode(url) : url);
    }

    public static HttpRequest put(CharSequence url) throws HttpRequestException {
        return new HttpRequest(url, "PUT");
    }

    public static HttpRequest put(URL url) throws HttpRequestException {
        return new HttpRequest(url, "PUT");
    }

    public static HttpRequest put(CharSequence baseUrl, Map<?, ?> params, boolean encode) {
        String url = append(baseUrl, (Map) params);
        return put(encode ? encode(url) : url);
    }

    public static HttpRequest put(CharSequence baseUrl, boolean encode, Object... params) {
        String url = append(baseUrl, params);
        return put(encode ? encode(url) : url);
    }

    public static HttpRequest delete(CharSequence url) throws HttpRequestException {
        return new HttpRequest(url, "DELETE");
    }

    public static HttpRequest delete(URL url) throws HttpRequestException {
        return new HttpRequest(url, "DELETE");
    }

    public static HttpRequest delete(CharSequence baseUrl, Map<?, ?> params, boolean encode) {
        String url = append(baseUrl, (Map) params);
        return delete(encode ? encode(url) : url);
    }

    public static HttpRequest delete(CharSequence baseUrl, boolean encode, Object... params) {
        String url = append(baseUrl, params);
        return delete(encode ? encode(url) : url);
    }

    public static HttpRequest head(CharSequence url) throws HttpRequestException {
        return new HttpRequest(url, "HEAD");
    }

    public static HttpRequest head(URL url) throws HttpRequestException {
        return new HttpRequest(url, "HEAD");
    }

    public static HttpRequest head(CharSequence baseUrl, Map<?, ?> params, boolean encode) {
        String url = append(baseUrl, (Map) params);
        return head(encode ? encode(url) : url);
    }

    public static HttpRequest head(CharSequence baseUrl, boolean encode, Object... params) {
        String url = append(baseUrl, params);
        return head(encode ? encode(url) : url);
    }

    public static HttpRequest options(CharSequence url) throws HttpRequestException {
        return new HttpRequest(url, METHOD_OPTIONS);
    }

    public static HttpRequest options(URL url) throws HttpRequestException {
        return new HttpRequest(url, METHOD_OPTIONS);
    }

    public static HttpRequest trace(CharSequence url) throws HttpRequestException {
        return new HttpRequest(url, METHOD_TRACE);
    }

    public static HttpRequest trace(URL url) throws HttpRequestException {
        return new HttpRequest(url, METHOD_TRACE);
    }

    public static void keepAlive(boolean keepAlive) {
        setProperty("http.keepAlive", Boolean.toString(keepAlive));
    }

    public static void proxyHost(String host) {
        setProperty("http.proxyHost", host);
        setProperty("https.proxyHost", host);
    }

    public static void proxyPort(int port) {
        String portValue = Integer.toString(port);
        setProperty("http.proxyPort", portValue);
        setProperty("https.proxyPort", portValue);
    }

    public static void nonProxyHosts(String... hosts) {
        if (hosts == null || hosts.length <= 0) {
            setProperty("http.nonProxyHosts", null);
            return;
        }
        StringBuilder separated = new StringBuilder();
        int last = hosts.length - 1;
        for (int i = 0; i < last; i++) {
            separated.append(hosts[i]);
            separated.append('|');
        }
        separated.append(hosts[last]);
        setProperty("http.nonProxyHosts", separated.toString());
    }

    private static String setProperty(final String name, final String value) {
        PrivilegedAction<String> action;
        if (value != null) {
            action = new PrivilegedAction<String>() {
                public String run() {
                    return System.setProperty(name, value);
                }
            };
        } else {
            action = new PrivilegedAction<String>() {
                public String run() {
                    return System.clearProperty(name);
                }
            };
        }
        return (String) AccessController.doPrivileged(action);
    }

    public HttpRequest(CharSequence url, String method) throws HttpRequestException {
        try {
            this.url = new URL(url.toString());
            this.requestMethod = method;
        } catch (MalformedURLException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest(URL url, String method) throws HttpRequestException {
        this.url = url;
        this.requestMethod = method;
    }

    private Proxy createProxy() {
        return new Proxy(Type.HTTP, new InetSocketAddress(this.httpProxyHost, this.httpProxyPort));
    }

    private HttpURLConnection createConnection() {
        try {
            HttpURLConnection connection;
            if (this.httpProxyHost != null) {
                connection = CONNECTION_FACTORY.create(this.url, createProxy());
            } else {
                connection = CONNECTION_FACTORY.create(this.url);
            }
            connection.setRequestMethod(this.requestMethod);
            return connection;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(method());
        stringBuilder.append(' ');
        stringBuilder.append(url());
        return stringBuilder.toString();
    }

    public HttpURLConnection getConnection() {
        if (this.connection == null) {
            this.connection = createConnection();
        }
        return this.connection;
    }

    public HttpRequest ignoreCloseExceptions(boolean ignore) {
        this.ignoreCloseExceptions = ignore;
        return this;
    }

    public boolean ignoreCloseExceptions() {
        return this.ignoreCloseExceptions;
    }

    public int code() throws HttpRequestException {
        try {
            closeOutput();
            return getConnection().getResponseCode();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest code(AtomicInteger output) throws HttpRequestException {
        output.set(code());
        return this;
    }

    public boolean ok() throws HttpRequestException {
        return 200 == code();
    }

    public boolean created() throws HttpRequestException {
        return HttpStatus.SC_CREATED == code();
    }

    public boolean serverError() throws HttpRequestException {
        return 500 == code();
    }

    public boolean badRequest() throws HttpRequestException {
        return 400 == code();
    }

    public boolean notFound() throws HttpRequestException {
        return HttpStatus.SC_NOT_FOUND == code();
    }

    public boolean notModified() throws HttpRequestException {
        return HttpStatus.SC_NOT_MODIFIED == code();
    }

    public String message() throws HttpRequestException {
        try {
            closeOutput();
            return getConnection().getResponseMessage();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest disconnect() {
        getConnection().disconnect();
        return this;
    }

    public HttpRequest chunk(int size) {
        getConnection().setChunkedStreamingMode(size);
        return this;
    }

    public HttpRequest bufferSize(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be greater than zero");
        }
        this.bufferSize = size;
        return this;
    }

    public int bufferSize() {
        return this.bufferSize;
    }

    public HttpRequest uncompress(boolean uncompress) {
        this.uncompress = uncompress;
        return this;
    }

    protected ByteArrayOutputStream byteStream() {
        int size = contentLength();
        if (size > 0) {
            return new ByteArrayOutputStream(size);
        }
        return new ByteArrayOutputStream();
    }

    public String body(String charset) throws HttpRequestException {
        OutputStream output = byteStream();
        try {
            copy(buffer(), output);
            return output.toString(getValidCharset(charset));
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public String body() throws HttpRequestException {
        return body(charset());
    }

    public HttpRequest body(AtomicReference<String> output) throws HttpRequestException {
        output.set(body());
        return this;
    }

    public HttpRequest body(AtomicReference<String> output, String charset) throws HttpRequestException {
        output.set(body(charset));
        return this;
    }

    public boolean isBodyEmpty() throws HttpRequestException {
        return contentLength() == 0;
    }

    public byte[] bytes() throws HttpRequestException {
        OutputStream output = byteStream();
        try {
            copy(buffer(), output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public BufferedInputStream buffer() throws HttpRequestException {
        return new BufferedInputStream(stream(), this.bufferSize);
    }

    public InputStream stream() throws HttpRequestException {
        InputStream stream;
        if (code() < 400) {
            try {
                stream = getConnection().getInputStream();
            } catch (IOException e) {
                throw new HttpRequestException(e);
            }
        }
        stream = getConnection().getErrorStream();
        if (stream == null) {
            try {
                stream = getConnection().getInputStream();
            } catch (IOException e2) {
                throw new HttpRequestException(e2);
            }
        }
        if (this.uncompress) {
            if (ENCODING_GZIP.equals(contentEncoding())) {
                try {
                    return new GZIPInputStream(stream);
                } catch (IOException e22) {
                    throw new HttpRequestException(e22);
                }
            }
        }
        return stream;
    }

    public InputStreamReader reader(String charset) throws HttpRequestException {
        try {
            return new InputStreamReader(stream(), getValidCharset(charset));
        } catch (UnsupportedEncodingException e) {
            throw new HttpRequestException(e);
        }
    }

    public InputStreamReader reader() throws HttpRequestException {
        return reader(charset());
    }

    public BufferedReader bufferedReader(String charset) throws HttpRequestException {
        return new BufferedReader(reader(charset), this.bufferSize);
    }

    public BufferedReader bufferedReader() throws HttpRequestException {
        return bufferedReader(charset());
    }

    public HttpRequest receive(File file) throws HttpRequestException {
        try {
            final OutputStream output = new BufferedOutputStream(new FileOutputStream(file), this.bufferSize);
            return (HttpRequest) new CloseOperation<HttpRequest>(this.ignoreCloseExceptions, output) {
                protected HttpRequest run() throws HttpRequestException, IOException {
                    return HttpRequest.this.receive(output);
                }
            }.call();
        } catch (FileNotFoundException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest receive(OutputStream output) throws HttpRequestException {
        try {
            return copy(buffer(), output);
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest receive(PrintStream output) throws HttpRequestException {
        return receive((OutputStream) output);
    }

    public HttpRequest receive(Appendable appendable) throws HttpRequestException {
        BufferedReader reader = bufferedReader();
        final BufferedReader bufferedReader = reader;
        final Appendable appendable2 = appendable;
        return (HttpRequest) new CloseOperation<HttpRequest>(reader, this.ignoreCloseExceptions) {
            public HttpRequest run() throws IOException {
                CharBuffer buffer = CharBuffer.allocate(HttpRequest.this.bufferSize);
                while (true) {
                    int read = bufferedReader.read(buffer);
                    int read2 = read;
                    if (read == -1) {
                        return HttpRequest.this;
                    }
                    buffer.rewind();
                    appendable2.append(buffer, 0, read2);
                    buffer.rewind();
                }
            }
        }.call();
    }

    public HttpRequest receive(Writer writer) throws HttpRequestException {
        BufferedReader reader = bufferedReader();
        final BufferedReader bufferedReader = reader;
        final Writer writer2 = writer;
        return (HttpRequest) new CloseOperation<HttpRequest>(reader, this.ignoreCloseExceptions) {
            public HttpRequest run() throws IOException {
                return HttpRequest.this.copy(bufferedReader, writer2);
            }
        }.call();
    }

    public HttpRequest readTimeout(int timeout) {
        getConnection().setReadTimeout(timeout);
        return this;
    }

    public HttpRequest connectTimeout(int timeout) {
        getConnection().setConnectTimeout(timeout);
        return this;
    }

    public HttpRequest header(String name, String value) {
        getConnection().setRequestProperty(name, value);
        return this;
    }

    public HttpRequest header(String name, Number value) {
        return header(name, value != null ? value.toString() : null);
    }

    public HttpRequest headers(Map<String, String> headers) {
        if (!headers.isEmpty()) {
            for (Entry header : headers.entrySet()) {
                header(header);
            }
        }
        return this;
    }

    public HttpRequest header(Entry<String, String> header) {
        return header((String) header.getKey(), (String) header.getValue());
    }

    public String header(String name) throws HttpRequestException {
        closeOutputQuietly();
        return getConnection().getHeaderField(name);
    }

    public Map<String, List<String>> headers() throws HttpRequestException {
        closeOutputQuietly();
        return getConnection().getHeaderFields();
    }

    public long dateHeader(String name) throws HttpRequestException {
        return dateHeader(name, -1);
    }

    public long dateHeader(String name, long defaultValue) throws HttpRequestException {
        closeOutputQuietly();
        return getConnection().getHeaderFieldDate(name, defaultValue);
    }

    public int intHeader(String name) throws HttpRequestException {
        return intHeader(name, -1);
    }

    public int intHeader(String name, int defaultValue) throws HttpRequestException {
        closeOutputQuietly();
        return getConnection().getHeaderFieldInt(name, defaultValue);
    }

    public String[] headers(String name) {
        Map<String, List<String>> headers = headers();
        if (headers != null) {
            if (!headers.isEmpty()) {
                List<String> values = (List) headers.get(name);
                if (values == null || values.isEmpty()) {
                    return EMPTY_STRINGS;
                }
                return (String[]) values.toArray(new String[values.size()]);
            }
        }
        return EMPTY_STRINGS;
    }

    public String parameter(String headerName, String paramName) {
        return getParam(header(headerName), paramName);
    }

    public Map<String, String> parameters(String headerName) {
        return getParams(header(headerName));
    }

    protected Map<String, String> getParams(String header) {
        if (header != null) {
            if (header.length() != 0) {
                int headerLength = header.length();
                int start = header.indexOf(59) + 1;
                if (start != 0) {
                    if (start != headerLength) {
                        int end = header.indexOf(59, start);
                        if (end == -1) {
                            end = headerLength;
                        }
                        Map<String, String> params = new LinkedHashMap();
                        while (start < end) {
                            int nameEnd = header.indexOf(61, start);
                            if (nameEnd != -1 && nameEnd < end) {
                                String name = header.substring(start, nameEnd).trim();
                                if (name.length() > 0) {
                                    String value = header.substring(nameEnd + 1, end).trim();
                                    int length = value.length();
                                    if (length != 0) {
                                        if (length > 2 && Typography.quote == value.charAt(0) && Typography.quote == value.charAt(length - 1)) {
                                            params.put(name, value.substring(1, length - 1));
                                        } else {
                                            params.put(name, value);
                                        }
                                    }
                                }
                            }
                            start = end + 1;
                            end = header.indexOf(59, start);
                            if (end == -1) {
                                end = headerLength;
                            }
                        }
                        return params;
                    }
                }
                return Collections.emptyMap();
            }
        }
        return Collections.emptyMap();
    }

    protected String getParam(String value, String paramName) {
        if (value != null) {
            if (value.length() != 0) {
                int length = value.length();
                int start = value.indexOf(59) + 1;
                if (start != 0) {
                    if (start != length) {
                        int end = value.indexOf(59, start);
                        if (end == -1) {
                            end = length;
                        }
                        while (start < end) {
                            int nameEnd = value.indexOf(61, start);
                            if (nameEnd != -1 && nameEnd < end && paramName.equals(value.substring(start, nameEnd).trim())) {
                                String paramValue = value.substring(nameEnd + 1, end).trim();
                                int valueLength = paramValue.length();
                                if (valueLength != 0) {
                                    if (valueLength > 2 && Typography.quote == paramValue.charAt(0) && Typography.quote == paramValue.charAt(valueLength - 1)) {
                                        return paramValue.substring(1, valueLength - 1);
                                    }
                                    return paramValue;
                                }
                            }
                            start = end + 1;
                            end = value.indexOf(59, start);
                            if (end == -1) {
                                end = length;
                            }
                        }
                        return null;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public String charset() {
        return parameter("Content-Type", PARAM_CHARSET);
    }

    public HttpRequest userAgent(String userAgent) {
        return header("User-Agent", userAgent);
    }

    public HttpRequest referer(String referer) {
        return header("Referer", referer);
    }

    public HttpRequest useCaches(boolean useCaches) {
        getConnection().setUseCaches(useCaches);
        return this;
    }

    public HttpRequest acceptEncoding(String acceptEncoding) {
        return header("Accept-Encoding", acceptEncoding);
    }

    public HttpRequest acceptGzipEncoding() {
        return acceptEncoding(ENCODING_GZIP);
    }

    public HttpRequest acceptCharset(String acceptCharset) {
        return header("Accept-Charset", acceptCharset);
    }

    public String contentEncoding() {
        return header("Content-Encoding");
    }

    public String server() {
        return header("Server");
    }

    public long date() {
        return dateHeader("Date");
    }

    public String cacheControl() {
        return header("Cache-Control");
    }

    public String eTag() {
        return header("ETag");
    }

    public long expires() {
        return dateHeader("Expires");
    }

    public long lastModified() {
        return dateHeader("Last-Modified");
    }

    public String location() {
        return header("Location");
    }

    public HttpRequest authorization(String authorization) {
        return header("Authorization", authorization);
    }

    public HttpRequest proxyAuthorization(String proxyAuthorization) {
        return header("Proxy-Authorization", proxyAuthorization);
    }

    public HttpRequest basic(String name, String password) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Basic ");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(name);
        stringBuilder2.append(':');
        stringBuilder2.append(password);
        stringBuilder.append(Base64.encode(stringBuilder2.toString()));
        return authorization(stringBuilder.toString());
    }

    public HttpRequest proxyBasic(String name, String password) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Basic ");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(name);
        stringBuilder2.append(':');
        stringBuilder2.append(password);
        stringBuilder.append(Base64.encode(stringBuilder2.toString()));
        return proxyAuthorization(stringBuilder.toString());
    }

    public HttpRequest ifModifiedSince(long ifModifiedSince) {
        getConnection().setIfModifiedSince(ifModifiedSince);
        return this;
    }

    public HttpRequest ifNoneMatch(String ifNoneMatch) {
        return header("If-None-Match", ifNoneMatch);
    }

    public HttpRequest contentType(String contentType) {
        return contentType(contentType, null);
    }

    public HttpRequest contentType(String contentType, String charset) {
        if (charset == null || charset.length() <= 0) {
            return header("Content-Type", contentType);
        }
        String separator = "; charset=";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(contentType);
        stringBuilder.append("; charset=");
        stringBuilder.append(charset);
        return header("Content-Type", stringBuilder.toString());
    }

    public String contentType() {
        return header("Content-Type");
    }

    public int contentLength() {
        return intHeader("Content-Length");
    }

    public HttpRequest contentLength(String contentLength) {
        return contentLength(Integer.parseInt(contentLength));
    }

    public HttpRequest contentLength(int contentLength) {
        getConnection().setFixedLengthStreamingMode(contentLength);
        return this;
    }

    public HttpRequest accept(String accept) {
        return header("Accept", accept);
    }

    public HttpRequest acceptJson() {
        return accept("application/json");
    }

    protected HttpRequest copy(InputStream input, OutputStream output) throws IOException {
        final InputStream inputStream = input;
        final OutputStream outputStream = output;
        return (HttpRequest) new CloseOperation<HttpRequest>(input, this.ignoreCloseExceptions) {
            public HttpRequest run() throws IOException {
                byte[] buffer = new byte[HttpRequest.this.bufferSize];
                while (true) {
                    int read = inputStream.read(buffer);
                    int read2 = read;
                    if (read == -1) {
                        return HttpRequest.this;
                    }
                    outputStream.write(buffer, 0, read2);
                }
            }
        }.call();
    }

    protected HttpRequest copy(Reader input, Writer output) throws IOException {
        final Reader reader = input;
        final Writer writer = output;
        return (HttpRequest) new CloseOperation<HttpRequest>(input, this.ignoreCloseExceptions) {
            public HttpRequest run() throws IOException {
                char[] buffer = new char[HttpRequest.this.bufferSize];
                while (true) {
                    int read = reader.read(buffer);
                    int read2 = read;
                    if (read == -1) {
                        return HttpRequest.this;
                    }
                    writer.write(buffer, 0, read2);
                }
            }
        }.call();
    }

    protected HttpRequest closeOutput() throws IOException {
        if (this.output == null) {
            return this;
        }
        if (this.multipart) {
            this.output.write("\r\n--00content0boundary00--\r\n");
        }
        if (this.ignoreCloseExceptions) {
            try {
                this.output.close();
            } catch (IOException e) {
            }
        } else {
            this.output.close();
        }
        this.output = null;
        return this;
    }

    protected HttpRequest closeOutputQuietly() throws HttpRequestException {
        try {
            return closeOutput();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    protected HttpRequest openOutput() throws IOException {
        if (this.output != null) {
            return this;
        }
        getConnection().setDoOutput(true);
        this.output = new RequestOutputStream(getConnection().getOutputStream(), getParam(getConnection().getRequestProperty("Content-Type"), PARAM_CHARSET), this.bufferSize);
        return this;
    }

    protected HttpRequest startPart() throws IOException {
        if (this.multipart) {
            this.output.write("\r\n--00content0boundary00\r\n");
        } else {
            this.multipart = true;
            contentType(CONTENT_TYPE_MULTIPART).openOutput();
            this.output.write("--00content0boundary00\r\n");
        }
        return this;
    }

    protected HttpRequest writePartHeader(String name, String filename) throws IOException {
        return writePartHeader(name, filename, null);
    }

    protected HttpRequest writePartHeader(String name, String filename, String contentType) throws IOException {
        StringBuilder partBuffer = new StringBuilder();
        partBuffer.append("form-data; name=\"");
        partBuffer.append(name);
        if (filename != null) {
            partBuffer.append("\"; filename=\"");
            partBuffer.append(filename);
        }
        partBuffer.append(Typography.quote);
        partHeader("Content-Disposition", partBuffer.toString());
        if (contentType != null) {
            partHeader("Content-Type", contentType);
        }
        return send(CRLF);
    }

    public HttpRequest part(String name, String part) {
        return part(name, null, part);
    }

    public HttpRequest part(String name, String filename, String part) throws HttpRequestException {
        return part(name, filename, null, part);
    }

    public HttpRequest part(String name, String filename, String contentType, String part) throws HttpRequestException {
        try {
            startPart();
            writePartHeader(name, filename, contentType);
            this.output.write(part);
            return this;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest part(String name, Number part) throws HttpRequestException {
        return part(name, null, part);
    }

    public HttpRequest part(String name, String filename, Number part) throws HttpRequestException {
        return part(name, filename, part != null ? part.toString() : null);
    }

    public HttpRequest part(String name, File part) throws HttpRequestException {
        return part(name, null, part);
    }

    public HttpRequest part(String name, String filename, File part) throws HttpRequestException {
        return part(name, filename, null, part);
    }

    public HttpRequest part(String name, String filename, String contentType, File part) throws HttpRequestException {
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(part));
            HttpRequest part2 = part(name, filename, contentType, stream);
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
            return part2;
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        } catch (Throwable th) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e3) {
                }
            }
        }
    }

    public HttpRequest part(String name, InputStream part) throws HttpRequestException {
        return part(name, null, null, part);
    }

    public HttpRequest part(String name, String filename, String contentType, InputStream part) throws HttpRequestException {
        try {
            startPart();
            writePartHeader(name, filename, contentType);
            copy(part, this.output);
            return this;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest partHeader(String name, String value) throws HttpRequestException {
        return send((CharSequence) name).send((CharSequence) ": ").send((CharSequence) value).send(CRLF);
    }

    public HttpRequest send(File input) throws HttpRequestException {
        try {
            return send(new BufferedInputStream(new FileInputStream(input)));
        } catch (FileNotFoundException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest send(byte[] input) throws HttpRequestException {
        return send(new ByteArrayInputStream(input));
    }

    public HttpRequest send(InputStream input) throws HttpRequestException {
        try {
            openOutput();
            copy(input, this.output);
            return this;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest send(final Reader input) throws HttpRequestException {
        try {
            openOutput();
            final Writer writer = new OutputStreamWriter(this.output, this.output.encoder.charset());
            return (HttpRequest) new FlushOperation<HttpRequest>(writer) {
                protected HttpRequest run() throws IOException {
                    return HttpRequest.this.copy(input, writer);
                }
            }.call();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest send(CharSequence value) throws HttpRequestException {
        try {
            openOutput();
            this.output.write(value.toString());
            return this;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public OutputStreamWriter writer() throws HttpRequestException {
        try {
            openOutput();
            return new OutputStreamWriter(this.output, this.output.encoder.charset());
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest form(Map<?, ?> values) throws HttpRequestException {
        return form((Map) values, "UTF-8");
    }

    public HttpRequest form(Entry<?, ?> entry) throws HttpRequestException {
        return form((Entry) entry, "UTF-8");
    }

    public HttpRequest form(Entry<?, ?> entry, String charset) throws HttpRequestException {
        return form(entry.getKey(), entry.getValue(), charset);
    }

    public HttpRequest form(Object name, Object value) throws HttpRequestException {
        return form(name, value, "UTF-8");
    }

    public HttpRequest form(Object name, Object value, String charset) throws HttpRequestException {
        boolean first = this.form ^ true;
        if (first) {
            contentType("application/x-www-form-urlencoded", charset);
            this.form = true;
        }
        charset = getValidCharset(charset);
        try {
            openOutput();
            if (!first) {
                this.output.write(38);
            }
            this.output.write(URLEncoder.encode(name.toString(), charset));
            this.output.write(61);
            if (value != null) {
                this.output.write(URLEncoder.encode(value.toString(), charset));
            }
            return this;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest form(Map<?, ?> values, String charset) throws HttpRequestException {
        if (!values.isEmpty()) {
            for (Entry entry : values.entrySet()) {
                form(entry, charset);
            }
        }
        return this;
    }

    public HttpRequest trustAllCerts() throws HttpRequestException {
        return this;
    }

    public HttpRequest trustAllHosts() {
        return this;
    }

    public URL url() {
        return getConnection().getURL();
    }

    public String method() {
        return getConnection().getRequestMethod();
    }

    public HttpRequest useProxy(String proxyHost, int proxyPort) {
        if (this.connection != null) {
            throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
        }
        this.httpProxyHost = proxyHost;
        this.httpProxyPort = proxyPort;
        return this;
    }

    public HttpRequest followRedirects(boolean followRedirects) {
        getConnection().setInstanceFollowRedirects(followRedirects);
        return this;
    }
}
