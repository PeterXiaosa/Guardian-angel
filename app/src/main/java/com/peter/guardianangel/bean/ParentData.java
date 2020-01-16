package com.peter.guardianangel.bean;

import java.util.List;

public class ParentData {
    private int month;

    private List<ChildrenData> childrenDataList;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<ChildrenData> getChildrenDataList() {
        return childrenDataList;
    }

    public void setChildrenDataList(List<ChildrenData> childrenDataList) {
        this.childrenDataList = childrenDataList;
    }
}
