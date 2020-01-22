package com.peter.guardianangel.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ChildrenData implements Parcelable {
    private int month;
    // 该月第几周
    private int week;
    // 起始日
    private int start;
    //结束日
    private int end;

    public ChildrenData() {
    }

    protected ChildrenData(Parcel in) {
        month = in.readInt();
        week = in.readInt();
        start = in.readInt();
        end = in.readInt();
    }

    public static final Creator<ChildrenData> CREATOR = new Creator<ChildrenData>() {
        @Override
        public ChildrenData createFromParcel(Parcel in) {
            return new ChildrenData(in);
        }

        @Override
        public ChildrenData[] newArray(int size) {
            return new ChildrenData[size];
        }
    };

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(month);
        dest.writeInt(week);
        dest.writeInt(start);
        dest.writeInt(end);
    }
}
