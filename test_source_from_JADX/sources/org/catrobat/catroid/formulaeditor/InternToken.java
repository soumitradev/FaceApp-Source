package org.catrobat.catroid.formulaeditor;

import java.util.List;
import org.catrobat.catroid.sensing.CollisionDetection;

public class InternToken {
    private InternTokenType internTokenType;
    private String tokenStringValue = "";

    public InternToken(InternTokenType internTokenType) {
        this.internTokenType = internTokenType;
    }

    public InternToken(InternTokenType internTokenType, String tokenStringValue) {
        this.tokenStringValue = tokenStringValue;
        this.internTokenType = internTokenType;
    }

    public void setTokenStringValue(String tokenString) {
        this.tokenStringValue = tokenString;
    }

    public String getTokenStringValue() {
        return this.tokenStringValue;
    }

    public void updateVariableReferences(String oldName, String newName) {
        if (this.internTokenType == InternTokenType.USER_VARIABLE && this.tokenStringValue.equals(oldName)) {
            this.tokenStringValue = newName;
        }
    }

    public void updateListReferences(String oldName, String newName) {
        if (this.internTokenType == InternTokenType.USER_LIST && this.tokenStringValue.equals(oldName)) {
            this.tokenStringValue = newName;
        }
    }

    public void getVariableAndListNames(List<String> variables, List<String> lists) {
        if (this.internTokenType == InternTokenType.USER_VARIABLE && !variables.contains(this.tokenStringValue)) {
            variables.add(this.tokenStringValue);
        }
        if (this.internTokenType == InternTokenType.USER_LIST && !lists.contains(this.tokenStringValue)) {
            lists.add(this.tokenStringValue);
        }
    }

    public void updateCollisionFormula(String oldName, String newName) {
        if (this.internTokenType == InternTokenType.COLLISION_FORMULA && this.tokenStringValue.equals(oldName)) {
            this.tokenStringValue = newName;
        }
    }

    public void updateCollisionFormulaToVersion(float catroidLanguageVersion) {
        if (catroidLanguageVersion == 0.993f && this.internTokenType == InternTokenType.COLLISION_FORMULA) {
            String secondSpriteName = CollisionDetection.getSecondSpriteNameFromCollisionFormulaString(this.tokenStringValue);
            if (secondSpriteName != null) {
                this.tokenStringValue = secondSpriteName;
            }
        }
    }

    public boolean isNumber() {
        return this.internTokenType == InternTokenType.NUMBER;
    }

    public boolean isOperator() {
        return this.internTokenType == InternTokenType.OPERATOR && Operators.isOperator(this.tokenStringValue);
    }

    public boolean isBracketClose() {
        return this.internTokenType == InternTokenType.BRACKET_CLOSE;
    }

    public boolean isFunctionParameterBracketOpen() {
        return this.internTokenType == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN;
    }

    public boolean isFunctionParameterBracketClose() {
        return this.internTokenType == InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE;
    }

    public boolean isFunctionParameterDelimiter() {
        return this.internTokenType == InternTokenType.FUNCTION_PARAMETER_DELIMITER;
    }

    public boolean isFunctionName() {
        return this.internTokenType == InternTokenType.FUNCTION_NAME;
    }

    public boolean isSensor() {
        return this.internTokenType == InternTokenType.SENSOR;
    }

    public boolean isEndOfFileToken() {
        return this.internTokenType == InternTokenType.PARSER_END_OF_FILE;
    }

    public boolean isUserVariable() {
        return this.internTokenType == InternTokenType.USER_VARIABLE;
    }

    public boolean isUserVariable(String name) {
        return this.internTokenType == InternTokenType.USER_VARIABLE && this.tokenStringValue.equals(name);
    }

    public boolean isUserList() {
        return this.internTokenType == InternTokenType.USER_LIST;
    }

    public boolean isString() {
        return this.internTokenType == InternTokenType.STRING;
    }

    public void appendToTokenStringValue(String stringToAppend) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.tokenStringValue);
        stringBuilder.append(stringToAppend);
        this.tokenStringValue = stringBuilder.toString();
    }

    public void appendToTokenStringValue(List<InternToken> internTokensToAppend) {
        for (InternToken internToken : internTokensToAppend) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.tokenStringValue);
            stringBuilder.append(internToken.tokenStringValue);
            this.tokenStringValue = stringBuilder.toString();
        }
    }

    public InternTokenType getInternTokenType() {
        return this.internTokenType;
    }

    public InternToken deepCopy() {
        return new InternToken(this.internTokenType, this.tokenStringValue);
    }
}
