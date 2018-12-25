package com.thoughtworks.xstream.mapper;

import kotlin.text.Typography;
import org.catrobat.catroid.common.BrickValues;

public class AbstractXmlFriendlyMapper extends MapperWrapper {
    private char dollarReplacementInClass = '-';
    private String dollarReplacementInField = "_DOLLAR_";
    private String noPackagePrefix = BrickValues.STRING_VALUE;
    private String underscoreReplacementInField = "__";

    protected AbstractXmlFriendlyMapper(Mapper wrapped) {
        super(wrapped);
    }

    protected String escapeClassName(String className) {
        className = className.replace(Typography.dollar, this.dollarReplacementInClass);
        if (className.charAt(0) != this.dollarReplacementInClass) {
            return className;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.noPackagePrefix);
        stringBuilder.append(className);
        return stringBuilder.toString();
    }

    protected String unescapeClassName(String className) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.noPackagePrefix);
        stringBuilder.append(this.dollarReplacementInClass);
        if (className.startsWith(stringBuilder.toString())) {
            className = className.substring(this.noPackagePrefix.length());
        }
        return className.replace(this.dollarReplacementInClass, Typography.dollar);
    }

    protected String escapeFieldName(String fieldName) {
        StringBuffer result = new StringBuffer();
        int length = fieldName.length();
        for (int i = 0; i < length; i++) {
            char c = fieldName.charAt(i);
            if (c == Typography.dollar) {
                result.append(this.dollarReplacementInField);
            } else if (c == '_') {
                result.append(this.underscoreReplacementInField);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    protected String unescapeFieldName(String xmlName) {
        StringBuffer result = new StringBuffer();
        int length = xmlName.length();
        int i = 0;
        while (i < length) {
            char c = xmlName.charAt(i);
            if (stringFoundAt(xmlName, i, this.underscoreReplacementInField)) {
                i += this.underscoreReplacementInField.length() - 1;
                result.append('_');
            } else if (stringFoundAt(xmlName, i, this.dollarReplacementInField)) {
                i += this.dollarReplacementInField.length() - 1;
                result.append(Typography.dollar);
            } else {
                result.append(c);
            }
            i++;
        }
        return result.toString();
    }

    private boolean stringFoundAt(String name, int i, String replacement) {
        if (name.length() < replacement.length() + i || !name.substring(i, replacement.length() + i).equals(replacement)) {
            return false;
        }
        return true;
    }
}
