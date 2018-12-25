package org.catrobat.catroid.scratchconverter.protocol.command;

import org.catrobat.catroid.scratchconverter.protocol.command.Command.ArgumentType;
import org.catrobat.catroid.scratchconverter.protocol.command.Command.Type;

public class ScheduleJobCommand extends Command {
    public ScheduleJobCommand(long jobID, boolean force, boolean verbose) {
        super(Type.SCHEDULE_JOB);
        addArgument(ArgumentType.JOB_ID, Long.valueOf(jobID));
        addArgument(ArgumentType.FORCE, Boolean.valueOf(force));
        addArgument(ArgumentType.VERBOSE, Boolean.valueOf(verbose));
    }
}
