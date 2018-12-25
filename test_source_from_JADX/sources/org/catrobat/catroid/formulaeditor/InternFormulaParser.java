package org.catrobat.catroid.formulaeditor;

import android.util.Log;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;

public class InternFormulaParser {
    private static final int MAXIMUM_TOKENS_TO_PARSE = 1000;
    public static final int PARSER_INPUT_SYNTAX_ERROR = -3;
    public static final int PARSER_NO_INPUT = -4;
    public static final int PARSER_OK = -1;
    public static final int PARSER_STACK_OVERFLOW = -2;
    private static final String TAG = InternFormulaParser.class.getSimpleName();
    private InternToken currentToken;
    private int currentTokenParseIndex;
    private int errorTokenIndex;
    private List<InternToken> internTokensToParse;

    private class InternFormulaParserException extends Exception {
        private static final long serialVersionUID = 1;

        InternFormulaParserException(String errorMessage) {
            super(errorMessage);
        }
    }

    public InternFormulaParser(List<InternToken> internTokensToParse) {
        this.internTokensToParse = internTokensToParse;
    }

    private void getNextToken() {
        this.currentTokenParseIndex++;
        this.currentToken = (InternToken) this.internTokensToParse.get(this.currentTokenParseIndex);
    }

    public int getErrorTokenIndex() {
        return this.errorTokenIndex;
    }

    private FormulaElement findLowerOrEqualPriorityOperatorElement(Operators currentOperator, FormulaElement currentElement) {
        FormulaElement returnElement = currentElement.getParent();
        FormulaElement notNullElement = currentElement;
        boolean condition = true;
        while (condition) {
            if (returnElement == null) {
                condition = false;
                returnElement = notNullElement;
            } else if (Operators.getOperatorByValue(returnElement.getValue()).compareOperatorTo(currentOperator) < 0) {
                condition = false;
                returnElement = notNullElement;
            } else {
                notNullElement = returnElement;
                returnElement = returnElement.getParent();
            }
        }
        return returnElement;
    }

    public void handleOperator(String operator, FormulaElement currentElement, FormulaElement newElement) {
        if (currentElement.getParent() == null) {
            FormulaElement formulaElement = new FormulaElement(ElementType.OPERATOR, operator, null, currentElement, newElement);
            return;
        }
        Operators parentOperator = Operators.getOperatorByValue(currentElement.getParent().getValue());
        Operators currentOperator = Operators.getOperatorByValue(operator);
        if (parentOperator.compareOperatorTo(currentOperator) >= 0) {
            FormulaElement newLeftChild = findLowerOrEqualPriorityOperatorElement(currentOperator, currentElement);
            if (newLeftChild.getParent() != null) {
                newLeftChild.replaceWithSubElement(operator, newElement);
            } else {
                FormulaElement formulaElement2 = new FormulaElement(ElementType.OPERATOR, operator, null, newLeftChild, newElement);
            }
        } else {
            currentElement.replaceWithSubElement(operator, newElement);
        }
    }

    private void addEndOfFileToken() {
        this.internTokensToParse.add(new InternToken(InternTokenType.PARSER_END_OF_FILE));
    }

    private void removeEndOfFileToken() {
        this.internTokensToParse.remove(this.internTokensToParse.size() - 1);
    }

    public FormulaElement parseFormula() {
        this.errorTokenIndex = -1;
        this.currentTokenParseIndex = 0;
        if (this.internTokensToParse != null) {
            if (this.internTokensToParse.size() != 0) {
                if (this.internTokensToParse.size() > 1000) {
                    this.errorTokenIndex = -2;
                    this.errorTokenIndex = 0;
                    return null;
                }
                try {
                    List<InternToken> copyIternTokensToParse = new ArrayList(this.internTokensToParse);
                    if (InternFormulaUtils.applyBracketCorrection(copyIternTokensToParse)) {
                        this.internTokensToParse.clear();
                        this.internTokensToParse.addAll(copyIternTokensToParse);
                    }
                } catch (EmptyStackException emptyStackException) {
                    Log.d(TAG, "Bracket correction failed.", emptyStackException);
                }
                addEndOfFileToken();
                this.currentToken = (InternToken) this.internTokensToParse.get(0);
                FormulaElement formulaParseTree = null;
                try {
                    formulaParseTree = formula();
                } catch (InternFormulaParserException e) {
                    this.errorTokenIndex = this.currentTokenParseIndex;
                }
                removeEndOfFileToken();
                return formulaParseTree;
            }
        }
        this.errorTokenIndex = -4;
        return null;
    }

    private FormulaElement formula() throws InternFormulaParserException {
        FormulaElement termListTree = termList();
        if (this.currentToken.isEndOfFileToken()) {
            return termListTree;
        }
        throw new InternFormulaParserException("Parse Error");
    }

    private FormulaElement termList() throws InternFormulaParserException {
        FormulaElement currentElement = term();
        while (this.currentToken.isOperator() && !this.currentToken.getTokenStringValue().equals(Operators.LOGICAL_NOT.name())) {
            String operatorStringValue = this.currentToken.getTokenStringValue();
            getNextToken();
            FormulaElement loopTermTree = term();
            handleOperator(operatorStringValue, currentElement, loopTermTree);
            currentElement = loopTermTree;
        }
        return currentElement.getRoot();
    }

    private FormulaElement term() throws InternFormulaParserException {
        FormulaElement currentElement;
        FormulaElement termTree = new FormulaElement(ElementType.NUMBER, null, null);
        FormulaElement currentElement2 = termTree;
        if (this.currentToken.isOperator() && this.currentToken.getTokenStringValue().equals(Operators.MINUS.name())) {
            currentElement = new FormulaElement(ElementType.NUMBER, null, termTree, null, null);
            termTree.replaceElement(new FormulaElement(ElementType.OPERATOR, Operators.MINUS.name(), null, null, currentElement));
            getNextToken();
        } else if (this.currentToken.isOperator() && this.currentToken.getTokenStringValue().equals(Operators.LOGICAL_NOT.name())) {
            currentElement = new FormulaElement(ElementType.NUMBER, null, termTree, null, null);
            termTree.replaceElement(new FormulaElement(ElementType.OPERATOR, Operators.LOGICAL_NOT.name(), null, null, currentElement));
            getNextToken();
        } else {
            currentElement = currentElement2;
        }
        switch (this.currentToken.getInternTokenType()) {
            case NUMBER:
                currentElement.replaceElement(ElementType.NUMBER, number());
                break;
            case BRACKET_OPEN:
                getNextToken();
                currentElement.replaceElement(new FormulaElement(ElementType.BRACKET, null, null, null, termList()));
                if (this.currentToken.isBracketClose()) {
                    getNextToken();
                    break;
                }
                throw new InternFormulaParserException("Parse Error");
            case FUNCTION_NAME:
                currentElement.replaceElement(function());
                break;
            case SENSOR:
                currentElement.replaceElement(sensor());
                break;
            case USER_VARIABLE:
                currentElement.replaceElement(userVariable());
                break;
            case USER_LIST:
                currentElement.replaceElement(userList());
                break;
            case STRING:
                currentElement.replaceElement(ElementType.STRING, string());
                break;
            case COLLISION_FORMULA:
                currentElement.replaceElement(collision());
                break;
            default:
                throw new InternFormulaParserException("Parse Error");
        }
        return termTree;
    }

    private FormulaElement userVariable() throws InternFormulaParserException {
        if (ProjectManager.getInstance().getCurrentlyEditedScene().getDataContainer().getUserVariable(ProjectManager.getInstance().getCurrentSprite(), ProjectManager.getInstance().getCurrentUserBrick(), this.currentToken.getTokenStringValue()) == null) {
            throw new InternFormulaParserException("Parse Error");
        }
        FormulaElement lookTree = new FormulaElement(ElementType.USER_VARIABLE, this.currentToken.getTokenStringValue(), null);
        getNextToken();
        return lookTree;
    }

    private FormulaElement collision() throws InternFormulaParserException {
        String firstSpriteName = ProjectManager.getInstance().getCurrentSprite().getName();
        String secondSpriteName = this.currentToken.getTokenStringValue();
        int spriteCount = 0;
        for (Sprite sprite : ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList()) {
            if (sprite.getName().compareTo(firstSpriteName) == 0 || sprite.getName().compareTo(secondSpriteName) == 0) {
                spriteCount++;
            }
        }
        if (spriteCount == 2) {
            FormulaElement lookTree = new FormulaElement(ElementType.COLLISION_FORMULA, this.currentToken.getTokenStringValue(), null);
            getNextToken();
            return lookTree;
        }
        throw new InternFormulaParserException("Parse Error, Sprite was not found");
    }

    private FormulaElement userList() throws InternFormulaParserException {
        if (ProjectManager.getInstance().getCurrentlyEditedScene().getDataContainer().getUserList(ProjectManager.getInstance().getCurrentSprite(), this.currentToken.getTokenStringValue()) == null) {
            throw new InternFormulaParserException("Parse Error");
        }
        FormulaElement lookTree = new FormulaElement(ElementType.USER_LIST, this.currentToken.getTokenStringValue(), null);
        getNextToken();
        return lookTree;
    }

    private FormulaElement sensor() throws InternFormulaParserException {
        if (Sensors.isSensor(this.currentToken.getTokenStringValue())) {
            FormulaElement sensorTree = new FormulaElement(ElementType.SENSOR, this.currentToken.getTokenStringValue(), null);
            getNextToken();
            return sensorTree;
        }
        throw new InternFormulaParserException("Parse Error");
    }

    private FormulaElement function() throws InternFormulaParserException {
        if (Functions.isFunction(this.currentToken.getTokenStringValue())) {
            FormulaElement functionTree = new FormulaElement(ElementType.FUNCTION, this.currentToken.getTokenStringValue(), null);
            getNextToken();
            if (this.currentToken.isFunctionParameterBracketOpen()) {
                getNextToken();
                functionTree.setLeftChild(termList());
                if (this.currentToken.isFunctionParameterDelimiter()) {
                    getNextToken();
                    functionTree.setRightChild(termList());
                }
                if (this.currentToken.isFunctionParameterBracketClose()) {
                    getNextToken();
                } else {
                    throw new InternFormulaParserException("Parse Error");
                }
            }
            return functionTree;
        }
        throw new InternFormulaParserException("Parse Error");
    }

    private String number() throws InternFormulaParserException {
        String numberToCheck = this.currentToken.getTokenStringValue();
        if (numberToCheck.matches("(\\d)+(\\.(\\d)+)?")) {
            getNextToken();
            return numberToCheck;
        }
        throw new InternFormulaParserException("Parse Error");
    }

    private String string() {
        String currentStringValue = this.currentToken.getTokenStringValue();
        getNextToken();
        return currentStringValue;
    }
}
