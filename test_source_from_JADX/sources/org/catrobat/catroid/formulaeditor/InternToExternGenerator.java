package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import android.util.Log;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.FormatNumberUtil;

public class InternToExternGenerator {
    private static final HashMap<String, Integer> INTERN_EXTERN_LANGUAGE_CONVERTER_MAP = new HashMap();
    private static final String TAG = InternToExternGenerator.class.getSimpleName();
    private Context context;
    private String generatedExternFormulaString = "";
    private ExternInternRepresentationMapping generatedExternInternRepresentationMapping = new ExternInternRepresentationMapping();

    static {
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.DIVIDE.name(), Integer.valueOf(R.string.formula_editor_operator_divide));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.MULT.name(), Integer.valueOf(R.string.formula_editor_operator_mult));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.MINUS.name(), Integer.valueOf(R.string.formula_editor_operator_minus));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.PLUS.name(), Integer.valueOf(R.string.formula_editor_operator_plus));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(".", Integer.valueOf(R.string.formula_editor_decimal_mark));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.SIN.name(), Integer.valueOf(R.string.formula_editor_function_sin));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.COS.name(), Integer.valueOf(R.string.formula_editor_function_cos));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.TAN.name(), Integer.valueOf(R.string.formula_editor_function_tan));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.LN.name(), Integer.valueOf(R.string.formula_editor_function_ln));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.LOG.name(), Integer.valueOf(R.string.formula_editor_function_log));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.PI.name(), Integer.valueOf(R.string.formula_editor_function_pi));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.SQRT.name(), Integer.valueOf(R.string.formula_editor_function_sqrt));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.RAND.name(), Integer.valueOf(R.string.formula_editor_function_rand));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.ABS.name(), Integer.valueOf(R.string.formula_editor_function_abs));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.ROUND.name(), Integer.valueOf(R.string.formula_editor_function_round));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.MOD.name(), Integer.valueOf(R.string.formula_editor_function_mod));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.ARCSIN.name(), Integer.valueOf(R.string.formula_editor_function_arcsin));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.ARCCOS.name(), Integer.valueOf(R.string.formula_editor_function_arccos));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.ARCTAN.name(), Integer.valueOf(R.string.formula_editor_function_arctan));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.EXP.name(), Integer.valueOf(R.string.formula_editor_function_exp));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.POWER.name(), Integer.valueOf(R.string.formula_editor_function_power));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.FLOOR.name(), Integer.valueOf(R.string.formula_editor_function_floor));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.CEIL.name(), Integer.valueOf(R.string.formula_editor_function_ceil));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.MAX.name(), Integer.valueOf(R.string.formula_editor_function_max));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.MIN.name(), Integer.valueOf(R.string.formula_editor_function_min));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.TRUE.name(), Integer.valueOf(R.string.formula_editor_function_true));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.FALSE.name(), Integer.valueOf(R.string.formula_editor_function_false));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.LENGTH.name(), Integer.valueOf(R.string.formula_editor_function_length));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.NUMBER_OF_ITEMS.name(), Integer.valueOf(R.string.formula_editor_function_number_of_items));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.LETTER.name(), Integer.valueOf(R.string.formula_editor_function_letter));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.JOIN.name(), Integer.valueOf(R.string.formula_editor_function_join));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DRONE_BATTERY_STATUS.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_battery_status));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DRONE_EMERGENCY_STATE.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_emergency_state));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DRONE_FLYING.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_flying));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DRONE_INITIALIZED.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_initialized));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DRONE_USB_ACTIVE.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_usb_active));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DRONE_USB_REMAINING_TIME.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_usb_remaining_time));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DRONE_CAMERA_READY.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_camera_ready));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DRONE_RECORD_READY.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_record_ready));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DRONE_RECORDING.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_recording));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DRONE_NUM_FRAMES.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_num_frames));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.ARDUINODIGITAL.name(), Integer.valueOf(R.string.formula_editor_function_arduino_read_pin_value_digital));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.ARDUINOANALOG.name(), Integer.valueOf(R.string.formula_editor_function_arduino_read_pin_value_analog));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.RASPIDIGITAL.name(), Integer.valueOf(R.string.formula_editor_function_raspi_read_pin_value_digital));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.FINGER_X.name(), Integer.valueOf(R.string.formula_editor_function_finger_x));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.FINGER_Y.name(), Integer.valueOf(R.string.formula_editor_function_finger_y));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.FINGER_TOUCHED.name(), Integer.valueOf(R.string.formula_editor_function_is_finger_touching));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.MULTI_FINGER_X.name(), Integer.valueOf(R.string.formula_editor_function_multi_finger_x));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.MULTI_FINGER_Y.name(), Integer.valueOf(R.string.formula_editor_function_multi_finger_y));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.MULTI_FINGER_TOUCHED.name(), Integer.valueOf(R.string.formula_editor_function_is_multi_finger_touching));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.LAST_FINGER_INDEX.name(), Integer.valueOf(R.string.formula_editor_function_index_of_last_finger));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.LIST_ITEM.name(), Integer.valueOf(R.string.formula_editor_function_list_item));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Functions.CONTAINS.name(), Integer.valueOf(R.string.formula_editor_function_contains));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.X_ACCELERATION.name(), Integer.valueOf(R.string.formula_editor_sensor_x_acceleration));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.Y_ACCELERATION.name(), Integer.valueOf(R.string.formula_editor_sensor_y_acceleration));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.Z_ACCELERATION.name(), Integer.valueOf(R.string.formula_editor_sensor_z_acceleration));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.COMPASS_DIRECTION.name(), Integer.valueOf(R.string.formula_editor_sensor_compass_direction));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.LATITUDE.name(), Integer.valueOf(R.string.formula_editor_sensor_latitude));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.LONGITUDE.name(), Integer.valueOf(R.string.formula_editor_sensor_longitude));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.LOCATION_ACCURACY.name(), Integer.valueOf(R.string.formula_editor_sensor_location_accuracy));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.ALTITUDE.name(), Integer.valueOf(R.string.formula_editor_sensor_altitude));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.X_INCLINATION.name(), Integer.valueOf(R.string.formula_editor_sensor_x_inclination));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.Y_INCLINATION.name(), Integer.valueOf(R.string.formula_editor_sensor_y_inclination));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.FACE_DETECTED.name(), Integer.valueOf(R.string.formula_editor_sensor_face_detected));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.FACE_SIZE.name(), Integer.valueOf(R.string.formula_editor_sensor_face_size));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.FACE_X_POSITION.name(), Integer.valueOf(R.string.formula_editor_sensor_face_x_position));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.FACE_Y_POSITION.name(), Integer.valueOf(R.string.formula_editor_sensor_face_y_position));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.GAMEPAD_A_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_a_pressed));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.GAMEPAD_B_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_b_pressed));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.GAMEPAD_UP_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_up_pressed));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.GAMEPAD_DOWN_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_down_pressed));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.GAMEPAD_LEFT_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_left_pressed));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.GAMEPAD_RIGHT_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_right_pressed));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.NXT_SENSOR_1.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_1));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.NXT_SENSOR_2.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_2));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.NXT_SENSOR_3.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_3));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.NXT_SENSOR_4.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_4));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.EV3_SENSOR_1.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_1));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.EV3_SENSOR_2.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_2));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.EV3_SENSOR_3.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_3));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.EV3_SENSOR_4.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_4));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.PHIRO_FRONT_LEFT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_front_left));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.PHIRO_FRONT_RIGHT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_front_right));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.PHIRO_SIDE_LEFT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_side_left));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.PHIRO_SIDE_RIGHT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_side_right));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.PHIRO_BOTTOM_LEFT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_bottom_left));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.PHIRO_BOTTOM_RIGHT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_bottom_right));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.NFC_TAG_ID.name(), Integer.valueOf(R.string.formula_editor_nfc_tag_id));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.NFC_TAG_MESSAGE.name(), Integer.valueOf(R.string.formula_editor_nfc_tag_message));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.LOUDNESS.name(), Integer.valueOf(R.string.formula_editor_sensor_loudness));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DATE_YEAR.name(), Integer.valueOf(R.string.formula_editor_sensor_date_year));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DATE_MONTH.name(), Integer.valueOf(R.string.formula_editor_sensor_date_month));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DATE_DAY.name(), Integer.valueOf(R.string.formula_editor_sensor_date_day));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.DATE_WEEKDAY.name(), Integer.valueOf(R.string.formula_editor_sensor_date_weekday));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.TIME_HOUR.name(), Integer.valueOf(R.string.formula_editor_sensor_time_hour));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.TIME_MINUTE.name(), Integer.valueOf(R.string.formula_editor_sensor_time_minute));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.TIME_SECOND.name(), Integer.valueOf(R.string.formula_editor_sensor_time_second));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_X.name(), Integer.valueOf(R.string.formula_editor_object_x));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_Y.name(), Integer.valueOf(R.string.formula_editor_object_y));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_TRANSPARENCY.name(), Integer.valueOf(R.string.formula_editor_object_transparency));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_BRIGHTNESS.name(), Integer.valueOf(R.string.formula_editor_object_brightness));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_COLOR.name(), Integer.valueOf(R.string.formula_editor_object_color));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_SIZE.name(), Integer.valueOf(R.string.formula_editor_object_size));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_ROTATION.name(), Integer.valueOf(R.string.formula_editor_object_rotation));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_LAYER.name(), Integer.valueOf(R.string.formula_editor_object_layer));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.COLLIDES_WITH_EDGE.name(), Integer.valueOf(R.string.formula_editor_function_collides_with_edge));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.COLLIDES_WITH_FINGER.name(), Integer.valueOf(R.string.formula_editor_function_touched));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_X_VELOCITY.name(), Integer.valueOf(R.string.formula_editor_object_x_velocity));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_Y_VELOCITY.name(), Integer.valueOf(R.string.formula_editor_object_y_velocity));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_ANGULAR_VELOCITY.name(), Integer.valueOf(R.string.formula_editor_object_angular_velocity));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_LOOK_NUMBER.name(), Integer.valueOf(R.string.formula_editor_object_look_number));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_LOOK_NAME.name(), Integer.valueOf(R.string.formula_editor_object_look_name));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_BACKGROUND_NUMBER.name(), Integer.valueOf(R.string.formula_editor_object_background_number));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_BACKGROUND_NAME.name(), Integer.valueOf(R.string.formula_editor_object_background_name));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Sensors.OBJECT_DISTANCE_TO.name(), Integer.valueOf(R.string.formula_editor_object_distance_to));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.LOGICAL_NOT.name(), Integer.valueOf(R.string.formula_editor_logic_not));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.NOT_EQUAL.name(), Integer.valueOf(R.string.formula_editor_logic_notequal));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.EQUAL.name(), Integer.valueOf(R.string.formula_editor_logic_equal));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.GREATER_OR_EQUAL.name(), Integer.valueOf(R.string.formula_editor_logic_greaterequal));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.GREATER_THAN.name(), Integer.valueOf(R.string.formula_editor_logic_greaterthan));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.LOGICAL_AND.name(), Integer.valueOf(R.string.formula_editor_logic_and));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.LOGICAL_OR.name(), Integer.valueOf(R.string.formula_editor_logic_or));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.SMALLER_OR_EQUAL.name(), Integer.valueOf(R.string.formula_editor_logic_leserequal));
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(Operators.SMALLER_THAN.name(), Integer.valueOf(R.string.formula_editor_logic_lesserthan));
    }

    public InternToExternGenerator(Context context) {
        this.context = context;
    }

    public void generateExternStringAndMapping(List<InternToken> internTokenFormula) {
        generateStringAndMappingInternal(internTokenFormula, false);
    }

    public void trimExternString(List<InternToken> internTokenFormula) {
        generateStringAndMappingInternal(internTokenFormula, true);
    }

    private void generateStringAndMappingInternal(List<InternToken> internTokenFormula, boolean trimNumbers) {
        Log.i(TAG, "generateExternStringAndMapping:enter");
        List<InternToken> internTokenList = new LinkedList();
        for (InternToken internToken : internTokenFormula) {
            internTokenList.add(internToken);
        }
        this.generatedExternInternRepresentationMapping = new ExternInternRepresentationMapping();
        this.generatedExternFormulaString = "";
        InternToken nextToken = null;
        InternToken internToken2 = null;
        int internTokenListIndex = 0;
        while (!internTokenList.isEmpty()) {
            if (appendWhiteSpace(internToken2, nextToken)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.generatedExternFormulaString);
                stringBuilder.append(FormatHelper.SPACE);
                this.generatedExternFormulaString = stringBuilder.toString();
            }
            int externStringStartIndex = this.generatedExternFormulaString.length();
            internToken2 = (InternToken) internTokenList.get(0);
            if (internTokenList.size() < 2) {
                nextToken = null;
            } else {
                nextToken = (InternToken) internTokenList.get(1);
            }
            String externTokenString = generateExternStringFromToken(internToken2, trimNumbers);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.generatedExternFormulaString);
            stringBuilder2.append(externTokenString);
            this.generatedExternFormulaString = stringBuilder2.toString();
            this.generatedExternInternRepresentationMapping.putMapping(externStringStartIndex, this.generatedExternFormulaString.length(), internTokenListIndex);
            internTokenList.remove(0);
            internTokenListIndex++;
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(this.generatedExternFormulaString);
        stringBuilder3.append(FormatHelper.SPACE);
        this.generatedExternFormulaString = stringBuilder3.toString();
    }

    private String generateExternStringFromToken(InternToken internToken, boolean trimNumbers) {
        String returnvalue;
        StringBuilder stringBuilder;
        switch (internToken.getInternTokenType()) {
            case NUMBER:
                return getExternStringForNumber(internToken.getTokenStringValue(), trimNumbers);
            case OPERATOR:
                returnvalue = internToken.getTokenStringValue();
                String mappingValue = getExternStringForInternTokenValue(internToken.getTokenStringValue(), this.context);
                return mappingValue == null ? returnvalue : mappingValue;
            case BRACKET_OPEN:
            case FUNCTION_PARAMETERS_BRACKET_OPEN:
                return Constants.OPENING_BRACE;
            case BRACKET_CLOSE:
            case FUNCTION_PARAMETERS_BRACKET_CLOSE:
                return ")";
            case FUNCTION_PARAMETER_DELIMITER:
                return ",";
            case USER_VARIABLE:
                stringBuilder = new StringBuilder();
                stringBuilder.append("\"");
                stringBuilder.append(internToken.getTokenStringValue());
                stringBuilder.append("\"");
                return stringBuilder.toString();
            case USER_LIST:
                stringBuilder = new StringBuilder();
                stringBuilder.append("*");
                stringBuilder.append(internToken.getTokenStringValue());
                stringBuilder.append("*");
                return stringBuilder.toString();
            case STRING:
                stringBuilder = new StringBuilder();
                stringBuilder.append(FormatHelper.QUOTE);
                stringBuilder.append(internToken.getTokenStringValue());
                stringBuilder.append(FormatHelper.QUOTE);
                return stringBuilder.toString();
            case COLLISION_FORMULA:
                returnvalue = CatroidApplication.getAppContext().getString(R.string.formula_editor_function_collision);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(returnvalue);
                stringBuilder2.append(Constants.OPENING_BRACE);
                stringBuilder2.append(internToken.getTokenStringValue());
                stringBuilder2.append(")");
                return stringBuilder2.toString();
            default:
                return getExternStringForInternTokenValue(internToken.getTokenStringValue(), this.context);
        }
    }

    private String getExternStringForNumber(String number, boolean trimNumbers) {
        if (trimNumbers) {
            number = getNumberExponentRepresentation(number);
        }
        if (!number.contains(".")) {
            return number;
        }
        String left = number.substring(null, number.indexOf(46));
        String right = number.substring(number.indexOf(46) + 1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(left);
        stringBuilder.append(getExternStringForInternTokenValue(".", this.context));
        stringBuilder.append(right);
        return stringBuilder.toString();
    }

    private String getNumberExponentRepresentation(String number) {
        Double value = Double.valueOf(Double.parseDouble(number));
        String numberToCheck = String.valueOf(value);
        if (value.doubleValue() >= 1.0d || !numberToCheck.contains("E")) {
            return FormatNumberUtil.cutTrailingZeros(number);
        }
        return numberToCheck;
    }

    private boolean appendWhiteSpace(InternToken currentToken, InternToken nextToken) {
        if (currentToken == null) {
            return false;
        }
        if (nextToken != null && C18441.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[nextToken.getInternTokenType().ordinal()] == 4) {
            return false;
        }
        return true;
    }

    public String getGeneratedExternFormulaString() {
        return this.generatedExternFormulaString;
    }

    public ExternInternRepresentationMapping getGeneratedExternInternRepresentationMapping() {
        return this.generatedExternInternRepresentationMapping;
    }

    private String getExternStringForInternTokenValue(String internTokenValue, Context context) {
        Integer stringResourceID = (Integer) INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.get(internTokenValue);
        if (stringResourceID == null) {
            return null;
        }
        return context.getString(stringResourceID.intValue());
    }

    public static int getMappedString(String token) {
        return ((Integer) INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.get(token)).intValue();
    }
}
