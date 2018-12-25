package com.parrot.arsdk.arcommands;

public class ARCommandsVersion {

    public enum CompareResult {
        V1_NEWER,
        SAME_VERSIONS,
        V2_NEWER
    }

    public static native String getVersionCode();

    public static native int[] getVersionCodeAsInt();

    private static native int nativeCompareVersions(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private static native int nativeCompareVersionsCode(String str, String str2);

    private ARCommandsVersion() {
    }

    public static CompareResult compareVersions(String v1, String v2) {
        int nativeRes = nativeCompareVersionsCode(v1, v2);
        CompareResult res = CompareResult.SAME_VERSIONS;
        if (nativeRes > 0) {
            return CompareResult.V1_NEWER;
        }
        if (nativeRes < 0) {
            return CompareResult.V2_NEWER;
        }
        return res;
    }

    public static CompareResult compareVersions(int[] v1, int[] v2) {
        int[] v1_padded = new int[4];
        int[] v2_padded = new int[4];
        int i = 0;
        while (i < 4) {
            v1_padded[i] = i < v1.length ? v1[i] : 0;
            v2_padded[i] = i < v2.length ? v2[i] : 0;
            i++;
        }
        int nativeRes = nativeCompareVersions(v1_padded[0], v1_padded[1], v1_padded[2], v1_padded[3], v2_padded[0], v2_padded[1], v2_padded[2], v2_padded[3]);
        CompareResult res = CompareResult.SAME_VERSIONS;
        if (nativeRes > 0) {
            return CompareResult.V1_NEWER;
        }
        if (nativeRes < 0) {
            return CompareResult.V2_NEWER;
        }
        return res;
    }
}
