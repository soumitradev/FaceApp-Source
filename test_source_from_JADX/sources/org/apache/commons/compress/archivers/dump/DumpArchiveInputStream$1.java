package org.apache.commons.compress.archivers.dump;

import java.util.Comparator;

class DumpArchiveInputStream$1 implements Comparator<DumpArchiveEntry> {
    final /* synthetic */ DumpArchiveInputStream this$0;

    DumpArchiveInputStream$1(DumpArchiveInputStream dumpArchiveInputStream) {
        this.this$0 = dumpArchiveInputStream;
    }

    public int compare(DumpArchiveEntry p, DumpArchiveEntry q) {
        if (p.getOriginalName() != null) {
            if (q.getOriginalName() != null) {
                return p.getOriginalName().compareTo(q.getOriginalName());
            }
        }
        return Integer.MAX_VALUE;
    }
}
