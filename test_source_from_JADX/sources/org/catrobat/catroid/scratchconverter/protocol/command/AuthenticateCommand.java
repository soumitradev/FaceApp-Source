package org.catrobat.catroid.scratchconverter.protocol.command;

import org.catrobat.catroid.scratchconverter.protocol.command.Command.ArgumentType;
import org.catrobat.catroid.scratchconverter.protocol.command.Command.Type;

public class AuthenticateCommand extends Command {
    public AuthenticateCommand(long clientID) {
        super(Type.AUTHENTICATE);
        addArgument(ArgumentType.CLIENT_ID, Long.valueOf(clientID));
    }
}
