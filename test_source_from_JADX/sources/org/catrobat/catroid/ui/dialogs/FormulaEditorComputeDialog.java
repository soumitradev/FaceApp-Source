package org.catrobat.catroid.ui.dialogs;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.facedetection.FaceDetectionHandler;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.generated70026.R;

public class FormulaEditorComputeDialog extends AlertDialog implements SensorEventListener {
    private TextView computeTextView;
    private Context context;
    private Formula formulaToCompute = null;

    public FormulaEditorComputeDialog(Context context) {
        super(context);
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
            setContentView(R.layout.dialog_formulaeditor_compute_landscape);
            this.computeTextView = (TextView) findViewById(R.id.formula_editor_compute_dialog_textview_landscape_mode);
        } else {
            setContentView(R.layout.dialog_formulaeditor_compute);
            this.computeTextView = (TextView) findViewById(R.id.formula_editor_compute_dialog_textview);
        }
        showFormulaResult();
    }

    public void setFormula(Formula formula) {
        this.formulaToCompute = formula;
        if (formula.containsElement(ElementType.SENSOR)) {
            SensorHandler.startSensorListener(this.context);
            SensorHandler.registerListener(this);
        }
        ResourcesSet resourcesSet = new ResourcesSet();
        formula.addRequiredResources(resourcesSet);
        if (resourcesSet.contains(Integer.valueOf(4))) {
            FaceDetectionHandler.startFaceDetection();
        }
        if (resourcesSet.contains(Integer.valueOf(2))) {
            ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).connectDevice(BluetoothDevice.LEGO_NXT, getContext());
        }
        if (resourcesSet.contains(Integer.valueOf(20))) {
            ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).connectDevice(BluetoothDevice.LEGO_EV3, getContext());
        }
        if (resourcesSet.contains(Integer.valueOf(6))) {
            ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).connectDevice(BluetoothDevice.ARDUINO, getContext());
        }
        if (resourcesSet.contains(Integer.valueOf(10))) {
            ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).connectDevice(BluetoothDevice.PHIRO, getContext());
        }
    }

    protected void onStop() {
        SensorHandler.unregisterListener(this);
        ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).pause();
        FaceDetectionHandler.stopFaceDetection();
        super.onStop();
    }

    public boolean onTouchEvent(MotionEvent event) {
        dismiss();
        return true;
    }

    private void showFormulaResult() {
        if (this.computeTextView != null) {
            setDialogTextView(this.formulaToCompute.getResultForComputeDialog(this.context));
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        showFormulaResult();
    }

    private void setDialogTextView(final String newString) {
        this.computeTextView.post(new Runnable() {
            public void run() {
                FormulaEditorComputeDialog.this.computeTextView.setText(newString);
                LayoutParams params = FormulaEditorComputeDialog.this.computeTextView.getLayoutParams();
                int height = FormulaEditorComputeDialog.this.computeTextView.getLineCount() * FormulaEditorComputeDialog.this.computeTextView.getLineHeight();
                int heightMargin = (int) (((double) height) * 0.5d);
                params.width = -1;
                params.height = height + heightMargin;
                FormulaEditorComputeDialog.this.computeTextView.setLayoutParams(params);
            }
        });
    }
}
