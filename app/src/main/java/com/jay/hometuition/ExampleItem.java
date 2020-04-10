package com.jay.hometuition;

public class ExampleItem {

    private int mImageResource,mCount;
    private String mName,mClass,mSchool,mBoard,mMarks,mGender,mSubject,mCity;

    public ExampleItem(int mImageResource, int mCount, String mName, String mClass, String mSchool, String mBoard, String mMarks, String mGender, String mSubject,String mCity) {
        this.mImageResource = mImageResource;
        this.mCount = mCount;
        this.mName = mName;
        this.mClass = mClass;
        this.mSchool = mSchool;
        this.mBoard = mBoard;
        this.mMarks = mMarks;
        this.mGender = mGender;
        this.mSubject = mSubject;
        this.mCity = mCity;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmSubject() {
        return mSubject;
    }

    public int getmCount() {
        return mCount;
    }

    public String getmSchool() {
        return mSchool;
    }

    public String getmBoard() {
        return mBoard;
    }

    public String getmMarks() {
        return mMarks;
    }

    public String getmGender() {
        return mGender;
    }

    public String getCount(){ return String.valueOf(mCount); }
    public int getmImageResource() {
        return mImageResource;
    }

    public String getmName() {
        return mName;
    }
    public String getmClass(){ return mClass; }
}
