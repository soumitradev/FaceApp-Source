package org.catrobat.catroid.web;

import java.io.InterruptedIOException;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.common.ScratchSearchResult;

public interface ScratchDataFetcher {
    ScratchSearchResult fetchDefaultScratchPrograms() throws WebconnectionException, InterruptedIOException;

    ScratchProgramData fetchScratchProgramDetails(long j) throws WebconnectionException, WebScratchProgramException, InterruptedIOException;

    ScratchSearchResult scratchSearch(String str, int i, int i2) throws WebconnectionException, InterruptedIOException;
}
