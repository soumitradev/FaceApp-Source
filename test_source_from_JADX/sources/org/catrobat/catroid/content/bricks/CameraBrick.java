package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.List;
import org.catrobat.catroid.camera.CameraManager.CameraState;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.generated70026.R;

public class CameraBrick extends BrickBaseType {
    private static final int OFF = 0;
    private static final int ON = 1;
    private int spinnerSelectionID;

    /* renamed from: org.catrobat.catroid.content.bricks.CameraBrick$1 */
    class C17781 implements OnItemSelectedListener {
        C17781() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            CameraBrick.this.spinnerSelectionID = position;
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public CameraBrick() {
        this.spinnerSelectionID = 1;
    }

    public CameraBrick(int onOrOff) {
        this.spinnerSelectionID = onOrOff;
    }

    public int getViewResource() {
        return R.layout.brick_video;
    }

    public View getView(Context context) {
        super.getView(context);
        Spinner videoSpinner = (Spinner) this.view.findViewById(R.id.brick_video_spinner);
        videoSpinner.setAdapter(createArrayAdapter(context));
        videoSpinner.setOnItemSelectedListener(new C17781());
        videoSpinner.setSelection(this.spinnerSelectionID);
        return this.view;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        Spinner setVideoSpinner = (Spinner) prototypeView.findViewById(R.id.brick_video_spinner);
        setVideoSpinner.setAdapter(createArrayAdapter(context));
        setVideoSpinner.setSelection(this.spinnerSelectionID);
        return prototypeView;
    }

    private ArrayAdapter<String> createArrayAdapter(Context context) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(context, 17367048, new String[]{context.getString(R.string.video_brick_camera_off), context.getString(R.string.video_brick_camera_on)});
        spinnerAdapter.setDropDownViewResource(17367049);
        return spinnerAdapter;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(17));
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createUpdateCameraPreviewAction(getCameraStateFromSpinner()));
        return null;
    }

    private CameraState getCameraStateFromSpinner() {
        if (this.spinnerSelectionID == 0) {
            return CameraState.stopped;
        }
        return CameraState.prepare;
    }
}
