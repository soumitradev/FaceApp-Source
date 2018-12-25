package com.parrot.freeflight.tasks;

import android.os.AsyncTask;
import android.util.Log;
import java.io.File;
import java.io.IOException;

public class MoveFileTask extends AsyncTask<File, Integer, Boolean> {
    private static final String TAG = MoveFileTask.class.getSimpleName();
    private File result;

    protected Boolean doInBackground(File... params) {
        if (params.length < 2) {
            throw new IllegalArgumentException("Not enough parameters. Shoud have source and destination files");
        }
        File source = params[0];
        File destination = params[1];
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder("Moving file ");
        stringBuilder.append(source.getAbsolutePath());
        stringBuilder.append(" to ");
        stringBuilder.append(destination.getAbsolutePath());
        Log.d(str, stringBuilder.toString());
        if (source.renameTo(destination)) {
            this.result = destination;
            source.getParentFile().delete();
            Log.d(TAG, "File moved successfully");
            return Boolean.valueOf(true);
        }
        Log.d(TAG, "Moving of file failed. Copying...");
        try {
            Boolean result = copyFile(source, destination);
            if (result.equals(Boolean.TRUE)) {
                str = TAG;
                stringBuilder = new StringBuilder("Copying of the file ");
                stringBuilder.append(source.getAbsolutePath());
                stringBuilder.append(" completed with success.");
                Log.d(str, stringBuilder.toString());
            } else {
                str = TAG;
                stringBuilder = new StringBuilder("Copying of the file ");
                stringBuilder.append(source.getAbsolutePath());
                stringBuilder.append(" to ");
                stringBuilder.append(destination.getAbsolutePath());
                stringBuilder.append(" failed");
                Log.w(str, stringBuilder.toString());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return Boolean.valueOf(false);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.Boolean copyFile(java.io.File r20, java.io.File r21) throws java.io.IOException {
        /*
        r19 = this;
        r1 = r19;
        r2 = r21;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = r5;
        r21.createNewFile();	 Catch:{ IOException -> 0x00dc, all -> 0x00d2 }
        r7 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x00dc, all -> 0x00d2 }
        r8 = r20;
        r7.<init>(r8);	 Catch:{ IOException -> 0x00d0, all -> 0x00ce }
        r3 = r7;
        r7 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x00d0, all -> 0x00ce }
        r7.<init>(r2);	 Catch:{ IOException -> 0x00d0, all -> 0x00ce }
        r4 = r7;
        r9 = r20.length();	 Catch:{ IOException -> 0x00d0, all -> 0x00ce }
        r11 = 0;
        r7 = 512; // 0x200 float:7.175E-43 double:2.53E-321;
        r7 = new byte[r7];	 Catch:{ IOException -> 0x00d0, all -> 0x00ce }
        r13 = -1;
    L_0x0024:
        r14 = r7.length;	 Catch:{ IOException -> 0x00d0, all -> 0x00ce }
        r14 = r3.read(r7, r5, r14);	 Catch:{ IOException -> 0x00d0, all -> 0x00ce }
        r13 = r14;
        r15 = -1;
        if (r14 != r15) goto L_0x0030;
    L_0x002d:
        r16 = r6;
        goto L_0x005d;
    L_0x0030:
        r4.write(r7, r5, r13);	 Catch:{ IOException -> 0x00d0, all -> 0x00ce }
        r14 = (long) r13;
        r16 = r6;
        r5 = r11 + r14;
        r11 = 1;
        r11 = new java.lang.Integer[r11];	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        r14 = (double) r5;	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        r17 = r5;
        r5 = (double) r9;	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        r14 = r14 / r5;
        r5 = (int) r14;	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        r5 = r5 * 100;
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        r6 = 0;
        r11[r6] = r5;	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        r1.publishProgress(r11);	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        r5 = r19.isCancelled();	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        if (r5 == 0) goto L_0x00c7;
    L_0x0053:
        r5 = TAG;	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        r6 = "Copy of the file was canceled";
        android.util.Log.d(r5, r6);	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        r11 = r17;
    L_0x005d:
        r5 = r19.isCancelled();	 Catch:{ IOException -> 0x00c2, all -> 0x00bd }
        if (r5 != 0) goto L_0x0067;
    L_0x0063:
        r6 = 1;
        r16 = r6;
    L_0x0067:
        if (r3 == 0) goto L_0x0072;
    L_0x0069:
        r3.close();	 Catch:{ IOException -> 0x006d }
        goto L_0x0072;
    L_0x006d:
        r0 = move-exception;
        r5 = r0;
        r5.printStackTrace();
    L_0x0072:
        if (r4 == 0) goto L_0x00b9;
    L_0x0074:
        r4.flush();	 Catch:{ IOException -> 0x00b4 }
        r4.close();	 Catch:{ IOException -> 0x00b4 }
        if (r16 == 0) goto L_0x00a2;
    L_0x007c:
        r5 = r19.isCancelled();	 Catch:{ IOException -> 0x00b4 }
        if (r5 != 0) goto L_0x00a2;
    L_0x0082:
        r5 = r20.delete();	 Catch:{ IOException -> 0x00b4 }
        if (r5 != 0) goto L_0x009f;
    L_0x0088:
        r5 = TAG;	 Catch:{ IOException -> 0x00b4 }
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00b4 }
        r7 = "Cant delete file";
        r6.<init>(r7);	 Catch:{ IOException -> 0x00b4 }
        r7 = r20.getAbsolutePath();	 Catch:{ IOException -> 0x00b4 }
        r6.append(r7);	 Catch:{ IOException -> 0x00b4 }
        r6 = r6.toString();	 Catch:{ IOException -> 0x00b4 }
        android.util.Log.w(r5, r6);	 Catch:{ IOException -> 0x00b4 }
    L_0x009f:
        r1.result = r2;	 Catch:{ IOException -> 0x00b4 }
        goto L_0x00b9;
    L_0x00a2:
        if (r16 != 0) goto L_0x00b9;
    L_0x00a4:
        r5 = r19.isCancelled();	 Catch:{ IOException -> 0x00b4 }
        if (r5 == 0) goto L_0x00b9;
    L_0x00aa:
        r5 = r21.exists();	 Catch:{ IOException -> 0x00b4 }
        if (r5 == 0) goto L_0x00b9;
    L_0x00b0:
        r21.delete();	 Catch:{ IOException -> 0x00b4 }
        goto L_0x00b9;
    L_0x00b4:
        r0 = move-exception;
        r5 = r0;
        r5.printStackTrace();
    L_0x00b9:
        r5 = r4;
        r4 = r3;
        goto L_0x014b;
    L_0x00bd:
        r0 = move-exception;
        r5 = r4;
        r4 = r3;
        goto L_0x0153;
    L_0x00c2:
        r0 = move-exception;
        r5 = r4;
        r4 = r3;
        r3 = r0;
        goto L_0x00e4;
    L_0x00c7:
        r6 = r16;
        r11 = r17;
        r5 = 0;
        goto L_0x0024;
    L_0x00ce:
        r0 = move-exception;
        goto L_0x00d5;
    L_0x00d0:
        r0 = move-exception;
        goto L_0x00df;
    L_0x00d2:
        r0 = move-exception;
        r8 = r20;
    L_0x00d5:
        r16 = r6;
        r5 = r4;
        r4 = r3;
        r3 = r0;
        goto L_0x0154;
    L_0x00dc:
        r0 = move-exception;
        r8 = r20;
    L_0x00df:
        r16 = r6;
        r5 = r4;
        r4 = r3;
        r3 = r0;
    L_0x00e4:
        r6 = "ExternalStorage";
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0152 }
        r9 = "Error writing ";
        r7.<init>(r9);	 Catch:{ all -> 0x0152 }
        r7.append(r2);	 Catch:{ all -> 0x0152 }
        r7 = r7.toString();	 Catch:{ all -> 0x0152 }
        android.util.Log.w(r6, r7, r3);	 Catch:{ all -> 0x0152 }
        r16 = 0;
        if (r4 == 0) goto L_0x0104;
    L_0x00fb:
        r4.close();	 Catch:{ IOException -> 0x00ff }
        goto L_0x0104;
    L_0x00ff:
        r0 = move-exception;
        r3 = r0;
        r3.printStackTrace();
    L_0x0104:
        if (r5 == 0) goto L_0x014b;
    L_0x0106:
        r5.flush();	 Catch:{ IOException -> 0x0146 }
        r5.close();	 Catch:{ IOException -> 0x0146 }
        if (r16 == 0) goto L_0x0134;
    L_0x010e:
        r3 = r19.isCancelled();	 Catch:{ IOException -> 0x0146 }
        if (r3 != 0) goto L_0x0134;
    L_0x0114:
        r3 = r20.delete();	 Catch:{ IOException -> 0x0146 }
        if (r3 != 0) goto L_0x0131;
    L_0x011a:
        r3 = TAG;	 Catch:{ IOException -> 0x0146 }
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0146 }
        r7 = "Cant delete file";
        r6.<init>(r7);	 Catch:{ IOException -> 0x0146 }
        r7 = r20.getAbsolutePath();	 Catch:{ IOException -> 0x0146 }
        r6.append(r7);	 Catch:{ IOException -> 0x0146 }
        r6 = r6.toString();	 Catch:{ IOException -> 0x0146 }
        android.util.Log.w(r3, r6);	 Catch:{ IOException -> 0x0146 }
    L_0x0131:
        r1.result = r2;	 Catch:{ IOException -> 0x0146 }
        goto L_0x014b;
    L_0x0134:
        if (r16 != 0) goto L_0x014b;
    L_0x0136:
        r3 = r19.isCancelled();	 Catch:{ IOException -> 0x0146 }
        if (r3 == 0) goto L_0x014b;
    L_0x013c:
        r3 = r21.exists();	 Catch:{ IOException -> 0x0146 }
        if (r3 == 0) goto L_0x014b;
    L_0x0142:
        r21.delete();	 Catch:{ IOException -> 0x0146 }
        goto L_0x014b;
    L_0x0146:
        r0 = move-exception;
        r3 = r0;
        r3.printStackTrace();
    L_0x014b:
        r3 = r16;
        r6 = java.lang.Boolean.valueOf(r3);
        return r6;
    L_0x0152:
        r0 = move-exception;
    L_0x0153:
        r3 = r0;
    L_0x0154:
        if (r4 == 0) goto L_0x015f;
    L_0x0156:
        r4.close();	 Catch:{ IOException -> 0x015a }
        goto L_0x015f;
    L_0x015a:
        r0 = move-exception;
        r6 = r0;
        r6.printStackTrace();
    L_0x015f:
        if (r5 == 0) goto L_0x01a6;
    L_0x0161:
        r5.flush();	 Catch:{ IOException -> 0x01a1 }
        r5.close();	 Catch:{ IOException -> 0x01a1 }
        if (r16 == 0) goto L_0x018f;
    L_0x0169:
        r6 = r19.isCancelled();	 Catch:{ IOException -> 0x01a1 }
        if (r6 != 0) goto L_0x018f;
    L_0x016f:
        r6 = r20.delete();	 Catch:{ IOException -> 0x01a1 }
        if (r6 != 0) goto L_0x018c;
    L_0x0175:
        r6 = TAG;	 Catch:{ IOException -> 0x01a1 }
        r7 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x01a1 }
        r9 = "Cant delete file";
        r7.<init>(r9);	 Catch:{ IOException -> 0x01a1 }
        r9 = r20.getAbsolutePath();	 Catch:{ IOException -> 0x01a1 }
        r7.append(r9);	 Catch:{ IOException -> 0x01a1 }
        r7 = r7.toString();	 Catch:{ IOException -> 0x01a1 }
        android.util.Log.w(r6, r7);	 Catch:{ IOException -> 0x01a1 }
    L_0x018c:
        r1.result = r2;	 Catch:{ IOException -> 0x01a1 }
        goto L_0x01a6;
    L_0x018f:
        if (r16 != 0) goto L_0x01a6;
    L_0x0191:
        r6 = r19.isCancelled();	 Catch:{ IOException -> 0x01a1 }
        if (r6 == 0) goto L_0x01a6;
    L_0x0197:
        r6 = r21.exists();	 Catch:{ IOException -> 0x01a1 }
        if (r6 == 0) goto L_0x01a6;
    L_0x019d:
        r21.delete();	 Catch:{ IOException -> 0x01a1 }
        goto L_0x01a6;
    L_0x01a1:
        r0 = move-exception;
        r6 = r0;
        r6.printStackTrace();
    L_0x01a6:
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.parrot.freeflight.tasks.MoveFileTask.copyFile(java.io.File, java.io.File):java.lang.Boolean");
    }

    public File getResultFile() {
        return this.result;
    }
}
