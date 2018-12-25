package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import java.io.Serializable;
import java.util.Set;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.generated70026.R;

public class Formula implements Serializable {
    private static final long serialVersionUID = 1;
    private FormulaElement formulaTree;
    private transient InternFormula internFormula;

    public Object readResolve() {
        if (this.formulaTree == null) {
            this.formulaTree = new FormulaElement(ElementType.NUMBER, "0 ", null);
        }
        this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
        return this;
    }

    public Formula(FormulaElement formulaElement) {
        this.internFormula = null;
        this.formulaTree = formulaElement;
        this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
    }

    public Formula(Integer value) {
        this.internFormula = null;
        if (value.intValue() < 0) {
            this.formulaTree = new FormulaElement(ElementType.OPERATOR, Operators.MINUS.toString(), null);
            this.formulaTree.setRightChild(new FormulaElement(ElementType.NUMBER, Long.toString(Math.abs((long) value.intValue())), this.formulaTree));
            this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
            return;
        }
        this.formulaTree = new FormulaElement(ElementType.NUMBER, value.toString(), null);
        this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
    }

    public Formula(Float value) {
        this(Double.valueOf((double) value.floatValue()));
    }

    public Formula(Double value) {
        this.internFormula = null;
        if (value.doubleValue() < BrickValues.SET_COLOR_TO) {
            this.formulaTree = new FormulaElement(ElementType.OPERATOR, Operators.MINUS.toString(), null);
            this.formulaTree.setRightChild(new FormulaElement(ElementType.NUMBER, Double.toString(Math.abs(value.doubleValue())), this.formulaTree));
            this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
            return;
        }
        this.formulaTree = new FormulaElement(ElementType.NUMBER, value.toString(), null);
        this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
    }

    public void updateVariableReferences(String oldName, String newName, Context context) {
        this.internFormula.updateVariableReferences(oldName, newName, context);
        this.formulaTree.updateVariableReferences(oldName, newName);
    }

    public void updateCollisionFormulas(String oldName, String newName, Context context) {
        this.internFormula.updateCollisionFormula(oldName, newName, context);
        this.formulaTree.updateCollisionFormula(oldName, newName);
    }

    public void updateCollisionFormulasToVersion(float catroidLanguageVersion) {
        this.internFormula.updateCollisionFormulaToVersion(CatroidApplication.getAppContext(), catroidLanguageVersion);
        this.formulaTree.updateCollisionFormulaToVersion(catroidLanguageVersion);
    }

    public boolean containsSpriteInCollision(String name) {
        return this.formulaTree.containsSpriteInCollision(name);
    }

    public Formula(String value) {
        this.internFormula = null;
        if (value.equalsIgnoreCase(Functions.ARDUINOANALOG.toString())) {
            this.formulaTree = new FormulaElement(ElementType.SENSOR, Functions.ARDUINOANALOG.toString(), null);
        } else if (value.equalsIgnoreCase(Functions.ARDUINODIGITAL.toString())) {
            this.formulaTree = new FormulaElement(ElementType.SENSOR, Functions.ARDUINODIGITAL.toString(), null);
        } else {
            this.formulaTree = new FormulaElement(ElementType.STRING, value, null);
            this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
        }
    }

    public Boolean interpretBoolean(Sprite sprite) throws InterpretationException {
        return Boolean.valueOf(interpretDouble(sprite).intValue() != 0);
    }

    public Integer interpretInteger(Sprite sprite) throws InterpretationException {
        return Integer.valueOf(interpretDouble(sprite).intValue());
    }

    public Double interpretDouble(Sprite sprite) throws InterpretationException {
        try {
            Object returnValue = this.formulaTree.interpretRecursive(sprite);
            if (returnValue instanceof String) {
                Double doubleReturnValue = Double.valueOf((String) returnValue);
                if (!doubleReturnValue.isNaN()) {
                    return doubleReturnValue;
                }
                throw new InterpretationException("NaN in interpretDouble()");
            } else if (!((Double) returnValue).isNaN()) {
                return (Double) returnValue;
            } else {
                throw new InterpretationException("NaN in interpretDouble()");
            }
        } catch (ClassCastException classCastException) {
            throw new InterpretationException("Couldn't interpret Formula.", classCastException);
        } catch (NumberFormatException numberFormatException) {
            throw new InterpretationException("Couldn't interpret Formula.", numberFormatException);
        }
    }

    public Float interpretFloat(Sprite sprite) throws InterpretationException {
        return Float.valueOf(interpretDouble(sprite).floatValue());
    }

    public String interpretString(Sprite sprite) throws InterpretationException {
        Object interpretation = this.formulaTree.interpretRecursive(sprite);
        if (!(interpretation instanceof Double) || !((Double) interpretation).isNaN()) {
            return String.valueOf(interpretation);
        }
        throw new InterpretationException("NaN in interpretString()");
    }

    public Object interpretObject(Sprite sprite) {
        return this.formulaTree.interpretRecursive(sprite);
    }

    public void setRoot(FormulaElement formula) {
        this.formulaTree = formula;
        this.internFormula = new InternFormula(formula.getInternTokenList());
    }

    public FormulaElement getRoot() {
        return this.formulaTree;
    }

    public String getTrimmedFormulaString(Context context) {
        return this.internFormula.trimExternFormulaString(context);
    }

    public InternFormulaState getInternFormulaState() {
        return this.internFormula.getInternFormulaState();
    }

    public boolean containsElement(ElementType elementType) {
        return this.formulaTree.containsElement(elementType);
    }

    public boolean isSingleNumberFormula() {
        return this.formulaTree.isSingleNumberFormula();
    }

    public Formula clone() {
        if (this.formulaTree != null) {
            return new Formula(this.formulaTree.clone());
        }
        return new Formula(Integer.valueOf(0));
    }

    public void removeVariableReferences(String name, Context context) {
        this.internFormula.removeVariableReferences(name, context);
    }

    public void addRequiredResources(Set<Integer> requiredResourcesSet) {
        this.formulaTree.addRequiredResources(requiredResourcesSet);
    }

    public String getResultForComputeDialog(Context context) {
        Sprite sprite = ProjectManager.getInstance().getCurrentSprite();
        ElementType type = this.formulaTree.getElementType();
        if (this.formulaTree.isLogicalOperator()) {
            try {
                return context.getString(interpretBoolean(sprite).booleanValue() ? R.string.formula_editor_true : R.string.formula_editor_false);
            } catch (InterpretationException e) {
                return "ERROR";
            }
        }
        if (!(type == ElementType.STRING || type == ElementType.SENSOR)) {
            if (type == ElementType.FUNCTION) {
                if (Functions.getFunctionByValue(this.formulaTree.getValue()) != Functions.LETTER) {
                    if (Functions.getFunctionByValue(this.formulaTree.getValue()) == Functions.JOIN) {
                    }
                }
            }
            if (this.formulaTree.isUserVariableWithTypeString(sprite)) {
                return (String) ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer().getUserVariable(sprite, this.formulaTree.getValue()).getValue();
            }
            try {
                return String.valueOf(interpretDouble(sprite));
            } catch (InterpretationException e2) {
                return "ERROR";
            }
        }
        try {
            return interpretString(sprite);
        } catch (InterpretationException e3) {
            return "ERROR";
        }
    }

    public FormulaElement getFormulaTree() {
        return this.formulaTree;
    }
}
