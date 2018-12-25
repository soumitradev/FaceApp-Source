package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.CallSuper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.adapter.BrickAdapter$ActionModeEnum;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.utils.Utils;

public abstract class FormulaBrick extends BrickBaseType implements OnClickListener {
    private transient BiMap<BrickField, Integer> brickFieldToTextViewIdMap = HashBiMap.create(2);
    ConcurrentFormulaHashMap formulaMap = new ConcurrentFormulaHashMap();

    public Formula getFormulaWithBrickField(BrickField brickField) throws IllegalArgumentException {
        if (this.formulaMap.containsKey(brickField)) {
            return (Formula) this.formulaMap.get(brickField);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Incompatible Brick Field: ");
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(" does have BrickField.");
        stringBuilder.append(brickField.toString());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setFormulaWithBrickField(BrickField brickField, Formula formula) throws IllegalArgumentException {
        if (this.formulaMap.containsKey(brickField)) {
            this.formulaMap.replace(brickField, formula);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Incompatible Brick Field: Cannot set BrickField.");
        stringBuilder.append(brickField.toString());
        stringBuilder.append(" fot ");
        stringBuilder.append(getClass().getSimpleName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    protected void addAllowedBrickField(BrickField brickField, int textViewResourceId) {
        this.formulaMap.putIfAbsent(brickField, new Formula(Integer.valueOf(0)));
        this.brickFieldToTextViewIdMap.put(brickField, Integer.valueOf(textViewResourceId));
    }

    @CallSuper
    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        for (Formula formula : this.formulaMap.values()) {
            formula.addRequiredResources(requiredResourcesSet);
        }
    }

    void replaceFormulaBrickField(BrickField oldBrickField, BrickField newBrickField) {
        if (this.formulaMap.containsKey(oldBrickField)) {
            Formula brickFormula = (Formula) this.formulaMap.get(oldBrickField);
            this.formulaMap.remove(oldBrickField);
            this.formulaMap.put(newBrickField, brickFormula);
        }
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        FormulaBrick clone = (FormulaBrick) super.clone();
        clone.formulaMap = this.formulaMap.clone();
        return clone;
    }

    public View getView(Context context) {
        super.getView(context);
        for (Entry<BrickField, Integer> entry : this.brickFieldToTextViewIdMap.entrySet()) {
            TextView brickFieldView = (TextView) this.view.findViewById(((Integer) entry.getValue()).intValue());
            brickFieldView.setText(getFormulaWithBrickField((BrickField) entry.getKey()).getTrimmedFormulaString(context));
            brickFieldView.setOnClickListener(this);
        }
        return this.view;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        for (Entry<BrickField, Integer> entry : this.brickFieldToTextViewIdMap.entrySet()) {
            ((TextView) prototypeView.findViewById(((Integer) entry.getValue()).intValue())).setText(getFormulaWithBrickField((BrickField) entry.getKey()).getTrimmedFormulaString(context));
        }
        return prototypeView;
    }

    public List<Formula> getFormulas() {
        return new ArrayList(this.formulaMap.values());
    }

    public TextView getTextView(BrickField brickField) {
        return (TextView) this.view.findViewById(((Integer) this.brickFieldToTextViewIdMap.get(brickField)).intValue());
    }

    public void highlightTextView(BrickField brickField) {
        ((TextView) this.view.findViewById(((Integer) this.brickFieldToTextViewIdMap.get(brickField)).intValue())).getBackground().mutate().setColorFilter(this.view.getContext().getResources().getColor(R.color.brick_field_highlight), Mode.SRC_ATOP);
    }

    public void onClick(View view) {
        if (this.adapter != null && this.adapter.getActionMode() == BrickAdapter$ActionModeEnum.NO_ACTION && !this.adapter.isDragging) {
            showFormulaEditorToEditFormula(view);
        }
    }

    public void showFormulaEditorToEditFormula(View view) {
        if (this.brickFieldToTextViewIdMap.inverse().containsKey(Integer.valueOf(view.getId()))) {
            FormulaEditorFragment.showFragment(view.getContext(), this, (BrickField) this.brickFieldToTextViewIdMap.inverse().get(Integer.valueOf(view.getId())));
        } else {
            FormulaEditorFragment.showFragment(view.getContext(), this, (BrickField) this.formulaMap.keys().nextElement());
        }
    }

    public View getCustomView(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("There is no custom view for the ");
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(".");
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected void setSecondsLabel(View view, BrickField brickField) {
        TextView textView = (TextView) view.findViewById(R.id.brick_seconds_label);
        Context context = textView.getContext();
        if (getFormulaWithBrickField(brickField).isSingleNumberFormula()) {
            try {
                textView.setText(context.getResources().getQuantityString(R.plurals.second_plural, Utils.convertDoubleToPluralInteger(((Formula) this.formulaMap.get(brickField)).interpretDouble(ProjectManager.getInstance().getCurrentSprite()).doubleValue())));
                return;
            } catch (InterpretationException e) {
                Log.e(getClass().getSimpleName(), "Interpretation of formula failed, fallback to quantity \"other\" for \"second(s)\" label.", e);
            }
        }
        textView.setText(context.getResources().getQuantityString(R.plurals.second_plural, Utils.TRANSLATION_PLURAL_OTHER_INTEGER));
    }
}
