package com.parrot.freeflight.utils;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CacheUtils {
    public static File createTempFile(Context context) {
        if (context == null) {
            throw new IllegalArgumentException();
        }
        File saveToDir = context.getExternalCacheDir();
        if (saveToDir == null) {
            saveToDir = context.getCacheDir();
        }
        File tempFile = null;
        try {
            return File.createTempFile("parrot", "", saveToDir);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static StringBuffer readFromFile(File tempFile) {
        StringBuffer stringBuffer = new StringBuffer();
        FileInputStream is = null;
        try {
            is = new FileInputStream(tempFile);
            byte[] buffer = new byte[1024];
            while (true) {
                int read = is.read(buffer);
                int count = read;
                if (read == -1) {
                    break;
                }
                stringBuffer.append(new String(buffer, 0, count));
            }
            if (is != null) {
                try {
                    is.close();
                } catch (byte[] buffer2) {
                    buffer2.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (is != null) {
                is.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (is != null) {
                is.close();
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
        return stringBuffer;
    }

    public static boolean copyFileFromAssetsToStorage(AssetManager assets, String name, File dest) {
        boolean result = true;
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = assets.open(name);
            os = new FileOutputStream(dest);
            StreamUtils.copyStream(is, os);
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (IOException e22) {
            result = false;
            e22.printStackTrace();
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                }
            }
            if (is != null) {
                is.close();
            }
        } catch (Throwable th) {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e32) {
                    e32.printStackTrace();
                }
            }
        }
        return result;
    }
}
