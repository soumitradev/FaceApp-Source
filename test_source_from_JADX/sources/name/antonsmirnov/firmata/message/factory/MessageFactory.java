package name.antonsmirnov.firmata.message.factory;

import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage;

public interface MessageFactory {
    ReportAnalogPinMessage analogRead(int i) throws MessageValidationException;

    AnalogMessage analogWrite(int i, int i2) throws MessageValidationException;

    ReportDigitalPortMessage digitalRead(int i) throws MessageValidationException;

    DigitalMessage digitalWrite(int i, int i2) throws MessageValidationException;

    SetPinModeMessage pinMode(int i, int i2) throws MessageValidationException;
}
