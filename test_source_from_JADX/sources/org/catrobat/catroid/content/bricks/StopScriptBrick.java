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
import org.catrobat.catroid.generated70026.R;

public class StopScriptBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private int spinnerSelection;

    /* renamed from: org.catrobat.catroid.content.bricks.StopScriptBrick$1 */
    class C18011 implements OnItemSelectedListener {
        C18011() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            StopScriptBrick.this.spinnerSelection = position;
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public StopScriptBrick(int spinnerSelection) {
        this.spinnerSelection = spinnerSelection;
    }

    public int getViewResource() {
        return R.layout.brick_stop_script;
    }

    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_stop_script_spinner);
        spinner.setAdapter(createArrayAdapter(context));
        spinner.setSelection(this.spinnerSelection);
        spinner.setOnItemSelectedListener(new C18011());
        return this.view;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        Spinner spinner = (Spinner) prototypeView.findViewById(R.id.brick_stop_script_spinner);
        spinner.setAdapter(createArrayAdapter(context));
        spinner.setSelection(this.spinnerSelection);
        return prototypeView;
    }

    private ArrayAdapter<String> createArrayAdapter(Context context) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(context, 17367048, new String[]{context.getString(R.string.brick_stop_this_script), context.getString(R.string.brick_stop_all_scripts), context.getString(R.string.brick_stop_other_scripts)});
        spinnerAdapter.setDropDownViewResource(17367049);
        return spinnerAdapter;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createStopScriptAction(this.spinnerSelection, sequence.getScript()));
        return null;
    }
}
