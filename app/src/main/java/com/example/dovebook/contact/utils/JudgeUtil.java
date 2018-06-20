package com.example.dovebook.contact.utils;

public class JudgeUtil {

    private static Boolean sContactListChange=false;

    public static Boolean isContactListChange(){
        return sContactListChange;
    }

    public static void setChangeSign(Boolean sign){
        sContactListChange=sign;
    }
}
