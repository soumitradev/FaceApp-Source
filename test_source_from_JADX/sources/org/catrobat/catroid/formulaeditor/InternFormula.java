package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import android.util.Log;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.generated70026.R;

public class InternFormula {
    private static final String TAG = InternFormula.class.getSimpleName();
    private InternToken cursorPositionInternToken;
    private int cursorPositionInternTokenIndex;
    private CursorTokenPosition cursorTokenPosition;
    private int externCursorPosition;
    private String externFormulaString;
    private ExternInternRepresentationMapping externInternRepresentationMapping;
    private InternFormulaTokenSelection internFormulaTokenSelection;
    private List<InternToken> internTokenFormulaList;
    private InternFormulaParser internTokenFormulaParser;

    public enum CursorTokenPosition {
        LEFT,
        MIDDLE,
        RIGHT
    }

    public enum CursorTokenPropertiesAfterModification {
        LEFT,
        RIGHT,
        SELECT,
        DO_NOT_MODIFY
    }

    public enum TokenSelectionType {
        USER_SELECTION,
        PARSER_ERROR_SELECTION
    }

    public InternFormula(List<InternToken> internTokenList) {
        this.internTokenFormulaList = internTokenList;
        this.externFormulaString = null;
        this.externInternRepresentationMapping = new ExternInternRepresentationMapping();
        this.internFormulaTokenSelection = null;
        this.externCursorPosition = 0;
        this.cursorPositionInternTokenIndex = 0;
    }

    public InternFormula(List<InternToken> internTokenList, InternFormulaTokenSelection internFormulaTokenSelection, int externCursorPosition) {
        this.internTokenFormulaList = internTokenList;
        this.externFormulaString = null;
        this.externInternRepresentationMapping = new ExternInternRepresentationMapping();
        this.internFormulaTokenSelection = internFormulaTokenSelection;
        this.externCursorPosition = externCursorPosition;
        updateInternCursorPosition();
    }

    public void setCursorAndSelection(int externCursorPosition, boolean isSelected) {
        this.externCursorPosition = externCursorPosition;
        updateInternCursorPosition();
        this.internFormulaTokenSelection = null;
        if (!isSelected) {
            if (this.externInternRepresentationMapping.getInternTokenByExternIndex(externCursorPosition) == Integer.MIN_VALUE) {
                return;
            }
            if (getFirstLeftInternToken(externCursorPosition - 1) != this.cursorPositionInternToken && !this.cursorPositionInternToken.isFunctionParameterBracketOpen()) {
                return;
            }
            if (!(this.cursorPositionInternToken.isFunctionName() || ((this.cursorPositionInternToken.isFunctionParameterBracketOpen() && this.cursorTokenPosition == CursorTokenPosition.LEFT) || this.cursorPositionInternToken.isSensor() || this.cursorPositionInternToken.isUserVariable() || this.cursorPositionInternToken.isUserList() || this.cursorPositionInternToken.isString()))) {
                return;
            }
        }
        selectCursorPositionInternToken(TokenSelectionType.USER_SELECTION);
    }

    public void handleKeyInput(int resourceId, Context context, String name) {
        List<InternToken> keyInputInternTokenList = new InternFormulaKeyboardAdapter().createInternTokenListByResourceId(resourceId, name);
        CursorTokenPropertiesAfterModification cursorTokenPropertiesAfterInput = CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        if (resourceId != R.id.formula_editor_keyboard_delete) {
            if (!isTokenSelected()) {
                if (this.cursorTokenPosition != null) {
                    switch (this.cursorTokenPosition) {
                        case LEFT:
                            cursorTokenPropertiesAfterInput = insertLeftToCurrentToken(keyInputInternTokenList);
                            break;
                        case MIDDLE:
                            cursorTokenPropertiesAfterInput = replaceCursorPositionInternTokenByTokenList(keyInputInternTokenList);
                            break;
                        case RIGHT:
                            cursorTokenPropertiesAfterInput = insertRightToCurrentToken(keyInputInternTokenList);
                            break;
                        default:
                            break;
                    }
                }
                cursorTokenPropertiesAfterInput = insertRightToCurrentToken(keyInputInternTokenList);
            } else {
                cursorTokenPropertiesAfterInput = replaceSelection(keyInputInternTokenList);
            }
        } else {
            cursorTokenPropertiesAfterInput = handleDeletion();
        }
        generateExternFormulaStringAndInternExternMapping(context);
        updateExternCursorPosition(cursorTokenPropertiesAfterInput);
        updateInternCursorPosition();
    }

    public void updateVariableReferences(String oldName, String newName, Context context) {
        for (InternToken internToken : this.internTokenFormulaList) {
            internToken.updateVariableReferences(oldName, newName);
        }
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public void updateListReferences(String oldName, String newName, Context context) {
        for (InternToken internToken : this.internTokenFormulaList) {
            internToken.updateListReferences(oldName, newName);
        }
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public void getVariableAndListNames(List<String> variables, List<String> lists) {
        for (InternToken internToken : this.internTokenFormulaList) {
            internToken.getVariableAndListNames(variables, lists);
        }
    }

    public void updateCollisionFormula(String oldName, String newName, Context context) {
        for (InternToken internToken : this.internTokenFormulaList) {
            internToken.updateCollisionFormula(oldName, newName);
        }
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public void updateCollisionFormulaToVersion(Context context, float catroidLanguageVersion) {
        for (InternToken internToken : this.internTokenFormulaList) {
            internToken.updateCollisionFormulaToVersion(catroidLanguageVersion);
        }
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public void removeVariableReferences(String name, Context context) {
        LinkedList<InternToken> toRemove = new LinkedList();
        for (InternToken internToken : this.internTokenFormulaList) {
            if (internToken.isUserVariable(name)) {
                toRemove.add(internToken);
            }
        }
        Iterator it = toRemove.iterator();
        while (it.hasNext()) {
            this.internTokenFormulaList.remove((InternToken) it.next());
        }
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public void updateInternCursorPosition() {
        int cursorPositionTokenIndex = this.externInternRepresentationMapping.getInternTokenByExternIndex(this.externCursorPosition);
        int leftCursorPositionTokenIndex = this.externInternRepresentationMapping.getInternTokenByExternIndex(this.externCursorPosition - 1);
        int leftleftCursorPositionTokenIndex = this.externInternRepresentationMapping.getInternTokenByExternIndex(this.externCursorPosition - 2);
        if (cursorPositionTokenIndex != Integer.MIN_VALUE) {
            if (leftCursorPositionTokenIndex == Integer.MIN_VALUE || cursorPositionTokenIndex != leftCursorPositionTokenIndex) {
                this.cursorTokenPosition = CursorTokenPosition.LEFT;
            } else {
                this.cursorTokenPosition = CursorTokenPosition.MIDDLE;
            }
        } else if (leftCursorPositionTokenIndex != Integer.MIN_VALUE) {
            this.cursorTokenPosition = CursorTokenPosition.RIGHT;
        } else if (leftleftCursorPositionTokenIndex != Integer.MIN_VALUE) {
            this.cursorTokenPosition = CursorTokenPosition.RIGHT;
            leftCursorPositionTokenIndex = leftleftCursorPositionTokenIndex;
        } else {
            this.cursorTokenPosition = null;
            this.cursorPositionInternToken = null;
            return;
        }
        switch (this.cursorTokenPosition) {
            case LEFT:
                this.cursorPositionInternToken = (InternToken) this.internTokenFormulaList.get(cursorPositionTokenIndex);
                this.cursorPositionInternTokenIndex = cursorPositionTokenIndex;
                break;
            case MIDDLE:
                this.cursorPositionInternToken = (InternToken) this.internTokenFormulaList.get(cursorPositionTokenIndex);
                this.cursorPositionInternTokenIndex = cursorPositionTokenIndex;
                break;
            case RIGHT:
                this.cursorPositionInternToken = (InternToken) this.internTokenFormulaList.get(leftCursorPositionTokenIndex);
                this.cursorPositionInternTokenIndex = leftCursorPositionTokenIndex;
                break;
            default:
                break;
        }
    }

    private void updateExternCursorPosition(CursorTokenPropertiesAfterModification cursorTokenPropertiesAfterInput) {
        switch (cursorTokenPropertiesAfterInput) {
            case LEFT:
                setExternCursorPositionLeftTo(this.cursorPositionInternTokenIndex);
                return;
            case RIGHT:
                setExternCursorPositionRightTo(this.cursorPositionInternTokenIndex);
                return;
            default:
                return;
        }
    }

    private CursorTokenPropertiesAfterModification replaceSelection(List<InternToken> tokenListToInsert) {
        if (InternFormulaUtils.isPeriodToken(tokenListToInsert)) {
            tokenListToInsert = new LinkedList();
            tokenListToInsert.add(new InternToken(InternTokenType.NUMBER, "0."));
        }
        int internTokenSelectionStart = this.internFormulaTokenSelection.getStartIndex();
        int internTokenSelectionEnd = this.internFormulaTokenSelection.getEndIndex();
        if (internTokenSelectionStart <= internTokenSelectionEnd && internTokenSelectionStart >= 0) {
            if (internTokenSelectionEnd >= 0) {
                List<InternToken> tokenListToRemove = new LinkedList();
                for (int tokensToRemove = 0; tokensToRemove <= internTokenSelectionEnd - internTokenSelectionStart; tokensToRemove++) {
                    tokenListToRemove.add(this.internTokenFormulaList.get(internTokenSelectionStart + tokensToRemove));
                }
                if (InternFormulaUtils.isFunction(tokenListToRemove)) {
                    this.cursorPositionInternToken = (InternToken) tokenListToRemove.get(0);
                    this.cursorPositionInternTokenIndex = internTokenSelectionStart;
                    return replaceCursorPositionInternTokenByTokenList(tokenListToInsert);
                }
                replaceInternTokens(tokenListToInsert, internTokenSelectionStart, internTokenSelectionEnd);
                return setCursorPositionAndSelectionAfterInput(internTokenSelectionStart);
            }
        }
        this.internFormulaTokenSelection = null;
        return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
    }

    private void deleteInternTokens(int deleteIndexStart, int deleteIndexEnd) {
        replaceInternTokens(new LinkedList(), deleteIndexStart, deleteIndexEnd);
    }

    private void replaceInternTokens(List<InternToken> tokenListToInsert, int replaceIndexStart, int replaceIndexEnd) {
        for (int tokensToRemove = replaceIndexEnd - replaceIndexStart; tokensToRemove >= 0; tokensToRemove--) {
            this.internTokenFormulaList.remove(replaceIndexStart);
        }
        this.internTokenFormulaList.addAll(replaceIndexStart, tokenListToInsert);
    }

    private CursorTokenPropertiesAfterModification handleDeletion() {
        CursorTokenPropertiesAfterModification cursorTokenPropertiesAfterModification = CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        if (this.internFormulaTokenSelection != null) {
            deleteInternTokens(this.internFormulaTokenSelection.getStartIndex(), this.internFormulaTokenSelection.getEndIndex());
            this.cursorPositionInternTokenIndex = this.internFormulaTokenSelection.getStartIndex();
            this.cursorPositionInternToken = null;
            this.internFormulaTokenSelection = null;
            return CursorTokenPropertiesAfterModification.LEFT;
        }
        InternToken firstLeftInternToken;
        switch (this.cursorTokenPosition) {
            case LEFT:
                firstLeftInternToken = getFirstLeftInternToken(this.externCursorPosition - 1);
                if (firstLeftInternToken == null) {
                    return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
                }
                if (firstLeftInternToken.getInternTokenType() != InternTokenType.FUNCTION_PARAMETER_DELIMITER) {
                    return deleteInternTokenByIndex(this.internTokenFormulaList.indexOf(firstLeftInternToken));
                }
                setExternCursorPositionLeftTo(this.internTokenFormulaList.indexOf(firstLeftInternToken));
                return cursorTokenPropertiesAfterModification;
            case MIDDLE:
                return deleteInternTokenByIndex(this.cursorPositionInternTokenIndex);
            case RIGHT:
                firstLeftInternToken = getFirstLeftInternToken(this.externCursorPosition);
                if (firstLeftInternToken.getInternTokenType() != InternTokenType.FUNCTION_PARAMETER_DELIMITER) {
                    return deleteInternTokenByIndex(this.cursorPositionInternTokenIndex);
                }
                setExternCursorPositionLeftTo(this.internTokenFormulaList.indexOf(firstLeftInternToken));
                return cursorTokenPropertiesAfterModification;
            default:
                return cursorTokenPropertiesAfterModification;
        }
    }

    private CursorTokenPropertiesAfterModification deleteInternTokenByIndex(int internTokenIndex) {
        InternToken tokenToDelete = (InternToken) this.internTokenFormulaList.get(internTokenIndex);
        List<InternToken> functionInternTokens;
        int functionInternTokensLastIndex;
        int startDeletionIndex;
        switch (tokenToDelete.getInternTokenType()) {
            case NUMBER:
                int externNumberOffset = this.externInternRepresentationMapping.getExternTokenStartOffset(this.externCursorPosition, internTokenIndex);
                if (externNumberOffset == -1) {
                    return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
                }
                if (InternFormulaUtils.deleteNumberByOffset(tokenToDelete, externNumberOffset) == null) {
                    this.internTokenFormulaList.remove(internTokenIndex);
                    this.cursorPositionInternTokenIndex = internTokenIndex;
                    this.cursorPositionInternToken = null;
                    return CursorTokenPropertiesAfterModification.LEFT;
                }
                this.externCursorPosition--;
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            case FUNCTION_NAME:
                functionInternTokens = InternFormulaUtils.getFunctionByName(this.internTokenFormulaList, internTokenIndex);
                if (functionInternTokens != null) {
                    if (functionInternTokens.size() != 0) {
                        deleteInternTokens(internTokenIndex, this.internTokenFormulaList.indexOf((InternToken) functionInternTokens.get(functionInternTokens.size() - 1)));
                        setExternCursorPositionLeftTo(internTokenIndex);
                        this.cursorPositionInternTokenIndex = internTokenIndex;
                        this.cursorPositionInternToken = null;
                        return CursorTokenPropertiesAfterModification.LEFT;
                    }
                }
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            case FUNCTION_PARAMETERS_BRACKET_OPEN:
                functionInternTokens = InternFormulaUtils.getFunctionByFunctionBracketOpen(this.internTokenFormulaList, internTokenIndex);
                if (functionInternTokens != null) {
                    if (functionInternTokens.size() != 0) {
                        functionInternTokensLastIndex = functionInternTokens.size() - 1;
                        startDeletionIndex = this.internTokenFormulaList.indexOf(functionInternTokens.get(0));
                        deleteInternTokens(startDeletionIndex, this.internTokenFormulaList.indexOf(functionInternTokens.get(functionInternTokensLastIndex)));
                        this.cursorPositionInternTokenIndex = startDeletionIndex;
                        this.cursorPositionInternToken = null;
                        return CursorTokenPropertiesAfterModification.LEFT;
                    }
                }
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            case FUNCTION_PARAMETERS_BRACKET_CLOSE:
                functionInternTokens = InternFormulaUtils.getFunctionByFunctionBracketClose(this.internTokenFormulaList, internTokenIndex);
                if (functionInternTokens != null) {
                    if (functionInternTokens.size() != 0) {
                        functionInternTokensLastIndex = functionInternTokens.size() - 1;
                        startDeletionIndex = this.internTokenFormulaList.indexOf(functionInternTokens.get(0));
                        deleteInternTokens(startDeletionIndex, this.internTokenFormulaList.indexOf(functionInternTokens.get(functionInternTokensLastIndex)));
                        this.cursorPositionInternTokenIndex = startDeletionIndex;
                        this.cursorPositionInternToken = null;
                        return CursorTokenPropertiesAfterModification.LEFT;
                    }
                }
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            case FUNCTION_PARAMETER_DELIMITER:
                functionInternTokens = InternFormulaUtils.getFunctionByParameterDelimiter(this.internTokenFormulaList, internTokenIndex);
                if (functionInternTokens != null) {
                    if (functionInternTokens.size() != 0) {
                        functionInternTokensLastIndex = functionInternTokens.size() - 1;
                        startDeletionIndex = this.internTokenFormulaList.indexOf(functionInternTokens.get(0));
                        deleteInternTokens(startDeletionIndex, this.internTokenFormulaList.indexOf(functionInternTokens.get(functionInternTokensLastIndex)));
                        this.cursorPositionInternTokenIndex = startDeletionIndex;
                        this.cursorPositionInternToken = null;
                        return CursorTokenPropertiesAfterModification.LEFT;
                    }
                }
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            default:
                deleteInternTokens(internTokenIndex, internTokenIndex);
                this.cursorPositionInternTokenIndex = internTokenIndex;
                this.cursorPositionInternToken = null;
                return CursorTokenPropertiesAfterModification.LEFT;
        }
    }

    private void setExternCursorPositionLeftTo(int internTokenIndex) {
        if (this.internTokenFormulaList.size() < 1) {
            this.externCursorPosition = 1;
        } else if (internTokenIndex >= this.internTokenFormulaList.size()) {
            setExternCursorPositionRightTo(this.internTokenFormulaList.size() - 1);
        } else {
            int externTokenStartIndex = this.externInternRepresentationMapping.getExternTokenStartIndex(internTokenIndex);
            if (externTokenStartIndex != Integer.MIN_VALUE) {
                this.externCursorPosition = externTokenStartIndex;
                this.cursorTokenPosition = CursorTokenPosition.LEFT;
            }
        }
    }

    private void setExternCursorPositionRightTo(int internTokenIndex) {
        if (this.internTokenFormulaList.size() >= 1) {
            if (internTokenIndex >= this.internTokenFormulaList.size()) {
                internTokenIndex = this.internTokenFormulaList.size() - 1;
            }
            int externTokenEndIndex = this.externInternRepresentationMapping.getExternTokenEndIndex(internTokenIndex);
            if (externTokenEndIndex != Integer.MIN_VALUE) {
                this.externCursorPosition = externTokenEndIndex;
                this.cursorTokenPosition = CursorTokenPosition.RIGHT;
            }
        }
    }

    public void generateExternFormulaStringAndInternExternMapping(Context context) {
        InternToExternGenerator internToExternGenerator = new InternToExternGenerator(context);
        internToExternGenerator.generateExternStringAndMapping(this.internTokenFormulaList);
        this.externFormulaString = internToExternGenerator.getGeneratedExternFormulaString();
        this.externInternRepresentationMapping = internToExternGenerator.getGeneratedExternInternRepresentationMapping();
    }

    public String trimExternFormulaString(Context context) {
        InternToExternGenerator internToExternGenerator = new InternToExternGenerator(context);
        internToExternGenerator.trimExternString(this.internTokenFormulaList);
        this.externFormulaString = internToExternGenerator.getGeneratedExternFormulaString();
        this.externInternRepresentationMapping = internToExternGenerator.getGeneratedExternInternRepresentationMapping();
        return this.externFormulaString;
    }

    private void selectCursorPositionInternToken(TokenSelectionType internTokenSelectionType) {
        this.internFormulaTokenSelection = null;
        if (this.cursorPositionInternToken != null) {
            List<InternToken> functionInternTokens;
            switch (this.cursorPositionInternToken.getInternTokenType()) {
                case FUNCTION_NAME:
                    functionInternTokens = InternFormulaUtils.getFunctionByName(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                    if (functionInternTokens != null) {
                        if (functionInternTokens.size() != 0) {
                            this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.cursorPositionInternTokenIndex, this.internTokenFormulaList.indexOf((InternToken) functionInternTokens.get(functionInternTokens.size() - 1)));
                            break;
                        }
                    }
                    return;
                case FUNCTION_PARAMETERS_BRACKET_OPEN:
                    functionInternTokens = InternFormulaUtils.getFunctionByFunctionBracketOpen(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                    if (functionInternTokens != null) {
                        if (functionInternTokens.size() != 0) {
                            this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.internTokenFormulaList.indexOf(functionInternTokens.get(0)), this.internTokenFormulaList.indexOf(functionInternTokens.get(functionInternTokens.size() - 1)));
                            break;
                        }
                    }
                    return;
                case FUNCTION_PARAMETERS_BRACKET_CLOSE:
                    functionInternTokens = InternFormulaUtils.getFunctionByFunctionBracketClose(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                    if (functionInternTokens != null) {
                        if (functionInternTokens.size() != 0) {
                            this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.internTokenFormulaList.indexOf(functionInternTokens.get(0)), this.internTokenFormulaList.indexOf(functionInternTokens.get(functionInternTokens.size() - 1)));
                            break;
                        }
                    }
                    return;
                case FUNCTION_PARAMETER_DELIMITER:
                    functionInternTokens = InternFormulaUtils.getFunctionByParameterDelimiter(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                    if (functionInternTokens != null) {
                        if (functionInternTokens.size() != 0) {
                            this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.internTokenFormulaList.indexOf(functionInternTokens.get(0)), this.internTokenFormulaList.indexOf(functionInternTokens.get(functionInternTokens.size() - 1)));
                            break;
                        }
                    }
                    return;
                case BRACKET_OPEN:
                    functionInternTokens = InternFormulaUtils.generateTokenListByBracketOpen(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                    if (functionInternTokens != null) {
                        if (functionInternTokens.size() != 0) {
                            this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.cursorPositionInternTokenIndex, this.internTokenFormulaList.indexOf(functionInternTokens.get(functionInternTokens.size() - 1)));
                            break;
                        }
                    }
                    return;
                case BRACKET_CLOSE:
                    functionInternTokens = InternFormulaUtils.generateTokenListByBracketClose(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                    if (functionInternTokens != null) {
                        if (functionInternTokens.size() != 0) {
                            this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.internTokenFormulaList.indexOf(functionInternTokens.get(0)), this.internTokenFormulaList.indexOf(functionInternTokens.get(functionInternTokens.size() - 1)));
                            break;
                        }
                    }
                    return;
                default:
                    this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.cursorPositionInternTokenIndex, this.cursorPositionInternTokenIndex);
                    break;
            }
        }
    }

    private CursorTokenPropertiesAfterModification insertLeftToCurrentToken(List<InternToken> internTokensToInsert) {
        InternToken firstLeftInternToken = null;
        if (this.cursorPositionInternTokenIndex > 0) {
            firstLeftInternToken = (InternToken) this.internTokenFormulaList.get(this.cursorPositionInternTokenIndex - 1);
        }
        if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isNumberToken(internTokensToInsert)) {
            InternFormulaUtils.insertIntoNumberToken(this.cursorPositionInternToken, 0, ((InternToken) internTokensToInsert.get(0)).getTokenStringValue());
            this.externCursorPosition++;
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        } else if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
            if (this.cursorPositionInternToken.getTokenStringValue().contains(".")) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            InternFormulaUtils.insertIntoNumberToken(this.cursorPositionInternToken, 0, "0.");
            this.externCursorPosition += 2;
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        } else if (firstLeftInternToken != null && firstLeftInternToken.isNumber() && InternFormulaUtils.isNumberToken(internTokensToInsert)) {
            firstLeftInternToken.appendToTokenStringValue((List) internTokensToInsert);
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        } else if (firstLeftInternToken != null && firstLeftInternToken.isNumber() && InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
            if (firstLeftInternToken.getTokenStringValue().contains(".")) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            firstLeftInternToken.appendToTokenStringValue(".");
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        } else if (InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
            this.internTokenFormulaList.add(this.cursorPositionInternTokenIndex, new InternToken(InternTokenType.NUMBER, "0."));
            this.cursorPositionInternToken = null;
            return CursorTokenPropertiesAfterModification.RIGHT;
        } else {
            this.internTokenFormulaList.addAll(this.cursorPositionInternTokenIndex, internTokensToInsert);
            return setCursorPositionAndSelectionAfterInput(this.cursorPositionInternTokenIndex);
        }
    }

    private CursorTokenPropertiesAfterModification insertRightToCurrentToken(List<InternToken> internTokensToInsert) {
        if (this.cursorPositionInternToken == null) {
            if (InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
                internTokensToInsert = new LinkedList();
                internTokensToInsert.add(new InternToken(InternTokenType.NUMBER, "0."));
            }
            this.internTokenFormulaList.addAll(0, internTokensToInsert);
            return setCursorPositionAndSelectionAfterInput(0);
        } else if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isNumberToken(internTokensToInsert)) {
            this.cursorPositionInternToken.appendToTokenStringValue((List) internTokensToInsert);
            return CursorTokenPropertiesAfterModification.RIGHT;
        } else if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
            if (this.cursorPositionInternToken.getTokenStringValue().contains(".")) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            this.cursorPositionInternToken.appendToTokenStringValue(".");
            return CursorTokenPropertiesAfterModification.RIGHT;
        } else if (InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
            this.internTokenFormulaList.add(this.cursorPositionInternTokenIndex + 1, new InternToken(InternTokenType.NUMBER, "0."));
            this.cursorPositionInternToken = null;
            this.cursorPositionInternTokenIndex++;
            return CursorTokenPropertiesAfterModification.RIGHT;
        } else {
            this.internTokenFormulaList.addAll(this.cursorPositionInternTokenIndex + 1, internTokensToInsert);
            return setCursorPositionAndSelectionAfterInput(this.cursorPositionInternTokenIndex + 1);
        }
    }

    private CursorTokenPropertiesAfterModification setCursorPositionAndSelectionAfterInput(int insertedInternTokenIndex) {
        if (((InternToken) this.internTokenFormulaList.get(insertedInternTokenIndex)).getInternTokenType() == InternTokenType.FUNCTION_NAME) {
            List<InternToken> functionInternTokenList = InternFormulaUtils.getFunctionByName(this.internTokenFormulaList, insertedInternTokenIndex);
            if (functionInternTokenList.size() >= 4) {
                this.internFormulaTokenSelection = new InternFormulaTokenSelection(TokenSelectionType.USER_SELECTION, insertedInternTokenIndex + 2, (((List) InternFormulaUtils.getFunctionParameterInternTokensAsLists(functionInternTokenList).get(0)).size() + insertedInternTokenIndex) + 1);
                this.cursorPositionInternTokenIndex = this.internFormulaTokenSelection.getEndIndex();
            } else {
                this.cursorPositionInternTokenIndex = (functionInternTokenList.size() + insertedInternTokenIndex) - 1;
                this.internFormulaTokenSelection = null;
            }
        } else {
            this.cursorPositionInternTokenIndex = insertedInternTokenIndex;
            this.internFormulaTokenSelection = null;
        }
        this.cursorPositionInternToken = null;
        return CursorTokenPropertiesAfterModification.RIGHT;
    }

    private CursorTokenPropertiesAfterModification replaceCursorPositionInternTokenByTokenList(List<InternToken> internTokensToReplaceWith) {
        Log.i(TAG, "replaceCursorPositionInternTokenByTokenList:enter");
        if (this.cursorPositionInternToken.isNumber() && internTokensToReplaceWith.size() == 1 && ((InternToken) internTokensToReplaceWith.get(0)).isOperator()) {
            replaceInternTokens(InternFormulaUtils.insertOperatorToNumberToken(this.cursorPositionInternToken, this.externInternRepresentationMapping.getExternTokenStartOffset(this.externCursorPosition, this.cursorPositionInternTokenIndex), (InternToken) internTokensToReplaceWith.get(0)), this.cursorPositionInternTokenIndex, this.cursorPositionInternTokenIndex);
            return setCursorPositionAndSelectionAfterInput(this.cursorPositionInternTokenIndex);
        } else if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isNumberToken(internTokensToReplaceWith)) {
            InternToken numberTokenToInsert = (InternToken) internTokensToReplaceWith.get(0);
            externNumberOffset = this.externInternRepresentationMapping.getExternTokenStartOffset(this.externCursorPosition, this.cursorPositionInternTokenIndex);
            if (externNumberOffset == -1) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            InternFormulaUtils.insertIntoNumberToken(this.cursorPositionInternToken, externNumberOffset, numberTokenToInsert.getTokenStringValue());
            this.externCursorPosition++;
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        } else if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isPeriodToken(internTokensToReplaceWith)) {
            if (this.cursorPositionInternToken.getTokenStringValue().contains(".")) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            externNumberOffset = this.externInternRepresentationMapping.getExternTokenStartOffset(this.externCursorPosition, this.cursorPositionInternTokenIndex);
            if (externNumberOffset == -1) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            InternFormulaUtils.insertIntoNumberToken(this.cursorPositionInternToken, externNumberOffset, ".");
            this.externCursorPosition++;
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        } else if (this.cursorPositionInternToken.isFunctionName()) {
            List<InternToken> functionInternTokens = InternFormulaUtils.getFunctionByName(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
            if (functionInternTokens == null) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            replaceInternTokens(InternFormulaUtils.replaceFunctionByTokens(functionInternTokens, internTokensToReplaceWith), this.cursorPositionInternTokenIndex, this.internTokenFormulaList.indexOf((InternToken) functionInternTokens.get(functionInternTokens.size() - 1)));
            return setCursorPositionAndSelectionAfterInput(this.cursorPositionInternTokenIndex);
        } else if (InternFormulaUtils.isPeriodToken(internTokensToReplaceWith)) {
            this.internTokenFormulaList.add(this.cursorPositionInternTokenIndex + 1, new InternToken(InternTokenType.NUMBER, "0."));
            this.cursorPositionInternToken = null;
            this.cursorPositionInternTokenIndex++;
            return CursorTokenPropertiesAfterModification.RIGHT;
        } else {
            replaceInternTokens(internTokensToReplaceWith, this.cursorPositionInternTokenIndex, this.cursorPositionInternTokenIndex);
            return setCursorPositionAndSelectionAfterInput(this.cursorPositionInternTokenIndex);
        }
    }

    public InternToken getFirstLeftInternToken(int externIndex) {
        for (int searchIndex = externIndex; searchIndex >= 0; searchIndex--) {
            if (this.externInternRepresentationMapping.getInternTokenByExternIndex(searchIndex) != Integer.MIN_VALUE) {
                return (InternToken) this.internTokenFormulaList.get(this.externInternRepresentationMapping.getInternTokenByExternIndex(searchIndex));
            }
        }
        return null;
    }

    public int getExternCursorPosition() {
        return this.externCursorPosition;
    }

    public InternFormulaParser getInternFormulaParser() {
        this.internTokenFormulaParser = new InternFormulaParser(this.internTokenFormulaList);
        return this.internTokenFormulaParser;
    }

    public void selectParseErrorTokenAndSetCursor() {
        if (this.internTokenFormulaParser != null) {
            if (this.internTokenFormulaList.size() != 0) {
                int internErrorTokenIndex = this.internTokenFormulaParser.getErrorTokenIndex();
                if (internErrorTokenIndex >= 0) {
                    if (internErrorTokenIndex >= this.internTokenFormulaList.size()) {
                        internErrorTokenIndex = this.internTokenFormulaList.size() - 1;
                    }
                    setExternCursorPositionRightTo(internErrorTokenIndex);
                    this.cursorPositionInternTokenIndex = internErrorTokenIndex;
                    this.cursorPositionInternToken = (InternToken) this.internTokenFormulaList.get(this.cursorPositionInternTokenIndex);
                    selectCursorPositionInternToken(TokenSelectionType.PARSER_ERROR_SELECTION);
                }
            }
        }
    }

    public TokenSelectionType getExternSelectionType() {
        if (isTokenSelected()) {
            return this.internFormulaTokenSelection.getTokenSelectionType();
        }
        return null;
    }

    public void selectWholeFormula() {
        if (this.internTokenFormulaList.size() != 0) {
            this.internFormulaTokenSelection = new InternFormulaTokenSelection(TokenSelectionType.USER_SELECTION, 0, this.internTokenFormulaList.size() - 1);
        }
    }

    public InternFormulaState getInternFormulaState() {
        List<InternToken> deepCopyOfInternTokenFormula = new LinkedList();
        InternFormulaTokenSelection deepCopyOfInternFormulaTokenSelection = null;
        for (InternToken tokenToCopy : this.internTokenFormulaList) {
            deepCopyOfInternTokenFormula.add(tokenToCopy.deepCopy());
        }
        if (isTokenSelected()) {
            deepCopyOfInternFormulaTokenSelection = this.internFormulaTokenSelection.deepCopy();
        }
        return new InternFormulaState(deepCopyOfInternTokenFormula, deepCopyOfInternFormulaTokenSelection, this.externCursorPosition);
    }

    public InternFormulaTokenSelection getSelection() {
        return this.internFormulaTokenSelection;
    }

    public int getExternSelectionStartIndex() {
        if (this.internFormulaTokenSelection == null) {
            return -1;
        }
        int externSelectionStartIndex = this.externInternRepresentationMapping.getExternTokenStartIndex(this.internFormulaTokenSelection.getStartIndex());
        if (externSelectionStartIndex == Integer.MIN_VALUE) {
            return -1;
        }
        return externSelectionStartIndex;
    }

    public int getExternSelectionEndIndex() {
        if (this.internFormulaTokenSelection == null) {
            return -1;
        }
        int externSelectionEndIndex = this.externInternRepresentationMapping.getExternTokenEndIndex(this.internFormulaTokenSelection.getEndIndex());
        if (externSelectionEndIndex == Integer.MIN_VALUE) {
            return -1;
        }
        return externSelectionEndIndex;
    }

    private InternToken getSelectedToken() {
        if (this.internFormulaTokenSelection != null) {
            if (this.internFormulaTokenSelection.getTokenSelectionType() == TokenSelectionType.USER_SELECTION) {
                int currentIndex = 0;
                for (InternToken token : this.internTokenFormulaList) {
                    if (token.getInternTokenType() == InternTokenType.STRING && this.internFormulaTokenSelection.getStartIndex() == currentIndex) {
                        return token;
                    }
                    currentIndex++;
                }
                return null;
            }
        }
        return null;
    }

    public String getSelectedText() {
        InternToken token = getSelectedToken();
        if (token == null) {
            return null;
        }
        return token.getTokenStringValue();
    }

    public void overrideSelectedText(String string, Context context) {
        InternToken token = getSelectedToken();
        if (token != null) {
            token.setTokenStringValue(string);
            generateExternFormulaStringAndInternExternMapping(context);
        }
    }

    public String getExternFormulaString() {
        return this.externFormulaString;
    }

    private boolean isTokenSelected() {
        return this.internFormulaTokenSelection != null;
    }

    public boolean isThereSomethingToDelete() {
        boolean z = true;
        if (this.internFormulaTokenSelection != null) {
            return true;
        }
        if (this.cursorTokenPosition == null || (this.cursorTokenPosition == CursorTokenPosition.LEFT && getFirstLeftInternToken(this.externCursorPosition - 1) == null)) {
            z = false;
        }
        return z;
    }
}
