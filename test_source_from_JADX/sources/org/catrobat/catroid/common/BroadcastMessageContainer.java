package org.catrobat.catroid.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.catrobat.catroid.ProjectManager;

public class BroadcastMessageContainer {
    private final List<String> broadcastMessages = new ArrayList();

    public void update() {
        Set<String> usedMessages = ProjectManager.getInstance().getCurrentlyEditedScene().getBroadcastMessagesInUse();
        this.broadcastMessages.clear();
        this.broadcastMessages.addAll(usedMessages);
    }

    public boolean addBroadcastMessage(String messageToAdd) {
        return (messageToAdd == null || messageToAdd.isEmpty() || this.broadcastMessages.contains(messageToAdd) || !this.broadcastMessages.add(messageToAdd)) ? false : true;
    }

    public List<String> getBroadcastMessages() {
        if (this.broadcastMessages.size() == 0) {
            update();
        }
        return this.broadcastMessages;
    }
}
