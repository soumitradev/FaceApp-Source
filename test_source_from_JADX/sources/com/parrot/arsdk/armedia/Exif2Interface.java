package com.parrot.arsdk.armedia;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffFieldTypeConstants;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldType;

public class Exif2Interface {
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    private static final String TAG = Exif2Interface.class.getSimpleName();
    private byte[] mArray;
    private Map<Tag, String> mAttributes;
    private String mFilename;

    public enum Tag {
        IMAGE_DESCRIPTION("ImageDescription", ExifTagConstants.EXIF_TAG_IMAGE_DESCRIPTION, TiffFieldTypeConstants.FIELD_TYPE_ASCII),
        MAKE("Make", ExifTagConstants.EXIF_TAG_MAKE, TiffFieldTypeConstants.FIELD_TYPE_ASCII),
        ORIENTATION("Orientation", ExifTagConstants.EXIF_TAG_ORIENTATION, TiffFieldTypeConstants.FIELD_TYPE_SHORT);
        
        private final FieldType mFieldType;
        private final TagInfo mTagInfo;
        private final String mTagName;

        private Tag(String name, TagInfo tag, FieldType fieldType) {
            this.mTagName = name;
            this.mTagInfo = tag;
            this.mFieldType = fieldType;
        }

        public String toString() {
            return this.mTagName;
        }
    }

    public Exif2Interface(String filename) throws IOException {
        this.mFilename = filename;
        this.mArray = null;
        this.mAttributes = new HashMap();
        loadAttributes();
    }

    public Exif2Interface(byte[] array) throws IOException {
        this.mFilename = null;
        this.mArray = array;
        this.mAttributes = new HashMap();
        loadAttributes();
    }

    private InputStream openInputStream() throws IOException {
        return onOpenStream();
    }

    protected InputStream onOpenStream() throws IOException {
        if (this.mFilename != null) {
            if (this.mFilename.startsWith("http")) {
                return new BufferedInputStream(new URL(this.mFilename).openConnection().getInputStream());
            }
            return new BufferedInputStream(new FileInputStream(new File(this.mFilename)));
        } else if (this.mArray != null) {
            return new ByteArrayInputStream(this.mArray);
        } else {
            return null;
        }
    }

    private synchronized void loadAttributes() throws IOException {
        InputStream is = null;
        try {
            is = openInputStream();
            JpegImageMetadata metadata = (JpegImageMetadata) Sanselan.getMetadata(is, null);
            if (is != null) {
                is.close();
            }
            if (metadata != null) {
                for (Tag tag : Tag.values()) {
                    TiffField field = metadata.findEXIFValue(tag.mTagInfo);
                    if (field != null) {
                        String value = null;
                        if (tag.mFieldType == TiffFieldTypeConstants.FIELD_TYPE_SHORT) {
                            try {
                                value = String.valueOf(field.getIntValue());
                            } catch (ClassCastException e) {
                                try {
                                    value = String.valueOf(field.getIntArrayValue()[0]);
                                } catch (ClassCastException e2) {
                                }
                            }
                        } else if (tag.mFieldType == TiffFieldTypeConstants.FIELD_TYPE_ASCII) {
                            value = field.getStringValue();
                        }
                        if (value != null) {
                            this.mAttributes.put(tag, value);
                        }
                    }
                }
            }
        } catch (ImageReadException e1) {
            e1.printStackTrace();
            throw new IOException(e1.getMessage());
        } catch (ImageReadException e3) {
            e3.printStackTrace();
        } catch (Throwable th) {
            if (is != null) {
                is.close();
            }
        }
    }

    public String getAttribute(Tag tag) {
        return (String) this.mAttributes.get(tag);
    }

    public void setAttribute(Tag tag, String value) {
        this.mAttributes.put(tag, value);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void saveAttributes() throws java.io.IOException {
        /*
        r17 = this;
        r1 = r17;
        monitor-enter(r17);
        r2 = r1.mFilename;	 Catch:{ all -> 0x0137 }
        if (r2 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r17);
        return;
    L_0x0009:
        r2 = new java.io.File;	 Catch:{ all -> 0x0137 }
        r3 = r1.mFilename;	 Catch:{ all -> 0x0137 }
        r2.<init>(r3);	 Catch:{ all -> 0x0137 }
        r3 = 0;
        r4 = r3;
        r5 = 0;
        r6 = org.apache.sanselan.Sanselan.getMetadata(r2);	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r6 = (org.apache.sanselan.formats.jpeg.JpegImageMetadata) r6;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        if (r6 == 0) goto L_0x002f;
    L_0x001b:
        r7 = r6.getExif();	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        if (r7 == 0) goto L_0x002f;
    L_0x0021:
        r8 = r7.getOutputSet();	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        r5 = r8;
        goto L_0x002f;
    L_0x0027:
        r0 = move-exception;
        r3 = r0;
        goto L_0x011a;
    L_0x002b:
        r0 = move-exception;
        r3 = r0;
        goto L_0x0126;
    L_0x002f:
        if (r5 != 0) goto L_0x0037;
    L_0x0031:
        r7 = new org.apache.sanselan.formats.tiff.write.TiffOutputSet;	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        r7.<init>();	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        r5 = r7;
    L_0x0037:
        r7 = r1.mAttributes;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r7 = r7.size();	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        if (r7 <= 0) goto L_0x0109;
    L_0x003f:
        r7 = r5.getRootDirectory();	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r8 = r1.mAttributes;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r8 = r8.keySet();	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r9 = r8.iterator();	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
    L_0x004d:
        r10 = r9.hasNext();	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        if (r10 == 0) goto L_0x00cd;
    L_0x0053:
        r10 = r9.next();	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r10 = (com.parrot.arsdk.armedia.Exif2Interface.Tag) r10;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r11 = r10.mTagInfo;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r11 = r5.findField(r11);	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        if (r11 == 0) goto L_0x006a;
    L_0x0063:
        r12 = r10.mTagInfo;	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        r5.removeField(r12);	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
    L_0x006a:
        r12 = r1.mAttributes;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r12 = r12.get(r10);	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r12 = (java.lang.String) r12;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r13 = 0;
        r14 = r10.mFieldType;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r15 = org.apache.sanselan.formats.tiff.constants.TiffFieldTypeConstants.FIELD_TYPE_SHORT;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        if (r14 != r15) goto L_0x0095;
    L_0x007b:
        r14 = r10.mTagInfo;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r15 = r10.mFieldType;	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r3 = java.lang.Integer.parseInt(r12);	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ SanselanException -> 0x0122, Exception -> 0x0116, all -> 0x0111 }
        r16 = r4;
        r4 = r5.byteOrder;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r3 = r14.encodeValue(r15, r3, r4);	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r13 = r3;
        goto L_0x00ae;
    L_0x0095:
        r16 = r4;
        r3 = r10.mFieldType;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r4 = org.apache.sanselan.formats.tiff.constants.TiffFieldTypeConstants.FIELD_TYPE_ASCII;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        if (r3 != r4) goto L_0x00ae;
    L_0x009f:
        r3 = r10.mTagInfo;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r4 = r10.mFieldType;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r14 = r5.byteOrder;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r3 = r3.encodeValue(r4, r12, r14);	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r13 = r3;
    L_0x00ae:
        if (r13 == 0) goto L_0x00c8;
    L_0x00b0:
        r3 = new org.apache.sanselan.formats.tiff.write.TiffOutputField;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r4 = r10.mTagInfo;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r14 = r10.mFieldType;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r15 = r13.length;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r3.<init>(r4, r14, r15, r13);	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r4 = r10.mTagInfo;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r7.removeField(r4);	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r7.add(r3);	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r4 = r16;
        r3 = 0;
        goto L_0x004d;
    L_0x00cd:
        r16 = r4;
        r3 = "exif";
        r4 = r2.getParentFile();	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r9 = 0;
        r3 = java.io.File.createTempFile(r3, r9, r4);	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r3.createNewFile();	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r4 = new java.io.BufferedOutputStream;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r9 = new java.io.FileOutputStream;	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r9.<init>(r3);	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r4.<init>(r9);	 Catch:{ SanselanException -> 0x0104, Exception -> 0x00ff, all -> 0x00fa }
        r9 = new org.apache.sanselan.formats.jpeg.exifRewrite.ExifRewriter;	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        r9.<init>();	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        r9.updateExifMetadataLossless(r2, r4, r5);	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        r4.close();	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        r4 = 0;
        r2.delete();	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        r3.renameTo(r2);	 Catch:{ SanselanException -> 0x002b, Exception -> 0x0027 }
        goto L_0x010b;
    L_0x00fa:
        r0 = move-exception;
        r3 = r0;
        r4 = r16;
        goto L_0x0131;
    L_0x00ff:
        r0 = move-exception;
        r3 = r0;
        r4 = r16;
        goto L_0x011a;
    L_0x0104:
        r0 = move-exception;
        r3 = r0;
        r4 = r16;
        goto L_0x0126;
    L_0x0109:
        r16 = r4;
    L_0x010b:
        if (r4 == 0) goto L_0x0120;
    L_0x010d:
        r4.close();	 Catch:{ all -> 0x0137 }
        goto L_0x0120;
    L_0x0111:
        r0 = move-exception;
        r16 = r4;
        r3 = r0;
        goto L_0x0131;
    L_0x0116:
        r0 = move-exception;
        r16 = r4;
        r3 = r0;
    L_0x011a:
        r3.printStackTrace();	 Catch:{ all -> 0x012f }
        if (r4 == 0) goto L_0x0120;
    L_0x011f:
        goto L_0x010d;
    L_0x0120:
        monitor-exit(r17);
        return;
    L_0x0122:
        r0 = move-exception;
        r16 = r4;
        r3 = r0;
    L_0x0126:
        r3.printStackTrace();	 Catch:{ all -> 0x012f }
        r5 = new java.io.IOException;	 Catch:{ all -> 0x012f }
        r5.<init>();	 Catch:{ all -> 0x012f }
        throw r5;	 Catch:{ all -> 0x012f }
    L_0x012f:
        r0 = move-exception;
        r3 = r0;
    L_0x0131:
        if (r4 == 0) goto L_0x0136;
    L_0x0133:
        r4.close();	 Catch:{ all -> 0x0137 }
    L_0x0136:
        throw r3;	 Catch:{ all -> 0x0137 }
    L_0x0137:
        r0 = move-exception;
        r2 = r0;
        monitor-exit(r17);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.parrot.arsdk.armedia.Exif2Interface.saveAttributes():void");
    }

    private static Bitmap rotate(Bitmap bmp, int orientation) {
        Matrix matrix;
        switch (orientation) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 5:
            case 7:
                return bmp;
            case 3:
                matrix = new Matrix();
                matrix.postRotate(180.0f);
                return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            case 6:
                matrix = new Matrix();
                matrix.postRotate(90.0f);
                return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            case 8:
                matrix = new Matrix();
                matrix.postRotate(270.0f);
                return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            default:
                return bmp;
        }
    }

    public static Bitmap handleOrientation(Bitmap bmp, String path) {
        int orientation = 0;
        try {
            String orientationstr = new Exif2Interface(path).getAttribute(Tag.ORIENTATION);
            if (!(orientationstr == null || orientationstr.equals(""))) {
                orientation = Integer.parseInt(orientationstr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate(bmp, orientation);
    }

    public static Bitmap handleOrientation(Bitmap bmp, byte[] array) {
        int orientation = 0;
        try {
            String orientationstr = new Exif2Interface(array).getAttribute(Tag.ORIENTATION);
            if (!(orientationstr == null || orientationstr.equals(""))) {
                orientation = Integer.parseInt(orientationstr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate(bmp, orientation);
    }
}
