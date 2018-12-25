package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\b\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000b0\r\u001a\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0002\u001a\u0017\u0010\u0016\u001a\u00020\u000e*\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u000eH\b\u001a5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c*\u00020\u00022\u0018\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0004\u0012\u0002H\u001c0\rH\bø\u0001\u0000¢\u0006\u0002\u0010\u001f\u0002\b\n\u0006\b\u0011(\u001e0\u0001¨\u0006 "}, d2 = {"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}, k = 2, mv = {1, 1, 10})
@JvmName(name = "TextStreamsKt")
/* compiled from: ReadWrite.kt */
public final class TextStreamsKt {
    @InlineOnly
    static /* synthetic */ BufferedReader buffered$default(Reader $receiver, int bufferSize, int i, Object obj) {
        if ((i & 1) != null) {
            bufferSize = 8192;
        }
        return ($receiver instanceof BufferedReader) != null ? (BufferedReader) $receiver : new BufferedReader($receiver, bufferSize);
    }

    @InlineOnly
    private static final BufferedReader buffered(@NotNull Reader $receiver, int bufferSize) {
        return $receiver instanceof BufferedReader ? (BufferedReader) $receiver : new BufferedReader($receiver, bufferSize);
    }

    @InlineOnly
    static /* synthetic */ BufferedWriter buffered$default(Writer $receiver, int bufferSize, int i, Object obj) {
        if ((i & 1) != null) {
            bufferSize = 8192;
        }
        return ($receiver instanceof BufferedWriter) != null ? (BufferedWriter) $receiver : new BufferedWriter($receiver, bufferSize);
    }

    @InlineOnly
    private static final BufferedWriter buffered(@NotNull Writer $receiver, int bufferSize) {
        return $receiver instanceof BufferedWriter ? (BufferedWriter) $receiver : new BufferedWriter($receiver, bufferSize);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void forEachLine(@org.jetbrains.annotations.NotNull java.io.Reader r13, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r14) {
        /*
        r0 = 0;
        r1 = "$receiver";
        kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r13, r1);
        r1 = "action";
        kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r14, r1);
        r1 = r13;
        r2 = r1 instanceof java.io.BufferedReader;
        if (r2 == 0) goto L_0x0014;
    L_0x0010:
        r2 = r1;
        r2 = (java.io.BufferedReader) r2;
        goto L_0x001b;
    L_0x0014:
        r2 = new java.io.BufferedReader;
        r3 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r2.<init>(r1, r3);
    L_0x001b:
        r2 = (java.io.Closeable) r2;
        r3 = 0;
        r3 = (java.lang.Throwable) r3;
        r4 = r2;
        r4 = (java.io.BufferedReader) r4;	 Catch:{ Throwable -> 0x0048 }
        r5 = 0;
        r6 = r5;
        r7 = lineSequence(r4);	 Catch:{ Throwable -> 0x0048 }
        r8 = r5;
        r9 = r7;
        r10 = r14;
        r11 = r9.iterator();	 Catch:{ Throwable -> 0x0048 }
    L_0x0030:
        r12 = r11.hasNext();	 Catch:{ Throwable -> 0x0048 }
        if (r12 == 0) goto L_0x003e;
    L_0x0036:
        r12 = r11.next();	 Catch:{ Throwable -> 0x0048 }
        r10.invoke(r12);	 Catch:{ Throwable -> 0x0048 }
        goto L_0x0030;
        r4 = kotlin.Unit.INSTANCE;	 Catch:{ Throwable -> 0x0048 }
        kotlin.io.CloseableKt.closeFinally(r2, r3);
        return;
    L_0x0046:
        r4 = move-exception;
        goto L_0x004a;
    L_0x0048:
        r3 = move-exception;
        throw r3;	 Catch:{ all -> 0x0046 }
    L_0x004a:
        kotlin.io.CloseableKt.closeFinally(r2, r3);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.TextStreamsKt.forEachLine(java.io.Reader, kotlin.jvm.functions.Function1):void");
    }

    @NotNull
    public static final List<String> readLines(@NotNull Reader $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        ArrayList result = new ArrayList();
        forEachLine($receiver, new TextStreamsKt$readLines$1(result));
        return result;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> T useLines(@org.jetbrains.annotations.NotNull java.io.Reader r8, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super kotlin.sequences.Sequence<java.lang.String>, ? extends T> r9) {
        /*
        r0 = 0;
        r1 = "$receiver";
        kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r8, r1);
        r1 = "block";
        kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r9, r1);
        r1 = r8 instanceof java.io.BufferedReader;
        if (r1 == 0) goto L_0x0013;
    L_0x000f:
        r1 = r8;
        r1 = (java.io.BufferedReader) r1;
        goto L_0x001a;
    L_0x0013:
        r1 = new java.io.BufferedReader;
        r2 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r1.<init>(r8, r2);
    L_0x001a:
        r1 = (java.io.Closeable) r1;
        r2 = 0;
        r2 = (java.lang.Throwable) r2;
        r3 = 0;
        r4 = 1;
        r5 = r1;
        r5 = (java.io.BufferedReader) r5;	 Catch:{ Throwable -> 0x0043 }
        r6 = r3;
        r7 = lineSequence(r5);	 Catch:{ Throwable -> 0x0043 }
        r5 = r9.invoke(r7);	 Catch:{ Throwable -> 0x0043 }
        kotlin.jvm.internal.InlineMarker.finallyStart(r4);
        r3 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r4, r4, r3);
        if (r3 == 0) goto L_0x003a;
    L_0x0036:
        kotlin.io.CloseableKt.closeFinally(r1, r2);
        goto L_0x003d;
    L_0x003a:
        r1.close();
    L_0x003d:
        kotlin.jvm.internal.InlineMarker.finallyEnd(r4);
        return r5;
    L_0x0041:
        r5 = move-exception;
        goto L_0x0045;
    L_0x0043:
        r2 = move-exception;
        throw r2;	 Catch:{ all -> 0x0041 }
    L_0x0045:
        kotlin.jvm.internal.InlineMarker.finallyStart(r4);
        r3 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r4, r4, r3);
        if (r3 == 0) goto L_0x0052;
    L_0x004e:
        kotlin.io.CloseableKt.closeFinally(r1, r2);
        goto L_0x005d;
    L_0x0052:
        if (r2 != 0) goto L_0x0058;
    L_0x0054:
        r1.close();
        goto L_0x005d;
    L_0x0058:
        r1.close();	 Catch:{ Throwable -> 0x005c }
        goto L_0x005d;
    L_0x005c:
        r1 = move-exception;
    L_0x005d:
        kotlin.jvm.internal.InlineMarker.finallyEnd(r4);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.TextStreamsKt.useLines(java.io.Reader, kotlin.jvm.functions.Function1):T");
    }

    @InlineOnly
    private static final StringReader reader(@NotNull String $receiver) {
        return new StringReader($receiver);
    }

    @NotNull
    public static final Sequence<String> lineSequence(@NotNull BufferedReader $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return SequencesKt__SequencesKt.constrainOnce(new LinesSequence($receiver));
    }

    @NotNull
    public static final String readText(@NotNull Reader $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        StringWriter buffer = new StringWriter();
        copyTo$default($receiver, buffer, 0, 2, null);
        String stringWriter = buffer.toString();
        Intrinsics.checkExpressionValueIsNotNull(stringWriter, "buffer.toString()");
        return stringWriter;
    }

    public static /* synthetic */ long copyTo$default(Reader reader, Writer writer, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        return copyTo(reader, writer, i);
    }

    public static final long copyTo(@NotNull Reader $receiver, @NotNull Writer out, int bufferSize) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(out, "out");
        long charsCopied = 0;
        char[] buffer = new char[bufferSize];
        int chars = $receiver.read(buffer);
        while (chars >= 0) {
            out.write(buffer, 0, chars);
            long charsCopied2 = charsCopied + ((long) chars);
            chars = $receiver.read(buffer);
            charsCopied = charsCopied2;
        }
        return charsCopied;
    }

    @InlineOnly
    private static final String readText(@NotNull URL $receiver, Charset charset) {
        return new String(readBytes($receiver), charset);
    }

    @InlineOnly
    static /* synthetic */ String readText$default(URL $receiver, Charset charset, int i, Object obj) {
        if ((i & 1) != null) {
            charset = Charsets.UTF_8;
        }
        return new String(readBytes($receiver), charset);
    }

    @NotNull
    public static final byte[] readBytes(@NotNull URL $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Closeable openStream = $receiver.openStream();
        Throwable th = (Throwable) null;
        try {
            InputStream it = (InputStream) openStream;
            int $i$a$1$use = 0;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            byte[] readBytes$default = ByteStreamsKt.readBytes$default(it, 0, 1, null);
            CloseableKt.closeFinally(openStream, th);
            return readBytes$default;
        } catch (Throwable th2) {
            CloseableKt.closeFinally(openStream, th);
        }
    }
}
