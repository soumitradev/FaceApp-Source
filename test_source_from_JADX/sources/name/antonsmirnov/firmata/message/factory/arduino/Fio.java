package name.antonsmirnov.firmata.message.factory.arduino;

import name.antonsmirnov.firmata.message.factory.BoardMessageFactory;

public class Fio extends BoardMessageFactory {
    public static final int MAX_PIN = 13;

    public Fio() {
        super(0, 13, BoardMessageFactory.arrayFromTo(0, 7), new int[]{3, 5, 6, 9, 10, 11});
    }
}
