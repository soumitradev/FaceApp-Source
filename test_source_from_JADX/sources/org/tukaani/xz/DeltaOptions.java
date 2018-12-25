package org.tukaani.xz;

import java.io.InputStream;

public class DeltaOptions extends FilterOptions {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DISTANCE_MAX = 256;
    public static final int DISTANCE_MIN = 1;
    static /* synthetic */ Class class$org$tukaani$xz$DeltaOptions = class$("org.tukaani.xz.DeltaOptions");
    private int distance = 1;

    static {
        if (class$org$tukaani$xz$DeltaOptions == null) {
        } else {
            Class cls = class$org$tukaani$xz$DeltaOptions;
        }
    }

    public DeltaOptions(int i) throws UnsupportedOptionsException {
        setDistance(i);
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
        return 1;
    }

    public int getDistance() {
        return this.distance;
    }

    public int getEncoderMemoryUsage() {
        return DeltaOutputStream.getMemoryUsage();
    }

    FilterEncoder getFilterEncoder() {
        return new DeltaEncoder(this);
    }

    public InputStream getInputStream(InputStream inputStream) {
        return new DeltaInputStream(inputStream, this.distance);
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return new DeltaOutputStream(finishableOutputStream, this);
    }

    public void setDistance(int i) throws UnsupportedOptionsException {
        if (i >= 1) {
            if (i <= 256) {
                this.distance = i;
                return;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Delta distance must be in the range [1, 256]: ");
        stringBuffer.append(i);
        throw new UnsupportedOptionsException(stringBuffer.toString());
    }
}
