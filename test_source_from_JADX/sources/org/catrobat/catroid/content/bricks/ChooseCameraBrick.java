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

public class ChooseCameraBrick extends BrickBaseType {
    private static final int BACK = 0;
    private static final int FRONT = 1;
    private int spinnerSelectionID;

    /* renamed from: org.catrobat.catroid.content.bricks.ChooseCameraBrick$1 */
    class C17791 implements OnItemSelectedListener {
        C17791() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            ChooseCameraBrick.this.spinnerSelectionID = position;
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public ChooseCameraBrick() {
        this.spinnerSelectionID = 1;
    }

    public ChooseCameraBrick(int frontOrBack) {
        this.spinnerSelectionID = frontOrBack;
    }

    public int getViewResource() {
        return R.layout.brick_choose_camera;
    }

    public View getView(Context context) {
        super.getView(context);
        Spinner videoSpinner = (Spinner) this.view.findViewById(R.id.brick_choose_camera_spinner);
        videoSpinner.setAdapter(createArrayAdapter(context));
        videoSpinner.setOnItemSelectedListener(new C17791());
        videoSpinner.setSelection(this.spinnerSelectionID);
        return this.view;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        Spinner setVideoSpinner = (Spinner) prototypeView.findViewById(R.id.brick_choose_camera_spinner);
        setVideoSpinner.setAdapter(createArrayAdapter(context));
        setVideoSpinner.setSelection(this.spinnerSelectionID);
        return prototypeView;
    }

    private ArrayAdapter<String> createArrayAdapter(Context context) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(context, 17367048, new String[]{context.getString(R.string.choose_camera_back), context.getString(R.string.choose_camera_front)});
        spinnerAdapter.setDropDownViewResource(17367049);
        return spinnerAdapter;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        if (this.spinnerSelectionID == 1) {
            requiredResourcesSet.add(Integer.valueOf(12));
        } else {
            requiredResourcesSet.add(Integer.valueOf(11));
        }
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.spinnerSelectionID == 1) {
            sequence.addAction(sprite.getActionFactory().createSetFrontCameraAction());
            return null;
        }
        sequence.addAction(sprite.getActionFactory().createSetBackCameraAction());
        return null;
    }
}
