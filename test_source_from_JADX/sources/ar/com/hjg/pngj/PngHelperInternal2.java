package ar.com.hjg.pngj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

final class PngHelperInternal2 {
    PngHelperInternal2() {
    }

    static OutputStream ostreamFromFile(File f, boolean allowoverwrite) {
        if (!f.exists() || allowoverwrite) {
            try {
                return new FileOutputStream(f);
            } catch (Exception e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not open for write");
                stringBuilder.append(f);
                throw new PngjInputException(stringBuilder.toString(), e);
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("File already exists: ");
        stringBuilder2.append(f);
        throw new PngjOutputException(stringBuilder2.toString());
    }
}
