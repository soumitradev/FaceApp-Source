package name.antonsmirnov.firmata.wrapper;

import java.util.Date;
import java.util.GregorianCalendar;

public class DateMessagePropertyManager extends MessagePropertyManager<Date> {
    private static final String KEY = "date";

    public DateMessagePropertyManager() {
        super(KEY);
    }

    protected Date createProperty() {
        return GregorianCalendar.getInstance().getTime();
    }
}