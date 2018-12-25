package com.parrot.freeflight.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.FloatMath;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ImageUtils {
    private static final String TAG = ImageUtils.class.getSimpleName();

    private ImageUtils() {
    }

    public static Bitmap decodeBitmapFromFile(String file, int width, int height) {
        if (width > 0) {
            if (height > 0) {
                Options options = new Options();
                options.inJustDecodeBounds = true;
                Bitmap bit = BitmapFactory.decodeFile(file, options);
                int h = (int) FloatMath.ceil(((float) options.outHeight) / ((float) height));
                int w = (int) FloatMath.ceil(((float) options.outWidth) / ((float) width));
                if (h > 1 || w > 1) {
                    if (h > w) {
                        options.inSampleSize = h;
                    } else {
                        options.inSampleSize = w;
                    }
                }
                options.inJustDecodeBounds = false;
                return BitmapFactory.decodeFile(file, options);
            }
        }
        return BitmapFactory.decodeFile(file);
    }

    public static boolean saveBitmap(File filename, Bitmap bitmap) {
        boolean result = false;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            result = bitmap.compress(CompressFormat.PNG, 100, out);
            if (out != null) {
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (FileNotFoundException e3) {
            Log.w(TAG, e3.toString());
            if (out != null) {
                try {
                    out.flush();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
                out.close();
            }
        } catch (Throwable th) {
            if (out != null) {
                try {
                    out.flush();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e42) {
                    e42.printStackTrace();
                }
            }
        }
        return result;
    }
}
