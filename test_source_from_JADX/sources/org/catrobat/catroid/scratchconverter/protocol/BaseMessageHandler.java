package org.catrobat.catroid.scratchconverter.protocol;

import org.catrobat.catroid.scratchconverter.protocol.message.base.BaseMessage;

public interface BaseMessageHandler {
    void onBaseMessage(BaseMessage baseMessage);
}
