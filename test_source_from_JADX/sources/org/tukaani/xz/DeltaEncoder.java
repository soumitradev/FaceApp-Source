package org.tukaani.xz;

class DeltaEncoder extends DeltaCoder implements FilterEncoder {
    private final DeltaOptions options;
    private final byte[] props = new byte[1];

    DeltaEncoder(DeltaOptions deltaOptions) {
        this.props[0] = (byte) (deltaOptions.getDistance() - 1);
        this.options = (DeltaOptions) deltaOptions.clone();
    }

    public long getFilterID() {
        return 3;
    }

    public byte[] getFilterProps() {
        return this.props;
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return this.options.getOutputStream(finishableOutputStream);
    }

    public boolean supportsFlushing() {
        return true;
    }
}
