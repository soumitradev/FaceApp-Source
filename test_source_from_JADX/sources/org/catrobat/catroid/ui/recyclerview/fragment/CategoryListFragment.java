package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor.Sensor;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.dialogs.LegoSensorPortConfigDialog.Builder;
import org.catrobat.catroid.ui.dialogs.LegoSensorPortConfigDialog.OnClickListener;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.recyclerview.adapter.CategoryListRVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.CategoryListRVAdapter.CategoryListItem;
import org.catrobat.catroid.ui.recyclerview.adapter.CategoryListRVAdapter.OnItemClickListener;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class CategoryListFragment extends Fragment implements OnItemClickListener {
    public static final String ACTION_BAR_TITLE_BUNDLE_ARGUMENT = "actionBarTitle";
    public static final String FRAGMENT_TAG_BUNDLE_ARGUMENT = "fragmentTag";
    public static final String FUNCTION_TAG = "functionFragment";
    private static final List<Integer> LIST_FUNCTIONS = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_number_of_items), Integer.valueOf(R.string.formula_editor_function_list_item), Integer.valueOf(R.string.formula_editor_function_contains)});
    private static final List<Integer> LIST_PARAMS = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_number_of_items_parameter), Integer.valueOf(R.string.formula_editor_function_list_item_parameter), Integer.valueOf(R.string.formula_editor_function_contains_parameter)});
    private static final List<Integer> LOCIG_COMPARISION = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_logic_equal), Integer.valueOf(R.string.formula_editor_logic_notequal), Integer.valueOf(R.string.formula_editor_logic_lesserthan), Integer.valueOf(R.string.formula_editor_logic_leserequal), Integer.valueOf(R.string.formula_editor_logic_greaterthan), Integer.valueOf(R.string.formula_editor_logic_greaterequal)});
    private static final List<Integer> LOGIC_BOOL = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_logic_and), Integer.valueOf(R.string.formula_editor_logic_or), Integer.valueOf(R.string.formula_editor_logic_not), Integer.valueOf(R.string.formula_editor_function_true), Integer.valueOf(R.string.formula_editor_function_false)});
    public static final String LOGIC_TAG = "logicFragment";
    private static final List<Integer> MATH_FUNCTIONS = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_sin), Integer.valueOf(R.string.formula_editor_function_cos), Integer.valueOf(R.string.formula_editor_function_tan), Integer.valueOf(R.string.formula_editor_function_ln), Integer.valueOf(R.string.formula_editor_function_log), Integer.valueOf(R.string.formula_editor_function_pi), Integer.valueOf(R.string.formula_editor_function_sqrt), Integer.valueOf(R.string.formula_editor_function_rand), Integer.valueOf(R.string.formula_editor_function_abs), Integer.valueOf(R.string.formula_editor_function_round), Integer.valueOf(R.string.formula_editor_function_mod), Integer.valueOf(R.string.formula_editor_function_arcsin), Integer.valueOf(R.string.formula_editor_function_arccos), Integer.valueOf(R.string.formula_editor_function_arctan), Integer.valueOf(R.string.formula_editor_function_exp), Integer.valueOf(R.string.formula_editor_function_power), Integer.valueOf(R.string.formula_editor_function_floor), Integer.valueOf(R.string.formula_editor_function_ceil), Integer.valueOf(R.string.formula_editor_function_max), Integer.valueOf(R.string.formula_editor_function_min)});
    private static final List<Integer> MATH_PARAMS = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_sin_parameter), Integer.valueOf(R.string.formula_editor_function_cos_parameter), Integer.valueOf(R.string.formula_editor_function_tan_parameter), Integer.valueOf(R.string.formula_editor_function_ln_parameter), Integer.valueOf(R.string.formula_editor_function_log_parameter), Integer.valueOf(R.string.formula_editor_function_pi_parameter), Integer.valueOf(R.string.formula_editor_function_sqrt_parameter), Integer.valueOf(R.string.formula_editor_function_rand_parameter), Integer.valueOf(R.string.formula_editor_function_abs_parameter), Integer.valueOf(R.string.formula_editor_function_round_parameter), Integer.valueOf(R.string.formula_editor_function_mod_parameter), Integer.valueOf(R.string.formula_editor_function_arcsin_parameter), Integer.valueOf(R.string.formula_editor_function_arccos_parameter), Integer.valueOf(R.string.formula_editor_function_arctan_parameter), Integer.valueOf(R.string.formula_editor_function_exp_parameter), Integer.valueOf(R.string.formula_editor_function_power_parameter), Integer.valueOf(R.string.formula_editor_function_floor_parameter), Integer.valueOf(R.string.formula_editor_function_ceil_parameter), Integer.valueOf(R.string.formula_editor_function_max_parameter), Integer.valueOf(R.string.formula_editor_function_min_parameter)});
    private static final List<Integer> OBJECT_BACKGROUND = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_object_background_number), Integer.valueOf(R.string.formula_editor_object_background_name)});
    private static final List<Integer> OBJECT_GENERAL_PROPERTIES = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_object_transparency), Integer.valueOf(R.string.formula_editor_object_brightness), Integer.valueOf(R.string.formula_editor_object_color)});
    private static final List<Integer> OBJECT_LOOK = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_object_look_number), Integer.valueOf(R.string.formula_editor_object_look_name)});
    private static final List<Integer> OBJECT_PHYSICAL_1 = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_object_x), Integer.valueOf(R.string.formula_editor_object_y), Integer.valueOf(R.string.formula_editor_object_size), Integer.valueOf(R.string.formula_editor_object_rotation), Integer.valueOf(R.string.formula_editor_object_layer)});
    private static final List<Integer> OBJECT_PHYSICAL_2 = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_collides_with_edge), Integer.valueOf(R.string.formula_editor_function_touched), Integer.valueOf(R.string.formula_editor_object_x_velocity), Integer.valueOf(R.string.formula_editor_object_y_velocity), Integer.valueOf(R.string.formula_editor_object_angular_velocity)});
    private static final List<Integer> OBJECT_PHYSICAL_COLLISION = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_collision)});
    public static final String OBJECT_TAG = "objectFragment";
    private static final List<Integer> SENSORS_ACCELERATION = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_x_acceleration), Integer.valueOf(R.string.formula_editor_sensor_y_acceleration), Integer.valueOf(R.string.formula_editor_sensor_z_acceleration)});
    private static final List<Integer> SENSORS_ARDUINO = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_arduino_read_pin_value_analog), Integer.valueOf(R.string.formula_editor_function_arduino_read_pin_value_digital)});
    private static final List<Integer> SENSORS_CAST_GAMEPAD = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_gamepad_a_pressed), Integer.valueOf(R.string.formula_editor_sensor_gamepad_b_pressed), Integer.valueOf(R.string.formula_editor_sensor_gamepad_up_pressed), Integer.valueOf(R.string.formula_editor_sensor_gamepad_down_pressed), Integer.valueOf(R.string.formula_editor_sensor_gamepad_left_pressed), Integer.valueOf(R.string.formula_editor_sensor_gamepad_right_pressed)});
    private static final List<Integer> SENSORS_COMPASS = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_compass_direction)});
    private static final List<Integer> SENSORS_DATE_TIME = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_date_year), Integer.valueOf(R.string.formula_editor_sensor_date_month), Integer.valueOf(R.string.formula_editor_sensor_date_day), Integer.valueOf(R.string.formula_editor_sensor_date_weekday), Integer.valueOf(R.string.formula_editor_sensor_time_hour), Integer.valueOf(R.string.formula_editor_sensor_time_minute), Integer.valueOf(R.string.formula_editor_sensor_time_second)});
    private static final List<Integer> SENSORS_DEFAULT = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_loudness), Integer.valueOf(R.string.formula_editor_function_touched)});
    private static final List<Integer> SENSORS_DRONE = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_drone_battery_status), Integer.valueOf(R.string.formula_editor_sensor_drone_emergency_state), Integer.valueOf(R.string.formula_editor_sensor_drone_flying), Integer.valueOf(R.string.formula_editor_sensor_drone_initialized), Integer.valueOf(R.string.formula_editor_sensor_drone_usb_active), Integer.valueOf(R.string.formula_editor_sensor_drone_usb_remaining_time), Integer.valueOf(R.string.formula_editor_sensor_drone_camera_ready), Integer.valueOf(R.string.formula_editor_sensor_drone_record_ready), Integer.valueOf(R.string.formula_editor_sensor_drone_recording), Integer.valueOf(R.string.formula_editor_sensor_drone_num_frames)});
    private static final List<Integer> SENSORS_EV3 = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_touch), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_infrared), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color_ambient), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color_reflected), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_hitechnic_color), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_temperature_c), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_temperature_f)});
    private static final List<Integer> SENSORS_FACE_DETECTION = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_face_detected), Integer.valueOf(R.string.formula_editor_sensor_face_size), Integer.valueOf(R.string.formula_editor_sensor_face_x_position), Integer.valueOf(R.string.formula_editor_sensor_face_y_position)});
    private static final List<Integer> SENSORS_GPS = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_latitude), Integer.valueOf(R.string.formula_editor_sensor_longitude), Integer.valueOf(R.string.formula_editor_sensor_location_accuracy), Integer.valueOf(R.string.formula_editor_sensor_altitude)});
    private static final List<Integer> SENSORS_INCLINATION = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_x_inclination), Integer.valueOf(R.string.formula_editor_sensor_y_inclination)});
    private static final List<Integer> SENSORS_NFC = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_nfc_tag_id), Integer.valueOf(R.string.formula_editor_nfc_tag_message)});
    private static final List<Integer> SENSORS_NXT = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_touch), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_sound), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_light), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_light_active), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_ultrasonic)});
    private static final List<Integer> SENSORS_PHIRO = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_phiro_sensor_front_left), Integer.valueOf(R.string.formula_editor_phiro_sensor_front_right), Integer.valueOf(R.string.formula_editor_phiro_sensor_side_left), Integer.valueOf(R.string.formula_editor_phiro_sensor_side_right), Integer.valueOf(R.string.formula_editor_phiro_sensor_bottom_left), Integer.valueOf(R.string.formula_editor_phiro_sensor_bottom_right)});
    private static final List<Integer> SENSORS_RASPBERRY = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_raspi_read_pin_value_digital)});
    private static final List<Integer> SENSORS_RASPBERRY_PARAMS = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_pin_default_parameter)});
    private static final List<Integer> SENSORS_TOUCH = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_finger_x), Integer.valueOf(R.string.formula_editor_function_finger_y), Integer.valueOf(R.string.formula_editor_function_is_finger_touching), Integer.valueOf(R.string.formula_editor_function_multi_finger_x), Integer.valueOf(R.string.formula_editor_function_multi_finger_y), Integer.valueOf(R.string.formula_editor_function_is_multi_finger_touching), Integer.valueOf(R.string.formula_editor_function_index_of_last_finger)});
    private static final List<Integer> SENSORS_TOUCH_PARAMS = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_no_parameter), Integer.valueOf(R.string.formula_editor_function_no_parameter), Integer.valueOf(R.string.formula_editor_function_no_parameter), Integer.valueOf(R.string.formula_editor_function_touch_parameter), Integer.valueOf(R.string.formula_editor_function_touch_parameter), Integer.valueOf(R.string.formula_editor_function_touch_parameter), Integer.valueOf(R.string.formula_editor_function_no_parameter)});
    public static final String SENSOR_TAG = "sensorFragment";
    private static final List<Integer> STRING_FUNCTIONS = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_length), Integer.valueOf(R.string.formula_editor_function_letter), Integer.valueOf(R.string.formula_editor_function_join)});
    private static final List<Integer> STRING_PARAMS = Arrays.asList(new Integer[]{Integer.valueOf(R.string.formula_editor_function_length_parameter), Integer.valueOf(R.string.formula_editor_function_letter_parameter), Integer.valueOf(R.string.formula_editor_function_join_parameter)});
    public static final String TAG = CategoryListFragment.class.getSimpleName();
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_list_view, container, false);
        this.recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view);
        setHasOptionsMenu(true);
        return parent;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeAdapter();
    }

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getArguments().getString(ACTION_BAR_TITLE_BUNDLE_ARGUMENT));
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        for (int index = 0; index < menu.size(); index++) {
            menu.getItem(index).setVisible(false);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onItemClick(CategoryListItem item) {
        switch (item.type) {
            case 0:
                ((FormulaEditorFragment) getFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG)).addResourceToActiveFormula(item.nameResId);
                getActivity().onBackPressed();
                return;
            case 1:
                showSelectSpriteDialog();
                return;
            case 2:
                showLegoSensorPortConfigDialog(item.nameResId, 0);
                return;
            case 3:
                showLegoSensorPortConfigDialog(item.nameResId, 1);
                return;
            default:
                return;
        }
    }

    private void showLegoSensorPortConfigDialog(int itemNameResId, final int type) {
        new Builder(getContext(), type, itemNameResId).setPositiveButton(getString(R.string.ok), new OnClickListener() {
            public void onPositiveButtonClick(DialogInterface dialog, int selectedPort, Enum selectedSensor) {
                TypedArray sensorPorts;
                if (type == 0) {
                    SettingsFragment.setLegoMindstormsNXTSensorMapping(CategoryListFragment.this.getActivity(), (Sensor) selectedSensor, SettingsFragment.NXT_SENSORS[selectedPort]);
                } else if (type == 1) {
                    SettingsFragment.setLegoMindstormsEV3SensorMapping(CategoryListFragment.this.getActivity(), (EV3Sensor.Sensor) selectedSensor, SettingsFragment.EV3_SENSORS[selectedPort]);
                }
                FormulaEditorFragment formulaEditor = (FormulaEditorFragment) CategoryListFragment.this.getFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
                if (type == 0) {
                    sensorPorts = CategoryListFragment.this.getResources().obtainTypedArray(R.array.formula_editor_nxt_ports);
                } else {
                    sensorPorts = CategoryListFragment.this.getResources().obtainTypedArray(R.array.formula_editor_ev3_ports);
                }
                int resourceId = sensorPorts.getResourceId(selectedPort, 0);
                if (resourceId != 0) {
                    formulaEditor.addResourceToActiveFormula(resourceId);
                    formulaEditor.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
                }
                CategoryListFragment.this.getActivity().onBackPressed();
            }
        }).create().show();
    }

    private void showSelectSpriteDialog() {
        final Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        List<Sprite> sprites = ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList();
        final List<Sprite> selectableSprites = new ArrayList();
        for (Sprite sprite : sprites) {
            if (!(sprites.indexOf(sprite) == 0 || sprite == currentSprite)) {
                selectableSprites.add(sprite);
            }
        }
        String[] selectableSpriteNames = new String[selectableSprites.size()];
        for (int i = 0; i < selectableSprites.size(); i++) {
            selectableSpriteNames[i] = ((Sprite) selectableSprites.get(i)).getName();
        }
        new AlertDialog$Builder(getActivity()).setTitle(R.string.formula_editor_function_collision).setItems(selectableSpriteNames, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Sprite selectedSprite = (Sprite) selectableSprites.get(which);
                currentSprite.createCollisionPolygons();
                selectedSprite.createCollisionPolygons();
                ((FormulaEditorFragment) CategoryListFragment.this.getFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG)).addCollideFormulaToActiveFormula(selectedSprite.getName());
                CategoryListFragment.this.getActivity().onBackPressed();
            }
        }).create().show();
    }

    private void initializeAdapter() {
        List<CategoryListItem> items;
        String argument = getArguments().getString(FRAGMENT_TAG_BUNDLE_ARGUMENT);
        if (OBJECT_TAG.equals(argument)) {
            items = getObjectItems();
        } else if (FUNCTION_TAG.equals(argument)) {
            items = getFunctionItems();
        } else if (LOGIC_TAG.equals(argument)) {
            items = getLogicItems();
        } else if (SENSOR_TAG.equals(argument)) {
            items = getSensorItems();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Argument for CategoryListFragent null or unknown: ");
            stringBuilder.append(argument);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        CategoryListRVAdapter adapter = new CategoryListRVAdapter(items);
        adapter.setOnItemClickListener(this);
        this.recyclerView.setAdapter(adapter);
    }

    private List<CategoryListItem> addHeader(List<CategoryListItem> subCategory, String header) {
        ((CategoryListItem) subCategory.get(0)).header = header;
        return subCategory;
    }

    private List<CategoryListItem> toCategoryListItems(List<Integer> nameResIds) {
        return toCategoryListItems(nameResIds, null, 0);
    }

    private List<CategoryListItem> toCategoryListItems(List<Integer> nameResIds, List<Integer> paramResIds) {
        return toCategoryListItems(nameResIds, paramResIds, 0);
    }

    private List<CategoryListItem> toCategoryListItems(List<Integer> nameResIds, int type) {
        return toCategoryListItems(nameResIds, null, type);
    }

    private List<CategoryListItem> toCategoryListItems(List<Integer> nameResIds, @Nullable List<Integer> paramResIds, int type) {
        if (paramResIds == null || paramResIds.size() == nameResIds.size()) {
            List<CategoryListItem> result = new ArrayList();
            for (int i = 0; i < nameResIds.size(); i++) {
                String param = "";
                if (paramResIds != null) {
                    param = getString(((Integer) paramResIds.get(i)).intValue());
                }
                int intValue = ((Integer) nameResIds.get(i)).intValue();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(getString(((Integer) nameResIds.get(i)).intValue()));
                stringBuilder.append(param);
                result.add(new CategoryListItem(intValue, stringBuilder.toString(), type));
            }
            return result;
        }
        throw new IllegalArgumentException("Sizes of paramResIds and nameResIds parameters do not fit");
    }

    private List<CategoryListItem> getObjectItems() {
        List<CategoryListItem> result = new ArrayList();
        result.addAll(getObjectGeneralPropertiesItems());
        result.addAll(getObjectPhysicalPropertiesItems());
        return result;
    }

    private List<CategoryListItem> getFunctionItems() {
        List<CategoryListItem> result = new ArrayList();
        result.addAll(addHeader(toCategoryListItems(MATH_FUNCTIONS, MATH_PARAMS), getString(R.string.formula_editor_functions_maths)));
        result.addAll(addHeader(toCategoryListItems(STRING_FUNCTIONS, STRING_PARAMS), getString(R.string.formula_editor_functions_strings)));
        result.addAll(addHeader(toCategoryListItems(LIST_FUNCTIONS, LIST_PARAMS), getString(R.string.formula_editor_functions_lists)));
        return result;
    }

    private List<CategoryListItem> getLogicItems() {
        List<CategoryListItem> result = new ArrayList();
        result.addAll(addHeader(toCategoryListItems(LOGIC_BOOL), getString(R.string.formula_editor_logic_boolean)));
        result.addAll(addHeader(toCategoryListItems(LOCIG_COMPARISION), getString(R.string.formula_editor_logic_comparison)));
        return result;
    }

    private List<CategoryListItem> getSensorItems() {
        List<CategoryListItem> result = new ArrayList();
        result.addAll(getDeviceSensorItems());
        result.addAll(getTouchDetectionSensorItems());
        result.addAll(getFaceDetectionSensorItems());
        result.addAll(getDateTimeSensorItems());
        result.addAll(getNxtSensorItems());
        result.addAll(getEv3SensorItems());
        result.addAll(getPhiroSensorItems());
        result.addAll(getArduinoSensorItems());
        result.addAll(getDroneSensorItems());
        result.addAll(getRaspberrySensorItems());
        result.addAll(getNfcItems());
        result.addAll(getCastGamepadSensorItems());
        return result;
    }

    private List<CategoryListItem> getObjectGeneralPropertiesItems() {
        List<Integer> resIds = new ArrayList(OBJECT_GENERAL_PROPERTIES);
        resIds.addAll(ProjectManager.getInstance().getCurrentSpritePosition() == 0 ? OBJECT_BACKGROUND : OBJECT_LOOK);
        return addHeader(toCategoryListItems(resIds), getString(R.string.formula_editor_object_general));
    }

    private List<CategoryListItem> getObjectPhysicalPropertiesItems() {
        List<CategoryListItem> result = toCategoryListItems(OBJECT_PHYSICAL_1);
        result.addAll(toCategoryListItems(OBJECT_PHYSICAL_COLLISION, 1));
        result.addAll(toCategoryListItems(OBJECT_PHYSICAL_2));
        return addHeader(result, getString(R.string.formula_editor_object_movement));
    }

    private List<CategoryListItem> getDeviceSensorItems() {
        Collection toCategoryListItems;
        List<CategoryListItem> deviceSensorItems = new ArrayList();
        deviceSensorItems.addAll(toCategoryListItems(SENSORS_DEFAULT));
        SensorHandler sensorHandler = SensorHandler.getInstance(getActivity());
        if (sensorHandler.accelerationAvailable()) {
            toCategoryListItems = toCategoryListItems(SENSORS_ACCELERATION);
        } else {
            toCategoryListItems = Collections.emptyList();
        }
        deviceSensorItems.addAll(toCategoryListItems);
        if (sensorHandler.inclinationAvailable()) {
            toCategoryListItems = toCategoryListItems(SENSORS_INCLINATION);
        } else {
            toCategoryListItems = Collections.emptyList();
        }
        deviceSensorItems.addAll(toCategoryListItems);
        if (sensorHandler.compassAvailable()) {
            toCategoryListItems = toCategoryListItems(SENSORS_COMPASS);
        } else {
            toCategoryListItems = Collections.emptyList();
        }
        deviceSensorItems.addAll(toCategoryListItems);
        deviceSensorItems.addAll(toCategoryListItems(SENSORS_GPS));
        return addHeader(deviceSensorItems, getString(R.string.formula_editor_device_sensors));
    }

    private List<CategoryListItem> getTouchDetectionSensorItems() {
        return addHeader(toCategoryListItems(SENSORS_TOUCH, SENSORS_TOUCH_PARAMS), getString(R.string.formula_editor_device_touch_detection));
    }

    private List<CategoryListItem> getFaceDetectionSensorItems() {
        if (!CameraManager.getInstance().hasBackCamera()) {
            if (CameraManager.getInstance().hasFrontCamera()) {
                return Collections.emptyList();
            }
        }
        return addHeader(toCategoryListItems(SENSORS_FACE_DETECTION), getString(R.string.formula_editor_device_face_detection));
    }

    private List<CategoryListItem> getDateTimeSensorItems() {
        return addHeader(toCategoryListItems(SENSORS_DATE_TIME), getString(R.string.formula_editor_device_date_and_time));
    }

    private List<CategoryListItem> getNxtSensorItems() {
        if (SettingsFragment.isMindstormsNXTSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_NXT, 2), getString(R.string.formula_editor_device_lego_nxt));
        }
        return Collections.emptyList();
    }

    private List<CategoryListItem> getEv3SensorItems() {
        if (SettingsFragment.isMindstormsEV3SharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_EV3, 3), getString(R.string.formula_editor_device_lego_ev3));
        }
        return Collections.emptyList();
    }

    private List<CategoryListItem> getPhiroSensorItems() {
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_PHIRO), getString(R.string.formula_editor_device_phiro));
        }
        return Collections.emptyList();
    }

    private List<CategoryListItem> getArduinoSensorItems() {
        if (SettingsFragment.isArduinoSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_ARDUINO), getString(R.string.formula_editor_device_arduino));
        }
        return Collections.emptyList();
    }

    private List<CategoryListItem> getDroneSensorItems() {
        if (SettingsFragment.isDroneSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_DRONE), getString(R.string.formula_editor_device_drone));
        }
        return Collections.emptyList();
    }

    private List<CategoryListItem> getRaspberrySensorItems() {
        if (SettingsFragment.isRaspiSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_RASPBERRY, SENSORS_RASPBERRY_PARAMS), getString(R.string.formula_editor_device_raspberry));
        }
        return Collections.emptyList();
    }

    private List<CategoryListItem> getNfcItems() {
        if (SettingsFragment.isNfcSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_NFC), getString(R.string.formula_editor_device_nfc));
        }
        return Collections.emptyList();
    }

    private List<CategoryListItem> getCastGamepadSensorItems() {
        if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            return addHeader(toCategoryListItems(SENSORS_CAST_GAMEPAD), getString(R.string.formula_editor_device_cast));
        }
        return Collections.emptyList();
    }
}
