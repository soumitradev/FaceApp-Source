package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

public final class Okio {
    private static final Logger logger = Logger.getLogger(Okio.class.getName());

    private Okio() {
    }

    public static BufferedSource buffer(Source source) {
        if (source != null) {
            return new RealBufferedSource(source);
        }
        throw new IllegalArgumentException("source == null");
    }

    public static BufferedSink buffer(Sink sink) {
        if (sink != null) {
            return new RealBufferedSink(sink);
        }
        throw new IllegalArgumentException("sink == null");
    }

    public static Sink sink(OutputStream out) {
        return sink(out, new Timeout());
    }

    private static Sink sink(final OutputStream out, final Timeout timeout) {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        } else if (timeout != null) {
            return new Sink() {
                public void write(Buffer source, long byteCount) throws IOException {
                    Util.checkOffsetAndCount(source.size, 0, byteCount);
                    while (byteCount > 0) {
                        timeout.throwIfReached();
                        Segment head = source.head;
                        int toCopy = (int) Math.min(byteCount, (long) (head.limit - head.pos));
                        out.write(head.data, head.pos, toCopy);
                        head.pos += toCopy;
                        long byteCount2 = byteCount - ((long) toCopy);
                        source.size -= (long) toCopy;
                        if (head.pos == head.limit) {
                            source.head = head.pop();
                            SegmentPool.recycle(head);
                        }
                        byteCount = byteCount2;
                    }
                }

                public void flush() throws IOException {
                    out.flush();
                }

                public void close() throws IOException {
                    out.close();
                }

                public Timeout timeout() {
                    return timeout;
                }

                public String toString() {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("sink(");
                    stringBuilder.append(out);
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                }
            };
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    public static Sink sink(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        Timeout timeout = timeout(socket);
        return timeout.sink(sink(socket.getOutputStream(), timeout));
    }

    public static Source source(InputStream in) {
        return source(in, new Timeout());
    }

    private static Source source(final InputStream in, final Timeout timeout) {
        if (in == null) {
            throw new IllegalArgumentException("in == null");
        } else if (timeout != null) {
            return new Source() {
                public long read(Buffer sink, long byteCount) throws IOException {
                    if (byteCount < 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("byteCount < 0: ");
                        stringBuilder.append(byteCount);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    } else if (byteCount == 0) {
                        return 0;
                    } else {
                        timeout.throwIfReached();
                        Segment tail = sink.writableSegment(1);
                        int bytesRead = in.read(tail.data, tail.limit, (int) Math.min(byteCount, (long) (2048 - tail.limit)));
                        if (bytesRead == -1) {
                            return -1;
                        }
                        tail.limit += bytesRead;
                        sink.size += (long) bytesRead;
                        return (long) bytesRead;
                    }
                }

                public void close() throws IOException {
                    in.close();
                }

                public Timeout timeout() {
                    return timeout;
                }

                public String toString() {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("source(");
                    stringBuilder.append(in);
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                }
            };
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    public static Source source(File file) throws FileNotFoundException {
        if (file != null) {
            return source(new FileInputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    @IgnoreJRERequirement
    public static Source source(Path path, OpenOption... options) throws IOException {
        if (path != null) {
            return source(Files.newInputStream(path, options));
        }
        throw new IllegalArgumentException("path == null");
    }

    public static Sink sink(File file) throws FileNotFoundException {
        if (file != null) {
            return sink(new FileOutputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Sink appendingSink(File file) throws FileNotFoundException {
        if (file != null) {
            return sink(new FileOutputStream(file, true));
        }
        throw new IllegalArgumentException("file == null");
    }

    @IgnoreJRERequirement
    public static Sink sink(Path path, OpenOption... options) throws IOException {
        if (path != null) {
            return sink(Files.newOutputStream(path, options));
        }
        throw new IllegalArgumentException("path == null");
    }

    public static Source source(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        Timeout timeout = timeout(socket);
        return timeout.source(source(socket.getInputStream(), timeout));
    }

    private static AsyncTimeout timeout(final Socket socket) {
        return new AsyncTimeout() {
            protected void timedOut() {
                try {
                    socket.close();
                } catch (Exception e) {
                    Logger access$000 = Okio.logger;
                    Level level = Level.WARNING;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to close timed out socket ");
                    stringBuilder.append(socket);
                    access$000.log(level, stringBuilder.toString(), e);
                }
            }
        };
    }
}