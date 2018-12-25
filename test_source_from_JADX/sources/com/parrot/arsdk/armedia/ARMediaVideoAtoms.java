package com.parrot.arsdk.armedia;

import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class ARMediaVideoAtoms {
    private static final String TAG = "ARMediaVideoAtoms";

    private static native void nativeChangePvatDate(String str, String str2);

    private static native byte[] nativeGetAtom(String str, String str2);

    private static native void nativeWritePvat(String str, int i, String str2);

    public static String getPvat(String path) {
        byte[] data = nativeGetAtom(path, "pvat");
        if (data == null) {
            data = getPVATFromJava(path);
        }
        String s = null;
        if (data != null) {
            try {
                s = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException uee) {
                ARSALPrint.m532e(TAG, "Error while creating pvat string");
                uee.printStackTrace();
            }
        } else {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to find PVAT in ");
            stringBuilder.append(path);
            ARSALPrint.m532e(str, stringBuilder.toString());
        }
        return s;
    }

    public static byte[] getAtom(String path, String atom) {
        return nativeGetAtom(path, atom);
    }

    public static void writePvat(String path, ARDISCOVERY_PRODUCT_ENUM discoveryProduct, String videoDate) {
        nativeWritePvat(path, discoveryProduct.getValue(), videoDate);
    }

    public static void changePvatDate(String path, String videoDate) {
        nativeChangePvatDate(path, videoDate);
    }

    private static byte[] getPVATFromJava(String path) {
        byte[] result;
        byte[] atomBuffer;
        boolean valid;
        int read;
        String str = path;
        String atomName = "pvat";
        byte[] result2 = null;
        long atomSize = 0;
        byte[] fourCCTagBuffer = new byte[4];
        boolean found = false;
        long wideAtomSize = 8;
        RandomAccessFile file = null;
        String atomName2;
        try {
            file = new RandomAccessFile(str, "r");
            while (!found) {
                atomName2 = atomName;
                result = result2;
                try {
                    file.seek((file.getFilePointer() + wideAtomSize) - 8);
                    atomSize = (long) file.readInt();
                    file.read(fourCCTagBuffer, 0, 4);
                    if (atomSize == 1) {
                        wideAtomSize = file.readLong() - 8;
                    } else if (atomSize == 0) {
                        break;
                    } else {
                        wideAtomSize = atomSize;
                    }
                    if ("pvat".equals(new String(fourCCTagBuffer))) {
                        found = true;
                    }
                    atomName = atomName2;
                    result2 = result;
                } catch (IOException e) {
                    IOException iOException = e;
                }
            }
            result = result2;
        } catch (IOException e2) {
            atomName2 = atomName;
            result = null;
            String str2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("error while reading file [");
            stringBuilder.append(str);
            stringBuilder.append("]");
            ARSALPrint.m532e(str2, stringBuilder.toString());
            if (found) {
                if (atomSize > 8) {
                    atomSize -= 8;
                }
                atomBuffer = new byte[((int) atomSize)];
                valid = true;
                read = 0;
                try {
                    read = file.read(atomBuffer, 0, (int) atomSize);
                } catch (IOException e3) {
                    valid = false;
                }
                if (((long) read) != atomSize) {
                    valid = false;
                }
                if (valid) {
                    result2 = atomBuffer;
                } else {
                    String str3 = TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("failed to read atom, read = [");
                    stringBuilder2.append(read);
                    stringBuilder2.append("], atomSise = [");
                    stringBuilder2.append(atomSize);
                    stringBuilder2.append("]");
                    ARSALPrint.m532e(str3, stringBuilder2.toString());
                    result2 = result;
                }
            } else {
                ARSALPrint.m532e(TAG, "failed to found atom = [pvat]");
                result2 = result;
            }
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return result2;
        }
        if (found) {
            if (atomSize > 8) {
                atomSize -= 8;
            }
            atomBuffer = new byte[((int) atomSize)];
            valid = true;
            read = 0;
            read = file.read(atomBuffer, 0, (int) atomSize);
            if (((long) read) != atomSize) {
                valid = false;
            }
            if (valid) {
                result2 = atomBuffer;
            } else {
                String str32 = TAG;
                StringBuilder stringBuilder22 = new StringBuilder();
                stringBuilder22.append("failed to read atom, read = [");
                stringBuilder22.append(read);
                stringBuilder22.append("], atomSise = [");
                stringBuilder22.append(atomSize);
                stringBuilder22.append("]");
                ARSALPrint.m532e(str32, stringBuilder22.toString());
                result2 = result;
            }
        } else {
            ARSALPrint.m532e(TAG, "failed to found atom = [pvat]");
            result2 = result;
        }
        if (file != null) {
            file.close();
        }
        return result2;
    }
}
