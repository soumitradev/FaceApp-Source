package android.support.transition;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

class TransitionUtils$MatrixEvaluator implements TypeEvaluator<Matrix> {
    final float[] mTempEndValues = new float[9];
    final Matrix mTempMatrix = new Matrix();
    final float[] mTempStartValues = new float[9];

    TransitionUtils$MatrixEvaluator() {
    }

    public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
        startValue.getValues(this.mTempStartValues);
        endValue.getValues(this.mTempEndValues);
        for (int i = 0; i < 9; i++) {
            this.mTempEndValues[i] = this.mTempStartValues[i] + (fraction * (this.mTempEndValues[i] - this.mTempStartValues[i]));
        }
        this.mTempMatrix.setValues(this.mTempEndValues);
        return this.mTempMatrix;
    }
}
