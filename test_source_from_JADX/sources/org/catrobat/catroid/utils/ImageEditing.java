package org.catrobat.catroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.util.Log;
import ar.com.hjg.pngj.PngReader;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.ChunkHelper;
import ar.com.hjg.pngj.chunks.PngChunk;
import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import com.badlogic.gdx.math.Vector2;
import com.parrot.arsdk.armedia.ARMediaManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.ScreenValues;

public final class ImageEditing {
    private static final int JPG_COMPRESSION_SETTING = 95;
    private static final String TAG = ImageEditing.class.getSimpleName();

    public enum ResizeType {
        STRETCH_TO_RECTANGLE,
        STAY_IN_RECTANGLE_WITH_SAME_ASPECT_RATIO,
        FILL_RECTANGLE_WITH_SAME_ASPECT_RATIO
    }

    private ImageEditing() {
        throw new AssertionError();
    }

    private static Bitmap scaleBitmap(Bitmap bitmap, int xSize, int ySize) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(((float) xSize) / ((float) bitmap.getWidth()), ((float) ySize) / ((float) bitmap.getHeight()));
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap getScaledBitmapFromPath(String imagePath, int outputRectangleWidth, int outputRectangleHeight, ResizeType resizeType, boolean justScaleDown) {
        if (imagePath == null) {
            return null;
        }
        int[] imageDimensions = getImageDimensions(imagePath);
        int originalWidth = imageDimensions[0];
        int originalHeight = imageDimensions[1];
        int[] scaledImageDimensions = getScaledImageDimensions(originalWidth, originalHeight, outputRectangleWidth, outputRectangleHeight, resizeType, justScaleDown);
        int newWidth = scaledImageDimensions[0];
        int newHeight = scaledImageDimensions[1];
        int loadingSampleSize = calculateInSampleSize(originalWidth, originalHeight, outputRectangleWidth, outputRectangleHeight);
        Options bitmapOptions = new Options();
        bitmapOptions.inSampleSize = loadingSampleSize;
        bitmapOptions.inJustDecodeBounds = false;
        return scaleBitmap(BitmapFactory.decodeFile(imagePath, bitmapOptions), newWidth, newHeight);
    }

    public static int[] getImageDimensions(String imagePath) {
        int[] imageDimensions = new int[2];
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        imageDimensions[0] = options.outWidth;
        imageDimensions[1] = options.outHeight;
        return imageDimensions;
    }

    private static int[] getScaledImageDimensions(int originalWidth, int originalHeight, int outputRectangleWidth, int outputRectangleHeight, ResizeType resizeType, boolean justScaleDown) {
        int i;
        int i2 = originalWidth;
        int i3 = originalHeight;
        int i4 = outputRectangleWidth;
        int i5 = outputRectangleHeight;
        ResizeType resizeType2 = resizeType;
        int newWidth = i2;
        int newHeight = i3;
        double sampleSizeWidth = ((double) i2) / ((double) i4);
        double sampleSizeHeight = ((double) i3) / ((double) i5);
        double sampleSizeMinimum = Math.min(sampleSizeWidth, sampleSizeHeight);
        double sampleSizeMaximum = Math.max(sampleSizeWidth, sampleSizeHeight);
        if (justScaleDown && i3 < i5) {
            if (i2 < i4) {
                i = newWidth;
                newWidth = i;
                return new int[]{newWidth, newHeight};
            }
        }
        i = newWidth;
        if (resizeType2 == ResizeType.STRETCH_TO_RECTANGLE) {
            newWidth = i4;
            newHeight = i5;
        } else if (resizeType2 == ResizeType.STAY_IN_RECTANGLE_WITH_SAME_ASPECT_RATIO) {
            newWidth = (int) Math.floor(((double) i2) / sampleSizeMaximum);
            newHeight = (int) Math.floor(((double) i3) / sampleSizeMaximum);
        } else {
            if (resizeType2 == ResizeType.FILL_RECTANGLE_WITH_SAME_ASPECT_RATIO) {
                newWidth = (int) Math.floor(((double) i2) / sampleSizeMinimum);
                newHeight = (int) Math.floor(((double) i3) / sampleSizeMinimum);
            }
            newWidth = i;
        }
        return new int[]{newWidth, newHeight};
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int rotationDegree) {
        Matrix rotateMatrix = new Matrix();
        rotateMatrix.postRotate((float) rotationDegree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotateMatrix, true);
    }

    public static void scaleImageFile(File file, double scaleFactor) throws FileNotFoundException {
        int[] originalBackgroundImageDimensions = getImageDimensions(file.getAbsolutePath());
        scaleImageFile(file, (int) (((double) originalBackgroundImageDimensions[0]) * scaleFactor), (int) (((double) originalBackgroundImageDimensions[1]) * scaleFactor));
    }

    public static void scaleImageFile(File file, int width, int height) throws FileNotFoundException {
        saveBitmapToImageFile(file, getScaledBitmapFromPath(file.getAbsolutePath(), width, height, ResizeType.FILL_RECTANGLE_WITH_SAME_ASPECT_RATIO, false));
    }

    public static double calculateScaleFactorToScreenSize(int resourceId, Context context) {
        if (context.getResources().getResourceTypeName(resourceId).compareTo("drawable") == 0) {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), resourceId, options);
            if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
                return calculateScaleFactor(options.outWidth, options.outHeight, 1280, ScreenValues.CAST_SCREEN_HEIGHT, true);
            }
            return calculateScaleFactor(options.outWidth, options.outHeight, ScreenValues.SCREEN_WIDTH, ScreenValues.SCREEN_HEIGHT, true);
        }
        throw new IllegalArgumentException("resource is not an image");
    }

    private static double calculateScaleFactor(int originalWidth, int originalHeight, int newWidth, int newHeight, boolean fillOutWholeNewArea) {
        if (!(originalHeight == 0 || originalWidth == 0 || newHeight == 0)) {
            if (newWidth != 0) {
                double widthScaleFactor = ((double) newWidth) / ((double) originalWidth);
                double heightScaleFactor = ((double) newHeight) / ((double) originalHeight);
                if (fillOutWholeNewArea) {
                    return Math.max(widthScaleFactor, heightScaleFactor);
                }
                return Math.min(widthScaleFactor, heightScaleFactor);
            }
        }
        throw new IllegalArgumentException("One or more values are 0");
    }

    public static Vector2 calculateScaleFactorsToScreenSize(int resourceId, Context context) {
        if (context.getResources().getResourceTypeName(resourceId).compareTo("drawable") == 0) {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), resourceId, options);
            return calculateScaleFactors(options.outWidth, options.outHeight, ScreenValues.SCREEN_WIDTH, ScreenValues.SCREEN_HEIGHT);
        }
        throw new IllegalArgumentException("resource is not an image");
    }

    private static Vector2 calculateScaleFactors(int originalWidth, int originalHeight, int newWidth, int newHeight) {
        if (!(originalHeight == 0 || originalWidth == 0 || newHeight == 0)) {
            if (newWidth != 0) {
                return new Vector2((float) (((double) newWidth) / ((double) originalWidth)), (float) (((double) newHeight) / ((double) originalHeight)));
            }
        }
        throw new IllegalArgumentException("One or more values are 0");
    }

    public static int calculateInSampleSize(int origWidth, int origHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (origHeight > reqHeight || origWidth > reqWidth) {
            int halfHeight = origHeight / 2;
            int halfWidth = origWidth / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static void saveBitmapToImageFile(File outputFile, Bitmap bitmap) throws FileNotFoundException {
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        try {
            if (!outputFile.getName().toLowerCase(Locale.US).endsWith(ARMediaManager.ARMEDIA_MANAGER_JPG)) {
                if (!outputFile.getName().toLowerCase(Locale.US).endsWith(".jpeg")) {
                    bitmap.compress(CompressFormat.PNG, 0, outputStream);
                    outputStream.flush();
                }
            }
            bitmap.compress(CompressFormat.JPEG, 95, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            try {
                outputStream.close();
            } catch (IOException e2) {
                Log.e(TAG, "Could not close OutputStream.", e2);
            }
        }
    }

    public static String readMetaDataStringFromPNG(String absolutePath, String key) throws PngjException {
        PngReader pngr = new PngReader(new File(absolutePath));
        pngr.readSkippingAllRows();
        for (PngChunk c : pngr.getChunksList().getChunks()) {
            if (ChunkHelper.isText(c)) {
                PngChunkTextVar ct = (PngChunkTextVar) c;
                String k = ct.getKey();
                String val = ct.getVal();
                if (key.equals(k)) {
                    pngr.close();
                    return val;
                }
            }
        }
        pngr.close();
        return "";
    }

    public static synchronized void writeMetaDataStringToPNG(String absolutePath, String key, String value) {
        synchronized (ImageEditing.class) {
            String tempFilename = new StringBuilder();
            int row = 0;
            tempFilename.append(absolutePath.substring(0, absolutePath.length() - 4));
            tempFilename.append("___temp.png");
            tempFilename = tempFilename.toString();
            File oldFile = new File(absolutePath);
            File newFile = new File(tempFilename);
            PngReader pngr = new PngReader(oldFile);
            PngWriter pngw = new PngWriter(newFile, pngr.imgInfo, true);
            pngw.copyChunksFrom(pngr.getChunksList(), 8);
            pngw.getMetadata().setText(key, value);
            while (row < pngr.imgInfo.rows) {
                pngw.writeRow(pngr.readRow());
                row++;
            }
            pngr.end();
            pngw.end();
            if (!oldFile.delete()) {
                Log.e(TAG, "writeMetaDataStringToPNG: Failed to delete old file");
            }
            if (!newFile.renameTo(new File(absolutePath))) {
                Log.e(TAG, "writeMetaDataStringToPNG: Failed to rename new file");
            }
        }
    }
}
