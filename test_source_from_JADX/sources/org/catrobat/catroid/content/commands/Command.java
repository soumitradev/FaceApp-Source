package org.catrobat.catroid.content.commands;

public interface Command {
    void execute();

    void undo();
}
