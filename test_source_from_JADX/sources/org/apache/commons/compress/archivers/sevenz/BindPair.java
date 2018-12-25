package org.apache.commons.compress.archivers.sevenz;

class BindPair {
    long inIndex;
    long outIndex;

    BindPair() {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BindPair binding input ");
        stringBuilder.append(this.inIndex);
        stringBuilder.append(" to output ");
        stringBuilder.append(this.outIndex);
        return stringBuilder.toString();
    }
}
