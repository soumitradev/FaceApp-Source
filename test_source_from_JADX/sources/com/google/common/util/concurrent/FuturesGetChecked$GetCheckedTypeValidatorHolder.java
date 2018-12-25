package com.google.common.util.concurrent;

import com.google.common.annotations.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

@VisibleForTesting
class FuturesGetChecked$GetCheckedTypeValidatorHolder {
    static final FuturesGetChecked$GetCheckedTypeValidator BEST_VALIDATOR = getBestValidator();
    static final String CLASS_VALUE_VALIDATOR_NAME;

    @IgnoreJRERequirement
    /* renamed from: com.google.common.util.concurrent.FuturesGetChecked$GetCheckedTypeValidatorHolder$ClassValueValidator */
    enum ClassValueValidator implements FuturesGetChecked$GetCheckedTypeValidator {
        INSTANCE;
        
        private static final ClassValue<Boolean> isValidClass = null;

        /* renamed from: com.google.common.util.concurrent.FuturesGetChecked$GetCheckedTypeValidatorHolder$ClassValueValidator$1 */
        static class C05991 extends ClassValue<Boolean> {
            C05991() {
            }

            protected Boolean computeValue(Class<?> type) {
                FuturesGetChecked.checkExceptionClassValidity(type.asSubclass(Exception.class));
                return Boolean.valueOf(true);
            }
        }

        static {
            isValidClass = new C05991();
        }

        public void validateClass(Class<? extends Exception> exceptionClass) {
            isValidClass.get(exceptionClass);
        }
    }

    /* renamed from: com.google.common.util.concurrent.FuturesGetChecked$GetCheckedTypeValidatorHolder$WeakSetValidator */
    enum WeakSetValidator implements FuturesGetChecked$GetCheckedTypeValidator {
        INSTANCE;
        
        private static final Set<WeakReference<Class<? extends Exception>>> validClasses = null;

        static {
            validClasses = new CopyOnWriteArraySet();
        }

        public void validateClass(Class<? extends Exception> exceptionClass) {
            for (WeakReference<Class<? extends Exception>> knownGood : validClasses) {
                if (exceptionClass.equals(knownGood.get())) {
                    return;
                }
            }
            FuturesGetChecked.checkExceptionClassValidity(exceptionClass);
            if (validClasses.size() > 1000) {
                validClasses.clear();
            }
            validClasses.add(new WeakReference(exceptionClass));
        }
    }

    FuturesGetChecked$GetCheckedTypeValidatorHolder() {
    }

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FuturesGetChecked$GetCheckedTypeValidatorHolder.class.getName());
        stringBuilder.append("$ClassValueValidator");
        CLASS_VALUE_VALIDATOR_NAME = stringBuilder.toString();
    }

    static FuturesGetChecked$GetCheckedTypeValidator getBestValidator() {
        try {
            return (FuturesGetChecked$GetCheckedTypeValidator) Class.forName(CLASS_VALUE_VALIDATOR_NAME).getEnumConstants()[0];
        } catch (Throwable th) {
            return FuturesGetChecked.weakSetValidator();
        }
    }
}
