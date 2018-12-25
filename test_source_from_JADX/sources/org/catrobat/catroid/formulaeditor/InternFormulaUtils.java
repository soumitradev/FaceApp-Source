package org.catrobat.catroid.formulaeditor;

import java.util.Collection;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public final class InternFormulaUtils {
    private InternFormulaUtils() {
        throw new AssertionError();
    }

    public static List<InternToken> getFunctionByFunctionBracketClose(List<InternToken> internTokenList, int functionBracketCloseInternTokenListIndex) {
        if (functionBracketCloseInternTokenListIndex != 0) {
            if (functionBracketCloseInternTokenListIndex != internTokenList.size()) {
                List<InternToken> functionInternTokenList = new LinkedList();
                functionInternTokenList.add(internTokenList.get(functionBracketCloseInternTokenListIndex));
                int functionIndex = functionBracketCloseInternTokenListIndex - 1;
                int nestedFunctionsCounter = 1;
                while (functionIndex >= 0) {
                    InternToken tempSearchToken = (InternToken) internTokenList.get(functionIndex);
                    functionIndex--;
                    switch (tempSearchToken.getInternTokenType()) {
                        case FUNCTION_PARAMETERS_BRACKET_OPEN:
                            nestedFunctionsCounter--;
                            break;
                        case FUNCTION_PARAMETERS_BRACKET_CLOSE:
                            nestedFunctionsCounter++;
                            break;
                        default:
                            break;
                    }
                    functionInternTokenList.add(tempSearchToken);
                    if (tempSearchToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN && nestedFunctionsCounter == 0) {
                        if (functionIndex < 0) {
                            return null;
                        }
                        tempSearchToken = (InternToken) internTokenList.get(functionIndex);
                        if (tempSearchToken.getInternTokenType() != InternTokenType.FUNCTION_NAME) {
                            return null;
                        }
                        functionInternTokenList.add(tempSearchToken);
                        Collections.reverse(functionInternTokenList);
                        return functionInternTokenList;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public static List<InternToken> getFunctionByParameterDelimiter(List<InternToken> internTokenList, int functionParameterDelimiterInternTokenListIndex) {
        if (functionParameterDelimiterInternTokenListIndex != 0) {
            if (functionParameterDelimiterInternTokenListIndex != internTokenList.size()) {
                List<InternToken> functionInternTokenList = new LinkedList();
                functionInternTokenList.add(internTokenList.get(functionParameterDelimiterInternTokenListIndex));
                int functionIndex = functionParameterDelimiterInternTokenListIndex - 1;
                int nestedFunctionsCounter = 1;
                while (functionIndex >= 0) {
                    InternToken tempSearchToken = (InternToken) internTokenList.get(functionIndex);
                    functionIndex--;
                    switch (tempSearchToken.getInternTokenType()) {
                        case FUNCTION_PARAMETERS_BRACKET_OPEN:
                            nestedFunctionsCounter--;
                            break;
                        case FUNCTION_PARAMETERS_BRACKET_CLOSE:
                            nestedFunctionsCounter++;
                            break;
                        default:
                            break;
                    }
                    functionInternTokenList.add(tempSearchToken);
                    if (tempSearchToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN && nestedFunctionsCounter == 0) {
                        if (functionIndex < 0) {
                            return null;
                        }
                        tempSearchToken = (InternToken) internTokenList.get(functionIndex);
                        if (tempSearchToken.getInternTokenType() != InternTokenType.FUNCTION_NAME) {
                            return null;
                        }
                        functionInternTokenList.add(tempSearchToken);
                        Collections.reverse(functionInternTokenList);
                        functionIndex = functionParameterDelimiterInternTokenListIndex + 1;
                        nestedFunctionsCounter = 1;
                        while (functionIndex < internTokenList.size()) {
                            tempSearchToken = (InternToken) internTokenList.get(functionIndex);
                            functionIndex++;
                            switch (tempSearchToken.getInternTokenType()) {
                                case FUNCTION_PARAMETERS_BRACKET_OPEN:
                                    nestedFunctionsCounter++;
                                    break;
                                case FUNCTION_PARAMETERS_BRACKET_CLOSE:
                                    nestedFunctionsCounter--;
                                    break;
                                default:
                                    break;
                            }
                            functionInternTokenList.add(tempSearchToken);
                            if (tempSearchToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE && nestedFunctionsCounter == 0) {
                                return functionInternTokenList;
                            }
                        }
                        return null;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public static List<InternToken> getFunctionByFunctionBracketOpen(List<InternToken> internTokenList, int functionBracketOpenInternTokenListIndex) {
        if (functionBracketOpenInternTokenListIndex > 0) {
            if (functionBracketOpenInternTokenListIndex < internTokenList.size()) {
                if (((InternToken) internTokenList.get(functionBracketOpenInternTokenListIndex - 1)).getInternTokenType() != InternTokenType.FUNCTION_NAME) {
                    return null;
                }
                return getFunctionByName(internTokenList, functionBracketOpenInternTokenListIndex - 1);
            }
        }
        return null;
    }

    public static List<InternToken> getFunctionByName(List<InternToken> internTokenList, int functionStartListIndex) {
        InternToken functionNameToken = (InternToken) internTokenList.get(functionStartListIndex);
        List<InternToken> functionInternTokenList = new LinkedList();
        if (functionNameToken.getInternTokenType() != InternTokenType.FUNCTION_NAME) {
            return null;
        }
        functionInternTokenList.add(functionNameToken);
        int functionIndex = functionStartListIndex + 1;
        if (functionIndex >= internTokenList.size()) {
            return functionInternTokenList;
        }
        InternToken functionStartParameter = (InternToken) internTokenList.get(functionIndex);
        if (!functionStartParameter.isFunctionParameterBracketOpen()) {
            return functionInternTokenList;
        }
        functionInternTokenList.add(functionStartParameter);
        int nestedFunctionsCounter = 1;
        functionIndex++;
        while (functionIndex < internTokenList.size()) {
            InternToken tempSearchToken = (InternToken) internTokenList.get(functionIndex);
            functionIndex++;
            switch (tempSearchToken.getInternTokenType()) {
                case FUNCTION_PARAMETERS_BRACKET_OPEN:
                    nestedFunctionsCounter++;
                    break;
                case FUNCTION_PARAMETERS_BRACKET_CLOSE:
                    nestedFunctionsCounter--;
                    break;
                default:
                    break;
            }
            functionInternTokenList.add(tempSearchToken);
            if (tempSearchToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE && nestedFunctionsCounter == 0) {
                return functionInternTokenList;
            }
        }
        return null;
    }

    public static List<InternToken> generateTokenListByBracketOpen(List<InternToken> internTokenList, int internTokenListIndex) {
        if (internTokenListIndex != internTokenList.size()) {
            if (((InternToken) internTokenList.get(internTokenListIndex)).getInternTokenType() == InternTokenType.BRACKET_OPEN) {
                List<InternToken> bracketInternTokenListToReturn = new LinkedList();
                bracketInternTokenListToReturn.add(internTokenList.get(internTokenListIndex));
                int bracketsIndex = internTokenListIndex + 1;
                int nestedBracketsCounter = 1;
                while (bracketsIndex < internTokenList.size()) {
                    InternToken tempSearchToken = (InternToken) internTokenList.get(bracketsIndex);
                    bracketsIndex++;
                    switch (tempSearchToken.getInternTokenType()) {
                        case BRACKET_OPEN:
                            nestedBracketsCounter++;
                            break;
                        case BRACKET_CLOSE:
                            nestedBracketsCounter--;
                            break;
                        default:
                            break;
                    }
                    bracketInternTokenListToReturn.add(tempSearchToken);
                    if (tempSearchToken.getInternTokenType() == InternTokenType.BRACKET_CLOSE && nestedBracketsCounter == 0) {
                        return bracketInternTokenListToReturn;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public static List<InternToken> generateTokenListByBracketClose(List<InternToken> internTokenList, int internTokenListIndex) {
        if (internTokenListIndex != internTokenList.size()) {
            if (((InternToken) internTokenList.get(internTokenListIndex)).getInternTokenType() == InternTokenType.BRACKET_CLOSE) {
                List<InternToken> bracketInternTokenListToReturn = new LinkedList();
                bracketInternTokenListToReturn.add(internTokenList.get(internTokenListIndex));
                int bracketSearchIndex = internTokenListIndex - 1;
                int nestedBracketsCounter = 1;
                while (bracketSearchIndex >= 0) {
                    InternToken tempSearchToken = (InternToken) internTokenList.get(bracketSearchIndex);
                    bracketSearchIndex--;
                    switch (tempSearchToken.getInternTokenType()) {
                        case BRACKET_OPEN:
                            nestedBracketsCounter--;
                            break;
                        case BRACKET_CLOSE:
                            nestedBracketsCounter++;
                            break;
                        default:
                            break;
                    }
                    bracketInternTokenListToReturn.add(tempSearchToken);
                    if (tempSearchToken.getInternTokenType() == InternTokenType.BRACKET_OPEN && nestedBracketsCounter == 0) {
                        Collections.reverse(bracketInternTokenListToReturn);
                        return bracketInternTokenListToReturn;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public static List<List<InternToken>> getFunctionParameterInternTokensAsLists(List<InternToken> functionInternTokenList) {
        List<List<InternToken>> functionParameterInternTokenList = new LinkedList();
        if (functionInternTokenList != null && functionInternTokenList.size() >= 4 && ((InternToken) functionInternTokenList.get(0)).getInternTokenType() == InternTokenType.FUNCTION_NAME) {
            if (((InternToken) functionInternTokenList.get(1)).getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN) {
                List<InternToken> currentParameterInternTokenList = new LinkedList();
                int searchIndex = 2;
                int nestedFunctionsCounter = 1;
                while (searchIndex < functionInternTokenList.size()) {
                    InternToken tempSearchToken = (InternToken) functionInternTokenList.get(searchIndex);
                    int i = C18431.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[tempSearchToken.getInternTokenType().ordinal()];
                    if (i != 5) {
                        switch (i) {
                            case 1:
                                nestedFunctionsCounter++;
                                currentParameterInternTokenList.add(tempSearchToken);
                                break;
                            case 2:
                                nestedFunctionsCounter--;
                                if (nestedFunctionsCounter != 0) {
                                    currentParameterInternTokenList.add(tempSearchToken);
                                    break;
                                }
                                break;
                            default:
                                break;
                        }
                    } else if (nestedFunctionsCounter == 1) {
                        functionParameterInternTokenList.add(currentParameterInternTokenList);
                        currentParameterInternTokenList = new LinkedList();
                        searchIndex++;
                        if (tempSearchToken.getInternTokenType() != InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE && nestedFunctionsCounter == 0) {
                            if (currentParameterInternTokenList.size() > 0) {
                                functionParameterInternTokenList.add(currentParameterInternTokenList);
                            }
                            return functionParameterInternTokenList;
                        }
                    }
                    currentParameterInternTokenList.add(tempSearchToken);
                    searchIndex++;
                    if (tempSearchToken.getInternTokenType() != InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE) {
                    }
                }
                return null;
            }
        }
        return null;
    }

    public static boolean isFunction(List<InternToken> internTokenList) {
        List<InternToken> functionList = getFunctionByName(internTokenList, 0);
        if (functionList == null || functionList.size() != internTokenList.size()) {
            return false;
        }
        return true;
    }

    private static InternTokenType getFirstInternTokenType(List<InternToken> internTokens) {
        if (internTokens != null) {
            if (internTokens.size() != 0) {
                return ((InternToken) internTokens.get(0)).getInternTokenType();
            }
        }
        return null;
    }

    public static boolean isPeriodToken(List<InternToken> internTokens) {
        if (internTokens != null) {
            if (internTokens.size() == 1) {
                return ((InternToken) internTokens.get(0)).getInternTokenType() == InternTokenType.PERIOD;
            }
        }
        return false;
    }

    public static boolean isFunctionToken(List<InternToken> internTokens) {
        InternTokenType firstInternTokenType = getFirstInternTokenType(internTokens);
        if (firstInternTokenType == null || firstInternTokenType != InternTokenType.FUNCTION_NAME) {
            return false;
        }
        return true;
    }

    public static boolean isNumberToken(List<InternToken> internTokens) {
        InternTokenType firstInternTokenType = getFirstInternTokenType(internTokens);
        if (firstInternTokenType == null || internTokens.size() > 1 || firstInternTokenType != InternTokenType.NUMBER) {
            return false;
        }
        return true;
    }

    public static List<InternToken> replaceFunctionByTokens(List<InternToken> functionToReplace, List<InternToken> internTokensToReplaceWith) {
        if (isFunctionToken(internTokensToReplaceWith)) {
            return replaceFunctionButKeepParameters(functionToReplace, internTokensToReplaceWith);
        }
        return internTokensToReplaceWith;
    }

    public static List<InternToken> insertOperatorToNumberToken(InternToken numberTokenToBeModified, int externNumberOffset, InternToken operatorToInsert) {
        List<InternToken> replaceTokenList = new LinkedList();
        String numberString = numberTokenToBeModified.getTokenStringValue();
        String leftPart = numberString.substring(null, externNumberOffset);
        String rightPart = numberString.substring(externNumberOffset);
        replaceTokenList.add(new InternToken(InternTokenType.NUMBER, leftPart));
        replaceTokenList.add(operatorToInsert);
        replaceTokenList.add(new InternToken(InternTokenType.NUMBER, rightPart));
        return replaceTokenList;
    }

    public static InternToken insertIntoNumberToken(InternToken numberTokenToBeModified, int externNumberOffset, String numberToInsert) {
        String numberString = numberTokenToBeModified.getTokenStringValue();
        String leftPart = numberString.substring(null, externNumberOffset);
        String rightPart = numberString.substring(externNumberOffset);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(leftPart);
        stringBuilder.append(numberToInsert);
        stringBuilder.append(rightPart);
        numberTokenToBeModified.setTokenStringValue(stringBuilder.toString());
        return numberTokenToBeModified;
    }

    public static List<InternToken> replaceFunctionButKeepParameters(List<InternToken> functionToReplace, List<InternToken> functionToReplaceWith) {
        List<List<InternToken>> keepParameterInternTokenList = getFunctionParameterInternTokensAsLists(functionToReplace);
        List<InternToken> replacedParametersFunction = new LinkedList();
        List<List<InternToken>> originalParameterInternTokenList = getFunctionParameterInternTokensAsLists(functionToReplaceWith);
        if (!(functionToReplace == null || keepParameterInternTokenList == null)) {
            if (originalParameterInternTokenList != null) {
                int index = 0;
                replacedParametersFunction.add(functionToReplaceWith.get(0));
                replacedParametersFunction.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));
                int functionParameterCount = getFunctionParameterCount(functionToReplaceWith);
                while (index < functionParameterCount) {
                    if (index >= keepParameterInternTokenList.size() || ((List) keepParameterInternTokenList.get(index)).size() <= 0) {
                        replacedParametersFunction.addAll((Collection) originalParameterInternTokenList.get(index));
                    } else {
                        replacedParametersFunction.addAll((Collection) keepParameterInternTokenList.get(index));
                    }
                    if (index < functionParameterCount - 1) {
                        replacedParametersFunction.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
                    }
                    index++;
                }
                replacedParametersFunction.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));
                return replacedParametersFunction;
            }
        }
        return functionToReplaceWith;
    }

    static int getFunctionParameterCount(List<InternToken> functionInternTokenList) {
        if (functionInternTokenList != null && functionInternTokenList.size() >= 4 && ((InternToken) functionInternTokenList.get(0)).getInternTokenType() == InternTokenType.FUNCTION_NAME) {
            if (((InternToken) functionInternTokenList.get(1)).getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN) {
                int nestedFunctionsCounter = 1;
                int searchIndex = 2;
                int functionParameterCount = 1;
                while (searchIndex < functionInternTokenList.size()) {
                    InternToken tempSearchToken = (InternToken) functionInternTokenList.get(searchIndex);
                    int i = C18431.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[tempSearchToken.getInternTokenType().ordinal()];
                    if (i != 5) {
                        switch (i) {
                            case 1:
                                nestedFunctionsCounter++;
                                break;
                            case 2:
                                nestedFunctionsCounter--;
                                break;
                            default:
                                break;
                        }
                    } else if (nestedFunctionsCounter == 1) {
                        functionParameterCount++;
                    }
                    searchIndex++;
                    if (tempSearchToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE && nestedFunctionsCounter == 0) {
                        return functionParameterCount;
                    }
                }
                return 0;
            }
        }
        return 0;
    }

    public static InternToken deleteNumberByOffset(InternToken cursorPositionInternToken, int externNumberOffset) {
        String numberString = cursorPositionInternToken.getTokenStringValue();
        if (externNumberOffset < 1) {
            return cursorPositionInternToken;
        }
        if (externNumberOffset > numberString.length()) {
            externNumberOffset = numberString.length();
        }
        String leftPart = numberString.substring(null, externNumberOffset - 1);
        String rightPart = numberString.substring(externNumberOffset);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(leftPart);
        stringBuilder.append(rightPart);
        cursorPositionInternToken.setTokenStringValue(stringBuilder.toString());
        if (cursorPositionInternToken.getTokenStringValue().isEmpty()) {
            return null;
        }
        return cursorPositionInternToken;
    }

    public static boolean applyBracketCorrection(List<InternToken> internFormula) throws EmptyStackException {
        Stack<InternTokenType> stack = new Stack();
        for (int index = 0; index < internFormula.size(); index++) {
            switch (((InternToken) internFormula.get(index)).getInternTokenType()) {
                case FUNCTION_PARAMETERS_BRACKET_OPEN:
                    stack.push(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN);
                    break;
                case FUNCTION_PARAMETERS_BRACKET_CLOSE:
                    if (stack.peek() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN) {
                        stack.pop();
                        break;
                    } else if (swapBrackets(internFormula, index, InternTokenType.BRACKET_CLOSE)) {
                        stack.pop();
                        break;
                    } else {
                        return false;
                    }
                case BRACKET_OPEN:
                    stack.push(InternTokenType.BRACKET_OPEN);
                    break;
                case BRACKET_CLOSE:
                    if (stack.peek() == InternTokenType.BRACKET_OPEN) {
                        stack.pop();
                        break;
                    } else if (swapBrackets(internFormula, index, InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE)) {
                        stack.pop();
                        break;
                    } else {
                        return false;
                    }
                default:
                    break;
            }
        }
        return true;
    }

    private static boolean swapBrackets(List<InternToken> internFormula, int firstBracketIndex, InternTokenType secondBracket) {
        for (int index = firstBracketIndex + 1; index < internFormula.size(); index++) {
            if (((InternToken) internFormula.get(index)).getInternTokenType() == secondBracket) {
                InternToken firstBracket = (InternToken) internFormula.get(firstBracketIndex);
                internFormula.set(firstBracketIndex, internFormula.get(index));
                internFormula.set(index, firstBracket);
                return true;
            }
        }
        return false;
    }
}
