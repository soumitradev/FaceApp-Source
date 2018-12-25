package com.parrot.arsdk.arsal;

import android.util.Log;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ARSALMd5 {
    public static final int MD5_LENGTH = 16;
    private static final String TAG = "Md5";
    private MessageDigest digest = null;

    public ARSALMd5() {
        initialize();
    }

    private boolean initialize() {
        try {
            this.digest = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            return true;
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.toString());
            return false;
        }
    }

    public String getTextDigest(byte[] hash, int index, int len) {
        StringBuffer txt = new StringBuffer();
        for (int i = 0; i < len; i++) {
            txt.append(String.format("%02x", new Object[]{Integer.valueOf(hash[index + i] & 255)}));
        }
        return txt.toString();
    }

    public boolean check(String filePath, String md5Txt) {
        byte[] md5 = compute(filePath);
        if (md5 == null || getTextDigest(md5, 0, md5.length).compareTo(md5Txt) != 0) {
            return false;
        }
        return true;
    }

    public byte[] compute(String filePath) {
        byte[] block = new byte[1024];
        byte[] md5 = null;
        try {
            initialize();
            FileInputStream src = new FileInputStream(filePath);
            while (true) {
                int read = src.read(block, 0, block.length);
                int count = read;
                if (read <= 0) {
                    break;
                }
                this.digest.update(block, 0, count);
            }
            md5 = this.digest.digest();
            src.close();
        } catch (FileNotFoundException e) {
            Log.d("DBG", e.toString());
        } catch (IOException e2) {
            Log.d("DBG", e2.toString());
        }
        return md5;
    }

    public byte[] compute(byte[] data) {
        initialize();
        this.digest.update(data);
        return this.digest.digest();
    }
}
