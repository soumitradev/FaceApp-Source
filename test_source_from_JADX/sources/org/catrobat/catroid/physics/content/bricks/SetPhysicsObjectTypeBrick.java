package org.catrobat.catroid.physics.content.bricks;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.BrickBaseType;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.physics.PhysicsObject.Type;

public class SetPhysicsObjectTypeBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private Type type = Type.NONE;

    /* renamed from: org.catrobat.catroid.physics.content.bricks.SetPhysicsObjectTypeBrick$1 */
    class C18541 implements OnItemSelectedListener {
        C18541() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if (position < Type.values().length) {
                SetPhysicsObjectTypeBrick.this.type = Type.values()[position];
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public SetPhysicsObjectTypeBrick(Type type) {
        this.type = type;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(3));
    }

    public int getViewResource() {
        return R.layout.brick_physics_set_physics_object_type;
    }

    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_set_physics_object_type_spinner);
        spinner.setAdapter(createAdapter(context));
        spinner.setSelection(this.type.ordinal());
        spinner.setOnItemSelectedListener(new C18541());
        return this.view;
    }

    private ArrayAdapter<String> createAdapter(Context context) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(context, 17367048);
        arrayAdapter.setDropDownViewResource(17367049);
        for (String type : context.getResources().getStringArray(R.array.physics_object_types)) {
            arrayAdapter.add(type);
        }
        return arrayAdapter;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        Spinner pointToSpinner = (Spinner) prototypeView.findViewById(R.id.brick_set_physics_object_type_spinner);
        pointToSpinner.setAdapter(createAdapter(context));
        pointToSpinner.setSelection(Type.DYNAMIC.ordinal());
        return prototypeView;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetPhysicsObjectTypeAction(sprite, this.type));
        return null;
    }

    @VisibleForTesting
    public Type getType() {
        return this.type;
    }
}
