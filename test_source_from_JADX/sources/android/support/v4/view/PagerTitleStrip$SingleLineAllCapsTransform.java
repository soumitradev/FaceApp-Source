package android.support.v4.view;

import android.content.Context;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import java.util.Locale;

class PagerTitleStrip$SingleLineAllCapsTransform extends SingleLineTransformationMethod {
    private Locale mLocale;

    PagerTitleStrip$SingleLineAllCapsTransform(Context context) {
        this.mLocale = context.getResources().getConfiguration().locale;
    }

    public CharSequence getTransformation(CharSequence source, View view) {
        source = super.getTransformation(source, view);
        return source != null ? source.toString().toUpperCase(this.mLocale) : null;
    }
}
