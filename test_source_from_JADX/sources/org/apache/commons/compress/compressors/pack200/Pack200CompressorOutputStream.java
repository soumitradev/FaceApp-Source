package org.apache.commons.compress.compressors.pack200;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import org.apache.commons.compress.compressors.CompressorOutputStream;

public class Pack200CompressorOutputStream extends CompressorOutputStream {
    private boolean finished;
    private final OutputStream originalOutput;
    private final Map<String, String> properties;
    private final StreamBridge streamBridge;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void finish() throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0039 in list [B:10:0x002e]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r5 = this;
        r0 = r5.finished;
        if (r0 != 0) goto L_0x0039;
    L_0x0004:
        r0 = 1;
        r5.finished = r0;
        r0 = java.util.jar.Pack200.newPacker();
        r1 = r5.properties;
        if (r1 == 0) goto L_0x0018;
    L_0x000f:
        r1 = r0.properties();
        r2 = r5.properties;
        r1.putAll(r2);
    L_0x0018:
        r1 = 0;
        r2 = 0;
        r3 = new java.util.jar.JarInputStream;	 Catch:{ all -> 0x0032 }
        r4 = r5.streamBridge;	 Catch:{ all -> 0x0032 }
        r4 = r4.getInput();	 Catch:{ all -> 0x0032 }
        r3.<init>(r4);	 Catch:{ all -> 0x0032 }
        r1 = r3;	 Catch:{ all -> 0x0032 }
        r4 = r5.originalOutput;	 Catch:{ all -> 0x0032 }
        r0.pack(r3, r4);	 Catch:{ all -> 0x0032 }
        r2 = 1;
        if (r2 != 0) goto L_0x0039;
    L_0x002e:
        org.apache.commons.compress.utils.IOUtils.closeQuietly(r1);
        goto L_0x0039;
    L_0x0032:
        r3 = move-exception;
        if (r2 != 0) goto L_0x0038;
    L_0x0035:
        org.apache.commons.compress.utils.IOUtils.closeQuietly(r1);
    L_0x0038:
        throw r3;
    L_0x0039:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.pack200.Pack200CompressorOutputStream.finish():void");
    }

    public Pack200CompressorOutputStream(OutputStream out) throws IOException {
        this(out, Pack200Strategy.IN_MEMORY);
    }

    public Pack200CompressorOutputStream(OutputStream out, Pack200Strategy mode) throws IOException {
        this(out, mode, null);
    }

    public Pack200CompressorOutputStream(OutputStream out, Map<String, String> props) throws IOException {
        this(out, Pack200Strategy.IN_MEMORY, props);
    }

    public Pack200CompressorOutputStream(OutputStream out, Pack200Strategy mode, Map<String, String> props) throws IOException {
        this.finished = false;
        this.originalOutput = out;
        this.streamBridge = mode.newStreamBridge();
        this.properties = props;
    }

    public void write(int b) throws IOException {
        this.streamBridge.write(b);
    }

    public void write(byte[] b) throws IOException {
        this.streamBridge.write(b);
    }

    public void write(byte[] b, int from, int length) throws IOException {
        this.streamBridge.write(b, from, length);
    }

    public void close() throws IOException {
        finish();
        try {
            this.streamBridge.stop();
        } finally {
            this.originalOutput.close();
        }
    }
}
