package org.tukaani.xz.lzma;

final class State {
    private static final int LIT_LIT = 0;
    private static final int LIT_LONGREP = 8;
    private static final int LIT_MATCH = 7;
    private static final int LIT_SHORTREP = 9;
    private static final int LIT_STATES = 7;
    private static final int MATCH_LIT = 4;
    private static final int MATCH_LIT_LIT = 1;
    private static final int NONLIT_MATCH = 10;
    private static final int NONLIT_REP = 11;
    private static final int REP_LIT = 5;
    private static final int REP_LIT_LIT = 2;
    private static final int SHORTREP_LIT = 6;
    private static final int SHORTREP_LIT_LIT = 3;
    static final int STATES = 12;
    private int state;

    State() {
    }

    State(State state) {
        this.state = state.state;
    }

    int get() {
        return this.state;
    }

    boolean isLiteral() {
        return this.state < 7;
    }

    void reset() {
        this.state = 0;
    }

    void set(State state) {
        this.state = state.state;
    }

    void updateLiteral() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:9:0x0018 in {2, 4, 7, 8} preds:[]
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
        r3 = this;
        r0 = r3.state;
        r1 = 3;
        if (r0 > r1) goto L_0x0009;
    L_0x0005:
        r0 = 0;
    L_0x0006:
        r3.state = r0;
        return;
    L_0x0009:
        r0 = r3.state;
        r2 = 9;
        if (r0 > r2) goto L_0x0013;
    L_0x000f:
        r0 = r3.state;
        r0 = r0 - r1;
        goto L_0x0006;
    L_0x0013:
        r0 = r3.state;
        r0 = r0 + -6;
        goto L_0x0006;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.lzma.State.updateLiteral():void");
    }

    void updateLongRep() {
        this.state = this.state < 7 ? 8 : 11;
    }

    void updateMatch() {
        int i = 7;
        if (this.state >= 7) {
            i = 10;
        }
        this.state = i;
    }

    void updateShortRep() {
        this.state = this.state < 7 ? 9 : 11;
    }
}
