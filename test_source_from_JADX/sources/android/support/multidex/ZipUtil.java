package android.support.multidex;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

final class ZipUtil {
    private static final int BUFFER_SIZE = 16384;
    private static final int ENDHDR = 22;
    private static final int ENDSIG = 101010256;

    static class CentralDirectory {
        long offset;
        long size;

        CentralDirectory() {
        }
    }

    ZipUtil() {
    }

    static long getZipCrc(File apk) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(apk, "r");
        try {
            long computeCrcOfCentralDir = computeCrcOfCentralDir(raf, findCentralDirectory(raf));
            return computeCrcOfCentralDir;
        } finally {
            raf.close();
        }
    }

    static CentralDirectory findCentralDirectory(RandomAccessFile raf) throws IOException, ZipException {
        long scanOffset = raf.length() - 22;
        if (scanOffset < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("File too short to be a zip file: ");
            stringBuilder.append(raf.length());
            throw new ZipException(stringBuilder.toString());
        }
        long stopOffset = scanOffset - PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH;
        if (stopOffset < 0) {
            stopOffset = 0;
        }
        int endSig = Integer.reverseBytes(ENDSIG);
        while (true) {
            raf.seek(scanOffset);
            if (raf.readInt() == endSig) {
                raf.skipBytes(2);
                raf.skipBytes(2);
                raf.skipBytes(2);
                raf.skipBytes(2);
                CentralDirectory dir = new CentralDirectory();
                dir.size = ((long) Integer.reverseBytes(raf.readInt())) & 4294967295L;
                dir.offset = ((long) Integer.reverseBytes(raf.readInt())) & 4294967295L;
                return dir;
            }
            long scanOffset2 = scanOffset - 1;
            if (scanOffset2 < stopOffset) {
                throw new ZipException("End Of Central Directory signature not found");
            }
            scanOffset = scanOffset2;
        }
    }

    static long computeCrcOfCentralDir(RandomAccessFile raf, CentralDirectory dir) throws IOException {
        CRC32 crc = new CRC32();
        long stillToRead = dir.size;
        raf.seek(dir.offset);
        byte[] buffer = new byte[16384];
        int length = raf.read(buffer, 0, (int) Math.min(PlaybackStateCompat.ACTION_PREPARE, stillToRead));
        while (length != -1) {
            crc.update(buffer, 0, length);
            long stillToRead2 = stillToRead - ((long) length);
            if (stillToRead2 == 0) {
                stillToRead = stillToRead2;
                break;
            }
            length = raf.read(buffer, 0, (int) Math.min(PlaybackStateCompat.ACTION_PREPARE, stillToRead2));
            stillToRead = stillToRead2;
        }
        return crc.getValue();
    }
}
