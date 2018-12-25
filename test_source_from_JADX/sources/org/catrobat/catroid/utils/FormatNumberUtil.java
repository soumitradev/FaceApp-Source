package org.catrobat.catroid.utils;

import java.math.BigDecimal;

public final class FormatNumberUtil {
    private FormatNumberUtil() {
        throw new AssertionError();
    }

    public static String cutTrailingZeros(String number) {
        BigDecimal decimal = new BigDecimal(number).stripTrailingZeros();
        if (decimal.compareTo(BigDecimal.ZERO) == 0) {
            decimal = BigDecimal.ZERO;
        }
        return decimal.toPlainString();
    }
}
