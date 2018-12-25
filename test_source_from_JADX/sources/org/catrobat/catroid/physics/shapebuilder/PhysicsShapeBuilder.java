package org.catrobat.catroid.physics.shapebuilder;

import android.util.Log;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Filter;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.utils.Utils;

public final class PhysicsShapeBuilder {
    private static final float[] ACCURACY_LEVELS = new float[]{0.125f, 0.25f, 0.5f, 0.75f, 1.0f};
    private static final String TAG = PhysicsShapeBuilder.class.getSimpleName();
    private static PhysicsShapeBuilder instance = null;
    private Map<String, ImageShapes> imageShapesMap = new HashMap();
    private PhysicsShapeBuilderStrategy strategy = new PhysicsShapeBuilderStrategyFastHull();

    private class ImageShapes {
        private static final int MAX_ORIGINAL_PIXMAP_SIZE = 512;
        private Pixmap pixmap;
        private Map<String, Shape[]> shapeMap = new HashMap();
        private float sizeAdjustmentScaleFactor = 1.0f;

        ImageShapes(Pixmap pixmap) {
            if (pixmap == null) {
                throw new RuntimeException("Pixmap must not null");
            }
            this.pixmap = pixmap;
            int width = this.pixmap.getWidth();
            int height = this.pixmap.getHeight();
            if (width <= 512 && height <= 512) {
                return;
            }
            if (width > height) {
                this.sizeAdjustmentScaleFactor = 512.0f / ((float) width);
            } else {
                this.sizeAdjustmentScaleFactor = 512.0f / ((float) height);
            }
        }

        private String getShapeKey(float accuracyLevel) {
            return String.valueOf((int) (100.0f * accuracyLevel));
        }

        private Shape[] computeNewShape(float accuracy) {
            int width = this.pixmap.getWidth();
            int height = this.pixmap.getHeight();
            int scaledWidth = Math.round((((float) width) * this.sizeAdjustmentScaleFactor) * accuracy);
            int scaledHeight = Math.round((((float) height) * this.sizeAdjustmentScaleFactor) * accuracy);
            if (scaledWidth < 1) {
                scaledWidth = 1;
            }
            int scaledWidth2 = scaledWidth;
            int scaledHeight2 = scaledHeight < 1 ? 1 : scaledHeight;
            Pixmap.setFilter(Pixmap$Filter.NearestNeighbour);
            Pixmap pixmap = new Pixmap(scaledWidth2, scaledHeight2, r0.pixmap.getFormat());
            Pixmap scaledPixmap = pixmap;
            pixmap.drawPixmap(r0.pixmap, 0, 0, width, height, 0, 0, scaledWidth2, scaledHeight2);
            return PhysicsShapeScaleUtils.scaleShapes(PhysicsShapeBuilder.this.strategy.build(scaledPixmap, 1.0f), 1.0f, r0.sizeAdjustmentScaleFactor * accuracy);
        }

        public Shape[] getShapes(float accuracyLevel) throws RuntimeException {
            String shapeKey = getShapeKey(accuracyLevel);
            if (!this.shapeMap.containsKey(shapeKey)) {
                Shape[] shapes = computeNewShape(accuracyLevel);
                if (shapes == null) {
                    return null;
                }
                this.shapeMap.put(shapeKey, shapes);
            }
            return (Shape[]) this.shapeMap.get(shapeKey);
        }
    }

    public static PhysicsShapeBuilder getInstance() {
        if (instance == null) {
            instance = new PhysicsShapeBuilder();
        }
        return instance;
    }

    private PhysicsShapeBuilder() {
    }

    public void reset() {
        this.strategy = new PhysicsShapeBuilderStrategyFastHull();
        this.imageShapesMap = new HashMap();
    }

    public synchronized Shape[] getScaledShapes(LookData lookData, float scaleFactor) throws RuntimeException {
        if (scaleFactor < 0.0f) {
            throw new RuntimeException("scaleFactor can not be smaller than 0");
        } else if (lookData == null) {
            throw new RuntimeException("get shape for null lookData not possible");
        } else {
            Pixmap pixmap = lookData.getPixmap();
            if (pixmap == null) {
                Log.e(TAG, "pixmap should not be null");
                return null;
            }
            String imageIdentifier = Utils.md5Checksum(lookData.getFile());
            if (!this.imageShapesMap.containsKey(imageIdentifier)) {
                this.imageShapesMap.put(imageIdentifier, new ImageShapes(pixmap));
            }
            Shape[] shapes = ((ImageShapes) this.imageShapesMap.get(imageIdentifier)).getShapes(getAccuracyLevel(scaleFactor));
            if (shapes == null) {
                Log.e(TAG, "shapes should not be null");
                return null;
            }
            return PhysicsShapeScaleUtils.scaleShapes(shapes, scaleFactor);
        }
    }

    private static float getAccuracyLevel(float scaleFactor) {
        if (ACCURACY_LEVELS.length == 0) {
            return 0.0f;
        }
        int accuracyIdx = 0;
        if (ACCURACY_LEVELS.length == 1) {
            return ACCURACY_LEVELS[0];
        }
        while (true) {
            int accuracyIdx2 = accuracyIdx;
            if (accuracyIdx2 >= ACCURACY_LEVELS.length - 1) {
                return ACCURACY_LEVELS[ACCURACY_LEVELS.length - 1];
            }
            if (scaleFactor < (ACCURACY_LEVELS[accuracyIdx2] + ACCURACY_LEVELS[accuracyIdx2]) / 2.0f) {
                return ACCURACY_LEVELS[accuracyIdx2];
            }
            accuracyIdx = accuracyIdx2 + 1;
        }
    }
}
