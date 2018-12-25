package org.catrobat.catroid.io;

import com.thoughtworks.xstream.converters.SingleValueConverter;
import org.catrobat.catroid.formulaeditor.UserVariable;

public class XStreamUserVariableConverter implements SingleValueConverter {
    public boolean canConvert(Class type) {
        return type.equals(UserVariable.class);
    }

    public String toString(Object object) {
        return ((UserVariable) object).getName();
    }

    public Object fromString(String s) {
        return new UserVariable(s);
    }
}
