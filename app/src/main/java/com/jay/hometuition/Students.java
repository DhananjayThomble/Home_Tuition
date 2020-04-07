package com.jay.hometuition;

public class Students {

    private static String mCity,mName,mGender,mClass,mSubject,mSchool,mBoard,mMarks,mStatus;
    private static int mLearners;
    private static String firebaseUser;

    //Setters
    public void setmStatus(String mStatus){ Students.mStatus = mStatus; }
    public void setmFirebaseUser(String mFirebaseUser){ Students.firebaseUser = mFirebaseUser; }
    public void setmCity(String mCity) {
        Students.mCity = mCity;
    }

    public void setmName(String mName) {
        Students.mName = mName;
    }

    public void setmGender(String mGender) {
        Students.mGender = mGender;
    }

    public void setmClass(String mClass) {
        Students.mClass = mClass;
    }

    public void setmSubject(String mSubject) {
        Students.mSubject = mSubject;
    }

    public void setmSchool(String mSchool) {
        Students.mSchool = mSchool;
    }

    public void setmBoard(String mBoard) {
        Students.mBoard = mBoard;
    }

    public void setmMarks(String mMarks) {
        Students.mMarks = mMarks;
    }

    public void setmLearners(int mLearners) {
        Students.mLearners = mLearners;
    }

    //Getters
    public String getmStatus() { return mStatus; }
    public String getFirebaseUser(){ return firebaseUser; }
    public String getmCity() {
        return mCity;
    }

    public String getmName() {
        return mName;
    }

    public String getmGender() {
        return mGender;
    }

    public String getmClass() {
        return mClass;
    }

    public String getmSubject() {
        return mSubject;
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

    public String getmLearners() {
        return String.valueOf(mLearners);
    }

    public String getAllstud(){
       String str1 = getmName() + " "+ getmBoard() + " "+ getmCity()+" "+getmClass()+" "+getmGender()+" "+getmSubject()+
                            " "+getmSchool()+" "+getmMarks()+" "+getmLearners();
       return str1;
    }

}
