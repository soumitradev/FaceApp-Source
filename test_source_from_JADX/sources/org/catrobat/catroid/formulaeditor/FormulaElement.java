package org.catrobat.catroid.formulaeditor;

import android.content.res.Resources.NotFoundException;
import android.util.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.GroupSprite;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.devices.arduino.Arduino;
import org.catrobat.catroid.devices.raspberrypi.RPiSocketConnection;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.nfc.NfcHandler;
import org.catrobat.catroid.sensing.CollisionDetection;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.TouchUtil;

public class FormulaElement implements Serializable {
    private static final String EMPTY_USER_LIST_INTERPRETATION_VALUE = "";
    public static final Double NOT_EXISTING_USER_LIST_INTERPRETATION_VALUE = Double.valueOf(BrickValues.SET_COLOR_TO);
    public static final Double NOT_EXISTING_USER_VARIABLE_INTERPRETATION_VALUE = Double.valueOf(BrickValues.SET_COLOR_TO);
    private static final long serialVersionUID = 1;
    private FormulaElement leftChild = null;
    private transient FormulaElement parent = null;
    private FormulaElement rightChild = null;
    private ElementType type;
    private String value;

    public enum ElementType {
        OPERATOR,
        FUNCTION,
        NUMBER,
        SENSOR,
        USER_VARIABLE,
        USER_LIST,
        BRACKET,
        STRING,
        COLLISION_FORMULA
    }

    public FormulaElement(ElementType type, String value, FormulaElement parent) {
        this.type = type;
        this.value = value;
        this.parent = parent;
    }

    public FormulaElement(ElementType type, String value, FormulaElement parent, FormulaElement leftChild, FormulaElement rightChild) {
        this.type = type;
        this.value = value;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        if (leftChild != null) {
            this.leftChild.parent = this;
        }
        if (rightChild != null) {
            this.rightChild.parent = this;
        }
    }

    public ElementType getElementType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public List<InternToken> getInternTokenList() {
        List<InternToken> internTokenList = new LinkedList();
        switch (this.type) {
            case BRACKET:
                internTokenList.add(new InternToken(InternTokenType.BRACKET_OPEN));
                if (this.rightChild != null) {
                    internTokenList.addAll(this.rightChild.getInternTokenList());
                }
                internTokenList.add(new InternToken(InternTokenType.BRACKET_CLOSE));
                break;
            case OPERATOR:
                if (this.leftChild != null) {
                    internTokenList.addAll(this.leftChild.getInternTokenList());
                }
                internTokenList.add(new InternToken(InternTokenType.OPERATOR, this.value));
                if (this.rightChild != null) {
                    internTokenList.addAll(this.rightChild.getInternTokenList());
                    break;
                }
                break;
            case FUNCTION:
                internTokenList.add(new InternToken(InternTokenType.FUNCTION_NAME, this.value));
                boolean functionHasParameters = false;
                if (this.leftChild != null) {
                    internTokenList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));
                    functionHasParameters = true;
                    internTokenList.addAll(this.leftChild.getInternTokenList());
                }
                if (this.rightChild != null) {
                    internTokenList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
                    internTokenList.addAll(this.rightChild.getInternTokenList());
                }
                if (functionHasParameters) {
                    internTokenList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));
                    break;
                }
                break;
            case USER_VARIABLE:
                internTokenList.add(new InternToken(InternTokenType.USER_VARIABLE, this.value));
                break;
            case USER_LIST:
                internTokenList.add(new InternToken(InternTokenType.USER_LIST, this.value));
                break;
            case NUMBER:
                internTokenList.add(new InternToken(InternTokenType.NUMBER, this.value));
                break;
            case SENSOR:
                internTokenList.add(new InternToken(InternTokenType.SENSOR, this.value));
                break;
            case STRING:
                internTokenList.add(new InternToken(InternTokenType.STRING, this.value));
                break;
            case COLLISION_FORMULA:
                internTokenList.add(new InternToken(InternTokenType.COLLISION_FORMULA, this.value));
                break;
            default:
                break;
        }
        return internTokenList;
    }

    public FormulaElement getRoot() {
        FormulaElement root = this;
        while (root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }

    public void updateVariableReferences(String oldName, String newName) {
        if (this.leftChild != null) {
            this.leftChild.updateVariableReferences(oldName, newName);
        }
        if (this.rightChild != null) {
            this.rightChild.updateVariableReferences(oldName, newName);
        }
        if (this.type == ElementType.USER_VARIABLE && this.value.equals(oldName)) {
            this.value = newName;
        }
    }

    public void getVariableAndListNames(List<String> variables, List<String> lists) {
        if (this.leftChild != null) {
            this.leftChild.getVariableAndListNames(variables, lists);
        }
        if (this.rightChild != null) {
            this.rightChild.getVariableAndListNames(variables, lists);
        }
        if (this.type == ElementType.USER_VARIABLE && !variables.contains(this.value)) {
            variables.add(this.value);
        }
        if (this.type == ElementType.USER_LIST && !lists.contains(this.value)) {
            lists.add(this.value);
        }
    }

    public boolean containsSpriteInCollision(String name) {
        boolean contained = false;
        if (this.leftChild != null) {
            contained = false | this.leftChild.containsSpriteInCollision(name);
        }
        if (this.rightChild != null) {
            contained |= this.rightChild.containsSpriteInCollision(name);
        }
        if (this.type == ElementType.COLLISION_FORMULA && this.value.equals(name)) {
            return true;
        }
        return contained;
    }

    public void updateCollisionFormula(String oldName, String newName) {
        if (this.leftChild != null) {
            this.leftChild.updateCollisionFormula(oldName, newName);
        }
        if (this.rightChild != null) {
            this.rightChild.updateCollisionFormula(oldName, newName);
        }
        if (this.type == ElementType.COLLISION_FORMULA && this.value.equals(oldName)) {
            this.value = newName;
        }
    }

    public void updateCollisionFormulaToVersion(float catroidLanguageVersion) {
        if (catroidLanguageVersion == 0.993f) {
            if (this.leftChild != null) {
                this.leftChild.updateCollisionFormulaToVersion(catroidLanguageVersion);
            }
            if (this.rightChild != null) {
                this.rightChild.updateCollisionFormulaToVersion(catroidLanguageVersion);
            }
            if (this.type == ElementType.COLLISION_FORMULA) {
                String secondSpriteName = CollisionDetection.getSecondSpriteNameFromCollisionFormulaString(this.value);
                if (secondSpriteName != null) {
                    this.value = secondSpriteName;
                }
            }
        }
    }

    public Object interpretRecursive(Sprite sprite) {
        Object returnValue = Double.valueOf(BrickValues.SET_COLOR_TO);
        switch (this.type) {
            case BRACKET:
                returnValue = this.rightChild.interpretRecursive(sprite);
                break;
            case OPERATOR:
                returnValue = interpretOperator(Operators.getOperatorByValue(this.value), sprite);
                break;
            case FUNCTION:
                returnValue = interpretFunction(Functions.getFunctionByValue(this.value), sprite);
                break;
            case USER_VARIABLE:
                returnValue = interpretUserVariable(sprite);
                break;
            case USER_LIST:
                returnValue = interpretUserList(sprite);
                break;
            case NUMBER:
                returnValue = this.value;
                break;
            case SENSOR:
                returnValue = interpretSensor(sprite);
                break;
            case STRING:
                returnValue = this.value;
                break;
            case COLLISION_FORMULA:
                try {
                    returnValue = interpretCollision(sprite, this.value);
                    break;
                } catch (Exception exception) {
                    returnValue = Double.valueOf(BrickValues.SET_COLOR_TO);
                    Log.e(getClass().getSimpleName(), Log.getStackTraceString(exception));
                    break;
                }
            default:
                break;
        }
        return normalizeDegeneratedDoubleValues(returnValue);
    }

    private Object interpretCollision(Sprite firstSprite, String formula) {
        String secondSpriteName = formula;
        try {
            Sprite secondSprite = ProjectManager.getInstance().getCurrentlyPlayingScene().getSprite(secondSpriteName);
            Look firstLook = firstSprite.look;
            if (!(secondSprite instanceof GroupSprite)) {
                return Double.valueOf(CollisionDetection.checkCollisionBetweenLooks(firstLook, secondSprite.look));
            }
            for (Sprite sprite : GroupSprite.getSpritesFromGroupWithGroupName(secondSpriteName)) {
                if (CollisionDetection.checkCollisionBetweenLooks(firstLook, sprite.look) == 1.0d) {
                    return Double.valueOf(1.0d);
                }
            }
            return Double.valueOf(BrickValues.SET_COLOR_TO);
        } catch (NotFoundException e) {
            Sprite sprite2 = firstSprite;
            NotFoundException exception = e;
            return Double.valueOf(BrickValues.SET_COLOR_TO);
        }
    }

    private Object interpretUserList(Sprite sprite) {
        UserList userList = ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer().getUserList(sprite, this.value);
        if (userList == null) {
            return NOT_EXISTING_USER_LIST_INTERPRETATION_VALUE;
        }
        List<Object> userListValues = userList.getList();
        if (userListValues.size() == 0) {
            return "";
        }
        if (userListValues.size() != 1) {
            return interpretMultipleItemsUserList(userListValues);
        }
        Object userListValue = userListValues.get(null);
        if (userListValue instanceof String) {
            return userListValue;
        }
        return userListValue;
    }

    private Object interpretMultipleItemsUserList(List<Object> userListValues) {
        List<String> userListStringValues = new ArrayList();
        boolean concatenateWithoutWhitespace = true;
        for (Double listValue : userListValues) {
            if (listValue instanceof Double) {
                Double doubleValueOfListItem = listValue;
                if (isNumberAIntegerBetweenZeroAndNine(doubleValueOfListItem)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(doubleValueOfListItem.intValue());
                    stringBuilder.append("");
                    userListStringValues.add(stringBuilder.toString());
                } else {
                    concatenateWithoutWhitespace = false;
                    userListStringValues.add(listValue.toString());
                }
            } else if (listValue instanceof String) {
                String stringValueOfListItem = (String) listValue;
                if (stringValueOfListItem.length() == 1) {
                    userListStringValues.add(stringValueOfListItem);
                } else {
                    userListStringValues.add(stringValueOfListItem);
                    concatenateWithoutWhitespace = false;
                }
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder(userListStringValues.size());
        boolean isFirstListItem = true;
        for (String userListStringValue : userListStringValues) {
            if (!(concatenateWithoutWhitespace || isFirstListItem)) {
                stringBuilder2.append(' ');
            }
            if (isFirstListItem) {
                isFirstListItem = false;
            }
            stringBuilder2.append(userListStringValue);
        }
        return stringBuilder2.toString();
    }

    public boolean isNumberAIntegerBetweenZeroAndNine(Double valueToCheck) {
        return !valueToCheck.isNaN() && !valueToCheck.isInfinite() && Math.floor(valueToCheck.doubleValue()) == valueToCheck.doubleValue() && valueToCheck.doubleValue() <= 9.0d && valueToCheck.doubleValue() >= BrickValues.SET_COLOR_TO;
    }

    private Object interpretUserVariable(Sprite sprite) {
        UserVariable userVariable = ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer().getUserVariable(sprite, this.value);
        if (userVariable == null) {
            return NOT_EXISTING_USER_VARIABLE_INTERPRETATION_VALUE;
        }
        Object userVariableValue = userVariable.getValue();
        if (userVariableValue instanceof String) {
            return userVariableValue;
        }
        return userVariableValue;
    }

    private Object interpretSensor(Sprite sprite) {
        Sensors sensor = Sensors.getSensorByValue(this.value);
        if (sensor.isObjectSensor) {
            return interpretObjectSensor(sensor, sprite);
        }
        return SensorHandler.getSensorValue(sensor);
    }

    private Object interpretFunction(Functions function, Sprite sprite) {
        Sprite sprite2 = sprite;
        Object left = null;
        Object right = null;
        Double doubleValueOfLeftChild = null;
        Double doubleValueOfRightChild = null;
        if (this.leftChild != null) {
            left = r1.leftChild.interpretRecursive(sprite2);
            if (left instanceof String) {
                try {
                    doubleValueOfLeftChild = Double.valueOf((String) left);
                } catch (NumberFormatException e) {
                    Log.d(getClass().getSimpleName(), "Couldn't parse String", e);
                }
            } else {
                doubleValueOfLeftChild = (Double) left;
            }
        }
        if (r1.rightChild != null) {
            right = r1.rightChild.interpretRecursive(sprite2);
            if (right instanceof String) {
                try {
                    doubleValueOfRightChild = Double.valueOf((String) right);
                } catch (NumberFormatException e2) {
                    Log.d(getClass().getSimpleName(), "Couldn't parse String", e2);
                }
            } else {
                doubleValueOfRightChild = (Double) right;
            }
        }
        double d = 1.0d;
        Object interpretFunctionRand;
        double pow;
        Arduino arduinoDigital;
        switch (function) {
            case SIN:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.sin(Math.toRadians(doubleValueOfLeftChild.doubleValue())));
            case COS:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.cos(Math.toRadians(doubleValueOfLeftChild.doubleValue())));
            case TAN:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.tan(Math.toRadians(doubleValueOfLeftChild.doubleValue())));
            case LN:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.log(doubleValueOfLeftChild.doubleValue()));
            case LOG:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.log10(doubleValueOfLeftChild.doubleValue()));
            case SQRT:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.sqrt(doubleValueOfLeftChild.doubleValue()));
            case RAND:
                if (doubleValueOfLeftChild != null) {
                    if (doubleValueOfRightChild != null) {
                        interpretFunctionRand = interpretFunctionRand(doubleValueOfLeftChild, doubleValueOfRightChild);
                        return interpretFunctionRand;
                    }
                }
                interpretFunctionRand = Double.valueOf(BrickValues.SET_COLOR_TO);
                return interpretFunctionRand;
            case ABS:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.abs(doubleValueOfLeftChild.doubleValue()));
            case ROUND:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : (double) Math.round(doubleValueOfLeftChild.doubleValue()));
            case PI:
                return Double.valueOf(3.141592653589793d);
            case MOD:
                if (doubleValueOfLeftChild != null) {
                    if (doubleValueOfRightChild != null) {
                        interpretFunctionRand = interpretFunctionMod(doubleValueOfLeftChild, doubleValueOfRightChild);
                        return interpretFunctionRand;
                    }
                }
                interpretFunctionRand = Double.valueOf(BrickValues.SET_COLOR_TO);
                return interpretFunctionRand;
            case ARCSIN:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.toDegrees(Math.asin(doubleValueOfLeftChild.doubleValue())));
            case ARCCOS:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.toDegrees(Math.acos(doubleValueOfLeftChild.doubleValue())));
            case ARCTAN:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.toDegrees(Math.atan(doubleValueOfLeftChild.doubleValue())));
            case EXP:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.exp(doubleValueOfLeftChild.doubleValue()));
            case POWER:
                if (doubleValueOfLeftChild != null) {
                    if (doubleValueOfRightChild != null) {
                        pow = Math.pow(doubleValueOfLeftChild.doubleValue(), doubleValueOfRightChild.doubleValue());
                        return Double.valueOf(pow);
                    }
                }
                pow = BrickValues.SET_COLOR_TO;
                return Double.valueOf(pow);
            case FLOOR:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.floor(doubleValueOfLeftChild.doubleValue()));
            case CEIL:
                return Double.valueOf(doubleValueOfLeftChild == null ? BrickValues.SET_COLOR_TO : Math.ceil(doubleValueOfLeftChild.doubleValue()));
            case MAX:
                if (doubleValueOfLeftChild != null) {
                    if (doubleValueOfRightChild != null) {
                        pow = Math.max(doubleValueOfLeftChild.doubleValue(), doubleValueOfRightChild.doubleValue());
                        return Double.valueOf(pow);
                    }
                }
                pow = BrickValues.SET_COLOR_TO;
                return Double.valueOf(pow);
            case MIN:
                if (doubleValueOfLeftChild != null) {
                    if (doubleValueOfRightChild != null) {
                        pow = Math.min(doubleValueOfLeftChild.doubleValue(), doubleValueOfRightChild.doubleValue());
                        return Double.valueOf(pow);
                    }
                }
                pow = BrickValues.SET_COLOR_TO;
                return Double.valueOf(pow);
            case TRUE:
                return Double.valueOf(1.0d);
            case FALSE:
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            case LETTER:
                return interpretFunctionLetter(right, left);
            case LENGTH:
                return interpretFunctionLength(left, sprite2);
            case JOIN:
                return interpretFunctionJoin(sprite2);
            case ARDUINODIGITAL:
                arduinoDigital = (Arduino) ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).getDevice(BluetoothDevice.ARDUINO);
                if (!(arduinoDigital == null || doubleValueOfLeftChild == null)) {
                    if (doubleValueOfLeftChild.doubleValue() >= BrickValues.SET_COLOR_TO) {
                        if (doubleValueOfLeftChild.doubleValue() <= 13.0d) {
                            return Double.valueOf(arduinoDigital.getDigitalArduinoPin(doubleValueOfLeftChild.intValue()));
                        }
                    }
                    return Double.valueOf(BrickValues.SET_COLOR_TO);
                }
            case ARDUINOANALOG:
                arduinoDigital = (Arduino) ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).getDevice(BluetoothDevice.ARDUINO);
                if (!(arduinoDigital == null || doubleValueOfLeftChild == null)) {
                    if (doubleValueOfLeftChild.doubleValue() >= BrickValues.SET_COLOR_TO) {
                        if (doubleValueOfLeftChild.doubleValue() <= 5.0d) {
                            return Double.valueOf(arduinoDigital.getAnalogArduinoPin(doubleValueOfLeftChild.intValue()));
                        }
                    }
                    return Double.valueOf(BrickValues.SET_COLOR_TO);
                }
            case RASPIDIGITAL:
                RPiSocketConnection connection = RaspberryPiService.getInstance().connection;
                if (doubleValueOfLeftChild != null) {
                    try {
                        if (!connection.getPin(doubleValueOfLeftChild.intValue())) {
                            d = BrickValues.SET_COLOR_TO;
                        }
                        return Double.valueOf(d);
                    } catch (Exception e3) {
                        Exception e4 = e3;
                        String simpleName = getClass().getSimpleName();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("RPi: exception during getPin: ");
                        stringBuilder.append(e4);
                        Log.e(simpleName, stringBuilder.toString());
                        break;
                    }
                }
                break;
            case MULTI_FINGER_TOUCHED:
                pow = (doubleValueOfLeftChild == null || !TouchUtil.isFingerTouching(doubleValueOfLeftChild.intValue())) ? BrickValues.SET_COLOR_TO : 1.0d;
                return Double.valueOf(pow);
            case MULTI_FINGER_X:
                return Double.valueOf(doubleValueOfLeftChild != null ? (double) TouchUtil.getX(doubleValueOfLeftChild.intValue()) : BrickValues.SET_COLOR_TO);
            case MULTI_FINGER_Y:
                return Double.valueOf(doubleValueOfLeftChild != null ? (double) TouchUtil.getY(doubleValueOfLeftChild.intValue()) : BrickValues.SET_COLOR_TO);
            case LIST_ITEM:
                return interpretFunctionListItem(left, sprite2);
            case CONTAINS:
                return interpretFunctionContains(right, sprite2);
            case NUMBER_OF_ITEMS:
                return interpretFunctionNumberOfItems(left, sprite2);
            default:
                break;
        }
        return Double.valueOf(BrickValues.SET_COLOR_TO);
    }

    private Object interpretFunctionNumberOfItems(Object left, Sprite sprite) {
        if (this.leftChild.type == ElementType.USER_LIST) {
            return Double.valueOf((double) handleNumberOfItemsOfUserListParameter(sprite));
        }
        return interpretFunctionLength(left, sprite);
    }

    private Object interpretFunctionContains(Object right, Sprite sprite) {
        if (this.leftChild.getElementType() == ElementType.USER_LIST) {
            UserList userList = ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer().getUserList(sprite, this.leftChild.getValue());
            if (userList == null) {
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            }
            for (Object userListElement : userList.getList()) {
                if (interpretOperatorEqual(userListElement, right).doubleValue() == 1.0d) {
                    return Double.valueOf(1.0d);
                }
            }
        }
        return Double.valueOf(BrickValues.SET_COLOR_TO);
    }

    private Object interpretFunctionListItem(Object left, Sprite sprite) {
        UserList userList = null;
        if (this.rightChild.getElementType() == ElementType.USER_LIST) {
            userList = ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer().getUserList(sprite, this.rightChild.getValue());
        }
        if (userList == null) {
            return "";
        }
        int index = 0;
        if (left instanceof String) {
            try {
                index = Double.valueOf((String) left).intValue();
            } catch (NumberFormatException numberFormatexception) {
                Log.d(getClass().getSimpleName(), "Couldn't parse String", numberFormatexception);
            }
        } else if (left == null) {
            return "";
        } else {
            index = ((Double) left).intValue();
        }
        index--;
        if (index < 0) {
            return "";
        }
        if (index >= userList.getList().size()) {
            return "";
        }
        return userList.getList().get(index);
    }

    private Object interpretFunctionJoin(Sprite sprite) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(interpretInterpretFunctionJoinParameter(this.leftChild, sprite));
        stringBuilder.append(interpretInterpretFunctionJoinParameter(this.rightChild, sprite));
        return stringBuilder.toString();
    }

    private String interpretInterpretFunctionJoinParameter(FormulaElement child, Sprite sprite) {
        String parameterInterpretation = "";
        if (child == null) {
            return parameterInterpretation;
        }
        if (child.getElementType() == ElementType.NUMBER) {
            Double number = Double.valueOf((String) child.interpretRecursive(sprite));
            if (number.isNaN()) {
                parameterInterpretation = "";
            } else if (isInteger(number.doubleValue())) {
                r2 = new StringBuilder();
                r2.append(parameterInterpretation);
                r2.append(number.intValue());
                parameterInterpretation = r2.toString();
            } else {
                r2 = new StringBuilder();
                r2.append(parameterInterpretation);
                r2.append(number);
                parameterInterpretation = r2.toString();
            }
            return parameterInterpretation;
        } else if (child.getElementType() == ElementType.STRING) {
            return child.value;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(parameterInterpretation);
            stringBuilder.append(child.interpretRecursive(sprite));
            return stringBuilder.toString();
        }
    }

    private Object interpretFunctionLength(Object left, Sprite sprite) {
        if (this.leftChild == null) {
            return Double.valueOf(BrickValues.SET_COLOR_TO);
        }
        if (this.leftChild.type == ElementType.NUMBER) {
            return Double.valueOf((double) this.leftChild.value.length());
        }
        if (this.leftChild.type == ElementType.STRING) {
            return Double.valueOf((double) this.leftChild.value.length());
        }
        if (this.leftChild.type == ElementType.USER_VARIABLE) {
            return Double.valueOf((double) handleLengthUserVariableParameter(sprite));
        }
        if (this.leftChild.type == ElementType.USER_LIST) {
            UserList userList = ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer().getUserList(sprite, this.leftChild.getValue());
            if (userList == null) {
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            }
            if (userList.getList().size() == 0) {
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            }
            Double interpretedList = this.leftChild.interpretRecursive(sprite);
            if (interpretedList instanceof Double) {
                Double interpretedListDoubleValue = interpretedList;
                if (!interpretedListDoubleValue.isNaN()) {
                    if (!interpretedListDoubleValue.isInfinite()) {
                        return Double.valueOf((double) String.valueOf(interpretedListDoubleValue.intValue()).length());
                    }
                }
                return Double.valueOf(BrickValues.SET_COLOR_TO);
            } else if (interpretedList instanceof String) {
                return Double.valueOf((double) ((String) interpretedList).length());
            }
        }
        if ((left instanceof Double) && ((Double) left).isNaN()) {
            return Double.valueOf(BrickValues.SET_COLOR_TO);
        }
        return Double.valueOf((double) String.valueOf(left).length());
    }

    private Object interpretFunctionLetter(Object right, Object left) {
        int index = 0;
        if (left instanceof String) {
            try {
                index = Double.valueOf((String) left).intValue();
            } catch (NumberFormatException numberFormatexception) {
                Log.d(getClass().getSimpleName(), "Couldn't parse String", numberFormatexception);
            }
        } else if (left == null) {
            return "";
        } else {
            index = ((Double) left).intValue();
        }
        index--;
        if (index < 0) {
            return "";
        }
        if (right != null) {
            if (index < String.valueOf(right).length()) {
                return String.valueOf(String.valueOf(right).charAt(index));
            }
        }
        return "";
    }

    private Object interpretFunctionMod(Object left, Object right) {
        double dividend = ((Double) left).doubleValue();
        double divisor = ((Double) right).doubleValue();
        if (dividend != BrickValues.SET_COLOR_TO) {
            if (divisor != BrickValues.SET_COLOR_TO) {
                if (divisor > BrickValues.SET_COLOR_TO) {
                    while (dividend < BrickValues.SET_COLOR_TO) {
                        dividend += Math.abs(divisor);
                    }
                } else if (dividend > BrickValues.SET_COLOR_TO) {
                    return Double.valueOf((dividend % divisor) + divisor);
                }
                return Double.valueOf(dividend % divisor);
            }
        }
        return Double.valueOf(dividend);
    }

    private Object interpretFunctionRand(Object left, Object right) {
        double from = ((Double) left).doubleValue();
        double to = ((Double) right).doubleValue();
        double low = from <= to ? from : to;
        double high = from <= to ? to : from;
        if (low == high) {
            return Double.valueOf(low);
        }
        if (!isInteger(low) || !isInteger(high) || isNumberWithDecimalPoint(this.leftChild) || isNumberWithDecimalPoint(this.rightChild)) {
            return Double.valueOf((Math.random() * (high - low)) + low);
        }
        return Double.valueOf(Math.floor(Math.random() * ((1.0d + high) - low)) + low);
    }

    private static boolean isNumberWithDecimalPoint(FormulaElement element) {
        return element.type == ElementType.NUMBER && element.value.contains(".");
    }

    private Object interpretOperator(Operators operator, Sprite sprite) {
        double d = 1.0d;
        Object leftObject;
        if (this.leftChild != null) {
            Object rightObject;
            try {
                leftObject = this.leftChild.interpretRecursive(sprite);
            } catch (NumberFormatException e) {
                leftObject = Double.valueOf(Double.NaN);
            }
            try {
                rightObject = this.rightChild.interpretRecursive(sprite);
            } catch (NumberFormatException e2) {
                rightObject = Double.valueOf(Double.NaN);
            }
            switch (operator) {
                case PLUS:
                    return Double.valueOf(interpretOperator(leftObject).doubleValue() + interpretOperator(rightObject).doubleValue());
                case MINUS:
                    return Double.valueOf(interpretOperator(leftObject).doubleValue() - interpretOperator(rightObject).doubleValue());
                case MULT:
                    return Double.valueOf(interpretOperator(leftObject).doubleValue() * interpretOperator(rightObject).doubleValue());
                case DIVIDE:
                    return Double.valueOf(interpretOperator(leftObject).doubleValue() / interpretOperator(rightObject).doubleValue());
                case POW:
                    return Double.valueOf(Math.pow(interpretOperator(leftObject).doubleValue(), interpretOperator(rightObject).doubleValue()));
                case EQUAL:
                    return interpretOperatorEqual(leftObject, rightObject);
                case NOT_EQUAL:
                    if (interpretOperatorEqual(leftObject, rightObject).doubleValue() == 1.0d) {
                        d = BrickValues.SET_COLOR_TO;
                    }
                    return Double.valueOf(d);
                case GREATER_THAN:
                    if (interpretOperator(leftObject).compareTo(interpretOperator(rightObject)) <= 0) {
                        d = BrickValues.SET_COLOR_TO;
                    }
                    return Double.valueOf(d);
                case GREATER_OR_EQUAL:
                    if (interpretOperator(leftObject).compareTo(interpretOperator(rightObject)) < 0) {
                        d = BrickValues.SET_COLOR_TO;
                    }
                    return Double.valueOf(d);
                case SMALLER_THAN:
                    if (interpretOperator(leftObject).compareTo(interpretOperator(rightObject)) >= 0) {
                        d = BrickValues.SET_COLOR_TO;
                    }
                    return Double.valueOf(d);
                case SMALLER_OR_EQUAL:
                    if (interpretOperator(leftObject).compareTo(interpretOperator(rightObject)) > 0) {
                        d = BrickValues.SET_COLOR_TO;
                    }
                    return Double.valueOf(d);
                case LOGICAL_AND:
                    if (interpretOperator(leftObject).doubleValue() * interpretOperator(rightObject).doubleValue() == BrickValues.SET_COLOR_TO) {
                        d = BrickValues.SET_COLOR_TO;
                    }
                    return Double.valueOf(d);
                case LOGICAL_OR:
                    Double left = interpretOperator(leftObject);
                    Double right = interpretOperator(rightObject);
                    if (left.doubleValue() == BrickValues.SET_COLOR_TO) {
                        if (right.doubleValue() == BrickValues.SET_COLOR_TO) {
                            d = BrickValues.SET_COLOR_TO;
                        }
                    }
                    return Double.valueOf(d);
                default:
                    break;
            }
        }
        try {
            leftObject = this.rightChild.interpretRecursive(sprite);
        } catch (NumberFormatException e3) {
            leftObject = Double.valueOf(Double.NaN);
        }
        int i = C18401.$SwitchMap$org$catrobat$catroid$formulaeditor$Operators[operator.ordinal()];
        if (i == 2) {
            return Double.valueOf(-interpretOperator(leftObject).doubleValue());
        }
        if (i == 14) {
            if (interpretOperator(leftObject).doubleValue() != BrickValues.SET_COLOR_TO) {
                d = BrickValues.SET_COLOR_TO;
            }
            return Double.valueOf(d);
        }
        return Double.valueOf(BrickValues.SET_COLOR_TO);
    }

    private Object interpretObjectSensor(Sensors sensor, Sprite sprite) {
        double d = BrickValues.SET_COLOR_TO;
        Object returnValue = Double.valueOf(BrickValues.SET_COLOR_TO);
        LookData lookData = sprite.look.getLookData();
        List<LookData> lookDataList = sprite.getLookList();
        int i = 0;
        if (lookData == null && lookDataList.size() > 0) {
            lookData = (LookData) lookDataList.get(0);
        }
        switch (sensor) {
            case OBJECT_BRIGHTNESS:
                return Double.valueOf((double) sprite.look.getBrightnessInUserInterfaceDimensionUnit());
            case OBJECT_COLOR:
                return Double.valueOf((double) sprite.look.getColorInUserInterfaceDimensionUnit());
            case OBJECT_TRANSPARENCY:
                return Double.valueOf((double) sprite.look.getTransparencyInUserInterfaceDimensionUnit());
            case OBJECT_LAYER:
                return Double.valueOf(((double) sprite.look.getZIndex()) - 1.0d);
            case OBJECT_ROTATION:
                return Double.valueOf((double) sprite.look.getDirectionInUserInterfaceDimensionUnit());
            case OBJECT_SIZE:
                return Double.valueOf((double) sprite.look.getSizeInUserInterfaceDimensionUnit());
            case OBJECT_X:
                return Double.valueOf((double) sprite.look.getXInUserInterfaceDimensionUnit());
            case OBJECT_Y:
                return Double.valueOf((double) sprite.look.getYInUserInterfaceDimensionUnit());
            case OBJECT_ANGULAR_VELOCITY:
                return Double.valueOf((double) sprite.look.getAngularVelocityInUserInterfaceDimensionUnit());
            case OBJECT_X_VELOCITY:
                return Double.valueOf((double) sprite.look.getXVelocityInUserInterfaceDimensionUnit());
            case OBJECT_Y_VELOCITY:
                return Double.valueOf((double) sprite.look.getYVelocityInUserInterfaceDimensionUnit());
            case OBJECT_LOOK_NUMBER:
            case OBJECT_BACKGROUND_NUMBER:
                if (lookData != null) {
                    i = lookDataList.indexOf(lookData);
                }
                return Double.valueOf(((double) i) + 1.0d);
            case OBJECT_LOOK_NAME:
            case OBJECT_BACKGROUND_NAME:
                return lookData != null ? lookData.getName() : "";
            case OBJECT_DISTANCE_TO:
                return Double.valueOf((double) sprite.look.getDistanceToTouchPositionInUserInterfaceDimensions());
            case NFC_TAG_MESSAGE:
                return NfcHandler.getLastNfcTagMessage();
            case NFC_TAG_ID:
                return NfcHandler.getLastNfcTagId();
            case COLLIDES_WITH_EDGE:
                if (StageActivity.stageListener.firstFrameDrawn) {
                    d = CollisionDetection.collidesWithEdge(sprite.look);
                }
                return Double.valueOf(d);
            case COLLIDES_WITH_FINGER:
                return Double.valueOf(CollisionDetection.collidesWithFinger(sprite.look));
            default:
                return returnValue;
        }
    }

    private Double interpretOperatorEqual(Object left, Object right) {
        try {
            if (getCompareResult(Double.valueOf(String.valueOf(left)), Double.valueOf(String.valueOf(right))) == 0) {
                return Double.valueOf(1.0d);
            }
            return Double.valueOf(BrickValues.SET_COLOR_TO);
        } catch (NumberFormatException e) {
            if (String.valueOf(left).compareTo(String.valueOf(right)) == 0) {
                return Double.valueOf(1.0d);
            }
            return Double.valueOf(BrickValues.SET_COLOR_TO);
        }
    }

    private int getCompareResult(Double left, Double right) {
        int compareResult;
        if (left.doubleValue() != BrickValues.SET_COLOR_TO) {
            if (right.doubleValue() != BrickValues.SET_COLOR_TO) {
                compareResult = left.compareTo(right);
                return compareResult;
            }
        }
        compareResult = Double.valueOf(Math.abs(left.doubleValue())).compareTo(Double.valueOf(Math.abs(right.doubleValue())));
        return compareResult;
    }

    private Double interpretOperator(Object object) {
        if (!(object instanceof String)) {
            return (Double) object;
        }
        try {
            return Double.valueOf((String) object);
        } catch (NumberFormatException e) {
            return Double.valueOf(Double.NaN);
        }
    }

    private Object normalizeDegeneratedDoubleValues(Object valueToCheck) {
        if (!(valueToCheck instanceof String)) {
            if (!(valueToCheck instanceof Character)) {
                if (valueToCheck == null) {
                    return Double.valueOf(BrickValues.SET_COLOR_TO);
                }
                if (((Double) valueToCheck).doubleValue() == Double.NEGATIVE_INFINITY) {
                    return Double.valueOf(-1.7976931348623157E308d);
                }
                if (((Double) valueToCheck).doubleValue() == Double.POSITIVE_INFINITY) {
                    return Double.valueOf(Double.MAX_VALUE);
                }
                return valueToCheck;
            }
        }
        return valueToCheck;
    }

    public FormulaElement getParent() {
        return this.parent;
    }

    public void setRightChild(FormulaElement rightChild) {
        this.rightChild = rightChild;
        this.rightChild.parent = this;
    }

    public void setLeftChild(FormulaElement leftChild) {
        this.leftChild = leftChild;
        this.leftChild.parent = this;
    }

    public void replaceElement(FormulaElement current) {
        this.parent = current.parent;
        this.leftChild = current.leftChild;
        this.rightChild = current.rightChild;
        this.value = current.value;
        this.type = current.type;
        if (this.leftChild != null) {
            this.leftChild.parent = this;
        }
        if (this.rightChild != null) {
            this.rightChild.parent = this;
        }
    }

    public void replaceElement(ElementType type, String value) {
        this.value = value;
        this.type = type;
    }

    public void replaceWithSubElement(String operator, FormulaElement rightChild) {
        FormulaElement cloneThis = new FormulaElement(ElementType.OPERATOR, operator, getParent(), this, rightChild);
        cloneThis.parent.rightChild = cloneThis;
    }

    private boolean isInteger(double value) {
        return Math.abs(value) - ((double) ((int) Math.abs(value))) < Double.MIN_VALUE;
    }

    public boolean isLogicalOperator() {
        return this.type == ElementType.OPERATOR && Operators.getOperatorByValue(this.value).isLogicalOperator;
    }

    public boolean containsElement(ElementType elementType) {
        if (!this.type.equals(elementType) && (this.leftChild == null || !this.leftChild.containsElement(elementType))) {
            if (this.rightChild == null || !this.rightChild.containsElement(elementType)) {
                return false;
            }
        }
        return true;
    }

    public boolean isUserVariableWithTypeString(Sprite sprite) {
        if (this.type == ElementType.USER_VARIABLE) {
            return ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer().getUserVariable(sprite, this.value).getValue() instanceof String;
        }
        return false;
    }

    private int handleLengthUserVariableParameter(Sprite sprite) {
        Object userVariableValue = ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer().getUserVariable(sprite, this.leftChild.value).getValue();
        if (userVariableValue instanceof String) {
            return String.valueOf(userVariableValue).length();
        }
        if (isInteger(((Double) userVariableValue).doubleValue())) {
            return Integer.toString(((Double) userVariableValue).intValue()).length();
        }
        return Double.toString(((Double) userVariableValue).doubleValue()).length();
    }

    private int handleNumberOfItemsOfUserListParameter(Sprite sprite) {
        UserList userList = ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer().getUserList(sprite, this.leftChild.value);
        if (userList == null) {
            return 0;
        }
        return userList.getList().size();
    }

    public boolean isSingleNumberFormula() {
        boolean z = false;
        if (this.type != ElementType.OPERATOR) {
            return this.type == ElementType.NUMBER;
        } else {
            if (Operators.getOperatorByValue(this.value) == Operators.MINUS && this.leftChild == null && this.rightChild.isSingleNumberFormula()) {
                z = true;
            }
            return z;
        }
    }

    public FormulaElement clone() {
        FormulaElement formulaElement = null;
        FormulaElement leftChildClone = this.leftChild == null ? null : this.leftChild.clone();
        if (this.rightChild != null) {
            formulaElement = this.rightChild.clone();
        }
        return new FormulaElement(this.type, this.value == null ? "" : this.value, null, leftChildClone, formulaElement);
    }

    public void addRequiredResources(Set<Integer> requiredResourcesSet) {
        if (this.leftChild != null) {
            this.leftChild.addRequiredResources(requiredResourcesSet);
        }
        if (this.rightChild != null) {
            this.rightChild.addRequiredResources(requiredResourcesSet);
        }
        if (this.type == ElementType.FUNCTION) {
            switch (Functions.getFunctionByValue(this.value)) {
                case ARDUINODIGITAL:
                case ARDUINOANALOG:
                    requiredResourcesSet.add(Integer.valueOf(6));
                    break;
                case RASPIDIGITAL:
                    requiredResourcesSet.add(Integer.valueOf(7));
                    break;
                default:
                    break;
            }
        }
        if (this.type == ElementType.SENSOR) {
            switch (Sensors.getSensorByValue(this.value)) {
                case NFC_TAG_MESSAGE:
                case NFC_TAG_ID:
                    requiredResourcesSet.add(Integer.valueOf(16));
                    break;
                case COLLIDES_WITH_EDGE:
                    requiredResourcesSet.add(Integer.valueOf(19));
                    break;
                case COLLIDES_WITH_FINGER:
                    requiredResourcesSet.add(Integer.valueOf(19));
                    break;
                case X_ACCELERATION:
                case Y_ACCELERATION:
                case Z_ACCELERATION:
                    requiredResourcesSet.add(Integer.valueOf(13));
                    break;
                case X_INCLINATION:
                case Y_INCLINATION:
                    requiredResourcesSet.add(Integer.valueOf(14));
                    break;
                case COMPASS_DIRECTION:
                    requiredResourcesSet.add(Integer.valueOf(15));
                    break;
                case LATITUDE:
                case LONGITUDE:
                case LOCATION_ACCURACY:
                case ALTITUDE:
                    requiredResourcesSet.add(Integer.valueOf(18));
                    break;
                case FACE_DETECTED:
                case FACE_SIZE:
                case FACE_X_POSITION:
                case FACE_Y_POSITION:
                    requiredResourcesSet.add(Integer.valueOf(4));
                    break;
                case NXT_SENSOR_1:
                case NXT_SENSOR_2:
                case NXT_SENSOR_3:
                case NXT_SENSOR_4:
                    requiredResourcesSet.add(Integer.valueOf(2));
                    break;
                case EV3_SENSOR_1:
                case EV3_SENSOR_2:
                case EV3_SENSOR_3:
                case EV3_SENSOR_4:
                    requiredResourcesSet.add(Integer.valueOf(20));
                    break;
                case PHIRO_FRONT_LEFT:
                case PHIRO_FRONT_RIGHT:
                case PHIRO_SIDE_LEFT:
                case PHIRO_SIDE_RIGHT:
                case PHIRO_BOTTOM_LEFT:
                case PHIRO_BOTTOM_RIGHT:
                    requiredResourcesSet.add(Integer.valueOf(10));
                    break;
                case DRONE_BATTERY_STATUS:
                case DRONE_CAMERA_READY:
                case DRONE_EMERGENCY_STATE:
                case DRONE_FLYING:
                case DRONE_INITIALIZED:
                case DRONE_NUM_FRAMES:
                case DRONE_RECORD_READY:
                case DRONE_RECORDING:
                case DRONE_USB_ACTIVE:
                case DRONE_USB_REMAINING_TIME:
                    requiredResourcesSet.add(Integer.valueOf(5));
                    break;
                case GAMEPAD_A_PRESSED:
                case GAMEPAD_B_PRESSED:
                case GAMEPAD_DOWN_PRESSED:
                case GAMEPAD_UP_PRESSED:
                case GAMEPAD_LEFT_PRESSED:
                case GAMEPAD_RIGHT_PRESSED:
                    requiredResourcesSet.add(Integer.valueOf(22));
                    break;
                default:
                    break;
            }
        }
        if (this.type == ElementType.COLLISION_FORMULA) {
            requiredResourcesSet.add(Integer.valueOf(19));
        }
    }
}
