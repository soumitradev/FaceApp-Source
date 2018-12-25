package org.tukaani.xz;

class RawCoder {
    RawCoder() {
    }

    static void validate(FilterCoder[] filterCoderArr) throws UnsupportedOptionsException {
        int i = 0;
        while (i < filterCoderArr.length - 1) {
            if (filterCoderArr[i].nonLastOK()) {
                i++;
            } else {
                throw new UnsupportedOptionsException("Unsupported XZ filter chain");
            }
        }
        if (filterCoderArr[filterCoderArr.length - 1].lastOK()) {
            i = 0;
            for (FilterCoder changesSize : filterCoderArr) {
                if (changesSize.changesSize()) {
                    i++;
                }
            }
            if (i > 3) {
                throw new UnsupportedOptionsException("Unsupported XZ filter chain");
            }
            return;
        }
        throw new UnsupportedOptionsException("Unsupported XZ filter chain");
    }
}
