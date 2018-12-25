package com.parrot.arsdk.arutils;

import com.parrot.arsdk.arsal.ARSALPrint;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ARUtilsMD5 {
    public static final int MD5_LENGTH = 16;
    private static final String TAG = "ARUtilsMD5";
    MessageDigest digest = null;

    public ARUtilsMD5() {
        initialize();
    }

    public boolean initialize() {
        try {
            this.digest = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            return true;
        } catch (NoSuchAlgorithmException e) {
            ARSALPrint.m530d(TAG, e.toString());
            return false;
        }
    }

    public void update(byte[] buffer, int index, int len) {
        this.digest.update(buffer, index, len);
    }

    public static String getTextDigest(byte[] hash, int index, int len) {
        StringBuffer txt = new StringBuffer();
        for (int i = 0; i < len; i++) {
            txt.append(String.format("%02x", new Object[]{Integer.valueOf(hash[index + i] & 255)}));
        }
        return txt.toString();
    }

    public String digest() {
        byte[] hash = this.digest.digest();
        return getTextDigest(hash, 0, hash.length);
    }
}
