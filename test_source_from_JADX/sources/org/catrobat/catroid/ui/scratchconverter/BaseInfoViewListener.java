package org.catrobat.catroid.ui.scratchconverter;

import org.catrobat.catroid.scratchconverter.protocol.Job;

public interface BaseInfoViewListener {
    void onError(String str);

    void onJobsInfo(Job[] jobArr);
}
