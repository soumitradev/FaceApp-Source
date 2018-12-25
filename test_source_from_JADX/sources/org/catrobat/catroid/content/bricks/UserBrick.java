package org.catrobat.catroid.content.bricks;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.generated70026.R;

public class UserBrick extends BrickBaseType implements OnClickListener {
    private static final String TAG = UserBrick.class.getSimpleName();
    private static final long serialVersionUID = 1;
    @XStreamAlias("definitionBrick")
    private UserScriptDefinitionBrick definitionBrick;
    @XStreamAlias("userBrickParameters")
    private List<UserBrickParameter> userBrickParameters;

    public UserBrick() {
        this.userBrickParameters = new ArrayList();
        this.definitionBrick = new UserScriptDefinitionBrick();
    }

    public UserBrick(UserScriptDefinitionBrick definitionBrick) {
        this.userBrickParameters = new ArrayList();
        this.definitionBrick = definitionBrick;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        this.definitionBrick.addRequiredResources(requiredResourcesSet);
    }

    public List<UserBrickParameter> getUserBrickParameters() {
        return this.userBrickParameters;
    }

    public void updateUserBrickParametersAndVariables() {
        updateUserVariableValues();
    }

    private void updateUserVariableValues() {
        DataContainer dataContainer = ProjectManager.getInstance().getCurrentlyEditedScene().getDataContainer();
        List<UserVariable> variables = new ArrayList();
        for (UserBrickParameter userBrickParameter : this.userBrickParameters) {
            UserScriptDefinitionBrickElement element = userBrickParameter.getElement();
            if (element != null) {
                List<Formula> formulas = userBrickParameter.getFormulas();
                Sprite sprite = ProjectManager.getInstance().getCurrentSprite();
                try {
                    for (Formula formula : formulas) {
                        variables.add(new UserVariable(element.getText(), formula.interpretDouble(sprite)));
                    }
                } catch (InterpretationException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        if (!variables.isEmpty()) {
            dataContainer.setUserBrickVariables(this, variables);
            Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
            if (currentSprite != null) {
                currentSprite.updateUserVariableReferencesInUserVariableBricks(variables);
            }
        }
    }

    public List<Formula> getFormulas() {
        List<Formula> formulaList = new LinkedList();
        for (UserBrickParameter parameter : this.userBrickParameters) {
            UserScriptDefinitionBrickElement element = parameter.getElement();
            Formula formula = parameter.getFormulaWithBrickField(BrickField.USER_BRICK);
            if (!(formula == null || element == null || !element.isVariable())) {
                formulaList.add(formula);
            }
        }
        return formulaList;
    }

    public int getViewResource() {
        return R.layout.brick_user;
    }

    public void onClick(View eventOrigin) {
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        updateUserVariableValues();
        List<ScriptSequenceAction> returnActionList = new ArrayList();
        ActionFactory actionFactory = sprite.getActionFactory();
        ScriptSequenceAction userSequence = (ScriptSequenceAction) ActionFactory.eventSequence(this.definitionBrick.getScript());
        this.definitionBrick.getScript().run(sprite, userSequence);
        returnActionList.add(userSequence);
        sequence.addAction(actionFactory.createUserBrickAction(userSequence, this));
        ProjectManager.getInstance().setCurrentUserBrick(this);
        if (sprite.isClone) {
            sprite.addUserBrick(this);
        }
        return returnActionList;
    }

    public UserScriptDefinitionBrick getDefinitionBrick() {
        return this.definitionBrick;
    }

    public void setDefinitionBrick(UserScriptDefinitionBrick definitionBrick) {
        this.definitionBrick = definitionBrick;
    }
}
