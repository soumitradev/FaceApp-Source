package name.antonsmirnov.firmata.message.factory.arduino;

import name.antonsmirnov.firmata.message.factory.BoardMessageFactory;

public class Uno extends BoardMessageFactory {
    public static final int MAX_PIN = 13;

    public Uno() {
        super(0, 13, BoardMessageFactory.arrayFromTo(0, 5), new int[]{3, 5, 6, 9, 10, 11});
    }
}
