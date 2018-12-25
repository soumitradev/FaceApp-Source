package org.catrobat.catroid.ui.recyclerview.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.catrobat.catroid.common.Nameable;

public class UniqueNameProvider {
    public String getUniqueName(String name, List<String> scope) {
        if (!scope.contains(name)) {
            return name;
        }
        Matcher matcher = Pattern.compile("\\((\\d+)\\)").matcher(name);
        int n = 1;
        if (matcher.find()) {
            name = name.replace(matcher.group(0), "").trim();
            n = Integer.parseInt(matcher.group(1));
        }
        while (n < Integer.MAX_VALUE) {
            String newName = new StringBuilder();
            newName.append(name);
            newName.append(" (");
            newName.append(n);
            newName.append(")");
            newName = newName.toString();
            if (!scope.contains(newName)) {
                return newName;
            }
            n++;
        }
        return name;
    }

    public String getUniqueNameInNameables(String name, List<? extends Nameable> scope) {
        List<String> names = new ArrayList();
        for (Nameable nameable : scope) {
            names.add(nameable.getName());
        }
        return getUniqueName(name, names);
    }
}
