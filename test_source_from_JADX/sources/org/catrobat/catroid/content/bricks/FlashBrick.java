package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.generated70026.R;

public class FlashBrick extends BrickBaseType {
    private static final int FLASH_OFF = 0;
    private static final int FLASH_ON = 1;
    private int spinnerSelectionID = 1;

    /* renamed from: org.catrobat.catroid.content.bricks.FlashBrick$1 */
    class C17811 implements OnItemSelectedListener {
        C17811() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            FlashBrick.this.spinnerSelectionID = position;
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public int getViewResource() {
        return R.layout.brick_flash;
    }

    public View getView(Context context) {
        super.getView(context);
        Spinner flashSpinner = (Spinner) this.view.findViewById(R.id.brick_flash_spinner);
        flashSpinner.setAdapter(createArrayAdapter(context));
        flashSpinner.setOnItemSelectedListener(new C17811());
        flashSpinner.setSelection(this.spinnerSelectionID);
        return this.view;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        Spinner setFlashSpinner = (Spinner) prototypeView.findViewById(R.id.brick_flash_spinner);
        setFlashSpinner.setAdapter(createArrayAdapter(context));
        setFlashSpinner.setSelection(this.spinnerSelectionID);
        return prototypeView;
    }

    private ArrayAdapter<String> createArrayAdapter(Context context) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(context, 17367048, new String[]{context.getString(R.string.brick_flash_off), context.getString(R.string.brick_flash_on)});
        spinnerAdapter.setDropDownViewResource(17367049);
        return spinnerAdapter;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(8));
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.spinnerSelectionID == 1) {
            sequence.addAction(sprite.getActionFactory().createTurnFlashOnAction());
            return null;
        }
        sequence.addAction(sprite.getActionFactory().createTurnFlashOffAction());
        return null;
    }
}
