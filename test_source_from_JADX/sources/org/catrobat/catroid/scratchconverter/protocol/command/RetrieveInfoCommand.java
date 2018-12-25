package org.catrobat.catroid.scratchconverter.protocol.command;

import org.catrobat.catroid.scratchconverter.protocol.command.Command.Type;

public class RetrieveInfoCommand extends Command {
    public RetrieveInfoCommand() {
        super(Type.RETRIEVE_INFO);
    }
}
