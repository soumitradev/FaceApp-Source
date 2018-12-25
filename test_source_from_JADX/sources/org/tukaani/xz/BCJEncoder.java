package org.tukaani.xz;

class BCJEncoder extends BCJCoder implements FilterEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static /* synthetic */ Class class$org$tukaani$xz$BCJEncoder = class$("org.tukaani.xz.BCJEncoder");
    private final long filterID;
    private final BCJOptions options;
    private final byte[] props;

    static {
        if (class$org$tukaani$xz$BCJEncoder == null) {
        } else {
            Class cls = class$org$tukaani$xz$BCJEncoder;
        }
    }

    BCJEncoder(BCJOptions bCJOptions, long j) {
        int startOffset = bCJOptions.getStartOffset();
        int i = 0;
        if (startOffset == 0) {
            this.props = new byte[0];
        } else {
            this.props = new byte[4];
            while (i < 4) {
                this.props[i] = (byte) (startOffset >>> (i * 8));
                i++;
            }
        }
        this.filterID = j;
        this.options = (BCJOptions) bCJOptions.clone();
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public long getFilterID() {
        return this.filterID;
    }

    public byte[] getFilterProps() {
        return this.props;
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return this.options.getOutputStream(finishableOutputStream);
    }

    public boolean supportsFlushing() {
        return false;
    }
}
