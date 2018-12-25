package name.antonsmirnov.firmata.message.factory.arduino;

import name.antonsmirnov.firmata.message.factory.BoardMessageFactory;

public class Mega2560 extends BoardMessageFactory {
    public static final int MAX_PIN = 54;

    public Mega2560() {
        super(0, 54, BoardMessageFactory.arrayFromTo(0, 15), BoardMessageFactory.union(BoardMessageFactory.arrayFromTo(2, 13), BoardMessageFactory.arrayFromTo(44, 46)));
    }
}
