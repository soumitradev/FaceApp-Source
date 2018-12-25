package org.tukaani.xz;

abstract class BCJOptions extends FilterOptions {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static /* synthetic */ Class class$org$tukaani$xz$BCJOptions = class$("org.tukaani.xz.BCJOptions");
    private final int alignment;
    int startOffset = 0;

    static {
        if (class$org$tukaani$xz$BCJOptions == null) {
        } else {
            Class cls = class$org$tukaani$xz$BCJOptions;
        }
    }

    BCJOptions(int i) {
        this.alignment = i;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException();
        }
    }

    public int getDecoderMemoryUsage() {
        return SimpleInputStream.getMemoryUsage();
    }

    public int getEncoderMemoryUsage() {
        return SimpleOutputStream.getMemoryUsage();
    }

    public int getStartOffset() {
        return this.startOffset;
    }

    public void setStartOffset(int i) throws UnsupportedOptionsException {
        if (((this.alignment - 1) & i) != 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Start offset must be a multiple of ");
            stringBuffer.append(this.alignment);
            throw new UnsupportedOptionsException(stringBuffer.toString());
        }
        this.startOffset = i;
    }
}
