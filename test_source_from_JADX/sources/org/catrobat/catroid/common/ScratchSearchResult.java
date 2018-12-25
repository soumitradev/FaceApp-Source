package org.catrobat.catroid.common;

import java.util.List;

public class ScratchSearchResult {
    private int pageNumber;
    private List<ScratchProgramData> programDataList;
    private String query;

    public ScratchSearchResult(List<ScratchProgramData> programDataList, String query, int pageNumber) {
        this.query = query;
        this.programDataList = programDataList;
        this.pageNumber = pageNumber;
    }

    public List<ScratchProgramData> getProgramDataList() {
        return this.programDataList;
    }

    public String getQuery() {
        return this.query;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }
}
