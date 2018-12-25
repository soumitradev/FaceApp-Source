package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_ENUM {
    eARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_RS_TRAVIS(0, "Travis (RS taxi) model."),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_RS_MARS(1, "Mars (RS space) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_RS_SWAT(2, "SWAT (RS SWAT) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_RS_MCLANE(3, "Mc Lane (RS police) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_RS_BLAZE(4, "Blaze (RS fire) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_RS_ORAK(5, "Orak (RS carbon hydrofoil) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_RS_NEWZ(6, "New Z (RS wooden hydrofoil) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_JS_MARSHALL(7, "Marshall (JS fire) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_JS_DIESEL(8, "Diesel (JS SWAT) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_JS_BUZZ(9, "Buzz (JS space) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_JS_MAX(10, "Max (JS F1) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_JS_JETT(11, "Jett (JS flames) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_JS_TUKTUK(12, "Tuk-Tuk (JS taxi) model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_SW_BLACK(13, "Swing black model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_SW_WHITE(14, "Swing white model"),
    ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_MAX(15);
    
    static HashMap<Integer, ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_ENUM retVal = (ARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCOMMANDS_COMMON_COMMONSTATE_PRODUCTMODEL_MODEL_UNKNOWN_ENUM_VALUE;
        }
        return retVal;
    }

    public String toString() {
        if (this.comment != null) {
            return this.comment;
        }
        return super.toString();
    }
}
