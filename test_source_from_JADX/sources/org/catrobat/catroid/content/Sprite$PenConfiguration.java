package org.catrobat.catroid.content;

import android.graphics.PointF;
import com.badlogic.gdx.graphics.Color;
import org.catrobat.catroid.common.BrickValues;

public class Sprite$PenConfiguration {
    public Color penColor = BrickValues.PEN_COLOR;
    public boolean penDown = false;
    public double penSize = 3.15d;
    public PointF previousPoint = null;
    public boolean stamp = false;
    final /* synthetic */ Sprite this$0;

    public Sprite$PenConfiguration(Sprite this$0) {
        this.this$0 = this$0;
    }
}
